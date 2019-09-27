package kr.co.itcen.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.action.main.MainActionFactory;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	

	@Override
	public void init() throws ServletException {
		String configPath = this.getServletConfig().getInitParameter("config");
		System.out.println(configPath);
		super.init();//UTF-8이 아닐때
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		ActionFactory actionFactory = new MainActionFactory();
		Action action = actionFactory.getAction(actionName);
		
		action.execute(request, response);//메인액션팩토리 만들면서 변경된 코딩
		
		
		//WebUtils.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
