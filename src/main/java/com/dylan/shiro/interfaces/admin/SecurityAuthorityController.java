package com.dylan.shiro.interfaces.admin;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dylan.shiro.application.AuthorityService;
import com.dylan.shiro.domain.Authority;
import com.youboy.core.orm.Page;

/**
 * @author LHacker
 *
 */
@Controller
@RequestMapping("/security/authority")
public class SecurityAuthorityController{

	private static final String REDIRECT_LIST = "redirect:/security/authority/list";

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/list", method = GET)
	public String list(Page<Authority> page, Model model) {
		page = authorityService.getPage(page);
		model.addAttribute(page);
		return "security/authority/list";
	}
	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		model.addAttribute(new Authority());
		return "security/authority/form";
	}

	@RequestMapping(value = "/create", method = POST)
	public String create(Authority entity) {
		authorityService.save(entity);
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Authority entity = authorityService.findOne(new ObjectId(id));
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return "security/authority/form";
	}

	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(Authority authority, HttpServletRequest request) {
		authorityService.update(authority);
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		authorityService.delete(new ObjectId(id));
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/delete", method = DELETE)
	public String delete(HttpServletRequest request) {
		String[] items = request.getParameterValues("items");
		for(String id : items){
			delete(id);
		}
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/check",method = POST)
	@ResponseBody
	public String checkUser(String name) throws IOException{
		if( authorityService.existsByName(name)){
			return "false";
		}
		return "true";
	}
}
