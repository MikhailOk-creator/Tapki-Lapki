package ru.mirea.tapki_lapki.business_object.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.business_object.item.Item;
import ru.mirea.tapki_lapki.business_object.item.ItemRepo;
import ru.mirea.tapki_lapki.business_object.order.OrderRepo;
import ru.mirea.tapki_lapki.business_object.order.Status;
import ru.mirea.tapki_lapki.business_object.product.Product;
import ru.mirea.tapki_lapki.business_object.product.ProductRepo;

/**
 * Service for client
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final ItemRepo itemRepo;
    private final ProductRepo productRepo;
    private final ClientRepo clientRepo;
    private final OrderRepo orderRepo;

    /**
     * Method for adding product to cart
     * @param productId id of product
     * @param clientId id of client
     */
    public void addProductToCart(Long productId, Long clientId) {
        Product product = productRepo.findById(productId).orElseThrow();
        Client client = clientRepo.findById(clientId).orElseThrow();
        Item item = new Item();
        if (itemRepo.findByProductAndOrder(product, orderRepo.findByClientAndStatusOfOrder(client, Status.CART)) != null) {
            item = itemRepo.findByProductAndOrder(product, orderRepo.findByClientAndStatusOfOrder(client, Status.CART));
            item.setQuantity(item.getQuantity() + 1);
            item.setTotalPrice(item.getTotalPrice() + product.getPriceOfProduct());
        } else {
            item.setProduct(product);
            item.setQuantity(1);
            item.setTotalPrice(product.getPriceOfProduct());
        }
        itemRepo.save(item);
    }

    /**
     * Method for deleting product from cart
     * @param productId id of product
     * @param clientId id of client
     */
    public void deleteProductFromCart(Long productId, Long clientId) {
        Product product = productRepo.findById(productId).orElseThrow();
        Client client = clientRepo.findById(clientId).orElseThrow();
        Item item = itemRepo.findByProductAndOrder(product, orderRepo.findByClientAndStatusOfOrder(client, Status.CART));
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            item.setTotalPrice(item.getTotalPrice() - product.getPriceOfProduct());
            itemRepo.save(item);
        } else {
            itemRepo.delete(item);
        }
    }

    /**
     * Method for placing order
     * @param clientId id of client
     */
    public void placeOrder(Long clientId) {
        Client client = clientRepo.findById(clientId).orElseThrow();
        orderRepo.findByClientAndStatusOfOrder(client, Status.CART).setStatusOfOrder(Status.NEW);
    }
}
