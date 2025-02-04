package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.model.Account;
import web.CayvaCaCanh.repository.AccountRepository;
import web.CayvaCaCanh.Service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }


    @Override
    public void saveAccount(Account account) {
        account.setStatus(false);
        accountRepository.save(account);
    }
}
