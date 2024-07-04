package pocketDock.com.pocketDock.service.Impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pocketDock.com.pocketDock.service.Interfaces.EmailService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;


@Service
@RequiredArgsConstructor

public class EmailServiceImpl implements EmailService {

    public static final String NEW_REGISTRATION_IN_EVENT = "new registration in event";
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String NOTIFICATION_FOR_EVENT = "notification for event";
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String thesenderEmail;


    public void sendMailWithAttachmentWithMap(String toEmail,
                                       String name,
                                       String event,
                                       String qrCodeName,
                                        MultipartFile imageUrl
    ) throws MessagingException {
        MimeMessage message=emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message,true, UTF_8_ENCODING);
        mimeMessageHelper.setFrom(thesenderEmail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText("Dear " + name + ",\n\n"
                + "We are delighted to inform you that your registration for " + event
                + " has been successfully registered! We look forward to welcoming you to this unique event.\n\n"
                + "Best regards,\n"
                + "PI cloud team");
        mimeMessageHelper.setSubject(NEW_REGISTRATION_IN_EVENT);

        String fileName=FileManager.saveFile(imageUrl);



        FileSystemResource QrCode= new FileSystemResource(new File(qrCodeName));

        mimeMessageHelper.addAttachment(QrCode.getFilename(),
                QrCode
        );
        mimeMessageHelper.addAttachment("map_image.png", imageUrl);


        emailSender.send(message);
        System.out.printf("Mail with attachment sent successfully..");

    }

    @Override
    public void sendSimpleMailMessage(String to, String title, String location) {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setSubject(NOTIFICATION_FOR_EVENT);
                message.setFrom(thesenderEmail);
                message.setTo(to);
                message.setText("Don't forget, the event " + title + " is happening tomorrow at " + location);
                emailSender.send(message);

        }


    @Override
    public void sendMailWithAttachment(String toEmail,
                                       String name,
                                       String event,
                                       String qrCodeName
                                       ) throws MessagingException {
        MimeMessage message=emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message,true, UTF_8_ENCODING);
        mimeMessageHelper.setFrom(thesenderEmail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText("Dear " + name + ",\n\n"
                + "We are delighted to inform you that your registration for " + event
                + " has been successfully registered! We look forward to welcoming you to this unique event.\n\n"
                + "Best regards,\n"
                + "PI cloud team");
        mimeMessageHelper.setSubject(NEW_REGISTRATION_IN_EVENT);

        FileSystemResource QrCode= new FileSystemResource(new File(qrCodeName));

        mimeMessageHelper.addAttachment(QrCode.getFilename(),
                QrCode
                );

        emailSender.send(message);
        System.out.printf("Mail with attachment sent successfully..");

    }

}