package at.finance_otter.web;

import at.finance_otter.service.ExposableException;
import at.finance_otter.service.SummaryService;
import at.finance_otter.service.dto.SummaryDTO;
import io.quarkus.security.Authenticated;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/fino/summary")
@Authenticated
@RequestScoped
@Transactional(rollbackOn = ExposableException.class)
public class SummaryResource {

    @Inject
    SummaryService summaryService;

    @GET
    @Produces("application/json")
    public SummaryDTO getSummary() {
        return summaryService.createSummary();
    }

}
