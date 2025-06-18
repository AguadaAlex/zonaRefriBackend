package com.DemoRefri.demoRefri.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(@Value("${aws.access-key}") String accessKey,
                     @Value("${aws.secret-key}") String secretKey,
                     @Value("${aws.region}") String region) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public String subirImagen(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType()) // ✅ Eliminando ACL.PUBLIC_READ
                .build();

        try {
            s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));
            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName; // ✅ URL generada manualmente
        } catch (Exception e) {
            throw new RuntimeException("Error al subir imagen a S3: " + e.getMessage(), e);
        }
    }
}