<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="${contextPath}/script/miniui/themes/default/miniui.css" rel="stylesheet"  type="text/css" />
	<link href="${contextPath}/styles/main.css" rel="stylesheet"  type="text/css" />
	<link href="${contextPath}/script/miniui/themes/icons.css" rel="stylesheet"  type="text/css" />
	<script type="text/javascript" charset="utf-8" src="${contextPath}/script/jquery-1.8.2.js"></script>
	<script src="${contextPath}/script/miniui/miniui.js" type="text/javascript"></script>

<style>
.mini-pager-first,.mini-pager-prev,.mini-pager-next,.mini-pager-last{
    display:none;
}
</style>
<title>会议安排清单</title>
</head>
<body>
会议安排清单<br/>
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:400px" 
    url="${contextPath}/listdvt.do" showPager="false" pageSize="500" >
    <div property="columns">
          <div field="MeetingDate" width="120">日期</div>
          <div field="MeetingVenue" width="100">会议室</div>
          <div field="T0900" width="100">09:00</div>
          <div field="T1000" width="100">10:00</div>
          <div field="T1100" width="100">11:00</div>
          <div field="T1200" width="100">12:00</div>
          <div field="T1330" width="100">13:30</div>
          <div field="T1430" width="100">14:30</div>
          <div field="T1530" width="100">15:30</div>
          <div field="T1630" width="100">16:30</div>
    </div>
</div>



<br/>
资源冲突列表<br/>
<div id="datagrid2" class="mini-datagrid" style="width:100%;height:400px" 
    url="${contextPath}/listconflicts.do" showPager="false" pageSize="500" >
    <div property="columns">
          <div field="CompanyName" width="120">上市公司</div>
          <div field="FundName" width="100">基金公司</div>
          <div field="StatusCode" width="100">冲突</div>
    </div>
</div>

<script type="text/javascript">
mini.parse();

var grid1 = mini.get("datagrid1");
grid1.load();


var grid2 = mini.get("datagrid2");
grid2.load();

</script>
</body>
</html>
