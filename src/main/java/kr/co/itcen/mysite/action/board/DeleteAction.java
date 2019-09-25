package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String gNo = request.getParameter("gNo");
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		Long userNo = authUser.getNo();
		List<BoardVo> list = new BoardDao().getList();
		int count = 0;
		

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getgNo() == Integer.parseInt(gNo)) {
				count++;
			}
		}

		if (count > 1) {
			new BoardDao().update(Integer.parseInt(gNo),Long.parseLong(no));
			
		} else {
			BoardVo vo = new BoardVo();
			vo.setNo(Long.parseLong(no));
			vo.setUserNo(userNo);
			new BoardDao().delete(vo);
		}

		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=list");

	}

}
