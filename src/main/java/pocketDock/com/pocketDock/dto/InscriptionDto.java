package pocketDock.com.pocketDock.dto;

import lombok.*;
import pocketDock.com.pocketDock.entity.Event;
import pocketDock.com.pocketDock.entity.Inscription;
import pocketDock.com.pocketDock.entity.OurUsers;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InscriptionDto {

    private Integer id;
    private Integer event_id;
    private Integer user_id;

//    private EventDto event;
//    private UserDto user;

    public static InscriptionDto fromEntity (Inscription inscri){
        //System.err.println(inscri.getUser().getId());
        return InscriptionDto.builder()
                .id(inscri.getId())
                .event_id(inscri.getEvent().getId())
                .user_id(inscri.getUser().getId())
                .build();
    }

    public static Inscription toEntity (InscriptionDto inscri){
        //System.err.println(inscri.getUser().getId());
        return Inscription.builder()
                .id(inscri.getId())
                .user(OurUsers.builder()
                        .id(inscri.getUser_id())
                        .build())
                .event( Event.builder()
                        .id(inscri.getEvent_id())
                        .build())
                .build();
    }

/*
    public static InscriptionDto fromEntity (Inscription inscription){
        return InscriptionDto.builder()
                .id(inscription.getId())
                .user(UserDto.fromEntity(inscription.getUser()))
                .event(EventDto.fromEntity(inscription.getEvent()))
                .build();
    }

    public static Inscription toEntity (InscriptionDto inscription){
        return Inscription.builder()
                .id(inscription.getId())
                .user(UserDto.toEntity(inscription.getUser()))
                .event(EventDto.toEntity(inscription.getEvent()))
                .build();

    }
*/
}
