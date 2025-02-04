package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.Cart;
import web.CayvaCaCanh.model.CartDetail;
import web.CayvaCaCanh.model.User;

import java.util.List;

public interface CartService  {

    Cart findByUserId(String userId);
    Cart findByUser(User user);
     Cart saveCart(Cart cart);
    Cart getCartByUserAndIdCart(User user, String cartId);
    void clearCart(Cart cart);
    List<CartDetail> findByProductIdsAndCartId(List<String> productIds, String cartId);
    int getQuantityForProduct(String productId, Cart cart);
//  XÓA SẢN PHẨM TRONG GIỎ HÀNG
    void removeOrderedProducts(Cart cart, List<String> productIds);
}
