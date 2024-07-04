package pocketDock.com.pocketDock.service;


import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.entity.OurUsers;
import pocketDock.com.pocketDock.entity.Publication;
import pocketDock.com.pocketDock.repository.OurUserRepo;
import pocketDock.com.pocketDock.repository.PubRepository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PubService implements IPubService {
    @Autowired
    ServiceBadworld serviceBadworld;
    PubRepository pubRepository;
    OurUserRepo ourUserRepo;
    static long totalPublications = 0;


    public List<Publication> retrieveAllPublications() {
        List<Publication> publications = pubRepository.findAll();
        return publications;
    }

    public Publication retrievePublication(Long idPub) {
        return pubRepository.findById(idPub).orElse(null);
    }

    public void removePublication(Long idPub) {
        pubRepository.deleteById(idPub);
    }


    //@Scheduled(cron = "*/35 * * * * *") // kool 10s
    //@Scheduled(cron = "0 0 0 * * *") // nos lil
    public void detectbadpublic() throws Exception {
        List<Publication> publications = pubRepository.findAll();
        for (Publication publication : publications) {
            ResponseEntity<String> con = serviceBadworld.filterBadWords1(publication.getContenuPub());
            String responseBody = con.getBody();
            JSONObject jsonObject = new JSONObject(responseBody);
            int badWordsTotal = jsonObject.getInt("bad_words_total");
            if (badWordsTotal != 0) {
                System.out.println("Bad words detected in the comment. Please remove them at id public = " + publication.getIdPub());
            }
            else {
                System.out.println(" No Bad words");
            }
        }
    }

    /*public Publication addPublication(Publication publication) { return pubRepository.save(publication);  }*/

    public ResponseEntity<?> addPublication(Publication publication) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(publication.getContenuPub());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");
        if (badWordsTotal == 0) {
            pubRepository.save(publication);
            return ResponseEntity.ok().body(" publication added ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the publication. Please remove them.");
        }
    }


    /*public Publication modifyPublication(Long id , Publication publication) { return pubRepository.save(publication); }*/

    /*public Publication modifyPublication(Long id, Publication publication) {
        Publication existingPublication = pubRepository.findById(id).orElse(null);
        if (existingPublication != null) {
            // Vérifier si le sujet doit être modifié
            if (publication.getSujet() != null) {
                existingPublication.setSujet(publication.getSujet());
            }
            // Vérifier si le contenu doit être modifié
            if (publication.getContenuPub() != null) {
                existingPublication.setContenuPub(publication.getContenuPub());
            }
            return pubRepository.save(existingPublication);
        }
        return null; // ou lancez une exception appropriée si l'entité n'est pas trouvée
    }*/
    public ResponseEntity<?> modifyPublication(Long id, Publication publication) {
        ResponseEntity<String> con = serviceBadworld.filterBadWords1(publication.getContenuPub());
        String responseBody = con.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        int badWordsTotal = jsonObject.getInt("bad_words_total");
        if (badWordsTotal == 0) {
            Publication newPublication = pubRepository.findById(id).orElse(null);
            newPublication.setSujet(publication.getSujet());
            newPublication.setContenuPub(publication.getContenuPub());
            pubRepository.save(newPublication);
            return ResponseEntity.ok().body(" public added ... ");
        } else {
            return ResponseEntity.badRequest().body("Bad words detected in the public. Please remove them.");
        }
    }

    /*************************************************************************************************/
    public Publication affecterPubAUser(Long idPub, Integer id) {
        Publication p = pubRepository.findById(idPub).get();  //Parent
        OurUsers u = ourUserRepo.findById(id).get(); //Child
        //On affecte le child au parent
        p.setUsers(u);
        return pubRepository.save(p);
    }

    /*************************************************************************************************/
    public List<Publication> getPublicationsOrderedByDate() {
        return pubRepository.findOrderByDate();
    }

    //renvoie une liste de publications ordonnées chronologiquement selon leur date de création
    public List<Publication> getPublicationsOrderedByPopularity() {
        return pubRepository.findOrderByPopularity();
    }

    //renvoie une liste de publications triées par le nombre de "likes", les plus populaires en premier
    public List<Publication> getPublicationsOrderedByNumberOfResponses() {
        return pubRepository.findOrderByNumberOfResponses();
    }
    //renvoie une liste de publications triées par le nombre de réponses,
    // les publications avec le plus grand nombre de réponses apparaissant en premier

    public void likePublication(Long idPub) {
        Publication publication = retrievePublication(idPub); //récupère pub correspondant à l'id idPub et stocker dans var publication
        if (publication != null)  //si pub mawjouda ou non
        {
            publication.setLikes(publication.getLikes() + 1); //incrementi nb likes
            pubRepository.save(publication); //save pub
        }
    }

    /*public void dislikePublication(Long idPub)
    {
        Publication publication = retrievePublication(idPub);
        if (publication != null && publication.getLikes() > 0) //si pub mawjouda ou non + nchouf ken les likes > 0
        {
            publication.setLikes(publication.getLikes() - 1);//decrementiw nb des likes
            pubRepository.save(publication);//save publication
        }
    }*/
    public void dislikePublication(Long idPub) {
        Publication publication = retrievePublication(idPub); //récupère pub correspondant à l'id idPub et stocker dans var publication
        if (publication != null)  //si pub mawjouda ou non
        {
            publication.setDislikes(publication.getDislikes() + 1); //incrementi nb dislikes
            pubRepository.save(publication); //save pub
        }
    }

     /*public Publication ApprouverPublication (long id)
    {
        Publication blogToApprove =pubRepository.findById(id).orElse(null); //récupère pub by id
        blogToApprove.setStatus(true); //modifie son statut
        //sendApprovedEmail(user);
        return pubRepository.save(blogToApprove);  //enrg de nouveau
    }*/

    public List<Publication> getAllApprovedPublications() {
        Boolean status = true;
        List<Publication> approvedForum = pubRepository.findBlogByStatusIs(status);
        return approvedForum;
    }

    public List<Publication> getAllUnapprovedPublications() {
        Boolean status = false;
        List<Publication> approvedForum = pubRepository.findBlogByStatusIs(status);
        return approvedForum;
    }

    /*************************************************************************************************/

   /*public void calculerNombrePubMensuel()
    {
        Month currentMonth = LocalDate.now().getMonth(); //récupériw mois de la date actuelle et stocker dans var

        List<Publication> allPublications = (List<Publication>) pubRepository.findAll(); //récupériw pub lkol
                                                                        // Les publ stockées dans une liste allPublications.
        long publicationsPostedThisMonth =
                allPublications.stream() //transforme liste des pub en un flux pour op filtrage
                .filter(publication -> publication.getDatePub().getMonth() == currentMonth) //nfiltriw publ li 3andhom nafs mois
                .count(); //ncomptiw nb de pub qui ont été postées ce mois et qui ont passé le filtre

        totalPublications += publicationsPostedThisMonth; //nb pub postées ce mois est ajouté au nombre total de pub
    }*/
    /*public long calculerNombrePubMensuel()
    {
        Month currentMonth = LocalDate.now().getMonth();   //récupériw mois de la date actuelle et stocker dans var

        List<Publication> allPublications = pubRepository.findAll();  //récupériw pub lkol
        // Les publ stockées dans une liste allPublications.

        long publicationsPostedThisMonth =
                allPublications.stream()        //transforme liste des pub en un flux pour op filtrage
                        .filter(publication -> publication.getDatePub().getMonth() == currentMonth)  //nfiltriw publ li 3andhom nafs mois
                        .count();         //ncomptiw nb de pub qui ont été postées ce mois et qui ont passé le filtre

        totalPublications += publicationsPostedThisMonth; // Mettre à jour totalPublications nb pub postées ce mois est ajouté au nombre total de pub

        return publicationsPostedThisMonth; // Retourner nb de publ postées ce mois-ci
    }*/
    public Map<Month, Long> calculerNombrePubMensuel() {
        List<Publication> allPublications = pubRepository.findAll();

        Map<Month, Long> publicationsByMonth = allPublications.stream()
                .collect(Collectors.groupingBy(
                        publication -> publication.getDatePub().getMonth(),
                        Collectors.counting()));

        return publicationsByMonth;
    }


    /*public void calculateMostActiveAuthors()
    {
       /* Map<User, Long> authorPublicationCount = getAll().stream()
                .collect(Collectors.groupingBy(Publication::getUser, Collectors.counting()));
        String mostActiveAuthor = String.valueOf(authorPublicationCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null));
        System.out.println("le user qui est tres active : " + mostActiveAuthor);
    }*/

    public List<Publication> searchPublicationsBySujet(String sujet) {
        return pubRepository.findBySujetContainingIgnoreCase(sujet);
    }


}