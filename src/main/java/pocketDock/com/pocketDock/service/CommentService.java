package pocketDock.com.pocketDock.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.entity.Commentaire;
import pocketDock.com.pocketDock.entity.OurUsers;
import pocketDock.com.pocketDock.entity.Publication;
import pocketDock.com.pocketDock.repository.CommentRepository;
import pocketDock.com.pocketDock.repository.OurUserRepo;
import pocketDock.com.pocketDock.repository.PubRepository;
import reactor.core.publisher.Mono;


import java.util.List;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService {
    CommentRepository commentRepository;
    PubRepository pubRepository;
    OurUserRepo ourUserRepo;
    @Autowired
    ServiceBadworld serviceBadworld;

    public List<Commentaire> retrieveAllCommentaires() {
        return commentRepository.findAll();
    }

    public List<Commentaire> retrieveAllCommentairesForPublication(Publication publication) {
        return commentRepository.findByPublication(publication);
    }

    public Commentaire retrieveCommentaire(Long idCom) {
        return commentRepository.findById(idCom).get();
    }

    public void removeCommentaire(Long idCom) {
        commentRepository.deleteById(idCom);
    }

    /*public Commentaire addCommentaire(Commentaire commentaire) { return commentRepository.save(commentaire); }*/
    public ResponseEntity<?> addCommentaire(Commentaire commentaire) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(commentaire.getContenuCom());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");

        if (badWordsTotal == 0) {
            commentRepository.save(commentaire);
            /*return ResponseEntity.ok().body(" Comment added ... ");*/
            return ResponseEntity.ok().body(commentaire);
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the comment. Please remove them.");
        }
    }

    //@Scheduled(cron = "*/10 * * * * *") // kool 10s
    //@Scheduled(cron = "0 0 0 * * *") // nos lil
    public void detectbadcomment() throws Exception {
        List<Commentaire> commentaires = commentRepository.findAll();
        for (Commentaire commentaire : commentaires) {
            ResponseEntity<String> con = serviceBadworld.filterBadWords1(commentaire.getContenuCom());
            String responseBody = con.getBody();
            JSONObject jsonObject = new JSONObject(responseBody);
            int badWordsTotal = jsonObject.getInt("bad_words_total");
            if (badWordsTotal != 0) {
                System.out.println("Bad words detected in the comment. Please remove them at idcomment = " + commentaire.getIdCom());
            }
            else {
                System.out.println(" No Bad words");
            }
        }
    }

    /*public Commentaire modifyCommentaire(Commentaire commentaire) { return commentRepository.save(commentaire); }*/
    /*public Commentaire modifyCommentaire(Long id, Commentaire commentaire)
    {
        Commentaire existingCommentaire = commentRepository.findById(id).orElse(null);
        if (existingCommentaire != null) {
            // Vérifier si le contenu doit être modifié
            if (commentaire.getContenuCom() != null) {
                existingCommentaire.setContenuCom(commentaire.getContenuCom());
            }
            if (commentaire.getDateCom() != null) {
                existingCommentaire.setDateCom(commentaire.getDateCom());
            }
            return commentRepository.save(existingCommentaire);
        }
        return null; // ou lancez une exception appropriée si l'entité n'est pas trouvée
    }*/
    public ResponseEntity<?> modifyCommentaire(Commentaire commentaire, long id) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(commentaire.getContenuCom());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");
        if (badWordsTotal == 0) {
            Commentaire commentaire1 = commentRepository.findById(id).orElse(null);
            commentaire1.setContenuCom(commentaire.getContenuCom());
            commentaire1.setDateCom(commentaire.getDateCom());
            commentRepository.save(commentaire1);
            /*return ResponseEntity.ok().body(" Comment updated ... ");*/
            return ResponseEntity.ok().body(commentaire1);
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the comment. Please remove them.");
        }
    }

    /*************************************************************************************************/
    public Commentaire affecterCommentAPub(Long idCom, Long idPub) {
        Commentaire c = commentRepository.findById(idCom).get();  //Parent
        Publication p = pubRepository.findById(idPub).get(); //Child
        //On affecte le child au parent
        c.setPublication(p);
        return commentRepository.save(c);
    }

    public Commentaire desaffecterCommentApub(Long idCom) {
        Commentaire commentaire = commentRepository.findById(idCom).orElse(null);
        if (commentaire != null) {
            commentaire.setPublication(null); // Utilisation de l'instance de Commentaire pour appeler setPublication
            return commentRepository.save(commentaire);
        } else {
            throw new EntityNotFoundException("Commentaire not found with the given ID");
        }
    }

    public Commentaire affecterCommentAUser(Long idCom, Integer id) {
        Commentaire c = commentRepository.findById(idCom).get();  //Parent
        OurUsers u = ourUserRepo.findById(id).get(); //Child
        //On affecte le child au parent
        c.setUsers(u);
        return commentRepository.save(c);
    }
}



