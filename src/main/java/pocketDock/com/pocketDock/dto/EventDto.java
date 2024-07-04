package pocketDock.com.pocketDock.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pocketDock.com.pocketDock.entity.Event;
import pocketDock.com.pocketDock.entity.OurUsers;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class EventDto {

    private Integer id;

    @NotNull(message = "Le title ne doit pas etre vide")
    @NotEmpty(message = "Le title ne doit pas etre vide")
    @NotBlank(message = "Le title ne doit pas etre vide")
    private String title;

    @NotNull(message = "Le description ne doit pas etre vide")
    @NotEmpty(message = "Le description ne doit pas etre vide")
    @NotBlank(message = "Le description ne doit pas etre vide")
    private String  description;

    @Future(message = "Le date doit etre pour futur")
    @NotNull(message = "Le date ne doit pas etre vide")
    private LocalDate date ;

    private String location;

    @NotNull(message = "Le capacity ne doit pas etre vide")
    @Positive(message = "La capacity doit positive")
    private Integer capacity;

    private Integer nbPlaceAvailable;
    private Integer user_id;

    @NotNull(message = "il faut specifier l'emplacement")
    private Double x;

    @NotNull(message = "il faut specifier l'emplacement")
    private Double y;


    public static EventDto fromEntity (Event event){
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .location(event.getLocation())
                .nbPlaceAvailable(event.getNbPlaceAvailable())
                .capacity(event.getCapacity())
                .x(event.getX())
                .y(event.getY())
                .user_id(event.getUser().getId())
                .build();
    }
    public static Event toEntity (EventDto event){
        return Event.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .location(event.getLocation())
                .capacity(event.getCapacity())
                .nbPlaceAvailable(event.getNbPlaceAvailable())
                .x(event.getX())
                .y(event.getY())
                .user(   OurUsers.builder()
                        .id(event.getUser_id())
                        .build())
                .build();
    }
}
