<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<form:form commandName="noticeVo" name="noticeForm" id="noticeForm" method="post">
	<form:hidden path="noticeIdx" />
	<form:hidden path="francIdx" />
	<div class="modal-body" style="height: 450px;">
		<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
			<div id="modalId">
				<label class="lb ">제목 <font class="fontSet">[필수]</font></label><br>
				<form:input path="title" class="form-control" style="ime-mode:active; width: 95%; display: inline;" maxlength="50" placeholder="제목" />
			</div>
			<br />
			<div>
				<label class="lb ">내용 <font class="fontSet">[필수]</font></label>
				<textarea id="content" style="border: 1px solid #EAEAEA; width: 95%; height: 330px; overflow-y: scroll;"><c:out value="${noticeVo.content}"/></textarea>
			</div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6"
			style="border-left: 1px solid #EAEAEA; height: 430px; padding-left: 3%;">
			<div>
				<label>형식</label>
				<div class="col-input-area">
					<input type="radio" id="isHtmlYn" name="isHtmlYn" value="Y"
						<c:if test="${noticeVo.isHtmlYn eq 'Y'}">checked</c:if> />&nbsp;HTML&nbsp;
					<input type="radio" id="isHtmlYn" name="isHtmlYn" value="N"
						<c:if test="${noticeVo.isHtmlYn eq 'N'}">checked</c:if> />&nbsp;TEXT
				</div>
			</div>
			<br>
			<div>
				<label>활성화여부</label>
				<div class="col-input-area">
					<input type="checkbox" data-group-cls="btn-group-sm" id="activeYn" data-reverse checked><br />
				</div>
			</div>
			<br>
			<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6" style="padding: 0;">
				<label>시작일<font class="fontSet">[필수]</font></label>
				<div class="input-group date" style="width: 90%;">
					<form:input path="startDate" class="form-control" style="background-color: #fff;" readonly="true" />
					<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				</div>
			</div>
			<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6" style="padding: 0;">
				<label>종료일<font class="fontSet">[필수]</font></label>
				<div class="input-group date" style="width: 90%;">
					<form:input path="endDate" class="form-control"
						style="background-color: #fff;" readonly="true" />
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-calendar"></i></span>
				</div>
			</div>
			&nbsp;
			<div>
				<label>팝업</label>
				<div class="col-input-area">
					<input type="checkbox" data-group-cls="btn-group-sm"
						id="popupActiveYn" onchange="ispopup();" data-reverse><br />
				</div>
			</div>
			<br>
			<div id="popupdate">
				<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6" style="padding: 0;">
					<label>팝업시작일</label>
					<div class="input-group date" style="width: 90%;"
						onclick="isdatepicker(event)">
						<form:input path="popupStartDate" class="form-control"
							readonly="true" />
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-calendar"></i></span>
					</div>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6" style="padding: 0;">
					<label>팝업종료일</label>
					<div class="input-group date" style="width: 90%;"
						onclick="isdatepicker(event)">
						<form:input path="popupEndDate" class="form-control"
							readonly="true" />
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-calendar"></i></span>
					</div>
				</div>
				&nbsp;
			</div>
		</div>
	</div>
	<br />
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="noticeSave()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>
	<input type="hidden" id="parentIdx">
