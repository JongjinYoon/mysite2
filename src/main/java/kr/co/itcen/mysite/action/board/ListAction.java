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

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser !=null) {

			Long no = authUser.getNo();
			authUser = new UserDao().get(no);
			request.setAttribute("authUser", authUser);
		}
		
		String page = request.getParameter("page");
		int countPage = 5;
		int curPage;
		int lastPage;

		if (page == null) {
			curPage = 1;
		} else {
			curPage = Integer.parseInt(page);
		}

		int startPage = ((curPage - 1) / countPage) * countPage + 1;
		int endPage = startPage + countPage - 1;

		int blockNum = 0;

		blockNum = (int) Math.floor((curPage - 1) / countPage);
		int blockStartNum = (countPage * blockNum) + 1;
		int blockLastNum = blockStartNum + (countPage - 1);

		BoardDao bd = new BoardDao();
		int total = bd.getCount();

		if (total % countPage == 0) {
			lastPage = (int) Math.floor(total / countPage);
		} else {
			lastPage = (int) Math.floor(total / countPage) + 1;
		}

		List<BoardVo> list = new BoardDao().getList((curPage - 1) * 5);
		request.setAttribute("list", list);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("blockStartNum", blockStartNum);
		request.setAttribute("blockLastNum", blockLastNum);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("curPage", curPage);

//		List<BoardVo> list = new BoardDao().getList();
//		request.setAttribute("list", list);

		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}

}
