<!DOCTYPE html>
<html>
	<head>
		<%@ page isELIgnored="false"%>
		<%@ page contentType="text/html;charset=UTF-8" language="java"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<title>添加任课教师</title>
		<link rel="stylesheet" href="css/font.css">
		<link rel="stylesheet" href="css/xadmin.css">
		<link rel="stylesheet" href="lib/layui/css/layui.css">
		<script type="text/javascript" src="./assets/js/jquery-1.8.3.min.js" ></script>		
		<script type="text/javascript" src="./js/xadmin.js"></script>
		<script src="./lib/layui/layui.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		    //全局定义一次, 加载formSelects
		    layui.config({
		        base: './js/' //此处路径请自行处理, 可以使用绝对路径
		    }).extend({
		        formSelects: 'formSelects-v4'
		    });
		    //加载模块
		    layui.use(['jquery', 'formSelects'], function(){
		        var formSelects = layui.formSelects;
		         
		    });
		</script>
		 
		
		<!-- <script type="text/javascript">
		    var formSelects = layui.formSelects;
		     
		</script> -->
		
	</head>

	<body>
	<link rel="stylesheet" href="./css/formSelects-v4.css" />
		<div class="" style="margin:80px auto; width:600px;">
			<div class="layui-row">
				<form class="layui-form">
					<!-- 无用div控制布局作用 -->
	               	<div style="height: 30px">
	               	</div>
					<div>
			            <select id="classId" name="courseId" class="valid" lay-filter="classId" xm-select="select1" xm-select-search="" xm-select-search-type="dl" >
			            	<option value="">请选择课程(可多选)</option>
							<c:forEach var="obj" items="${courses}" varStatus="s">
			
								<option value="${obj.courseId}">${obj.courseName}</option>
			
							</c:forEach>
			            </select>
			        </div>	
			        
			        
			        <!-- 无用div控制布局作用 -->
	               	<div style="height: 30px">
	               	</div>
			        
			        
			        <div>
			            <select id="teacherId" name="teacherId" class="valid" lay-filter="teacherId" xm-select="select2" xm-select-search="" xm-select-search-type="dl">
			            	<option value="">请选择任课教师(可多选)</option>
							<c:forEach var="obj" items="${teachers}" varStatus="s">
			
								<option value="${obj.userId}">${obj.realname}</option>
			
							</c:forEach>
			            </select>
			        </div>	
			        
			        <!-- 无用div控制布局作用 -->
	               	<div style="height: 30px">
	               	</div>
			        
			        <div class="layui-form-item" style="margin-left: 400px;">
						<label for="L_repass" class="layui-form-label">
						</label>
						<button class="layui-btn" lay-filter="add" lay-submit="">
							增加
						</button>
					</div>
					
				</form>
        	</div>
		</div>
        
	 	<script src="./lib/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
		<script src="./js/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
		
		<script type="text/javascript">

			var startTime,endTime,startTimeS,endTimeS;
			 
			layui.use(['form', 'layer','laydate'],function() {
				
					$ = layui.jquery;
					var form = layui.form, 
					layer = layui.layer;
				    var laydate = layui.laydate; 
	
					//监听提交
					form.on('submit(add)',
						function(data) { 
							console.log(data)
							$.ajax({  
								type: "POST",
								url: "Course/addTeach?courseId=" + data.field.courseId + "&teacherId=" + data.field.teacherId,
								dataType: 'json',
								async: false,
								success: function(da) {
									if (da.code == 0) {
										layer.alert(da.msg, {
												icon: 6 
											},
											function() {
												//关闭当前frame
												xadmin.close();

												// 可以对父窗口进行刷新 
												xadmin.father_reload();
											});


									} else {
										layer.alert(da.msg, {
											icon: 5
										});

									}
								}
							})

							return false;
						});
				});
		
		</script>
	</body>
</html>
