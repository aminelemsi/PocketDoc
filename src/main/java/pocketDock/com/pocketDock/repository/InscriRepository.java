package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pocketDock.com.pocketDock.entity.Inscription;

import java.util.List;

public interface InscriRepository extends JpaRepository<Inscription,Integer> {
    List<Inscription> findAllByUserId(Integer userId);
    List<Inscription> findAllByEventId(Integer eventId);

    Inscription findByEventId(Integer evntId);

    @Query("SELECT i.user.email FROM Inscription i WHERE i.id = :inscriptionId")
    String findUserEmailByInscriptionId(Long inscriptionId);
    @Query("SELECT i.user.name FROM Inscription i WHERE i.id = :inscriptionId")
    String findUserUsernameByInscriptionId(Long inscriptionId);
    @Query("SELECT i.event.title FROM Inscription i WHERE i.id = :inscriptionId")
    String findEventTitleByInscriptionId(Long inscriptionId);
}
