package com.dylan.shiro.interfaces.admin;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dylan.shiro.application.AuthorityService;
import com.dylan.shiro.application.RoleService;
import com.dylan.shiro.domain.Authority;
import com.dylan.shiro.domain.Role;
import com.youboy.core.orm.Page;

/**
 * @author LHacker
 *
 */
@Controller
@RequestMapping("/security/role")
public class SecurityRoleController{

	private static final String REDIRECT_LIST = "redirect:/security/role/list";

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = GET)
	public String list(Page<Role> page, Model model) {
		page = roleService.getPage(page);
		model.addAttribute(page);
		return "security/role/list";
	}

	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		List<Authority> authorities = authorityService.findAll();
		model.addAttribute(new Role()).addAttribute("authorities", authorities);
		return "security/role/form";
	}

	@RequestMapping(value = "/create", method = POST)
	public String create(Role role,HttpServletRequest request) {
		String [] authorityId = request.getParameterValues("authorityId");
		role.setAuthorities(authorityService.getAuthoritiesById(authorityId));
		roleService.save(role);
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Role role = roleService.get(new ObjectId(id));
		List<Authority> authorities = authorityService.findAll();
		model.addAttribute(role).addAttribute("authorities", authorities).addAttribute("_method", "PUT").addAttribute("status", "edit");
		return "security/role/form";
	}

	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(Role role, HttpServletRequest request) {
		String [] authorityId = request.getParameterValues("authorityId");
		role.setAuthorities(authorityService.getAuthoritiesById(authorityId));
		roleService.save(role);
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		roleService.delete(new ObjectId(id));
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/delete", method = DELETE)
	public String delete(HttpServletRequest request) {
		String[] items = request.getParameterValues("items");
		for (String item : items) {
			delete(item);
		}
		return REDIRECT_LIST;
	}
	@RequestMapping(value = "/check",method = POST)
	@ResponseBody
	public String checkUser(String name) throws IOException{
		if( roleService.existsByName(name)){
			return "false";
		}
		return "true";
	}

}
