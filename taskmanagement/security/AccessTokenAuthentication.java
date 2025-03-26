package taskmanagement.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class AccessTokenAuthentication implements Authentication {
    private final String token;
    private boolean authenticated = false;

    public AccessTokenAuthentication(String token) { this.token = token; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }

    @Override
    public Object getCredentials() { return token; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return "bearer access token"; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() { return "bearer access token"; }
}