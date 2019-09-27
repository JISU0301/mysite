package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		UserVo updateUser = (UserVo) session.getAttribute("updateUser");
		if (updateUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		
		UserVo vo = new UserVo();
		vo.setNo(updateUser.getNo());
		vo.setName(name);
		vo.setGender(gender);
		
		if("".equals(password)) {
			vo.setPassword(updateUser.getPassword());
		} else {
			vo.setPassword(password);
		}
		
		new UserDao().update(vo);
		

		//WebUtils.redirect(request, response, request.getContextPath() + "/WEB-INF/views/user/update.jsp");
		WebUtils.redirect(request, response, request.getContextPath() + "/user?a=loginform");
	}

}
