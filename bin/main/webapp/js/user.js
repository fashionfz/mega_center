function getUser(){
	$.ajaxSetup({cache:false});
    $('#dg').datagrid({
	    url:"queryUser.do",
	    pagination:true,
	    height:320,
	    pageSize:2,
	    pageList:[2],
	    loadMsg:'数据装载中......',
	    sortName:'name',
	    sortOrder:'desc',
	    frozenColumns:[[
	                    {field:'ck',checkbox:true}
	                   ]],
	    columns:[[ 
	        {field:'id',title:'编号',width:100,hidden:true},
	        {field:'name',title:'名称',width:100},
	        {field:'password',title:'密码',width:150},
	        {field:'sex',title:'性别',width:100,formatter:function(value,row){
				if(value==0)
					return "女";
				else
					return "男";
			}},
	        {field:'age',title:'年龄',width:100},
	        {field:'isAdmin',title:'是否管理员',width:100,formatter:function(value,row){
				if(value==0)
					return "否";
				else
					return "是";
			}},
	        {field:'status',title:'状态',width:100,formatter:function(value,row){
				if(value==0)
					return "停用";
				else
					return "启用";
			}},
	        {field:'note',title:'备注',width:100},
            {field:'opter',title:'操作',width:200,formatter:function(value,row,index){
				return "<div class=\"buttons\"><button class='positive' onclick='modifyUser("+index+");'><img height='10px' src=\"images/apply2.png\" alt=\"\"/>修改</button>&nbsp;&nbsp;"+
                "<button class='negative' onclick='deleteUser(\""+row.id+"\",\""+row.name+"\");'><img height='10px' src=\"images/cross.png\" alt=\"\"/>删除</button>&nbsp;&nbsp;"+
                "<button class='regular' onclick='anth(\""+row.id+"\",\""+row.name+"\");'><img height='10px' src=\"images/auth.png\" alt=\"\"/>授权</button></div>"
			}}
	    ]]
	});
}

function addUser(){
    var o={};
    var a = $("#frmUser").serializeArray();  
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
        url: "addUser.do", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
        data: jsonuserinfo,         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到      
        dataType: 'json',
        success: function (result) {     //回调函数，result，返回值
        	closeW();
        	if(result==0){
        		getUser();
        	}else if(result==1){
        		alert("用户已超出授权范围！");
        	}else if(result==99){
        		alert("licence非法！");
        	}

        }
    });
}

function closeW(){
	$('#w').window('close');
}
function anth(id,name){
	$.ajaxSetup({cache:false});
	$('#w2').window({title:"("+name+")用户授权"});
	$('#dg11').datagrid({
	    url:"queryAnthRole.do?userId="+id,
	    columns:[[
            {field:'ck',checkbox:true},   
	        {field:'id',title:'角色编号',width:50},
	        {field:'name',title:'角色名称',width:100}
	    ]]
	});
	
	$('#dg22').datagrid({
	    url:"queryUnAnthRole.do?userId="+id,
	    columns:[[
            {field:'ck',checkbox:true},   
	        {field:'id',title:'角色编号',width:50},
	        {field:'name',title:'角色名称',width:100}
	    ]]
	});

	$('#w2').window('open');
	

}


function addRole(){
	var roles = $('#dg22').datagrid("getSelections");
	for(var i=0;i<roles.length;i++){
		$('#dg11').datagrid("insertRow",{
			row:{
				ck:'',
				id: roles[i]['id'],
				name: roles[i]['name']
			}

		});
		var index = $('#dg22').datagrid('getRowIndex', roles[i]);
		$('#dg22').datagrid("deleteRow",index);
		$('#dg22').datagrid("clearSelections");
	}
}

function removeRole(){
	var roles = $('#dg11').datagrid("getSelections");
	for(var i=0;i<roles.length;i++){
		$('#dg22').datagrid("insertRow",{
			row:{
				ck:'',
				id: roles[i]['id'],
				name: roles[i]['name']
			}

		});
		var index = $('#dg11').datagrid('getRowIndex', roles[i]);
		$('#dg11').datagrid("deleteRow",index);
		$('#dg11').datagrid("clearSelections");
	}
	
}

function closeW2(){
	$('#w2').window('close');
}
function saveAnth(){
	var row = $('#dg').datagrid("getSelected");
	var userId = row['id'];
	var roleIds="";
	var roles = $('#dg11').datagrid("getRows");
	for(var i=0;i<roles.length;i++){
		roleIds=roleIds+roles[i]['id']+":";
	}	
	
	$.post("userAuthRole.do",{userId:userId,roleIds:roleIds},function (data, textStatus){        
		$('#w2').window('close');
	   });
	
}

function deleteUser(id,name){
	if (confirm("确认要删除？")) {
		if(name=="megaeyes"){
			alert("超级用户不能删除！");
			return;
		}
		$.post("deleteUser.do",{userId:id},function (data, textStatus){        
				if(data==1){
					var row = $('#dg').datagrid("getSelected");
					var index = $('#dg').datagrid('getRowIndex', row);
					$('#dg').datagrid("deleteRow",index);
				}
		   },"json");
    }

}


function modifyUser(index){
	$('#dg').datagrid("selectRow",index);
	var row = $('#dg').datagrid("getSelected");
	var id = row['id'];
	$('#w').window({title:"修改用户"});
	$('#w').window('open');
	$('#frmUser').form('load',{
		id:row['id'],
		name:row['name'],
		password:row['password'],
		sex:row['sex'],
		age:row['age'],
		isAdmin:row['isAdmin'],
		status:row['status'],
		note:row['note']
	});	
}

var toolbar = [{
    text:'添加',
    iconCls:'icon-add',
    handler:function(){
    	$('#w').window({title:"新增用户"});
        $('#w').window('open');
        $('#frmUser').form('load',{
            id:null,
            name:"",
            password:"",
            sex:1,
            age:null,
            isAdmin:0,
            status:1,
            note:""
        });
        
    }
}];