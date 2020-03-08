package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.service.dto.SummaryDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class SummaryService {

    @Inject
    DatabaseAdapter databaseAdapter;


    public SummaryDTO createSummary() {
        Map<String, Double> credits = this.databaseAdapter.getCredits()
                .stream()
                .collect(Collectors.toMap(a -> (String)a[0], a -> (Double)a[1]));
        Map<String, Double> liabilities = this.databaseAdapter.getLiabilities()
                .stream()
                .collect(Collectors.toMap(a -> (String)a[0], a -> (Double)a[1]));
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

}
