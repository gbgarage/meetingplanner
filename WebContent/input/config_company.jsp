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
 
    <tr><h1>请选择上市公司</h1></tr>      
<!--     <tr><input id="btnEdit1" class="mini-buttonedit" onbuttonclick="onFundButtonEdit" name="a" textName="b"/></tr>    -->
    <tr><a id="btnEdit1" class="mini-button mini-button-iconRight" iconCls="icon-edit" onclick="onCompanyButtonEdit"></a></tr>   

    <tr><h1>请选择能参与会议的时间</h1></tr>
<!--     <tr><input id="btnEdit3" class="mini-buttonedit" onbuttonclick="onFundTimeButtonEdit"/></tr>     -->
    <tr><a id="btnEdit3" class="mini-button" iconCls="icon-edit" onclick="onCompanyTimeButtonEdit" tooltip="编辑"></a></tr>
    
                    <div id="timeGrid" class="mini-datagrid" style="width:250px;height:200px;" 
                            showPageSize="false" showPageIndex="false"
                        pagerStyle="padding:2px;"
                        multiSelect="true" >
                        <div property="columns">
                            <div field="date" width="100" headerAlign="center" allowSort="true">已选择日期</div>     
                            <div field="time_window" width="150" headerAlign="center" allowSort="true">已选择时间</div>
                        </div>
                    </div>
                    
    
   <script type="text/javascript">
        
    	mini.parse();
    	var selectedCompany;
        var timeGrid = mini.get("timeGrid");

        function onCompanyButtonEdit(e) {
            var btnEdit = this;
            mini.open({
                url: "./config_company_name.jsp",
                title: "选择列表",
                width: 650,
                height: 380,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            btnEdit.setValue(data.id);
                            btnEdit.setText(data.name);
                            selectedCompany = data.id;
                            timeGrid.url = "./getCompanyTime/" + selectedCompany + ".do";
                            timeGrid.reload();
                        }
                    }

                }
            }); 
            }    
   

           function onCompanyTimeButtonEdit(e) {
               var btnEdit = this;
               if (selectedCompany == null) {
               	alert("请先选择上市公司");
               	return;
               }
               mini.open({
                   url: "./config_companytime.jsp?company=" + selectedCompany,
                   showMaxButton: true,
                   title: "选择列表",
                   width: 650,
                   height: 380,
                   onload: function () {
                       var iframe = this.getIFrameEl();
                       iframe.contentWindow.SetData(null);
                   },
                   ondestroy: function (action) {                    
                       if (action == "ok") {
                           var iframe = this.getIFrameEl();

                           var data = iframe.contentWindow.GetData();
                           data = mini.clone(data);

//                            btnEdit.setValue(data.id);
//                            btnEdit.setText(data.text);

                           var json = mini.encode(data);
                           
                           $.ajax({
                               url: "./saveTimeCompany/" + selectedCompany + ".do",
                               type: "post",
                               data: json,
                               dataType: "json",
                               contentType: "application/json",
                               success: function (text) {
                                   timeGrid.url = "./getCompanyTime/" + selectedCompany + ".do";
                                   timeGrid.reload();
                               }
                           });
                           
                       }
                       

                       

                   }
               }); 
               


           }
             
    </script>
</body>
</html>