//------------- charts.js -------------//
InitServerList();
			
		function InitServerList()
		{

		$.ajax({
		type: "POST",
		url: "user/GetServers",
		success: function(data)
		{
		if(data.includes("登录"))
        window.location.href = "login.jsp";
        
		var da = JSON.parse(data);
		var string = "";
		
		string += spawnOption("ALL","全部服务器");
		
		
		for(var i = 0 ; i < da.length ; i++)
		{
		string+= spawnOption(da[i].serverName,da[i].serverName)
		}
		
		
		$("#serverInfo").html(string)
		},
		error: function(da)
		{
		
		}
		})
		}
			
		
function spawnOption(v,k)
{
//<option value="audi">Audi</option>
var string = '<option value="' + v + '">' + k  + '</option>';
return string;
}			

var objColors = 
{
black: "#000",
blue: "#75b9e6",
brown: "#d1b993",
dark: "#79859b",
gray: "#f3f5f6",
green: "#71d398",
lime: "#a8db43",
mageta: "#eb45a7",
orange: "#f4b162",
pink: "#f78db8",
purple: "#af91e1",
red: "#f68484",
teal: "#97d3c5",
white: "#fff",
yellow: "#ffcc66"
} 
$(document).ready(function() {

	InitSellInfo();
	InitLineCharts();
	
	//------------- Sparklines in sidebar -------------//
	$('#usage-sparkline').sparkline([35,46,24,56,68, 35,46,24,56,68], {
		width: '180px',
		height: '30px',
		lineColor: colours.dark,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});

	$('#cpu-sparkline').sparkline([22,78,43,32,55, 67,83,35,44,56], {
		width: '180px',
		height: '30px',
		lineColor: colours.dark,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});

	$('#ram-sparkline').sparkline([12,24,32,22,15, 17,8,23,17,14], {
		width: '180px',
		height: '30px',
		lineColor: colours.dark,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});

    //------------- Init pie charts -------------//
    //pass the variables to pie chart init function
    //first is line width, size for pie, animated time , and colours object for theming.
	initPieChart(10,40, 1500, colours);
	initPieChartPage(20,100,1500, colours);

 	
});

	var colours = {
		white: objColors.white,
		dark: objColors.dark,
		red : objColors.red,
		blue: objColors.blue,
		green : objColors.green,
		yellow: objColors.yellow,
		brown: objColors.brown,
		orange : objColors.orange,
		purple : objColors.purple,
		pink : objColors.pink,
		lime : objColors.lime,
		magenta: objColors.magenta,
		teal: objColors.teal,
		textcolor: '#5a5e63',
		gray: objColors.gray
	}

	//generate random number for charts
	randNum = function(){
		return (Math.floor( Math.random()* (1+150-50) ) ) + 80;
	}

//------------- Line charts style 2 -------------//
	function InitLineCharts(){
	
	            var requestURL = "";
		        if($("#serverInfo").val()=="ALL")
				requestURL = "user/GetSellInfo?nocache=" + Math.random();
				else
				requestURL = "user/GetSellInfo?nocache=" + Math.random() + "&servername=" + $("#serverInfo").val();
				
	            var data = []
	            $.ajax({
                type: "POST",
                url: requestURL,
                success: function (da) {  
           
                  data = eval("("+da+")")
                  
				var d1 = [];
		
		for(var key in data)
		{
		if(key=="totalPrice") continue;
		var d = [];
		d.push(key)
		d.push(data[key])
		d1.push(d)
		}
		var compare = function(arr1,arr2)
		{
		var day1 = new Date(Date.parse(arr1[0]));
		var day2 = new Date(Date.parse(arr2[0]));
		if(day1>day2) return 1;
		else if(day1<day2) return -1;
		else return 0;
		}
		d1.sort(compare);

		//var d1 = [["周一", data[0]], ["周二", data[1]], ["周三", data[2]], ["周四", data[3]], ["周五",data[4]], ["周六",data[5]], ["周日", data[6]]];
		
		//for(var i =1;i<=7;i++)
		//d1[i][1] = data[i.toString()]
		
		var total = 0;
		
        for(var key in data)
		if(key!="totalPrice")
		total+=data[key];
		
		
    	var options = {
    		grid: {
				show: true,
			    labelMargin: 20,
			    axisMargin: 40, 
			    borderWidth: 0,
			    borderColor:null,
			    minBorderMargin: 20,
			    clickable: true, 
			    hoverable: true,
			    autoHighlight: true,
			    mouseActiveRadius: 100
			},
			series: {
				grow: {
		            active: true,
		     		duration: 1000
		        },
	            lines: {
            		show: true,
            		fill: false,
            		lineWidth: 2.5
	            },
	            points: {
	            	show:true,
	            	radius: 5,
	            	lineWidth: 3.0,
	            	fill: true,
	            	fillColor: colours.red,
	            	strokeColor: colours.white 
	            }
	        },
	        colors: [colours.dark, colours.red],
	        legend: { 
	        	position: "ne", 
	        	margin: [0,-25], 
	        	noColumns: 0,
	        	labelBoxBorderColor: null,
	        	labelFormatter: function(label, series) {
				    return '<div style="padding: 10px; font-size:20px;font-weight:bold;color:'+colours.textcolor+'">'+ label + ': ￥'+ total +'</div>';
				},
				backgroundColor: colours.white,
    			backgroundOpacity: 0.5,
    			hideSquare: true //hide square color helper 
	    	},
	        shadowSize:0,
	        tooltip: true, //activate tooltip
			tooltipOpts: {
				content: "%y.0(元)",
				shifts: {
					x: -45,
					y: -50
				},
				defaultTheme: false
			},
			yaxis: { 
				show:false
			},
			xaxis: { 
	        	mode: "categories",
	        	tickLength: 0
	        }
    	}

		var plot = $.plot($("#line-chart-dots2"),[{
    			label: "近一周总收入", 
    			data: d1,
    		}], options
    	);
		
                }
                })
                             



	}

	
	function InitSellInfo() {	
    	
        var requestURL = "";
        if($("#serverInfo").val()=="ALL")
		requestURL = "user/GetSellCount?nocache=" + Math.random();
		else
		requestURL = "user/GetSellCount?nocache=" + Math.random() + "&servername=" + $("#serverInfo").val();
              $.ajax({
        type: "POST",
        url: requestURL,
        success: function (da) {  
          var dataObj=eval("("+da+")")
          
          var largest = 0;
          
          var data = [];
          var dataCnt = 0;
          var totalPrice = dataObj["totalPrice"];
          var totalCount = dataObj["totalCount"];
		  var ttotalPrice = dataObj["ttotalPrice"];
          var ttotalCount = dataObj["ttotalCount"];
		  $("#totalCount").html(totalCount)
		  $("#ttotalCount").html(ttotalCount)
		  
		  $("#totalPrice").html("￥" + totalPrice)
		  $("#ttotalPrice").html("￥" + ttotalPrice)
       


        }
                     })

}
	
