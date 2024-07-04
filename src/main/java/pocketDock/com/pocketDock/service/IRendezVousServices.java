package pocketDock.com.pocketDock.service;



import pocketDock.com.pocketDock.entity.DoctorConsultationStats;
import pocketDock.com.pocketDock.entity.OurUsers;
import pocketDock.com.pocketDock.entity.Rendez_Vous;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IRendezVousServices  {

    // public String addRendezVous(Rendez_Vous Rendez_Vous);
    public String addRendezVous(Rendez_Vous rendezVous);
    public Map<DoctorConsultationStats, Long> countConsultationsByDoctor();
    void deleteRendezVous(long rendezVousId);
    public List<Rendez_Vous> getAllRendezVousByPatientId(Long idPatient);

    public Rendez_Vous getRendezVousByid(Long idRendezVous);
    public void sendRemindersForNext24Hours();

    public List<Rendez_Vous> getAllRendezVousByDoctortId(Long idPatient);
    public List<Rendez_Vous> retrieveRDVsForNextHour();

}