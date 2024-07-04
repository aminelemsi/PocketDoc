package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pocketDock.com.pocketDock.entity.Publication;


import java.util.List;


@Repository
public interface PubRepository extends JpaRepository<Publication, Long>
{
    @Query("SELECT p FROM Publication p ORDER BY p.datePub DESC")
    List<Publication> findOrderByDate();
    // Classement par date de création (les plus récentes en premier : décroissant )

    /*@Query("SELECT p FROM Publication p ORDER BY (SELECT COUNT(l) FROM PubLike l WHERE l.publication = p) DESC")
    List<Publication> findOrderByPopularity();
    // Classement par popularité (nombre de likes)*/

    @Query("SELECT p FROM Publication p ORDER BY p.likes DESC")
    List<Publication> findOrderByPopularity();

    @Query("SELECT p FROM Publication p ORDER BY SIZE(p.commentaires) DESC")
    List<Publication> findOrderByNumberOfResponses();
    // Classement par nombre de réponses

    List<Publication> findBlogByStatusIs(Boolean status);


    List<Publication> findBySujetContainingIgnoreCase(String sujet);
}