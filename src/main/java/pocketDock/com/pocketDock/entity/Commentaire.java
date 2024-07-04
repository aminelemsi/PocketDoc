package pocketDock.com.pocketDock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commentaire
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCom;
    private String contenuCom;
    private Date dateCom;

    @JsonIgnore
    @ManyToOne
    Publication publication;     //association avc commentaire

    @ManyToOne
    private OurUsers users;                                               //dans commentaire association bi


}
