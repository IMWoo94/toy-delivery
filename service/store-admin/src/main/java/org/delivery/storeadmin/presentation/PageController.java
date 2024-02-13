package org.delivery.storeadmin.presentation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("")
@Slf4j
public class PageController {

	@RequestMapping(path = {"/", "/main"})
	public ModelAndView main() {
		SecurityContext context = SecurityContextHolder.getContext();
		Map<String, Object> authentication = new HashMap<>();
		authentication.put("authentication", context.getAuthentication());
		return new ModelAndView("main", authentication);
	}

	@RequestMapping("/order")
	public ModelAndView order() {
		return new ModelAndView("order/order");
	}

}
