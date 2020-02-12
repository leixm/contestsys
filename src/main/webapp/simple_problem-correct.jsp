<!DOCTYPE html>
<html class="x-admin-sm">
    <head>
    <%@page isELIgnored="false" %>
		<%@ page contentType="text/html;charset=UTF-8" language="java"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <meta charset="UTF-8">
        <title>批改页面</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <link rel="stylesheet" href="./css/font.css">
        <link rel="stylesheet" href="./css/xadmin.css">
		<link rel="stylesheet" href="lib/layui/css/layui.css">
		<script type="text/javascript" src="./assets/js/jquery-1.8.3.min.js" ></script>
        <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
        <script type="text/javascript" src="./js/xadmin.js"></script>
        <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
        <!--[if lt IE 9]>
            <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
            <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]--></head>
    <% request.setCharacterEncoding("UTF-8"); %>
    <body>
        <div class="layui-fluid">
            <div class="layui-row">
                <form class="layui-form">
			<% System.out.println("aaa"); %>
					<!-- 无用div控制布局作用 -->
                	<div style="height: 30px">
                	</div>

					<div class="layui-form-item" style="display: none">
						<label for="cStatusId" class="layui-form-label">
							考试状态id
						</label>
						<div class="layui-input-inline">
							<input type="text" id="cStatusId" name="cStatusId" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${cStatusId}" disabled>
						</div>
					</div>

					<div class="layui-form-item" style="display: none">
						<label for="cStatus" class="layui-form-label">
							考试批改状态
						</label>
						<div class="layui-input-inline">
							<input type="text" id="cStatus" name="cStatus" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${cStatus}" disabled>
						</div>
					</div>

					<div class="layui-form-item">
						<label for="simType" class="layui-form-label">
							所属课程
						</label>
						<div class="layui-input-inline">
							<input type="text" id="simType" name="" required=""  autocomplete="off" class="layui-input" value="${correctObjects[0].courseName}" disabled>
						</div>
					</div>

					<c:forEach var="obj" items="${correctObjects}" varStatus="s">
						<div class="layui-form-item" style="display: none">
							<label for="simCount" class="layui-form-label">
								题目数量统计，方便更新分数
							</label>
							<div class="layui-input-inline">
								<input type="text" id="simCount" name="simCount" required="" autocomplete="off" class="layui-input" value="${s.count}" disabled>
							</div>
						</div>

						<div class="layui-form-mid layui-word-aux" style="margin: auto">
							<span class="x-red">---- 题目${s.count} ----</span>
						</div>

						<div class="layui-form-item" style="display: none">
							<label for="solutionId" class="layui-form-label">
								作答Id，方便更新分数
							</label>
							<div class="layui-input-inline">
								<input type="text" id="solutionId" name="solutionId${s.count}" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${obj.solutionId}" disabled>
							</div>
						</div>

						<div class="layui-form-item">
							<label for="simContent" class="layui-form-label">
								题目内容
							</label>
							<div class="layui-input-inline">
							<textarea type="text" id="simContent" name="" required=""  style="height: 180px" autocomplete="off" class="layui-textarea" value="${obj.simContent}" disabled>
									${obj.simContent}</textarea>
							</div>
						</div>

						<div class="layui-form-item">
							<label for="simContent" class="layui-form-label">
								学生作答内容
							</label>
							<div class="layui-input-inline">
							<textarea type="text" id="stuAnswer" name="" required="" style="height: 180px" autocomplete="off" class="layui-textarea" value="${obj.stuAnswer}" disabled>
									${obj.stuAnswer}</textarea>
							</div>
						</div>

						<div class="layui-form-item" style="display: none">
							<label for="oldScore" class="layui-form-label">
								上次提交的得分，方便数据库进行更新分数统计
							</label>
							<div class="layui-input-inline">
								<input type="text" id="oldScore" name="oldScore${s.count}" required=""  autocomplete="off" class="layui-input" value="${obj.realScore}">
							</div>
						</div>

						<div class="layui-form-item">
							<label for="simScore" class="layui-form-label">
								满分分值
							</label>
							<div class="layui-input-inline">
								<input type="text" id="simScore" name="" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${obj.fullScore}" disabled>
							</div>
						</div>

						<div class="layui-form-item">
							<label for="realScore" class="layui-form-label">
								实际得分
							</label>
							<div class="layui-input-inline">
								<input type="text" id="realScore" name="realScore${s.count}" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${obj.realScore}">
							</div>
							<div class="layui-form-mid layui-word-aux">
								<span class="x-red">*</span>
							</div>
						</div>
					</c:forEach>

					<!-- 无用div控制布局作用 -->
					<div style="height: 30px">
					</div>

                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <button class="layui-btn" lay-filter="add" lay-submit="">确认</button>
					</div>
                </form>

            </div>
        </div>
        <script>
				layui.use(['form', 'layer','laydate'],
					function() {
						$ = layui.jquery;
						var form = layui.form,
						layer = layui.layer;
						var laydate = layui.laydate;

						//监听提交
						form.on('submit(add)',function(data) {
							var formData = data.field;
							$.ajax({
									type: "POST",
                                	url: "Teacher/correctSimpleProblem",
									dataType: 'json',
									data: formData,
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

        <script>
			var _hmt = _hmt || []; (function() {
                var hm = document.createElement("script");
                hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(hm, s);
            })();
		</script>

	</body>

</html>