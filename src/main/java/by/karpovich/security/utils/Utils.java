package by.karpovich.security.utils;

import by.karpovich.security.exception.ImpossibleActionException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class Utils {

    public static final String UPLOAD_PATH = "D://image//poster";

    public static String saveFile(MultipartFile file) {
        if (file == null) {
            return null;
        }

        Path uploadPath = Paths.get(UPLOAD_PATH);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new ImpossibleActionException("Incorrect path");
            }
        }

        String uuidFile = UUID.randomUUID().toString();
        String name = uuidFile + "-" + file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(name);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImpossibleActionException("Incorrect path");
        }

        return name;
    }

    public static byte[] getImageAsResponseEntity(String fileName) {
        if (fileName == null) {
            return null;
        }
        String dirPath = UPLOAD_PATH + "//";
        InputStream in = null;
        byte[] media = new byte[0];
        try {
            in = new FileInputStream(dirPath + fileName);
            media = IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new ImpossibleActionException("Incorrect path");
        }
        return media;
    }
}
