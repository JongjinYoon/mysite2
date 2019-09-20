package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

public class BoardDao {
	public void delete(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			String sql = " delete" + "   from board" + "  where no = ?" + "    and user_no= ?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			pstmt.setLong(2, vo.getUserNo());

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

	public Boolean insert(BoardVo vo) {
		Boolean result = false;

		Connection connection = null;
		PreparedStatement pstmt = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			// 일단 조회수 0으로 설정
			String sql = "insert into board " + "   values(null, " + "         ?, ?, 0, now(),"
					+ "			(SELECT IFNULL(MAX(g_no) + 1, 1) FROM board a)," + "			1,0,"
					+ "			(select no from user where no = ?) )";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);

			stmt = connection.createStatement();
			rs = stmt.executeQuery("select last_insert_id()");
			if (rs.next()) {
				Long no = rs.getLong(1);
				vo.setNo(no);
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

	public List<BoardVo> getList() {
		List<BoardVo> result = new ArrayList<BoardVo>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select a.no, title, b.name, hit, reg_date, b.no, a.contents " + "     from board a, user b "
					+ "    where a.user_no = b.no " + " order by reg_date desc ";
			pstmt = connection.prepareStatement(sql);
			// pstmt.setString(1, vo.getTitle());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long userNo = rs.getLong(6);
				String content = rs.getString(7);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setUserNo(userNo);
				vo.setContent(content);

				result.add(vo);
			}
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

	public List<BoardVo> getList(int page) {
		List<BoardVo> result = new ArrayList<BoardVo>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select a.no, title, b.name, hit, reg_date, b.no, a.contents " + "     from board a, user b "
					+ "    where a.user_no = b.no " + " order by reg_date desc " + "    limit ?,5";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, page);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long userNo = rs.getLong(6);
				String content = rs.getString(7);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setUserNo(userNo);
				vo.setContent(content);

				result.add(vo);
			}
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

			String url = "jdbc:mariadb://192.168.1.70:3307/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}

		return connection;
	}

	public Boolean update(BoardVo vo) {
		Connection connection = null;
		Boolean result = false;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			String sql = "update board set title = ?, contents = ? where no=?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
		return result;

	}

	public int getCount() {
		int result = 0;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select count(*) " 
					+ "		from board " 
					+ " order by reg_date";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
			int count = rs.getInt(1);
			result = count;
			}

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

}
