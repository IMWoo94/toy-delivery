package org.delivery.api.filter;

import java.io.IOException;

import org.delivery.api.custom.CachedBodyHttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Order(value = Ordered.HIGHEST_PRECEDENCE)
// @Component
// @WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class ContentCachingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		FilterChain filterChain) throws
		ServletException,
		IOException {
		System.out.println("IN  ContentCachingFilter ");
		CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(
			httpServletRequest);
		filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
	}
}
