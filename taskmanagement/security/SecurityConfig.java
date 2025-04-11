package taskmanagement.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())  // Enabling HTTP Basic Authentication
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults())) // Enabling JWT authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll() // expose the /error endpoint
                        .requestMatchers("/actuator/shutdown").permitAll() // required for tests
                        .requestMatchers("/h2-console/**").permitAll() // expose H2 console
                        .requestMatchers(HttpMethod.POST, "/token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/accounts").permitAll()
                        .anyRequest().authenticated()) // All requests must be authenticated
                .csrf(AbstractHttpConfigurer::disable) // allow modifying requests from tests
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .sessionManagement(sessions ->
                        sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair keyPair) {
        return NimbusJwtDecoder
                .withPublicKey((RSAPublicKey) keyPair.getPublic())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
        JWK rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);

    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource)   {
        return new NimbusJwtEncoder(jwkSource);
    }



    // Seems like this has to be declared to support multiple types of authentication.
    //
    // It was working before, in Task #2, when there was only Basic Authentication.
    // That time Spring created automatically the beans for DaoAuthenticationProvider and AuthenticationManager.
    // But now, it isn't working.
    // May be due to presence of new AuthenticationProviders, like AccessTokenAuthenticationProviders,
    // it demands explicit declaration.
    //
    // -- UPDATE --
    // When I deleted the custom AuthenticationProvider(AccessTokenAuthenticationProvider), now it
    // works when I dont explicitly declare AuthenticationManager or DaoAuthenticationProvider
    /*
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
                                                               PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthProvider) {
        return new ProviderManager(List.of(tokenProvider, daoAuthProvider));
    }
    */
}