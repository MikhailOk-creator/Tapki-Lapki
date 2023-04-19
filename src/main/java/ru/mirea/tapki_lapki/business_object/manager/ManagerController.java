package ru.mirea.tapki_lapki.business_object.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.business_object.product.Product;

@Controller
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @QueryMapping
    public Iterable<Product> products() {
        return managerService.allProducts();
    }
}
