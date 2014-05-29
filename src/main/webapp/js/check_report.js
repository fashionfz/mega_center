function init(){
        showReport2();
		showReport();
}


function showReport2(){
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
            data: [6],
            color:'gray'
        },{
            name: '正常',
            data: [8],
            color:'green'
        },{
            name: '错误',
            data: [2],
            color:'red'
        }]
    });
    
}


function showReport(){
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
            data: [0, 8],
            color:'green',
            y:5
        }, {
            name: '错误',
            data: [0, 2],
            color:'red',
            y:0
        },{
            name: '原始',
            data: [0, 10],
            color:'yellow'
        }]
    });
}