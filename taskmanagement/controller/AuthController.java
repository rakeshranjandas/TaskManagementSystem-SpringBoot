package taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/token")
    public String token() {
        return "hi";
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
    }

}
