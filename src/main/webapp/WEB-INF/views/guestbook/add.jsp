<%@page import="kr.co.itcen.mysite.vo.GuestbookVo"%>
<%@page import="kr.co.itcen.mysite.dao.GuestbookDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String contents = request.getParameter("contents");

	GuestbookVo vo = new GuestbookVo();
	vo.setName(name);
	vo.setPassword(password);
	vo.setContents(contents);
	
	new GuestbookDao().insert(vo);
	response.sendRedirect(request.getContextPath());	
%>