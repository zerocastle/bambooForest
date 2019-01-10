package bambooforest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDBBean {

	private static MemberDBBean instance = new MemberDBBean();

	private MemberDBBean() {
		// TODO Auto-generated constructor stub
	}

	public static MemberDBBean getInstance() {
		return instance;
	}

	public static final int VALID = 0;
	public static final int INVALID_PASSWORD = 1;
	public static final int INVALID_USER = 2;
	public static final int ERROR = 100;

	private Connection getConnection() throws Exception {
		// TODO Auto-generated method stub

		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/jsp");

		return ds.getConnection();
	}

	public int memberCheck(String memberId, String password) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			String sql = "select * from member where memberId = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				String dbPassword = rs.getString("password");
				if (dbPassword.equals(password)) {
					return VALID;
				} else
					return INVALID_PASSWORD;
			} else {
				return INVALID_USER;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		return ERROR;

	}

	public MemberBean getMember(String memberId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			String sql = "select * from member where memberId = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				MemberBean member = new MemberBean();
				member.setMemberId(rs.getString("memberId"));
				member.setPassword(rs.getString("password"));
				return member;
			}else
				return null;

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		return null;

	}

}
