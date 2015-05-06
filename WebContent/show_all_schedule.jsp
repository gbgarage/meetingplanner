<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <script src="${contextPath}/src/jquery.js"></script>
    <script src="${contextPath}/src/jquery-ui.min.js"></script>

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


        var timeslot = new Array("MeetingDate", "MeetingVenue", "T0900", "T1000", "T1100", "T1200", "T1330", "T1430", "T1530", "T1630");
        function getTDField(data, fieldName) {
            var td1 = $("<td></td>");
            td1.html(data[fieldName]);

            return td1;
        }

        function getListDvt(data, textStatus, jqXHR) {
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

        var conflictList = new Array("CompanyName", "StatusCode");

        function getListConflicts(data, textStatus, jqXHR) {
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
                    td1.html("<input type=checkbox />");
                    tr1.append(td1);

                    datagrid2.append(tr1);
                }

                //set up mtobject

            }
        }

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
                        <th class="mini-grid-headerCell">选择</th>
                    </tr>
                </table>
            </div>
        </td>
        <td width=50%>
            冲突解决<br/>

            <div style="width:100%;height:350px;overflow:hidden;background:#F8F8F8">
                增加时间
                工作餐时间 <input type=button value="调整为工作餐"><br/>
                自定义时间<input type=button value="自定义时间"> <br/>
                会议降级<br/>
                将选中会议调整至一对多<br/>
                替换调整<br/>
                请将冲突拖动至替换位置<br/>
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