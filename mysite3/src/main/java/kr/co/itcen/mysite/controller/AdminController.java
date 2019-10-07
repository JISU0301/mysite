package kr.co.itcen.mysite.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.mysite.vo.UserVo;

@Auth("ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping("")
	public String main(@AuthUser UserVo authUser, Model model) {
		return "admin/main";
	}
	
	
	
	@RequestMapping("/guestbook")
	public String guestbook(@AuthUser GuestbookVo guestbook, Model model) {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
