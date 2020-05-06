package forumService.forumService.Autentication.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // http.csrf().csrfTokenRepository(csrfTokenRepository());
        http
                // .csrf().disable()
                .anonymous().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                // .antMatchers(HttpMethod.GET, "/oauth/token").permitAll()
                .antMatchers(HttpMethod.GET, "/usuarios").authenticated().antMatchers(HttpMethod.GET, "/**")
                .authenticated();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

}