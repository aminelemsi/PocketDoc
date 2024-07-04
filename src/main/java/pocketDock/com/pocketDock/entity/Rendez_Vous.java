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
public class Rendez_Vous implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name="null";


    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @ManyToOne

    @JoinColumn(name = "patient")
    private OurUsers patient;

    @ManyToOne

    @JoinColumn(name = "doctor")

    private OurUsers doctor;
}
