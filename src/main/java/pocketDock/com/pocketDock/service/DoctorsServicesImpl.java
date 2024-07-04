package pocketDock.com.pocketDock.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.entity.*;
import pocketDock.com.pocketDock.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorsServicesImpl implements IDoctorService {
    private final OurUserRepo doctorRepository;

    @Override
    public List<OurUsers> recupreAllDoctors() {
        List<OurUsers> doctors = doctorRepository.findAll();

        List<OurUsers> doctorUsers = doctors.stream()
                .filter(user -> "DOCTOR".equals(user.getRole()))
                .collect(Collectors.toList());

        return doctorUsers;
    }
}
