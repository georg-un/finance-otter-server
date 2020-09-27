package at.finance_otter.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartSeries {

    private String name;
    private List<ChartData> series;

    public List<ChartSeries> toList() {
        return new ArrayList<>(Arrays.asList(this));
    }

    public static ChartSeries fromAmountCategoryDateQuery(Object[] object) {
        ChartSeries chartSeries = new ChartSeries();
        if ((Double) object[1] < 10) {
            chartSeries.setName(
                    ((Integer) ((Double) object[0]).intValue()).toString() + ".0" +
                            ((Integer) ((Double) object[1]).intValue()).toString()
            );
        } else {
            chartSeries.setName(
                    ((Integer) ((Double) object[0]).intValue()).toString() + "." +
                            ((Integer) ((Double) object[1]).intValue()).toString()
            );
        }
        chartSeries.setSeries(
                new ArrayList<>(Arrays.asList(
                        new ChartData(
                                (BigInteger) object[2],
                                (Double) object[3]
                        )
                ))
        );
        return chartSeries;
    }

}
