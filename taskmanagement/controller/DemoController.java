package taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taskmanagement.entity.AccessToken;
import taskmanagement.repository.AccessTokenRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demo")
public class DemoController {
//
//    @Autowired
//    private JwtEncoder jwtEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @GetMapping("/hello")
//    public String hello(Authentication authentication) {
//        return "Hello, " + authentication.getName() +
//                ". Your authorities are: " + authentication.getAuthorities();
//    }
//
//    @PostMapping("/token")
//    public String token(HttpServletRequest request) {
//        System.out.println(request);
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null || !authHeader.startsWith("Basic ")) {
//            throw new BadCredentialsException("Full authentication is required");
//        }
//
//        String encoded = authHeader.substring(6);
//        String decoded = new String(Base64.getDecoder().decode(encoded));
//        String[] credentials = decoded.split(":", 2);
//
//        if (credentials.length != 2) {
//            throw new BadCredentialsException("Invalid authentication format");
//        }
//
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
//
//        Authentication authentication = authenticationManager.authenticate(authToken);
//
//        List<String> authorities = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
//                .subject(authentication.getName())
//                .issuedAt(Instant.now())
//                .expiresAt(Instant.now().plus(60, ChronoUnit.SECONDS))
//                .claim("scope", authorities)
//                .build();
//
//        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
//    }
}