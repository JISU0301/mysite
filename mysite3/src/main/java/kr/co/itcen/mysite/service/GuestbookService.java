package kr.co.itcen.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;




@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao guestbookDao;

	public void delete(GuestbookVo vo) {
		guestbookDao.delete(vo);
	}

	public List<GuestbookVo> getList() {
		List<GuestbookVo> result = new ArrayList<GuestbookVo>();
		result=guestbookDao.getList();
		return result;
	}

	public void insertaddList(GuestbookVo vo) {
		guestbookDao.insert(vo);
		
	}
}
