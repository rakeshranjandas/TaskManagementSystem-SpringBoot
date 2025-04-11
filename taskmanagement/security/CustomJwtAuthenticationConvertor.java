package taskmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Purpose of this component is to load SecurityContext with Account details post JWT authentication.
 *
 * In default JWT authentication, `JWTAuthenticationToken` is saved in the SecurityContext.
 * So later we don't get much account information from the principal.
 *
 * This component converts the jwt token to UserNamePasswordAuthenticationToken that holds account details
*/
@Component
public class CustomJwtAuthenticationConvertor implements Converter<Jwt, AbstractAuthenticationToken> {

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject(); // or jwt.getClaim("preferred_username")
        UserDetails userDetails = accountDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                jwt, // can be null too
                userDetails.getAuthorities()
        );
    }
}
