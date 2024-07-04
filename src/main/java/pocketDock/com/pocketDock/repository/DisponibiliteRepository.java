package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pocketDock.com.pocketDock.entity.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
    Optional<Disponibilite> findByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);
    Disponibilite findByDoctor(Doctor doctor);
    int countByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(OurUsers doctor, LocalDateTime startTime, LocalDateTime endTime);
    Disponibilite findByDoctorAndStartTime(OurUsers doctor, LocalDateTime rendezVousDate);

}