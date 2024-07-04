package pocketDock.com.pocketDock.service;


import pocketDock.com.pocketDock.entity.Reclamation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IReclamationService {
    public List<Reclamation> retrieveAllReclamation();
    public Reclamation retrieveReclamation(Long idRec);
    public Reclamation addReclamation(Reclamation reclamation);
    public void removeReclamation(Long idRec);
    public Reclamation modifyReclamation(Reclamation reclamation);
    public Map<LocalDate, Long> calculateNumberOfReclamationsWeekly() ;
    public Reclamation affecterRecAUser(Long idRec, Integer id) ;

}
