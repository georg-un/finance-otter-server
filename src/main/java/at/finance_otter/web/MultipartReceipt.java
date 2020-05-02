package at.finance_otter.web;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class MultipartReceipt {

    @FormParam("receipt")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream receipt;

}
