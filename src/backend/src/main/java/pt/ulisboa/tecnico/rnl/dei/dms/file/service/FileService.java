package pt.ulisboa.tecnico.rnl.dei.dms.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.file.repository.FileRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.DEIException;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    private File fetchFileOrThrow(String fileName) {
        return fileRepository.findByName(fileName)
                .orElseThrow(() -> new DEIException(ErrorMessage.NO_SUCH_FILE, fileName));
    }

    @Transactional
    public static byte[] compressFile(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    @Transactional
    public static byte[] decompressFile(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    @Transactional
    public String uploadFile(MultipartFile file) throws IOException {
        File newFile = fileRepository.save(File.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .data(compressFile(file.getBytes())).build());
        return newFile.getName();
    }

    @Transactional
    public byte[] downloadFile(String fileName) {
        File file = fetchFileOrThrow(fileName);

        return decompressFile(file.getData());
    }
}
