package web.CayvaCaCanh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.CayvaCaCanh.model.Cart;
import web.CayvaCaCanh.model.Order;
import web.CayvaCaCanh.model.User;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    Page<Order> findByCartUser(User user, Pageable pageable);
    Page<Order> findByCartUserAndStatus(User user, int status,Pageable pageable);
    List<Order> findByCart(Cart cart);

    Page<Order> findByStatus( int status, Pageable pageable);
}
