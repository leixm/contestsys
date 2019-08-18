
		
		function GetServerInfo()
		{

		$.ajax({
		type: "POST",
		url: "user/GetServers",
		success: function(data)
		{

		var da = JSON.parse(data);
		var base = "Page/" + $("#username").val() + "/";
		//<button class="btn btn-danger" type="button" id="btn1" onclick="article_del(this)">删除</button>
		for(var i = 0 ; i < da.length ; i++)
		{
		var url = base + da[i].serverID + "/index.html";
		var fun = '"window.open(\'' + url + '\')"';

		$("#datatable1 tbody").append('<tr class="odd gradeA">' + '<td>' + da[i].serverName + '</td>' + '<td>' + da[i].qqGroup + '</td>' +'<td>' + da[i].packCount + '</td>' + '<td>' + '<button class="btn btn-primary" type="button"  onclick='+ fun +'>预览服务器</button>' +  '<button class="btn btn-danger" type="button" onclick="article_del(this)">删除</button> ' + '<button class="btn btn-yellow" type="button"  onclick="downloadFileByForm(this)">下载插件</button> ' + '<button class="btn btn-success" type="button" onclick="updateServerInfo(this)">修改信息</button> ' +'</td>' + '</tr>');
		}
		
		},
		error: function(da)
		{
		
		}
		})
		}
		
		InitUserInfo();
		GetServerInfo();
		
		
		function error(message)
		{
		$("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>' +  message + '</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
		}
		
		
		function AddServer()
		{
		
		if($("#serverName").val()=="" || $("#QQGroup").val()=="")
		{
		error("信息不能为空");
		return;
		}
		
		if($("#serverName").val().length>20)
		{
		error("服务器名称不能超过20个字符");
		$("#serverName").focus();
		return;
		}
		
		var reg = /^[0-9]*$/
				
		if($("#QQGroup").val().length>10 || !$("#QQGroup").val().match(reg))
		{
		error("请检查QQ群输入是否正确");
		$("#QQGroup").focus();
		return;
		}
		
		
		$.ajax({
		type: "POST",
		url: "user/AddServer?nocache=" + Math.random() + "&servername=" + $("#serverName").val() + "&qqgroup=" + $("#QQGroup").val(),
		success: function(da)
		{

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
		else{
		$("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>添加失败,'+ da +'</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
		}
		},
		error: function(da)
		{
		$("#CallBackInfo").html('<div class="alert alert-danger fade in"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><strong>添加失败,发生了错误!</strong> </div>')
                setTimeout(function(){
                 $("#CallBackInfo").empty()
                 },2000)
		}
		})
		}
		
		function article_del(obj){  
                var res = confirm('确认要删除吗？');  
                if(res == true)  
                {  
                    $(obj).parents("tr").remove()
                }
                
                //console.log($(obj).parents("tr").children("td:first").html())	
                	
                $.ajax({
                type: "POST",
                url: "user/DeleteServer?nocache=" + Math.random() +"&servername=" +$(obj).parents("tr").children("td:first").html(),
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
		
		function updateServerInfo(obj)
		{
			var child = $(obj).parents("tr")[0].children;
			var oldname = child[0].innerHTML;
			$("#oldname").val(oldname)
			$("#newname").val(child[0].innerHTML)
			$("#qqgroup").val(child[1].innerHTML)
			$("#updateServerModal").modal();
			
		}
            
        function SubmitServerInfo()
        {
        	
        	if($("#newname").val()=="" || $("#qqgroup").val()=="")
    		{
        		alert("信息不能为空");
    		return;
    		}
    		
    		if($("#newname").val().length>20)
    		{
    		alert("服务器名称不能超过20个字符");
    		$("#newname").focus();
    		return;
    		}
    		
    		var reg = /^[0-9]*$/
    				
    		if($("#qqgroup").val().length>10 || !$("#qqgroup").val().match(reg))
    		{
    		alert("请检查QQ群输入是否正确");
    		$("#qqgroup").focus();
    		return;
    		}
    		
    		
        	$.ajax({
        		url:"user/UpdateServerInfo?" + $("#updateServerForm").serialize() + "&oldname=" + $("#oldname").val(),
        		type:"POST",
        		success: function(data)
        		{
        		location.reload()
        		},
        		error: function(data)
        		{
        			alert("后台异常")
        		}
        	})
        }
            
        function InitUserInfo()
        {
        $.ajax({
        type: "POST",
        url: "user/GetProfile?nocache=" + Math.random(),
        success: function(data)
        {
        if(data.includes("登录"))
        window.location.href = "login.jsp";
        
        var da = JSON.parse(data)
        for(var key in da)
        $("#" + key).val(da[key])
        },
        error: function(da)
        {
        
        },
        })
        }
        
    
		
		
		
		function downloadFileByForm(obj) {
        var url = "user/DownloadPlugin?nocache=" + Math.random()+"&servername=" +$(obj).parents("tr").children("td:first").html();
        var fileName = "K2Shop.cs";
        var form = $("<form></form>").attr("action", url).attr("method", "post");
        form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
        form.appendTo('body').submit().remove();
    }