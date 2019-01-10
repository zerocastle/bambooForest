package bambooforest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ReplyDBBean {

	private static ReplyDBBean instance = new ReplyDBBean();

	private ReplyDBBean() {
		// TODO Auto-generated constructor stub
	}

	public static ReplyDBBean getInstance() {
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

	// 게시글 번호 에 대한 댓글 불러오기
	public ArrayList<ReplyBean> getReplyList(int postId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReplyBean bean = null;
		ArrayList<ReplyBean> list = null;
		try {
			String sql = "select * from reply where postId = ?";
			list = new ArrayList<ReplyBean>();
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ReplyBean();
				bean.setReplyId(rs.getInt("replyId"));
				bean.setComment(rs.getString("comment"));
				bean.setCreated(rs.getTimestamp("created"));
				bean.setPostId(rs.getInt("postId"));
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

	// 댓글 입력

	public ReplyBean addReply(ReplyBean reply) {
	      int result=0;
	      
	      Connection conn = getConnection();
	      PreparedStatement pstmt=null, pstmt2 = null;
	      ResultSet rs = null ,rsKey = null;
	      try {
	         String sql = "insert into reply (comment, postId, memberId) values (?,?,?)";
	         pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
	         pstmt.setString(1,reply.getComment());
	         pstmt.setInt(2, reply.getPostId());
	         pstmt.setString(3,reply.getMemberId());
	         result = pstmt.executeUpdate(); // rs에는 삽입된 id가 저장된다.
	         rsKey = pstmt.getGeneratedKeys();
	         if(rsKey.next()) {
	        	 result = rsKey.getInt("replyId");
	         }
	        		 
	         
	         String sql2 = "select * from reply where replyId = ?";
	         pstmt2 = conn.prepareStatement(sql2);
	         pstmt2.setInt(1,result);
	         rs = pstmt2.executeQuery();
	         if(rs.next()) {
	            reply.setCreated(rs.getTimestamp("created"));
	            reply.setReplyId(result);
	            return reply;
	         }
	         
	      }catch(SQLException e) {
	         e.printStackTrace();
	      }finally {
	         try {
	            if( pstmt!= null) pstmt.close();
	            if( pstmt2!= null) pstmt2.close();
	            if( rs!= null) rs.close();
	            if( conn!= null) conn.close();
	            }catch(SQLException ex) {
	               ex.printStackTrace();
	            }
	      }
	      System.out.println(reply.getComment() + " " );
	      return reply;
	      
	   }

}
