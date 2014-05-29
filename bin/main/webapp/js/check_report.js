function init(){
    	$.ajaxSetup({cache:false});
        $.getJSON("queryCheckCount.do?mainId="+Request("mainId"), function(data){
        	showReport2(data['allCount'],data['checkCount'],data['errorCount']);
          });
        
        getCheck();
        
        
        $('#dg').datagrid({
        	onDblClickRow: function(index,rowData){
        		var row = $('#dg').datagrid("getSelected");
        		showReport(rowData['localRecordCycle'],index+2,rowData['localRecordCycle']-index-2);
        		parent.sendMsg(encodeURI(row.vicName+"已被"+parent.getUserName()+"锁定"));
        		
        	       $.getJSON("queryCheckLog.do?testId="+rowData['id'], function(data){
        	            $.each(data, function(i,item){
        	            	//alert(item.errorNode);
        	            });
        	            
        	       });
            }
        });
        
}


function getCheck(){
	$.ajaxSetup({cache:false});
    $('#dg').datagrid({
	    url:"queryCheckReport.do",
	    pagination:true,
	    height:260,
	    pageSize:7,
	    pageList:[7],
	    loadMsg:'数据装载中......',
	    columns:[[ 
	        {field:'id',title:'编号',width:100,hidden:true},
	        {field:'deviceId',title:'设备编号',width:100,hidden:true},
	        {field:'vicName',title:'摄像头名称',width:100},
	        {field:'dvrName',title:'DVR名称',width:150},
	        {field:'dvrIp',title:'DVR ip',width:150},
	        {field:'localRecordCycle',title:'存储周期',width:150},
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
	        {field:'timeStr',title:'检查时间',width:100}
	    ]]
	});
}
    
    
function Request(strName) 
{ 
	var strHref = window.document.location.href; 
	var intPos = strHref.indexOf("?"); 
	var strRight = strHref.substr(intPos + 1); 
	var arrTmp = strRight.split("&"); 
	for(var i = 0; i < arrTmp.length; i++) 
	{ 
		var arrTemp = arrTmp[i].split("="); 
		if(arrTemp[0].toUpperCase() == strName.toUpperCase()) 
			return arrTemp[1]; 
		
	} 
	return ""; 
	
}






function showReport2(all,check,error){
                var chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
            defaultSeriesType: 'bar'
        },
        title: {
            text: '视频检测报告'
        },
        xAxis: {
            min: 0,
            categories: ['错误比']
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        legend: {
            backgroundColor: '#FFFFFF',
            reversed: true
        },
        tooltip: {
            formatter: function() {
                return ''+
                     this.series.name +': '+ this.y +'';
            }
        },
        plotOptions: {
            series: {
                stacking: 'normal',
                pointWidth: 30
            }

        },
        series: [{
            name: '未检查',
            data: [all-check],
            color:'gray'
        },{
            name: '正常',
            data: [check-error],
            color:'green'
        },{
            name: '错误',
            data: [error],
            color:'red'
        }]
    });
    
}


function showReport(all,error,right){
	var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container2',
            defaultSeriesType: 'bar',
            height:200
        },
        title: {
            text: '检测详情'
        },
        xAxis: {
            categories: ['存储策略:', '实际存储:']
        },
        yAxis: {
            min: 0,
            title: {
                text: '时间（天）'
            }
        },
        legend: {
            backgroundColor: '#FFFFFF',
            reversed: true
        },
        tooltip: {
            formatter: function() {
                return ''+
                     this.series.name +': '+ this.y +'';
            }
        },
        plotOptions: {
            series: {
                stacking: 'normal',
                pointWidth: 20,
                events: {
                    click: function(event) {
                        alert(this.name);
                    }
                }
            }
        },
        series: [{
            name: '正常',
            data: [0, right],
            color:'green',
            y:5
        }, {
            name: '错误',
            data: [0, error],
            color:'red',
            y:0
        },{
            name: '原始',
            data: [all, 0],
            color:'yellow'
        }]
    });
}