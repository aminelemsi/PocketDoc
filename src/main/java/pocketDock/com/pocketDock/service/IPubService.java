package pocketDock.com.pocketDock.service;

import org.springframework.http.ResponseEntity;
import pocketDock.com.pocketDock.entity.Publication;

import java.time.Month;
import java.util.List;
import java.util.Map;

public interface IPubService {
    List<Publication> retrieveAllPublications();
    Publication retrievePublication(Long idPub);
    void removePublication(Long idPub);
    public ResponseEntity<?> addPublication(Publication publication);
    ResponseEntity<?> modifyPublication(Long id, Publication publication);
    public Publication affecterPubAUser(Long idPub, Integer id);

    public List<Publication> getPublicationsOrderedByDate();
    public List<Publication> getPublicationsOrderedByPopularity();
    public List<Publication> getPublicationsOrderedByNumberOfResponses();
    public void likePublication(Long idPub);
    public void dislikePublication(Long idPub);
    /*public Publication ApprouverPublication (long id);*/
    List<Publication> getAllApprovedPublications();
    List<Publication> getAllUnapprovedPublications();

    /*public long calculerNombrePubMensuel();*/

    public Map<Month, Long> calculerNombrePubMensuel();

    /*public void calculateMostActiveAuthors();*/

    public List<Publication> searchPublicationsBySujet(String sujet);

}
