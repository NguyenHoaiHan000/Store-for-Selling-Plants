package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.AccountService;
import web.CayvaCaCanh.Service.CustomUserDetails;
import web.CayvaCaCanh.model.Account;
import web.CayvaCaCanh.model.Roles;
import web.CayvaCaCanh.model.Staffs;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomDetailsService implements UserDetailsService {

    private AccountService accountService;

    @Autowired
    public CustomDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    public CustomDetailsService() {

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username);
        }

        Staffs staff = account.getStaff(); // Lấy thông tin Staffs từ Account

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Roles role = account.getRole();
        if (role != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new CustomUserDetails(account, staff, grantedAuthorities);
    }
}
