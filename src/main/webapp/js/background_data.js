function register(){
    $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",  //设置头
                data: JSON.stringify(GetJsonDataRe()),  //序列化
                url: "User/Register?rancode=" + $("#inputNum").val(),
                dataType: "text",
                success: function (da) {  
					console.log(da)
                var dt = JSON.parse(da);
                if(dt.success > 0){  
                    alert("注册成功！请登录您的邮箱激活账号！")    
                    window.location.href = "login.html"
                }else{
                    var data = JSON.parse(da);
                    document.getElementById('alert').style.display = 'block';
                    document.getElementById('background_fade').style.display = 'block';
                    document.getElementById('confirm_state').innerHTML = dt.msg;
					$("#imgCode").attr("src","User/GetRanCode?ran=" + Math.random());
                }

                }, 
                error: function(da){
                    document.getElementById('alert').style.display = 'block';
                    document.getElementById('background_fade').style.display = 'block';
                } 
                }
                ) 
}  


function GetJsonDataRe()
{
var json = {
        "userId" : $("#user").val(),
        "password" : $("#pwd2").val(),
        "email": $("#email").val(),
    };
return json
}
  