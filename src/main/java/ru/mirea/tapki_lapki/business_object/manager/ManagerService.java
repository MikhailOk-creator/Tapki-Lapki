package ru.mirea.tapki_lapki.business_object.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.business_object.product.Product;
import ru.mirea.tapki_lapki.business_object.product.ProductRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
    private final ProductRepo productRepo;

    public void addProduct (Product product) {
        try {
            productRepo.save(product);
            log.info("Save new product");
        } catch (Exception e) {
            log.error("Error! No new product added");
        }
    }

    public void editProduct (Long id, String name_p, String description, String image_url) {
        try {
            Product edit_product = productRepo.findByName_p(productRepo.findById(id).get().getName_p());

            if (name_p != null && name_p != "") {
                edit_product.setName_p(name_p);
            }
            if (description != null && description != "") {
                edit_product.setDescription(description);
            }
            if (image_url != null && image_url != "") {
                edit_product.setImage_url(image_url);
            }

            productRepo.save(edit_product);
            log.info("Updated product with id: {}", id);
        } catch (Exception e) {
            log.error("The product has not been updated");
        }
    }

    public void deletedProduct (Long id) {
        try {
            if (productRepo.findById(id).isPresent()) {
                String del_product_name = productRepo.findById(id).get().getName_p();
                productRepo.deleteById(id);
                log.info("Product {} deleted", del_product_name);
            } else {
                log.error("Product not found");
            }
        } catch (Exception e) {
            log.error("The product could not be removed");
        }
    }
}
