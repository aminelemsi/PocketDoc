package pocketDock.com.pocketDock.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pocketDock.com.pocketDock.service.*;
import pocketDock.com.pocketDock.entity.*;
import pocketDock.com.pocketDock.Exception.*;

import java.util.List;

@RestController

@RequestMapping("/docteur")
@RequiredArgsConstructor
public class DoctorController {
    private  final DoctorsServicesImpl doctorsServices;

    @GetMapping(path = "all_doctors")
    public ResponseEntity<?> GetALLUniversity() {
        try {
            List<OurUsers> docteurs =doctorsServices.recupreAllDoctors();

            return ResponseEntity.ok(docteurs);
        } catch (RessourceNotFound exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("une chose mal passé");
        }
    }
}