<!DOCTYPE html>
<html>
	<head>
		<%@ page isELIgnored="false"%>
		<%@ page contentType="text/html;charset=UTF-8" language="java"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<meta charset="UTF-8">
		<title>欢迎页面-X-admin2.2</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
		<link rel="stylesheet" href="css/font.css">
		<link rel="stylesheet" href="css/xadmin.css">
		<link rel="stylesheet" href="lib/layui/css/layui.css">

		<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
		<script src="./lib/layui/layui.js" charset="utf-8"></script>
		<script type="text/javascript" src="./js/xadmin.js"></script>

	</head>

	<body>
		<div class="layui-fluid">
			<div class="layui-row">
				<form class="layui-form">
					<div class="layui-form-item">
						<label for="userId" class="layui-form-label">
							<span class="x-red">*</span>用户名
						</label>
						<div class="layui-input-inline">
							<input type="text" id="userId" name="userId" required="" lay-verify="required" autocomplete="off" class="layui-input">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>学号或者工号
						</div>
					</div>
					<div class="layui-form-item">
						<label for="password" class="layui-form-label">
							<span class="x-red">*</span>密码
						</label>
						<div class="layui-input-inline">
							<input type="text" id="password" name="password" required="" autocomplete="off" class="layui-input">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>
					<div class="layui-form-item"> 
						<label for="repass" class="layui-form-label">
							<span class="x-red">*</span>确认密码
						</label>
						<div class="layui-input-inline">
							<input type="text" id="repass" name="repass" required="" lay-verify="repass" autocomplete="off" class="layui-input">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>
					<div class="layui-form-item">
						<label for="email" class="layui-form-label">
							<span class="x-red">*</span>邮箱
						</label>
						<div class="layui-input-inline">
							<input type="text" id="email" name="email" required="" lay-verify="email" autocomplete="off" class="layui-input">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>用户邮箱地址
						</div>
					</div>
					<div class="layui-form-item">
						<label for="realname" class="layui-form-label">
							<span class="x-red">*</span>真实姓名
						</label>
						<div class="layui-input-inline">
							<input type="text" id="realname" name="realname" required="" lay-verify="required" autocomplete="off" class="layui-input">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>
					
					<div class="layui-form-item" >
						<label for="classId" class="layui-form-label">
							<span class="x-red">*</span>班级</label>
						<div class="layui-input-inline">
							<select id="classId" name="classId" class="valid" lay-filter="classId">
							<option value="null" selected="selected"> 请选择班级  </option>
								<c:forEach var="obj" items="${classes}" varStatus="s">

									<option value="${obj.class_id}">${obj.name}</option>

								</c:forEach>

							</select>
						</div>
					</div>

					<div class="layui-form-item">
						<label for="L_repass" class="layui-form-label">
						</label>
						<button class="layui-btn" lay-filter="add" lay-submit="">
							增加
						</button>
					</div>
				</form> 
			</div>
		</div>


		<script>
			layui.use(['form', 'layer'],
				function() {
					$ = layui.jquery;
					var form = layui.form,
						layer = layui.layer;


					//自定义验证规则
					form.verify({
						nikename: function(value) {
							if (value.length != 10) {
								return '用户名至少得10个字符';
							}
						}, 
						repass: function(value) {
							if ($('#password').val() != $('#repass').val()) {
								return '两次密码不一致';
							}
						}
					});

					//监听提交
					form.on('submit(add)',
						function(data) {

							console.log(data.field);

							$.ajax({
								type: "POST", 
								url: "User/AddStudent",
								dataType: 'json',
								data: data.field,
								async: false,
								success: function(da) {
									console.log(JSON.stringify(da))
									if (da.code == 0) {
										layer.alert('添加成功', {
												icon: 6
											},
											function() {
												//关闭当前frame
												xadmin.close();

												// 可以对父窗口进行刷新 
												xadmin.father_reload();
											});


									} else {
										layer.alert(data.msg, {
											icon: 5
										});

									}
								}
							})

							return false;
						});
		
					form.on('select(level)', function(data) {
		  
		                console.log(data)
						if(data.value=="1"){
						$("select[name='classId']").attr("disabled","true");
						layui.form.render('select');
						}
						else{
							$("select[name='classId']").removeAttr("disabled");
							layui.form.render('select');
						}
						
		
					})
		
		
				}); 
 
             
		/* 	$("#level").change(function() {
				console.log("kkk")
			});  */
			 
			 
			/*  tt();
			 
			function tt()
			{
			var obj = document.getElementById("level").parentNode.children[1].children[1];
			console.log(obj)
			
			for(var i=0;i<obj.children.length;i++)
			{
			if($(obj.children[i]).attr("class")=="layui-this")
			{
			console.log($(obj.children[i]).attr("lay-value"))
			}
			}
			
			$(obj).change(function()
			{
			console.log("ss")
			})
			
			
			}; 
			   */
			/*  $("#email").change(function(){
				 tt();
			 }) */
			//console.log(document.getElementById("level"))
		</script>

	</body>
</html>  
