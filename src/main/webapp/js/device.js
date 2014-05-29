var startPoint=0;
var startPointX=0;
var startPointY=0;
var endPoint=0;
var endPointX=0;
var endPointY=0;
var startX=0;
var startY=0;
var endX=0;
var endY=0;
var mouseStauts=0;
var flag=true;
function select(id,e){
	endX=getEvent(e).x;
	endY=getEvent(e).y;
	endPoint =id;
	endPointY=endPoint%100;
	endPointX=parseInt(endPoint/100);
	
/*	flag=true;
	if(mouseStauts==1){
	    $("#main tr").each(function(trindex,tritem){
	        $(tritem).find("td").each(function(tdindex,tditem){
	        	if(endX>startX&&endY>startY){
		        	if(flag){
		        	   $(tditem).addClass("td1");
		            }
		            if($(tditem).attr("id")==id){
		            	flag=false;
		            }
	        	}
	            
	        });
	    });
	}*/
	
	if(mouseStauts==1){
		if(endX>startX){
		     for(var x=startPointX;x<endPointX+1;x++){
		            if(endY>startY){
		            	for(var y=startPointY;y<endPointY+1;y++){
		            		if(y<10){
		            			$("#"+x+"0"+y).addClass("td1");
		            		}else{
		            			$("#"+x+y).addClass("td1");
		            		}
		            		
		            	}
		            }
		        
		     }
		}
	}
}
function start(id,e){
	mouseStauts=1;
	startX=getEvent(e).x;
	startY=getEvent(e).y;
	startPoint =id;
	startPointY=startPoint%100;
	startPointX=parseInt(startPoint/100);
	//alert(startPointX+"."+startPointY);
}
function end(){
	mouseStauts=0;
}
function savePlot(){
	
	var row = $('#dg').datagrid("getSelected");
	var deviceId = row['inv'];
	var plot="";
	$("#main tr").each(function(trindex,tritem){
        $(tritem).find("td").each(function(tdindex,tditem){
            //tableData[trindex][tdindex]=$(tditem).text();
            //alert($(tditem).attr("class"));
            if($(tditem).attr("class")=="td1"){
            	plot=plot+"1";
            }else{
            	plot=plot+"0";
            }
        });
    });
	//保存存储策略
	$.post("savePlot.do",{deviceId:deviceId,plot:plot},function (data, textStatus){  
		alert("保存成功！");
		$('#w2').window('close');
		query();
	   });
}


function resetPlot(){
	$("#main tr").each(function(trindex,tritem){
        $(tritem).find("td").each(function(tdindex,tditem){
            $(tditem).removeClass("td1");
        });
    });
}


function getEvent(e){                                  
    if (typeof e == 'undefined'){                                               
        e = window.event;                                   
        
    }                                       
    //alert(e.x?e.x : e.layerX);                                        
    if(typeof e.x == 'undefined'){                                              
        e.x = e.layerX;                                     
        
    }                                       
    if(typeof e.y == 'undefined'){                                          
        e.y = e.layerY;                                     
        
    }                                       
    return e;                               
    
} 



function execlLoad(){
	var para = $("#frmQuery").serialize(); 
	$("#excel").attr("src","deviceExcelOut.do?"+para);
}

function closeW(){
	$('#w').window('close');
}

function closeW4(){
	$('#w4').window('close');
}

function saveDevice(){
    var o={};
    var a = $("#frmDevice").serializeArray();  
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
        url: "addDevice.do", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
        data: jsonuserinfo,         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到      
        dataType: 'json',
        success: function (result) {     //回调函数，result，返回值
        	closeW();
        	if(result==0){
            	query();
        	}else if(result==1){
        		alert("设备已超出授权范围！");
        	}else if(result==99){
        		alert("licence非法！");
        	}
        }
    });

}


function deleteDevice(id){
	if (confirm("确认要删除？")) {
		$.post("deleteDevice.do",{deviceId:id},function (data, textStatus){        
				if(data==1){
					var row = $('#dg').datagrid("getSelected");
					var index = $('#dg').datagrid('getRowIndex', row);
					$('#dg').datagrid("deleteRow",index);
				}
		   },"json");
    }
}

