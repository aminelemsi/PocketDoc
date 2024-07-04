package pocketDock.com.pocketDock.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.entity.OurUsers;
import pocketDock.com.pocketDock.entity.Publication;
import pocketDock.com.pocketDock.entity.Reclamation;
import pocketDock.com.pocketDock.repository.ReclamationRepository;
import pocketDock.com.pocketDock.repository.OurUserRepo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class ReclamationService implements IReclamationService {
    OurUserRepo ourUserRepo;
    ReclamationRepository reclamationRepository;
    public List<Reclamation> retrieveAllReclamation()
    {
        return reclamationRepository.findAll();
    }
    public Reclamation retrieveReclamation(Long idRec)
    {
        return reclamationRepository.findById(idRec).get();
    }

    public Reclamation addReclamation(Reclamation reclamation)
    {
        return reclamationRepository.save(reclamation);
    }
    public void removeReclamation(Long idRec)
    {
        reclamationRepository.deleteById(idRec);
    }
    public Reclamation modifyReclamation(Reclamation reclamation)
    {
        return reclamationRepository.save(reclamation);
    }
    // Method to calculate the number of reclamations submitted weekly
    @Override
    public Map<LocalDate, Long> calculateNumberOfReclamationsWeekly() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the start date of the current week
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Retrieve all reclamations from the repository
        List<Reclamation> allReclamations = reclamationRepository.findAll();

        // Group reclamations by week and count the number of reclamations in each week
        Map<LocalDate, Long> reclamationsByWeek = allReclamations.stream()
                .collect(Collectors.groupingBy(reclamation ->
                                reclamation.getDateRec().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                        Collectors.counting()));

        // Populate weeks without reclamations with 0 count
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        while (startOfWeek.isBefore(currentDate)) {
            reclamationsByWeek.putIfAbsent(startOfWeek, 0L);
            startOfWeek = startOfWeek.plusWeeks(1);
        }

        return reclamationsByWeek;
    }
    public Reclamation affecterRecAUser(Long idRec, Integer id) {
        Reclamation reclamation = reclamationRepository.findById(idRec)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + idRec));

        OurUsers user = ourUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Assign the user to the reclamation
        reclamation.setUsers(user);

        // Save the updated reclamation
        return reclamationRepository.save(reclamation);
    }





}