<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 구글차트 -->
<script type="text/javascript" src="<c:url value='/Content/site/loader.js'/>"></script>

<div class="box box-widget">
	<form:form commandName="CalendarVo" name="user" id="user" method="post">
	<div class="box-header">
		<div class="user-block under-line">스케줄 편성표 목록</div>
	</div>

	<div class="box-body">
		<div class="input-group date" style="width:235px;">
		<form:input path="searchDate" class="form-control" style="background-color: #fff;" readonly="true" onchange="salesDateChange();"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
		</div>
		<br>
		<div style="overflow:scroll;overflow-y:hidden;height:650px;">
		<div class="ibox float-e-margins">
            <!-- BEGIN TABLE -->
            <div id="mychart" style="width:auto; height: 650px;"></div>
			<!-- END TABLE -->
           </div>
	</div>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
				<form:hidden path="domainIdx" />
				<form:hidden path="brandIdx" />
				<form:hidden path="francIdx" />
		</div>
	</div>
	</form:form>
</div>


<script type="text/javaScript">
	var deviceList, calendarList;
	var date;
	var year;
	var month;
	var today;
	var day;
	var endday;
	settingDate();
	$.datePickerCommon.createDatePicker('searchDate');
    $('#searchDate').attr('readOnly', true);

	$(document).ready(function () {
		google.charts.load("current", {packages:["timeline"]});
		google.charts.setOnLoadCallback(drawChart);

		treeSearchList(0, false, null, 'STO02');
		treeHeight();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });
	});

	function salesDateChange(){
		scheduleList();
	}

	function settingDate(){
		date = new Date();
		year = date.getFullYear();
		month;
		if (date.getMonth()+1 < 10) {
			month = '0' + new String(date.getMonth()+1);
		}else {
			month =  new String(date.getMonth()+1);
		}
		day = new String(date.getDate());

		if (day < 10) {
			day = '0' + day;
		}

		today = year + '-' + month + '-' + day;
		endday = today;

		$("#searchDate").val(today);
	}

	//트리 클릭 이벤트
	function callbackTreeSearchKeyEvt(e)
	{
        if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO01');
        	e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data){
		settingDate();
		$('#user #domainIdx').val(data.node.li_attr.domainidx);
        $('#user #brandIdx').val(data.node.li_attr.brandidx);
        $('#user #francIdx').val(data.node.li_attr.francidx);
    	scheduleList();
	}

	// 	트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData)
	{
		settingDate();
		scheduleList();
	}


	function scheduleList(){
		var param = {
						domainIdx : $("#domainIdx").val(),
						brandIdx : $("#brandIdx").val(),
						francIdx : $("#francIdx").val(),
						searchDate : $("#searchDate").val()
					};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/Calendar/CalendarList/"/>', param);

		if (result.resultCode == 0){
			deviceList = result.data.deviceList;
			calendarList = result.data.calendarList;
			google.charts.setOnLoadCallback(drawChart);
        }
	}

	function drawChart(){
		var data = calendarList;

		if(data == null || data == undefined)
			return;

		var headList = deviceList;
		var length = headList.length;

		var searchDate = $("#searchDate").val().replace(/-/gi,'');
		var container = document.getElementById('mychart');
		var chart = new google.visualization.Timeline(container);
		var dataTable = new google.visualization.DataTable();
		dataTable.addColumn({
			type : 'string',
			id : 'Term'
		});

		dataTable.addColumn({
			type : 'string',
			id : 'Name'
		});

		dataTable.addColumn({
			type : 'date',
			id : 'Start'
		});

		dataTable.addColumn({
			type : 'date',
			id : 'End'
		});

		var colorAry = new Array();
		var colorNameAry = new Array();

		for(var headCnt=0; headCnt<length; headCnt++){
        	 var dataList = data;
 			 var dataLength = dataList.length;
 			 var checkRet = false;
        	 for(var dataCnt=0; dataCnt<dataLength; dataCnt++){
 				if(headList[headCnt].deviceIdx == dataList[dataCnt].deviceIdx && dataList[dataCnt].mainScheduleIdx != null){
 					checkRet = true;
 					var screenScheduleTypeName = "";

 					var overLab = false;
					for(var i = 0; i < colorNameAry.length; i++){
						if(colorNameAry[i] == dataList[dataCnt].mainScheduleName)
							overLab = true;
							break;
 					}

					if(!overLab){
						//기본
						if(dataList[dataCnt].scheduleType == 'R')
						{
							colorNameAry.push(dataList[dataCnt].mainScheduleName);
							colorAry.push('#7598be');
						}
						//예약
						else
						{
							colorNameAry.push(dataList[dataCnt].mainScheduleName);
							colorAry.push('#3a5f7a');
						}
					}

					var hour = parseInt(dataList[dataCnt].startTime.substring(0,2));
					var min = parseInt(dataList[dataCnt].startTime.substring(2,4));
					var sec = parseInt(dataList[dataCnt].startTime.substring(4,6));

					var ehour = parseInt(dataList[dataCnt].endTime.substring(0,2));
					var emin = parseInt(dataList[dataCnt].endTime.substring(2,4));
					var esec = parseInt(dataList[dataCnt].endTime.substring(4,6));
					dataTable.addRows([
						[headList[headCnt].deviceName,
							dataList[dataCnt].mainScheduleName,
							new Date(year, month, day, hour, min, sec),
							new Date(year,month, day, ehour, emin, esec)]
					]);
 				}
 			}

        	if(checkRet == false){
        		colorAry.push('white');
        		dataTable.addRows([
					[headList[headCnt].deviceName,
						'스케줄 미편성',
						new Date(year, month, day, 0, 0, 0),
						new Date(year, month, day, 23, 59, 59)]
				]);
        	}
	        checkRet == false;
		}

		var options = {
			colors : colorAry,
 			width : 1360,
			hAxis : {
				format : 'HH시',
				minValue : new Date(year, month, day, 0, 0, 0),
				maxValue : new Date(year, month, day, 23, 59, 59)
			}
		};

		chart.draw(dataTable, options);
	}

</script>