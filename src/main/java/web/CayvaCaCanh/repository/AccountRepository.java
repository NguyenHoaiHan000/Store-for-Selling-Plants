package web.CayvaCaCanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.CayvaCaCanh.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByUsername(String username);
}
