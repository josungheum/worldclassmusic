<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
	.col-title-area{
		width:19%;
		display:inline;
		padding-top:6px;
		padding-bottom:6px;
		float:left;
	}

	.col-input-area{
		width:79%;
		display:inline;
		padding-top:3px;
		padding-bottom:3px;
		float:left;
	}

	.datatable-custom{
		font-size : 11px;
	}

	.jstree-proton .jstree-disabled.jstree-clicked {
 	   background: transparent;
	}

	#scheduleTable.dataTable tbody tr.selected {
	  background-color: #B2CCFF;
	}
	
	#frm .selectBox {
		margin-bottom: 5px;
	}
	li.device > a .jstree-themeicon {background-image: none !important;}
	li.franc > a .jstree-themeicon {background-image: block !important;}
</style>
<div class="modal-body" id="scheduleForm">
	<form:form commandName="ScheduleVo" name="frm" id="frm" method="post">
		<form:hidden path="mainScheduleIdx"/>
		<form:hidden path="domainIdx"/>
		<form:hidden path="brandIdx"/>
		<form:hidden path="dayOfWeek"/>
		<input type="hidden" id="disabledTable_scheduleTable" value="N" />
		<textarea id="resultScreenList" style="display:none;">${ScheduleVo.resultScreenList}</textarea>
		<div style="float: left;width:70%; display:inline;">
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>스케줄 종류<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<label class="radio-inline"><input type="radio" name="scheduleType" <c:if test="${ScheduleVo.scheduleType == null or ScheduleVo.scheduleType eq 'R'}">checked</c:if>  value="R">기본</label>
					<label class="radio-inline"><input type="radio" name="scheduleType" <c:if test="${ScheduleVo.scheduleType eq 'T'}">checked</c:if>  value="T">예약</label>
					<label class="radio-inline"><input type="radio" name="scheduleType" <c:if test="${ScheduleVo.scheduleType eq 'E'}">checked</c:if>  value="E">이벤트</label>
				</div>
			</div>
			
			<div style="clear: both;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>스케줄명<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<form:input path="mainScheduleName" class="form-control" style="ime-mode: active" maxlength="50" placeholder="스케줄명"/>
				</div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>시작/종료일<font class="fontSet">[필수]</font></label>
				</div>
				<div class="col-input-area" style="width:35%;">
					<div class="input-group date">
						<form:input path="startDate" class="form-control" style="background-color: #fff" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>
				</div>
				
				<div class="col-input-area" style="width:35%;">
					<div class="input-group date" >
						<form:input path="endDate" class="form-control" style="background-color: #fff" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>
				</div>
			</div>
			<div style="clear: both;"></div>
			<div class="time1">
				<div class="col-title-area" style="width:25%;">
					<label>시간<font class="fontSet">[필수]</font></label>
				</div>
				<div class="col-input-area" style="width:35%;">
					<div class="input-group bootstrap-timepicker ">
						<form:input path="startTime" id="startTime" class="form-control" style="ime-mode:active; display: inline; background-color: white;" placeholder="시작시간"/><span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
					</div>
					
				</div>
				
				<div class="col-input-area" style="width:35%;">
					<div class="input-group bootstrap-timepicker ">
						<form:input path="endTime" id="endTime" class="form-control" readonly="true" style="ime-mode:active; display: inline; background-color: white;" placeholder="종료시간"/><span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
					</div>
				</div>
			</div>
			<div class="time1" style="clear: both;"></div>
			
			<div class="week">
				<div class="col-title-area" style="width:25%;">
					<label>요일<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<label class="radio-inline"><input type="radio" name="dayofweekRadio" checked value="1111111">매일</label>
					<label class="radio-inline"><input type="radio" name="dayofweekRadio" value="0111110">주중</label>
					<label class="radio-inline"><input type="radio" name="dayofweekRadio" value="1000001">주말</label>
					<div style="clear: both;"></div>
					<label style="font-weight:normal; margin-right: 15px;color: red;">
						<input class="optiongroup" name="dayofweekCheckBox" type="checkbox" checked="checked" id="dayofweek0" value="0" style="margin-right: 3px;">일
					</label>
					<label style="font-weight:normal; margin-right: 15px;">
						<input class="optiongroup" name="dayofweekCheckBox" type="checkbox" checked="checked" id="dayofweek1" value="1" style="margin-right: 3px;">월
					</label>
					<label style="font-weight:normal; margin-right: 15px;">
						<input class="optiongroup" name="dayofweekCheckBox" type="checkbox" checked="checked" id="dayofweek2" value="2" style="margin-right: 3px;">화
					</label>
					<label style="font-weight:normal; margin-right: 15px;">
						<input class="optiongroup" name="dayofweekCheckBox" type="checkbox" checked="checked" id="dayofweek3" value="3" style="margin-right: 3px;">수
					</label>
					<label style="font-weight:normal; margin-right: 15px;">
						<input class="optiongroup" name="dayofweekCheckBox" type="checkbox" checked="checked" id="dayofweek4" value="4" style="margin-right: 3px;">목
					</label>
					<label style="font-weight:normal; margin-right: 15px;">
						<input class="optiongroup" name="dayofweekCheckBox" type="checkbox" checked="checked" id="dayofweek5" value="5" style="margin-right: 3px;">금
					</label>
					<label style="font-weight:normal; margin-right: 15px;color: #1c84c6;">
						<input class="optiongroup" name="dayofweekCheckBox" type="checkbox" checked="checked" id="dayofweek6" value="6" style="margin-right: 3px;">토
					</label>
				</div>
			</div>
			<div class="week" style="clear: both;"></div>
			<div class="event">
				<div class="col-title-area" style="width:25%;">
					<label>이벤트<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<label class="radio-inline"><input type="radio" name="eventType" <c:if test="${ScheduleVo.eventType == '' or ScheduleVo.eventType == null or ScheduleVo.eventType eq 'B'}">checked</c:if>  value="B">시작 종료 제어</label>
					<!-- label class="radio-inline"><input type="radio" name="eventType" <c:if test="${ ScheduleVo.eventType eq 'M'}">checked</c:if> value="M">모바일</label-->
				</div>
			</div>
			<div class="event" style="clear: both;"></div>
			<div class="event">
				<div class="col-title-area" style="width:25%;">
					<label>실행 횟수<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<label class="radio-inline"><input type="radio" name="countType" <c:if test="${ScheduleVo.countType == '' or ScheduleVo.countType == null or ScheduleVo.countType eq '1'}">checked</c:if> value="1">1회</label>
					<!-- label class="radio-inline"><input type="radio" name="countType" <c:if test="${ScheduleVo.countType eq '2'}">checked</c:if> value="2">다수</label-->
				</div>
			</div>
			<div class="event" style="clear: both;"></div>
			
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>활성화</label>
				</div>
				<div class="col-input-area" style="width:75%;">
					<input type="checkbox" data-group-cls="" id="activeYn" value="Y" data-reverse checked><br/>
				</div>
			</div>
			<div>
				<div class="col-title-area">
					<label>스크린<font class="fontSet">[필수]</font></label>
				</div>
				<div class="col-input-area">
					<div id="DataTable"> </div>
				</div>
			</div>
		</div>
		<div style="float: left;width:30%;display:inline;">
			<label>적용 단말 목록<font class="fontSet">[필수]</font></label>
			<select id="deviceType" class="form-control selectBox" onchange="searchDeviceTree()">
				<option value="">단말 종류 전체</option>
				<c:forEach items="${brandDeviceTypeList}" var="list">
					<option value="${list.deviceType}"><c:out value="${list.deviceTypeName}" /></option>
				</c:forEach>
			</select>
			<!-- select id="deviceSiteType" class="form-control selectBox" onchange="searchDeviceTree()">
				<option value="">매장 구분 전체</option>
				<c:forEach items="${deviceSiteTypeList}" var="list">
					<option value="${list.codeValue}"><c:out value="${list.codeName}" /></option>
				</c:forEach>
			</select-->
			<select id="deviceResType" class="form-control selectBox" onchange="searchDeviceTree()">
				<option value="">해상도 전체</option>
				<c:forEach items="${deviceResTypeList}" var="list">
					<option value="${list.codeValue}"><c:out value="${list.codeName}" /></option>
				</c:forEach>
			</select>
			<div style="height:405px; border-color:#d2d6de; border-style:solid; overflow: auto;" id="targetTreeList" ></div>
		</div>
		<div style="clear:both;"></div>
		<br/>
		<div class="modal-footer">
			<button type="button" id="SaveSchedule" class="btn btn-primary" onclick="fn:saveSchedule()" id="procBtn">저장</button>
			<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
		</div>
	</form:form>
