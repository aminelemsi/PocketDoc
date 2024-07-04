package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pocketDock.com.pocketDock.entity.Rendez_Vous;

import java.time.LocalDateTime;
import java.util.List;

public interface Rendez_VousRepository extends JpaRepository<Rendez_Vous, Long> {
    List<Rendez_Vous> findByPatientId(Long patientId);
    List<Rendez_Vous> findByDoctorId(Long doctorId);
    @Query("SELECT MONTH(r.date), COUNT(r) FROM Rendez_Vous r GROUP BY MONTH(r.date)")
    List<Object[]> countConsultationsByMonth();

    @Query("SELECT r FROM Rendez_Vous r WHERE r.date > ?1 AND r.date <= ?2")
    List<Rendez_Vous> findRDVsForNextHour(LocalDateTime now, LocalDateTime nextHour);
    @Query("SELECT r.doctor, COUNT(r) FROM Rendez_Vous r WHERE r.doctor.role = 'DOCTOR' GROUP BY r.doctor")
    List<Object[]> countConsultationsByDoctor();
}