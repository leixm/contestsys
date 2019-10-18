<html class="x-admin-sm">
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
		<link rel="stylesheet" href="lib/layui/css/layui.css">
		<link rel="stylesheet" href="css/font.css">
		<link rel="stylesheet" href="css/xadmin.css">
		<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
		<script src="./lib/layui/layui.all.js" charset="utf-8"></script>
		<script type="text/javascript" src="./js/xadmin.js"></script>
		<!--[if lt IE 9]>
          <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
          <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

	</head>

	<body> 
		<body>
			<div class="x-nav">
				<span class="layui-breadcrumb"> <a href="">首页</a> <a href="">演示</a>
					<a> <cite>导航元素</cite></a>
				</span> <a class="layui-btn layui-btn-small" style="line-height: 1.6em; margin-top: 3px; float: right" onclick="location.reload()"
				 title="刷新"> <i class="layui-icon layui-icon-refresh" style="line-height: 30px"></i></a>
			</div>
			<div class="layui-fluid">
				<div class="layui-row layui-col-space15">
					<div class="layui-col-md12">
						<div class="layui-card">


							<div class="layui-card-body layui-table-body layui-table-main">
								<table class="layui-table" id="demo" lay-filter="demo"></table>
							</div>

						</div>
					</div>
				</div>
			</div>
		</body>






		<script id="dateTpl" type="text/html">
			{{#  var fn = function(){
        return moment(d.ApplyDate).format("YYYY-MM-DD");
         }; if(true){ }}
        {{ fn() }}
        {{#  } }}
 
    </script>



		<script>
			$(document).ready(function() {

				initTable();
			});



			function initTable() {
				var timeArea = $("#timearea").val();
				var startTime = "";
				var endTime = "";
				if (timeArea) {
					startTime = timeArea.split(" - ")[0]; //开始时间
					endTime = timeArea.split(" - ")[1]; //结束时间
				}
				layui.use(['table', 'layer','laydate'], function() {
					var table = layui.table;
					var laydate = layui.laydate;
					//执行渲染 
					table.render({
						id: 'demo',
						elem: '#demo', //指定原始表格元素选择器（推荐id选择器）
						height: '600', //容器高度
						cols: [
							[ {
								field: 'user_id',
								title: '学号',
								width: 180,
								sort: true
							}, {
								field: 'realName',
								title: '姓名',
								width: 180,
								sort: true
							}, {
								field: 'name',
								title: '班级',
								width: 200,
								sort: true
							}] 
						],
						url: 'Contest/ViewContestStudent?id=' + '${contest_id}',
						method: 'post',
						limits: [10, 20, 30, 50, 100],
						limit: 10, //默认采用10
						loading: true,
						page: true
					});



	
		

		
		
				});

			}
		</script>
	</body>



</html>
