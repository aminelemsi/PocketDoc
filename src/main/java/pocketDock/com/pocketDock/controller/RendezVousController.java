package pocketDock.com.pocketDock.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.service.*;
import pocketDock.com.pocketDock.entity.*;
import pocketDock.com.pocketDock.Exception.*;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/rendezVous")
@RequiredArgsConstructor
public class RendezVousController {
    UserServiceImpl userService;
    private final IRendezVousServicesImpl rendezVousServices;

  /*  @PostMapping(path = "/add")
    public ResponseEntity<?> ajouterUnRendezVous(@RequestBody Rendez_Vous rendezVous){
        try{
            String rendeVous=rendezVousServices.addRendezVous(rendezVous);
            return new ResponseEntity<>(rendeVous,HttpStatus.CREATED);
        }catch (DoctorNotAvailableException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RessourceNotFound exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }

    }*/

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> SupprimerRendeVous(@PathVariable("id") long idRdv) {
        try {
            rendezVousServices.deleteRendezVous(idRdv);
            return ResponseEntity.ok("rendez vous deleted avec succé");
        } catch (RessourceNotFound exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("rendezvous n'existe pas  avec id  " + idRdv);
        }

    }
    @PostMapping("/add-rendezvous")
    public ResponseEntity<?> addRendezVous(@RequestBody Rendez_Vous rendezVous) {
        try {
            String response = rendezVousServices.addRendezVous(rendezVous);
            return ResponseEntity.ok().body("{\"message\": \"" + response + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Erreur lors de l'ajout du rendez-vous : " + e.getMessage() + "\"}");
        }
    }


    @GetMapping("/countByDoctor")
    public Map<String, Long> countConsultationsByDoctor() {
        Map<DoctorConsultationStats, Long> consultationsByDoctor = rendezVousServices.countConsultationsByDoctor();

        // Convertir la map en utilisant le nom et le prénom du médecin comme clé
        return consultationsByDoctor.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName() + " " + entry.getKey().getLastname(), Map.Entry::getValue));
    }
    @GetMapping("/docteur")
    public List<OurUsers> searchDoctors(
            @RequestParam(required = false) String Country,
            @RequestParam(required = false) String City) {
        return userService.searchDoctorsByCountryOrCity(Country, City);
    }
    @GetMapping("/next-hour")
    public List<Rendez_Vous> getRDVsForNextHour() {
        return rendezVousServices.retrieveRDVsForNextHour();
    }
    @GetMapping(path = "/all/rdvDoctor/{idDoctor}")
    public  ResponseEntity<?>GetAllRendeVousByDoctorId(@PathVariable("idDoctor")long idDoctor){
        try{
            List<Rendez_Vous>rendezVous=rendezVousServices.getAllRendezVousByDoctortId(idDoctor);
            return ResponseEntity.ok(rendezVous);
        }
        catch (RessourceNotFound exception){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
    @GetMapping(path = "/all/rendez/vous/{idPatient}")
    public  ResponseEntity<?>GetAllRendeVousByPatientId(@PathVariable("idPatient")long idPatient){
        try{
            List<Rendez_Vous>rendezVous=rendezVousServices.getAllRendezVousByPatientId(idPatient);
            return ResponseEntity.ok(rendezVous);
        }
        catch (RessourceNotFound exception){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }


    @GetMapping(path = "/details/{id_rendez_vous}")
    public ResponseEntity<?> getRendezVousById(@PathVariable("id_rendez_vous")Long idRendezVous){
        try {
            return ResponseEntity.ok(rendezVousServices.getRendezVousByid(idRendezVous));
        }catch (RessourceNotFound exp){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exp.getMessage());
        }
    }
    @GetMapping("/consultationsByMonth")
    public Map<Month, Long> getConsultationsByMonth() {
        return rendezVousServices.countConsultationsByMonth();
    }


}