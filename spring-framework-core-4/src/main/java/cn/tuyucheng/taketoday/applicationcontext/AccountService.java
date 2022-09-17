package cn.tuyucheng.taketoday.applicationcontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageSource messageSource;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getAccountName() {
        return messageSource.getMessage("account.name", null, Locale.US);
    }
}