<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="../script/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
	<link href="../script/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />        
    <script src="../script/miniui/miniui.js" type="text/javascript"></script>
    
     <link rel="stylesheet" type="text/css" href="../script/jsoneditor.css">
     <script type="text/javascript" src="../script/jsoneditor.js"></script>
	 <script src="../script/lib/ace/ace.js" type="text/javascript"></script>    
	 <script src="../script/lib/ace/mode-json.js" type="text/javascript"></script>    
	 <script src="../script/lib/ace/theme-textmate.js" type="text/javascript"></script>
	 <script src="../script/lib/ace/theme-jsoneditor.js" type="text/javascript"></script>
	 <!-- json lint -->
    <script type="text/javascript" src="../script/lib/jsonlint/jsonlint.js"></script>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
		Model名:<input type='text' value="" size=60  id='className' /><input value="搜索"  type="button" onclick="search()" style="width:60px;" /> <br/>
	 	查看模式:<select id="mode">
	 					<option value="code">code</option>
						<option value="tree">tree</option>
						<option value="view">view</option>
						<option value="form">form</option>
						<option value="text">text</option></select><br/>
	 	
	 	<div id="jsoneditor"  style="width:650px;height:600px;"></div>
	 	
	  <div class="mini-toolbar"
			style="text-align:center;padding-top:8px;padding-bottom:8px;"
			borderStyle="border:0;">
			<a class="mini-button" style="width:60px;" onclick="onOk()" id="btnSave">保存</a> 
			<span style="display:inline-block;width:25px;"></span>
			<a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
		</div>
  </body>
     <script type="text/javascript" charset="utf-8">
     	mini.parse();
        var container = document.getElementById("jsoneditor");
        var mode = document.getElementById('mode');
     	mode.onchange = function () {
        editor.setMode(mode.value);
    	};
    	var options = {
        mode: mode.value,
        error: function (err) {
            alert(err.toString());
        }
   		};
   		var json = {};
	    var editor = new jsoneditor.JSONEditor(container, options, json);
	    
     	 function SetData(data) {
     	 	 $("#className").val(data.className);
			  $.ajax({
                    url: "../getModelInfo.do?pk=" + data.pk+"&className="+data.className,
                    cache: false,
                    success: function (text) {
                    	editor.set(text);
                    }	
                });   
     	 }
     	 
     	 function onOk()
     	 {
     	 	 $.ajax({
                    url: "../saveModelInfo.do",
                    cache: false,
                    data: {modelData:editor.getText(),className:$("#className").val()},
                    type: "post",
                    success: function (text) {
                    	if(text.success)
                    	{
                    		onCancel();
                    	}
                    	else
                    	{
                    		alert(text.msg);
                    	}
                    }	
                }); 
     	 }
     	 
     	 function GetData(data) {
     	 	
     	 }
     	
     	function onCancel(e) {
            CloseWindow("cancel");
        }
        
        function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
   	  </script>
</html>
