<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="../script/boot.js" type="text/javascript"></script>
<!--     <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> -->
    <link href="../styles/demo.css" rel="stylesheet" type="text/css" />        
</head>
<body>
 
   <h1>基金公司信息总表维护</h1>

    <div style="width:800px;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">保存</a>            
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:800px;height:280px;" 
        url="./getFundList.do" idField="id" 
        allowResize="true" pageSize="20" 
        allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
        editNextOnEnterKey="true"  editNextRowCell="true"
    >
        <div property="columns">
            <div type="indexcolumn"></div>
            <div type="checkcolumn"></div>
<!--             <div name="id"  field="id" headerAlign="center" allowSort="true" width="150" style="width:10%;">基金序号 -->
<!--                 <input property="editor" class="mini-textbox" /> -->
<!--             </div> -->
            <div name="fundName" field="fundName" width="120" headerAlign="center" allowSort="true" style="width:30%;">基金公司名称
                <input property="editor" class="mini-textbox" />
            </div>                      
            <div name="contactor" field="contactor" width="100" allowSort="true" style="width:20%;">联系人
                <input property="editor" class="mini-textbox"/>
            </div>            
            <div name="phoneNumber" field="phoneNumber" width="100" allowSort="true" style="width:20%;">电话号码
                <input property="editor" class="mini-textbox"/>
            </div>       
            <div name="priority" field="priority" width="100" allowSort="true" style="width:10%;">优先级
                <input property="editor" class="mini-textbox"/>
            </div>          
        </div>
    </div>
    <script type="text/javascript">

        mini.parse();

        var grid = mini.get("datagrid1");
        grid.load();
        

        //////////////////////////////////////////////////////

        function search() {
            var key = mini.get("key").getValue();

            grid.load({ key: key });
        }

        function onKeyEnter(e) {
            search();
        }

        function addRow() {          
//             var newRow = { name: "New Row" };
			var newRow = {id:999};
            grid.addRow(newRow, 0);

            grid.beginEditCell(newRow, "LoginName");
        }
        function removeRow() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                grid.removeRows(rows, true);
            }
        }
        function saveData() {
            
            var data = grid.getChanges();
            var json = mini.encode(data);
            
            grid.loading("保存中，请稍后......");
            $.ajax({
                url: "./crudFund/submit.do",
                data: json,
// 				data: "[{\"name\":\"test\",\"age\":1},{\"name\":\"test2\",\"age\":2}]",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }


        grid.on("celleditenter", function (e) {
            var index = grid.indexOf(e.record);
            if (index == grid.getData().length - 1) {
                var row = {};
                grid.addRow(row);
            }
        });
        
    </script>

</body>
</html>