<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
<!--     <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> -->
    
    <script src="../script/boot.js" type="text/javascript"></script>
    <link href="../styles/demo.css" rel="stylesheet" type="text/css" />  
    
        
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    </style>
</head>
<body>
    <div class="mini-fit">

        <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;" 
            idField="id" allowResize="true"
            borderStyle="border-left:0;border-right:0;" onrowdblclick="onRowDblClick"
        >
            <div property="columns">
                <div type="indexcolumn" ></div>
                <div field="date" width="30%" headerAlign="center" allowSort="true">日期</div>    
                <div field="time_window" width="30%" headerAlign="center" allowSort="true">时间</div>                                            
            </div>
        </div>
    
    </div>                
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>

</body>
</html>
<script type="text/javascript">
    mini.parse();

    var grid = mini.get("grid1");

    //动态设置URL
    grid.setUrl("./getLunchTime.do");
    //也可以动态设置列 grid.setColumns([]);

    grid.load();

    function GetData() {
        var row = grid.getSelected();
        return row;
    }
    function onKeyEnter(e) {
        search();
    }
    function onRowDblClick(e) {
        onOk();
    }
    //////////////////////////////////
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }

    function onOk() {
        CloseWindow("ok");
    }
    function onCancel() {
        CloseWindow("cancel");
    }


</script>