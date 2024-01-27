package org.delivery.api.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggerFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		var req = new ContentCachingRequestWrapper((HttpServletRequest)request);
		var res = new ContentCachingResponseWrapper((HttpServletResponse)response);

		// 캐싱된 요청과 응답을 넘겨 준다.
		chain.doFilter(req, res);

		// 응답 시에 요청, 응답 log
		// Request 정보
		var headerNames = req.getHeaderNames();
		var headerValues = new StringBuilder();

		headerNames.asIterator().forEachRemaining(
			headerKey -> {
				var headerValue = req.getHeader(headerKey);

				headerValues.append("[")
					.append(headerKey)
					.append(" : ")
					.append(headerValue)
					.append("] ");
			}
		);

		var requestBody = new String(req.getContentAsByteArray());
		var uri = req.getRequestURI();
		var method = req.getMethod();
		log.info(">>>>> uri : {}, method : {}", uri, method);
		log.info(">>>>> header : {}", headerValues);
		log.info(">>>>> body : {}", requestBody);

		// Response 정보
		var responseHeaderValues = new StringBuilder();

		res.getHeaderNames().forEach(
			headerKey -> {
				var headerValue = res.getHeader(headerKey);

				responseHeaderValues.append("[")
					.append(headerKey)
					.append(" : ")
					.append(headerValue)
					.append("] ");
			}
		);

		var responseBody = new String(res.getContentAsByteArray());
		log.info("<<<<< uri : {}, method : {}", uri, method);
		log.info("<<<<< header : {}", responseHeaderValues);
		log.info("<<<<< body : {}", responseBody);

		// 본문 내용을 읽었기 때문에 다시 캐싱에 저장된 내용으로 다시 copy 할 수 있도록 꼭 불러줄 것.
		// 만약, copy 를 하지 않으면 응답 정보가 비어서 넘어갈 것이다.
		res.copyBodyToResponse();
	}

	/**
	 * CustomFilter 를 생성하여 ContentCachingRequestWrapper 의 제한 적인 이슈도 해결
	 @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
	 IOException,
	 ServletException {

	 // ContentCachingFilter 에 의해서 warpping 되어 넘어옴
	 var req = (HttpServletRequest)request;
	 var res = new ContentCachingResponseWrapper((HttpServletResponse)response);

	 // Filter 에서 본문 내용을 읽었지만, Controller 단에서도 또 읽을 수 있다.
	 // 그 비밀은 Custom 한 ContentCachingFilter 에 의해서 request 객체가 캐싱을 사용할 수 있는 warpping 된 객체이기 때문이다.
	 BufferedReader reader = req.getReader();
	 reader.lines().forEach(
	 line -> {
	 log.info(line);
	 }
	 );

	 // 캐싱된 요청과 응답을 넘겨 준다.
	 chain.doFilter(req, res);

	 var uri = req.getRequestURI();
	 var method = req.getMethod();

	 // Response 정보
	 var responseHeaderValues = new StringBuilder();

	 res.getHeaderNames().forEach(
	 headerKey -> {
	 var headerValue = res.getHeader(headerKey);

	 responseHeaderValues.append("[")
	 .append(headerKey)
	 .append(" : ")
	 .append(headerValue)
	 .append("] ");
	 }
	 );

	 var responseBody = new String(res.getContentAsByteArray());
	 log.info("<<<<< uri : {}, method : {}", uri, method);
	 log.info("<<<<< header : {}", responseHeaderValues);
	 log.info("<<<<< body : {}", responseBody);

	 // 본문 내용을 읽었기 때문에 다시 캐싱에 저장된 내용으로 다시 copy 할 수 있도록 꼭 불러줄 것.
	 // 만약, copy 를 하지 않으면 응답 정보가 비어서 넘어갈 것이다.
	 res.copyBodyToResponse();
	 }
	 */
}
