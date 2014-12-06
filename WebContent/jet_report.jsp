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
<center><h1 style="margin:0;padding:15px;cursor:default;font-family:微软雅黑,黑体,宋体;">H5性能报告</h1>  </center>





<div class="mini-splitter" style="width:100%;height:80%;">
    <div size="50" showCollapseButton="true">
        <div class="mini-fit">
            <ul id="caseIdTree" class="mini-tree" style="width:100%;" url="subs/jet.txt"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false"
                expandOnLoad="false">
            </ul>
        </div>
    </div>
    <div size="240" showCollapseButton="true">
        <div class="mini-fit">

            <div id="jetTable" class="mini-treegrid" style="width:100%;height:100%;"
                 showTreeIcon="true"
                 treeColumn="dayString" idField="id" parentField="pid" resultAsTree="false"
                 allowResize="true" expandOnLoad="false" >
                <div property="columns">

                    <div name="dayString" field="dayString" width="80">日期</div>
                    <div field="hourOfDay" width="80">小时</div>


                    <div field="type" width="80">类型</div>
                    <div field="total" width="80">调用次数</div>



                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">


    mini.parse();


    var servicePerformanceTree = mini.get("jetTable");
    var tree = mini.get("caseIdTree");
    function onItemClick(e) {
        var item = e.item;
        var url = menuUrlMapping[item.id];
        if (url) {
            location.href = url;
        }


    }

    tree.on("nodeselect", function (e) {

        var type = e.node.id;

        if (e.node._level>0) {



            servicePerformanceTree.load("jet_statistics/loadDataByType.do?type=" + type);
//            servicePerformanceTree.expandLevel(0);
        }

    });
//

</script>


</body>

</html>