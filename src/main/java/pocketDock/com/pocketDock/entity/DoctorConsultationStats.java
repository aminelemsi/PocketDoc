package pocketDock.com.pocketDock.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "Rendez_Vous")
public class DoctorConsultationStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String name;
    private String lastname;
    private Long consultationCount;


}