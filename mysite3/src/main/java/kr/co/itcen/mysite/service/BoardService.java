package kr.co.itcen.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public void insert(BoardVo vo) {
		boardDao.insert(vo);
	}
	
	public void update(BoardVo vo) {
		boardDao.update(vo);
	}
	
	public void delete(BoardVo vo) {
		boardDao.delete(vo.getNo(), vo.getPassword());
	}
	
	public List<BoardVo> getList() {
		List<BoardVo> result = new ArrayList<BoardVo>();
		result=boardDao.getList();
		return result;
	}
}
