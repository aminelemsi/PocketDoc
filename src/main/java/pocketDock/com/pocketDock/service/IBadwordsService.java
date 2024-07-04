package pocketDock.com.pocketDock.service;



import pocketDock.com.pocketDock.entity.Badwords;

import java.util.List;

public interface IBadwordsService {
    List<Badwords> getAllBadwords();
    Badwords getBadwordById(long id);
    Badwords addBadword(Badwords badwords);
    void removeBadword(long id);
}
