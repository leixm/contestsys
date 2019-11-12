$(document).ready(function() {
			
				var defaults = {
		  			containerID: 'toTop', // fading element id
					containerHoverID: 'toTopHover', // fading element hover id
					scrollSpeed: 1200,
					easingType: 'linear' 
		 		};
				
				
				$().UItoTop({ easingType: 'easeOutQuart' });
				
			});
			
			
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
var uid= "uid=" + getRequest().uid;
//$("#jinbihref").href = "shop.html?" + uid;
var url = window.location.href;
var i = 0;
for(i = url.length-1; i >=0 ; i--)
if(url[i]=='/')
break;
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
$.ajax({
type: "POST",
url: "/rustshop/user/GetServerConfig?nocache=" + Math.random() + "&servername=" + serverName,
success: function(data)
{
var da = JSON.parse(data)

$("#pageName").html(da.serverName + "商城");
$("#QQGroup").html(da.qqGroup);

var cnt =1;

var config = da.goodsConfig


for(var i=1;i<=config.titleGoodsCount;i++)
{
	var goods = config.titleGoods[i];
   if(goods.goodName!="notset" && goods.goodName!="")
   {
      $("#h" + cnt + "goodName").html(goods.goodName)
      $("#h" + cnt + "price").html("￥" + goods.price)
      $("#h" + cnt + "goodDes").html(goods.goodDes)
      $("#h" + cnt + "href").attr("href",goods.goodID + ".html?" + uid);
      $("#h" + cnt + "img").attr("src",goods.img);
	  cnt++;
   }
}



cnt = 1;

$("#staticGoods").html("")
var count = parseInt(config.staticGoodsCount/3);
for(var i=0;i<count;i++){
	$("#staticGoods").append(spawnBox(spawnStaticGoods(3*i+1)+spawnStaticGoods(3*i+2)+spawnStaticGoods(3*i+3)))
}

for(var i=1;i<=config.staticGoodsCount;i++)
{
	var goods = config.staticGoods[i];
	
if(goods.goodName!="notset" && goods.goodName!="")
   {
      $("#g" + cnt + "goodName").html(goods.goodName)
      $("#g" + cnt + "price").html("￥" + goods.price)
      $("#g" + cnt + "goodDes").html(goods.goodDes)
      $("#g" + cnt + "href").attr("href",goods.goodID + ".html?" + uid);
      $("#g" + cnt + "href1").attr("href",goods.goodID + ".html?" + uid);
      $("#g" + cnt + "href2").attr("href",goods.goodID + ".html?" + uid);
      $("#g" + cnt + "img").attr("src",goods.img);
	  cnt++;
   }
}
 
for(;cnt<=config.staticGoodsCount;cnt++)
$("#g" + cnt).hide();


},
error: function(da)
{
}
})	


function spawnStaticGoods(id)
{
var string = '<div class="col_1_of_3 span_1_of_3" id="{0}"><a href="" id="{1}"><div class="view view-fifth"><div class="top_box"><h3 class="m_1" id="{2}">未设置礼包</h3><p class="m_2" id="{3}">未设置简介</p><div class="grid_img"><div class="css3"><img id="{4}" src="" alt=""/></div><div class="mask"><div class="info">查看详情</div></div></div><div class="price" id="{5}">￥0</div></div></div><span class="rating"><input type="radio" class="rating-input" id="rating-input-1-5" name="rating-input-1"><label for="rating-input-1-5" class="rating-star1"></label><input type="radio" class="rating-input" id="rating-input-1-4" name="rating-input-1"><label for="rating-input-1-4" class="rating-star1"></label><input type="radio" class="rating-input" id="rating-input-1-3" name="rating-input-1"><label for="rating-input-1-3" class="rating-star1"></label><input type="radio" class="rating-input" id="rating-input-1-2" name="rating-input-1"><label for="rating-input-1-2" class="rating-star1"></label><input type="radio" class="rating-input" id="rating-input-1-1" name="rating-input-1"><label for="rating-input-1-1" class="rating-star1"></label>&nbsp;(100)</span></a><ul class="list"><li><img src="/rustshop/images/plus.png" alt=""/><ul class="icon1 sub-icon1 profile_img"><li><a class="active-icon c1" href="" id="{6}"> 查看详情 </a><ul class="sub-icon1 list"><li><h3>查看详情</h3><a href="" id="{7}"></a></li><li><p>您可以进入此页面查看商品的详情(获取每个礼包的具体内容)</p></li></ul></li></ul></li></ul><div class="clear"></div></div>'
	string = format(string,'g' + id,'g' + id + 'href','g' + id +'goodName','g' + id + 'goodDes','g' + id + 'img','g' + id + 'price','g' + id + 'href1','g' + id + 'href2')
	return string 
}

    function spawnBox(content)
    {
    	var string = '<div class="box1">{0}<div class="clear"></div></div>'
    		string = format(string,content)
    		return string;
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
	
