package pocketDock.com.pocketDock.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.entity.Badwords;
import pocketDock.com.pocketDock.service.BadwordsService;

import java.util.List;


    @RestController
    @RequestMapping("/badwords")
    @AllArgsConstructor
    public class BadwordController {
        private final BadwordsService badwordsService;

        @GetMapping("/retrieve-all")
        public List<Badwords> getAllBadwords() {
            return badwordsService.getAllBadwords();
        }

        @PostMapping("/add-badwords")
        public Badwords addBadword(@RequestBody Badwords badwords) {
            return badwordsService.addBadword(badwords);
        }

        @GetMapping("/retrieve/{id}")
        public Badwords getBadwordById(@PathVariable("id") long id) {
            return badwordsService.getBadwordById(id);
        }

        @DeleteMapping("/remove/{id}")
        public void removeBadword(@PathVariable("id") long id) {
            badwordsService.removeBadword(id);
        }
    }

