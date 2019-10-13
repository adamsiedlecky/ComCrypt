package pl.adamsiedlecki.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.adamsiedlecki.spring.config.securityStuff.CommCryptUserDetailsService;
import pl.adamsiedlecki.spring.config.securityStuff.CustomRequestCache;
import pl.adamsiedlecki.spring.config.securityStuff.MySimpleUrlAuthenticationSuccessHandler;
import pl.adamsiedlecki.spring.config.securityStuff.SecurityUtils;


@Configuration
@EnableWebSecurity
@PropertySource(value = "classpath:languages/polish.properties", encoding = "Windows-1250")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CommCryptUserDetailsService userDetailsService;
    private Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error"; //
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

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
                .successHandler(new MySimpleUrlAuthenticationSuccessHandler())
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

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        log.info("Inside AuthenticationSuccessHandler bean method. It probably means that bean is created.");
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

}
