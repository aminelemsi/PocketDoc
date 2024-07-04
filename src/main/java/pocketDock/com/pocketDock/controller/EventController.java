package pocketDock.com.pocketDock.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.service.Impl.EmailServiceImpl;
import pocketDock.com.pocketDock.service.Impl.EventServiceImpl;
import pocketDock.com.pocketDock.service.Impl.InscriServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@CrossOrigin("http://localhost:4200")
public class EventController {

    private final EventServiceImpl eventService;
    private final EmailServiceImpl emailService;
    private final InscriServiceImpl inscriService;


    @GetMapping("/")
    public List<EventDto> getEvents() {
        List<EventDto> listEvents = eventService.findAll();
        return listEvents;
    }

    // To Integrate
    @GetMapping("/evNonInsc/{userId}")
    public List<EventDto> findALLNoInscri(@PathVariable Integer userId){
        return eventService.findALLNoInscri(userId);
    }

    /*@PostMapping("/send-email")
    public void sendEmail(@RequestParam("image") MultipartFile imageUrl) throws MalformedURLException, MessagingException, UnsupportedEncodingException {
        emailService.sendEmailWithAttachment2(imageUrl);
    }

    @PostMapping("/send-email2")
    public void sendEmail2(@RequestParam("image") MultipartFile image,
                           @RequestParam("inscription") String inscriptionJson) throws Exception {
        InscriptionDto inscription = new ObjectMapper().readValue(inscriptionJson, InscriptionDto.class);
        inscriService.savewWithMap(image, inscription);
    }*/


/*
    @GetMapping("/statistics")
    public Map<Integer, Long> getEventStatistics() {
        return eventService.getEventStatistics();
    }
*/
    /*
    @GetMapping("/")

        public ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(eventService.retrieveAllEvents());
    }*/


    @GetMapping("/{event-id}")
    public EventDto retrieveEvent(@PathVariable("event-id") Integer evId) {
        EventDto eventDto = eventService.findById(evId);
        return eventDto;
    }

    @PostMapping("/")
    public Integer addevent(@Valid @RequestBody EventDto e) throws IOException {
        return eventService.save1(e);
    }

    @DeleteMapping("/{event-id}")
    public void removeevent(@PathVariable("event-id") Integer evId) {
        eventService.delete(evId);
    }

    @PutMapping("/")
    public Integer modifyevent(@Valid @RequestBody EventDto e) throws IOException {
        return eventService.save1(e);
    }

    @GetMapping("/users/{user-id}")
    public List<EventDto> getEventsByUserId (@PathVariable("user-id") Integer id){
        return eventService.findAllByUserId(id);
    }
}