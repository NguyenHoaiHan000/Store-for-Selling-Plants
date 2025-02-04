package web.CayvaCaCanh.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.CayvaCaCanh.Service.*;
import web.CayvaCaCanh.model.*;
import web.CayvaCaCanh.repository.*;
import web.CayvaCaCanh.Controller.Method.method;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class UserReviewController {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/order/{orderId}/review")
    public String showReviewPage(@PathVariable Long orderId, Model model) {
        // Lấy đơn hàng từ ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        // Lấy danh sách các chi tiết đơn hàng
        List<OrderDetail> orderDetails = order.getOrderDetails();

        // Lấy đánh giá cho từng sản phẩm trong đơn hàng
        Map<String, Review> productReviews = new HashMap<>();
        for (OrderDetail detail : orderDetails) {
            Product product = detail.getProduct();
            if (product != null) {
                Review review = reviewService.findByProductId(product.getId());
                productReviews.put(product.getId(), review);
            }
        }

        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("productReviews", productReviews);

        return "user/review/review"; // Tên template Thymeleaf cho trang đánh giá
    }


    @PostMapping("/review")
    public ResponseEntity<String> submitReview(@RequestParam String productId, @RequestParam int rating) {
        // Lấy thông tin người dùng hiện tại
        System.out.println("mã sảng phẩm + đánh giá" + productId + rating);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByAccountUsername(username);


        ReviewId reviewId = new ReviewId(productId, user.getId());

        // Tìm Review hiện tại hoặc tạo mới nếu không tồn tại
        Review review = reviewRepository.findById(reviewId).orElse(new Review());

        // Thiết lập các thuộc tính cho Review
        review.setReviewId(reviewId);
        review.setUser(user);
        review.setRating(rating);
        String list = user.getId() + "," + productId + "," + rating;
        method methods = new method();
        String tmp = methods.saveRatingRecord(list);
//			String tmp = "";

        System.out.println("get danh gia");
        System.out.println("CustomerId: " + user.getId() + " \nProductId: " + productId + " \nRating: " + rating);
//			System.out.println(" PHUC "+" "+ tmp);
//			// lưu đánh giá
        // Lưu Review
        reviewRepository.save(review);

        return ResponseEntity.ok("Review submitted successfully");
    }

}
