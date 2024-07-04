package pocketDock.com.pocketDock.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.entity.Reponse;
import pocketDock.com.pocketDock.service.IReponseService;

import java.util.List;

@RestController
@RequestMapping("reponse")
@AllArgsConstructor
public class ReponseController {
    IReponseService reponseService;

    @GetMapping("/retrieve-all-reponse")
    public List<Reponse> getReponse() {
        List<Reponse> listReponse = reponseService.retrieveAllReponse();
        return listReponse;
    }

    @PostMapping("/add-reponse")
    public Reponse addReponse(@RequestBody Reponse r) {
        Reponse reponse = reponseService.addReponse(r);
        return reponse;
    }

    @GetMapping("/retrieve-reponse/{reponse-id}")
    public Reponse retrieveReponse(@PathVariable("reponse-id") Long idRep) {
        Reponse reponse = reponseService.retrieveReponse(idRep);
        return reponse;
    }

    @DeleteMapping("/remove-reponse/{reponse-id}")
    public void removeReponse(@PathVariable("reponse-id") Long idRep) {
        reponseService.removeReponse(idRep);
    }

    @PutMapping("/modify-reponse")
    public Reponse modifyReponse(@RequestBody Reponse r) {
        Reponse reponse = reponseService.modifyReponse(r);
        return reponse;
    }

    @PutMapping("/affecterRepARec/{idRep}/{idRec}")
    public Reponse affecterRepARec(@PathVariable Long idRep, @PathVariable Long idRec)
    {
        return reponseService.affecterRepARec(idRep, idRec);
    }


    @PutMapping("/affecterRepAUser/{reponse-id}/{user-id}")
    public Reponse affecterRepAUser(@PathVariable("reponse-id") Long idRep, @PathVariable Integer id) {
        Reponse reponse = reponseService.affecterRepAUser(idRep, id);
        return reponse;
    }
    @GetMapping("/{reclamation-id}/responses")
    public List<Reponse> getResponsesForReclamation(@PathVariable("reclamation-id") Long reclamationId) {
        List<Reponse> responses = reponseService.getResponsesForReclamation(reclamationId);
        return responses;
    }

}
