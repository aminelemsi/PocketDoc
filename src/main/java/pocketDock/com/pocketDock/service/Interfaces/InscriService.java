package pocketDock.com.pocketDock.service.Interfaces;

import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.dto.InscriptionDto;
import pocketDock.com.pocketDock.entity.Inscription;

import java.util.List;

public interface InscriService extends AbstractService<InscriptionDto>{
    List<InscriptionDto> findAllByUserId(Integer UserId);
    List<InscriptionDto> findAllByEventId(Integer EventId);
    public Inscription savo(Inscription ins);
   // public Inscription save2(Integer idUser, Integer idEvent);
    public List<EventDto> findAllEventByUserId(Integer UserId);
}
