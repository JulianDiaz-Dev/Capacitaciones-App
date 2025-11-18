package com.example.capacitaciones.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.capacitaciones.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final String bucket;

    public MinioServiceImpl(
            @Value("${minio.url}") String url,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey,
            @Value("${minio.bucket}") String bucket) {
        try {
            System.out.println("MinioClient inicializado correctamente");
            this.minioClient = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
                    .build();
            this.bucket = bucket;
            
        } catch (Exception e) {
            System.err.println("Error inicializando MinioClient: " + e.getMessage());
            throw e; // relanzar para que Spring siga fallando pero tengas el log
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
    try {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(filename)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        }

        // URL pública del archivo
        String urlPublica = "http://localhost:9000/" + bucket + "/" + filename;
        return urlPublica;

    } catch (Exception e) {
        throw new RuntimeException("No se pudo subir el archivo", e);
    }
}
}
