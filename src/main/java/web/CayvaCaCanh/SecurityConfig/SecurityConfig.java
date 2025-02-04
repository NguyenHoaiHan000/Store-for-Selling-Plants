package web.CayvaCaCanh.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import web.CayvaCaCanh.Service.Ipml.CustomDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(new CustomDetailsService()) // Thay thế CustomDetailsService bằng implementation thực tế của bạn
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(cscf -> cscf.disable()).authorizeRequests((auth) -> auth
                        .requestMatchers("/*").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAuthority("USER")
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/login")
                        .usernameParameter("userName")
                        .passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler())).
                logout(logout -> logout.
                        logoutUrl("/logout").logoutSuccessUrl("/login")                       );


        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = "/login?error";
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                redirectUrl = "/admin/home";
            } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
                redirectUrl = "/user/home";
            }
            response.sendRedirect(redirectUrl);
        };
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomize() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/signup/assets/**", "/fe/**", "/assets/**");
    }
}
