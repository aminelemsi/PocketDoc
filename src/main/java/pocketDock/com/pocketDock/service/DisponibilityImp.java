package pocketDock.com.pocketDock.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import pocketDock.com.pocketDock.entity.*;
import pocketDock.com.pocketDock.repository.DisponibiliteRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class DisponibilityImp implements IDisponibilite{
    private final DisponibiliteRepository disponibiliterepo;
    private final UserServiceImpl userservice;
    @Transactional
    public List<Disponibilite> createWeeklyAvailabilities(Integer doctorId, Status status, LocalDateTime startTime, LocalDateTime endTime) {
        List<Disponibilite> availabilities = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = startDate.plusDays(6);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDateTime startMorning = LocalDateTime.of(date, LocalTime.of(8, 0));
            LocalDateTime endMorning = LocalDateTime.of(date, LocalTime.of(12, 0));
            LocalDateTime startAfternoon = LocalDateTime.of(date, LocalTime.of(14, 0));
            LocalDateTime endAfternoon = LocalDateTime.of(date, LocalTime.of(18, 0));

            availabilities.addAll(createAvailabilitySlots(doctorId, status, startMorning, endMorning, 30));
            availabilities.addAll(createAvailabilitySlots(doctorId, status, startAfternoon, endAfternoon, 30));
        }

        return disponibiliterepo.saveAll(availabilities);
    }

    public List<Disponibilite> createAvailabilitySlots(Integer doctorId, Status status, LocalDateTime startTime, LocalDateTime endTime, int slotDurationMinutes) {
        List<Disponibilite> slots = new ArrayList<>();
        OurUsers doctor = userservice.findByIdAndRole(doctorId, "DOCTOR");

        LocalDateTime current = startTime;

        while (current.isBefore(endTime)) {
            if (disponibiliterepo.countByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(doctor, current, current.plusMinutes(slotDurationMinutes)) == 0) {
                Disponibilite availability = new Disponibilite();
                availability.setDoctor((Doctor) doctor);
                availability.setStatus(status);
                availability.setStartTime(current);
                current = current.plusMinutes(slotDurationMinutes);
                availability.setEndTime(current);
                slots.add(availability);
            } else {
                current = current.plusMinutes(slotDurationMinutes);
            }
        }

        return slots;
    }

}
