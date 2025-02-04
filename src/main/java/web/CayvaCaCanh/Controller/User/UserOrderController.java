package web.CayvaCaCanh.Controller.User;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.CayvaCaCanh.Service.*;
import web.CayvaCaCanh.model.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.coyote.http11.Constants.a;

@Controller
@RequestMapping("/user/")
public class UserOrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StaffsService staffsService;

    @Autowired
    private ProductService productService;


    @Autowired
    private UserService userService;

    @GetMapping("/cart/pay")
    public ModelAndView processPayment(@RequestParam("selectedProductIds") String selectedProductIds) {
        ModelAndView mv = new ModelAndView();
        // In ra giá trị của selectedProductIds để kiểm tra

        // Chuyển đổi chuỗi JSON thành danh sách ID sản phẩm
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> productIds;
        try {
            productIds = objectMapper.readValue(selectedProductIds, new TypeReference<List<String>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            mv.setViewName("redirect:/cart-pay/cart");
            return mv;
        }

        System.out.println("Product IDs List: " + productIds);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByAccountUsername(username);

        // Lấy giỏ hàng của người dùng
//        Account account = accountServiceImpl.getAccountByUsername(username);
//
        Cart cart = cartService.findByUser(user);

        // Lấy các chi tiết sản phẩm dựa trên ID đã chọn
        List<CartDetail> selectedCartDetails = cartService.findByProductIdsAndCartId(productIds, cart.getId());

        // Kiểm tra nếu không có sản phẩm nào được chọn
        if (selectedCartDetails.isEmpty()) {
            mv.setViewName("redirect:/cart-pay/cart");
            return mv;
        }

        // Chuyển dữ liệu đến trang thanh toán
        mv.addObject("selectedCartDetails", selectedCartDetails);
        mv.setViewName("user/cart/pay");

        return mv;
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam("selectedProductIds") String selectedProductIds, Authentication authentication
            , RedirectAttributes redirectAttributes) {
        // Tách các mã sản phẩm từ chuỗi
        List<String> productIdList = Arrays.asList(selectedProductIds.split(","));

        System.out.println("list sản phẩm" + productIdList  );
        System.out.println(" sản phẩm khi thanh toán"  );

        // Lấy thông tin người dùng từ SecurityContext
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByAccountUsername(username);

        if (user.getAddress() == null || user.getAddress().isEmpty()
           || user.getSdt() == null || user.getSdt().isEmpty()) {
            // Nếu thông tin chưa đủ, thêm thông báo lỗi
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn chưa đủ thông tin.");
            return "redirect:/user/AccountDetail"; // Chuyển hướng về trang giỏ hàng hoặc trang hiện tại
        }
        // Lấy giỏ hàng của người dùng
        Cart cart = cartService.findByUser(user);
        Pay pay = payService.getPaysById("1");
        // Tạo đơn hàng mới
        Order order = new Order();
        order.setDate(new Date());
        order.setPay(pay);
        order.setCart(cart);
        order.setStatus(1);
        // Lưu đơn hàng vào cơ sở dữ liệu
        orderService.save(order);
        System.out.println("đơn đã mua" +order);
        // Tạo chi tiết đơn hàng từ mã sản phẩm
        List<OrderDetail> orderDetails = productIdList.stream()
                .map(productId -> {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrder(order);
                    // Tìm sản phẩm bằng ID và thiết lập cho chi tiết đơn hàng
                    Product product = productService.findByID(productId);  // Phương thức để lấy sản phẩm

                    if (product == null) {
                        throw new RuntimeException("Product with ID " + productId + " not found");
                    }

                    detail.setProduct(product);
                    detail.setQuantity(cartService.getQuantityForProduct(productId,cart));  // Phương thức để lấy số lượng
                    detail.setPrice(product.getPrice());
                    OrderDetailPK pk = new OrderDetailPK(order.getId(), productId);
                    detail.setId(pk);

                    System.out.println("đơn đã mua" + detail);
                    return detail;
                })
                .collect(Collectors.toList());

        order.setOrderDetails(orderDetails);

        // Xóa các sản phẩm đã đặt hàng khỏi giỏ hàng
        cartService.removeOrderedProducts(cart, productIdList);

        // Redirect đến trang thành công
        return "redirect:/user/products";
    }


}
