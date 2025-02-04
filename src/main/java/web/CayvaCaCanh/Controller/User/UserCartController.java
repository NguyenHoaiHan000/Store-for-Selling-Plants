package web.CayvaCaCanh.Controller.User;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.CayvaCaCanh.Service.*;
import web.CayvaCaCanh.model.*;
import org.springframework.security.core.Authentication;
import web.CayvaCaCanh.repository.CartRepository;

import java.util.*;

@Controller
@RequestMapping("/user/")
public class UserCartController {


    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private CartRepository cartRepository;

    //    CHI TIẾT GIỎ HÀNG
    @GetMapping("/cart")
    public String showCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            System.out.println("Username: " + username);

            // Giả sử bạn có một UserService để lấy thông tin User từ username
            User user = userService.findByAccountUsername(username);

            if (user != null) {
                Cart cart = cartService.findByUser(user);
                if (cart != null) {
                    model.addAttribute("cart", cart);
                    return "user/cart/cart"; // Thymeleaf template name
                } else {
                    System.out.println("Cart not found for user: " + user.getSurname());
                }
            } else {
                System.out.println("User not found for username: " + username);
            }
        } else {
            System.out.println("Authentication or UserDetails is null");
        }

        return "error"; // Trả về trang lỗi nếu không tìm thấy giỏ hàng
    }

    //  THÊM SẢN PHẨM VÀO GIỎ HÀNG
    @PostMapping("cart/add/{productId}")
    @ResponseBody
    public Map<String, Object> addToCart(@PathVariable String productId, @RequestParam("quantity") int quantity, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("success", false);
            response.put("message", "User not authenticated");
            return response;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByAccountUsername(username);

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = cartService.findByUser(user);
            if (cart == null) {
                cart = new Cart();
                cart.setId(IdGenerator.generateCartId()); // Generate a new ID for the cart
                cart.setUser(user);
                cartService.saveCart(cart);
            }
            session.setAttribute("cart", cart);
        }

        Product product = productService.findByID(productId);
        if (product == null) {
            response.put("success", false);
            response.put("message", "Product not found");
            return response;
        }

        CartDetailPk pk = new CartDetailPk(cart.getId(), product.getId());
        CartDetail existingDetail = cart.getListCartDetail().stream()
                .filter(detail -> detail.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingDetail != null) {
            existingDetail.setQuantity(existingDetail.getQuantity() + quantity);
            cartDetailService.save(existingDetail);
        } else {
            CartDetail cartDetail = new CartDetail(pk, quantity, product, cart);
            cartDetailService.save(cartDetail);
            cart.getListCartDetail().add(cartDetail);
        }

        cartService.saveCart(cart);
        response.put("success", true);
        return response;
    }

    public class IdGenerator {
        public static String generateCartId() {
            // Ví dụ tạo ID ngẫu nhiên với độ dài 10 ký tự
            return UUID.randomUUID().toString().substring(0, 10).replace("-", "").toUpperCase();
        }
    }

    //    XÓA SẢN PHẨM KHỎI GIỎ HÀNG
//    @PostMapping("cartdetail/remove/{productId}")
//    public String removeFromCart(@PathVariable String productId, HttpSession session) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return "redirect:/login";
//        }
//
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        String username = userDetails.getUsername();
//
//        User user = userService.findByAccountUsername(username);
//
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null) {
//            cart = cartService.findByUser(user);
//            if (cart == null) {
//                return "redirect:/user/products";
//            }
//            session.setAttribute("cart", cart);
//        }
//
//        Product product = productService.findByID(productId);
//        System.out.println("137 :" + productId);
//        if (product == null) {
//            return "redirect:/user/product";
//        }
//
//        Iterator<CartDetail> iterator = cart.getListCartDetail().iterator();
//        while (iterator.hasNext()) {
//            CartDetail detail = iterator.next();
//            System.out.println("165 "+ detail);
//            if (detail.getProduct().getId().equals(productId)) {
//                iterator.remove();
//                cartDetailService.delete(detail);
//            }
//        }
//
//        cartService.saveCart(cart);
//        return "redirect:/user/cart";
//    }
    @PostMapping("cartdetail/remove/{productId}")
    public String removeFromCart(@PathVariable String productId, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.findByAccountUsername(username);

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = cartService.findByUser(user);
            if (cart == null) {
                return "redirect:/user/products";
            }
            session.setAttribute("cart", cart);
        }

        Product product = productService.findByID(productId);
        if (product == null) {
            return "redirect:/user/product";
        }

        CartDetailPk cartDetailPk = new CartDetailPk(cart.getId(), productId);
        CartDetail detail = cartDetailService.findById(cartDetailPk);
        System.out.println("chi tiết giỏ hàng "+ detail);
        if (detail != null) {
            System.out.println("chi tiết xóa giỏ hàng "+ detail);
            cartDetailService.delete(detail);
            cart.getListCartDetail().remove(detail);
//            cartService.saveCart(cart);
            session.setAttribute("cart", cart);
        } else {
            System.out.println("CartDetail không tồn tại với id: " + cartDetailPk);
        }

        return "redirect:/user/cart";
    }

    @PostMapping("/cartdetail/update")
    @ResponseBody
    public ResponseEntity<String> updateCartDetail(@RequestBody CartUpdateRequest updateRequest) {
        String productId = updateRequest.getProductId();
        int quantity = updateRequest.getQuantity();
        String cartId = updateRequest.getCartId();
        System.out.println("má sản phẩm khi cập nhật số lượng" +productId+" " +quantity+" " +cartId);
        // Tìm CartDetail trong cơ sở dữ liệu
        CartDetail cartDetail = cartDetailService.findByProductIdAndCartId(productId,cartId);

        if (cartDetail != null) {
            cartDetail.setQuantity(quantity);
            cartDetailService.save(cartDetail); // Lưu cập nhật vào cơ sở dữ liệu

            return ResponseEntity.ok("Updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart detail not found");
        }
    }

}
