package at.finance_otter.web;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import at.finance_otter.service.dto.PurchaseDTO;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MultipartBody {

    @FormParam("receipt")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream receipt;

    @FormParam("purchase")
    @PartType(MediaType.TEXT_PLAIN)
    public String purchase;

}
