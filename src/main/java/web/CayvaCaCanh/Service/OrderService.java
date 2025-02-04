package web.CayvaCaCanh.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.CayvaCaCanh.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    void save(Order order);
    Order getOrderById(Long id);
    Page<Order> findAllOrder(Pageable pageable);
    List<OrderDetail> getOrderDetailsByOrderId(Long id);
    void approveOrder(Long orderId);

    void cancelOrder(Long orderId) ;
    void packOrder(Long orderId) ;
    void CustomerCancelOrder(Long orderId);
    void OrderSuccess(Long orderId);
    // Phương thức để lấy đơn hàng của người dùng với phân trang
    Page<Order> getOrdersByUser(User user, Pageable pageable);
    Page<Order> getOrdersByUserAndStatus(User user, int status, Pageable pageable);
    List<Order> findOrdersByCart(Cart cart);
    Optional<Order> findById(Long id);
//    Tổng số đơn hàng
    long getTotalOrders();
    Map<Integer, Long> getOrderStatusCount();

    Page<Order> findOrdersByStatus(int status, Pageable pageable);
}
