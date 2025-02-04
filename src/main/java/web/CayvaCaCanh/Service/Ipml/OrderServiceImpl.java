package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.OrderService;
import web.CayvaCaCanh.model.*;
import web.CayvaCaCanh.repository.OrderDetailRepository;
import web.CayvaCaCanh.repository.OrderRepository;
import web.CayvaCaCanh.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }


    @Override
    public Page<Order> findAllOrder(Pageable pageable) {

        return orderRepository.findAll(pageable);


    }

    public List<OrderDetail> getOrderDetailsByOrderId(Long id) {
        return orderRepository.findById(id)
                .map(Order::getOrderDetails)
                .orElse(Collections.emptyList());
    }


    @Override
    public void approveOrder(Long orderId) {
        // Tìm đơn hàng theo ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));


        // Cập nhật trạng thái đơn hàng
        order.setStatus(2);  // 2 là đã duyệt

        // Lưu đơn hàng đã cập nhật
        orderRepository.save(order);
    }

    //HỦY
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // Cập nhật trạng thái đơn hàng thành 3
        order.setStatus(3); // Giả sử 3 là trạng thái "Đã hủy"
        orderRepository.save(order);
    }

    @Override
    public void packOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        for (OrderDetail detail : orderDetails) {
            Product product = detail.getProduct();
            int quantity = detail.getQuantity();

            // Cập nhật số lượng tồn kho
            int newStock = product.getNumber() - quantity;
            if (newStock < 0) {
                throw new RuntimeException("Not enough stock for product " + product.getName());
            }
            product.setNumber(newStock);
            productRepository.save(product);
        }
        // Cập nhật trạng thái đơn hàng thành 3
        order.setStatus(4); // Giả sử 3 là trạng thái "Đã hủy"
        orderRepository.save(order);
    }

    @Override
    public void CustomerCancelOrder(Long orderId) {
        // Tìm đơn hàng theo ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        // Cập nhật số lượng tồn kho khi đơn hàng bị hủy
        for (OrderDetail detail : orderDetails) {
            Product product = detail.getProduct();
            int quantity = detail.getQuantity();

            // Cập nhật số lượng tồn kho (tăng lên vì đơn hàng bị hủy)
            int newStock = product.getNumber() + quantity;
            product.setNumber(newStock);
            productRepository.save(product);
        }

        // Cập nhật trạng thái đơn hàng thành trạng thái "Khách hủy"
        order.setStatus(5); // Giả sử 5 là trạng thái "Khách hủy"
        orderRepository.save(order);
    }

    @Override
    public void OrderSuccess(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // Cập nhật trạng thái đơn hàng thành 3
        order.setStatus(6); // Giả sử 3 là trạng thái "Đã hủy"
        orderRepository.save(order);
    }

//LỊCH SỬ ĐƠN  HÀNG

    @Override
    public Page<Order> getOrdersByUser(User user, Pageable pageable) {
        // Sử dụng phương thức của repository để tìm đơn hàng của người dùng
        return orderRepository.findByCartUser(user, pageable);
    }

    @Override
    public Page<Order> getOrdersByUserAndStatus(User user, int status, Pageable pageable) {
        // Implement your query here to filter orders by user and status (as int)
        return orderRepository.findByCartUserAndStatus(user, status, pageable);
    }


    public List<Order> findOrdersByCart(Cart cart) {
        return orderRepository.findByCart(cart);
    }

    @Override
    public long getTotalOrders() {
        return orderRepository.count(); // Lấy tổng số đơn hàng
    }

    //    biểu đồ cho trạng thái đơn hàng
    @Override
    public Map<Integer, Long> getOrderStatusCount() {
        return orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
    }

   @Override
    public Page<Order> findOrdersByStatus(int status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }
}
