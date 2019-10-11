package pl.adamsiedlecki.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;
import pl.adamsiedlecki.spring.config.securityStuff.CustomRequestCache;
import pl.adamsiedlecki.spring.config.securityStuff.SecurityUtils;


@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:languages/polish.properties", encoding = "Windows-1250")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CommCryptUserDetailsService userDetailsService;

    @Autowired
    public void WebSecurityConfig(CommCryptUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and()
                .authorizeRequests()
                .antMatchers("/VAADIN/**", "/PUSH/**", "/UIDL/**", "/login", "/login/**", "/error/**", "/accessDenied/**", "/vaadinServlet/**").permitAll()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/message/**").permitAll()
                .antMatchers("/user-panel/**").hasAuthority("USER")
                .antMatchers("/control-panel").hasAuthority("OWNER")

                .and()
                .authorizeRequests()
                .anyRequest().hasAuthority("OWNER")
                .and()
                .formLogin()
                .permitAll();

    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                        "/VAADIN/**",
                        "/frontend/**",
                        "/images/**",
                        "/frontend-es5/**", "/frontend-es6/**"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

}
