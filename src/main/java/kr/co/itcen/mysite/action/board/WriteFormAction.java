package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근 제어(ACL)
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			//여기다 로그인 하고 글쓰라고 하는 페이지
			return;
		}

		Long no = authUser.getNo();
		authUser = new UserDao().get(no);
		String gNo = request.getParameter("gNo");
		String oNo = request.getParameter("oNo");
		String depth = request.getParameter("depth");

		request.setAttribute("authUser", authUser);
		request.setAttribute("gNo", gNo);
		request.setAttribute("oNo", oNo);
		request.setAttribute("depth", depth);
		WebUtils.forward(request, response, "/WEB-INF/views/board/write.jsp");

	}

}