</div>
<script type="text/javaScript">
	$(document).ready(function() {
		
// 		$('.time').mask('00:00:00');
		
		$.timePickerCommon.createSecondTimePicker('startTime', $("#startTime").val(), 23);
		$.timePickerCommon.createSecondTimePicker('endTime', $("#endTime").val(), 23);
		
		$('.modal-dialog').css('width', '1100px').css('height', '1200px');
		
		
		$('#activeYn').checkboxpicker(	defaults = {	offLabel: '비활성화',	onLabel: '활성화'});

		$.datePickerCommon.createDatePicker('startDate');
		$.datePickerCommon.createDatePicker('endDate');
		ScheduleTree();

		$('#startDate').attr('readOnly', true);
		$('#endDate').attr('readOnly', true);

		$($.fn.dataTable.tables(true)).DataTable().columns.adjust();

		if ($("#resultScreenList").val() != '') {
			var screenList = JSON.parse($("#resultScreenList").val());
			$('#scheduleTable').DataTable().rows.add(screenList).draw();
		}

		if ("${ScheduleVo.activeYn}" == "N"){
			$('#activeYn').prop("checked", false);
		}
		else{
			$('#activeYn').prop("checked", true);
		}
		
		//변경 주 체크박스 값 대입
		if($("#dayOfWeek").val() != ""){
			var daOfWeek = $("#dayOfWeek").val().split("");
			for(var i = 0; i < daOfWeek.length; i++){
				$("#dayofweek"+i).attr("checked",daOfWeek[i] == "1" ?true:false);
			}	
		}
		

		// 버튼 비활성화 처리
		if($('#user #domainIdx').val() == 0 || $('#user #brandIdx').val() == 0 || $('#user #francIdx').val() == 0) {
			changeDisable();
		} else if ($('#user #domainIdx').val() != 0 && $('#user #brandIdx').val() != 0 && $('#user #francIdx').val() != 0) {

		}
		if("${sessionScope.adminType}" == 'ADM0003' && ("${ScheduleVo.adminType}" == 'ADM0001' || "${ScheduleVo.adminType}" == 'ADM0002')){
			changeDisable();
		}
		
		scheduleTypeShowHide($("#scheduleForm [name='scheduleType']:checked").val());
		
		
	});
	
	// 디바이스 타입 변경 시 처리
	$("[name='dayofweekRadio']").change(function(){
		var daOfWeek = $(this).val().split("");
		for(var i = 0; i < daOfWeek.length; i++){
			$("#dayofweek"+i).attr("checked",daOfWeek[i] == "1" ?true:false);
		}
	});
	
	// 시간 변경 시 처리
	$("[name='startTime'], [name='endTime']").change(function(){
		timeMaxData(this);
	});
	
	
	
	
	// 스케줄 타입별 내용 변경
	$("[name='scheduleType']").change(function(){
		scheduleTypeShowHide($(this).val());
		
		
	});
	
	function scheduleTypeShowHide(type){
		if(type == "R"){	//기본
			$(".event, .week, .time1").hide();
			$("#startTime").val("00:00:00");
			$("#endTime").val("23:59:59");
		}else if(type == "T"){	//예약
			$(".time1, .week").show();
			$(".event").hide();
		}else{	//이벤트
			$(".week").hide();
			//$(".time1, .event").show();
			$(".time1, .event").hide();	//시간, 이벤트, 실행횟수 숨김 처리
			$("#startTime").val("00:00:00");
			$("#endTime").val("23:59:59");
		}
	}
	
	function timePicker(){
		$('.time').mask('00:00:00');
	}

	$('#DataTable').crudDatatable({
		id : 'scheduleTable',
    	mainName : 'screenName',
    	seqKey : 'screenIdx',
		scrollHeight : '20vh',
		headList : [{
				title : '스크린'
			}, {
				title : ' 타입'
			}, {
				title : '기본시간'
			}, {
				title : '재생시간'
			}],
		jsonData : null,
		columnDefs : [
			{
				'targets': 0,
	            'data': 'screenName',
	            'className': 'text-center tl200',
			    'render': function (data, type, full, meta) {
			        return '<div class="ellipsis200">'+fnDataTableRenderText(data)+'</div>';
			    }
			}, 
			{
				'targets': 1,
	            'data': 'screenType',
	            'className': 'text-center',
			    'render': function (data, type, full, meta) {
			    	var text = data;
			    	if(data == "S"){
			    		text = '스크린';
			    	}
			    	else if(data == "O"){
			    		text = '운영';
			    	}
			    	else {
			    		text = '컨텐츠';
			    	}
			        return text;
			    }
			}, 
			{
				'targets': 2,
                'data': 'playTime',
                'className': 'text-center',
			    'render': function (data, type, full, meta) {
			    	var time = new Date(0, 0, 0, 0, 0, 0, data);
            		var hour = time.getHours().toString().length > 1 ? time.getHours().toString() : "0" + time.getHours().toString();
            		var min = time.getMinutes().toString().length > 1 ? time.getMinutes().toString() : "0" + time.getMinutes().toString();
            		var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();
                    return hour + ':' + min + ':' + sec;
			    }
			},
			{
				'targets': 3,
                'data': 'screenPlayTime',
                'className': 'text-center tl130',
			    'render': function (data, type, full, meta) {
			    	if(full.screenType == 'S'){
			    		return '<input type="text" name="screenPlayTime" maxlength="8" style="width: 100%; display: none;" class="form-control time" onkeydown="timePicker();" value="00:00:00">'
			    	}
			    	else {
			    		var time = new Date(0, 0, 0, 0, 0, 0, data);
	            		var hour = time.getHours().toString().length > 1 ? time.getHours().toString() : "0" + time.getHours().toString();
	            		var min = time.getMinutes().toString().length > 1 ? time.getMinutes().toString() : "0" + time.getMinutes().toString();
	            		var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();
	            		return '<input type="text" name="screenPlayTime" maxlength="8" style="width: 100%;" class="form-control time" onkeydown="timePicker();" value="'+hour + ':' + min + ':' + sec+'">';
			    	}
			    	
			    }
			}
			],
		columns:[{
				width:'40%'
			},{
				width:'10%'
			},{
				width:'10%'
			},{
				width:'10%'
			}]
			
    });
	

	function searchDeviceTree(){
		var param = {
				domainIdx : $("#scheduleForm #domainIdx").val(),
		   		brandIdx : $("#scheduleForm #brandIdx").val(),
		   		francIdx : $("#scheduleForm #francIdx").val(),
		   		mainScheduleIdx : $("#scheduleForm #mainScheduleIdx").val(),
		   		searchDvType: $('#scheduleForm #deviceType').val(),
		   		searchDvRes: $('#scheduleForm #deviceResType').val(),
		   		//searchDvSite: $('#scheduleForm #deviceSiteType').val() //매장 구분은 쓰지 않음
		   		searchDvSite: ""
		}

		$('#targetTreeList').jstree('destroy');
		treeSearchList(0,'<c:url value="/Schedule/SearchKioskTree"/>',param);

	}
	
	
</script>