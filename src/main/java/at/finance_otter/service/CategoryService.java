package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.service.dto.CategoryDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryService {

    @Inject
    DatabaseAdapter databaseAdapter;

    public List<CategoryDTO> getCategories() {
        return this.databaseAdapter.getCategories()
                .stream()
                .map(CategoryDTO::fromCategory)
                .collect(Collectors.toList());
    }

}
