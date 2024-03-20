package org.acme;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.ServerException;
import io.quarkus.devui.runtime.DevUIBuildTimeStaticHandler;
import jakarta.activation.MimeType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
@ApplicationScoped
public class S3Repository {

    @Inject
    MinioClient minioClient;




    public void enviarArquivo(String bucket, String hash, InputStream file) {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(hash).stream(
                            file, -1, 10485760)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .build());
        } catch (Exception e) {
            System.out.println("Falha ao enviar o arquivo");
            e.printStackTrace();
        }
    }
  }