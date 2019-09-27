package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 접근 제어(ACL)/////////////////////////////////////////////////
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath() + "/user?a=loginform");
			return;
		}
		////////////////////////////////////////////////////////////

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath() + "/user?a=loginform");
			return;
		}

		/////////////////////////////////////////

		UserVo userVo = new UserDao().get(authUser.getNo());
		
		if(userVo==null) {
			request.setAttribute("result", "fail");
			WebUtils.redirect(request, response, request.getContextPath() + "/user?a=loginform");
			return;
		}
		
		request.setAttribute("userVo", userVo);
		request.setAttribute("no", request.getParameter("no"));
		WebUtils.forward(request, response, "/WEB-INF/views/board/write.jsp");
		

	}
}
