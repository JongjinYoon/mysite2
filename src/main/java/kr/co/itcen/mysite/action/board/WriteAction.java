package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String gNo = request.getParameter("gNo");
		String oNo = request.getParameter("oNo");
		String depth = request.getParameter("depth");
		Long no = authUser.getNo();
		BoardVo vo = new BoardVo();
		
		if (gNo != "" && oNo != "" && depth != "") {
			
			new BoardDao().update(Integer.parseInt(gNo),Integer.parseInt(oNo));
		
			vo.setTitle(title);
			vo.setContent(content);
			vo.setgNo(Integer.parseInt(gNo));
			vo.setoNo(Integer.parseInt(oNo)+1);
			vo.setDepth(Integer.parseInt(depth)+1);
			vo.setUserNo(no);
			
			new BoardDao().commentInsert(vo);
			
		} else {
			
			vo.setTitle(title);
			vo.setContent(content);
			vo.setUserNo(no);

			new BoardDao().insert(vo);
		}

		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=list");
	}
}
