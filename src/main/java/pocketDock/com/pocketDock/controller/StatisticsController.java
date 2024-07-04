package pocketDock.com.pocketDock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.dto.EventStatDto;
import pocketDock.com.pocketDock.service.Impl.StatisticsServiceImpl;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
@CrossOrigin("http://localhost:4200")

public class StatisticsController {
    private final StatisticsServiceImpl statisticsService;

    @GetMapping("/nbInscriEvent")
    public List<EventStatDto> getEventStats() {
        return statisticsService.getEventStats();
    }

    @GetMapping("/avInscriptions")
    public Double getAverageInscriptionsPerEvent() {
        return statisticsService.getAverageInscriptionsPerEvent();
    }

    @GetMapping("/evBtwDate")
    public List<EventDto> findEventBeetweenDate(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
            ){
        return statisticsService.findEventBeetweenDate(start,end);
    }

    @GetMapping("/evMod")
    public List<EventDto> findEventModified(){
        return statisticsService.findEventModified();
    }

/*
    @GetMapping("/event-by-date")
    public Map<LocalDateTime, Event> findEventByDate(
            @RequestParam("start-date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("end-date")  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ){
        return statisticsService.findEventByDate(startDate,endDate);
    }*/
}
