package pocketDock.com.pocketDock.service.Interfaces;

import pocketDock.com.pocketDock.dto.EventDto;

import java.io.IOException;
import java.util.List;

public interface EventService extends AbstractService<EventDto>{
    List<EventDto> findAllByUserId (Integer userId);
    Integer save1 (EventDto dto) throws IOException;


}
