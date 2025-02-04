package web.CayvaCaCanh.Controller.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.CayvaCaCanh.Service.CartService;
import web.CayvaCaCanh.Service.OrderDetailService;
import web.CayvaCaCanh.Service.OrderService;
import web.CayvaCaCanh.Service.UserService;
import web.CayvaCaCanh.model.*;

import java.util.List;

@Controller
@RequestMapping("/user/")
public class UserOrderHistoryController {


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("/order/history")
    public String showUserOrdersAndDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "1") int status,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // Tìm người dùng theo tên đăng nhập
        User user = userService.findByAccountUsername(username);
        if (user == null) {
            return "error"; // Trang lỗi nếu không tìm thấy người dùng
        }

        // Lấy danh sách đơn hàng của người dùng với phân trang
        Page<Order> orderPage = orderService.getOrdersByUserAndStatus(user, status, PageRequest.of(page, size));
        List<Order> orders = orderPage.getContent();

        // Lặp qua từng đơn hàng và lấy chi tiết đơn hàng
        for (Order order : orders) {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(order.getId());
            double totalPrice = 0;
            for (OrderDetail detail : orderDetails) {
                double productTotal = detail.getPrice() * detail.getQuantity();
                detail.setProductTotal(productTotal); // Thêm tổng giá của sản phẩm vào đối tượng detail
                totalPrice += productTotal;
            }
            order.setOrderDetails(orderDetails); // Gán danh sách chi tiết đơn hàng vào đơn hàng
             // Gán tổng giá vào đơn hàng
            order.setTotalPrice(totalPrice);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", orderPage.getNumber());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalItems", orderPage.getTotalElements());
        model.addAttribute("currentStatus", status);
        return "user/History/history"; // Tên của template Thymeleaf cho lịch sử đơn hàng của người dùng
    }

    @PostMapping("/order/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long id) {
        // Tìm đơn hàng theo id
        Order order = orderService.getOrderById(id);
        if (order != null && order.getStatus() == 1) { // Kiểm tra trạng thái đơn hàng có thể hủy
            // Cập nhật trạng thái đơn hàng thành "đã hủy" (ví dụ: trạng thái 5)
            order.setStatus(3);
            orderService.save(order);
        }
        return "redirect:/user/order/history"; // Chuyển hướng lại trang lịch sử đơn hàng
    }
}
