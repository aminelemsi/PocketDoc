package pocketDock.com.pocketDock.service;

import org.springframework.http.ResponseEntity;
import pocketDock.com.pocketDock.entity.Commentaire;
import pocketDock.com.pocketDock.entity.Publication;

import java.util.List;

public interface ICommentService
{
    public List<Commentaire> retrieveAllCommentairesForPublication(Publication publication);
    public List<Commentaire> retrieveAllCommentaires();
    Commentaire retrieveCommentaire(Long idCom);
    void removeCommentaire(Long idCom);
    public ResponseEntity<?> addCommentaire(Commentaire commentaire);
    public ResponseEntity<?>  modifyCommentaire(Commentaire commentaire, long id);
    public Commentaire affecterCommentAPub(Long idCom, Long idPub);
    public Commentaire desaffecterCommentApub(Long idCom);
    public Commentaire affecterCommentAUser(Long idCom, Integer id);

}
