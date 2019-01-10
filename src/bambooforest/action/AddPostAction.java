package bambooforest.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bambooforest.model.MemberBean;
import bambooforest.model.PostBean;
import bambooforest.model.PostDBBean;

public class AddPostAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result;
		if (request.getMethod().equals("POST"))
			result = processPost(request, response);
		else
			result = processGet(request, response);
		return result;
	}

	public String processGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		MemberBean member = (MemberBean) session.getAttribute("member");

		if (member == null)
			return "/login.jsp";
		else
			return "/addPostForm.jsp";

	}

	public String processPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		MemberBean member = (MemberBean) session.getAttribute("member");
		String memberId= member.getMemberId();
		
		request.setCharacterEncoding("utf-8");
		String result = null;
		PostDBBean bean = PostDBBean.getInstance();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		PostBean post = new PostBean();
		post.setTitle(title);
		post.setContent(content);
		post.setMemberId(memberId);
		int x = bean.addPost(post);
		if(x > 0) {
			result = "/index.jsp";
		}else
			result = null;
		
		
		return result;

	}

}
