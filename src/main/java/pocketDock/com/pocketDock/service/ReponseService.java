    package pocketDock.com.pocketDock.service;

    import lombok.AllArgsConstructor;
    import org.springframework.stereotype.Service;
    import pocketDock.com.pocketDock.entity.OurUsers;
    import pocketDock.com.pocketDock.entity.Reclamation;
    import pocketDock.com.pocketDock.entity.Reponse;
    import pocketDock.com.pocketDock.repository.OurUserRepo;
    import pocketDock.com.pocketDock.repository.ReclamationRepository;
    import pocketDock.com.pocketDock.repository.ReponseRepository;

    import java.util.List;

    @Service
    @AllArgsConstructor
    public class ReponseService implements IReponseService  {
        private ReponseRepository reponseRepository;

        ReclamationRepository reclamationRepository;
        OurUserRepo ourusersRepository;
        public List<Reponse> retrieveAllReponse() {
            return reponseRepository.findAll();
        }

        public Reponse retrieveReponse(Long idRep) {
            return reponseRepository.findById(idRep).get();
        }

        public Reponse addReponse(Reponse reponse) {
            return reponseRepository.save(reponse);
        }

        public void removeReponse(Long idRep) {
            reponseRepository.deleteById(idRep);
        }

        public Reponse modifyReponse(Reponse reponse) {
            return reponseRepository.save(reponse);
        }


        public Reponse affecterRepARec (Long idRep, Long idRec)
        {
            Reponse rep = reponseRepository.findById(idRep).get();  //Parent
            Reclamation r = reclamationRepository.findById(idRec).get(); //Child
            //On affecte le child au parent
            rep.setReclamations(r);
            return reponseRepository.save(rep);
        }

        public Reponse affecterRepAUser(Long idRep, Integer id)
        {
            Reponse rep = reponseRepository.findById(idRep).get();  //Parent
            OurUsers u = ourusersRepository.findById(id).get(); //Child
            //On affecte le child au parent
            rep.setUsers(u);
            return reponseRepository.save(rep);
        }
        public List<Reponse> getResponsesForReclamation(Long reclamationId) {
            return reponseRepository.findByReclamationId(reclamationId);
        }

    }
