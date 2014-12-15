<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="../script/boot.js" type="text/javascript"></script>
<!--     <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> -->
    <link href="../styles/demo.css" rel="stylesheet" type="text/css" />        
</head>
<body>

		<h1>当前1对1会议需求列表</h1>
                    <div id="MeetReqGrid" class="mini-datagrid" style="width:350px;height:300px;" 
                            showPageSize="false" showPageIndex="false" ondrawcell="onDrawCell"
                        pagerStyle="padding:2px;"
                        multiSelect="false" >
                        <div property="columns">
                        	<div field="fundId" width="50" headerAlign="center" allowSort="true">序号</div> 
                            <div field="fund" width="100" headerAlign="center" allowSort="true">基金名称</div>     
                            <div field="company" width="150" headerAlign="center" allowSort="true">上市公司名称</div>                            
                        </div>
                    </div>
                    
        <br>
		<h1>是否开始计算</h1>
		
		<a id="submitbutton" class="mini-button" style="margin: 10px" href="./calCalendar/calculate.do">计算</a> 

</body>

<script>
	mini.parse();
	var MeetReqGrid = mini.get("MeetReqGrid");
	MeetReqGrid.setUrl("./getMeetReqList.do");
	MeetReqGrid.load();
    
    function onDrawCell(e) {
    	if (e.column.field=="fund") {
    		e.cellHtml = e.value.fundName;
    	}
    	
    	if (e.column.field=="company") {
    		e.cellHtml = e.value.name;
    	}
    }
	
</script>
</html>