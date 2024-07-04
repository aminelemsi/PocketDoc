package pocketDock.com.pocketDock.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.entity.Badwords;
import pocketDock.com.pocketDock.repository.BadwordsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BadwordsService {
    private final BadwordsRepository badwordsRepository;

    public List<Badwords> getAllBadwords() {
        return badwordsRepository.findAll();
    }

    public Badwords addBadword(Badwords badwords) {
        return badwordsRepository.save(badwords);
    }

    public Badwords getBadwordById(long id) {
        return badwordsRepository.findById(id).orElse(null);
    }

    public void removeBadword(long id) {
        badwordsRepository.deleteById(id);
    }
}
