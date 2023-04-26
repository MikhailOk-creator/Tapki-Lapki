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

/**
 * Service for manager
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
    /// Path to upload images
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductRepo productRepo;

    /**
     * Method for getting all products
     * @return list of products
     */
    public List<Product> allProducts() {
        return productRepo.findAll();
    }

    /**
     * Method for getting product by id
     * @param name_p name of product
     * @param description description of product
     * @param price price of product
     * @param image image of product
     * @return product
     */
    public Product addProduct (String name_p, String description, Double price, MultipartFile image) {
        try {
            Product product = new Product();

            product.setNameOfProduct(name_p);
            product.setDescription(description);
            product.setPriceOfProduct(price);
            // product.setImageURL(uploadImage(product.getId(), image));

            productRepo.save(product);
            log.info("Save new product");
            return product;
        } catch (Exception e) {
            log.error("Error! No new product added");
            return null;
        }
    }

    /**
     * Method for editing product
     * @param id id of product that we want to edit
     * @param name_p new name of product
     * @param description new description of product
     * @return
     */
    public Product editProduct (Long id, String name_p, String description, Double price) {
        try {
            Product edit_product = productRepo.findByNameOfProduct(productRepo.findById(id).get().getNameOfProduct());

            if (name_p != null && name_p.equals("")) {
                edit_product.setNameOfProduct(name_p);
            }
            if (description != null && description.equals("")) {
                edit_product.setDescription(description);
            }
            if (price != null && price > 0) {
                edit_product.setPriceOfProduct(price);
            }

            productRepo.save(edit_product);
            log.info("Updated product with id: {}", id);
            return edit_product;
        } catch (Exception e) {
            log.error("The product has not been updated");
            return null;
        }
    }

    /**
     * Method for deleting product
     * @param id id of product that we want to delete
     */
    public Boolean deletedProduct (Long id) {
        try {
            if (productRepo.findById(id).isPresent()) {
                String del_product_name = productRepo.findById(id).get().getNameOfProduct();
                productRepo.deleteById(id);
                log.info("Product {} deleted", del_product_name);
                return true;
            } else {
                log.error("Product not found");
                return false;
            }
        } catch (Exception e) {
            log.error("The product could not be removed");
            return false;
        }
    }

    /**
     * Method for uploading image
     * @param product_id id of product that we want to add image
     * @param image_file image that we want to add
     * @return path to image
     */
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
