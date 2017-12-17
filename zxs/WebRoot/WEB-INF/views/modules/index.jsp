<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="${ctxStatic }/css/bootstrap.min.css" />
<script src="${ctxStatic }/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#dbwzclick").click(function(){
			window.parent.location.href="${ctx}?index=5";		
		});
		$("#dbyhclick").click(function(){
			window.parent.location.href="${ctx}?index=6";		
		});
		$("#rycxclick").click(function(){
			window.parent.location.href="${ctx}?index=7";		
		});
		$("#sgdjclick").click(function(){
			window.parent.location.href="${ctx}?index=9";		
		});
		$("#aqksclick").click(function(){
			window.parent.location.href="${ctx}?index=2";		
		});
	});

	$(function() {
	
		if ($('#serviceimg').height() > $('#divicebody').height()) {
			$('#divicebody').css("height", $('#serviceimg').height());
		} else {
			$('#serviceimg').css("height", $('#divicebody').height());
		}
	});

	/** * 签收任务 */
	function claim(taskId) {
		$.get('${ctx}/act/task/claim', {
			taskId : taskId
		}, function(data) {
			if (data == 'true') {
				top.$.jBox.tip('签收完成');
				location = '${ctx}/zbwjbab/baZbwjbab/sh';
			} else {
				top.$.jBox.tip('签收失败');
			}
		});
	}
</script>
<style>
body,ul {
	padding: 0;
	margin: 0;
}

.bitable {
	border: 1px solid #ccc;
	width: 61%;
	margin-left: 1%;
	float: left;
	margin-top: 7px;
}

li {
	list-style: none;
	overflow: hidden;
}

.cotop {
	background: #e0eaf1;
	height: 25px;
	line-height: 25px;
	padding: 3px;
	border-bottom: 1px solid #ccc;
}

.divtbody {
	height: 230px;
	overflow: hidden;
}

.divtbody ul li .one {
	width: 10%;
	display: block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	float: left;
	padding: 0.5%;
	text-align: center;
	border-bottom: 1px solid #ccc;
	min-height: 20px;
}

.divtbody ul li .two {
	width: 28%;
	display: block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	float: left;
	text-align: center;
	border-left: 1px solid #ccc;
	padding: 0.5%;
	border-bottom: 1px solid #ccc;
	min-height: 20px;
}

.divtbody ul li .three {
	width: 29%;
	display: block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	float: left;
	text-align: center;
	border-left: 1px solid #ccc;
	padding: 0.5%;
	border-bottom: 1px solid #ccc;
	min-height: 20px;
}

.divtbody ul li .fore {
	width: 29%;
	display: block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	float: left;
	text-align: center;
	border-left: 1px solid #ccc;
	padding: 0.5%;
	border-bottom: 1px solid #ccc;
	min-height: 20px;
}

.divtbody ul li:nth-child(odd) {
	background-color: #f5f5f5;
}

.right {
	float: right;
	margin-right: 19px;
	cursor: pointer;
	text-decoration: underline;
}

.right_img {
	position: relative;
	float: right;
	margin-right: 8px;
	cursor: pointer;
	text-decoration: underline;
}

.topimg {
	display: table;
	margin: 0 auto;
	overflow: hidden;
	height: 110px
}

.topimg ul li a {
	display: block;
	overflow: hidden;
}

.topimg ul li {
	color: #0088cc;
	width: 100px;
	float: left;
	display: block;
	text-align: center;
	margin-right: 25px;
	margin-bottom-bottom: 10px;
}

.topimg ul li:hover {
	background: #f4f6e3;
	cursor: pointer;
	width: 120px;
}

.topimg ul li a img {
	max-width: 67px;
	width: 56%;
	padding-top: 5px;
}

.topimg ul li p {
	font-size: 14px;
	padding-top: 10px;
	font-weight: 900;
}

.caright {
	float: right;
	margin-right: 8px;
	cursor: pointer;
	text-decoration: underline;
}

.yellow {
	display: block;
	width: 20px;
	height: 20px;
	background: yellow;
	border-radius: 50%;
	margin: 0 auto;
}

.reddiv {
	margin: 0 auto;
	display: block;
	width: 20px;
	height: 20px;
	background: red;
	border-radius: 50%;
}

