package bambooforest.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bambooforest.model.ReplyBean;
import bambooforest.model.ReplyDBBean;

public class AddCommentAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String comment = request.getParameter("comment");
		String memberId = request.getParameter("memberId");
		System.out.println(memberId);
		String strPostId = request.getParameter("postId");
		int postId = Integer.parseInt(strPostId);

		ReplyBean rb = new ReplyBean();
		rb.setComment(comment);
		rb.setMemberId(memberId);
		rb.setPostId(postId);

		ReplyDBBean db = ReplyDBBean.getInstance();
		ReplyBean result = db.addReply(rb);

		if (result != null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postId", result.getPostId());
			map.put("replyId", result.getReplyId());
			map.put("memberId", result.getMemberId());
			map.put("comment", result.getComment());
			map.put("created", result.getCreated().toString());

			JSONObject object = new JSONObject(map);
			String jsonText = object.toJSONString();
			return jsonText;
		}
		return null;
	}

}
