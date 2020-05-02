package at.finance_otter.web;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MultipartPurchase extends MultipartReceipt {

    @FormParam("purchase")
    @PartType(MediaType.TEXT_PLAIN)
    public String purchase;

}