#news {
	width: 100%;
	word-break: keep-all; /* 不换行 */
	white-space: nowrap; /* 不换行 */
	overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
	text-overflow: ellipsis;
	/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
}
</style>
<script src="${ctxStatic }/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript">
	require.config({
		paths : {
			echarts : '${ctxStatic }/echarts-2.2.7/build/dist'
		}
	});
	require([ 'echarts', 'echarts/chart/pie', // 按需加载所需图表，如需动态类型切换功能，别忘了同时加载相应图表
	'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/map',
			'echarts/chart/funnel' ], function(ec) {
		var myChart = ec.init(document.getElementById('main'));
		var myCh = ec.init(document.getElementById('ma'));
		var option = {
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : [ '待办备案', '待办评价', '待办投诉', '预警信息' ]
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'pie', 'funnel' ],
						option : {
							funnel : {
								x : '25%',
								width : '50%',
								funnelAlign : 'center',
								max : 1548
							}
						}
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			series : [ {
				name : '系统概况',
				type : 'pie',
				radius : [ '50%', '70%' ],
				itemStyle : {
					normal : {
						label : {
							show : false
						},
						labelLine : {
							show : false
						}
					},
					emphasis : {
						label : {
							show : true,
							position : 'center',
							textStyle : {
								fontSize : '30',
								fontWeight : 'bold'
							}
						}
					}
				},
				data : [ {
					value : ${zb},
					name : '待办备案'
				}, {
					value : ${pj},
					name : '待办评价'
				}, {
					value : ${ts},
					name : '待办投诉'
				}, {
					value : ${yj},
					name : '预警信息'
				} ]
			} ]
		};
		opt = {
			//标题，每个图表最多仅有一个标题控件，每个标题控件可设主副标题  

			//提示框，鼠标悬浮交互时的信息提示  
			tooltip : {
				//触发类型，默认（'item'）数据触发，可选为：'item' | 'axis'  
				trigger : 'axis'
			},
			//图例，每个图表最多仅有一个图例  
			legend : {
				//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
				show : false,
				//水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
				x : 'center',
				//垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
				y : 'top',
				//legend的data: 用于设置图例，data内的字符串数组需要与sereis数组内每一个series的name值对应  
				data : [ '待办备案', '待办评价', '待办投诉', '预警信息' ]
			},
			//工具箱，每个图表最多仅有一个工具箱  
			toolbox : {
				//显示策略，可选为：true（显示） | false（隐藏），默认值为false  
				show : true,
				//启用功能，目前支持feature，工具箱自定义功能回调处理  
				feature : {
					//辅助线标志  
					mark : {
						show : true
					},
					//dataZoom，框选区域缩放，自动与存在的dataZoom控件同步，分别是启用，缩放后退  
					dataZoom : {
						show : true,
						title : {
							dataZoom : '区域缩放',
							dataZoomReset : '区域缩放后退'
						}
					},
					//数据视图，打开数据视图，可设置更多属性,readOnly 默认数据视图为只读(即值为true)，可指定readOnly为false打开编辑功能  
					dataView : {
						show : true,
						readOnly : true
					},
					//magicType，动态类型切换，支持直角系下的折线图、柱状图、堆积、平铺转换  
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					//restore，还原，复位原始图表  
					restore : {
						show : true
					},
					//saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'  
					saveAsImage : {
						show : true
					}
				}
			},
			//是否启用拖拽重计算特性，默认关闭(即值为false)  
			calculable : true,
			//直角坐标系中横轴数组，数组中每一项代表一条横轴坐标轴，仅有一条时可省略数值  
			//横轴通常为类目型，但条形图时则横轴为数值型，散点图时则横纵均为数值型  
			xAxis : [ {
				//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
				show : true,
				//坐标轴类型，横轴默认为类目型'category'  
				type : 'category',
				//类目型坐标轴文本标签数组，指定label内容。 数组项通常为文本，'\n'指定换行  
				data : [ '待办备案', '待办评价', '待办投诉', '预警信息' ]
			} ],
			//直角坐标系中纵轴数组，数组中每一项代表一条纵轴坐标轴，仅有一条时可省略数值  
			//纵轴通常为数值型，但条形图时则纵轴为类目型  
			yAxis : [ {
				//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
				show : true,
				//坐标轴类型，纵轴默认为数值型'value'  
				type : 'value',
				//分隔区域，默认不显示  
				splitArea : {
					show : true
				}
			} ],

			//sereis的数据: 用于设置图表数据之用。series是一个对象嵌套的结构；对象内包含对象  
			series : [ {
				//系列名称，如果启用legend，该值将被legend.data索引相关  
				name : '总量',
				//图表类型，必要参数！如为空或不支持类型，则该系列数据不被显示。  
				type : 'bar',
				//系列中的数据内容数组，折线图以及柱状图时数组长度等于所使用类目轴文本标签数组axis.data的长度，并且他们间是一一对应的。数组项通常为数值  
				data : [ ${qba}, ${pj}, ${qts}, ${qyj}, ],
				//系列中的数据标注内容  
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				},
				//系列中的数据标线内容  
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};

		myChart.setOption(option);
		myCh.setOption(opt);
	});
