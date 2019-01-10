package bambooforest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PostDBBean {

	private static PostDBBean instance = new PostDBBean();

	private PostDBBean() {
		// TODO Auto-generated constructor stub
	}

	public static PostDBBean getInstance() {
		return instance;
	}

	private Connection getConnection() {
		Context initCtx = null;
		Context envCtx;
		DataSource ds = null;
		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/jsp");
			return ds.getConnection();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 리스트 불러오기
	public ArrayList<PostBean> getList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PostBean> list = null;
		PostBean bean = null;
		try {
			String sql = "select * from post";
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<PostBean>();
			while (rs.next()) {
				bean = new PostBean();
				bean.setPostId(rs.getInt("postId"));
				bean.setTitle(rs.getString("title"));
				bean.setCreated(rs.getTimestamp("created"));
				bean.setContent(rs.getString("content"));
				bean.setMemberId(rs.getString("memberId"));
				list.add(bean);
			}

		} catch (Exception e) {
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
				list = null;

			}
		}

		return list;
	}

	// 상세보기

	public PostBean getPost(int postId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PostBean bean = null;
		try {
			String query = "select * from post where postId = ?";

			conn = this.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean = new PostBean();
				bean.setPostId(rs.getInt("postId"));
				bean.setTitle(rs.getString("title"));
				bean.setCreated(rs.getTimestamp("created"));
				bean.setContent(rs.getString("content"));
				bean.setMemberId(rs.getString("memberId"));

			} else {
				return null;
			}
		} catch (Exception e) {
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
		return bean;
	}

	// 글 등록

	public int addPost(PostBean post) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			String query = "insert into post (title,content,memberId) values(?,?,?)";
			conn = this.getConnection();
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, post.getTitle());

			pstmt.setString(2, post.getContent());
			pstmt.setString(3, post.getMemberId());
			x = pstmt.executeUpdate();

		} catch (Exception e) {
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
		return x;

	}

}
