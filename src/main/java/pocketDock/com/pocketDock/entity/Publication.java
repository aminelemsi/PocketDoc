package pocketDock.com.pocketDock.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity                   //mouhamed
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Publication
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPub;
    private String sujet;
    private String contenuPub;
    private LocalDate datePub;
    int likes;
    int dislikes;
    boolean status ;

    @OneToMany (cascade=CascadeType.ALL, mappedBy = "publication")
    private Set<Commentaire> commentaires;                   //association avec commentaire

    @ManyToOne
    private OurUsers users;              //dans publication association bi avec user



}
