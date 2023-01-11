package com.example.processador.config.security;


import com.example.processador.config.security.firewall.RequestRejectedExceptionFilter;
import com.example.processador.model.cliente.services.UsuarioDetalhesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

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
                .antMatchers("/h2-console/**","/index","/", "/about", "/css/**", "/webjars/**", "/signup/**","/images/**","/js/**","/sass/**","/webfonts/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login?error").permitAll().and().logout()
                .permitAll()
                .and().exceptionHandling().accessDeniedHandler(defaultAccessDeniedHandler)
                .and().headers().frameOptions().sameOrigin();

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
