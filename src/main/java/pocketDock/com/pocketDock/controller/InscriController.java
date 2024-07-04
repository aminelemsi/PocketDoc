package pocketDock.com.pocketDock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.dto.InscriptionDto;
import pocketDock.com.pocketDock.service.Impl.EmailServiceImpl;
import pocketDock.com.pocketDock.service.Impl.InscriServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inscs")
@CrossOrigin("http://localhost:4200")

public class InscriController {
    private final InscriServiceImpl inscriService;
    private final EmailServiceImpl emailService;

    @GetMapping("/")
    public List<InscriptionDto> getEvents() {
        List<InscriptionDto> listInscri = inscriService.findAll();
        return listInscri;
    }

    @GetMapping("/{insc-id}")
    public InscriptionDto retrieveEvent(@PathVariable("insc-id") Integer insId) {
        InscriptionDto inscri = inscriService.findById(insId);
        return inscri;
    }

    @PostMapping("/")
    public Integer addInscri(@RequestBody InscriptionDto i) throws Exception {

            return inscriService.save(i);

    }
    @PostMapping("/WthMap")
    public void addInscriWithMap(@RequestParam("image") MultipartFile image,
                           @RequestParam("inscription") String inscriptionJson) throws Exception {
        InscriptionDto inscription = new ObjectMapper().readValue(inscriptionJson, InscriptionDto.class);
        inscriService.savewWithMap(image, inscription);
    }

    @DeleteMapping("/{insc-id}/{user-id}")
    public void removeInscriWithUserId(@PathVariable("insc-id") Integer inscId, @PathVariable("user-id") Integer userId) {
        inscriService.deleteWithUserId(inscId,userId);
    }


    @DeleteMapping("/{insc-id}")
    public void removeInscri(@PathVariable("insc-id") Integer inscId) {
        inscriService.delete(inscId);
    }

    //@PutMapping("/")
    //public Integer modifyInscri(@RequestBody InscriptionDto i) {
      //  return inscriService.save(i);
    //}

    @GetMapping("/users/{user-id}")
    public List<InscriptionDto> getInscriByUserId (@PathVariable("user-id") Integer id){
        return inscriService.findAllByUserId(id);
    }

    @GetMapping("/events/{event-id}")
    public List<InscriptionDto> getInscriByEventId (@PathVariable("event-id") Integer id){
        return inscriService.findAllByEventId(id);
    }

    @GetMapping("/users/events/{user-id}")
    public List<EventDto> getAllEventByUserId(@PathVariable("user-id") Integer id){
        return inscriService.findAllEventByUserId(id);
    }
}
