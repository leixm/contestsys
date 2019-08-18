			
			
			function getRequest() {
  var url = window.location.search; //获取url中"?"符后的字串
  var theRequest = new Object();
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for(var i = 0; i < strs.length; i ++) {
       
      theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
       
    }
  }
  return theRequest;
}

function spawnOption(v,k)
{
//<option value="audi">Audi</option>
var string = '<option value="' + v + '">' + k + '(' + v + ')' + '</option>';
return string;
}

var userobj;

function SubOrder()
{
    if($("#playerInfo").val()==null || userobj[$("#playerInfo").val()]==null ||userobj[$("#playerInfo").val()]=="未指定玩家")
     {
     alert("发生了一个错误，您未选择您的ID或者您的ID无效");
     return false;
     }
     
     $("#steamid").val($("#playerInfo").val())
     $("#username").val(userobj[$("#playerInfo").val()])
     /*

     return false;*/
  
     
}
var uid= getRequest().uid;
$("#indexhref").attr("href","index.html?uid=" + uid);	
		
			   
			
var url = window.location.href;
var i = 0;
var goodName = "";

for(i = url.length-1; i >=0 ; i--)
if(url[i]=='.')
break;
i--;

for(;i>=0;i--)
{
if(url[i]=='/')
break;
goodName+=url[i];
}
goodName = goodName.split("").reverse().join("")
i--;
var serverName = "";
for(;i>=0;i--)
{
if(url[i]=='/')
break;
serverName+=url[i];
}
serverName = serverName.split("").reverse().join("")

i--;
var userName = "";
for(;i>=0;i--)
{
if(url[i]=='/')
break;
userName+=url[i];
}
userName = userName.split("").reverse().join("")


//询问
$.ajax({
                type: "POST",
                url: "/rustshop/user/getUserResult?uid=" + uid + "&servername=" + serverName,
                async: false,
                success: function (da) {  
                userobj = da;

				var string;
				string+= spawnOption("请选择您的游戏昵称","未指定玩家")
				$.each(da, function (k, v) {
			string += spawnOption(k,v)
        })
		$("#playerInfo").html(string)
		$("#steamid").val("123");
		$("#username").val("aa");
               },
                error: function(da){
                
                }
                }
                )
				
				
$.ajax({
type:"POST",
async: false,
url:"/rustshop/user/GetGoodConfig?nocache=" + Math.random() + "&servername="+serverName,
success: function(data)
{
     
      var da = JSON.parse(data)
	  
      for(var i=0;i<da.length;i++)
	  if(da[i].goodID==goodName)
	  {
	  $("#serverName").val(da[i].serverName)
	  $("#owner").val(da[i].owner)
	  
	  $("#pageName").html(da[i].serverName + "商城");
	  $("#img").attr("src",da[i].img);
	  $("#img1").attr("src",da[i].img1);
	  $("#img2").attr("src",da[i].img2);
	  $("#img3").attr("src",da[i].img3);
	  
	  $("#iimg").attr("src",da[i].img);
	  $("#iimg1").attr("src",da[i].img1);
	  $("#iimg2").attr("src",da[i].img2);
	  $("#iimg3").attr("src",da[i].img3);
	  
	  
	  $("#goodName").html(da[i].goodName);
	  $("#price").html("￥" + da[i].price);
	  
	  var itemlist = da[i].goodContentMap;
	  var nameMap = da[i].itemNameMap;
	  
	  

	  var string = "";
	  
	  if(itemlist["权限"]!=undefined)
	  {
	  var permlist = itemlist["权限"];
	  var permArr = permlist.split("|");
	  for(var j=0;j<permArr.length;j++)
	  {
	  string+= spawnICON("perm","",permArr[j].split(":")[0])
	  }
	  }
	  
	  if(itemlist["金币"]!=undefined)
	  string+= spawnICON("balance","","金币 × " + itemlist["金币"])
	  
	  if(itemlist["服务器奖励点"]!=undefined)
	  string+= spawnICON("rewardPoint","","服务器奖励点 × " + itemlist["服务器奖励点"])
      
	  for(var key in itemlist)
	  {
	  if(key=="权限" || key=="金币" || key=="服务器奖励点") continue;
	  string+= spawnICON("item",key,nameMap[key] + "×" + itemlist[key]);
	  }
	  

	  
	 // $("#goodContent").html(da[i].goodContent)
	  $("#icondiv").append(string);
	  
	  $("#fprice").val(da[i].price);
	  $("#fgoodName").val(da[i].goodName);
	  $("#QQGroup").html(da[i].qqGroup);
	  break;
	  }
	  
	  var cnt = 0;
	  if(da.length<=0)
	  return;
	  for(var i=1;i<=5;i++)
	  {
	  $("#pack" + i + "img").attr("src",da[cnt].img);
	  $("#pack" + i + "price").html("￥" + da[cnt].price);
	  $("#pack" + i + "goodName").html(da[cnt].goodName);
	  cnt = (cnt + 1) % da.length;
	  }
	  

				var defaults = {
		  			containerID: 'toTop', // fading element id
					containerHoverID: 'toTopHover', // fading element hover id
					scrollSpeed: 1200,
					easingType: 'linear' 
		 		};
				
				
				$().UItoTop({ easingType: 'easeOutQuart' });
				
		
			
},
error: function(da)
{

}
})

function spawnICON(type,imgName,labelName)
{
/*
<div class="icondiv">
<img src="images/autoturret.png" class="iconimg"/>
<div class="labeldiv">
<font class="labelfont">蓝图全解</font>
</div>
</div>
*/
var string = "";
string+= '<div class="icondiv">';

if(type=="balance")
string+= '<img src="/rustshop/images/icon/' + 'balance' + '.png" class="iconimg"/>';
else if(type=="perm")
string+= '<img src="/rustshop/images/icon/' + 'perm' + '.png" class="iconimg"/>';
else if(type=="rewardPoint")
string+= '<img src="/rustshop/images/icon/' + 'rewardPoint' + '.png" class="iconimg"/>';
else 
string+= '<img src="/rustshop/images/icon/' + imgName + '.png" class="iconimg"/>';

string+= '<div class="labeldiv">';
string+= '<font class="labelfont">' + labelName + '</font>';
string+= '</div></div>';
return string;
}


		