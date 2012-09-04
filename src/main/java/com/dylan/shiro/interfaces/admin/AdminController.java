package com.dylan.shiro.interfaces.admin;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class AdminController {

	/**
	 * when login success,it always redirect to this method,just support GET method
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = GET)
	public String index() {
		return "index";
	}

	/**
	 * must supported any method
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/deny")
	public String deny() {
		return "deny";
	}

}
