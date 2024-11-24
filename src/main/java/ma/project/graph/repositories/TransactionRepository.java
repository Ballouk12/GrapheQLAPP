package ma.project.graph.repositories;

import ma.project.graph.entities.Compte;
import ma.project.graph.entities.Transaction;
import ma.project.graph.entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction,Long> {
    List<Transaction> findByCompte(Compte compte);
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type=:typetansaction")
    double sumByType(@Param("typetansaction") TypeTransaction typetansaction);

}
