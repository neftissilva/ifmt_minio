package org.acme;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

public class MultipartBody {
    @FormParam("anexo")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @Schema(type = SchemaType.STRING, format = "binary", description = "file")
    public InputStream anexo;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;

    public InputStream getAnexo() {
        return anexo;
    }

    public void setAnexo(InputStream anexo) {
        this.anexo = anexo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
