package com.example.processador;

import com.example.processador.config.security.DefaultAccessDeniedHandler;
import com.example.processador.config.security.RequestRejectedExceptionFilter;
import com.example.processador.config.security.ThymeleafUrlAuthenticationSuccessHandler;
import com.example.processador.model.cliente.services.UsuarioDetalhesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {


    @Autowired
    private UsuarioDetalhesService usuarioDetalhesService;

    @Autowired
    private DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(usuarioDetalhesService).passwordEncoder(passwordEncoder());

        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/", "/about", "/css/**", "/webjars/**", "/signup/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login?error").permitAll().and().logout()
                .permitAll()
                .and().exceptionHandling().accessDeniedHandler(defaultAccessDeniedHandler);

        http.addFilterBefore(new RequestRejectedExceptionFilter(),
                ChannelProcessingFilter.class);

        http
                .sessionManagement()
                .enableSessionUrlRewriting(true);

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new ThymeleafUrlAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
