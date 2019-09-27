package kr.co.itcen.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.repository.Paging;
import kr.co.itcen.mysite.search.Search;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

//	public void insert(BoardVo vo) {
//		boardDao.insert(vo);
//	}
//	
//	public void update(BoardVo vo) {
//		boardDao.update(vo);
//	}
//	
//	public void delete(BoardVo vo) {
//		boardDao.delete(vo.getNo(), vo.getPassword());
//	}

	public Map<String, Object> getList(Search search) {

		int totalCnt = boardDao.totalCnt(search.getKwd()); // 전체 게시글 수 구하기

		Paging pagination = new Paging(search.getPage(), totalCnt, 5, 5);

		search.setPagination(pagination);
		search.setStNo((pagination.getCurrentPage() - 1) * pagination.getListSize());
		search.setEndNo(pagination.getListSize());
		List<BoardVo> list = boardDao.getList(search);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pagination", pagination);

		return map;

//	public BoardVo getinfo(BoardVo vo) {
//		return boardDao.get(no);
//		
//	}
//	

	}
}
