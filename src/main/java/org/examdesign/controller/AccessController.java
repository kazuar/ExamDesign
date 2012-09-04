package org.examdesign.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class AccessController {
	
	@RequestMapping("/login")
	public String login(Model model, @RequestParam(required=false) String message, @RequestParam(required=false) String messageType) {
		model.addAttribute("message", message);
		model.addAttribute("messageType", messageType);
		return "/login";
	}

	@RequestMapping(value = "/denied")
 	public String denied() {
		return "/denied";
	}

	@RequestMapping(value = "/login/failure")
 	public String loginFailure(Map<String, Object> map) {
		map.put("message", "Login Failure!");
		map.put("messageType", "error");
		return "redirect:/login";
		//String message = "Login Failure!";
		//return "redirect:/login?message="+message;
	}

	@RequestMapping(value = "/logout/success")
 	public String logoutSuccess(Map<String, Object> map) {
		map.put("message", "Logout Success!");
		map.put("messageType", "information");
		return "redirect:/login";
		//String message = "Logout Success!";
		//return "redirect:/login?message="+message;
	}
}
