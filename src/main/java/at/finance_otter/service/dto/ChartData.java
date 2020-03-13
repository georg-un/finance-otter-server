package at.finance_otter.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartData {

    private String name;
    private Double value;

}
