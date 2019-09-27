package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.action.board.Paging;
import kr.co.itcen.mysite.vo.BoardVo;



public class BoardDao {
	//작성
	public Boolean insert(BoardVo boardvo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "insert into board(no, title, contents, hit, reg_date, g_no, o_no, depth, user_no)" +
					" values(null, ?, ?, 0, now(), (select ifnull(max(b.g_no)+1,1) from board b), 1, 0, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, boardvo.getTitle());
			pstmt.setString(2, boardvo.getContents());
			pstmt.setLong(3, boardvo.getUserNo());
			int count = pstmt.executeUpdate();

			result = (count == 1); 

			stmt = connection.createStatement();
			// mysql에만 있는 함수
			rs = stmt.executeQuery("select last_insert_id()");
			if(rs.next()) {
				Long no = rs.getLong(1);
				boardvo.setNo(no);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}



	//수정
	public Boolean update(BoardVo boardVo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "update board set title = ?, contents = ?, reg_date = now() where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getNo());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//삭제
	public void delete(BoardVo boardVo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = "delete from board where no=?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, boardVo.getNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Boolean update(int gNo, Long no) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "update board set title = '삭제된 게시물입니다.' where (select count(*) from (select * from board where g_no=?) a) > 1 and no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, gNo);
			pstmt.setLong(2, no);

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	//게시글 리스트
	public List<BoardVo> getList() {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select b.no as no, b.title, u.name, b.contents, b.hit, date_format(reg_date,'%Y-%m-%d %h:%i:%s') as reg_date, b.depth" +
					"       from user u, board b" +
					"      where u.no = b.user_no" +
					"   order by b.g_no desc, b.o_no asc";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				BoardVo boardVo = new BoardVo();

				boardVo.setNo(rs.getLong("no"));
				boardVo.setTitle(rs.getString("title"));
				boardVo.setUserName(rs.getString("name"));
				boardVo.setContents(rs.getString("contents"));
				boardVo.setHit(rs.getInt("hit"));
				boardVo.setRegDate(rs.getString("reg_date"));
				boardVo.setDepth(rs.getInt("depth"));

				result.add(boardVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//게시글 번호 정보
	public BoardVo getList(Long no) {
		BoardVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select u.no as userno, b.no as no, b.title, b.contents" +
					"       from user u, board b" +
					"      where u.no = b.user_no" +
					"        and b.no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = new BoardVo();

				result.setTitle(rs.getString("title"));
				result.setContents(rs.getString("contents"));
				result.setUserNo(rs.getLong("userno"));
				result.setNo(rs.getLong("no"));

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<BoardVo> getList(String keyword, Paging pagination) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("Dao kwd :" + keyword);

		try {
			connection = getConnection();
			String sql = "select b.no as no, b.title, u.name, b.contents, b.hit, date_format(reg_date,'%Y-%m-%d %h:%i:%s') as reg_date, b.depth, b.g_no" +
			        "       from user u, board b" +
					"      where u.no = b.user_no" +
			        "        and (b.title like ? or b.contents like ?)" +
			        "   order by g_no desc, o_no asc" +
			        "      limit ?, ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + ((keyword == null) ? "" : keyword) + "%");
			pstmt.setString(2, "%" + ((keyword == null) ? "" : keyword) + "%");
			pstmt.setInt(3, (pagination.getCurrentPage() - 1) * pagination.getListSize());
			pstmt.setInt(4, pagination.getListSize());
			
			
			rs = pstmt.executeQuery();

			while(rs.next()) {

				BoardVo boardVo = new BoardVo();
				
				boardVo.setNo(rs.getLong("no"));
				boardVo.setTitle(rs.getString("title"));
				boardVo.setUserName(rs.getString("name"));
				boardVo.setContents(rs.getString("contents"));
				boardVo.setHit(rs.getInt("hit"));
				boardVo.setRegDate(rs.getString("reg_date"));
				boardVo.setDepth(rs.getInt("depth"));
				boardVo.setgNo(rs.getInt("g_no"));
				
				result.add(boardVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int getCount(String kwd) {
		int count = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			
			String sql = "select count(*) as 'cnt'" +
			        "       from user u, board b" +
					"      where u.no = b.user_no" +
			        "        and (b.title like ? or b.contents like ?)" +
			        "   order by g_no desc, o_no asc";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + ((kwd == null) ? "" : kwd) + "%");
			pstmt.setString(2, "%" + ((kwd == null) ? "" : kwd) + "%");
			
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("cnt");
			}
						
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	//g_no, o_no, depth
	public BoardVo select(Long no) {
		BoardVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select g_no, o_no, depth from board where no = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = new BoardVo();

				result.setgNo(rs.getInt("g_no"));
				result.setoNo(rs.getInt("o_no"));
				result.setDepth(rs.getInt("depth"));

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//답글쓰기
	public Boolean upBoard(BoardVo boardVo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;


		try {
			connection = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, 'i', ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setInt(3, boardVo.getgNo());
			pstmt.setInt(4, boardVo.getoNo());
			pstmt.setInt(5, boardVo.getDepth());
			pstmt.setLong(6, boardVo.getUserNo());

			int count = pstmt.executeUpdate();

			result = (count == 1);

			stmt = connection.createStatement();

			rs = stmt.executeQuery("select last_insert_id()");
			if (rs.next()) {
				Long no = rs.getLong(1);
				boardVo.setNo(no);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	//게시글 답글
	public Boolean insertBoard(BoardVo boardVo) {
		Boolean result = false;
		Statement stmt=null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "update board set o_no=o_no+1 where g_no = ? and o_no >= ?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, boardVo.getgNo());
			pstmt.setInt(2, boardVo.getoNo());


			//				int count = pstmt.executeUpdate();
			//
			//				result = (count == 1);


			String sql1 =
					"insert into board(no, title, contents, hit, reg_date, g_no, o_no, depth, user_no)"
							+ " values(null, ?, ?, 0, now(), ?, ?, ?, ?)";
			pstmt=connection.prepareStatement(sql1); 
			pstmt.setString(1,boardVo.getTitle()); 
			pstmt.setString(2, boardVo.getContents());
			pstmt.setInt(3, boardVo.getgNo()); 
			pstmt.setInt(4, boardVo.getoNo());
			pstmt.setInt(5, boardVo.getDepth()); 
			pstmt.setLong(6, boardVo.getUserNo());

			int count = pstmt.executeUpdate(); result = (count == 1);

			stmt = connection.createStatement(); rs =
					stmt.executeQuery("select last_insert_id()");


		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	public Boolean update(Long no) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "update board set hit=hit+1 where no=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	private Connection getConnection() throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.1.77:3307/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch(ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		} 
		return connection;
	}



	



	


}

