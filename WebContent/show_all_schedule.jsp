<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <script src="${contextPath}/src/jquery.js"></script>
    <script src="${contextPath}/src/jquery-ui.min.js"></script>
    <script src="${contextPath}/script/miniui/miniui.js" type="text/javascript"></script>

    <link href="${contextPath}/script/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/styles/main.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/script/miniui/themes/icons.css" rel="stylesheet" type="text/css"/>

    <style type="text/css">
        .cfobject {
            background-color: #8cb5c0;
        }

        .mtobject {
            background-color: #c3deb7;
        }

        .nlobject {
            background-color: #ffeebc;
        }
    </style>

    <script type="text/javascript">
    
    $(document).ready(function(){
    	
    	var conflictdata;
    	var scheduledata;
    
        function addNotificationList(c1, c2) {
            var notif = $("#notification_list")[0];
            var newTR = notif.insertRow(notif.rows.length);
            var newTD1 = newTR.insertCell(0);
            newTD1.innerHTML = c1;
            newTD1.className = "nlobject";

            var newTD2 = newTR.insertCell(1);
            newTD2.innerHTML = c2;
        }

        function swapObject(sourceId, targetId) {
            var sourceHTML = $("#" + sourceId).html();
            var targetHTML = $("#" + targetId).html();
            $("#" + sourceId).html(targetHTML);
            $("#" + targetId).html(sourceHTML);
        }

        function getOriginalClass(classList) {
            //console.log(classList);
            return classList.split(" ")[0];
        }

        function dropHandler(event, ui) {
            // if the same class, then just return
            if (getOriginalClass($("#" + ui.helper[0].id).attr('class')) ==
                    getOriginalClass($("#" + this.id).attr('class'))) return;

            //swap objects
            swapObject(ui.helper[0].id, this.id);

            //add Notifications
            var dragSourceText = $("#" + ui.helper[0].id).text();
            var dragTargetText = $("#" + this.id).text();
            addNotificationList(dragSourceText + " => " + dragTargetText, "会议/冲突 交换");
        }


        $(function () {
            jQuery.post(
                    "${contextPath}/listdvt.do",
                    null,
                    getListDvt,
                    "json");

            jQuery.post("${contextPath}/listconflicts.do",
                    null,
                    getListConflicts,
                    "json"
            );

        });


        var timeslot = new Array("MeetingDate", "MeetingVenue", "T0900", "T1000", "T1100", "T1200", "T1300", "T1400", "T1500", "T1600");
        function getTDField(data, fieldName) {
            var td1 = $("<td></td>");
            td1.html(data[fieldName]);

            return td1;
        }

        function getListDvt(data, textStatus, jqXHR) {
        	scheduledata = data;
            var datagrid1 = $("#datagrid1");
            if (textStatus == "success") {
                //console.log(data);
                for (var i = 0; i < data.length; i++) {
                    var tr1 = $("<tr></tr>");

                    for (var j = 0; j < timeslot.length; j++) {
                        var td1 = getTDField(data[i], timeslot[j])
                        td1.attr("id", "m" + "_" + i + "_" + j);

                        if (td1.html() != "" && timeslot[j].substring(0, 1) == "T") {

                            td1.addClass("mtobject").draggable({ opacity: 0.35, revert: true, revertDuration: 100 })
                                    .droppable({drop: dropHandler});
                            //td1.class ="mtobject";
                        }

                        tr1.append(td1);
                    }
                    datagrid1.append(tr1);

                }


            }
        }

        var conflictList = new Array("FundName", "StatusCode", "fund_id", "company_id");

        function getListConflicts(data, textStatus, jqXHR) {
        	conflictdata = data;
            var datagrid2 = $("#datagrid2");
            if (textStatus == "success") {
                //console.log(data);
                for (var i = 0; i < data.length; i++) {
                    var tr1 = $("<tr></tr>");


                    for (var j = 0; j < conflictList.length; j++) {
                        var td1 = getTDField(data[i], conflictList[j])
                        td1.attr("id", "c" + "_" + i + "_" + j);

                        if (td1.html() != "" && j == 0) {

                            td1.addClass("cfobject").draggable({ opacity: 0.35, revert: true, revertDuration: 100 })
                                    .droppable({drop: dropHandler});
                        }


                        tr1.append(td1);
                    }

                    td1 =$("<td></td>");
                    td1.html("<input type='checkbox' name='conflictcheck' id='" + i + "'/>");
                    tr1.append(td1);
                    
                    td1 =$("<td></td>");
                    td1.html("<span name='isResolved' id='isResolved" + i + "'>");
                    tr1.append(td1);

                    datagrid2.append(tr1);
                }

                //set up mtobject

            }
        }
        
        $("#addlunchbutton").click(function () {
        	var flag = 0; 
        	var id = 0;

        	$("[name='conflictcheck']").each(function () { 
		        	if ($(this).attr("checked")) { 
		        	flag += 1;  	
		        	id = $(this).attr("id");
	        	} 
        	});
        	if (flag > 1) alert("请不要复选");
        	else {
        		if (flag==1) alert("当前选中的是： "+ id);
        		
        		//open lunch select window
        		
                    var btnEdit = this;
                    mini.open({
        				url:  "./schedule/select_lunch.jsp",
                        title: "选择列表",
                        width: 650,
                        height: 380,
                        ondestroy: function (action) {
                            if (action == "ok") {
                                var iframe = this.getIFrameEl();
                                var windowdata = iframe.contentWindow.GetData();
                                windowdata = mini.clone(windowdata);    //必须
                                if (windowdata) {
                            		//post fund_id and company_id to backend to add lunch
                                    jQuery.post(
                                            "./addlunch.do",
                                            { fund_id: conflictdata[id]["fund_id"], 
                                            	company_id: conflictdata[id]["company_id"]
                                            	, lunchtime_id: windowdata.id } ,
                                            function(data) {
                                            		if (data!=1) alert("error");
                                            		else $("#isResolved" + id).text("已解决");
                                            });
                                }
                            }

                        }
                    }); 
                    
                 }    
        });	    
    });
    </script>
    <title>会议安排清单</title>
