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

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		System.out.println(authUser);
		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		
		Long no = null;
		
		if(!"".equals(request.getParameter("no"))) {
			no=Long.parseLong(request.getParameter("no"));
		}
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContents(contents);
		boardVo.setoNo(1);
		boardVo.setDepth(0);
		boardVo.setUserNo(authUser.getNo());
		
		if(no==null) {
			new BoardDao().insert(boardVo);
		} else {
			boardVo.setNo(no);
			BoardVo selectBoardVo = new BoardDao().select(no);
			
			Integer gNo = selectBoardVo.getgNo();
			Integer oNo = selectBoardVo.getoNo()+1;
			Integer depth = selectBoardVo.getDepth()+1;
			
			boardVo.setgNo(gNo);
			boardVo.setoNo(oNo);
			boardVo.setDepth(depth);
			
			new BoardDao().insertBoard(boardVo);

		}
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=list");

	}

}
