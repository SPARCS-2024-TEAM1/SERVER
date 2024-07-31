package com.sparcs.team1.global.common.external.clova.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final AmazonS3Client amazonS3Client;
    private final AudioConverter audioConverter;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadObjectStorage(String fileName, MultipartFile multipartFile) {
        File convertedFile = audioConverter.convertAudioToMp3(multipartFile);

        if (convertedFile == null) {
            throw new CustomException(ErrorType.FAILED_FILE_UPLOAD_ERROR);
        }

        try (FileInputStream fileInputStream = new FileInputStream(convertedFile)) {
            // 파일 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileInputStream.available());
            metadata.setContentType("audio/mpeg");

            amazonS3Client.putObject(
                    new PutObjectRequest(
                            bucket,
                            fileName,
                            fileInputStream,
                            metadata
                    ).withCannedAcl(
                            CannedAccessControlList.PublicRead
                    )
            );
        } catch (IOException e) {
            throw new CustomException(ErrorType.FAILED_FILE_UPLOAD_ERROR);
        } finally {
            if (convertedFile.exists()) {
                convertedFile.delete();
            }
        }

        return fileName;
    }
}
