package com.example.processador.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestRejectedExceptionFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            RequestRejectedException requestRejectedException = (RequestRejectedException) servletRequest.getAttribute("isNormalized");
            if (Objects.nonNull(requestRejectedException)) {
                throw requestRejectedException;
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (RequestRejectedException requestRejectedException) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            log
                    .error(
                            "request_rejected: remote={}, user_agent={}, request_url={}",
                            httpServletRequest.getRemoteHost(),
                            httpServletRequest.getHeader(HttpHeaders.USER_AGENT),
                            httpServletRequest.getRequestURL(),
                            requestRejectedException
                    );

            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
