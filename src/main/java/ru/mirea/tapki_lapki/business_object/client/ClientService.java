package ru.mirea.tapki_lapki.business_object.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.tapki_lapki.business_object.item.Item;
import ru.mirea.tapki_lapki.business_object.item.ItemRepo;
import ru.mirea.tapki_lapki.business_object.order.Order;
import ru.mirea.tapki_lapki.business_object.order.OrderRepo;
import ru.mirea.tapki_lapki.business_object.order.Status;
import ru.mirea.tapki_lapki.business_object.product.Product;
import ru.mirea.tapki_lapki.business_object.product.ProductRepo;

import java.util.Collections;
import java.util.List;

import static ru.mirea.tapki_lapki.business_object.order.Status.CART;

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
    public Item addProductToCart(Long productId, Long clientId) {
        try {
            Product product = productRepo.findById(productId).orElseThrow();
            Client client = clientRepo.findById(clientId).orElseThrow();

            if (!client.getCart()) {
                Order order = new Order();
                order.setStatusOfOrder(CART);
                order.setCart(true);
                order.setClient(client);
                orderRepo.save(order);
                Item item = new Item();
                item.setProduct(product);
                item.setQuantity(1);
                item.setTotalPrice(product.getPriceOfProduct());
                item.setOrder(orderRepo.findByClientIdAndIsCart(clientId, true));
                item.setTotalPrice(product.getPriceOfProduct());
                itemRepo.save(item);
                client.setCart(true);
                clientRepo.save(client);
                log.info("Client {} create new cart", clientId);
                return item;
            } else {
                Order exist_order = orderRepo.findByClientIdAndIsCart(clientId, true);
                Item item = itemRepo.findByProductAndOrder(product, exist_order);
                if (item != null) {
                    log.warn("Found another product in cart");
                    item.setQuantity(item.getQuantity() + 1);
                    item.setTotalPrice(item.getTotalPrice() + product.getPriceOfProduct());
                    itemRepo.save(item);
                    log.info("Add new product {} in client {} cart", productId, clientId);
                    return item;
                } else {
                    log.warn("Not found product in cart");
                    Item new_item = new Item();
                    new_item.setProduct(product);
                    new_item.setQuantity(1);
                    new_item.setTotalPrice(product.getPriceOfProduct());
                    new_item.setOrder(item.getOrder());
                    itemRepo.save(new_item);
                    log.info("Add new product {} in client {} cart", productId, clientId);
                    return new_item;
                }
            }
        } catch (Exception e) {
            log.error("Something wrong!");
            log.warn(e.toString());
            return null;
        }
    }

    /**
     * Method for deleting product from cart
     * @param productId id of product
     * @param clientId id of client
     */
    public Item deleteProductFromCart(Long productId, Long clientId) {
        Product product = productRepo.findById(productId).orElseThrow();
        Order exist_order = orderRepo.findByClientIdAndIsCart(clientId, true);
        Item item = itemRepo.findByProductAndOrder(product, exist_order);
        if (item != null) {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                item.setTotalPrice(item.getTotalPrice() - product.getPriceOfProduct());
                itemRepo.save(item);
            } else {
                itemRepo.delete(item);
            }
            return item;
        } else {
            log.warn("Client {} don't have product {} in cart!", clientId, productId);
            return null;
        }
    }

    public List<Item> showClientCart(Long clientId) {
        Order exist_order = orderRepo.findByClientIdAndIsCart(clientId, true);
        List<Item> cart = itemRepo.findAll();
        for (Item item : cart) {
            if (item.getOrder().getId() != exist_order.getId()) {
                cart.remove(item);
            }
        }
        return cart;
    }

    /**
     * Method for placing order
     * @param clientId id of client
     */
    public Order placeOrder(Long clientId) {
        try {
            Client client = clientRepo.findById(clientId).orElseThrow();
            Order order = orderRepo.findByClientAndStatusOfOrder(client, CART.toString());
            order.setStatusOfOrder(Status.NEW);
            orderRepo.save(order);
            log.info("Order {} by user {} was placed", order.getId(), client.getId());
            return order;
        } catch (Exception e) {
            log.error("Order was not placed");
            return null;
        }
    }
}
