package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징, 리스트 등등 알고리즘 추가
		String keyword = request.getParameter("kwd");
		String pageStr = request.getParameter("page");
		int page = Integer.parseInt((pageStr == null || pageStr.length() == 0) ? "1" : pageStr);
		
		BoardDao dao = new BoardDao();
		
		int totalCnt = dao.getCount(keyword); // 전체 게시글 수
		Paging pagination = new Paging(page, totalCnt, 3, 5);
		
		List<BoardVo> list = dao.getList(keyword, pagination);
		request.setAttribute("list", list);
		request.setAttribute("pagination", pagination);
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}