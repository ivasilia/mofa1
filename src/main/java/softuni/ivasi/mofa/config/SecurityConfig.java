package softuni.ivasi.mofa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import softuni.ivasi.mofa.security.CurrentUserDetailsService;
import softuni.ivasi.mofa.security.CustomSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)    //  @PreAuthorize etc..
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CurrentUserDetailsService userDetailsService;
    private final CustomSuccessHandler authSuccessHandler;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          CurrentUserDetailsService userDetailsService,
                          CustomSuccessHandler authSuccessHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(this.userDetailsService).
                passwordEncoder(this.passwordEncoder);
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                authorizeRequests().
                antMatchers("/", "/login", "/register").permitAll().
                antMatchers("/admin/**").hasRole("ADMIN").
                antMatchers("/user/**").hasRole("USER").
                antMatchers("/curator/**").hasRole("CURATOR").
                and().
                formLogin().
                loginPage("/login").permitAll().
                loginProcessingUrl("/login").
                failureForwardUrl("/login-error").
                successForwardUrl("/index").
//                defaultSuccessUrl("/").
        successHandler(authSuccessHandler).
                usernameParameter("username").
                passwordParameter("password").
                and().
                logout().
                logoutRequestMatcher(new AntPathRequestMatcher("/_leaving")).
//                logoutUrl("/_leaving").
        logoutSuccessUrl("/login").
                invalidateHttpSession(true).
                deleteCookies("JSESSIONID").
                and().
                csrf().disable();
    }
}
