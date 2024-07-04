package pocketDock.com.pocketDock.service;

import com.sun.jdi.LongValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pocketDock.com.pocketDock.entity.*;
import pocketDock.com.pocketDock.repository.*;
import pocketDock.com.pocketDock.Exception.*;

import javax.print.Doc;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IRendezVousServicesImpl implements IRendezVousServices{
    private final Rendez_VousRepository rendezVousRepository;

    private final DoctorRepository doctorRepository;
    private  final DisponibiliteRepository disponibilityRepository;
    private final OurUserRepo userRepository;
    private final EmailService emailService;



    @Override

    @Transactional

    public String addRendezVous(Rendez_Vous rendezVous) {
        // Vérifier si le patient du rendez-vous est null
        if (rendezVous.getPatient() == null) {
            return "Le patient du rendez-vous est null. Veuillez sélectionner un patient.";
        }

        // Rechercher le patient
        OurUsers patient = userRepository.findByIdAndRole(rendezVous.getPatient().getId(), "USER");

        // Vérifier si le patient a été trouvé
        if (patient == null) {
            return "Patient introuvable. Veuillez entrer les bonnes coordonnées.";
        }

        // Rechercher le médecin
        OurUsers doctor = userRepository.findByIdAndRole(rendezVous.getDoctor().getId(), "DOCTOR");

        // Vérifier si le médecin a été trouvé
        if (doctor == null) {
            return "Médecin introuvable. Veuillez entrer les bonnes coordonnées.";
        }

        // Créer le rendez-vous
        rendezVous.setPatient(patient);
        rendezVous.setDoctor(doctor);

        // Enregistrer le rendez-vous
        Rendez_Vous savedRendezVous = rendezVousRepository.save(rendezVous);

        // Envoi de l'email de confirmation au patient
        String recipientEmail = patient.getEmail();
        String subject = "Confirmation de rendez-vous";
        String messageBody = "Votre rendez-vous a été fixé avec succès.";
        emailService.sendSimpleMailMessage(
                recipientEmail,
                subject,
                messageBody
        );

        // Créer une disponibilité avec statut "RESERVED" pour le médecin

        // Vérifier si le médecin est disponible
        boolean isDisponible = doctor.getDisponibilites().isEmpty() ||
                doctor.getDisponibilites().stream()
                        .noneMatch(disponibilite -> {
                            LocalDateTime rendezVousStartTime = rendezVous.getDate();
                            LocalDateTime rendezVousEndTime = rendezVousStartTime.plusMinutes(30); // Ajouter 30 minutes au rendez-vous

                            LocalDateTime disponibiliteStartTime = disponibilite.getStartTime();
                            LocalDateTime disponibiliteEndTime = disponibilite.getEndTime();

                            // Vérifier si le rendez-vous chevauche une disponibilité du médecin avec statut "RESERVED"
                            return disponibilite.getStatus() == Status.RESERVED &&
                                    ((rendezVousStartTime.isBefore(disponibiliteEndTime) || rendezVousStartTime.equals(disponibiliteEndTime)) &&
                                            (rendezVousEndTime.isAfter(disponibiliteStartTime) || rendezVousEndTime.equals(disponibiliteStartTime)));
                        });

        if (!isDisponible) {
            throw new DoctorNotAvailableException("Le médecin n'est pas disponible à la date spécifiée. Veuillez choisir une autre date.");
        }
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setDoctor(doctor);
        disponibilite.setStatus(Status.RESERVED);
        disponibilite.setStartTime(rendezVous.getDate().plusHours(2));;
        disponibilite.setEndTime(rendezVous.getDate().plusHours(2).plusMinutes(30)); // Ajouter 30 minutes à la disponibilité
        rendezVous.setDate(rendezVous.getDate().plusHours(2));

        // Enregistrer la disponibilité
        disponibilityRepository.save(disponibilite);

        return "Rendez-vous ajouté avec succès.";
    }

    @Override
    public void deleteRendezVous(long rendezVousId) {
        // Rechercher le rendez-vous dans la base de données
        Rendez_Vous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RessourceNotFound("Aucun rendez-vous avec l'id :" + rendezVousId));

        // Récupérer le patient associé au rendez-vous
        OurUsers patient = rendezVous.getPatient();

        // Récupérer le médecin associé au rendez-vous
        OurUsers doctor = rendezVous.getDoctor();

        // Rechercher la disponibilité associée au rendez-vous
        Disponibilite disponibilite = doctor.getDisponibilites().stream()
                .filter(d -> d.getStartTime().isEqual(rendezVous.getDate()))
                .findFirst()
                .orElseThrow(() -> new RessourceNotFound("Aucune disponibilité associée à ce rendez-vous"));

        // Supprimer la disponibilité de la liste de disponibilités du médecin
        doctor.getDisponibilites().remove(disponibilite);

        // Supprimer la disponibilité de la base de données
        disponibilityRepository.delete(disponibilite);

        // Retirer le rendez-vous des listes de rendez-vous du patient et du médecin
        doctor.getRendezVous().remove(rendezVous);
        patient.getRendezVous().remove(rendezVous);

        // Enregistrer les modifications dans la base de données
        userRepository.save(patient);
        userRepository.save(doctor);

        // Supprimer le rendez-vous de la base de données
        rendezVousRepository.delete(rendezVous);
    }

    @Override
    public List<Rendez_Vous> getAllRendezVousByPatientId(Long idPatient) {
        List<Rendez_Vous>listeRendezVous=rendezVousRepository.findByPatientId(idPatient);

        return listeRendezVous;
    }
    @Override
    public List<Rendez_Vous> getAllRendezVousByDoctortId(Long idDoctor) {
        List<Rendez_Vous>listeRendezVous=rendezVousRepository.findByDoctorId(idDoctor);

        return listeRendezVous;
    }

    @Override
    public Rendez_Vous getRendezVousByid(Long idRendezVous) {
        Rendez_Vous rendezVous=rendezVousRepository.findById(idRendezVous).orElseThrow(()->new RessourceNotFound("Accun rendez Vous avec cet id : "+idRendezVous));
        return rendezVous;
    }
    public Map<Month, Long> countConsultationsByMonth() {
        List<Object[]> consultations = rendezVousRepository.countConsultationsByMonth();
        Map<Month, Long> consultationsByMonth = new HashMap<>();
        for (Object[] row : consultations) {
            Integer monthInt = (Integer) row[0];
            Month month = Month.of(monthInt);
            Long count = (Long) row[1];
            consultationsByMonth.put(month, count);
        }
        return consultationsByMonth;
    }
    public Map<DoctorConsultationStats, Long> countConsultationsByDoctor() {
        List<Object[]> consultations = rendezVousRepository.countConsultationsByDoctor();
        Map<DoctorConsultationStats, Long> consultationsByDoctor = new HashMap<>();
        for (Object[] row : consultations) {
            OurUsers doctor = (OurUsers) row[0];
            Long count = (Long) row[1];

            DoctorConsultationStats stats = new DoctorConsultationStats();
            stats.setName(doctor.getName());
            stats.setLastname(doctor.getLastname());
            stats.setConsultationCount(count);

            consultationsByDoctor.put(stats, count);
        }
        return consultationsByDoctor;
    }
    @Scheduled(fixedRate = 3600000) // Toutes les heures
    public void scheduleTask() {
        this.sendRemindersForNext24Hours();
    }
    public List<Rendez_Vous> retrieveRDVsForNextHour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.plusHours(1);
        return rendezVousRepository.findRDVsForNextHour(now, nextHour);
    }
    public void sendRemindersForNext24Hours() {
        List<Rendez_Vous> rdvs = this.retrieveRDVsForNextHour() ;
        for (Rendez_Vous rdv : rdvs) {
            String patientEmail = rdv.getPatient().getEmail(); // Supposons que l'e-mail du patient soit stocké dans l'entité RDV
            String subject = "Rappel de rendez-vous";
            String body = "Votre rendez-vous est prévu dans moins de 24 heures. Veuillez vous préparer.";
            emailService.sendSimpleMailMessage(patientEmail, subject, body);
        }
    }
}
