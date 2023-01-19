package com.example.processador.config.jwt;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final ClienteRepository clienteRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String email;

            if (authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response );
                return;
            }
            jwt = authHeader.substring(7);
            email = jwtService.extractUsername(jwt);
        System.out.println("Email- jwtAuthenticationFilter  "+authHeader.substring(7));
        System.out.println("Email- email  "+jwtService.extractUsername(jwt));
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken
                                    (userDetails,
                                    null,
                                    userDetails.getAuthorities()
                                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);
    }
    private boolean sameCliente(String userNameNoToken, Integer IdClienteNaReq) {
        Optional<Cliente> byEmail = clienteRepository.findByEmail(userNameNoToken);

        if (byEmail.get().getId().equals(IdClienteNaReq)){
            return true;
        }
        return true;
    }
}

