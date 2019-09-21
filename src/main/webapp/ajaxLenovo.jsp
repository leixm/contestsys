<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联想搜索功能</title>
</head>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function(){
		
		$("input[name=uname]").css({
			"position":"relative"
		});
		
		$("#lns").css({
			"border":"1px #ccc solid",
			"width":"171px",
			"position":"absolute",
			"top":"84px",
			"left":"72px",
			"display":"none"
		});
		
		
		// 键盘松开的时候触发联想功能
		$("input[name=uname]").keyup(function(){
			var uname = $(this).val();
			if(uname != ""){
				$.ajax({
					url:"LenovoServlet",
					type:"post",
					data:"",
					dataType:"html",
					async:true,
					success:function(result){
						$("#lns").show();
						$("#lns").html(result);
						
						// 点击模糊列表的值，必须在这里写，其他位置不起作用
						$("li").unbind("click").bind("click", function(){
							$("input[name=uname]").val($(this).html());
							$("input[name=uname]").focus();
							$("#lns").hide();
						});
						
						// 点击其他地方的时候隐藏
						//$("input[name=uname]").blur(function(){
						//	$("#lns").hide();
						//});
					}
				});
			}else{
				$("#lns").html("");
				$("#lns").hide();
			}
		});
		
	});	
</script>
<body>
	<h3>输入框联想搜索功能</h3>
	请输入：<input type="text" name="uname"><input type="button" value="搜索">
	<div id="lns"></div>
</body>
</html>