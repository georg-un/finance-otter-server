package at.finance_otter.web;

import at.finance_otter.service.dto.PurchaseDTO;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

public class MultipartPurchase extends MultipartReceipt {

    @FormParam("purchase")
    @PartType(MediaType.APPLICATION_JSON)
    public PurchaseDTO purchase;

}
