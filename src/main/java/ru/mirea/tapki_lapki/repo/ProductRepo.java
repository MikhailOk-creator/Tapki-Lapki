package ru.mirea.tapki_lapki.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.tapki_lapki.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
