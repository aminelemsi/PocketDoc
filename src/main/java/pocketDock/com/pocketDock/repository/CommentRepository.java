package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pocketDock.com.pocketDock.entity.Commentaire;
import pocketDock.com.pocketDock.entity.Publication;


import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Commentaire,Long>
{
    List<Commentaire> findByPublication(Publication publication);

}
