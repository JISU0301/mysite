package kr.co.itcen.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.search.Search;
import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	
//	// 작성
//	public Boolean insert(BoardVo vo)  {
//		int count = sqlSession.insert("board.insert",vo);
//		System.out.println(vo);
//		return count == 1;
//	}
//
//	// 수정
//	public Boolean update(BoardVo vo) {
//		int count = sqlSession.insert("board.update",vo);
//		System.out.println(vo);
//		return count==1;
//	}
//
//	
//
//	// 삭제
//	public Boolean delete(Long no, String password) {
//		BoardVo vo= new BoardVo();
//		vo.setNo(no);
//		vo.setPassword(password);
//		int count=sqlSession.delete("delete",vo);
//		return count==1;
//	}

	// 게시글 리스트
	public List<BoardVo> getList(Search search) {
		List<BoardVo> list = sqlSession.selectList("board.getList",search);
		return list;
	}
	
	
	
	// 게시글 수 구하기
	public int totalCnt(String kwd) {
		int count = sqlSession.selectOne("board.getListCount", kwd);
		return count;
	}

	
	
	

//	// 게시글 번호 정보
//	public BoardVo get(Long no) {    //getnolist
//		return sqlSession.selectOne("board.getByNoList",no);
//	}
//	
//	
	
	

//	public BoardVo get(BoardVo vo) {
//		BoardVo result = sqlSession.selectOne("board.getBy)
//	}
	
//	public List<BoardVo> getList(String keyword, Paging pagination) {
//		List<BoardVo> result = new ArrayList<BoardVo>();
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		System.out.println("Dao kwd :" + keyword);
//
//		try {
//			connection = getConnection();
//			String sql = "select b.no as no, b.title, u.name, b.contents, b.hit, date_format(reg_date,'%Y-%m-%d %h:%i:%s') as reg_date, b.depth, b.g_no" +
//			        "       from user u, board b" +
//					"      where u.no = b.user_no" +
//			        "        and (b.title like ? or b.contents like ?)" +
//			        "   order by g_no desc, o_no asc" +
//			        "      limit ?, ?";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, "%" + ((keyword == null) ? "" : keyword) + "%");
//			pstmt.setString(2, "%" + ((keyword == null) ? "" : keyword) + "%");
//			pstmt.setInt(3, (pagination.getCurrentPage() - 1) * pagination.getListSize());
//			pstmt.setInt(4, pagination.getListSize());
//			
//			
//			rs = pstmt.executeQuery();
//
//			while(rs.next()) {
//
//				BoardVo boardVo = new BoardVo();
//				
//				boardVo.setNo(rs.getLong("no"));
//				boardVo.setTitle(rs.getString("title"));
//				boardVo.setUserName(rs.getString("name"));
//				boardVo.setContents(rs.getString("contents"));
//				boardVo.setHit(rs.getInt("hit"));
//				boardVo.setRegDate(rs.getString("reg_date"));
//				boardVo.setDepth(rs.getInt("depth"));
//				boardVo.setgNo(rs.getInt("g_no"));
//				
//				result.add(boardVo);
//
//			}
//
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//	
//	
//	

//	
//	// g_no, o_no, depth
//	public BoardVo select(Long no) {
//		BoardVo result = null;
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			connection = getConnection();
//
//			String sql = "select g_no, o_no, depth from board where no = ?";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setLong(1, no);
//
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				result = new BoardVo();
//
//				result.setgNo(rs.getInt("g_no"));
//				result.setoNo(rs.getInt("o_no"));
//				result.setDepth(rs.getInt("depth"));
//
//			}
//
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//
//	public Boolean insertBoard(BoardVo boardVo) {
//		Boolean result = false;
//
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			connection = getConnection();
//
//			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, 'i', ?)";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, boardVo.getTitle());
//			pstmt.setString(2, boardVo.getContents());
//			pstmt.setInt(3, boardVo.getgNo());
//			pstmt.setInt(4, boardVo.getoNo());
//			pstmt.setInt(5, boardVo.getDepth());
//			pstmt.setLong(6, boardVo.getUserNo());
//
//			int count = pstmt.executeUpdate();
//
//			result = (count == 1);
//
//			stmt = connection.createStatement();
//
//			rs = stmt.executeQuery("select last_insert_id()");
//			if (rs.next()) {
//				Long no = rs.getLong(1);
//				boardVo.setNo(no);
//			}
//
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (stmt != null) {
//					stmt.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//
//	// 게시글 답글
//	public Boolean update(Integer gNo, Integer oNo) {
//		Boolean result = false;
//
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			connection = getConnection();
//
//			String sql = "update board set o_no=o_no+1, where g_no = ? and o_no >= ?";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setInt(1, gNo);
//			pstmt.setInt(2, oNo);
//
//			int count = pstmt.executeUpdate();
//
//			result = (count == 1);
//
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//
//	public Boolean update(Long no) {
//		Boolean result = false;
//
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			connection = getConnection();
//
//			String sql = "update board set hit=hit+1 where no=?";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setLong(1, no);
//
//			int count = pstmt.executeUpdate();
//
//			result = (count == 1);
//
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//
//	private Connection getConnection() throws SQLException {
//		Connection connection = null;
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//
//			String url = "jdbc:mariadb://192.168.1.77:3307/webdb?characterEncoding=utf8";
//			connection = DriverManager.getConnection(url, "webdb", "webdb");
//		} catch (ClassNotFoundException e) {
//			System.out.println("Fail to Loading Driver:" + e);
//		}
//		return connection;
//	}

}
