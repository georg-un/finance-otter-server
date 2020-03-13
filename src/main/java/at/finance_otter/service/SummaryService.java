package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.service.dto.ChartSeries;
import at.finance_otter.service.dto.SummaryDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class SummaryService {

    @Inject
    DatabaseAdapter databaseAdapter;


    public SummaryDTO createSummary() {
        Map<String, Double> credits = this.databaseAdapter.getCredits()
                .stream()
                .collect(Collectors.toMap(a -> (String) a[0], a -> (Double) a[1]));
        Map<String, Double> liabilities = this.databaseAdapter.getLiabilities()
                .stream()
                .collect(Collectors.toMap(a -> (String) a[0], a -> (Double) a[1]));
        Map<String, Double> balances = new HashMap<>();
        // Add all users without purchases to the credits-map
        for (Map.Entry<String, Double> liability : liabilities.entrySet()) {
            if (!credits.containsKey(liability.getKey())) {
                credits.put(liability.getKey(), 0.00);
            }
        }
        // Calculate the balances
        for (Map.Entry<String, Double> entry : credits.entrySet()) {
            balances.put(entry.getKey(), entry.getValue() - liabilities.get(entry.getKey()));
        }
        return new SummaryDTO(balances);
    }

    public List<ChartSeries> getAmountByMonthAndCategory(Integer nMonths) {
        return databaseAdapter.getAmountByCategoryAndDate(nMonths)
                .stream()
                .map(ChartSeries::fromAmountCategoryDateQuery)
                .map(ChartSeries::toList)
                .reduce(new ArrayList<>(), this::reduceChartSeries);
    }

    private List<ChartSeries> reduceChartSeries(List<ChartSeries> aggregatedList, List<ChartSeries> newList) {
        for (ChartSeries series : newList) {
            this.addChartSeriesToList(aggregatedList, series);
        }
        return aggregatedList;
    }

    private List<ChartSeries> addChartSeriesToList(List<ChartSeries> aggregatedList, ChartSeries series) {
        Optional<ChartSeries> sameSeries = aggregatedList
                .stream()
                .filter(x -> x.getName().equals(series.getName()))
                .findFirst();

        if (sameSeries.isPresent()) {
            sameSeries.get().getSeries()
                    .addAll(series.getSeries());
        } else {
            aggregatedList.add(series);
        }

        return aggregatedList;
    }

}
