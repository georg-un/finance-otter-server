package at.finance_otter.web;

import at.finance_otter.persistence.entity.Receipt;
import at.finance_otter.service.ExposableException;
import at.finance_otter.service.PurchaseService;
import at.finance_otter.service.ReceiptService;
import at.finance_otter.service.dto.PurchaseDTO;
import io.quarkus.security.Authenticated;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/fino/purchases")
@Authenticated
@RequestScoped
@Transactional(rollbackOn = ExposableException.class)
public class PurchaseResource {

    @Inject
    PurchaseService purchaseService;

    @Inject
    ReceiptService receiptService;

    @GET
    @Path("/{purchaseId}")
    @Produces("application/json")
    public PurchaseDTO getPurchase(@PathParam("purchaseId") String purchaseId) {
        return purchaseService.getPurchase(purchaseId);
    }

    @POST
    @Path("/{purchaseId}/receipt")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response createReceipt(
            @PathParam("purchaseId") String purchaseId,
            @MultipartForm MultipartBody multipartBody
    ) throws IOException, ExposableException {
        Receipt receipt = receiptService.createReceipt(IOUtils.toByteArray(multipartBody.file), purchaseId);
        if (receipt != null) {
            Response.ResponseBuilder response = Response.ok();
            return response.build();
        } else {
            Response.ResponseBuilder response = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            return response.build();
        }
    }

    @GET
    @Path("/{purchaseId}/receipt")
    @Produces("image/png")
    public byte[] getReceipt(@PathParam("purchaseId") String purchaseId) {
        Receipt receipt = this.receiptService.getReceipt(purchaseId);
        return receipt != null ? receipt.getImage() : null;
    }

    @GET
    @Produces("application/json")
    public List<PurchaseDTO> getPurchases(@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
        return purchaseService.getPurchases(offset, limit);
    }

    @POST
    @Produces("application/json")
    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        return purchaseService.createPurchase(purchaseDTO);
    }

    @PUT
    @Produces("application/json")
    public PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        return purchaseService.updatePurchase(purchaseDTO);
    }

    @DELETE
    @Path("/{purchaseId}")
    @Produces("application/json")
    public void deletePurchase(@PathParam("purchaseId") String purchaseId) throws ExposableException {
        this.purchaseService.deletePurchase(purchaseId);
    }

}
