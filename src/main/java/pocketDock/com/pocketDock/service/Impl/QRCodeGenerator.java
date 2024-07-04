package pocketDock.com.pocketDock.service.Impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pocketDock.com.pocketDock.entity.Event;
import pocketDock.com.pocketDock.entity.Inscription;
import pocketDock.com.pocketDock.entity.OurUsers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@RequiredArgsConstructor
@Service

public class QRCodeGenerator {

    public static String generateQRCode(Inscription ins, Event event, OurUsers user) throws WriterException, IOException {

       // String qrCodePath = "C:\\Users\\DELL\\IdeaProjects\\QRcode\\";
        String qrCodePath = ".\\uploads\\QRcode\\";
        String qrCodeName = qrCodePath+user.getName()+"-"+event.getTitle()+"-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                        "Username: "+user.getName()+ "\n"+
                        "Email: "+user.getEmail()+ "\n"+
                        "Event Title: "+event.getTitle()+ "\n" +
                        "Event Description: "+event.getDescription()+ "\n"+
                        "Date: "+event.getDate()+ "\n" +
                        "Location: "+event.getLocation(), BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return qrCodeName;

    }
}
