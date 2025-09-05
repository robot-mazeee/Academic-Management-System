package pt.ulisboa.tecnico.rnl.dei.dms.file.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ulisboa.tecnico.rnl.dei.dms.file.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>  {
    Optional<File> findByName(String name);
}
