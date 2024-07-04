package pocketDock.com.pocketDock.service;


import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    public static final String NEW_REGISTRATION_IN_EVENT = "new registration in event";
    public static final String UTF_8_ENCODING = "UTF-8";
    private final JavaMailSender emailSender;


    private String thesenderEmail="nadia.nebhen@hotmail.com";

    @Override
    public void sendSimpleMailMessage(String to, String name, String title) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(NEW_REGISTRATION_IN_EVENT);
        message.setFrom(thesenderEmail);
        message.setTo(to);
        message.setText(" bonjour " + name + " Confirmation d'inscri dans " + title);
        emailSender.send(message);

    }
}