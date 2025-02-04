package web.CayvaCaCanh.Service.Ipml;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.CartService;
import web.CayvaCaCanh.Service.CategoryService;
import web.CayvaCaCanh.model.Cart;
import web.CayvaCaCanh.model.CartDetail;
import web.CayvaCaCanh.model.CartDetailPk;
import web.CayvaCaCanh.model.User;
import web.CayvaCaCanh.repository.CartDetailRepository;
import web.CayvaCaCanh.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;


    public Cart findByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserAndIdCart(User user, String cartId) {
        // Sử dụng CartRepository để tìm giỏ hàng theo tài khoản và ID giỏ hàng
        Optional<Cart> optionalCart = cartRepository.findByUserAndId(user, cartId);
        return optionalCart.orElseThrow(() -> new RuntimeException("Cart not found for user and cartId: " + cartId));
    }
    @Override
    public void clearCart(Cart cart) {
        // Xóa tất cả chi tiết trong giỏ hàng
        cartDetailRepository.deleteByCart(cart);
        // Xóa giỏ hàng
        cartRepository.delete(cart);
    }

    @Override
    public List<CartDetail> findByProductIdsAndCartId(List<String> productIds, String cartId) {
        return cartDetailRepository.findByProductIdsAndCartId(productIds, cartId);
    }

    @Override
    public int getQuantityForProduct(String productId, Cart cart) {
        CartDetailPk id = new CartDetailPk(cart.getId(),productId );
        CartDetail cartDetail = cartDetailRepository.findById(id).orElse(null);
        return cartDetail != null ? cartDetail.getQuantity() : 0;
    }

    @Override
    @Transactional
    public void removeOrderedProducts(Cart cart, List<String> productIds) {
        List<CartDetail> itemsToRemove = cartDetailRepository.findByCartAndProductIdIn(cart, productIds);
        cartDetailRepository.deleteAll(itemsToRemove);
    }


}
