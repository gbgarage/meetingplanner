<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <script src="script/jquery-1.8.2.js" type="text/javascript" charset="utf-8"></script>
	<link href="script/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />        
    <script src="script/miniui/miniui.js" type="text/javascript"></script>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
  	  Model名:<input id="className"  textField="className"  valueField="className" class="mini-autocomplete"  required="false"  style="width:450px;"  url="getModelClassFullName.do" /><input value="搜索"  type="button" onclick="search()" style="width:60px;" /> <br/>
  	  <div id="modelSelect" class="mini-datagrid" style="width:100%;height:230px;"  allowResize="true" pageSize="20"  
		     multiSelect="false"   url="getModelList.do"  idField="id" >
		      <div property="columns">	
		     		<div type="checkcolumn" ></div> 
		     	   <div field="className"  visible="false" width="40" >类名</div>   
		     	   <div field="pk" width="40" >编号</div>   
		     	   <div name="action" width="40"   renderer="onActionRenderer">详情</div>		        
  	  		   </div>
	   </div>    
	   <div class="mini-toolbar"
			style="text-align:center;padding-top:8px;padding-bottom:8px;"
			borderStyle="border:0;">
			<a class="mini-button" style="width:60px;" onclick="onOk()" id="btnSave">确定</a> 
			<span style="display:inline-block;width:25px;"></span>
			<a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
		</div>
  </body>
     <script type="text/javascript" charset="utf-8">
     	mini.parse();
     	var grid = mini.get("modelSelect");
     	save_state = false;
     	
     	function search()
     	{	
     		var className = mini.get("className").getValue();
     		grid.load({className:className});
     	}
     	
     	
     	 function SetData(data) {
     	 	$("#className").val(data);
     	 }
     	 
     	 function onActionRenderer(e)
     	 {
     	 	 var pk = e.record.pk;
     	 	 var className = e.record.className;
     	 	 var s = "<a  href='javascript:;' onclick='modelDetail(" + pk + ",\""+className+"\");'>详情</a>";
     	 	 return s;
     	 }
     	 
    	function modelDetail(pk,className)
  	 	{
    	 	mini.open({
                    url: "newsub/modelDetailForm.jsp",
                    title: "编挡板", width: 700, height: 750,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { pk: pk, className: className };
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                    }
                });
    	}
     	 
     	 function GetData() {
     		 if(!save_state)
     		 {
     		 	return null;
     		 }
     	 	
     	 	var result = null;
     	 	//var rows = grid.getSelecteds();
     	 	var row = grid.getSelected();
     	 	if(row)
     	 	{
     	 		//result = {pk:row.pk,className:row.className}; 
     	 		 $.ajax({
                    url: "getModelInfo.do",
                    async: false,
                    cache: false,
                    data: {pk:row.pk,className:row.className},
                    type: "post",
                    success: function (text) {
                    result = text;
                    }	
                }); 
     	 	}
     	 	return result;
     	 }
     	
     	function onCancel(e) {
     		save_state = false;
            CloseWindow("cancel");
        }
        
        function onOk()
        {
        	save_state = true;
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