</head>
<body>
会议安排清单<br/>

<div style="overflow: auto; width: 100%;height: 60%">
    <table id="datagrid1" class="mini-grid-table" style="border:1px solid #cccccc">
        <thead>
            <th class="mini-grid-headerCell">日期</th>
            <th class="mini-grid-headerCell">会议室</th>
            <th class="mini-grid-headerCell">09:00</th>
            <th class="mini-grid-headerCell">10:00</th>
            <th class="mini-grid-headerCell">11:00</th>
            <th class="mini-grid-headerCell">12:00</th>
            <th class="mini-grid-headerCell">13:00</th>
            <th class="mini-grid-headerCell">14:00</th>
            <th class="mini-grid-headerCell">15:00</th>
            <th class="mini-grid-headerCell">16:00</th>
        </thead>
    </table>
</div>
<br/>
<table width=100%>
    <tr>
        <td width=50%>
            会议冲突列表<br/>

            <div style="width:100%;height:350px;overflow:scroll">
                <table id="datagrid2" class="mini-grid-table"
                       style="border:1px solid #cccccc;width:100%;overflow:scroll">
                    <tr>
                        <th class="mini-grid-headerCell">会议安排</th>
                        <th class="mini-grid-headerCell">冲突情况</th>
                        <th class="mini-grid-headerCell">基金公司ID</th>
                        <th class="mini-grid-headerCell">上市公司ID</th>
                        <th class="mini-grid-headerCell">选择</th>       
                        <th class="mini-grid-headerCell">是否解决</th>                
                    </tr>
                </table>
            </div>
        </td>
        <td width=50%>
            冲突解决<br/>

            <div style="width:100%;height:350px;overflow:hidden;background:#F8F8F8">
                <p>将选中冲突需求加入工作餐时间 <input type="button" id="addlunchbutton" value="调整为工作餐" /></p>
                <p>将选中会议调整至一对多 <input type="button" value="调整为一对多会议" /></p>
                <p>请将冲突拖动至替换位置</p>
            </div>
        </td>
    </tr>
</table>

<br/>
通知列表
<table class="mini-grid-table" id="notification_list" style="border:1px solid #cccccc;width:50%">
    <tr>
        <th class="mini-grid-headerCell">变更</th>
        <th class="mini-grid-headerCell">备注</th>
    </tr>
</table>
</body>
</html>