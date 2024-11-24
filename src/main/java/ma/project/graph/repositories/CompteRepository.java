package ma.project.graph.repositories;

import ma.project.graph.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompteRepository extends JpaRepository<Compte,Long> {
    @Query("SELECT SUM(c.solde) FROM Compte c")
    Float sumSoldes();
}
