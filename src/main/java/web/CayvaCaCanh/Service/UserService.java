package web.CayvaCaCanh.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.CayvaCaCanh.model.User;

public interface UserService {
    User  findByAccountUsername(String username);
    boolean updateUserPassword(User user, String currentPassword, String newPassword, String confirmNewPassword);
    Page<User> findAllUsers(Pageable pageable);
    void updateUser(User user);
    void saveUser(User user);
    long getTotalUsers();
}
