package at.finance_otter.web;

import at.finance_otter.service.CategoryService;
import at.finance_otter.service.ExposableException;
import at.finance_otter.service.dto.CategoryDTO;
import io.quarkus.security.Authenticated;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/fino/categories")
@Authenticated
@RequestScoped
@Transactional(rollbackOn = ExposableException.class)
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoryDTO> getCategories() {
        return categoryService.getCategories();
    }

}
