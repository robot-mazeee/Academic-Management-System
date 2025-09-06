package pt.ulisboa.tecnico.rnl.dei.dms.file.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
    private Long curricularUnitId;

    @Lob
    @Column(nullable = false)
    private byte[] data;
}
