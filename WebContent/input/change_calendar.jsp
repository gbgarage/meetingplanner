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

		<h1>请选择角色</h1>
		
		<div style="width:300px;height:100px;">
		
			<div id="rolelistbox" class="mini-listbox" style="width:150px;height:100px;float:left;" 
			    textField="text" valueField="id" onvaluechanged="onRoleChanged"
			    url="../commonctrl/roleswithoutorgnizer.txt">
			</div>
			
			<div style="width:150px;height:100px;float:left;">
			<a id="cancelMeetingButton" class="mini-button">取消选择的会议</a>
			<a id="AMtoPMButton" class="mini-button">所有上午会议改下午</a>
			</div>
			
		</div>
		
		<br><br><br>
		
		<div id="selectname"></div>
		<br>
		<div id="selecttime"></div>
        
<script>
mini.parse();

var rolelistbox = mini.get("rolelistbox");
var cancelMeetingButton = mini.get("cancelMeetingButton");
var AMtoPMButton = mini.get("AMtoPMButton");

function onRoleChanged(e) {
	
	var roleid = rolelistbox.getValue();
	
	
	if (roleid == "fundmanager") {
		
		var div = (function () {/*
			<h1>请选择基金公司</h1>
	        <div id="fundgrid" class="mini-datagrid" style="width:800px;height:290px;"
	            idField="id" allowResize="true"
	            borderStyle="border-left:0;border-right:0;" onrowclick="onRowclick"
	        >
	        <div property="columns">
	                <div type="indexcolumn" ></div>
	                <div field="fundName" width="30%" headerAlign="center" allowSort="true">基金</div>    
	                <div field="phoneNumber" width="30%" headerAlign="center" allowSort="true">电话</div>                                            
	                <div field="contactor" width="30%" headerAlign="center" dateFormat="yyyy-MM-dd" allowSort="true">联系人</div>                
	        </div>
        	</div>	     
			*/}).toString().match(/[^]*\/\*([^]*)\*\/\}$/)[1];
			
		document.getElementById("selectname").innerHTML = div;
	        
		mini.parse();
 	    var fundgrid = mini.get("fundgrid");	
 		fundgrid.setUrl("./getFundList.do");
 		fundgrid.load();
	}

	if (roleid == "companymanager") {
		
		var div = (function () {/*
	        <h1>请选择上市公司</h1>
	        <div id="companygrid" class="mini-datagrid" style="width:800px;height:290px;"
	            idField="id" allowResize="true"
	            borderStyle="border-left:0;border-right:0;" onrowclick="onRowclick"
	        >
	            <div property="columns">
	                <div type="indexcolumn" ></div>
	                <div field="id" width="30%" headerAlign="center" allowSort="true">上市公司序号</div>    
	                <div field="name" width="30%" headerAlign="center" allowSort="true">公司名称</div>                                            
	                <div field="contact" width="30%" headerAlign="center" dateFormat="yyyy-MM-dd" allowSort="true">联系人</div>                
	            </div>
	        </div>    
			*/}).toString().match(/[^]*\/\*([^]*)\*\/\}$/)[1];
		
		document.getElementById("selectname").innerHTML = div;
        
		mini.parse();
 	    var companygrid = mini.get("companygrid");	
 	    companygrid.setUrl("./getCompanyList.do");
 	    companygrid.load();
	}
	
	if (roleid == "organizer") {
		submitbutton.setHref("../show_all_schedule.jsp");
	}
	
}

function onRowclick(e) {
	
	var roleid = rolelistbox.getValue();
	var fundgrid = mini.get("fundgrid");
	var companygrid = mini.get("companygrid");
	
	if (roleid == "fundmanager") {
		cancelMeetingButton.setHref("./cancelMeetingForFund/" + fundgrid.getSelected().id + ".do");		
		AMtoPMButton.setHref("./AMtoPMForFund/" + fundgrid.getSelected().id + ".do");	

		var div = (function () {/*
			<h1>请选择时间</h1>
	        <div id="timegrid" class="mini-datagrid" style="width:800px;height:290px;"
	            idField="id" allowResize="true"
	            borderStyle="border-left:0;border-right:0;" onrowclick="onRowclicklv2"
	        >
            <div property="columns">
                <div field="date" width="100" headerAlign="center" allowSort="true">已选择日期</div>     
                <div field="time_window" width="150" headerAlign="center" allowSort="true">已选择时间</div>
            </div>
        	</div>	     
			*/}).toString().match(/[^]*\/\*([^]*)\*\/\}$/)[1];
			
		document.getElementById("selecttime").innerHTML = div;
	        
		mini.parse();
 	    var timegrid = mini.get("timegrid");	
 	    timegrid.setUrl("./getFundTime/" + fundgrid.getSelected().id + ".do");
 	    timegrid.reload();
	}

	if (roleid == "companymanager") {
		cancelMeetingButton.setHref("./cancelMeetingForCompany/" + companygrid.getSelected().id + ".do");		
		AMtoPMButton.setHref("./AMtoPMForCompany/" + companygrid.getSelected().id + ".do");	
		
		var div = (function () {/*
			<h1>请选择时间</h1>
	        <div id="timegrid" class="mini-datagrid" style="width:800px;height:290px;"
	            idField="id" allowResize="true"
	            borderStyle="border-left:0;border-right:0;" onrowclick="onRowclicklv2"
	        >
            <div property="columns">
                <div field="date" width="100" headerAlign="center" allowSort="true">已选择日期</div>     
                <div field="time_window" width="150" headerAlign="center" allowSort="true">已选择时间</div>
            </div>
        	</div>	     
			*/}).toString().match(/[^]*\/\*([^]*)\*\/\}$/)[1];
			
		document.getElementById("selecttime").innerHTML = div;
	        
		mini.parse();
 	    var timegrid = mini.get("timegrid");	
 	    timegrid.setUrl("./getCompanyTime/" + companygrid.getSelected().id + ".do");
 	    timegrid.reload();
	}

}


function onRowclicklv2(e) {
	
	var roleid = rolelistbox.getValue();
	var fundgrid = mini.get("fundgrid");
	var companygrid = mini.get("companygrid");
	var timegrid = mini.get("timegrid");
	
	if (roleid == "fundmanager") {
		cancelMeetingButton.setHref("./cancelMeetingForFund/" + fundgrid.getSelected().id + "/" + timegrid.getSelected().id + ".do");		
		AMtoPMButton.setHref("./AMtoPMForFund/" + fundgrid.getSelected().id + "/" + timegrid.getSelected().id + ".do");	
	}

	if (roleid == "companymanager") {
		cancelMeetingButton.setHref("./cancelMeetingForCompany/" + companygrid.getSelected().id + "/" + timegrid.getSelected().id + ".do");		
		AMtoPMButton.setHref("./AMtoPMForCompany/" + companygrid.getSelected().id + "/" + timegrid.getSelected().id + ".do");	
	}

}

</script>

</body>
</html>