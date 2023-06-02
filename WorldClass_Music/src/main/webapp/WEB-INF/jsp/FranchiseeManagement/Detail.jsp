<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/jsp/Common/metaCommon.jsp" />
<form:form commandName="franchiseeVo" name="franc" id="franc" method="post">
<form:hidden path="mainImg" id="mainImg"/>
<br>
<style>
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td{
	padding: 7px;
	vertical-align: middle;
}
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td label{
	vertical-align: middle;
}
</style>
<div class="box-body">
	<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
		<table>
		<tr>
			<td style="width: 150px">
				<label style="margin:0px;">대표이미지</label>
			</td>
			<td style="width: 700px">
				<img id="mainImgPath" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="FileAdd();"/>
			</td>
		</tr>
		<tr>
			<td>
				<label style="margin:0px;">사이트 주소</label>
			</td>
			<td>
				<form:input path="francAddr" class="form-control" style="ime-mode:active; width: 70%; display: inline; background-color: white;" maxlength="200" placeholder="기본 주소"/><br/><br/>
				<form:input path="francAddrDetail" class="form-control" style="ime-mode:active; width: 70%; display: inline; background-color: white;" maxlength="200" placeholder="상세 주소"/>
			</td>
		</tr>
		<tr>
			<td>
				<label style="margin:0px;">전화번호</label>
			</td>
			<td>
				<form:input path="telNum" class="form-control clsNotKor" style="ime-mode:active; width: 70%; display: inline; background-color: white;" maxlength="20" placeholder="전화번호"/>
			</td>
		</tr>
		<tr>
			<td>
				<label style="margin:0px;">영업시간</label>
			</td>
			<td>
				<div class="input-group bootstrap-timepicker timepicker" style="float: left; margin-right : 10px; width: 25%">
					<form:input path="salesStartTime" id="salesStartTime" class="form-control" style="ime-mode:active; display: inline; background-color: white;" placeholder="시작시간"/>
				</div>
				<div class="input-group bootstrap-timepicker timepicker" style="float: left; width: 25%">
					<form:input path="salesEndTime" id="salesEndTime" class="form-control" style="ime-mode:active; display: inline; background-color: white;" placeholder="종료시간"/>
				</div>
				<input type="checkbox" class="mainEdit" onclick="allDayCheck();" style="margin-left : 10px;" data-group-cls="btn-group-sm" name="allDaySalesYn" id="allDaySalesYn" value="" data-reverse <c:if test="${franchiseeVo.allDaySalesYn eq 'Y'}">checked</c:if>> 익일<br /><br />
			</td>
		</tr>
		<tr>
			<td>
				<label style="margin:0px;">업종</label>
			</td>
			<td>
				<select class="form-control" style="ime-mode:active; width: 70%; display: inline; background-color: white;" id="categoryType">
			    	<option value="0" >선택 하세요</option>
			    	<c:forEach items="${catetoryTypeList}" var="list">
						<option value="<c:out value="${list.codeValue}" />" <c:if test="${list.codeValue eq franchiseeVo.categoryType}">selected</c:if>><c:out value="${list.codeName}" /></option>
					</c:forEach>
			    </select>
			</td>
		</tr>
		<tr>
			<td>
				<label style="margin:0px;">휴무일</label>
			</td>
			<td>
				<form:input path="closedDayInfo" class="form-control" style="ime-mode:active; width: 70%; display: inline; background-color: white;" maxlength="100" placeholder="휴무일"/>
			</td>
		</tr>
		<tr>
			<td>
				<label style="margin:0px;">메모</label>
			</td>
			<td>
				<form:textarea path="memo" class="form-control" style="ime-mode:active; width: 70%; display: inline; background-color: white;" cols="3" rows="4" placeholder="메모"/>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="box-footer" id="inputBtn">
	<div class="pull-right" id="btnGroup">
		<button type="button" class="btn btn-primary2" data-toggle="modal" id="saveBtn" onclick="detailSave();"><i class="glyphicon glyphicon-saved mr-5"></i>저장</button>
		<!-- <button type="button" class="btn btn-primary" data-toggle="modal" id="deleteBtn" onclick="Delete();">Delete</button> -->
	</div>
</div>
</form:form>
<script type="text/javascript">
	$(document).ready(function () {
		if("${franchiseeVo.mainimgPath}" != "")
			$("#mainImgPath").attr("src","${franchiseeVo.mainimgPath}");

		$.timePickerCommon.createTimePicker('salesStartTime', $("#salesStartTime").val(), 23);
		$.timePickerCommon.createTimePicker('salesEndTime', $("#salesEndTime").val(), 23);

	});

	function detailSave(){
		if (!$.formCheck('#francAddr', 'N', 200, 'N', '주소')) return;
		if (!$.formCheck('#francAddrDetail', 'N', 200, 'N', '상세 주소')) return;
		if (!$.formCheck('#telNum', 'N', 20, 'N', '전화번호')) return;
		if (!$.formCheck('#closedDayInfo', 'N', 100, 'N', '휴무일')) return;
		if (!$.formCheck('#memo', 'N', 4000, 'N', '내용')) return;
		
		var param = {
			francIdx: $("#francIdx").val(),
			brandIdx: $("#brandIdx").val(),
			mainImg: $("#mainImg").val(),
			francAddr: $('#francAddr').val(),
			francAddrDetail: $('#francAddrDetail').val(),
			telNum: $('#telNum').val(),
			salesStartTime: $('#salesStartTime').val(),
			salesEndTime: $('#salesEndTime').val(),
			allDaySalesYn: $('#allDaySalesYn').val(),
			memo: $('#memo').val(),
			closedDayInfo: $('#closedDayInfo').val(),
			categoryType : $('#categoryType').val()
		};

		var msgCode = 'U';
		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/FranchiseeManagement/DetailInfoUpdate"/>', param);

				if (dResult.resultCode == 0){
					commMessage(msgCode,'사이트 상세정보');
			   }
			}
		}
		commonSave(msgCode, callBack);
	}

	function allDayCheck(){
		if($("#allDaySalesYn").is(":checked")){
// 			$("#salesStartTime").val("00:00").attr("disabled","disabled");
// 			$("#salesEndTime").val("23:59").attr("disabled","disabled");
			$("#allDaySalesYn").val("Y");
		}else{
// 			$("#salesStartTime").removeAttr("disabled");
// 			$("#salesEndTime").removeAttr("disabled");
			$("#allDaySalesYn").val("N");
		}
	}

	function FileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#mainImg").val(jsonData.fileContentIdx);
		$("#mainImgPath").attr("src",jsonData.thumbnailPath);
// 		<input type="hidden" id="fileIdx" value="' + jsonData.idx + '">');
	}

	function FileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/InfoDetail/",
			width: 400,
			height: 200,
			title: '대표 이미지',
			ext: ext,
			Finished: FileUploadFinishedEH
		});
	}




</script>