package com.sda.OnlineShop.services;

import com.sda.OnlineShop.entities.CustomerOrder;
import com.sda.OnlineShop.entities.SelectedProduct;
import com.sda.OnlineShop.entities.ShoppingCart;
import com.sda.OnlineShop.entities.User;
import com.sda.OnlineShop.repository.CustomerOrderRepository;
import com.sda.OnlineShop.repository.SelectedProductRepository;
import com.sda.OnlineShop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private SelectedProductRepository selectedProductRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    public void launchOrder (String authenticatedEmailAddress) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmailAddress(authenticatedEmailAddress);
        User user = shoppingCart.getUser();

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user);
//        customerOrder.setSelectedProducts(shoppingCart.getSelectedProducts());
        customerOrderRepository.save(customerOrder);

        // scoatem selectedProducts din shoppingCart pentru ca sunt prinse in comanda
        for (SelectedProduct selectedProduct : shoppingCart.getSelectedProducts()) {
            selectedProduct.setShoppingCart(null);
            selectedProduct.setCustomerOrder(customerOrder);
            selectedProductRepository.save(selectedProduct);
        }
    }
}