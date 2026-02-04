package bsc_registration.feature.auth;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class LoginRateFilter extends OncePerRequestFilter {

	private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();


	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getRequestURI().equals("/api/auth/login")) {
			String ip = request.getRemoteAddr();
			Bucket bucket = buckets.computeIfAbsent(ip, this::newBucket);

			if (bucket.tryConsume(1)) {
				filterChain.doFilter(request, response);
			} else {
				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				response.getWriter().write("Too many login attempts. Try again later.");
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private Bucket newBucket(final String key) {
		final Refill refill = Refill.greedy(5, Duration.ofMinutes(1));
		final Bandwidth limit = Bandwidth.classic(5, refill);
		return Bucket.builder().addLimit(limit).build();
	}
}
