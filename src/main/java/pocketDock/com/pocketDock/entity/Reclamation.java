package pocketDock.com.pocketDock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reclamation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long idRec;
        private String descriptionRec;
        private Date dateRec;
        private String status;
        @Column(nullable = false) // Ensure priority is not nullable
        @ColumnDefault("'Medium'") // Set the default value to 'Medium'
        private String priority;

        @JsonIgnore

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "reclamations")
        private Set<Reponse> reponses;
        @ManyToOne
        private OurUsers users;

}
