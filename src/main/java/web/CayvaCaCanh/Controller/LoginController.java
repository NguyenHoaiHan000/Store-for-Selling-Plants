package web.CayvaCaCanh.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.CayvaCaCanh.Service.AccountService;
import web.CayvaCaCanh.Service.RoleService;
import web.CayvaCaCanh.Service.UserService;
import web.CayvaCaCanh.model.Account;
import web.CayvaCaCanh.model.Roles;
import web.CayvaCaCanh.model.User;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService rolesService;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }
        return "login"; // Thymeleaf view name (login.html)
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("userName") String username, @RequestParam("password") String password) {
        // Perform authentication logic here (e.g., check credentials against database)
        Account account = accountService.findByUsername(username);

        if (account != null && account.getPassword().equals(password) && ("ADMIN".equals(account.getRole().getName())|| "USER".equals(account.getRole().getName()))) {
            return "redirect:/admin/home"; // Chuyển hướng đến trang admin nếu đăng nhập thành công
        } else {
            return "redirect:/login?error"; // Chuyển hướng đến trang login với thông báo lỗi nếu không thành công
        }
    }


//    SIGN UP

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new Account());
        return "signup/signup"; // Thymeleaf view name (register.html)
    }


    @PostMapping("/register")
    public String processRegistration(@RequestParam("userName") String username,
                                      @RequestParam("password") String password,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      @RequestParam("email") String email,
                                      Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "signup/SignUp";
        }

        Account existingAccount = accountService.findByUsername(username);
        if (existingAccount != null) {
            model.addAttribute("error", "Username already exists!");
            return "signup/SignUp";
        }

        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPassword(passwordEncoder.encode(password));
        newAccount.setEmail(email);
        newAccount.setStatus(true);
        Roles userRole = rolesService.findById("USER"); // Giả sử mã của role là "USER"
        newAccount.setRole(userRole);


        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString().substring(0, 10)); // Tạo mã khách hàng tự động
        newUser.setAccount(newAccount);
        newUser.setName(username);
        accountService.saveAccount(newAccount);
        userService.saveUser(newUser);
        return "redirect:/login";
    }
}