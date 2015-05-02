<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <script src="./script/boot.js" type="text/javascript"></script>
<!--     <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> -->
    <link href="./styles/demo.css" rel="stylesheet" type="text/css" />    

    <script src="./script/UserSelectWindow.js" type="text/javascript"></script>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    .header
    {
        background:url(./commonctrl/header.gif) repeat-x 0 -1px;
    }
    .Note
    {
        background:url(./commonctrl/Notes_Large.png) no-repeat;width:32px;height:32px;
    }
    .Reports
    {
        background:url(./commonctrl/Reports_Large.png) no-repeat;width:32px;height:32px;
    }
    </style> 
        
</head>
<body>


<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div class="header" region="north" height="70" showSplit="false" showHeader="false">
    	<div style="float:left">
    		<h1 style="margin:0;padding:15px;cursor:default;font-family:'Trebuchet MS',Arial,sans-serif;">东方证券会议组织系统(v0.1)</h1>
    	</div>
    	
    	<div style="text-align: right">
    		<a href="./j_spring_security_logout">Log Out</a>
    	</div>
    </div>
    <div title="south" region="south" showSplit="false" showHeader="false" height="30" >
        <div style="line-height:28px;text-align:center;cursor:default">Copyright © 上海金科云桥有限公司版权所有 </div>
    </div>
    <div showHeader="false" region="west" width="180" maxWidth="250" minWidth="100" style="border:0;" >
        <!--OutlookMenu-->
        <div class="mini-outlookmenu" url="./commonctrl/outlookmenu.txt" onitemselect="onItemSelect"
            idField="id" parentField="pid" textField="text"
        >
        </div>

    </div>
    <div title="center" region="center" bodyStyle="overflow:hidden;">
        <!--Tabs-->
        <div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width:100%;height:100%;"      
            bodyStyle="border:0;background:white;"      
        >        
            <div title="首页" url="./welcome.jsp" >        
            </div>
        
        </div>        
    </div>
</div>
    
    <script type="text/javascript">
        mini.parse();

        function showTab(node) {
            var tabs = mini.get("mainTabs");

            var id = "tab$" + node.id;
            var tab = tabs.getTab(id);
            if (!tab) {
                tab = {};
                tab.name = id;
                tab.title = node.text;
                tab.showCloseButton = true;
                tab.url = node.url;

                tabs.addTab(tab);
            }
            tabs.activeTab(tab);
        }       

        function onItemSelect(e) {
            var item = e.item;
            showTab(item);
        }
    </script>

 
</body>
</html>