  
    function onerror()
    {
    alert("ajax发生错误");
    }
    
    function getInfo()
    {
    var t = document.getElementById("tip")
    var username = form1.username.value;
    var password = form1.password.value;
    
    if(username=="")
    {
    t.style.color='red';
    t.value="用户名不能为空";
    return;
    }
    
    if(password=="")
    {
    t.style.color='red';
    t.value="密码不能为空";
    return;
    }
    
    var url = "user/ajax?action=login&nocache=" + new Date().getTime()+"&username="+form1.username.value+"&password="+form1.password.value;
    var loader = new net.AjaxRequest(url,deal_getInfo,onerror,"POST");
    }
    
    function deal_getInfo() 
    {
    var t = document.getElementById("tip")
    if(this.req.responseText.includes("成功"))
    {
    
    var check = document.getElementById("remember");
    if(check.checked==true)
    {
    setCookie("lxmUserName",form1.username.value,864000);
    setCookie("lxmPassWord",form1.password.value,864000);
    }
    //form1.submit();
    window.location.href="profile.html"
   
    }
    
    else
    {
    t.style.color='red';
    t.value=this.req.responseText;
    }

    }
    
function setCookie(name, value, iDay) 
{
    var oDate=new Date();
     
    oDate.setDate(oDate.getDate()+iDay);
     
    document.cookie=name+'='+encodeURIComponent(value)+';expires='+oDate;
}

    function submitInfo()
    {
    var username = form2.username.value;
    var password = form2.password.value;
    var reg = /^[\w]{6,12}$/
    var t = document.getElementById("tip1")
    var t1 = document.getElementById("tip2")
    
    if(username=="")
    {
    t.style.color='red';
    t.value="用户名不能为空";
    form2.username.focus();
    setTimeout(function(){
                 t.value="";
                 },3000)
    return;
    }
    
    if(password=="")
    {
    t1.style.color='red';
    t1.value="密码不能为空";
    form2.password.focus();
    setTimeout(function(){
                 t1.value="";
                 },3000)
    return;
    }


if(!username.match(reg) )
{
    t.style.color='red';
    t.value="用户名必须由6~12位数字、字母组成";
    form2.username.focus();
    setTimeout(function(){
                 t.value="";
                 },3000)
    return;
}

if(!password.match(reg) )
{
    t1.style.color='red';
    t1.value="密码必须由6~12位数字、字母组成";
    form2.password.focus();
    setTimeout(function(){
                 t1.value="";
                 },3000)
    return;
}




    var url = "user/ajax?action=register&nocache=" + new Date().getTime()+"&username="+form2.username.value;
    var loader = new net.AjaxRequest(url,deal_submitInfo,onerror,"POST");
    }
    
    
    function deal_submitInfo()
    {
    var t = document.getElementById("tip1")
    if(this.req.responseText.includes("不存在"))
    form2.submit();
    else
    {
    t.style.color='red';
    t.value=this.req.responseText;
    }

    }
    
    
    function getRegisterInfo()
    {
var t = document.getElementById("tip1")
var username = form2.username.value;
var reg = /^[\w]{6,12}$/  

    if(username=="")
    {
    t.style.color='red';
    t.value="用户名不能为空";
    setTimeout(function(){
                 t.value="";
                 },3000)
    return;
    }
    
if(!username.match(reg) )
{
    t.style.color='red';
    t.value="用户名必须由6~12位数字、字母组成";
    setTimeout(function(){
                 t.value="";
                 },3000)
    return;
}

    var url = "user/ajax?action=register&nocache=" + new Date().getTime()+"&username="+form2.username.value;
    var loader = new net.AjaxRequest(url,deal_getRegisterInfo,onerror,"POST");
    }
    
    function deal_getRegisterInfo()
    {
    var t = document.getElementById("tip1");
    if(this.req.responseText.includes("不存在"))
    {
        t.style.color='green';
        t.value="该用户名可以使用";
    }
    else
    {
    t.style.color='red';
    t.value="该用户名已经被注册！";
    }

    }
    