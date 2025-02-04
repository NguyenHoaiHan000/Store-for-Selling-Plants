package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.CayvaCaCanh.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u JOIN FETCH u.account a WHERE a.username = :username")
    User findByAccountUsername(@Param("username") String username);
}