function execlIn(){
	$('#w4').window('open');
}



function query(){
    var o={};
    var a = $("#frmQuery").serializeArray();  
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
	 getDevice(o);
}




function getDevice(jsonuserinfo){
	$.ajaxSetup({cache:false});
    $('#dg').datagrid({
	    url:"queryDevice.do",
	    queryParams:jsonuserinfo,
	    pagination:true,
	    height:340,
	    pageSize:10,
	    pageList:[10],
	    loadMsg:'数据装载中......',
	    frozenColumns:[[
	                    {field:'ck',checkbox:true}
	                   ]],
	    columns:[[ 
	        {field:'id',title:'编号',width:100,hidden:true},
	        {field:'vicName',title:'摄像头名称',width:100},
	        {field:'dvrName',title:'DVR名称',width:100},
	        {field:'dvrIp',title:'DVR-ip',width:100},
	        {field:'dvrPort',title:'DVR-port',width:100},
	        {field:'dvrUserName',title:'DVR用户名',width:100},
	        {field:'dvrPassword',title:'DVR密码',width:100},
	        {field:'vicType',title:'类型',width:50,formatter:function(value,row){
				if(value==1)
					return "大厅";
				else if(value==2)
					return "加钞间";
				else if(value==3)
					return "ATM环境";
				else if(value==4)
					return "营业区环境";
				else if(value==5)
					return "柜员";
				else
					return "未分类";
			}},
	        {field:'recordCycle',title:'本地录像周期',width:100},
	        {field:'recordCycleRemote',title:'中心录像周期',width:100},
	        {field:'channel',title:'通道',width:50},
	        {field:'recordPlot',title:'编辑中心存储策略',width:100,formatter:function(value,row,index){
	        	if(value==null||value=="null")
	        		return "<div class=\"buttons\"><button class='regular' onclick='setPlot(\""+row.id+"\",\""+row.vicName+"\")'><img height='10px' src=\"images/auth.png\" alt=\"\"/>未编辑</button></div>";
	        	else
	        		return "<div class=\"buttons\"><button class='positive' onclick='setPlot(\""+row.id+"\",\""+row.vicName+"\")'><img height='10px' src=\"images/apply2.png\" alt=\"\"/>已编辑</button></div>";
			}},
            {field:'opter',title:'操作',width:200,formatter:function(value,row,index){
				return "<div class=\"buttons\"><button class='positive' onclick='modifyDevice("+index+")'><img height='10px' src=\"images/auth.png\" alt=\"\"/>修改</button>&nbsp;&nbsp;"+
                "<button class='negative' onclick='deleteDevice(\""+row.id+"\")'><img height='10px' src=\"images/cross.png\" alt=\"\"/>删除</button></div>";
			}}
	    ]]
	});	
}



function modifyDevice(index){
	$('#dg').datagrid("selectRow",index);
	var row = $('#dg').datagrid("getSelected");
	$('#w').window('open');
    $('#frmDevice').form('load',{
        id:row['inv'],
        vicName:row['vicName'],
        dvrName:row['dvrName'],
        dvrIp:row['dvrIp'],
        dvrPort:row['dvrPort'],
        dvrUserName:row['dvrUserName'],
        dvrPassword:row['dvrPassword'],
        channel:row['channel'],
        recordCycle:row['recordCycle'],
        recordCycleRemote:row['recordCycleRemote'],
        vicType:row['vicType']
    });
}


function addDevice(){
	$('#w').window('open');
    $('#frmDevice').form('load',{
        id:null,
        vicName:"",
        dvrName:"",
        dvrIp:"",
        dvrUserName:"",
        dvrPassword:"",
        channel:"",
        recordCycle:"",
        vicType:-1
    });
	
}
function setPlot(id,name){
	   $("#main tr").each(function(trindex,tritem){
	        $(tritem).find("td").each(function(tdindex,tditem){
	            $(tditem).removeClass("td1");
	        });
	    });
	   
	$('#w2').window({title:"("+name+")存储策略"});
	$('#w2').window('open');
}