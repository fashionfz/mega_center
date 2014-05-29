function openUrl(url){
	$("#center").attr("src",url);
	if(url=="device.html"){
		$('#daohang').accordion('select','设备管理');
		$('#content').panel("setTitle","设备管理 >> DVR管理");
	}else if(url=="user.html"){
		$('#daohang').accordion('select','系统管理');
		$('#content').panel("setTitle","系统管理 >> 用户管理");
	}else if(url=="role.html"){
		$('#daohang').accordion('select','系统管理');
		$('#content').panel("setTitle","系统管理 >> 角色管理");
	}else if(url=="menu.html"){
		$('#daohang').accordion('select','系统管理');
		$('#content').panel("setTitle","系统管理 >> 菜单管理");
	}else if(url=="service.html"){
		$('#daohang').accordion('select','系统管理');
		$('#content').panel("setTitle","系统管理 >> 服务管理");		
	}else if(url=="licence.html"){
		$('#daohang').accordion('select','系统帮助');
		$('#content').panel("setTitle","系统帮助 >> 授权管理");
	}else if(url=="check_report.html"){
		$('#daohang').accordion('select','检测管理');
		$('#content').panel("setTitle","检测管理 >> 检查报告");
	}else if(url=="history_report.html"){
		$('#daohang').accordion('select','检测管理');
		$('#content').panel("setTitle","检测管理 >> 历史报告");
	}else if(url=="check_log.html"){
		$('#daohang').accordion('select','检测管理');
		$('#content').panel("setTitle","检测管理 >> 检查日志");
	}else if(url=="devicegroup.html"){
		$('#daohang').accordion('select','设备管理');
		$('#content').panel("setTitle","设备管理 >> 设备组管理");
	}else if(url=="checkconfig.html"){
		$('#daohang').accordion('select','检测管理');
		$('#content').panel("setTitle","检测管理 >> 检测配置");
	}
}

function openReport(url){
	$('#pop').hide();
	$("#center").attr("src",url);
	$('#daohang').accordion('select','检测管理');
	$('#content').panel("setTitle","检测管理 >> 检测报告");
}

//amq
