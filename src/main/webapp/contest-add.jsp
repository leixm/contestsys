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
		<script type="text/javascript" src="./assets/js/jquery-1.8.3.min.js" ></script>
		<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
		<script src="./lib/layui/layui.js" charset="utf-8"></script>
		<script type="text/javascript" src="./js/xadmin.js"></script>

	</head>

	<body>
		<div class="layui-fluid">
			<div class="layui-row">
				<form class="layui-form">
					<!-- 无用div控制布局作用 -->
                	<div style="height: 30px">
                	</div>
                	
					<div class="layui-form-item">
						<label for="title" class="layui-form-label">
							<span class="x-red">*</span>考试名称
						</label>
						<div class="layui-input-inline">
							<input type="text" id="title" name="title" required="" lay-verify="required" autocomplete="off" class="layui-input">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>

					<div class="layui-form-item">
						<label for="startTimeS" class="layui-form-label">
							<span class="x-red">*</span>开始时间
						</label>
						<div class="layui-input-inline">
							<input id="startTimeS" name="startTimeS" type="text" class="layui-input"  autocomplete="off">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>

					<div class="layui-form-item">
						<label for="endTimeS" class="layui-form-label" >
							<span class="x-red">*</span>结束时间
						</label>
						<div class="layui-input-inline">
							<input id="endTimeS" name="endTimeS" type="text" class="layui-input" autocomplete="off">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>



					<div class="layui-form-item">
						<label for="paperId" class="layui-form-label">
							<span class="x-red">*</span>试卷</label>
						<div class="layui-input-inline">
							<select id="paperId" name="paperId" class="valid" lay-filter="paperId" lay-verify="required">
								<option value="null"> 请选择试卷 </option>

								<c:forEach var="obj" items="${paper}" varStatus="s">
									<option value="${obj.paper_id}">${obj.title}</option>

								</c:forEach>

							</select>
						</div>
					</div>


					<div class="layui-form-item">
						<label for="teacher" class="layui-form-label">
							<span class="x-red">*</span>负责人</label>
						<div class="layui-input-inline">
							<select id="teacher" name="teacher" class="valid" lay-filter="teacher" lay-verify="required">
								<option value="null"> 请选择教师 </option>

								<c:forEach var="obj" items="${teachers}" varStatus="s">
									<option value="${obj.userId}">${obj.realname}</option>

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
		 
		var startTime,endTime,startTimeS,endTimeS;
		
		
			layui.use(['form', 'layer','laydate'],
				function() {
					$ = layui.jquery;
					var form = layui.form,
						layer = layui.layer;
				    var laydate = layui.laydate; 

					//监听提交
					form.on('submit(add)',
						function(data) {

							console.log(data.field);
                            if(data.field.startTimeS>=data.field.endTimeS){
								layer.msg("考试结束时间必须大于考试开始时间！",{time:2000})
								return false;
							} 
							
							
/*                             var obj = data.field;
                            obj.startTimeS = startTime;
                            obj.endtime = endTime;   */
                           // console.log(obj)
							$.ajax({
								type: "POST",
								url: "Contest/AddContest",
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
                    
					laydate.render({
						elem: '#startTimeS',
						type: 'datetime',
						format: 'yyyy-MM-dd HH:mm:ss',
						min: '2019-5-20',
						done: function(value, date, endDate) {
							startTime = date;
							startTimeS = value;
						}
					});
					laydate.render({
						elem: '#endTimeS',
						type: 'datetime',
						format: 'yyyy-MM-dd HH:mm:ss',
						min: '2019-5-20',
						done: function(value, date, endDate) {
						    endTime = date;
						    startTimeS = value;
						}
					});

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
		<script type="text/javascript">
			$(document).ready(function() {
					loadingUserName();
			});
		
			
			/* 显示登录的用户名 */
		      function loadingUserName() {
		      		$.ajax({
								type: "POST", 
								url: "User/getUsername",
								data: '',
								async: false,
								dataType: 'json', 
								success: function(data) {
									if(data.level<1) {	//判断有误权限进来此页面
										alert("无访问权限");
										window.location.href = "./error.html";
									}
								}
							})
		      }
		
		</script>
	</body>
</html>
