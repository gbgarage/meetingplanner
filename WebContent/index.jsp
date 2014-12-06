<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
	<head>
	
	<title>接口性能测试</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="script/miniui/themes/default/miniui.css" rel="stylesheet"  type="text/css" />
	<link href="styles/main.css" rel="stylesheet"  type="text/css" />
	<link href="script/miniui/themes/icons.css" rel="stylesheet"  type="text/css" />
	<script type="text/javascript" charset="utf-8" src="script/jquery-1.8.2.js"></script>
	<script src="script/miniui/miniui.js" type="text/javascript"></script>
	</head>

<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		<!-- north start -->
		<div class="header" region="north" height="70" showSplit="false"
			showHeader="false">
			<h1 style="margin:0;padding:15px;cursor:default;font-family:微软雅黑,黑体,宋体;">接口性能测试</h1>
			<!-- <div style="position:absolute;top:18px;right:10px;">
			   <a class="mini-button mini-button-iconTop" iconCls="icon-redo"
					onclick="backToOldVer()" plain="true">返回旧版</a>
				<a class="mini-button mini-button-iconTop" iconCls="icon-edit"
					onclick="userInfo()" plain="true"></a> 
				<a class="mini-button mini-button-iconTop" iconCls="icon-close"
					onclick="logout()" plain="true">注销</a>
			</div>
 -->		</div>
		<!--  north end -->
		<!-- south start -->
	    <div showHeader="false" region="south" showSplit="false"   height="25">
			<div style="line-height:20px;text-align:center;cursor:default">Copyright © Ctrip无线事业部</div>
		</div>
		<!-- south end -->
		<!-- center start -->
		<div title="center" region="center" style="border:0;"
			bodyStyle="overflow:hidden;">
			<div class="mini-splitter" style="width:100%;height:100%;"
				borderStyle="border:0;">
				<div size="180" maxSize="250" minSize="100"
					showCollapseButton="true" style="border:0;">
					<!--OutlookTree-->
					<div id="leftTree" class="mini-outlooktree" url="subs/tree.txt"
						onnodeclick="onNodeClick" textField="text" idField="id"
						parentField="pid" expandOnLoad="true">
					</div>
				</div>
				<div showCollapseButton="false" style="border:0;">
					<!--Tabs-->
					<div id="mainTabs" class="mini-tabs" activeIndex="0"
						style="width:100%;height:100%;">
						<!--
						 <div title="挡板数据配置" url="a_baffleDataMng.jsp" name="tab$a_baffleDataMng"></div>
						 -->
					</div>
				</div>
			</div>
		</div>
		<!-- center end -->
	</div>
	</div>

	<script type="text/javascript">
        mini.parse();
        var tabs = mini.get("mainTabs");

        function showTab(node) {
            var tabs = mini.get("mainTabs");
            var id = "tab$" + node.id;
           // var filepath = "../../module/" + node.pid + "/"; //文件夹路径
            var tab = tabs.getTab(id);
            if (!tab) {
                tab = {};
                tab.name = id;
                tab.title = node.text;
                tab.showCloseButton = true;
                //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
                //mini_JSPath + filepath + node.id + ".jsp";
                tab.url =   node.id+".jsp";                      
                tabs.addTab(tab);
            }
            tabs.activeTab(tab);
        }
        var index = 1;
        function addTab(  tab) {
            //add tab
            tabs.addTab(tab);
            //active tab
            tabs.activeTab(tab);
        }
        function removeTab() {
            var tab = tabs.getActiveTab();
            if (tab) {
                tabs.removeTab(tab);
            }
        }

        
        
        function onNodeClick(e) {
            var node = e.node;
            var isLeaf = e.isLeaf;
            if (isLeaf) {
                showTab(node);
            }
        }
        

	</script>
</body>
</html>
