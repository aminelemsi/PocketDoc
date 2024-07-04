package pocketDock.com.pocketDock.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.entity.Event;
import pocketDock.com.pocketDock.entity.Inscription;
import pocketDock.com.pocketDock.repository.EventRepository;
import pocketDock.com.pocketDock.repository.InscriRepository;
import pocketDock.com.pocketDock.service.Interfaces.EventService;
import pocketDock.com.pocketDock.service.Interfaces.IServiceMapbox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final InscriRepository inscriRepository;

    @Autowired
    private IServiceMapbox mapbox;
    @Override
    public Integer save1(EventDto dto) throws IOException {
        Event event = EventDto.toEntity(dto);
        event.setNbPlaceAvailable(event.getCapacity());
        event.setLocation( mapbox.getAddressFromCoordinates(event.getY(),event.getX()));
        Event ev= eventRepository.save(event);

        //System.err.println(ev.getUser().getEmail());
        return ev.getId();
    }

    public List<Event> getEventsForTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return eventRepository.findEventsForTomorrow(tomorrow);
    }


    @Override
    public Integer save(EventDto dto) {
        return null;
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findById(Integer id) {

        return eventRepository.findById(id)
                .map(EventDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("NO EVENT FOUND WITH ID " + id));
    }

    @Override
    public void delete(Integer id) {

        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDto> findAllByUserId(Integer userId) {
        return eventRepository.findAllByUserId(userId)
                .stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }

    //To Integrate
    public List<EventDto> findALLNoInscri(Integer userId) {
        // Récupérer tous les événements auxquels l'utilisateur est inscrit
        List<Inscription> inscriptions = inscriRepository.findAllByUserId(userId);

        // Récupérer tous les événements
        List<Event> allEvents = eventRepository.findAll();

        // Créer une liste pour stocker les événements non inscrits
        List<Event> eventsNotInscribed = new ArrayList<>();

        // Parcourir tous les événements
        for (Event event : allEvents) {
            boolean isInscribed = false;

            // Vérifier si l'utilisateur est inscrit à cet événement
            for (Inscription inscription : inscriptions) {
                if (inscription.getEvent().getId().equals(event.getId())) {
                    isInscribed = true;
                    break;
                }
            }

            // Si l'utilisateur n'est pas inscrit à cet événement, l'ajouter à la liste des événements non inscrits
            if (!isInscribed) {
                eventsNotInscribed.add(event);
            }
        }


        return eventsNotInscribed.stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }


/*    public Map<Integer, Long> getEventStatistics() {
        List<Object[]> eventStatistics = eventRepository.getEventStatistics();
        Map<Integer, Long> statisticsMap = new LinkedHashMap<>(); // Utilisez LinkedHashMap pour conserver l'ordre d'insertion

        for (Object[] stat : eventStatistics) {
            Integer eventId = (Integer) stat[0];
            Long inscriptionCount = (Long) stat[1];
            statisticsMap.put(eventId, inscriptionCount);
        }

        return statisticsMap;
    }*/



}
