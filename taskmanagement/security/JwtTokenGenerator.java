package taskmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequestScope
public class JwtTokenGenerator {

    @Autowired
    private JwtEncoder jwtEncoder;

    public String getToken() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .claim("scope", authorities)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(180, ChronoUnit.SECONDS))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

}
