package pocketDock.com.pocketDock.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class EventStatDto {
    private Integer Id;
    private String Title;
    private Long nbInscris;

    // Constructor
    public EventStatDto(Integer id, String title, Long nbInscris) {
        this.Id = id;
        this.Title = title;
        this.nbInscris = nbInscris;
    }
}
