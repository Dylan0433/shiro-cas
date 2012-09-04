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

import com.dylan.shiro.application.RoleService;
import com.dylan.shiro.application.UserService;
import com.dylan.shiro.domain.Role;
import com.dylan.shiro.domain.User;
import com.dylan.shiro.interfaces.util.MD5HashUtils;
import com.youboy.core.orm.Page;

/**
 * @author LHacker
 * 
 */
@Controller
@RequestMapping("/security/user")
public class SecurityUserController {
	private static final String REDIRECT_LIST = "redirect:/security/user/list";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = GET)
	public String list(Page<User> page, Model model) {
		page = userService.getPage(page);
		model.addAttribute(page);
		return "security/user/list";
	}

	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		List<Role> roles = roleService.findAll();
		model.addAttribute(new User()).addAttribute("roles", roles);
		return "security/user/form";
	}

	@RequestMapping(value = "/create", method = POST)
	public String create(User user, HttpServletRequest request) {
		String[] roleId = request.getParameterValues("roleId");
		List<Role> roles = roleService.getRolesById(roleId);
		if (roles.isEmpty()) {
			addDefaultRole(roles);
		}
		user.setRoles(roles);
		String passwordAsMd5 = MD5HashUtils.asMD5(user.getPassword(), user.getUsername());
		user.setPassword(passwordAsMd5);
		userService.save(user);
		return REDIRECT_LIST;
	}

	/**
	 * 专为商铺后台创建用户时调用
	 * @param user
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/gl-create", method = POST)
	public void glCreate(User user, HttpServletRequest request) {
		create(user, request);
	}

	/**
	 * 添加一个默认的角色，该角色应该有最小的权限，只能查看
	 * 
	 * @param roles
	 */
	private void addDefaultRole(List<Role> roles) {
		Role role = roleService.getByName("user");
		if (null == role) {
			throw new RuntimeException("the db has not a role name 'user',please add a role name 'user' to the db,and add some permission to it");
		}
		roles.add(role);
	}

	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		User user = userService.findOne(new ObjectId(id));
		List<Role> roles = roleService.findAll();
		model.addAttribute("user", user).addAttribute("roles", roles).addAttribute("_method", "PUT").addAttribute("status", "edit");
		return "security/user/form";
	}

	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(User user, HttpServletRequest request) {
		String[] roleId = request.getParameterValues("roleId");
		user.setRoles(roleService.getRolesById(roleId));
		String passwordAsMd5 = MD5HashUtils.asMD5(user.getPassword(), user.getUsername());
		user.setPassword(passwordAsMd5);
		userService.update(user);
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		userService.delete(new ObjectId(id));
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
	/**
	 * 专为商铺后台删除用户时调用
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/gl-delete", method = POST)
	public void glDelete(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		User user = userService.getByName(userName);
		if(null != user){
			userService.delete(user.getId());
		}
	}

	@RequestMapping(value = "/check", method = POST)
	@ResponseBody
	public String checkUser(String username) throws IOException {
		if (userService.existsByUsername(username)) {
			return "false";
		}
		return "true";
	}

}
