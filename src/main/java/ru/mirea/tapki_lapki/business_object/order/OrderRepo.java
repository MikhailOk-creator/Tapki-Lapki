package ru.mirea.tapki_lapki.business_object.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.tapki_lapki.business_object.client.Client;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Order findByClientAndStatusOfOrder(Client client, String status);
    Order findByClientIdAndStatusOfOrder(Long clientId, Status status);
    Order findByStatusOfOrder(String status);
    Order findByClientIdAndIsCart(Long ClientId, Boolean cart);
}
