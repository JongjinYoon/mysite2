package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if (authUser !=null) {

			Long no = authUser.getNo();
			authUser = new UserDao().get(no);
			//request.setAttribute("authUser", authUser);
		}
//		Long userNo = authUser.getNo();
//		authUser = new UserDao().get(userNo);
//		request.setAttribute("authUser", authUser);
		
		String no = request.getParameter("no");
		String hit = request.getParameter("hit");
		request.setAttribute("no", no);
		request.setAttribute("hit", hit);
		request.setAttribute("authUser", authUser);
		new BoardDao().update(Long.parseLong(no),Integer.parseInt(hit));
		
		List<BoardVo> list = new BoardDao().getList();
		request.setAttribute("list", list);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
