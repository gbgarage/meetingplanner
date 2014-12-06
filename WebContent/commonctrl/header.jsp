<%-- <%@page import="ctrip.system.model.User"%> --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <% --%>
// User u = (User) session.getAttribute("user");
// if (u == null) {
// 	response.sendRedirect("login.jsp");
// 	return;
// }
<%-- %> --%>
<h1 id="pubglobal_header">
	<!-- created by 张海云 2013 -->
	<span class="topbar">
	<a href="index.jsp">新版入口</a>
	<a href="logout.jsp">注销</a>
    <a href="userinfo.jsp"><%=u.getUser_name() %></a>	
	</span>
	<strong>挡板数据管理系统</strong>
</h1>
<ul class="policy_nav01">
<li id="baffleDataManageJsp" class="current"><a href="baffleDataManage.jsp" style="text-decoration:none;" >挡板数据管理</a></li>
<li id="baffleUseManageJsp" class=""><a href="baffleConfigManage.jsp" style="text-decoration:none;" >挡板使用设置</a></li>
<li id="baffleLogsp" class=""><a href="baffleLogManage.jsp" style="text-decoration:none;" >挡板访问日志</a></li>
<li id="userListJsp" class=""><a href="userList.jsp" style="text-decoration:none;" >用户管理</a></li>
<li id="functionManageJsp" class=""><a href="functionManage.jsp" style="text-decoration:none;" >功能管理</a></li>
<li id="" class=""><a href="" style="text-decoration:none;" >帮助</a></li>
</ul>
