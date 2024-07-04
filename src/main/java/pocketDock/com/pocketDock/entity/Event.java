package pocketDock.com.pocketDock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Event extends AbstractEntity {

    private String title;
    private String  description;
    private LocalDate date ;
    private String location;
    private Integer capacity;
    private Integer nbPlaceAvailable;
    private Double x;
    private Double y;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OurUsers user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="event")
    @JsonIgnore
    @ToString.Exclude
    private List<Inscription> Inscris;

}
