
     
     		$(load());
	
	            function load(){
		    	       var data = []
                      $.ajax({
                type: "POST",
                url: "user/GetGoods?nocache=" + Math.random(),
                async : false, //同步执行
                success: function (da) {  
                  if(da.includes("登录"))
        window.location.href = "login.jsp";
        
                  var dataObj=eval("("+da+")")
                  
                  for(var i = 0;i < dataObj.length ;i++)
                  {
				   $("#datatable1 tbody").append('<tr class="odd gradeA"><td>'+ dataObj[i].goodName + '</td><td>' + dataObj[i].price +'</td><td>' + dataObj[i].serverName +'</td>' +  '<td><div class="form-group mb15"><button class="btn btn-primary" type="button" id="showfdivbtn" onclick="showfdiv(this)">设置商品图片</button></div> <div class="form-group mb15"><button class="btn btn-danger" type="button" id="btn1" onclick="article_del(this)">删除</button></div></td>' +'</tr>')
                  }
                  
                }
                }
                )
               }
               
/*               InitPrice();
               function InitPrice()
               {
               var string = "";
               var arr= [1,2,5,10,20,30,40,50,60,70,80,90,100,120,150,180,200,250,300,400,500,600,700,800,900,1000];
               string += spawnOption("","请设置金额");
               for(var i =0;i<arr.length;i++)
               string+= spawnOption(arr[i] , arr[i] + "元");
               $("#price").html(string)
               }*/
               
               
               
               function GetImgInfo()
               {
               $.ajax({
               type: "POST",
               url: "user/GetImgInfo?nocache=" + Math.random() + "&servername=" + $("#fServerName").val() + "&goodname=" + $("#fGoodName").val() ,
               success: function(data)
               {
               
               var da = JSON.parse(data)
               var imgList = da.imgList;
               var imgConfig = da.imgConfig;
               
               var string = "";
               string += spawnOption(imgConfig["img"],imgConfig["img"]);
               
               for(var i=0;i<imgList.length;i++)
               {
               if(imgConfig["img"]!=imgList[i])
               string+= spawnOption(imgList[i],imgList[i]);
               }
               $("#fimg").html(string)
               
               string = "";
               string += spawnOption(imgConfig["img1"],imgConfig["img1"]);
               
               for(var i=0;i<imgList.length;i++)
               {
               if(imgConfig["img1"]!=imgList[i])
               string+= spawnOption(imgList[i],imgList[i]);
               }
               $("#fimg1").html(string)
               
               
              string = "";
               string += spawnOption(imgConfig["img2"],imgConfig["img2"]);
               
               for(var i=0;i<imgList.length;i++)
               {
               if(imgConfig["img2"]!=imgList[i])
               string+= spawnOption(imgList[i],imgList[i]);
               }
               $("#fimg2").html(string)
               
               string = "";
               string += spawnOption(imgConfig["img3"],imgConfig["img3"]);
               
               for(var i=0;i<imgList.length;i++)
               {
               if(imgConfig["img3"]!=imgList[i])
               string+= spawnOption(imgList[i],imgList[i]);
               }
               $("#fimg3").html(string)
               
               
               },
               error: function(da)
               {
               
               }
               })
               }
               
               function UpdateGoodImg()
               {
               $.ajax({
               type: "POST",
               url: "user/UpdateGoodImg?nocache=" + Math.random() + "&servername=" + $("#fServerName").val() + "&goodname=" + $("#fGoodName").val() + "&img=" + $("#fimg").val() + "&img1=" + $("#fimg1").val()+ "&img2=" + $("#fimg2").val()+ "&img3=" + $("#fimg3").val(),
               success: function(da)
               {
               if(da.includes("成功"))
                {
                 $("#fCallBackInfo").empty()
                 $("#fCallBackInfo").html('<div class="alert alert-success fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><i class="fa-ok alert-icon s24"></i><strong>添加成功!</strong></div>')
                 setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
                }
                else if(da.includes("登录"))
                {
                 window.location.href = "login.jsp";
                }
                else
                {
                $("#fCallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>添加失败,'+ da +'</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
                }
               
               },
               error: function(da)
               {
               $("#fCallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>添加失败,发生了错误!</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
               }
               })
               }
               
               function error(message)
		{
		$("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>' +  message + '</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
		}
    
                function btn1Onclick(){
                
                if($("#goodname").val() =="" || $("#gooddes").val() =="" || $("#goodcontent").val() ==""|| $("#price").val() ==""|| $("#serverInfo").val() =="notset")
                {
                error("请每项信息，信息不能为空！");
                return;
                }
                
                if($("#goodname").val().length>20)
                {
                error("商品名称过长(不超过20个字符)");
                $("#goodname").focus();
                return;
                }
                
                if($("#gooddes").val().length>30)
                {
                error("商品简介过长(不超过30个字符)");
                $("#gooddes").focus();
                return;
                }
                
                if(isNaN($("#price").val()) || $("#price").val()<=0 || $("#price").val()>=5000)
                	{
                	error("价格不合法：大于0 小于等于5000");
                    $("#price").focus();
                    return;
                	}
                
                if($("#goodcontent").val().length>500)
                {
                error("商品名称过长(不超过500个字符)");
                $("#goodcontent").focus();
                return;
                }
                
                
                
                $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(GetJsonData()),
                url: "user/AddGoods?nocache=" + Math.random(),
                dataType: "text",
                success: function (da) {  
                if(da.includes("成功"))
                {
                 $("#CallBackInfo").empty()
                 $("#CallBackInfo").html('<div class="alert alert-success fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><i class="fa-ok alert-icon s24"></i><strong>添加成功!</strong></div>')
                 setTimeout(function(){
                 $("#CallBackInfo").empty()
                 location.reload()
                 },500)
                }
                else if(da.includes("登录"))
                {
                 window.location.href = "login.jsp";
                }
                else
                {
                $("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>添加失败,'+ da +'</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
                }
               },
                error: function(da){
           
                $("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>添加失败,发生了错误!</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
                }
                }
                )
                
                }
                
            function article_del(obj){  
                var res = confirm('确认要删除吗？');  
                if(res == true)  
                {  
                    $(obj).parents("tr").remove()
                }
                
           
                var child = $(obj).parents("tr")[0].children;
    
                $.ajax({
                type: "POST",
                url: "user/DeleteGoods?nocache=" + Math.random() +"&goodname=" + child[0].innerHTML + "&servername=" + child[2].innerHTML,
                success: function (da) {  
                if(da.includes("成功"))
                location.reload()
                else if(da.includes("登录"))
                {
                 window.location.href = "login.jsp";
                }
                }
                }
                )
            }
            
            
            
            function GetServerInfo()
		{

		$.ajax({
		type: "POST",
		url: "user/GetServers",
		success: function(data)
		{
		var da = JSON.parse(data);
		var string = "";
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
		
		GetServerInfo();
            
function spawnOption(v,k)
{
//<option value="audi">Audi</option>
var string = '<option value="' + v + '">' + k  + '</option>';
return string;
}  
         
function closefdiv()
{
//$("#fffdiv").style.display = "none"; 
document.getElementById("fdiv").style.display = "none";
}   

function showfdiv(obj)
{
var child = $(obj).parents("tr")[0].children;
var goodName = child[0].innerHTML;
var serverName = child[2].innerHTML;

$("#imgConfigModal").modal({height:650})

$("#fGoodName").val(goodName)
$("#fServerName").val(serverName)
GetImgInfo()

}  

function fileupload3(){
    var formData = new FormData($("#form5")[0]);

    showWaitModal()
        $.ajax({
                url:'load/upload',
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
                $("#fCallBackInfo").empty()
                 $("#fCallBackInfo").html('<div class="alert alert-success fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><i class="fa-ok alert-icon s24"></i><strong>上传成功!</strong></div>')
                 setTimeout(function(){
                 $("#fCallBackInfo").empty()
                 },2000)
                }
                else if(da.includes("登录"))
                {
                 window.location.href = "login.jsp";
                }
				else
				{				
					$("#fCallBackInfo").empty()
                 $("#fCallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>' + da +'!</strong> </div>')
                 setTimeout(function(){
                 $("#fCallBackInfo").empty()
                 },2000)
				}
                    GetImgInfo();
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

//document.getElementById("fdiv").style.display= "none";

           /* document.onclick = function(e){
                e = e || window.event;
                var dom =  e.srcElement|| e.target;

                if(dom.id != "fdiv" && dom.id != "showfdivbtn" && document.getElementById("fdiv").style.display == "block") {
                    document.getElementById("fdiv").style.display = "none";
                }
            };*/

var itemlist = new Object();
function InitItemList()
{
$.ajax({
type: "POST",
url: "user/GetItemResult?nocache=" + Math.random(),
success: function(data)
{

itemlist = data;
var string = "";
string += spawnOption("notset","请选择类别");
for(var key in data)
{
string += spawnOption(key,key)
}
$("#categorylist").html(string)
},
})
}
InitItemList();


function categorylistchange(obj)
{
var da = itemlist[obj.value];
var arr = [];

for(var key in da)
{
arr.push(key)
}
arr.sort();

var string = "";

if(obj.value.includes("权限"))  // 选择分类的时候直接初始化数量文本框 因为特殊分类不需要选择小类别
{
$("#switchdiv").html('<input type="text" class="form-control" id="itemlist" name="itemlist" placeholder="请输入权限备注名，如：蓝图全解">');
$("#itemcount").val("")
$("#itemcount").attr('placeholder',"请输入权限名称，如:kits.vip")
return;
}
else if(obj.value.includes("奖励点"))
{
$("#itemcount").val("")
$("#itemcount").attr('placeholder',"请输入奖励点数量")
}
else if(obj.value.includes("金币"))
{
$("#itemcount").val("")
$("#itemcount").attr('placeholder',"请输入金币数量")
}
else
{
$("#itemcount").attr('placeholder',"")
$("#itemcount").val("")
}

$("#switchdiv").html('<select id="itemlist" name="itemlist" class="form-control" onchange="itemlistchange(this)"></select>');

if(!(obj.value=="权限" || obj.value=="金币" || obj.value=="服务器奖励点"))
string+= spawnOption("notset","请选择物品");
for(var i=0;i<arr.length;i++)
{
string += spawnOption(arr[i],arr[i]);
}
$("#itemlist").html(string)
}

function itemlistchange(obj)
{

if(obj.value=="notset")
{
$("#itemcount").attr('placeholder',"")
return;
}

if(obj.value.includes("奖励点"))
{
$("#itemcount").val("")
$("#itemcount").attr('placeholder',"请输入奖励点数量")
}
else if(obj.value.includes("金币"))
{
$("#itemcount").val("")
$("#itemcount").attr('placeholder',"请输入金币数量")
}
else
{
$("#itemcount").val("1")
}
}

var additemlist = new Object();
function AddItem()
{
if($("#categorylist").val()=="notset")
{
alert("请选择类别");
$("#categorylist").focus();
return;
}

if($("#itemlist").val()=="notset" || $("#itemlist").val()==undefined)
{
$("#itemlist").focus();
return;
}

if($("#itemcount").val()=="" || $("itemcount").val==undefined)
{
$("#itemcount").focus();
return;
}

var string = "";
if($("#categorylist").val()=="权限")
{
string += $("#itemlist").val() + ":" + $("#itemcount").val() + "\n";
if(additemlist["权限"]!=undefined)
additemlist["权限"] = additemlist["权限"] + "|" + $("#itemlist").val() + ":" + $("#itemcount").val();

else
additemlist["权限"] =$("#itemlist").val() + ":" + $("#itemcount").val();
}

else
{
if($("#itemlist").val().includes("金币") || $("#itemlist").val().includes("服务器奖励点") )
string += $("#itemlist").val() + ":" + $("#itemcount").val() + "\n";
else
string += $("#itemlist").val() + " × " + $("#itemcount").val() + "\n";

var key = itemlist[$("#categorylist").val()][$("#itemlist").val()];
if(additemlist[key]!=undefined)  //是否已经存在存在则叠加 
additemlist[key] = (parseInt(additemlist[key]) + parseInt($("#itemcount").val())).toString()
else
additemlist[key] = $("#itemcount").val(); //添加到容器中暂存

}


$("#goodcontent").html($("#goodcontent").html() + string)
} //结束

function ClearGoodContent()
{
$("#goodcontent").html("");
additemlist = new Object();
}

function hideWaitModal(){
		$('#waitModal').modal('hide');
	}
	
	function showWaitModal(){
		$('#waitModal').modal({backdrop:'static',keyboard:false});
	}
	
function GetJsonData()
{
var json = {
        "goodName" : $("#goodname").val(),
        "goodDes" : $("#gooddes").val(),
        "price": parseFloat($("#price").val()),
        "serverName": $("#serverInfo").val(),
        "goodContentMap": additemlist,
    };

return json
}
