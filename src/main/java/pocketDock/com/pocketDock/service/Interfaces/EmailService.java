package pocketDock.com.pocketDock.service.Interfaces;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMailMessage(String to, String name, String title);
    void sendMailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException;

}
