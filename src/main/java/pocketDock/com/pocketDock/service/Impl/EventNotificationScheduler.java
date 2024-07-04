package pocketDock.com.pocketDock.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pocketDock.com.pocketDock.entity.Event;
import pocketDock.com.pocketDock.entity.Inscription;
import pocketDock.com.pocketDock.entity.OurUsers;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventNotificationScheduler {
    private final EventServiceImpl eventService;
    private final EmailServiceImpl emailService;
    private final WhatsAppMsg whatsAppMsg;
    private final InscriServiceImpl inscriService;
//    @Scheduled(fixedRate = 30000) // Run every day (24 * 60 * 60 * 1000 milliseconds)
    //@Scheduled(cron = "0 0 9 * * *")

    public void sendEventNotifications() throws Exception {
        //System.err.println(LocalDate.now());
        List<Event> events = eventService.getEventsForTomorrow(); // Implement this method to get events happening tomorrow
        for (Event event : events) {
            for (Inscription inscription : event.getInscris()) {
                if (!inscription.isNotified()) {
                    System.err.println(inscription.getId());
                    System.err.println(inscription.getUser().getEmail());
                    OurUsers user = inscription.getUser();
                    String email = user.getEmail();
                    String subject = "Reminder: " + event.getTitle() + " Tomorrow!";
                    String body = "Don't forget, the event " + event.getTitle() + " is happening tomorrow at " + event.getLocation();
                    emailService.sendSimpleMailMessage(email,
                            event.getTitle(),
                            event.getLocation());

                    String tel = "+216" + String.valueOf(user.getTelephone());
                    System.err.println(tel);
                    //whatsAppMsg.sendWhatsAppMessage(tel,subject);
                    // Update notification status
                    inscription.setNotified(true);
                    // You may want to save the inscription back to the database
                    //InscriptionDto dto = InscriptionDto.fromEntity(inscription);
                    inscriService.savo(inscription);
                }
            }
        }
    }
}