</form:form>
<script type="text/javaScript">
	$(document).ready(function() {
		$('.modal-dialog').css('width', '1000px');
		$('#activeYn').checkboxpicker(	defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		$('#popupActiveYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		$.datePickerCommon.createDatePicker('startDate');
		$.datePickerCommon.createDatePicker('endDate');
		$.datePickerCommon.createDatePicker('popupStartDate');
		$.datePickerCommon.createDatePicker('popupEndDate');

		$('#activeYn').prop('checked', "${noticeVo.activeYn}" == "N" ? false : true);

		if ("${noticeVo.popupActiveYn}" == "N" || "${noticeVo.popupActiveYn}" == "")
			$('#popupActiveYn').prop("checked", false);
		else
			$('#popupActiveYn').prop("checked", true);

		if ("${noticeVo.isHtmlYn}" == "Y" || "${noticeVo.isHtmlYn}" == "")
			$('#isHtmlYn').prop("checked", true);
		else
			$('#isHtmlYn').prop("checked", false);


		ispopup();
	});

	function ispopup() {
		if ($('#popupActiveYn').prop('checked')) {
			$('#popupStartDate').attr('disabled', false).css(
					'background-color', '#fff');
			$('#popupEndDate').attr('disabled', false).css('background-color',
					'#fff');
		} else {
			$('#popupStartDate').val('').attr('disabled', true).css(
					'background-color', '#eee');
			$('#popupEndDate').val('').attr('disabled', true).css(
					'background-color', '#eee');
		}
	}

	function isdatepicker(event) {
		if (!$('#popupActiveYn').prop('checked'))
			event.stopImmediatePropagation();
	}

	function noticeSave() {
		var popupActiveYn = '';
		var popupStartDate = $('#popupStartDate').val();
		var popupEndDate = $('#popupEndDate').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();

		if (!$.formCheck('#noticeForm #title', 'Y', 50, 'N', '제목'))
			return;
		if (!$.formCheck('#noticeForm #content', 'Y', 4000, 'N', '내용'))
			return;

		var ishtml = $.formOtherUtil.isArrCheck('isHtmlYn');

		if (ishtml.length == 0) {
			$.modalCommon.alertView('형식을 선택하세요.');
			return;
		}

		if (!$.formCheck('#noticeForm #startDate', 'Y', 50, 'N', '시작일'))
			return;
		if (!$.formCheck('#noticeForm #endDate', 'Y', 50, 'N', '종료일'))
			return;

		if (new Date(startDate) > new Date(endDate)) {
			$.modalCommon.alertView("종료일이 시작일보다 빠를 수 없습니다.", null, null, null);
			return;
		}

		if ($('#popupActiveYn').prop('checked')) {
			popupActiveYn = 'Y';

			if (popupStartDate == '') {
				$.modalCommon.alertView("팝업시작일을 입력해 주세요.", null, null, null);
				return;
			}
			if (popupEndDate == '') {
				$.modalCommon.alertView("팝업종료일을 입력해 주세요.", null, null, null);
				return;
			}
			if (new Date(popupStartDate) > new Date(popupEndDate)) {
				$.modalCommon.alertView("종료일이 시작일보다 빠를 수 없습니다.", null, null,
						null);
				return;
			}
		} else {
			popupActiveYn = 'N';
		}

		var param = {
			noticeIdx : $('#noticeForm #noticeIdx').val(),
			francIdx : $('#francIdx').val(),
			domainIdx : $('#domainIdx').val(),
			brandIdx : $('#brandIdx').val(),
			title : $('#noticeForm #title').val(),
			startDate : $('#noticeForm #startDate').val() + ' 00:00:00',
			endDate : $('#noticeForm #endDate').val() + ' 23:59:59',
			popupActiveYn : popupActiveYn,
			content : $('#noticeForm #content').val(),
			activeYn : $('#activeYn').prop('checked') ? "Y":"N",
			isHtmlYn : $("#noticeForm :input:radio[name=isHtmlYn]:checked").val()
		};
		if (popupStartDate != "")
			param.popupStartDate = popupStartDate + ' 00:00:00';
		if (popupEndDate != "")
			param.popupEndDate = popupEndDate + ' 23:59:59';
		if($('#francIdx').val() != "0")
			param.brandOnlyYn = "N";
		else
			param.brandOnlyYn = "Y";


		var msg = $('#noticeForm #noticeIdx').val() == "0" ? 'C':'U';

		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/NoticeManagement/NoticeCreate"/>', param);
				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msg,'공지사항');
					noticeList();
			    }
			}
		}
		commonSave(msg, callBack);

	}
</script>
