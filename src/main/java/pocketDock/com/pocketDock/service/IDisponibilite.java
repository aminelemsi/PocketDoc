package pocketDock.com.pocketDock.service;



import pocketDock.com.pocketDock.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IDisponibilite {
    public List<Disponibilite> createWeeklyAvailabilities(Integer doctorId, Status status, LocalDateTime startTime, LocalDateTime endTime);
    public List<Disponibilite> createAvailabilitySlots(Integer doctorId, Status status, LocalDateTime startTime, LocalDateTime endTime, int slotDurationMinutes);
}