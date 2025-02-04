package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.CayvaCaCanh.model.Cart;
import web.CayvaCaCanh.model.CartDetail;
import web.CayvaCaCanh.model.CartDetailPk;

import java.util.List;

public interface CartDetailRepository   extends JpaRepository<CartDetail, CartDetailPk> {

    @Query("SELECT cd FROM CartDetail cd WHERE cd.cart.id = :cartId AND cd.product.id IN :productIds")
    List<CartDetail> findByProductIdsAndCartId(@Param("productIds") List<String> productIds, @Param("cartId") String cartId);
// XÓA  CÁC SẢN PHẨM CÓ CÙNG ID TRONG GIỎ HÀNG
    @Query("SELECT cd FROM CartDetail cd WHERE cd.cart = :cart AND cd.product.id IN :productIds")
    List<CartDetail> findByCartAndProductIdIn(@Param("cart") Cart cart, @Param("productIds") List<String> productIds);

    void deleteByCart(Cart cart);

    CartDetail findByProductIdAndCartId(String productId, String cartId);
}
