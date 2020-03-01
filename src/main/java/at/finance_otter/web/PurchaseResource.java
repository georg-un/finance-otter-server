package at.finance_otter.web;

import at.finance_otter.service.ExposableException;
import at.finance_otter.service.PurchaseService;
import at.finance_otter.service.dto.PurchaseDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/fino/purchases")
public class PurchaseResource {

    @Inject
    PurchaseService purchaseService;

    @GET
    @Path("/{secPurchaseId}")
    @Produces("application/json")
    public PurchaseDTO getPurchase(@PathParam("secPurchaseId") String secId) {
        return this.purchaseService.getPurchaseBySecId(secId);
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

}
