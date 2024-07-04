package pocketDock.com.pocketDock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pocketDock.com.pocketDock.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}