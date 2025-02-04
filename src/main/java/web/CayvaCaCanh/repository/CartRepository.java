package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.CayvaCaCanh.model.Cart;
import web.CayvaCaCanh.model.User;

import java.util.Optional;

@Repository
public interface CartRepository  extends JpaRepository<Cart, String> {
    Cart findByUserId(String userId);

    Cart findByUser(User user);

    @Query("SELECT COUNT(c.id) FROM Cart c")
    Long countCartById();
    @Query("SELECT MAX(c.id) FROM Category c")
    String findMaxId();

    @Query("SELECT c FROM Cart c WHERE c.user = :user AND c.id = :cartId")
    Optional<Cart> findByUserAndId(@Param("user") User user, @Param("cartId") String cartId);
}
