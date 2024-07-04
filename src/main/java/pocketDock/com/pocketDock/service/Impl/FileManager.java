package pocketDock.com.pocketDock.service.Impl;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private static final String UPLOAD_DIR = "./uploads/imageMap";

    public static String saveFile(MultipartFile file) {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (success) System.out.println("Directory created: " + UPLOAD_DIR);
        }

        if (file.getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);
            try {
                Files.copy(file.getInputStream(), filePath);
                System.out.println("File saved: " + filePath);
            } catch (IOException e) {
                System.out.println("Could not save file: " + e);
            }

            return uniqueFileName;
        } else {
            throw new IllegalArgumentException("File is empty");
        }
    }

}