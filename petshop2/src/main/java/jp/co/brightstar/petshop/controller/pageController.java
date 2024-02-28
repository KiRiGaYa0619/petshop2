package jp.co.brightstar.petshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jp.co.brightstar.petshop.model.Orderinfo;
import jp.co.brightstar.petshop.model.customer;
import jp.co.brightstar.petshop.model.pet;
import jp.co.brightstar.petshop.service.loginService;
@Controller
public class pageController {
	@Autowired
	private loginService service;
	
	@GetMapping("/index")
	public String index(Model model, HttpSession session) {
	    // 检查用户是否已登录，并将登录状态添加到模型中
	    boolean isLoggedIn = session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn");
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    // 其他逻辑...
	    return "index";
	}

	
	@GetMapping("/contact")
	public String showContactForm() {
		return "contact";
	}
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}
	@GetMapping("/mypage")
	public ModelAndView mypage(Model model, HttpSession session,customer cus) {
	    // 检查用户是否已登录，并将登录状态添加到模型中
	    boolean isLoggedIn = session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn");
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    ModelAndView mav = new ModelAndView();
	    String userid = (String) session.getAttribute("userid");
	    customer customer = service.findCustomerById(userid);
	    pet pet = service.findPetById(userid);
	    mav.addObject("customer", customer);
	    mav.addObject("pet", pet);
	    mav.setViewName("mypage");
	    return mav;
	    
	}
	@GetMapping("/booking1")
	public String bookForm(Model model) {
		Orderinfo order = new Orderinfo();
		model.addAttribute("order", order);
	    return "booking1";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    // 将会话中的 "isLoggedIn" 属性移除，表示用户已退出登录
	    session.removeAttribute("isLoggedIn");
	    session.removeAttribute("msg");
	    // 其他清理操作...
	    
	    // 重定向到登录页面或其他页面
	    return "redirect:/index"; // 假设登录页面的路径为 "/login"
	}
	@GetMapping("/about")
	public String about(Model model, HttpSession session) {
	    // 检查用户是否已登录，并将登录状态添加到模型中
	    boolean isLoggedIn = session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn");
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    // 其他逻辑...
	    return "about";
	}
	@GetMapping("/service")
	public String service(Model model, HttpSession session) {
	    // 检查用户是否已登录，并将登录状态添加到模型中
	    boolean isLoggedIn = session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn");
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    // 其他逻辑...
	    return "service";
	}

}
