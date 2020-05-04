package pl.bajerska.befindyourmeal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.bajerska.befindyourmeal.user.UserDetailsServiceImpl;
import pl.bajerska.befindyourmeal.user.UserService;
import pl.bajerska.befindyourmeal.user.UserSimpleUrlAuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    public SecurityConfig(final UserDetailsServiceImpl userDetailsService, final DataSource dataSource, final UserService userService) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public AuthenticationSuccessHandler userSimpleUrlAuthenticationSuccessHandler() {
        return new UserSimpleUrlAuthenticationSuccessHandler(userService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll()
                .antMatchers("/", "/main/**", "/add/**", "/register/**", "/login/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(userSimpleUrlAuthenticationSuccessHandler());

        http.csrf().disable();
        http.headers()
                .frameOptions()
                .sameOrigin();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
