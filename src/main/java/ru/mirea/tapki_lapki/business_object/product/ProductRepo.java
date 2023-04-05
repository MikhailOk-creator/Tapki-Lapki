package ru.mirea.tapki_lapki.business_object.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByNameOfProduct(String nameOfProduct);
}
