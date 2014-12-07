<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="script/miniui/themes/default/miniui.css" rel="stylesheet"  type="text/css" />
	<link href="styles/main.css" rel="stylesheet"  type="text/css" />
	<link href="script/miniui/themes/icons.css" rel="stylesheet"  type="text/css" />
	<script type="text/javascript" charset="utf-8" src="script/jquery-1.8.2.js"></script>
	<script src="script/miniui/miniui.js" type="text/javascript"></script>

<title>会议安排清单</title>
</head>
<body>
<div id="datagrid1" class="mini-datagrid" style="width:1200px;height:250px;" 
    url="listdvt.do">
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
<script type="text/javascript">
mini.parse();

var grid = mini.get("datagrid1");
grid.load();


</script>
</body>
</html>