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
    
<!--     <div style="text-align:center;line-height:23px;padding:5px;"> -->
<!--         <div> -->
<!--             <span>上市公司名称：</span>     -->
<!--             <input id="keyText" class="mini-textbox" style="width:160px;" onenter="onSearchClick"/> -->
<!--             <a class="mini-button" onclick="onSearchClick" style="width:60px;height:20px;">查找</a>        -->
<!--         </div> -->
<!--     </div> -->
    <div style="padding-left:5px;padding-right:5px;">
        <table cellpadding="0" cellspacing="0" >
            <tr>
                <td >
                    <h4 style="margin:0;line-height:22px;font-size:13px;">备选上市公司列表：</h4>
                    <div id="serachGrid" class="mini-datagrid" style="width:250px;height:200px;" 
                            showPageSize="false" showPageIndex="false"
                        pagerStyle="padding:2px;"
                        multiSelect="true" url="./getCompanyList.do"
                    >
                        <div property="columns">
                            <div type="checkcolumn" ></div>
                            <div field="name" width="150" headerAlign="center" allowSort="true">上市公司</div>
                        </div>
                    </div>
              
                </td>
                <td style="padding:5px;">
            <input type="button" value="选择" onclick="addSelected()" style="width:50px;"/><br />
                </td>
                <td >
                    <h4 style="margin:0;line-height:22px;font-size:13px;">已选上市公司：</h4>
                    <div id="selectedList" class="mini-datagrid" style="width:250px;height:200px;"              
                            multiSelect="true" allowCellEdit="true" url="./get1on1CompanyList/<%=request.getParameter("fund")%>.do" ondrawcell="onDrawCellDisplayCompanyName">  
                        <div property="columns">
                        	<div type="checkcolumn" width="20"></div>
                            <div field="company" width="100" headerAlign="center" allowSort="true">上市公司</div>    
							<div type="checkboxcolumn" field="musthave" trueValue="1" falseValue="0" width="50" headerAlign="center">必须</div>     
							<div type="checkboxcolumn" field="small" trueValue="1" falseValue="0" width="50" headerAlign="center">小规模</div>                          
                        </div>
                    </div>                       
                </td>
                <td style="padding:5px;">
            <input type="button" value="删除" onclick="removeSelecteds()" style="width:55px;margin-bottom:2px;"/><br />                
            <input type="button" value="清空" onclick="removeAllSelecteds()" style="width:55px;"/><br />                
                </td>
            </tr>
        </table>
    </div>
    <div style="padding:15px;text-align:center;">   
            
        <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
        <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
    </div>

    
    <script type="text/javascript">
        mini.parse();
        
        function onDrawCellDisplayCompanyName(e) {
        	if (e.column.field=="company") {
        		e.cellHtml = e.value.name;
        	}
        }

        //////////////////////////////////////        

        function SetData(data) {
            //跨页面调用，克隆数据更安全
            data = mini.clone(data);
            grid.load();
            selectedList.load();
            grid.deselectAll();
//             selectedList.removeAll();

        }
        
        function GetData() {
            var rows = selectedList.getData();
            for (var i = 0, l = rows.length; i < l; i++) {
            	var row = rows[i];
            	delete row._index;
            	delete row._uid;
            	delete row.company.conflict;
            	delete row.fund.conflict;
            }
            
//             var ids = [], texts = [], musthave = [], small = [];
//             for (var i = 0, l = rows.length; i < l; i++) {
//                 var row = rows[i];
//                 ids.push(row.id);
//                 texts.push(row.name);
//                 musthave.push(row.musthave);
//                 small.push(row.small);
//             }

//             var data = {};
//             data.id = ids.join(",");
//             data.text = texts.join(",");
//             data.musthave = musthave.join(",");
//             data.small = small.join(",");
//             return data;

			return rows;

        }
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        }
        function onOk(e) {
            CloseWindow("ok");
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        /////////////////////////////////////

        var win = mini.get("selectWindow");
        var grid = mini.get("serachGrid");
        var selectedList = mini.get("selectedList");
        var keyText = mini.get("keyText");
        var deptTree = mini.get("deptTree");


        function onSearchClick(e) {
            grid.load({
                key: keyText.value
            });
        }
        function onClearClick(e) {
            deptTree.setValue("");
        }

        function addSelected() {
            
            var items = grid.getSelecteds();

            //根据id属性，来甄别要加入选中的记录
            var idField = grid.getIdField();

            //把已选中的数据，用key-value缓存，以便进一步快速匹配
            var idMaps = {};
            var selecteds = selectedList.getData();
            for (var i = 0, l = selecteds.length; i < l; i++) {
                var o = selecteds[i];
                var id = o[idField];
                idMaps[id] = o;
            }

            //遍历要加入的数组
            for (var i = items.length - 1; i >= 0; i--) {
                var o = items[i];
                var id = o[idField];
                if (idMaps[id] != null) items.removeAt(i);
            }  
            
            var incRecords = new Array();

            for (var i = 0, l = items.length; i < l; i++) {

				var incRecord = {}, company = {}, fund = {};
				incRecord.musthave = 0;
				incRecord.small = 0;
				company.name = items[i].name;
				company.conflict = 0;
				fund.conflict = 0;
				incRecord.company = company;
				incRecord.fund = fund;
				incRecord.fundId = <%=request.getParameter("fund")%>;
				incRecord.companyId = items[i].id;
				
				incRecords.push(incRecord);
            }
            
            //selectedList.addRows(items);
            selectedList.addRows(incRecords);
        }

        function removeSelecteds() {
            var items = selectedList.getSelecteds();
            //selectedList.removeItems(items);
            selectedList.removeRows(items, false);
        }
        function removeAllSelecteds() {
            //selectedList.removeAll();
            selectedList.clearRows();
        }

    </script>

</body>
</html>
