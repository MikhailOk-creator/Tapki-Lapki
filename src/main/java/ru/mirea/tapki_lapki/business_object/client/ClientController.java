package ru.mirea.tapki_lapki.business_object.client;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.mirea.tapki_lapki.business_object.item.Item;
import ru.mirea.tapki_lapki.business_object.order.Order;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @MutationMapping("addProductToCart")
    public Item addProductToCart(@Argument Long productId,
                                 @Argument Long clientId) {
        return clientService.addProductToCart(productId, clientId);
    }


    @MutationMapping("deleteProductFromCart")
    public Item deleteProductFromCart(@Argument Long productId,
                                      @Argument Long clientId) {
        return clientService.deleteProductFromCart(productId, clientId);
    }

    @MutationMapping("createOrder")
    public Order createOrder(@Argument Long clientId) {
        return clientService.placeOrder(clientId);
    }

    @QueryMapping("showCart")
    public Iterable<Item> showCart(@Argument Long clientId) {
        return clientService.showClientCart(clientId);
    }
}
