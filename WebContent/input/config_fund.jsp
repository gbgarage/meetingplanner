<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="../script/boot.js" type="text/javascript"></script>
<!--     <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> -->
    <link href="../styles/demo.css" rel="stylesheet" type="text/css" />        
</head>
<body>
 
    <tr><h1>请选择基金公司</h1></tr>      
<!--     <tr><input id="btnEdit1" class="mini-buttonedit" onbuttonclick="onFundButtonEdit" name="a" textName="b"/></tr>    -->
    <tr><a id="btnEdit1" class="mini-button mini-button-iconRight" iconCls="icon-edit" onclick="onFundButtonEdit"></a></tr>   
    
    <tr><h1>请选择需要一对一会议的上市公司</h1></tr>
<!--     <tr><input id="btnEdit2" class="mini-buttonedit" onbuttonclick="on1v1CompanyButtonEdit"/></tr>     -->
    <tr><a id="btnEdit2" class="mini-button" iconCls="icon-edit" onclick="on1v1CompanyButtonEdit" tooltip="编辑"></a></tr>
                    <div id="companyGrid" class="mini-datagrid" style="width:250px;height:200px;" 
                            showPageSize="false" showPageIndex="false" ondrawcell="onDrawCellDisplayCompanyName"
                        pagerStyle="padding:2px;"
                        multiSelect="true" >
                        <div property="columns">
                            <div field="company" width="150" headerAlign="center" allowSort="true">已选择上市公司</div>
                            <div type="checkboxcolumn" field="musthave" trueValue="1" falseValue="0" width="50" headerAlign="center">必须</div>     
							<div type="checkboxcolumn" field="small" trueValue="1" falseValue="0" width="50" headerAlign="center">小规模</div>   
                        </div>
                    </div>

    <tr><h1>请选择能参与会议的时间</h1></tr>
<!--     <tr><input id="btnEdit3" class="mini-buttonedit" onbuttonclick="onFundTimeButtonEdit"/></tr>     -->
    <tr><a id="btnEdit3" class="mini-button" iconCls="icon-edit" onclick="onFundTimeButtonEdit" tooltip="编辑"></a></tr>
    
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
    	var selectedFund;
        var companyGrid = mini.get("companyGrid");
        var timeGrid = mini.get("timeGrid");
        var path = "${contextPath}";
        
        function onFundButtonEdit(e) {
            var btnEdit = this;
            mini.open({
//                 url: bootPATH + "./config_fund_name.jsp",
				url:  path + "/input/config_fund_name.jsp",
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
                            btnEdit.setText(data.fundName);
                            selectedFund = data.id;
                            companyGrid.url = "./get1on1CompanyList/" + selectedFund + ".do";
                            companyGrid.reload();
                            timeGrid.url = "./getFundTime/" + selectedFund + ".do";
                            timeGrid.reload();
                        }
                    }

                }
            }); 
            mini.parse();
            }    
            
            function on1v1CompanyButtonEdit(e) {
                var btnEdit = this;
                if (selectedFund == null) {
                	alert("请先选择基金公司");
                	return;
                }
                
                mini.open({
//                     url: bootPATH + "./config_1v1Company.jsp?fund=" + selectedFund,
					url: path + "/input/config_1v1Company.jsp?fund=" + selectedFund,
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

//                             btnEdit.setValue(data.id);
//                             btnEdit.setText(data.text);

                            var json = mini.encode(data);
                            
                            $.ajax({
                                url: "./save1on1Company/" + selectedFund + ".do",
                                type: "post",
                                data: json,
                                dataType: "json",
                                contentType: "application/json",
                                success: function (text) {
                                    companyGrid.url = "./get1on1CompanyList/" + selectedFund + ".do";
                                    companyGrid.reload();
                                }
                            });
                        }
                    }
                }); 
                


            }

           function onFundTimeButtonEdit(e) {
               var btnEdit = this;
               if (selectedFund == null) {
               	alert("请先选择基金公司");
               	return;
               }
               mini.open({
//                    url: bootPATH + "./config_fundtime.jsp?fund=" + selectedFund,
				   url: path + "/input/config_fundtime.jsp?fund=" + selectedFund,	
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
                               url: "./saveTimeFund/" + selectedFund + ".do",
                               type: "post",
                               data: json,
                               dataType: "json",
                               contentType: "application/json",
                               success: function (text) {
                                   timeGrid.url = "./getFundTime/" + selectedFund + ".do";
                                   timeGrid.reload();
                               }
                           });
                           
                       }
                       

                       

                   }
               }); 
               


           }
           
           function onDrawCellDisplayCompanyName(e) {
           	if (e.column.field=="company") {
           		e.cellHtml = e.value.name;
           	}
           }
             
    </script>
</body>
</html>