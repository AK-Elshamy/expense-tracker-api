
package com.elshamy.expensetracker.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationConverter(
            CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwt.getSubject());

        return UsernamePasswordAuthenticationToken.authenticated(
                userDetails,
                jwt,
                userDetails.getAuthorities()
        );
    }
}
