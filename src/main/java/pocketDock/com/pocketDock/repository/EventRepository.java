package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.dto.EventStatDto;
import pocketDock.com.pocketDock.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EventRepository extends JpaRepository<Event,Integer> {

    @Query("SELECT NEW pocketDock.com.pocketDock.dto.EventStatDto(e.id, e.title, COUNT(i)) FROM Event e LEFT JOIN e.Inscris i GROUP BY e.id, e.title ORDER BY COUNT(i) DESC")
    List<EventStatDto> findEventStats();

    @Query("SELECT AVG(size(e.Inscris)) FROM Event e")
    Double getAverageInscriptionsPerEvent();

    List<Event> findAllByUserId(Integer userId);


    @Query("SELECT e.createdDate as EventDate FROM Event e WHERE e.date BETWEEN :start AND :end")
    Map<LocalDateTime, EventDto> findEventByDate(LocalDateTime start, LocalDateTime end);
    @Query("SELECT e FROM Event e WHERE FUNCTION('DATE', e.date) = :tomorrow")
    List<Event> findEventsForTomorrow(LocalDate tomorrow);

    List<Event> findEventsByDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT e FROM Event e WHERE e.createdDate IS NULL OR e.lastModifiedDate <> e.createdDate")
    List<Event> findEventModified();

/*    @Query("SELECT e.id, COUNT(i) " +
            "FROM Event e " +
            "LEFT JOIN e.Inscris i " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(i) DESC")
    List<Object[]> getEventStatistics();*/


}
