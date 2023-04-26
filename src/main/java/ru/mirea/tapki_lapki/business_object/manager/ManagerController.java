package ru.mirea.tapki_lapki.business_object.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.tapki_lapki.business_object.product.Product;

@Controller
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @QueryMapping
    public Iterable<Product> products() {
        return managerService.allProducts();
    }

    @MutationMapping("createProduct")
    public Product addProduct(@Argument String nameOfProduct,
                              @Argument String description,
                              @Argument Double price,
                              @Argument MultipartFile image) {
        return managerService.addProduct(nameOfProduct, description, price, image);

    }
}
