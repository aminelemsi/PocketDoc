package pocketDock.com.pocketDock.entity;

import jakarta.persistence.*;
import lombok.*;


import java.beans.Transient;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private  Long doctorId;
    private LocalDateTime dateTime;
    private LocalDateTime StartTime;
    private LocalDateTime EndTime;
    private Long AvailabilityId;
    private Status status;
}
