package forumService.forumService.Config;

import static forumService.forumService.Config.SecurityConstants.EXPIRATION_TIME;
import static forumService.forumService.Config.SecurityConstants.HEADER_STRING;
import static forumService.forumService.Config.SecurityConstants.SECRET;
import static forumService.forumService.Config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import forumService.forumService.Models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        try {
            final Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        final String email = ((User) authResult.getPrincipal()).getUsername();
        final String token = Jwts.builder().setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        response.addHeader("access-control-expose-headers", "Authorization");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

}