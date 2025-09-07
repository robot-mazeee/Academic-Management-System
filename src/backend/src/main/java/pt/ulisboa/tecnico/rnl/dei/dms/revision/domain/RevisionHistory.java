package pt.ulisboa.tecnico.rnl.dei.dms.revision.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RevisionHistory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "revision_id")
    private Revision revision;

    private LocalDateTime dateTime;

    private RevisionStatus status;

    protected RevisionHistory() {}

    public RevisionHistory(Revision revision, RevisionStatus status) {
        this.status = status;
        this.revision = revision;
        this.dateTime = LocalDateTime.now();;
    }
}
