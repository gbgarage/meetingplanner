<%--
  Created by IntelliJ IDEA.
  User: flai
  Date: 14-5-21
  Time: 上午9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script src="script/boot.js" type="text/javascript"></script>
</head>
<body>

<form name="selectDateForm" action="h5_summaries_report.jsp" method="post">
<h4>开始时间</h4>
<input id="startDate" name="startDate" class="mini-datepicker" style="width:200px;"  nullValue="null"
       format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
<br>
<h4>结束时间</h4>
<input id="endDate" name="endDate" class="mini-datepicker" style="width:200px;"  nullValue="null"
       format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>

<br>
<input type="submit"  style="width:60px;" value="确定">
</form>

<script type="text/javascript">


    mini.parse();




</script>


</body>

</html>