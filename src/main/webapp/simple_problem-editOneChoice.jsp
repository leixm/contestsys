<!DOCTYPE html>
<html class="x-admin-sm">
    <head>
    <%@page isELIgnored="false" %>
		<%@ page contentType="text/html;charset=UTF-8" language="java"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <meta charset="UTF-8">
        <title>欢迎页面-X-admin2.2</title>
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

					<div class="layui-form-item">
						<label for="simId" class="layui-form-label">
							<span class="x-red">*</span>题目id
						</label>
						<div class="layui-input-inline">
							<input type="text" id="simId" name="simId" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${simId}" disabled>
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>

					<div class="layui-form-item">
						<label for="simType" class="layui-form-label">
							<span class="x-red">*</span>题目类型
						</label>
						<div class="layui-input-inline">
							<input type="text" id="simType" name="simType" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${simType}" disabled>
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>

					<div class="layui-form-item">
						<label for="simScore" class="layui-form-label">
							<span class="x-red">*</span>题目分值
						</label>
						<div class="layui-input-inline">
							<input type="text" id="simScore" name="simScore" required="" lay-verify="required" autocomplete="off" class="layui-input" value="${simScore}">
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>

					<div class="layui-form-item">
						<label for="simContent" class="layui-form-label">
							<span class="x-red">*</span>题目内容
						</label>
						<div class="layui-input-inline">
							<textarea type="text" id="simContent" name="simContent" required="" lay-verify="required" style="height: 180px" autocomplete="off" class="layui-textarea" value="">
								${simContent}</textarea>
						</div>
						<div class="layui-form-mid layui-word-aux">
							<span class="x-red">*</span>
						</div>
					</div>

					<!-- 无用div控制布局作用 -->
					<div style="height: 30px">
					</div>

					<div class="layui-form-item">
						<label for="optionDiv" class="layui-form-label">
							<span class="x-red">*</span>选项内容：
						</label>
						<button onclick="removeOption()" type="button" class="layui-btn layui-btn-radius layui-btn-danger" style="float: right; margin-right:30px; ">删除</button>
						<button onclick="addOption()" type="button" class="layui-btn layui-btn-radius layui-btn-normal" style="float: right; ">添加</button>
						<div class="layui-input-inline" id="optionDiv">
							<c:forEach var="obj" items="${options}" varStatus="s">
								<input type="text" id="option${s.count}" name="option${s.count}" required="" lay-verify="required" autocomplete="off" class="layui-input options" value="${obj.content}">
							</c:forEach>
						</div>
					</div>

					<!-- 无用div控制布局作用 -->
					<div style="height: 30px">
					</div>

					<div class="layui-form-item">
						<label for="answerDiv" class="layui-form-label">
							<span class="x-red">*</span>答案内容：
						</label>
						<button onclick="removeAnswer()" type="button" class="layui-btn layui-btn-radius layui-btn-danger" style="float: right; margin-right:30px; ">删除</button>
						<button onclick="addAnswer()" type="button" class="layui-btn layui-btn-radius layui-btn-normal" style="float: right;">添加</button>
						<div class="layui-input-inline" id="answerDiv">
							<c:forEach var="obj" items="${answers}" varStatus="s">
								<input type="text" id="answer${s.count}" name="answer${s.count}" required="" lay-verify="required" autocomplete="off" class="layui-input answers" value="${obj.content}">
							</c:forEach>
						</div>
					</div>


                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <button class="layui-btn" lay-filter="add" lay-submit="">更新</button></div>
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
							formData['optionNum'] = $(".options").length;
                            formData['answerNum'] = $(".answers").length;
							$.ajax({
									type: "POST",
									url: "Teacher/updateSimpleProblem",
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


				function addOption() {
                    var addOptionNum = $(".options").length + 1;
					var opName = "option"+addOptionNum;
					var addOp = "<input type='text'  id=\'"+opName+"\' name=\'"+opName+"\' required='' lay-verify='required' autocomplete='off' class='layui-input options' value=''>"
					$("#optionDiv").append(addOp);
				}
				function  removeOption() {
					var opName = $(".options").length;
                    if(opName > 1) {
                        $("#option" + opName).remove(); //remove text box
                    }else {
                        layer.msg("选项至少需要一个", {
                            time: 2000,
                        });
					}
				}

				function addAnswer() {
                    var addAnswerNum = $(".answers").length + 1;
                    var anName = "answer"+addAnswerNum;
					var addAn = "<input type='text' id=\'"+anName+"\' name=\'"+anName+"\' required='' lay-verify='required' autocomplete='off' class='layui-input answers' value=''>"
                    var tempType = "${simType}";
                    if(tempType == "单选题") {
                        layer.msg('单选题答案只能为一个', {
                            time: 2000, //4s后自动关闭
                        });
                    }else {
                        $("#answerDiv").append(addAn);
                    }

				}
                function  removeAnswer() {
                    var anName = $(".answers").length;
                    if(anName > 1) {
                        $("#answer"+anName).remove(); //remove text box
					}else {
                        layer.msg('答案至少需要一个', {
                            time: 2000, //4s后自动关闭
                        });
					}
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