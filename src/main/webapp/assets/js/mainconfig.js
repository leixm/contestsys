
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
		
		string += spawnOption("empty","请选择服务器");
		
		
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
		

		var currentConfig = new Object();
		
		function SelectChange()
		{
		var selectServer = $("#serverInfo").val();
		$("#staticGoods").html("")
		$("#titleGoods").html("")
		
		if(selectServer=="empty")
		{
		return;
		}
		
		
		
		$.ajax({
		type: "POST",
		url: "user/GetServers?nocache=" + Math.random() + "&servername=" + selectServer,
		async: false,
		success: function(data)
		{
		var da = JSON.parse(data);
		
		var obj = new Object();
		obj = da[0];
		
		currentConfig = obj.goodsConfig
		currentConfig.serverID = obj.serverID
		
	    var config = obj.goodsConfig;
		
		$("#titleGoods").append(spawnBr())
		for(var j=1;j<=config.titleGoodsCount;j++)  //标题
		{
		var string = "";
        var goods = config.titleGoods[j]
        	
		if(goods.goodName == "notset")
		string += spawnOption("notset","未设置商品");
		else
		{
		string += spawnOption(goods.goodName,goods.goodName);      //需要修改 结构变化！！！
		}
		
        $("#titleGoods").append(spawnTitleGoods(j,string))
		}
		$("#titleGoods").append(spawnBr())
		
		
		$("#staticGoods").append(spawnBr())
		for(var j=1;j<=config.staticGoodsCount;j++)  //静态
		{
		var string = "";
		var goods = config.staticGoods[j];
		
		if(goods.goodName == "notset")
			string += spawnOption("notset","未设置商品");
			else
			{
			string += spawnOption(goods.goodName,goods.goodName);      //需要修改 结构变化！！！
			}
		
		
		$("#staticGoods").append(spawnStaticGoods(j,string))
		}
		
		
		
		

		$.ajax({
                type: "POST",
                url: "user/GetGoods?nocache=" + Math.random() + "&servername=" + selectServer,
                async: false,
                success: function (data) {  
                  var da=eval("("+data+")")

                   
                
				  var ar = [];
                  for(var j=0;j<da.length;j++)
				  {
				  ar.push(da[j].goodName);
				  }
				  
				  for(var j=1;j<=currentConfig.titleGoodsCount;j++)
				  {
				  var arr = JSON.parse(JSON.stringify(ar));
				  var config = $("#h" + j).val();			  
				  var index = arr.indexOf(config);
				  
				  if (index > -1)
                  arr.splice(index, 1);
				  if(config!="undefine")
                  arr.unshift(config);
				  
				  string = "";
				  for(var k=0;k<arr.length;k++)
				  if(arr[k]=="notset")
				  string+= spawnOption(arr[k],"未设置商品");
				  else 
				  string+= spawnOption(arr[k],arr[k]);
				  $("#h" + j).html(string)
				  
				  }
				  
				  for(var j=1;j<=currentConfig.staticGoodsCount;j++)
				  {
					  
				  var arr= JSON.parse(JSON.stringify(ar));
				  var config = $("#g" + j).val();
				  var index = arr.indexOf(config);
				  if (index > -1)
                  arr.splice(index, 1);
				  if(config!="undefine")
                  arr.unshift(config);
				  
				  string = "";
				  for(var k=0;k<arr.length;k++)
				  if(arr[k] == "notset")
				  string+= spawnOption(arr[k],"未设置商品");
				  else
				  string+= spawnOption(arr[k],arr[k]);
				  $("#g" + j).html(string)
				  
				  }
                   
                }
                }
                )
		
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


                
                function UpdateConfig()
				{
				var selectServer = $("#serverInfo").val();
				if(selectServer=="empty" || selectServer=="undefine")
				return;
				
				
				
				$.ajax({
				type: "POST",
				contentType: "application/json; charset=utf-8",
                data: JSON.stringify(GetJsonData()),
				url: "user/UpdateServerConfig?nocache=" + Math.random(),
				success: function(da)
				{

				if(da.includes("成功"))
                {
                 $("#CallBackInfo").empty()
                 $("#CallBackInfo").html('<div class="alert alert-success fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><i class="fa-ok alert-icon s24"></i><strong>成功更新设置!</strong></div>')
                 location.reload()
                 setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },500)
                }
                else
                {
                $("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>更新失败,发生了错误!</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
                }
                
				},
				error: function(da)
				{
				$("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>更新失败,发生了错误!</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
				},
				})
				}
				
				function GetJsonData()
				{
				
				var obj = currentConfig;
				
				 
				for(var i=1;i<=currentConfig.titleGoodsCount;i++)
				{
				currentConfig.titleGoods[i].goodName = $("#h" + i).val()
				}
				
				for(var i=1;i<=currentConfig.staticGoodsCount;i++)
				{
				currentConfig.staticGoods[i].goodName = $("#g" + i).val()
				}
				
				
				return obj;
				
				}

				
                    
				function spawnTitleGoods(id , selectContent)
				{
					/*<div class="form-group">
                    <label class="control-label">动态标题商品1</label>
                    <select id="h1" name="h1" class="form-control"></select>
                    </div>*/
					var titleGoodsString = '<div class="form-group"><label class="control-label">{0}</label>{1}</div>';
					titleGoodsString = format(titleGoodsString,'动态标题商品' + id ,spawnSelect('h' + id,'h' + id,selectContent))
					return titleGoodsString;
				}
				
				function spawnStaticGoods(id , selectContent)
				{
					/*<div class="form-group"><label class="control-label">{0}</label>{1}</div> */
					var staticGoodsString = '<div class="form-group"><label class="control-label">{0}</label>{1}</div>';
					staticGoodsString = format(staticGoodsString,'静态商品' + id ,spawnSelect('g' + id,'g' + id,selectContent))
					return staticGoodsString;
				}
            
function spawnSelect(id,name,content)
{
var select = '<select id="{0}" name="{1}" class="form-control">{2}</select>';
select = format(select,id,name,content)
return select;
}

function format( message ) {
	if (!message) return null;
	var ss = message.split(/\{\d+?\}/);
	for ( var i = 0; i < ss.length; i++ ) {
		if (!arguments[i + 1]) break;
		ss[i] += arguments[i + 1];
	}
	return ss.join("");
}


function updateStaticGoodsCount()
{
	if($("#serverInfo").val()==undefined || $("#serverInfo").val()=="empty")
	{
		alert("请先选择服务器")
		return
	}
	
	$("#modalservername").val($("#serverInfo").val())
	$("#staticgoodscount").val(currentConfig.staticGoodsCount)
	
	$("#updateStaticGoodsCountModal").modal()
}

function submitUpdateStaticGoodsCount()
{
	$.ajax({
		type: "POST",
		url: "user/UpdateStaticGoodsCount?" + $("#updateStaticGoodsCountForm").serialize() + "&servername=" + $("#modalservername").val(),
		success: function(data)
		{
			alert(data)
			location.reload()
		},
		error: function()
		{
			alert("后台发生异常")
		}
	})
} 

function spawnBr()
{
	var string = '<div class="form-group"><br></div>';
	return string;
}


