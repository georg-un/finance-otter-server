package at.finance_otter.web;

import at.finance_otter.service.ExposableException;
import at.finance_otter.service.SummaryService;
import at.finance_otter.service.dto.ChartSeries;
import at.finance_otter.service.dto.SummaryDTO;
import io.quarkus.security.Authenticated;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.util.List;

@Path("/fino/summary")
@Authenticated
@RequestScoped
@Transactional(rollbackOn = ExposableException.class)
public class SummaryResource {

    @Inject
    SummaryService summaryService;

    @GET
    @Path("/balance")
    @Produces("application/json")
    public SummaryDTO getSummary() {
        return summaryService.createSummary();
    }

    @GET
    @Path("/month_category")
    @Produces("application/json")
    public List<ChartSeries> getAmountByMonthAndCategory(@QueryParam("months") Integer months) {
        return months == null ?
                summaryService.getAmountByMonthAndCategory(6) :
                summaryService.getAmountByMonthAndCategory(months);
    }

}
