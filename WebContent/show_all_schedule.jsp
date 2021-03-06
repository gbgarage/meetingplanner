<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <script src="${contextPath}/src/jquery.js"></script>
    <script src="${contextPath}/src/jquery-ui.min.js"></script>

<%-- 	<script src="${contextPath}/src/jquery-1.11.3.min.js"></script> --%>
<%-- 	<script src="${contextPath}/src/jquery-ui-1.11.4.min.js"></script> --%>
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
        
        .smobject {
        	background-color: #9E94EB;
        }
        
        .omobject {
        	background-color: #F08AA7;
        }

        .ooobject {
        	background-color: #c3deb7;
        }
        
        .nlobject {
            background-color: #ffeebc;
        }
        
        #hoverwin {
        	background-color: #C6E2FF;    
		    /*希望窗口有边框*/  
		    border: 1px red solid;  
		    /*希望窗口宽度和高度固定，不要太大*/  
		    width: 300px;  
		    height: 100px;  
		    /*希望控制窗口的位置*/  
 		    position: absolute;
/* 		    top: 100px;   */
/* 		    left: 350px;   */
		    /*希望窗口开始时不可见*/  
		    display: none;
        }
        
        #hoverwinmeetingtype {
		    /*控制标题栏的背景色*/  
		    background-color: blue;  
		    /*控制标题栏中文字的颜色*/  
		    color: yellow;  
		    /*控制标题栏的左内边距*/  
		    padding-left:3px;  
        }
        
        #hoverwincompany {
		    padding-left: 3px;  
		    padding-top: 5px;      
        }
        
        #hoverwinfund {
		    padding-left: 3px;  
		    padding-top: 5px;
        }
        
    </style>

    <script type="text/javascript">

    $(document).ready(function(){
    	
    	var conflictdata;
    	var scheduledata;
        var timeslot = new Array("MeetingDate", "MeetingVenue", "T0900", 
        		"T1000", "T1100", "T1200", 
        		"T1300", "T1400", "T1500",
        		"T1600");
    
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
            
            // if target class is blank object, swap the conflict meeting into the blank one, eg. used by add lunch feature
            if (getOriginalClass($(this).attr('class')) == 'bkobject') {
            	//if target is blank object, conflict block is the source block
            	var conflictblockid = ui.helper[0].id;
            	var meeetingblockid = this.id;
            	
        		//post fund_id and company_id to backend to add lunch
        		//lunchtime_id is derived from target block location, need to change later
                jQuery.post(
                        "${contextPath}/schedule/addlunch.do",
                        { fund_id: conflictdata[conflictblockid.split("_")[1]]["fund_id"], 
                        	company_id: conflictdata[conflictblockid.split("_")[1]]["company_id"],
                        	lunchtime_id: meeetingblockid.split("_")[2]-1, 
                        	venue_id: scheduledata[meeetingblockid.split("_")[1]]['MeetingVenue']} ,
                        function(data) {
                        }
                );
        		return;
            }

            //set id for meetingblock and conflict block which will be swapped
            var meetingblockid;
            var conflictblockid;
            
            //swap objects
            swapObject(ui.helper[0].id, this.id);

            var meetingClass = getOriginalClass($("#" + ui.helper[0].id).attr('class'));
            
            if ( meetingClass == "ooobject" || meetingClass == "smobject" || meetingClass == "omobject"  ) {
            	meetingblockid = ui.helper[0].id;
            } 
            else meetingblockid = this.id;
            
            if (getOriginalClass($("#" + this.id).attr('class')) == "cfobject" ) {
            	conflictblockid = this.id;
            } 
            else conflictblockid = ui.helper[0].id;
            
            //id of the meeting which will swap attendee
            var meeting_id = scheduledata[meetingblockid.split("_")[1]][timeslot[parseInt(meetingblockid.split("_")[2])] +"ID"];
 
            //id of the meeting which will swap attendee
            var meeting_type = scheduledata[meetingblockid.split("_")[1]][timeslot[parseInt(meetingblockid.split("_")[2])] +"TYPE"];
 
            //id of the conflict which will be resolved after the swap
            var conflict_fundid = conflictdata[conflictblockid.split("_")[1]]["fund_id"];
            var conflict_companyid = conflictdata[conflictblockid.split("_")[1]]["company_id"];
            
            //post meetingid, fundid, companyid to server side
            
            jQuery.post(
            	"${contextPath}/schedule/swapmeeting.do",
                { meeting_id: meeting_id, 
                	conflict_fundid: conflict_fundid, 
                	conflict_companyid: conflict_companyid },
                function(data) {
                	if (data!=1) alert("error");
                }
            );
            
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

        function getTDField(data, fieldName) {
            var td1 = $("<td></td>");
            td1.html(data[fieldName]);

            return td1;
        }

        function getListDvt(data, textStatus, jqXHR) {
        	scheduledata = mini.clone(data);
            var datagrid1 = $("#datagrid1");
            if (textStatus == "success") {
                //console.log(data);
                for (var i = 0; i < data.length; i++) {
                    var tr1 = $("<tr></tr>");

                    for (var j = 0; j < timeslot.length; j++) {
                        var td1 = getTDField(data[i], timeslot[j])
						td1.attr("id", "m" + "_" + i + "_" + j);

                        if (td1.html() != "" && timeslot[j].substring(0, 1) == "T") {
                        	var meetingType = data[i][timeslot[j]+"TYPE"];
                        	var meetingId = data[i][timeslot[j]+"ID"];
                        	var meetingClass;
                        	switch (meetingType) {
                        		case 'P': meetingClass = "omobject meetingobject"; break;
                        		case 'O': meetingClass = "ooobject meetingobject"; break;
                        		case 'S': meetingClass = "smobject meetingobject"; break;
                        	};
                    
                            td1.addClass(meetingClass).draggable({ opacity: 0.35, revert: true, revertDuration: 100 })
                                    .droppable({drop: dropHandler});
                            td1.attr("name", meetingId);
                        } else if (td1.html() == "" && timeslot[j].substring(0, 1) == "T"){
                        	//if td does not have data, means no meeting at that time, set class to bkobject = blank object
                        	td1.addClass("bkobject").droppable({drop: dropHandler});
                        }

                        tr1.append(td1);
                    }
                    datagrid1.append(tr1);

                }


            }
        }

        var conflictList = new Array("FundName", "StatusCode", "fund_id", "company_id");

        function getListConflicts(data, textStatus, jqXHR) {
        	conflictdata = mini.clone(data);
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
        		
        		//show current selection
//         		if (flag==1) alert("当前选中的是： "+ id);
        		
        		//open lunch select window
        		
                    var btnEdit = this;
                    mini.open({
        				url:  "${contextPath}/schedule/select_lunch.jsp",
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
                                            "${contextPath}/schedule/addlunch.do",
                                            { fund_id: conflictdata[id]["fund_id"], 
                                            	company_id: conflictdata[id]["company_id"]
                                            	, lunchtime_id: windowdata.id } ,
                                            function(data) {
                                            		if (data!=1) alert("error");
                                            		else $("#isResolved" + id).text("已解决");
                                            }
                                    );
                                }
                            }

                        }
                    }); 
                    
                 }    
        });	    
        
        $("#changetosmallbutton").click(function () {
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
        		//show current selection
//         		if (flag==1) alert("当前选中的是： "+ id);
        		
        		//open company other 1 on 1 meeting and select to change to small window
        		
                    var btnEdit = this;
                    mini.open({
        				url:  "${contextPath}/schedule/select_changesmall.jsp?companyid=" + conflictdata[id]["company_id"],
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
                                            "${contextPath}/schedule/changesmall.do",
                                            { fund_id: conflictdata[id]["fund_id"], 
                                            	company_id: conflictdata[id]["company_id"],
                                            	meeting_id: windowdata.id},
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
        
        /* 当鼠标移动到上面的时候浮动窗口显示 */
        $('.meetingobject').live('mouseenter', function(){
        	$('#hoverwin').show();
            $('#hoverwin').css("left",document.body.scrollLeft+event.clientX+1); 
            $('#hoverwin').css("top",document.body.scrollLeft+event.clientY+10);
            
            jQuery.post(
                "${contextPath}/schedule/showmeetingdetail/" + $(this).attr('name') +".do", null,
            	function(data) {
                	$('#hoverwinmeetingtype').text("会议类型: " + data.type);
                	$('#hoverwincompany').text("公司信息: " + data.companyStr);
                	$('#hoverwinfund').text("基金信息: " + data.fundStr);
            	}
            );
            
        });
        
        /* 当鼠标移动移出的时候浮动窗口显示 */
        $('.meetingobject').live('mouseleave', function(){
        	$('#hoverwin').hide();
        });
        
    });
    
    </script>
    <title>会议安排清单</title>
    <span style="background-color: #c3deb7">1对1</span><span style="background-color: #9E94EB">小规模</span><span style="background-color: #F08AA7">1对多</span>
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
                <p>将选中冲突会议需求加入工作餐时间 <input type="button" id="addlunchbutton" value="调整为工作餐" /></p>
                <p>将选中冲突会议需求调整至一对多 <input type="button" id="changetosmallbutton" value="调整为一对多会议" /></p>
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

<div id="hoverwin">
	<div id="hoverwinmeetingtype">会议类型: </div>
	<div id="hoverwincompany">公司信息: </div>
	<div id="hoverwinfund">基金信息: </div>
</div>
</body>
</html>