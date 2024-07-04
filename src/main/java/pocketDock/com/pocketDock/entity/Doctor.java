package pocketDock.com.pocketDock.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Doctor extends OurUsers {

    @Column(name = "specialite")
    private  String specialite;

    @Column(name = "adresse_cabinet")
    private String adresseCabinet;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Rendez_Vous> rendezVous =new HashSet<>();

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    private Set<Disponibilite> disponibilites =new HashSet<>() ;
}
