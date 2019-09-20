<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册页面</title>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="./css/font.css">
<link rel="stylesheet" href="./css/xadmin.css">
<link rel="stylesheet" href="lib/layui/css/layui.css">
<link rel="stylesheet" href="css/theme49.min.css">
<script src="./lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="./js/xadmin.js"></script>
<style type="text/css">
#ce {
	margin: 0px 0px 0px 400px;
}
</style>

</head>
<body>
	<div class="x-nav">
		<a class="layui-btn layui-btn-small" style="line-height: 1.0em; margin-top: 0px; float: right" onclick="location.reload()"
		 title="刷新"> <i class="layui-icon layui-icon-refresh" style="line-height: 40px"></i></a>
	</div>
	<br>
	<br>
	<br>
	<br>
	<div id="ce">
		<form class="layui-form layui-form-pane" >
			<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
			<div class="layui-form-item">
				<label class="layui-form-label">学号</label>
				<div class="layui-input-block" style="width: 150px;">
					<input type="text" name="stuid" placeholder="请登录系统" autocomplete="off"
						class="layui-input" " value="${user.userId}" disabled>
				</div>
			</div>
			<!-- <div class="layui-form-item">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<input type="radio" name="sex" value="0" title="男"> <input
						type="radio" name="sex" value="1" title="女" checked>
				</div>
			</div> -->
			<div class="layui-form-item" >
				<label class="layui-form-label">班级</label>
				<div class="layui-input-block" style="width: 120px;">
					<input type="text" name="classname" placeholder="请登录系统" autocomplete="off"
						class="layui-input" id="classname" disabled>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block" style="width: 120px;">
					<input type="text" name="stuname" placeholder="" autocomplete="off"
						class="layui-input" lay-verify="required|checkname" value="${user.realname}">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">旧密码</label>
				<div class="layui-input-block" style="width: 200px;">
					<input type="password" name="oldpwd" placeholder="不改密码可为空" autocomplete="off"
						class="layui-input" id="oldpwd" lay-verify="checkoldpwd">
				</div>
			</div>
			
			<div class="layui-form-item" style="width: 10%;">
				<label class="layui-form-label">新密码</label>
				<div class="layui-input-block" style="width: 200px;">
					<input type="password" name="newpwd1" placeholder="不改密码可为空" autocomplete="off"
						class="layui-input" id="newpwd1" lay-verify="pwdlength">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">确认密码</label>
				<div class="layui-input-block" style="width: 200px;">
					<input type="password" name="newpwd2" placeholder="再次输入新密码" autocomplete="off"
						class="layui-input" id="newpwd2" lay-verify="repass">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-block" style="width: 200px;">
					<input type="text" name="stuemail" placeholder="" autocomplete="off"
						class="layui-input"  lay-verify="email|required" value="${user.email}" >
				</div>
			</div>
			
			<!--
			<div class="layui-form-item">
				<label class="layui-form-label">语言</label>
				<div class="layui-input-block">
					<input type="checkbox" name="like[java]" title="Java"> <input
						type="checkbox" name="like[C++]" title="C++"><input
						type="checkbox" name="like[Python]" title="Python">
				</div>
			</div>
			
		
			<div class="layui-form-item">
				<label class="layui-form-label">城市</label>
				<div class="layui-input-block" style="width: 120px;">
					<select name="interest" lay-filter="aihao">
						<option value="北京">北京</option>
						<option value="上海">上海</option>
						<option value="深圳">深圳</option>
						<option value="青岛">青岛</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item" style="margin: 0px 0px 0px 25px;">
				<label class="layui-form-label">邮件提醒</label>
				<div class="layui-input-block">
					<input type="checkbox" lay-skin="switch">
				</div>
			</div>
			<br>
			<div class="layui-form-item layui-form-text"
				style="margin: 0px 0px 0px 25px; width: 600px;">
				<label class="layui-form-label">个人介绍</label>
				<div class="layui-input-block">
					<textarea placeholder="请填写内容" class="layui-textarea"></textarea>
				</div>
			</div> -->
			<br>
			<div class="layui-form-item" style="margin: 0px 0px 0px -110px;">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter="formDemo" style="width: 100px;">确认修改</button>
				</div>
			</div>
			
			<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
		</form>
	</div>

	<script src="./lib/layui/layui.js" charset="utf-8"></script>
	<script>
		//提交表单数据到后台，进行资料修改
		layui.use('form' ,function() {
			var $ = layui.$;
			var form = layui.form;
			
			//自定义验证规则
			form.verify({
				checkname: function(value) {
					if (value.length < 2) {
						return '用户名至少要2个字符';
					}
				}, 
				pwdlength: function(value) {
					if (value.length != 6 && value!="") {
						return '用户密码至少要6个字符';
					}else if(value==""&&$('#oldpwd').val()!="") {
						return '新密码不能为空';
					}
					
				}, 
				repass: function(value) {
					if ($('#newpwd1').val() != $('#newpwd2').val()) {
						return '两次密码不一致';
					}
				},
				checkoldpwd: function(value) {
					var result = "";
					if (value != "") {
							var data = "oldpwd="+$('#oldpwd').val();
							$.ajax({
					             type: "post",
					             url: "Student/selOldPwd",
					             data: data,
					             async: false,
					             dataType: "json",
					             success:function(data) {
					             	result = data;
						         } 
				        	 });
					}
					
					if (result == "-1") {
						return '旧密码校验不通过';
					}
				}
				
			});
			
			
			//监听提交
			form.on('submit(formDemo)', function(data) {
				layer.confirm('是否修改?', function(index) {
					 $.ajax({
			             type: "post",
			             url: "Student/updateStuInfo",
			             data: data.field,
			             dataType: "json",
			             async:false,
			             success:function(data) {
			                console.log(JSON.stringify(data))
								if (data.code == 0) {
									layer.alert('修改资料成功,请重新登录系统', {
											icon: 6
										},
										function() {
											window.parent.location.href="./login.html"
										});
								} else {
									layer.alert(data.msg, {
										icon: 5
									});

								}
			             }
			         });
			         layer.close(index);
				});
				//去掉了就没法出现layer.confirm
				return false;
			});
		});
		
	</script>
	 <script type="text/javascript">
       	/*获取登录的用户信息 */
	      window.onload = function() {
	     	var $ = layui.$
     		$.ajax({
					type: "POST", 
					url: "User/getUsername",
					data: '',
					dataType: 'json', 
					async: false,
					success: function(data) {
						$("#classname").val(data.className);
					}
				})
	      }
    </script>
	
	<script>
		layui.use('util', function() {
			var util = layui.util;

			//执行
			util.fixbar({
				bar1 : true,
				bar2 : true,
				click : function(type) {
					console.log(type);
					if (type === 'bar1') {
						alert('点击了bar1')
					}
				}
			});
		});
		
	</script>
	  
</body>
</html>
