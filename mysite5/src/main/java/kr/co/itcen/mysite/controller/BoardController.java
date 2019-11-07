package kr.co.itcen.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.search.Search;
import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String getList(Search search, Model model) {
		if(search.getPage() < 1) {
			search.setPage(1);
		}
		if(search.getKwd() == null || search.getKwd().length() == 0) {
			search.setKwd("");
		}
		
		Map<String, Object> map = boardService.getList(search);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("pagination", map.get("pagination"));
		
		return "board/list";
	}
	
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String write(HttpSession session) {
		if(session != null && session.getAttribute("authUser") !=null)
			return "/board/write";
		return "redirect:/board";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo,HttpSession session) {
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		vo.setUserNo(authUser.getNo());
		if(vo.getGroupNo() == null || vo.getOrderNo()==null || vo.getDepth() == null){
			boardService.insert(vo);
			
		}else {
			boardService.boardUpdate(vo);
			boardService.insert(vo);
		}
		return "redirect:/board";
	}
		
	
	
	
	/*
	 * @RequestMapping(value = "/write/{no}", method = RequestMethod.POST) public
	 * String write(@ModelAttribute BoardVo vo, @PathVariable("no") Long no,
	 * 
	 * @RequestParam("title") String title, @RequestParam("contents") String
	 * contents, HttpSession session, Model model){ // 접근 제어(ACL) if (session ==
	 * null) { return "user/login"; } UserVo authUser = (UserVo)
	 * session.getAttribute("authUser"); if(authUser == null) { return "user/login";
	 * }
	 * 
	 * UserVo userVo = userService.getUser(authUser.getNo());
	 * model.addAttribute("userVo", userVo);
	 * 
	 * if(no == null) { no = vo.getNo(); }
	 * 
	 * BoardVo boardVo = new BoardVo(); boardVo.setTitle(title);
	 * boardVo.setContents(contents); boardVo.setOrderNo(1); boardVo.setDepth(0);
	 * boardVo.setUserNo(authUser.getNo());
	 * 
	 * if(no == null) { //boardService.insert(boardVo); } else { boardVo.setNo(no);
	 * BoardVo selectBVo = boardService.select(no);
	 * 
	 * Integer groupNo = selectBVo.getGroupNo(); Integer orderNo =
	 * selectBVo.getOrderNo()+1; Integer depth = selectBVo.getDepth()+1;
	 * 
	 * boardVo.setGroupNo(groupNo); boardVo.setOrderNo(orderNo);
	 * boardVo.setDepth(depth); boardService.update(groupNo, orderNo);
	 * boardService.insertBoard(boardVo); }
	 * 
	 * return "redirect:/board"; }
	 * 
	 * @RequestMapping(value = ("/write/{no}"), method = RequestMethod.GET) public
	 * String write(@ModelAttribute BoardVo vo, @PathVariable("no") Long no,
	 * HttpSession session, Model model) { // 접근 제어(ACL) if (session == null) {
	 * return "user/login"; } UserVo authUser = (UserVo)
	 * session.getAttribute("authUser"); if(authUser == null) { return "user/login";
	 * }
	 * 
	 * model.addAttribute("no", no); return "/board/write"; }
	 */
	
	@RequestMapping(value = ("/view/{no}"), method = RequestMethod.GET)
	public String view(@PathVariable("no") Long no, HttpSession session, Model model) {
		
		boardService.hitUpdate(no); // 조회수 증가
		
		BoardVo boardVo = boardService.getView(no);
		model.addAttribute("boardVo", boardVo);
		
		if (session == null) {
			return "board/view";
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "board/view";
		}
		model.addAttribute("authUser", authUser);
		
		return "board/view";
	}
	
	@RequestMapping(value = ("/modify"), method = RequestMethod.GET)
	public String modify(@ModelAttribute BoardVo vo, Model model) {
		
		BoardVo boardVo = boardService.getView(vo.getNo());
		model.addAttribute("boardVo", boardVo);
		
		return "board/modify";
	}
	
	@RequestMapping(value = ("/modify"), method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo boardVo, HttpSession session, Model model) {
		if (session == null) {
			return "redirect:/board/list";
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/board/list";
		}
		
		if (authUser.getNo() != boardVo.getUserNo()) {
			return "redirect:/board/list";
		}
		
		boardService.boardUpdate(boardVo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = ("/delete/{no}"), method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "board/delete";
	}
	
	@RequestMapping(value = ("/delete"), method = RequestMethod.POST)
	public String delete(@ModelAttribute BoardVo vo, @RequestParam("text") String text, HttpSession session) {
		boardService.delete(vo);
		return "redirect:/guestbook";
	}
	
	
}