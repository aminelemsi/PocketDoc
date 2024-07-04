package pocketDock.com.pocketDock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.sql.Blob;
import java.util.*;
import java.util.concurrent.BlockingDeque;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name="ourusers")
public class OurUsers implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastname;
    private String City;
    private String Country;
    private String rue;
    private String codePostal;
    private Date datenaissance ;
    private String aboutme;
    private String email;
    private String password;
    private Integer telephone;
    private String role;
    private Integer status;
    private String profileImageUrl;
    private String profileImageUrl1;
    private String resetToken;



    @OneToMany(cascade = CascadeType.ALL)
    private Set<Publication> publications;

    @OneToMany (mappedBy="users", cascade=CascadeType.ALL)
    private Set<Commentaire> commentaires;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Publication> reclamation;


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Disponibilite> disponibilites = new HashSet<>();
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Rendez_Vous> rendezVous = new HashSet<>();

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }



    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "etudiant")
    private Set<Fiche> fichesEtu;
    @JsonIgnore
    @OneToMany(mappedBy = "psy")
    private Set<Fiche> psyFiches;
    @JsonIgnore
    @OneToOne
    private Convention convention;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="doc")
    private Set<FileEntity> demandes;


    public static OurUsers fromJsonString(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, OurUsers.class);
    }

}


