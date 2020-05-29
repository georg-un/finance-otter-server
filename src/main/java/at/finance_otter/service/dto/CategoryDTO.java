package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.Category;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    private Long id;
    private String label;
    private String color;

    public static CategoryDTO fromCategory(Category category) {
        if (category != null) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getGenId());
            dto.setLabel(category.getLabel());
            dto.setColor(category.getColor());
            return dto;
        } else {
            return null;
        }
    }
}
