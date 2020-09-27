package at.finance_otter.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ChartData {

    private BigInteger categoryId;
    private Double value;


    public static ChartData fromAmountCategoryQuery(Object[] object) {
        return new ChartData(
                ((BigInteger)object[0]),
                ((Double)object[1])
        );
    }

}
