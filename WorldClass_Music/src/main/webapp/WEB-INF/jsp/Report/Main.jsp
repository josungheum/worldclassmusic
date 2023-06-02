<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">보고서 조회</div>
	</div>
	<form:form commandName="ReportVo" name="user" id="user" method="post" >
		<form:hidden path="searchDay" />
		<form:hidden path="searchEndDay" />
		<form:hidden path="reportType" />
	</form:form>
	<h3><a id="anchorExcelDown" href="" hidden>Download Excel Document</a></h3>
	<div class="box-body">
		<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
			<div class="form-group">
				<div class="label-div col-sm-1" style="height:30px;display:inline-block;">
		        	<label class="lb" style="">조회 조건 :</label>
		        </div>
		        <div class="col-sm-9" style="padding-left:0px;">
		        	<div class="col-input-area" style="width:165px; vertical-align:top; display:inline-block;">
		        		<select id="selectreportType" class="form-control input-sm">
							<option value="Report_BrandCountByDayofweek" selected>요일별 브랜드 조회 수</option>
							<option value="Report_BrandCountByDay">일별 브랜드 조회 수</option>
							<option value="Report_ActionCountByHour">시간대 별 사용 건수</option>
							<option value="Report_ActionCountByDayofweek">요일 별 사용 건수</option>
						</select>
					</div>
		        	<div class="col-input-area" style="width:139px; vertical-align:top; display:inline-block;">
						<div class="input-group date">
							<input type="text" id="localSearchDay" class="form-control" style="background-color: #fff; height:30px; width:100px;" value="" >
							<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					</div>
					<div class="col-input-area" style="width:30px; vertical-align:middle; display:inline-block;">
						&nbsp;&nbsp; ~ &nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					<div class="col-input-area" style="width:139px; vertical-align:top; display:inline-block;">
						<div class="input-group date" >
							<input type="text" id="localSearchEndDay" class="form-control" style="background-color: #fff; height:30px; width:100px;" value="">
							<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					</div>
					<div class="col-input-area" style="width:120px; vertical-align:top; display:inline-block;">
						<button type="button" class="btn btn-primary btn-sm" id="btn_excel_down" onclick="excelDown();">엑셀 다운로드</button>
					</div>
		        </div>
			</div>
		</div>
	</div>
</div>
<script type="text/javaScript">
	var date;
	var year;
	var month;
	var today;
	var endday;

	$(document).ready(function () {
		$.datePickerCommon.createDatePicker('localSearchDay');
	    $.datePickerCommon.createDatePicker('localSearchEndDay');
		$('#localSearchDay').attr('readOnly', true);
		$('#localSearchEndDay').attr('readOnly', true);
		
		$('button').not(".notDisabled").attr('disabled', false);
		
		settingDate();
		
		//FIXME : 트리가 없어지지 않아 강제 삭제
		$(".col-md-3").remove();
		$("#bodyDiv").attr('class', 'col-md-12 col-sm-12 col-xs-12 col-lg-12');		
	});

	function settingDate(){
		date = new Date();
		year = date.getFullYear();
		month;
		if (date.getMonth()+1 < 10) {
			month = '0' + new String(date.getMonth()+1);
		}else {
			month =  new String(date.getMonth()+1);
		}
		var day = new String(date.getDate());

		if (day < 10) {
			day = '0' + day;
		}

		today = year + '-' + month + '-' + day;
		endday = today;

		$("#localSearchDay").val(today);
		$("#localSearchEndDay").val(today);
	}

	function excelDown(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectreportType = $("#selectreportType > option:selected").val();
		
		localSearchDay = localSearchDay.replace(/-/gi, "");
		localSearchEndDay = localSearchEndDay.replace(/-/gi, "");

		$("#user #searchDay").val(localSearchDay);
		$("#user #searchEndDay").val(localSearchEndDay);
		$("#user #reportType").val(selectreportType);
		
		$('#user #searchDay').not(".notDisabled").attr('disabled', false);
		$('#user #searchEndDay').not(".notDisabled").attr('disabled', false);
		$('#user #reportType').not(".notDisabled").attr('disabled', false);
 
		$('#user').attr('action', '<c:url value="/Report/ExportExcel"/>');
   		$('#user').submit();
	}

	function reportTypeChange(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectreportType = $("#selectreportType > option:selected").val();
// 		var selectpayProcCode = $("#selectPayProcCode > option:selected").val();

		var dfday = dateDiff(localSearchDay, localSearchEndDay)

		if(localSearchDay > localSearchEndDay){
			settingDate();
			$.modalCommon.alertView("조회 종료일은 시작일보다\n이후 일자이어야 합니다.");
			return false;
		}/* else if(dfday > 31){
			settingDate();
			$.modalCommon.alertView("조회 범위는 최대 31일 입니다.");
			return false;
		}*/else{
			$("#user #searchDay").val(localSearchDay);
			$("#user #searchEndDay").val(localSearchEndDay);
			$("#user #reportType").val(selectreportType);
// 			$("#user #payProcCode").val(selectpayProcCode);
		}
	}

	function searchDateChange(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectreportType = $("#selectreportType > option:selected").val();
// 		var selectpayProcCode = $("#selectPayProcCode > option:selected").val();

		var dfday = dateDiff(localSearchDay, localSearchEndDay)

		if(localSearchDay > localSearchEndDay){
			settingDate();
			$.modalCommon.alertView("조회 종료일은 시작일보다\n이후 일자이어야 합니다.");
			return false;
		}/*else if(dfday > 31){
			settingDate();
			$.modalCommon.alertView("조회 범위는 최대 31일 입니다.");
			return false;
		}*/else{
			$("#user #searchDay").val(localSearchDay);
			$("#user #searchEndDay").val(localSearchEndDay);
			$("#user #reportType").val(selectreportType);
// 			$("#user #payProcCode").val(selectpayProcCode);
		}
	}

	// 두개의 날짜를 비교하여 차이를 알려준다.
	function dateDiff(_date1, _date2) {
	    var diffDate_1 = _date1 instanceof Date ? _date1 : new Date(_date1);
	    var diffDate_2 = _date2 instanceof Date ? _date2 : new Date(_date2);

	    diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
	    diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

	    var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
	    diff = Math.ceil(diff / (1000 * 3600 * 24));

	    return diff;
	}
</script>