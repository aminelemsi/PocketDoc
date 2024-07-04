package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pocketDock.com.pocketDock.entity.Reponse;

import java.util.List;
@Repository

public interface ReponseRepository extends JpaRepository<Reponse, Long> {

    // Define method to find responses by reclamation
    @Query("SELECT r FROM Reponse r WHERE r.reclamations.idRec = :reclamationId")
    List<Reponse> findByReclamationId(@Param("reclamationId") Long reclamationId);

}
