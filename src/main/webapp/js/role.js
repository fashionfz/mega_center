function getRole() {
	$.ajaxSetup({
		cache : false
	});
	$('#dg')
			.datagrid(
					{
						url : "queryRole.do",
						pagination : true,
						height : 320,
						pageSize : 2,
						pageList : [ 2 ],
						loadMsg : '数据装载中......',
						sortName : 'name',
						sortOrder : 'desc',
						frozenColumns : [ [ {
							field : 'ck',
							checkbox : true
						} ] ],
						columns : [ [
								{
									field : 'id',
									title : '编号',
									width : 150
								},
								{
									field : 'name',
									title : '名称',
									width : 150
								},
								{
									field : 'status',
									title : '状态',
									width : 100,
									formatter : function(value, row) {
										if (value == 0)
											return "停用";
										else
											return "启用";
									}
								},
								{
									field : 'remark',
									title : '备注',
									width : 100
								},
								{
									field : 'opter',
									title : '操作',
									width : 200,
									formatter : function(value, row, index) {
										return "<div class=\"buttons\"><button class='positive' onclick='modifyRole("
												+ index
												+ ");'><img height='10px' src=\"images/apply2.png\" alt=\"\"/>修改</button>&nbsp;&nbsp;"
												+ "<button class='negative' onclick='deleteRole(\""
												+ row.id
												+ "\",\""
												+ row.name
												+ "\");'><img height='10px' src=\"images/cross.png\" alt=\"\"/>删除</button>&nbsp;&nbsp;"
												+ "<button class='regular' onclick='anth(\""
												+ row.id
												+ "\",\""
												+ row.name
												+ "\");'><img height='10px' src=\"images/auth.png\" alt=\"\"/>授权</button></div>";
									}
								} ] ]
					});
}

function addRole22() {
	var o = {};
	var a = $("#frmRole").serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});

	var jsonuserinfo = JSON.stringify(o);
	$.ajax({
		type : "POST", // 访问WebService使用Post方式请求
		contentType : "application/json", // WebService 会返回Json类型
		url : "addRole.do", // 调用WebService的地址和方法名称组合 ---- WsURL/方法名
		data : jsonuserinfo, // 这里是要传递的参数，格式为 data:
								// "{paraName:paraValue}",下面将会看到
		dataType : 'json',
		success : function(result) { // 回调函数，result，返回值
			closeW();
			init();
		}
	});

}

function RateArticleSuccess(result) {
	alert(result.UserName + result.Mobile + result.Pwd);
}
function closeW() {
	$('#w').window('close');
}

function anth(id, name) {
	$('#w2').window({
		title : "(" + name + ")角色授权"
	});
	$('#dg11').datagrid({
		url : "queryAnthMenu.do?roleId=" + id,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : '功能编号',
			width : 50
		}, {
			field : 'name',
			title : '功能名称',
			width : 100
		} ] ]
	});

	$('#dg22').datagrid({
		url : "queryUnAnthMenu.do?roleId=" + id,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : '功能编号',
			width : 50
		}, {
			field : 'name',
			title : '功能名称',
			width : 100
		} ] ]
	});

	$('#w2').window('open');

}

function addRole() {
	var roles = $('#dg22').datagrid("getSelections");
	for ( var i = 0; i < roles.length; i++) {
		$('#dg11').datagrid("insertRow", {
			row : {
				ck : '',
				id : roles[i]['id'],
				name : roles[i]['name']
			}

		});
		var index = $('#dg22').datagrid('getRowIndex', roles[i]);
		$('#dg22').datagrid("deleteRow", index);
		$('#dg22').datagrid("clearSelections");
	}
}

function removeRole() {
	var roles = $('#dg11').datagrid("getSelections");
	for ( var i = 0; i < roles.length; i++) {
		$('#dg22').datagrid("insertRow", {
			row : {
				ck : '',
				id : roles[i]['id'],
				name : roles[i]['name']
			}

		});
		var index = $('#dg11').datagrid('getRowIndex', roles[i]);
		$('#dg11').datagrid("deleteRow", index);
		$('#dg11').datagrid("clearSelections");
	}

}

function closeW2() {
	$('#w2').window('close');
}
function saveAnth() {
	var row = $('#dg').datagrid("getSelected");
	var roleId = row['id'];
	var menuIds = "";
	var menus = $('#dg11').datagrid("getRows");
	for ( var i = 0; i < menus.length; i++) {
		menuIds = menuIds + menus[i]['id'] + ":";
	}

	$.post("roleAuthMenu.do", {
		roleId : roleId,
		menuIds : menuIds
	}, function(data, textStatus) {
		$('#w2').window('close');

	});

}

function deleteRole(id, name) {
	if (confirm("确认要删除？")) {
		$.post("deleteRole.do", {
			roleId : id
		}, function(data, textStatus) {
			if (data == 1) {
				var row = $('#dg').datagrid("getSelected");
				var index = $('#dg').datagrid('getRowIndex', row);
				$('#dg').datagrid("deleteRow", index);
			}
		}, "json");
	}

}

function modifyRole(index) {
	$('#dg').datagrid("selectRow", index);
	var row = $('#dg').datagrid("getSelected");
	var id = row['id'];
	$('#w').window('open');
	$('#frmRole').form('load', {
		id : row['id'],
		name : row['name'],
		status : row['status'],
		remark : row['remark']
	});
}
