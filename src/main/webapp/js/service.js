function getService(){
	$.ajaxSetup({cache:false});
    $('#dg').datagrid({
	    url:"queryService.do",
	    pagination:true,
	    height:320,
	    pageSize:2,
	    pageList:[2],
	    loadMsg:'数据装载中......',
	    columns:[[ 
	        {field:'id',title:'编号',width:100,hidden:true},
	        {field:'name',title:'名称',width:100},
	        {field:'code',title:'功能',width:150},
	        {field:'ip',title:'ip',width:150},
	        {field:'port',title:'端口',width:150},
	        {field:'url',title:'URL',width:150},
	        {field:'acName',title:'用户名',width:150},
	        {field:'acPwd',title:'性密码',width:100},
	        {field:'status',title:'状态',width:100,formatter:function(value,row){
				if(value==0)
					return "停用";
				else
					return "启用";
			}},
            {field:'opter',title:'操作',width:200,formatter:function(value,row,index){
				return "<div class=\"buttons\"><button class='positive' onclick='modifyService("+index+");'><img height='10px' src=\"images/apply2.png\" alt=\"\"/>修改</button>&nbsp;&nbsp;"+
                "<button class='negative' onclick='deleteService(\""+row.id+"\",\""+row.name+"\");'><img height='10px' src=\"images/cross.png\" alt=\"\"/>删除</button></div>";
			}}
	    ]]
	});
}

function addService(){
    var o={};
    var a = $("#frmService").serializeArray();  
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
        url: "addService.do", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
        data: jsonuserinfo,         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到      
        dataType: 'json',
        success: function (result) {     //回调函数，result，返回值
        	closeW();
        	getService();

        }
    });
}

function closeW(){
	$('#w').window('close');
}


function deleteService(id,name){
	if (confirm("确认要删除？")) {
		$.post("deleteService.do",{serviceId:id},function (data, textStatus){        
				if(data==1){
					var row = $('#dg').datagrid("getSelected");
					var index = $('#dg').datagrid('getRowIndex', row);
					$('#dg').datagrid("deleteRow",index);
				}
		   },"json");
    }

}


function modifyService(index){
	$('#dg').datagrid("selectRow",index);
	var row = $('#dg').datagrid("getSelected");
	var id = row['id'];
	$('#w').window({title:"修改服务"});
	$('#w').window('open');
	
	$('#frmService').form('load',{
		id:row['id'],
		name:row['name'],
		code:row['code'],
		ip:row['ip'],
		port:row['port'],
		url:row['url'],
		acName:row['acName'],
		acPwd:row['acPwd'],
		status:row['status']
	});	
}

var toolbar = [{
    text:'添加',
    iconCls:'icon-add',
    handler:function(){
    	$('#w').window({title:"新增服务"});
        $('#w').window('open');
        $('#frmService').form('load',{
            id:null,
            name:"",
            code:1,
            ip:0,
            port:null,
            url:0,
            acName:null,
            acPwd:null,
            status:1
        });
        
    }
}];