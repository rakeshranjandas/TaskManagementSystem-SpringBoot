package taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taskmanagement.dto.TokenDTO;
import taskmanagement.security.JwtTokenGenerator;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @PostMapping("/token")
    public TokenDTO token() {
        // This endpoint is secured by Basic Authentication, which is working out-of-the box.
        return new TokenDTO(jwtTokenGenerator.getToken());
    }

}
