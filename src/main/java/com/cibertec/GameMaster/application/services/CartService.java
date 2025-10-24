package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.CustomerPort;
import com.cibertec.GameMaster.application.port.OrderPort;
import com.cibertec.GameMaster.application.port.ProductPort;
import com.cibertec.GameMaster.domain.Cart;
import com.cibertec.GameMaster.domain.ItemCart;
import com.cibertec.GameMaster.infraestructure.database.entity.Customer;
import com.cibertec.GameMaster.infraestructure.database.entity.Order;
import com.cibertec.GameMaster.infraestructure.database.entity.OrderItem;
import com.cibertec.GameMaster.infraestructure.database.entity.Product;
import com.cibertec.GameMaster.infraestructure.web.dto.cart.PurchaseRequest;
import com.cibertec.GameMaster.infraestructure.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private OrderPort orderPort;

    @Autowired
    private ProductPort productPort;

    @Autowired
    private CustomerPort customerPort;

    @Transactional
    public Long procesarOrden(PurchaseRequest request) {


        Customer customer = customerPort.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", request.customerId()));

        Cart cart = new Cart(
                request.items().stream()
                        .map(item -> {
                            Product product = productPort.findById(item.productId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Product", item.productId()));
                            return new ItemCart(product.getId(), product.getName(), product.getPrice(), item.itemCount());
                        })
                        .toList()
        );

        // Crear la orden
        Order order = Order.builder()
                .customer(customer)
                .orderTotal(cart.getTotal())
                .deliveryAddress(request.deliveryAddress())
                .deliveryPhone(request.deliveryPhone())
                .paymentMethod(request.paymentMethod())
                .observations(request.observations())
                .build();


        // Crear los items de la orden
        List<OrderItem> orderItems = cart.getItems().stream().map(
                itemCart -> OrderItem.builder()
                        .order(order)
                        .productId(itemCart.getProductId())
                        .productName(itemCart.getProductName())
                        .itemQuantity(itemCart.getItemCount())
                        .unitPrice(itemCart.getUnitPrice())
                        .subtotal(itemCart.getSubtotal())
                        .build()
        ).toList();

        order.setOrderItems(orderItems);

        orderPort.save(order);

        return order.getId();
    }


}
