function getHistoryReport(){
	$.ajaxSetup({cache:false});
    $('#dg').datagrid({
	    url:"queryCheckMain.do",
	    pagination:true,
	    height:320,
	    pageSize:10,
	    pageList:[10],
	    loadMsg:'数据装载中......',
	    columns:[[ 
	        {field:'id',title:'编号',width:200,hidden:true},
	        {field:'name',title:'名称',width:200},
	        {field:'timeStr',title:'检测时间',width:200},
            {field:'opter',title:'操作',width:200,formatter:function(value,row,index){
				return "<div class=\"buttons\"><button class='regular' onclick='report(\""+row.id+"\");'><img height='10px' src=\"images/auth.png\" alt=\"\"/>查看</button></div>";
			}}
	    ]]
	});
}

function report(id){
	window.location="check_report.html?mainId="+id;
}