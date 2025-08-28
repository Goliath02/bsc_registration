package bsc_registration.adapter;

import bsc_registration.domain.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final HandlerExceptionResolver handlerExceptionResolver;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {

			String token = null;

// 1. Check Header
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
			}

// 2. Check Cookie
			if (token == null) {
				if (request.getCookies() != null) {
					for (Cookie cookie : request.getCookies()) {
						if ("jwt".equals(cookie.getName())) {
							token = cookie.getValue();
							break;
						}
					}
				}
			}

			if (token == null) {
				throw new AuthenticationServiceException("JWT Token is empty");
			}
			System.out.println("Authorization Header: " + request.getHeader("Authorization"));
			String username = jwtService.extractUsername(token);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (username != null && authentication == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				System.out.printf("Authoriteis %s", userDetails.getAuthorities());

				if (jwtService.isTokenValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

		} catch (Exception exception) {
			handlerExceptionResolver.resolveException(request, response, null, exception);
		}
		filterChain.doFilter(request, response);
	}
}
