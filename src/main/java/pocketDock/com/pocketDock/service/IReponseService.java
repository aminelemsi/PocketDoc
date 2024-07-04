package pocketDock.com.pocketDock.service;


import pocketDock.com.pocketDock.entity.Reponse;

import java.util.List;

public interface IReponseService {
    List<Reponse> retrieveAllReponse();
    Reponse retrieveReponse(Long idRep);
    Reponse addReponse(Reponse reponse);
    void removeReponse(Long idRep);
    Reponse modifyReponse(Reponse reponse);
    public Reponse affecterRepARec (Long idRep, Long idRec);
    public Reponse affecterRepAUser(Long idRep, Integer id);
    public List<Reponse> getResponsesForReclamation(Long reclamationId);

    }
