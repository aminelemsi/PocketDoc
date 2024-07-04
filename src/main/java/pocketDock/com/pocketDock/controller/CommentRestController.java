package pocketDock.com.pocketDock.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.entity.Commentaire;
import pocketDock.com.pocketDock.entity.Publication;
import pocketDock.com.pocketDock.service.ICommentService;

import java.lang.String;
import java.util.List;

@RestController
@RequestMapping("/commentaire")
@AllArgsConstructor
public class CommentRestController
{
    ICommentService ComService;


    @GetMapping("/retrieve-all-commentaires")
    public List<Commentaire> getCommentaires()
    {
        List<Commentaire> listCommentaires = ComService.retrieveAllCommentaires();
        return listCommentaires;
    }

    @GetMapping("/get/byPublication/{publicationId}")
    public List<Commentaire> retrieveAllCommentairesForPublication(@PathVariable long publicationId)
    {
        Publication publication = new Publication();
        publication.setIdPub(publicationId);
        return ComService.retrieveAllCommentairesForPublication(publication);
    }

    @GetMapping("/retrieve-commentaire/{commentaire-id}")
    public Commentaire retrieveCommentaire(@PathVariable("commentaire-id") Long idCom)
    {
        Commentaire commentaire = ComService.retrieveCommentaire(idCom);
        return commentaire;
    }

    @DeleteMapping("/remove-commentaire/{commentaire-id}")
    public void removeCommentaire(@PathVariable("commentaire-id") Long idCom)
    {
        ComService.removeCommentaire(idCom);
    }

    /*@PostMapping("/add-commentaire")
    public Commentaire addCommentaire(@RequestBody Commentaire c)
    {
        Commentaire commentaire = ComService.addCommentaire(c);
        return commentaire;
    }*/

    /*@PostMapping("/add-commentaire")
    public ResponseEntity<?> addCommentaire(@RequestBody Commentaire c) {
        try {
            Commentaire commentaire = ComService.addCommentaire(c);
            return ResponseEntity.ok(commentaire);
        } catch (IllegalArgumentException e) {
            // Gérer l'erreur liée aux mots interdits ici, par exemple :
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/

    @PostMapping("/add-commentaire")
    public ResponseEntity<?> addCommentaire(@RequestBody Commentaire c)
    {
        return ComService.addCommentaire(c);
    }

    /*@PutMapping("/modify/{id}")
    public Commentaire modifyCommentaire(@PathVariable long id, @RequestBody Commentaire commentaire)
    {
        return ComService.modifyCommentaire(id , commentaire);
    }*/

    @PutMapping("/modify/{id}")
    public ResponseEntity<?>  modifyCommentaire(@RequestBody Commentaire commentaire, @PathVariable long id)
    {
        return ComService.modifyCommentaire(commentaire,id);
    }


    /*************************************************************************************************/

    @PutMapping("/affecterCommentAPub/{idCom}/{idPub}")
    public Commentaire affecterCommentAPub(@PathVariable Long idCom, @PathVariable Long idPub)
    {
        return ComService.affecterCommentAPub(idCom, idPub);
    }

    @DeleteMapping("/desaffecter-projet-detail/{idCom}")
    public ResponseEntity<?> desaffecterCommentApub(@PathVariable Long idCom)
    {
        Commentaire commentaire = ComService.desaffecterCommentApub(idCom);
        if (commentaire != null)
        {
            return ResponseEntity.ok().body("Commentaire désaffecté de la publication.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/affecterCommentAUser/{idCom}/{id}")
    public Commentaire affecterCommentAUser(@PathVariable Long idCom, @PathVariable Integer id)
    {
        return ComService.affecterCommentAUser(idCom, id);
    }





}
