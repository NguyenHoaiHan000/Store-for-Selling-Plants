package web.CayvaCaCanh.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.CayvaCaCanh.Service.OrderDetailService;
import web.CayvaCaCanh.Service.OrderService;
import web.CayvaCaCanh.model.*;
import web.CayvaCaCanh.repository.OrderDetailRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")

public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("")
    public String showCategories(Model model, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "11") int size,
                                 @RequestParam(defaultValue = "ALL") String status) {
        Page<Order> receiptPage;

        if ("ALL".equals(status)) {
            // Nếu chọn "ALL", lấy tất cả các đơn hàng
            receiptPage = orderService.findAllOrder(PageRequest.of(page, size));
        } else {
            // Nếu không, lấy các đơn hàng theo trạng thái
            int statusInt = Integer.parseInt(status);
            receiptPage = orderService.findOrdersByStatus(statusInt, PageRequest.of(page, size));
        }
        model.addAttribute("orders", receiptPage.getContent());
        model.addAttribute("currentPage", receiptPage.getNumber());
        model.addAttribute("totalPages", receiptPage.getTotalPages());
        model.addAttribute("totalItems", receiptPage.getTotalElements());
        return "admin/order/list";


    }

    @GetMapping("/{id}/details")
    public String getOrderDetails(@PathVariable Long id, Model model) {
        // Lấy chi tiết đơn hàng từ cơ sở dữ liệu
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(id);
        Optional<Order> optionalOrder = orderService.findById(id);
        if (!optionalOrder.isPresent()) {
            // Xử lý khi không tìm thấy đơn hàng
            return "redirect:/admin/orders"; // Hoặc trang lỗi khác
        }

        Order order = optionalOrder.get();
        double totalPrice = 0;
        for (OrderDetail detail : orderDetails) {
            double productTotal = detail.getPrice() * detail.getQuantity();
            detail.setProductTotal(productTotal); // Thêm tổng giá của sản phẩm vào đối tượng detail
            totalPrice += productTotal;
        }
        // Đưa dữ liệu vào model để Thymeleaf có thể sử dụng
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderId", id); // Nếu cần hiển thị thêm thông tin đơn hàng
        Cart cart = order.getCart();
        if (cart != null) {
            User customer = cart.getUser(); // Lấy thông tin khách hàng từ giỏ hàng
            model.addAttribute("customer", customer); // Thêm thông tin khách hàng vào model
            model.addAttribute("customerEmail", customer.getAccount().getEmail());
        }

        System.out.println("chi tiết phiếu đặt" + orderDetails);
        model.addAttribute("totalPrice", totalPrice);
        // Trả về tên của Thymeleaf template
        return "admin/order/orderDetailsModal";
    }


//

    @PostMapping("/{orderId}/details/update")
    @ResponseBody
    public ResponseEntity<String> updateOrderDetailQuantity(
            @RequestParam("productId") String productId,
            @RequestParam("quantity") Integer quantity,
            @PathVariable("orderId") Long orderId) {

        try {

            Order order = orderService.findById(orderId).orElseThrow();

            // Kiểm tra trạng thái đơn hàng
            if (order.getStatus() != 2) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Order status does not allow quantity update");
            }

            // Lấy thông tin chi tiết đơn hàng dựa vào orderId và productId
            OrderDetailPK id = new OrderDetailPK();
            id.setOrderId(orderId);
            id.setProductId(productId);

            OrderDetail detail = orderDetailRepository.findById(id).orElseThrow() ;

            // Cập nhật số lượng
            detail.setQuantity(quantity);
            orderDetailRepository.save(detail);

            // Nếu cần, cập nhật tổng giá sản phẩm
            // detail.setProductTotal(detail.getQuantity() * detail.getPrice());

            return ResponseEntity.ok("Quantity updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update quantity");
        }
    }


    //    DUYỆT
    @PostMapping("/approve")
    public ResponseEntity<String> approveOrder(@RequestParam("id") Long orderId) {
        try {
            System.out.println("đá nhấn duyệt " + orderId);
            orderService.approveOrder(orderId); // Gọi service để duyệt đơn hàng
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi duyệt đơn hàng!");
        }
    }

    //   HỦY
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestParam("id") Long orderId) {
        try {
            System.out.println("ĐÃ NHẤN HỦY " + orderId);
            orderService.cancelOrder(orderId); // Gọi service để hủy đơn hàng
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi hủy đơn hàng!");
        }
    }

    //    ĐÓNG GÓI
    @PostMapping("/pack")
    public ResponseEntity<String> packOrder(@RequestParam("id") Long orderId) {
        try {
            System.out.println("ĐÓNG GÓI " + orderId);
            orderService.packOrder(orderId); // Gọi service để hủy đơn hàng
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi hủy đơn hàng!");
        }
    }

    //KHÁCH HỦY
    @PostMapping("/CustomerCancelOrder")
    public ResponseEntity<String> CustomerCancelOrder(@RequestParam("id") Long orderId) {
        try {
            System.out.println("ĐÓNG GÓI " + orderId);
            orderService.CustomerCancelOrder(orderId); // Gọi service để hủy đơn hàng
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi hủy đơn hàng!");
        }
    }

    @PostMapping("/Succes")
    public ResponseEntity<String> OrderSuccess(@RequestParam("id") Long orderId) {
        try {
            System.out.println("ĐÃ NHẤN HỦY " + orderId);
            orderService.OrderSuccess(orderId); // Gọi service để hủy đơn hàng
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi hủy đơn hàng!");
        }
    }
}
