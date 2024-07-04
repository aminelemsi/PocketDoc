package pocketDock.com.pocketDock.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.entity.Publication;
import pocketDock.com.pocketDock.service.IPubService;


import java.time.Month;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/publication")
@AllArgsConstructor
public class PubRestController {
    IPubService PubliService;

    @GetMapping("/retrieve-all-publications")
    public List<Publication> getPublications()
    {
        List<Publication> listPublications = PubliService.retrieveAllPublications();
        return listPublications;
    }

    @GetMapping("/retrieve-publication/{publication-id}")
    public Publication retrievePublication(@PathVariable("publication-id") Long idPub)
    {
        Publication publication = PubliService.retrievePublication(idPub);
        return publication;
    }


    @DeleteMapping("/remove-publication/{publication-id}")
    public void removePublication(@PathVariable("publication-id") Long idPub)
    {
        PubliService.removePublication(idPub);
    }


    /*@PostMapping("/add-publication")
    public Publication addPublication(@RequestBody Publication p)
    {
        Publication publication = PubliService.addPublication(p);
        return publication;
    }*/

    /*@PostMapping("/add-publication")
    public ResponseEntity<?> addPublication(@RequestBody Publication p) {
        try {
            Publication publication = PubliService.addPublication(p);
            return ResponseEntity.ok(publication);
        } catch (IllegalArgumentException e) {
            // Gérer l'erreur liée aux mots interdits ici, par exemple :
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/

    @PostMapping("/add-publication")
    public ResponseEntity<?> addPublication(@RequestBody Publication p)
    {
        return PubliService.addPublication(p);
    }

    /*@PutMapping("/publications/{id}")
    public ResponseEntity<Publication> modifyPublication(@PathVariable("id") Long id, @RequestBody Publication newPublication) {
        Publication modifiedPublication = PubliService.modifyPublication(id, newPublication);
        return ResponseEntity.ok(modifiedPublication);
    }*/
    /*@PutMapping("/publications/{id}")
    public Publication modifyPublication(@PathVariable long id, @RequestBody Publication publication)
    {
        return PubliService.modifyPublication(id , publication);
    }*/
    @PutMapping("/publications/{id}")
    public ResponseEntity<?> modifyPublication(@PathVariable long id, @RequestBody Publication publication)
    {
        return PubliService.modifyPublication(id , publication);
    }

    @PutMapping("/affecterPubAUser/{idPub}/{id}")
    public Publication affecterPubAUser(@PathVariable Long idPub, @PathVariable Integer id)
    {
        return PubliService.affecterPubAUser(idPub, id);
    }

    /* ************************************************************************************ */

    /*@GetMapping("/orderByDate")
    public ResponseEntity<List<Publication>> getPublicationsOrderedByDate()
    {
        return ResponseEntity.ok(PubliService.getPublicationsOrderedByDate());
    }*/
    @GetMapping("/orderByDate")
    public ResponseEntity<List<Publication>> getPublicationsOrderedByDate()
    {
        List<Publication> publications = PubliService.getPublicationsOrderedByDate();
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/orderByPopularity")
    public ResponseEntity<List<Publication>> getPublicationsOrderedByPopularity() {
        return ResponseEntity.ok(PubliService.getPublicationsOrderedByPopularity());
    }

    @GetMapping("/orderByNumberOfResponses")
    public ResponseEntity<List<Publication>> getPublicationsOrderedByNumberOfResponses() {
        return ResponseEntity.ok(PubliService.getPublicationsOrderedByNumberOfResponses());
    }

    @PostMapping("/like/{idPub}")
    public void likePublication(@PathVariable Long idPub)
    {
        PubliService.likePublication(idPub);
    }

   /* @PostMapping("/dislike/{idPub}")
    public void dislikePublication(@PathVariable Long idPub)
    {
        PubliService.dislikePublication(idPub);
    }*/

    @PostMapping("/dislike/{idPub}")
    public void dislikePublication(@PathVariable Long idPub)
    {
        PubliService.dislikePublication(idPub);
    }

    /*@PutMapping("/approuver/{id}")
    public Publication ApprouverPublication(@PathVariable long id)
    {
        return PubliService.ApprouverPublication(id);
    }*/

    @GetMapping("/get/allapproved")
    public List<Publication> getAllApprovedPublications(){ return PubliService.getAllApprovedPublications();}

    @GetMapping("/get/allunapproved")
    public List<Publication> getAllUnApprovedPublications(){ return PubliService.getAllUnapprovedPublications();}

    /****************************************************************************************************/

    /*@GetMapping("/calculer-nombre-pub-mensuel")
    public void calculerNombrePubMensuel()
    {
        PubliService.calculerNombrePubMensuel();
    }*/

    /*@GetMapping("/calculer-nombre-pub-mensuel")
    public ResponseEntity<Long> calculerNombrePubMensuel() {
        long nombrePubMensuel = PubliService.calculerNombrePubMensuel();
        return ResponseEntity.ok(nombrePubMensuel);
    }*/


    @GetMapping("/publications-par-mois")
    public Map<Month, Long> getPublicationsParMois()
    {
        return PubliService.calculerNombrePubMensuel();
    }


    @GetMapping("/search")
    public List<Publication> searchPublicationsBySujet(@RequestParam String sujet) {
        return PubliService.searchPublicationsBySujet(sujet);
    }

}
