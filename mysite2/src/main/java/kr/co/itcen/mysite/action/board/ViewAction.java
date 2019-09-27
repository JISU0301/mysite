package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long no = Long.parseLong(request.getParameter("no"));
		
		new BoardDao().update(no);
		
		BoardVo boardVo = new BoardDao().getList(no);
		request.setAttribute("boardVo",boardVo);
		
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath() + "/user?a=loginform" );
			return;
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath() + "/user?a=loginform" );
			return;
		}
		
		request.setAttribute("authUser", authUser);	
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");


	}

}
