package web.CayvaCaCanh.Controller.Admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.CayvaCaCanh.Service.OrderService;
import web.CayvaCaCanh.Service.ProductService;
import web.CayvaCaCanh.Service.ReviewService;
import web.CayvaCaCanh.Service.UserService;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String index() {
        return "redirect:/admin";
    }

    @GetMapping("/home")
    public String home(Model model) {
        long totalOrders = orderService.getTotalOrders();
        long totalUsers = userService.getTotalUsers();
        long totalProducts = productService.getTotalProducts();
        long totalReviewedProducts = reviewService.getTotalReviewedProducts();
        Map<Integer, Long> statusCountMap = orderService.getOrderStatusCount();
        model.addAttribute("statusCountMap", statusCountMap);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalReviewedProducts", totalReviewedProducts);
        model.addAttribute("message", "Welcome to CayvaCaCanh!");
        return "admin/index"; // Đây là tên của file index.html trong thư mục templates
    }


}
