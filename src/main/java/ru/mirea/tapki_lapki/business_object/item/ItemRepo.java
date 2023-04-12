package ru.mirea.tapki_lapki.business_object.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.tapki_lapki.business_object.order.Order;
import ru.mirea.tapki_lapki.business_object.product.Product;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    Item findByProductAndOrder(Product product, Order byClientAndStatus);
}
