package ma.project.graph.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private Long idCompte;
    private Float amount;
    private String dateTransaction;
    private TypeTransaction type;
}
