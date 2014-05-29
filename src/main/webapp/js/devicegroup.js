function getGroup(){
	$.ajaxSetup({cache:false});
    $('#dg').datagrid({
	    url:"queryGroup.do",
	    pagination:true,
	    height:380,
	    pageSize:10,
	    pageList:[10],
	    loadMsg:'数据装载中......',
	    frozenColumns:[[
	                    {field:'ck',checkbox:true}
	                   ]],
	    columns:[[ 
	        {field:'id',title:'编号',width:200},
	        {field:'name',title:'名称',width:200},
	        {field:'status',title:'状态',width:100,formatter:function(value,row){
				if(value==0)
					return "停用";
				else
					return "启用";
			}},
            {field:'opter',title:'操作',width:200,formatter:function(value,row,index){
				return "<div class=\"buttons\"><button class='positive' onclick='modifyGroup("+index+");'><img height='10px' src=\"images/apply2.png\" alt=\"\"/>修改</button>&nbsp;&nbsp;"+
                "<button class='negative' onclick='deleteGroup(\""+row.id+"\",\""+row.name+"\");'><img height='10px' src=\"images/cross.png\" alt=\"\"/>删除</button>"+
                "<button class='regular' onclick='scanGroup(\""+row.id+"\",\""+row.name+"\");'><img height='10px' src=\"images/auth.png\" alt=\"\"/>查看设备</button></div>";
			}}
	    ]]
	});
}

function addGroup(){
    var o={};
    var a = $("#frmGroup").serializeArray();  
    $.each(a, function(){
        if (o[this.name]) {  
            if (!o[this.name].push) {  
                o[this.name] = [o[this.name]];  
            }  
            o[this.name].push(this.value || '');  
        }  
        else {  
            o[this.name] = this.value || '';  
        }  
    });  
	
	 var jsonuserinfo = JSON.stringify(o);
	 

    $.ajax({
        type: "POST",   //访问WebService使用Post方式请求
        contentType: "application/json", //WebService 会返回Json类型
        url: "addGroup.do", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
        data: jsonuserinfo,         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到      
        dataType: 'json',
        success: function (result) {     //回调函数，result，返回值
        	closeW();
        	getGroup();
        }
    });
}

function closeW(){
	$('#w').window('close');
}

function closeW3(){
	$('#w3').window('close');
}

function deleteGroup(id,name){
	if (confirm("确认要删除？")) {
		$.post("deleteGroup.do",{groupId:id},function (data, textStatus){        
				if(data==1){
					alert("删除成功！");
					var row = $('#dg').datagrid("getSelected");
					var index = $('#dg').datagrid('getRowIndex', row);
					$('#dg').datagrid("deleteRow",index);
				}
		   },"json");
    }

}


function scanGroup(id,name){
	$.ajaxSetup({cache:false});
	$('#w2').window({title:"("+name+")设备组"});
	$('#dg22').datagrid({
	    url:"queryByGroup.do?groupId="+id,
	    pagination:true,
	    height:340,
	    pageSize:9,
	    pageList:[9],
	    loadMsg:'数据装载中......',
	    columns:[[ 
            {field:'ck',checkbox:true},   
	        {field:'vicId',title:'设备ID',width:100,hidden:true},
	        {field:'vicName',title:'设备名称',width:200},
	        {field:'recordType',title:'检查存储',width:200,formatter:function(value,row){
				if(value==0)
					return "前端存储";
				else if(value==1)
					return "中心存储";
				else
					return "前端和中心存储";
			},editor:{
                type:'combobox',
                options:{
                    valueField:'productid',
                    textField:'productname',
                    url:'products.json',
                    required:true
                }
                }},
                {field:'opter',title:'操作',width:200,formatter:function(value,row,index){
    				return "<div class=\"buttons\"><button class='regular' onclick='edite("+index+");'><img height='10px' src=\"images/auth.png\" alt=\"\"/>修改存储</button>&nbsp;&nbsp;" +
    						"<button class='positive' onclick='updateR("+index+");'><img height='10px' src=\"images/apply2.png\" alt=\"\"/>保存</button></div>";
    			}}
	        
	    ]]
	});
	$('#w2').window('open');
}

function modifyGroup(index){
	$('#dg').datagrid("selectRow",index);
	var row = $('#dg').datagrid("getSelected");
	var id = row['id'];
	$('#w').window('open');
	$('#frmGroup').form('load',{
		id:row['id'],
		name:row['name'],
		status:getStatus2(row['status'])
	});	
}


function getStatus2(status){
	if(status=="启用")
		return 1;
	else
		return 0;
}

function closeW2(){
	$('#w2').window('close');
}

function addDevice(){
	var row = $('#dg').datagrid("getSelected");
	var groupId = row['id'];
	var devices = $('#dg33').datagrid("getSelections");
	var deviceIds="";
	var types="";
	for(var i=0;i<devices.length;i++){
		deviceIds=deviceIds+devices[i]['id']+":";
		types=types+devices[i]['recordType']+":";
	}
	$.post("addGroupDevice.do",{groupId:groupId,deviceIds:deviceIds,types:types},function (data, textStatus){        
		$('#w3').window('close');
		for(var i=0;i<devices.length;i++){
			$('#dg22').datagrid("insertRow",{
				row:{
					ck:'',
					vicId: devices[i]['id'],
					vicName: devices[i]['vicName'],
					recordType:devices[i]['recordType']
				}

			});
		}
		
		
 	   var rows = $('#dg22').datagrid("getRows");
 	   for(var i=rows.length-devices.length;i<rows.length;i++){
 		   $('#dg22').datagrid('beginEdit', i);
	    }
    });
}


function edite(index){
     $('#dg22').datagrid('beginEdit', index);
}
function updateR(index){
	if ($('#dg22').datagrid('validateRow', index)){
       var ed = $('#dg22').datagrid('getEditor', {index:index,field:'recordType'});
       var productname = $(ed.target).combobox('getText');
       $('#dg22').datagrid('getRows')[index]['productname'] = productname;
       $('#dg22').datagrid('endEdit', index);
       
       var rows = $('#dg22').datagrid('getRows'); 
       var row = rows[index];
       
       var row2 = $('#dg').datagrid("getSelected");
       var groupId = row2['id'];
       
       $.post("upGroupDevice.do",{groupId:groupId,deviceId:row['vicId'],type:row['recordType']},function (data, textStatus){  
           alert("保存成功！")
       });
	}
}