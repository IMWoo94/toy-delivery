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
		// 요청 시 log 작성은 별도의 class 를 생성 해야 함으로 이후에 별도로 진행 예정

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
}