</script>
<script type="text/javascript">
	
</script>
</head>

<body>
	<div class="bitable" id="#serviceparent">

		<div class="cotop">
			<img src="${ctxStatic }/images/icon_changyongfuwu.png" />常用服务
		</div>

		<div class="topimg" id="serviceimg">
			<ul>
				<li><a href="javascript:" id="dbwzclick"><img
						src="${ctxStatic }/images/chancheng_05.png"></a>
					<p>待办违章</p></li>
				<li><a href="javascript:" id="dbyhclick"><img
						src="${ctxStatic }/images/chancheng_11.png"></a>
					<p>待办隐患</p></li>
				<li><a href="javascript:" id="rycxclick"><img
						src="${ctxStatic }/images/chancheng_07.png"></a>
					<p>人员查询</p></li>
				<li><a href="javascript:" id="sgdjclick"><img
						src="${ctxStatic }/images/chancheng_03.png"></a>
					<p>事故登记</p></li>
				<li><a href="javascript:" id="aqksclick"><img
						src="${ctxStatic }/images/chancheng_09.png"></a>
					<p>安全考试</p></li>

				<%-- <%-- <li><a href="${ctx}/sg/sgtj/aqsg"><img
						src="${ctxStatic }/images/chancheng_15.png"></a>
					<p>统计分析</p></li> --%>
				<%-- <li><a href="${ctx}//wzyh/wzyhtj/wzyhToTal"><img
						src="${ctxStatic }/images/chancheng_15.png"></a>
					<p>统计分析</p></li>  --%>
				<%--  <li>
            <a href="${ctx}/xcjc/xcjcXcqdb/OTXtwo"><img src="${ctxStatic }/images/chancheng_15.png"></a>
            <p>统计分析</p>
        </li> --%>
			</ul>
		</div>

	</div>


	<!--公告-->
	<div class="bitable" style="width:36%">
		<div class="cotop">
			<img src="${ctxStatic }/images/icon_tonggao.png" />通知公告 <span
				class="right"><a href="${ctx }/ts/tsNews/list">更多 <img
					src="${ctxStatic }/images/gengduo.png" /></a></span>
		</div>
		<div class="commbody" id="divicebody"
			style='padding-left: 10px;padding-right: 10px;'>
			<c:forEach items="${newslist }" var="news">
				<li style='margin-top: 1px;'><a id='news'
					href="${ctx }/ts/tsNews/form?id=${news.id}">${news.title}</a></li>
			</c:forEach>
		</div>
	</div>

	<!--待办违章-->
	<div class="bitable">
		<div class="cotop">

			<img src="${ctxStatic }/images/icon_daibanweizhang.png" /> 待办违章 <span
				class="right"><a href="${ctx }/wz/wzgl/list">更多 <img
					src="${ctxStatic }/images/gengduo.png" />
			</a></span>
		</div>
		<div class="divtbody">
			<ul>
				<li><span class="one" style="width:18%">违章编号</span> <span
					class="two" style="width:17%">违章时间</span> <span class="two"
					style="width:12%">违章类型</span> <span class="three" style="width:11%">违章项目</span>
					<span class="two" style="width:11%">当前状态</span> <span class="two"
					style="width:12%">处理人</span> <span class="three" style="width:11%">违章详情</span>
				</li>
				<c:forEach items="${pjlist }" var="pj">
					<li><span class="one" style="width:18%">${pj.violationNo }
					</span> <span class="two" style="width:17%">
							<td><fmt:formatDate value="${pj.violationDate }"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</span> <span class="two" style="width:12%">${pj.violationType }</span> <span
						class="three" style="width:11%">${pj.projectName }</span> <span
						class="two" style="width:11%">${pj.status }</span> <span
						class="two" style="width:12%">${pj.handleUnit }</span> <span>
							<a class="two" style="width:11%"
							href="${ctx}/ry/rycx/wzdetail?id=${pj.id}">查看详情</a>
					</span></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<!-- 隐患状态表-->
	<div class="bitable" style="width:36%">
		<div class="cotop">
			<img src="${ctxStatic }/images/icon_yinhuangzhuangtaibiao.png" />
			隐患状态表<span class="right"> <a href="${ctx }/yh/total/list">更多<img
					src="${ctxStatic }/images/gengduo.png" /></a></span>
		</div>
		<div class="divtbody">
			<ul>
				<li><span class="one" style="width:21%;">部门</span> <span
					class="two" style="width:18%;">已整改</span> <span class="three"
					style="color: #333;width:18%;">未整改 </span> <span class="fore"
					style="color: #333;width:18%;">总数</span> <span class="fore"
					style="color: #333;width:18%;">整改率</span> <c:forEach
						items="${yhlist }" var="yh">
						<li><span class="one" style="width:21%;">${yh.departmentName }</span>
							<span class="two" style="width:18%;">${yh.change} </span> <span
							class="three" style="color: #333;width:18%;">${yh.notchange}
						</span> <span class="three" style="color: #333;width:18%;">${yh.total }</span>
							<span class="three" style="color: #333;width:18%;">${yh.rate }</span>
						</li>
					</c:forEach>
			</ul>
		</div>
	</div>


	<!--待办投诉-->
	<div class="bitable">
		<div class="cotop">
			<img src="${ctxStatic }/images/icon_daibanyinhuan.png" /> 待办隐患<span
				class="right"><a href="${ctx }/ts/tsTsdjb/todo">更多<img
					src="${ctxStatic }/images/gengduo.png" /></a></span>
		</div>
		<div class="divtbody">
			<ul>
				<li><span class="one" style="width:18%">隐患编号</span> <span
					class="two" style="width:14%">隐患时间</span> <span class="two"
					style="width:12%">隐患类型</span> <span class="three" style="width:12%">隐患项目</span>
					<span class="fore" style="width:12%">当前状态</span> <span class="fore"
					style="width:12%">处理人</span> <span class="fore" style="width:12%">隐患详情</span></li>

				<c:forEach items="${tslist}" var="act" varStatus="st">
					<c:set var="ba" value="${tstslist }" />
					<c:set var="task" value="${act.task}" />
					<c:set var="vars" value="${act.vars}" />
					<c:set var="procDef" value="${act.procDef}" />

					<c:set var="status" value="${act.status}" />
					<li><span class="one" style="width:10%"> <c:if
								test="${ba[st.index].code==1 }">
								<div class="yellow"></div>
							</c:if> <c:if test="${ba[st.index].code==2}">
								<div class="reddiv"></div>
							</c:if>
					</span> <a class="two" style="width:25%"
						href="${ctx}/ts/tsTsdjb/form?id=${ba[st.index].id}">${ba[st.index].xmmc }</a>
						<span class="two" style="width:21%">${ba[st.index].dqzt }</span> <span
						class="three" style="width:20%"><fmt:formatDate
								value="${ba[st.index].updateDate }"
								pattern="yyyy-MM-dd HH:mm:ss" /></span> <span class="fore"
						style="width:18%"> <c:if test="${empty task.assignee}">
								<a href="javascript:claim('${task.id}');">签收任务</a>
							</c:if> <c:if test="${not empty task.assignee}">
								<%--
							<a href="${ctx}${procExecUrl}/exec/${task.taskDefinitionKey}?procInsId=${task.processInstanceId}&act.taskId=${task.id}">办理</a> --%>
								<a
									href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">任务办理</a>
							</c:if>

					</span></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!--重复违章人员-->
	<div class="bitable" style="width:36%">
		<div class="cotop">
			<img src="${ctxStatic }/images/icon_chongfuweizhang.png" /> 重复违章人员<span
				class="right"><a href="${ctx}/wz/total/list">更多<img
					src="${ctxStatic }/images/gengduo.png" /></a></span>
		</div>
		<div class="divtbody">
			<ul>
				<li><span class="one" style="width:18%;">工号</span> <span
					class="two" style="width:15%;">姓名</span> <span class="three"
					style="color: #333;width:15%;">工作年限 </span> <span class="fore"
					style="color: #333;width:15%;">单船</span> <span class="fore"
					style="color: #333;width:15%;">非船</span> <span class="fore"
					style="color: #333; width:14%">违章数</span></li>

				<c:forEach items="${yjlist }" var="yj">
					<li><span class="one" style="width:18%;">${yj.workNo }</span>
						<span class="two" style="width:15%;">${yj.adminName} </span> <span
						class="three" style="color: #333;width:15%;">${yj.year} </span> <span
						class="three" style="color: #333;width:15%;">${yj.ship }</span> <span
						class="three" style="color: #333;width:15%;">${yj.notship }</span>
						<span class="three" style="color: #333;width:14%;">${yj.total }</span>
					</li>
				</c:forEach>

			</ul>
		</div>
	</div>
</body>

</html>