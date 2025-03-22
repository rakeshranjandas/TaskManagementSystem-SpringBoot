package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taskmanagement.request.RegisterRequest;
import taskmanagement.service.AccountsService;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        accountsService.register(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }


}
