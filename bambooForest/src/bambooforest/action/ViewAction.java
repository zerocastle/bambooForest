package bambooforest.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bambooforest.model.PostDBBean;
import bambooforest.model.ReplyDBBean;

public class ViewAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pId = request.getParameter("postId");
		if (pId != null) {
			int postId = Integer.parseInt(pId);
			PostDBBean db = PostDBBean.getInstance();
			ReplyDBBean reply = ReplyDBBean.getInstance();
			request.setAttribute("post", db.getPost(postId));
			request.setAttribute("reply", reply.getReplyList(postId));

		}

		return "/view.jsp";
	}

}
