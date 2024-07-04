package pocketDock.com.pocketDock.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.dto.InscriptionDto;
import pocketDock.com.pocketDock.entity.Event;
import pocketDock.com.pocketDock.entity.Inscription;
import pocketDock.com.pocketDock.entity.OurUsers;
import pocketDock.com.pocketDock.repository.EventRepository;
import pocketDock.com.pocketDock.repository.InscriRepository;
import pocketDock.com.pocketDock.repository.OurUserRepo;
import pocketDock.com.pocketDock.service.Interfaces.InscriService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service

public class InscriServiceImpl implements InscriService {

    private final InscriRepository inscriRepository;
    private final EmailServiceImpl emailService;
    private final EventRepository eventRepository;
    private final OurUserRepo userRepository;
    private final QRCodeGenerator qrCodeGenerator;

    public Integer savewWithMap( MultipartFile imageUrl,InscriptionDto dto) throws Exception {

        Inscription inscription = InscriptionDto.toEntity(dto);

        Event event = eventRepository.findById(dto.getEvent_id()).get();
        OurUsers user = userRepository.findById(dto.getUser_id()).get();

        System.err.println(event.getInscris().size());
        System.err.println(event.getCapacity());
        System.err.println(event.getNbPlaceAvailable());

     /*   boolean isUserAlreadyRegistered = event.getInscris().stream()
                .anyMatch(ins -> ins.getUser().getId().equals(user.getId()));

        if (isUserAlreadyRegistered) {
            throw new RuntimeException("L'utilisateur est déjà inscrit à cet événement.");
        }*/

        if (event.getNbPlaceAvailable() <= 0) {
            throw new RuntimeException("La capacité de l'événement est déjà atteinte.");
        }

        event.setNbPlaceAvailable(event.getNbPlaceAvailable()-1);
        System.err.println("nb place av "+event.getNbPlaceAvailable());

        //inscription.setUser(user);
        //inscription.setEvent(event);

        System.err.println("size avant " + event.getInscris().size());
       // event.getInscris().add(inscription);
        System.err.println("size apres " + event.getInscris().size());

        inscriRepository.save(inscription);
        eventRepository.save(event);

        String qrCodeName = qrCodeGenerator.generateQRCode(inscription, event, user);
        System.out.println(qrCodeName);


        emailService.sendMailWithAttachmentWithMap(
                user.getEmail(),
                user.getName(),
                event.getTitle(),
                qrCodeName,
                imageUrl);


        return inscription.getId();
    }


    @Override
    public Integer save(InscriptionDto dto) throws Exception {

        Inscription inscription = InscriptionDto.toEntity(dto);

        Event event = eventRepository.findById(dto.getEvent_id()).get();
        OurUsers user = userRepository.findById(dto.getUser_id()).get();

   /*     boolean isUserAlreadyRegistered = event.getInscris().stream()
                .anyMatch(ins -> ins.getUser().getId().equals(user.getId()));

        if (isUserAlreadyRegistered) {
            throw new RuntimeException("L'utilisateur est déjà inscrit à cet événement.");
        }*/

        if (event.getNbPlaceAvailable() <= 0) {
            throw new RuntimeException("La capacité de l'événement est déjà atteinte.");
        }

        System.err.println("nb place av "+event.getNbPlaceAvailable());

        event.setNbPlaceAvailable(event.getNbPlaceAvailable()-1);

        inscription.setUser(user);
        inscription.setEvent(event);

        System.err.println("size avant " + event.getInscris().size());
        event.getInscris().add(inscription);
        System.err.println("size apres " + event.getInscris().size());


        inscriRepository.save(inscription);
        eventRepository.save(event);

        String qrCodeName = qrCodeGenerator.generateQRCode(inscription, event, user);
        System.out.println(qrCodeName);


        emailService.sendMailWithAttachment(
                inscription.getUser().getEmail(),
                inscription.getUser().getName(),
                inscription.getEvent().getTitle(),
                qrCodeName
        );

        return inscription.getId();
    }
    @Override
    public Inscription savo(Inscription ins) {

        return inscriRepository.save(ins);

    }


    @Override
    public List<InscriptionDto> findAll() {
        return inscriRepository.findAll()
                .stream()
                .map(InscriptionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public InscriptionDto findById(Integer id) {
        return inscriRepository.findById(id)
                .map(InscriptionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("NO INSCRI FOUND WITH ID " + id));
    }

    public void deleteWithUserId(Integer idEvent,Integer idUser) {


        Event event = eventRepository.findById(idEvent).get();
        List<Inscription> inscriptions = inscriRepository.findAllByEventId(idEvent);
        for (Inscription i : inscriptions){
            if (i.getUser().getId()==idUser){
                event.getInscris().remove(i);

                inscriRepository.deleteById(i.getId());
                event.setNbPlaceAvailable(event.getNbPlaceAvailable() + 1);
                eventRepository.save(event);
            }
        }


    }

    @Override
    public void delete(Integer id) {

        System.err.println(id);

        Event event = eventRepository.findById(id).get();
        Inscription inscription = inscriRepository.findByEventId(id);
        System.err.println(inscription.getId() + inscription.getEvent().getTitle());

        event.getInscris().remove(inscription);

      //  inscriRepository.deleteById(inscription.getId());
        /*Inscription i = inscriRepository.findById(id).get();
        i.getEvent().getInscris().remove(i);*/

        inscriRepository.deleteById(inscription.getId());


        //event.setNbPlaceAvailable(event.getNbPlaceAvailable() + 1);
        eventRepository.save(event);
    }

    @Override
    public List<InscriptionDto> findAllByUserId(Integer UserId) {
        return inscriRepository.findAllByUserId(UserId)
                .stream()
                .map(InscriptionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDto> findAllEventByUserId(Integer UserId) {

        List<Inscription> inscriptions = inscriRepository.findAllByUserId(UserId);
        Set<Event> events = new HashSet<>();
        for (Inscription inscription : inscriptions) {
            events.add(inscription.getEvent());
        }

        return events.stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public List<InscriptionDto> findAllByEventId(Integer EventId) {
        return inscriRepository.findAllByEventId(EventId)
                .stream()
                .map(InscriptionDto::fromEntity)
                .collect(Collectors.toList());
    }
}
