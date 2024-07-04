package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pocketDock.com.pocketDock.entity.Badwords;

@Repository
public interface BadwordsRepository extends JpaRepository<Badwords, Long> {
}
