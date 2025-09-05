package pt.ulisboa.tecnico.rnl.dei.dms.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pt.ulisboa.tecnico.rnl.dei.dms.file.service.FileService;

import java.io.IOException;

@RestController
public class FileController {
	@Autowired
	private FileService fileService;

	@PostMapping("files/upload")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
		String uploadFile = fileService.uploadFile(file);
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadFile);
	}

	@GetMapping("files//download/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName){
		byte[] file = fileService.downloadFile(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.ALL)
				.body(file);
	}
}
