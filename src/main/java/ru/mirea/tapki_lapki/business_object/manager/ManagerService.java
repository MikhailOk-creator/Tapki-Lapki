package ru.mirea.tapki_lapki.business_object.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.tapki_lapki.business_object.product.Product;
import ru.mirea.tapki_lapki.business_object.product.ProductRepo;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductRepo productRepo;

    public List<Product> allProducts() {
        return productRepo.findAll();
    }

    public void addProduct (Product product) {
        try {
            productRepo.save(product);
            log.info("Save new product");
        } catch (Exception e) {
            log.error("Error! No new product added");
        }
    }

    public void editProduct (Long id, String name_p, String description, MultipartFile image) {
        try {
            Product edit_product = productRepo.findByNameOfProduct(productRepo.findById(id).get().getNameOfProduct());

            if (name_p != null && name_p != "") {
                edit_product.setNameOfProduct(name_p);
            }
            if (description != null && description != "") {
                edit_product.setDescription(description);
            }
            String image_url = uploadImage(id, image);
            if (image_url != null && image_url != "") {
                edit_product.setImageURL(image_url);
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
                String del_product_name = productRepo.findById(id).get().getNameOfProduct();
                productRepo.deleteById(id);
                log.info("Product {} deleted", del_product_name);
            } else {
                log.error("Product not found");
            }
        } catch (Exception e) {
            log.error("The product could not be removed");
        }
    }

    public String uploadImage (Long product_id, MultipartFile image_file) {
        if (productRepo.findById(product_id) != null && image_file != null && !image_file.getOriginalFilename().isEmpty()) {
            Product edit_product = productRepo.findByNameOfProduct(productRepo.findById(product_id).get().getNameOfProduct());

            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + image_file.getOriginalFilename();

            edit_product.setImageURL(resultFileName);

            try {
                String result_path = uploadPath + "/" + resultFileName;
                image_file.transferTo(new File(result_path));
                log.info("File {} uploaded", resultFileName);
                productRepo.save(edit_product);
                log.info("Product with id {} updated");
                return result_path;
            } catch (Exception e) {
                log.error("File {} not uploaded", resultFileName);
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
