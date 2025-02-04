package web.CayvaCaCanh.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.CayvaCaCanh.Service.UserService;
import web.CayvaCaCanh.model.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/user")
public class UserAccountDetail {

    @Autowired
    private UserService userService;

    @Autowired

    private PasswordEncoder passwordEncoder;

    @GetMapping("AccountDetail")
    public String getAccountPage( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userService.findByAccountUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("errorMessages", new HashMap<String, String>());
        return "user/AccountDetail/AccountDetail"; // Tên template Thymeleaf cho trang đánh giá
    }

    @PostMapping("/AccountDetail")
    public String updateAccount(
            @ModelAttribute User user,
            @ModelAttribute Account account,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword,
            Model model) {

        // Lấy thông tin người dùng từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // Tìm người dùng từ cơ sở dữ liệu
        User existingUser = userService.findByAccountUsername(username);

        if (existingUser == null) {
            model.addAttribute("errorMessage", "Người dùng không tồn tại.");
            return "redirect:/user/AccountDetail"; // Chuyển hướng về trang chi tiết tài khoản
        }
        Map<String, String> errorMessages = validateUserInformation(user, currentPassword, newPassword, confirmNewPassword, existingUser);
        if (!errorMessages.isEmpty()) {
            model.addAttribute("errorMessages", errorMessages);
            return "user/AccountDetail/AccountDetail";
        }


        // Cập nhật địa chỉ
        existingUser.setAddress(user.getAddress());
        existingUser.setName(user.getName());
        existingUser.setSdt(user.getSdt());
        existingUser.setSurname(user.getSurname());
        existingUser.setDayOfBirth(user.getDayOfBirth());
        existingUser.getAccount().setEmail(user.getAccount().getEmail());
        // Xử lý cập nhật mật khẩu
        boolean passwordUpdateSuccess = userService.updateUserPassword(existingUser, currentPassword, newPassword, confirmNewPassword);

        if (!passwordUpdateSuccess) {
            model.addAttribute("errorMessage", "Cập nhật mật khẩu không thành công. Vui lòng kiểm tra lại.");
            return "redirect:/user/AccountDetail"; // Chuyển hướng về trang chi tiết tài khoản
        }

        // Cập nhật thông tin người dùng
        userService.updateUser(existingUser);

        return "redirect:/account"; // Chuyển hướng về trang tài khoản sau khi cập nhật thành công
    }

    private Map<String, String> validateUserInformation(User user, String currentPassword, String newPassword, String confirmNewPassword, User existingUser) {
        Map<String, String> errorMessages = new HashMap<>();

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, existingUser.getAccount().getPassword())) {
            errorMessages.put("currentPassword", "Current password is incorrect");
        }

        // Kiểm tra sự khớp giữa mật khẩu mới và xác nhận mật khẩu mới
        if (!newPassword.equals(confirmNewPassword)) {
            errorMessages.put("confirmNewPassword", "New password and confirmation do not match");
        }

        // Kiểm tra địa chỉ
        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            errorMessages.put("address", "Address cannot be empty");
        }

        // Kiểm tra tên
        if (user.getName() == null || user.getName().isEmpty()) {
            errorMessages.put("name", "Name cannot be empty");
        }

        // Kiểm tra số điện thoại
        if (user.getSdt() == null || user.getSdt().length() != 10) {
            errorMessages.put("sdt", "Phone number must be exactly 10 digits");
        }

        // Kiểm tra surname
        if (user.getSurname() == null || user.getSurname().isEmpty()) {
            errorMessages.put("surname", "Surname cannot be empty");
        }

        return errorMessages;
    }
}
