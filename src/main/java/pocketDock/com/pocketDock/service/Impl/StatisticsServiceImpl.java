package pocketDock.com.pocketDock.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.dto.EventDto;
import pocketDock.com.pocketDock.dto.EventStatDto;
import pocketDock.com.pocketDock.repository.EventRepository;
import pocketDock.com.pocketDock.service.Interfaces.StatisticsService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service

public class StatisticsServiceImpl implements StatisticsService {
    private final EventRepository eventRepository;

    public List<EventStatDto> getEventStats() {
        return eventRepository.findEventStats();
    }

    public Double getAverageInscriptionsPerEvent() {
        return eventRepository.getAverageInscriptionsPerEvent();
    }

    public List<EventDto> findEventBeetweenDate(LocalDate start, LocalDate end){
        return eventRepository.findEventsByDateBetween(start,end)
                .stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<EventDto> findEventModified(){
        return eventRepository.findEventModified()
                .stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }


    /*
    @Override
    public Map<LocalDateTime, Event> findEventByDate(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));
        return eventRepository.findEventByDate(start,end);
    }*/
}
