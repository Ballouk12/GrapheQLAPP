package ma.project.graph.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Float amount;
    @Temporal(TemporalType.DATE)
    private Date dateTransaction;
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;
    @ManyToOne
    private Compte compte ;
}
