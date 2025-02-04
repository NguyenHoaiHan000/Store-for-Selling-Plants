package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.Account;

public interface AccountService {
    Account findByUsername(String username);
    void saveAccount(Account account);
}
