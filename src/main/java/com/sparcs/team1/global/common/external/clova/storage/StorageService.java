package com.sparcs.team1.global.common.external.clova.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    public String uploadObjectStorage(String fileName, MultipartFile multipartFile) {
        // 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            bucket,
                            fileName,
                            multipartFile.getInputStream(),
                            metadata
                    ).withCannedAcl(
                            CannedAccessControlList.PublicRead
                    )
            );
        } catch (IOException e) {
            throw new CustomException(ErrorType.FAILED_FILE_UPLOAD_ERROR);
        }

        return fileName;
    }
}
