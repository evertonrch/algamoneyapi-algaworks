package com.algamoney.api.token;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Stream;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if ("/oauth/token".equalsIgnoreCase(request.getRequestURI())
            && "refresh_token".equals(request.getParameter("grant_type"))
            && request.getCookies() != null) {

            String refreshToken = Stream.of(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("refreshToken"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

            request = new CustomServletRequestWrapper(request, refreshToken);
        }
        filterChain.doFilter(request, servletResponse);
    }
}
