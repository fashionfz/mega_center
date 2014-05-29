function getMenu() {
	$.ajaxSetup({
		cache : false
	});
	$('#dg')
			.datagrid(
					{
						url : "queryMenu.do",
						pagination : true,
						height : 400,
						pageSize : 10,
						pageList : [ 10 ],
						loadMsg : '数据装载中......',
						columns : [ [
								{
									field : 'id',
									title : '编号',
									width : 100,
									hidden : true
								},
								{
									field : 'name',
									title : '名称',
									width : 100
								},
								{
									field : 'url',
									title : 'URL',
									width : 150
								},
								{
									field : 'icon',
									title : '图标',
									width : 100
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
									field : 'opter',
									title : '操作',
									width : 200,
									formatter : function(value, row, index) {
										return "<div class=\"buttons\"><button class='positive' onclick='modifyMenu("
												+ index
												+ ");'><img height='10px' src=\"images/apply2.png\" alt=\"\"/>修改</button>&nbsp;&nbsp;"
												+ "<button class='negative' onclick='deleteMenu(\""
												+ row.id
												+ "\",\""
												+ row.name
												+ "\");'><img height='10px' src=\"images/cross.png\" alt=\"\"/>删除</button></div>";
									}
								} ] ]
					});
}

function addMenu() {
	var o = {};
	var a = $("#frmMenu").serializeArray();
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
		url : "addMenu.do", // 调用WebService的地址和方法名称组合 ---- WsURL/方法名
		data : jsonuserinfo, // 这里是要传递的参数，格式为 data:
								// "{paraName:paraValue}",下面将会看到
		dataType : 'json',
		success : function(result) { // 回调函数，result，返回值
			closeW();
			init();
		}
	});
}

function closeW() {
	$('#w').window('close');
}

function deleteMenu(id, name) {
	if (confirm("确认要删除？")) {
		$.post("deleteMenu.do", {
			menuId : id
		}, function(data, textStatus) {
			if (data == 1) {
				var row = $('#dg').datagrid("getSelected");
				var index = $('#dg').datagrid('getRowIndex', row);
				$('#dg').datagrid("deleteRow", index);
			}
		}, "json");
	}

}

function modifyMenu(index) {
	$('#dg').datagrid("selectRow", index);
	var row = $('#dg').datagrid("getSelected");
	var id = row['id'];
	$('#w').window('open');
	$('#frmMenu').form('load', {
		id : row['id'],
		name : row['name'],
		url : row['url'],
		icon : row['icon'],
		status : row['status']
	});
}