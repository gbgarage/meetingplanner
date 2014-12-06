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
    <script src="script/menu.js" type="text/javascript"></script>
    <style type="text/css">


        .New_Button, .Edit_Button, .Delete_Button, .Update_Button, .Cancel_Button {
            font-size: 11px;
            color: #1B3F91;
            font-family: Verdana;
            margin-right: 5px;
        }

        .progressbar {
            position: relative;
            background: #bbb;
            width: 100%;
            height: 16px;
            overflow: hidden;
        }

        .progressbar-percent {
            position: absolute;
            height: 18px;
            background: blue;
            left: 0;
            top: 0px;
            overflow: hidden;
            z-index: 1;
        }

        .progressbar-label {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            font-size: 13px;
            color: White;
            z-index: 10;
            text-align: center;
            height: 16px;
            line-height: 16px;
        }


    </style>
</head>
<body>
<ul id="menu" class="mini-menubar" style="width:100%;"
    url="subs/menu.txt" idField="id" parentField="pid" onitemclick="onItemClick">
</ul>

<center><h1 style="margin:0;padding:15px;cursor:default;font-family:微软雅黑,黑体,宋体;">Mobile服务详情</h1>  </center>

<div class="mini-splitter" style="width:100%;height:70%;">
    <div size="50" showCollapseButton="true">
        <div class="mini-fit">
            <ul id="caseIdTree" class="mini-tree" style="width:100%;" url="mobile_statistics/loadMenu.do"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false"
                expandOnLoad="false">
            </ul>
        </div>
    </div>
    <div size="240" showCollapseButton="true">
        <div class="mini-fit">

            <div id="servicePerformance" class="mini-treegrid" style="width:100%;height:100%;"
                 showTreeIcon="true"
                 treeColumn="dayString" idField="id" parentField="pid" resultAsTree="false"
                 allowResize="true" expandOnLoad="false" ondrawcell="onDrawCell">
                <div property="columns">

                    <div name="dayString" field="dayString" width="80">日期</div>
                    <div field="hourOfDay" width="80">小时</div>

                    <div field="previousServiceName" width="110">页面名</div>
                    <div field="previousPV" width="80">PV</div>
                    <div field="previousUV" width="80">UV</div>
                    <div field="currentServiceName" width="110">下个页面名</div>
                    <div field="currentPV" width="80">Next PV</div>
                    <div field="currentUV" width="80">Next UV</div>
                    <div field="conversionRate" width="80">转化率</div>


                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">


    mini.parse();


    var servicePerformanceTree = mini.get("servicePerformance");
    var tree = mini.get("caseIdTree");
    function onItemClick(e) {
        var item = e.item;
        var url = menuUrlMapping[item.id];
        if (url) {
            location.href = url;
        }


    }

    tree.on("nodeselect", function (e) {

        var serviceId = e.node.id;
        $.trim()
        if (serviceId.indexOf("-")!=-1||serviceId.indexOf("_")!=-1) {



            servicePerformanceTree.load("mobile_statistics/loadDataByService.do?service_id=" + serviceId);
//            servicePerformanceTree.expandLevel(0);
        }

    });

    function onDrawCell(e) {
        var node = e.node,
                column = e.column,
                field = e.field,
                value = e.value;


        if (field == "conversionRate") {

            e.cellHtml = '<div class="progressbar">'
                    + '<div class="progressbar-percent" style="width:' + value + '%;"></div>'
                    + '<div class="progressbar-label">' + value.toFixed(2) + '%</div>'
                    + '</div>';

        }

    }

</script>


</body>

</html>