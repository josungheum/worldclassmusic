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
	
	#displayBrightness, #volume {
	 	width: 2em;
	    height: 1.6em;
	    top: 50%;
	    margin-top: -.8em;
	    text-align: center;
	    line-height: 1.6em;
	  }
	  .selectBox {
		margin-bottom: 5px;
	}
</style>
<div class="modal-body" id="scheduleForm">
	<form:form commandName="ControlScheduleVo" name="frm" id="frm" method="post">
		<form:hidden path="controlScheduleIdx"/>
		<form:hidden path="domainIdx"/>
		<form:hidden path="brandIdx"/>
		<form:hidden path="francIdx"/>
		<form:hidden path="dayOfWeek"/>
		<div style="float: left;width:70%; display:inline;">
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>스케줄 명<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:62%;display:inline;padding-bottom: 5px;">
					<form:input path="controlScheduleName" class="form-control" style="ime-mode: active" maxlength="50" placeholder="스케줄명"/>
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
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>시간<font class="fontSet">[필수]</font></label>
				</div>
				<div class="col-input-area" style="width:35%;">
				
					<div class="input-group bootstrap-timepicker " style="float: left; margin-right : 10px; width: 100%">
						<form:input path="startTime" id="startTime" class="form-control" style="ime-mode:active; display: inline; background-color: white;" placeholder="시작시간"/>
					</div>
				</div>
			</div>
			<div style="clear: both;"></div>
			<div>
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
			<div style="clear: both;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>제어 명령</label>
				</div>
				<div style="float: left;width:75%;display:inline;padding-bottom: 5px;">
				 <c:forEach items="${controlCodeList}" var="list">
				 		<c:if test="${list.codeValue == 'CTL0001'}">
							<div class="col-input-area" style="width:30%;">
							<label>
								<input type="radio" name="controlType" <c:if test="${ControlScheduleVo.controlType == list.codeValue}">checked</c:if> value="${list.codeValue}">
								${list.codeName}
							</label>
							</div>
							<div class="col-input-area" style="width:35%;">
								<label class="radio-inline"><input type="radio" name="${list.codeValue}_val" <c:if test="${ControlScheduleVo.controlType == 'CTL0001' and ControlScheduleVo.controlValue eq 'Y'}">checked</c:if> value="Y">ON</label>
								<label class="radio-inline"><input type="radio" name="${list.codeValue}_val" <c:if test="${ControlScheduleVo.controlType == 'CTL0001' and ControlScheduleVo.controlValue eq 'N'}">checked</c:if> value="N">OFF</label>
							</div>
						<div style="clear: both;"></div>
						</c:if>
						<c:if test="${list.codeValue == 'CTL0004'}">
							<div class="col-input-area" style="width:30%;">
							<label>
								<input type="radio" name="controlType" <c:if test="${ControlScheduleVo.controlType == list.codeValue}">checked</c:if> value="${list.codeValue}">
								${list.codeName}
							</label>
							</div>
							<div class="col-input-area" style="width:35%;">
								<label class="radio-inline"><input type="radio" name="${list.codeValue}_val" <c:if test="${ControlScheduleVo.controlType == 'CTL0004' and ControlScheduleVo.controlValue eq 'Y'}">checked</c:if> value="Y">ON</label>
								<label class="radio-inline"><input type="radio" name="${list.codeValue}_val" <c:if test="${ControlScheduleVo.controlType == 'CTL0004' and ControlScheduleVo.controlValue eq 'N'}">checked</c:if> value="N">OFF</label>
							</div>
						<div style="clear: both;"></div>
						</c:if>
						<!-- c:if test="${list.codeValue == 'CTL0005'}">
							<div class="col-input-area" style="width:30%;">
								<label>
									<input type="radio" name="controlType" <c:if test="${ControlScheduleVo.controlType == list.codeValue}">checked</c:if> value="${list.codeValue}">
									${list.codeName}
								</label>
							</div>
							<div class="col-input-area" style="width:35%; padding-top:8px;">
								<div id="volumeSlider">
								  <div id="volume" class="ui-slider-handle"></div>
								</div>
								<input type="range" style="display:none;" class="custom-range" id="volume" value="<c:if test="${ControlScheduleVo.controlValue == null}">0</c:if><c:if test="${ControlScheduleVo.controlType == 'CTL0005' and ControlScheduleVo.controlValue != null}">${ControlScheduleVo.controlValue}</c:if>">
							</div-->
						<!-- /c:if-->
						<c:if test="${list.codeValue != 'CTL0005' and list.codeValue != 'CTL0004' and list.codeValue != 'CTL0001'}">
						<div class="col-input-area" style="width:30%;">
						<label>
							<input type="radio" name="controlType" <c:if test="${ControlScheduleVo.controlType == list.codeValue}">checked</c:if> value="${list.codeValue}">
							${list.codeName}
						</label>
						</div>
						<div class="col-input-area" style="width:35%;"><input type="radio" name="${list.codeValue}_val" checked value="Y" style="display:none;"></div>
						<div style="clear: both;"></div>
						</c:if>
				</c:forEach>
					</div>
				</div>
			<div style="clear: both;"></div>
			<div>
				<div class="col-title-area" style="width:25%;">
					<label>활성화</label>
				</div>
				<div class="col-input-area" style="width:75%;">
					<input type="checkbox" data-group-cls="" id="activeYn" value="Y" data-reverse checked><br/>
				</div>
			</div>
		</div>
		<div style="float: left;width:30%; display:inline;">
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
		$('#scheduleForm').parent().css('min-height', '610px');
		$.timePickerCommon.createSecondTimePicker('startTime', $("#startTime").val(), 23);
		
		sliderFunction("#volume", "#volumeSlider");
		
		if('${ControlScheduleVo.controlType}' == 'CTL0005'){
			var volume = '${ControlScheduleVo.controlValue}' == '' ? '0':'${ControlScheduleVo.controlValue}';
			$("#volume").attr("style","left: "+volume+"%;").text(volume);
		}
	  
		$('.modal-dialog').css('width', '1100px').css('height', '1200px');
		
		
		$('#activeYn').checkboxpicker(	defaults = {	offLabel: '비활성화',	onLabel: '활성화'});

		$.datePickerCommon.createDatePicker('startDate');
		$.datePickerCommon.createDatePicker('endDate');
		ScheduleTree();

		$('#startDate').attr('readOnly', true);
		$('#endDate').attr('readOnly', true);

		

		if ("${ControlScheduleVo.activeYn}" == "N"){
			$('#activeYn').prop("checked", false);
		}
		else{
			$('#activeYn').prop("checked", true);
		}
		
		//변경 주 체크박스 값 대입
		if($("#dayOfWeek").val() != ""){
			var daOfWeek = $("#dayOfWeek").val().split("");
			for(var i = 0; i < daOfWeek.length; i++){
				$("#dayofweek"+i).prop("checked",daOfWeek[i] == "1" ?true:false);
			}	
		}
		

		// 버튼 비활성화 처리
		if($('#user #domainIdx').val() == 0 || $('#user #brandIdx').val() == 0 || $('#user #francIdx').val() == 0) {
			changeDisable();
		} else if ($('#user #domainIdx').val() != 0 && $('#user #brandIdx').val() != 0 && $('#user #francIdx').val() != 0) {

		}
		if("${sessionScope.adminType}" == 'ADM0003' && ("${ControlScheduleVo.adminType}" == 'ADM0001' || "${ControlScheduleVo.adminType}" == 'ADM0002')){
			changeDisable();
		}
	});
	
	// 요일 변경 시  처리
	$("[name='dayofweekRadio'").change(function(){
		var daOfWeek = $(this).val().split("");
		for(var i = 0; i < daOfWeek.length; i++){
			$("#dayofweek"+i).prop("checked",daOfWeek[i] == "1" ?true:false);
		}
	});

function sliderFunction(id,sliderId){
	var handle = $( id);
    $( sliderId ).slider({
      create: function() {
        handle.text( $( this ).slider( "value" ) );
      },
      slide: function( event, ui ) {
        handle.text( ui.value );
      }
    });
}

function searchDeviceTree(){
	var param = {
			domainIdx : $("#scheduleForm #domainIdx").val(),
	   		brandIdx : $("#scheduleForm #brandIdx").val(),
	   		francIdx : $("#scheduleForm #francIdx").val(),
	   		mainScheduleIdx : $("#scheduleForm #mainScheduleIdx").val(),
	   		searchDvType: $('#scheduleForm #deviceType').val(),
	   		searchDvRes: $('#scheduleForm #deviceResType').val(),
	   		//searchDvSite: $('#scheduleForm #deviceSiteType').val()	//매장구분은 쓰지 않음
	   		searchDvSite: ""
	}

	$('#targetTreeList').jstree('destroy');
	treeSearchList(0,'<c:url value="/ControlSchedule/SearchKioskTree"/>',param);
}
</script>