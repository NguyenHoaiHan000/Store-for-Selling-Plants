package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.SupplierService;
import web.CayvaCaCanh.Service.UserService;
import web.CayvaCaCanh.model.User;
import web.CayvaCaCanh.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean updateUserPassword(User user, String currentPassword, String newPassword, String confirmNewPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getAccount().getPassword())) {
            return false; // Mật khẩu hiện tại không đúng
        }

        if (!newPassword.equals(confirmNewPassword)) {
            return false; // Mật khẩu mới không khớp
        }

        user.getAccount().setPassword(passwordEncoder.encode(newPassword));
        return true; // Cập nhật mật khẩu thành công
    }


    @Override
    public User findByAccountUsername(String username) {
        return userRepository.findByAccountUsername(username);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    //    tổng khách hàng
    @Override
    public long getTotalUsers() {
        return userRepository.count(); // Lấy tổng số khách hàng
    }


    //     DANH SÁCH KHÁCH HÀNG
    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
