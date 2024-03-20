package org.acme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.PartType;


@Path("/arquivos")
public class UploadResource {

       // Injetar dependências necessárias (ex: S3 repository)
    @Inject
    S3Repository s3Repository;

    // Definir método para upload de arquivo
  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response uploadFile(@MultipartForm MultipartBody anexo) throws IOException {
    try {
//      String fileName = file.getName();
//      long fileSize = file.getSize();

      // Salvar arquivo no bucket S3
      s3Repository.enviarArquivo("arquivos", anexo.getFileName() != null ? anexo.getFileName() : UUID.randomUUID().toString(), anexo.getAnexo());

      // Retornar resposta com sucesso
      return Response.ok().status(201).build();
    } catch (Exception e) {
      // Tratar erros e retornar resposta de erro
      return Response.serverError().build();
    }
  }
      
}
