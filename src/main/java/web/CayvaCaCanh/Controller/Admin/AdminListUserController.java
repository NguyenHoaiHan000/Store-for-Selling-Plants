package web.CayvaCaCanh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.CayvaCaCanh.Service.UserService;
import web.CayvaCaCanh.model.User;


@Controller
@RequestMapping("/admin/listUser")
public class AdminListUserController{

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        // Tạo đối tượng Pageable với trang hiện tại và kích thước trang
        Pageable pageable = PageRequest.of(page, size);

        // Lấy danh sách người dùng với phân trang từ service
        Page<User> usersPage = userService.findAllUsers(pageable);

        // Thêm các thuộc tính vào mô hình để gửi đến giao diện
        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("size", size);

        return "admin/listUser/list";
    }
}