//Setup easy pie charts in sidebar
var initPieChart = function(lineWidth, size, animateTime, colours) {
	$(".pie-chart").easyPieChart({
        barColor: colours.dark,
        borderColor: colours.dark,
        trackColor: '#d9dde2',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });
}

//Setup easy pie charts in page
var initPieChartPage = function(lineWidth, size, animateTime, colours) {

	$(".easy-pie-chart").easyPieChart({
        barColor: colours.dark,
        borderColor: colours.dark,
        trackColor: colours.gray,
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });
    $(".easy-pie-chart-red").easyPieChart({
        barColor: colours.red,
        borderColor: colours.red,
        trackColor: '#fbccbf',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });
    $(".easy-pie-chart-green").easyPieChart({
        barColor: colours.green,
        borderColor: colours.green,
        trackColor: '#b1f8b1',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });
    $(".easy-pie-chart-blue").easyPieChart({
        barColor: colours.blue,
        borderColor: colours.blue,
        trackColor: '#d2e4fb',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });
    $(".easy-pie-chart-teal").easyPieChart({
        barColor: colours.teal,
        borderColor: colours.teal,
        trackColor: '#c3e5e5',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });
    $(".easy-pie-chart-purple").easyPieChart({
        barColor: colours.purple,
        borderColor: colours.purple,
        trackColor: '#dec1f5',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });

}

function SelectChange()
{
	InitSellInfo();
	InitLineCharts();
}

 
function InitOrderDetailTable()
{
$.ajax({
type: "POST",
url: "user/GetOrderDetail?nocache=" + Math.random(),
success: function(data)
{
	
var da = JSON.parse(data);
for(var i=0;i<da.length;i++)
{
var s = "否";
if(da[i].isFinished || da[i].isFinished=="1")  
{
s="是";
}

$("#datatable1 tbody").append('<tr class="odd gradeA"><td>'+ da[i].id + '</td>'+ '<td>' + da[i].userID +'</td>' + '<td>' + da[i].goodName +'</td>' + '<td>' +  da[i].price + '</td>' + '<td>' + da[i].applyTime +'</td>' + '<td>' +  da[i].serverName + '</td>' + '<td>' +  s + '</td>' +'</tr>')   
}
},
error: function(data)
{

}
})
}


InitOrderDetailTable();

function InitCleanMoney()
{
$.ajax({
type: "POST",
url: "user/CalCleanMoney?nocache=" + Math.random(),
success: function(data)
{
   $("#hasCleanMoney").html("￥" + data)
},
})

$.ajax({
type: "POST",
url: "user/CalNotCleanMoney?nocache=" + Math.random(),
success: function(data)
{
   $("#canCleanMoney").html("￥" + data)
},
})


}
InitCleanMoney();

function fileupload3(){
    var formData = new FormData($("#form5")[0]);
	showWaitModal();
        $.ajax({
                url:'load/upload2',
                type:'post',
                data:formData,
                //必须false才会自动加上正确的Content-Type
                contentType: false,
                //必须false才会避开jQuery对 formdata 的默认处理
                //XMLHttpRequest会对 formdata 进行正确的处理
                processData: false,
                success:function(da){
                    if(da.includes("成功"))
                {
                alert("成功上传二维码！")
                }
				else
				{
				alert(da)	
				}
                
                hideWaitModal()
                },
                error:function(data){
                    alert("后台发生异常");
					hideWaitModal()
                },
                cache:false,
                async:true
            }); 
}

	
function showfdiv()
{
var div = document.getElementById('fdiv');
$("#fdiv").attr("style","display:block;");
}

function closefdiv()
{
//$("#fdiv").hide();
}

function INITQRCODE()
{
$.ajax({
type: "POST",
url: "user/GetQRCODE?nocache=" + Math.random() ,
success: function(data)
{
if(data=="notset")return;
$("#fdivimg").attr("src",data);
},
})
}
INITQRCODE();

function hideWaitModal(){
		$('#waitModal').modal('hide');
	}
	
	function showWaitModal(){
		$('#waitModal').modal({backdrop:'static',keyboard:false});
	}
	
