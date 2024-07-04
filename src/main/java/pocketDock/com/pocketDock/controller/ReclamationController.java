package pocketDock.com.pocketDock.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.entity.Reclamation;
import pocketDock.com.pocketDock.service.IReclamationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reclamation")
@AllArgsConstructor
public class ReclamationController {
    IReclamationService RecService;


    @GetMapping("/retrieve-all-reclamation")
    public List<Reclamation> getReclamation() {
        List<Reclamation> listReclamation = RecService.retrieveAllReclamation();
        return listReclamation;
    }

    @PostMapping("/add-reclamation")
    public Reclamation addReclamation(@RequestBody Reclamation r) {
        Reclamation reclamation = RecService.addReclamation(r);
        return reclamation;
    }

    @GetMapping("/retrieve-reclamation/{reclamation-id}")
    public Reclamation retrieveReclamation(@PathVariable("reclamation-id") Long idRec) {
        Reclamation reclamation = RecService.retrieveReclamation(idRec);
        return reclamation;
    }

    @DeleteMapping("/remove-reclamation/{reclamation-id}")
    public void removeReclamation(@PathVariable("reclamation-id") Long idRec) {
        RecService.removeReclamation(idRec);
    }

    // http://localhost:8089/PI/commentaire/modify-commentaire
    @PutMapping("/modify-reclamation")
    public Reclamation modifyReclamation(@RequestBody Reclamation c) {
        Reclamation reclamation = RecService.modifyReclamation(c);
        return reclamation;
    }


    @GetMapping("/calculate-weekly-reclamations")
    public Map<LocalDate, Long> calculateNumberOfReclamationsWeekly() {
        return RecService.calculateNumberOfReclamationsWeekly();
    }
    @PutMapping("/affecterRecAUser/{reclamation-id}/{user-id}")
    public Reclamation affecterRecAUser(@PathVariable("reclamation-id") Long idRec, @PathVariable("user-id") Integer id) {
        Reclamation reclamation = RecService.affecterRecAUser(idRec, id);
        return reclamation;
    }

}

