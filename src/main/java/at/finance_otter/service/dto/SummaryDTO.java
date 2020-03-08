package at.finance_otter.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SummaryDTO implements Serializable {
    private Map<String, Double> balances;
}
