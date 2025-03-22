package taskmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanagement.entity.Account;
import taskmanagement.exception.UserNameNotUniqueException;
import taskmanagement.repository.AccountsRepository;
import taskmanagement.request.RegisterRequest;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest registerRequest) {

        if (accountsRepository.findByUsernameIgnoreCase(registerRequest.getEmail()).isPresent()) {
            throw new UserNameNotUniqueException();
        }

        Account account = new Account();
        account.setUsername(registerRequest.getEmail());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        accountsRepository.save(account);
    }

}
