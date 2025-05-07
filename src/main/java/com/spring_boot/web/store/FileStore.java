package com.spring_boot.web.store;

import com.spring_boot.web.domain.AttachFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.spring_boot.web.store
 * fileName       : FileStore
 * author         : mzc01-jungminim
 * date           : 2025. 4. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 16.        mzc01-jungminim       최초 생성
 */
@Slf4j
@Component
public class FileStore {

    @Value("${file.dir}")
    String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<AttachFile> storeFiles(List<MultipartFile> files) throws IOException {
        List<AttachFile> result = new ArrayList<>();
        for(MultipartFile file : files) {
            if (!file.isEmpty()) {
                AttachFile attachFile = storeFile(file);
                result.add(attachFile);
            }
        }

        return result;
    }

    public AttachFile storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String uploadFileName = file.getOriginalFilename();
        String ext = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        String uuid = UUID.randomUUID().toString();
        String savedFileName = uuid.concat(".").concat(ext);

        file.transferTo(new File(getFullPath(savedFileName)));

        AttachFile attachFile = new AttachFile(uploadFileName, savedFileName);

        return attachFile;
    }

}
