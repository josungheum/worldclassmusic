<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="franchiseeVo" name="kioskform" id="kioskform" method="post">
	<form:hidden path="deviceIdx" />
	<form:hidden path="domainIdx" />
	<form:hidden path="brandIdx" />
	<form:hidden path="francIdx" />
	<div class="modal-body">
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="brandDeviceTypeList" class="control-label">단말 종류 <font class="fontSet">[필수]</font></label>
			    <select id="deviceType" class="form-control">
			    	<option value="0" >선택 하세요</option>
			    	<c:forEach items="${brandDeviceTypeList}" var="list">
						<option value="${list.deviceType}" <c:if test="${list.deviceType eq franchiseeVo.deviceType}">selected</c:if>><c:out value="${list.deviceTypeName}" /></option>
					</c:forEach>
			    </select>
			</div>
		</div>
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="deviceCode" class="control-label">단말 코드 <font class="fontSet">[필수]</font></label>
		    	<form:input path="deviceCode" class="form-control clsNotKor" maxlength="30" placeholder="단말 코드"/>
			</div>

		</div>
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="deviceName" class="control-label">단말 명 <font class="fontSet">[필수]</font></label>
				<form:input path="deviceName" class="form-control" maxlength="50" placeholder="단말 명"/>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="deviceResTypeList" class="control-label">단말 해상도</label>
			    <select id="deviceResType" class="form-control">
			    	<option value="0" >선택 하세요</option>
			    	<c:forEach items="${deviceResTypeList}" var="list">
						<option value="${list.codeValue}" <c:if test="${list.codeValue eq franchiseeVo.deviceResType}">selected</c:if>><c:out value="${list.codeName}" /></option>
					</c:forEach>
			    </select>
			</div>
		</div>
		<!-- div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="deviceSiteTypeList" class="control-label">매장 타입</label>
			    <select id="deviceSiteType" class="form-control">
			    	<option value="0" >선택 하세요</option>
			    	<c:forEach items="${deviceSiteTypeList}" var="list">
						<option value="${list.codeValue}" <c:if test="${list.codeValue eq franchiseeVo.deviceSiteType}">selected</c:if>><c:out value="${list.codeName}" /></option>
					</c:forEach>
			    </select>
			</div>
		</div-->
		<%-- <div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="deviceTransTypeList" class="control-label">이미지 전환 효과</label>
			    <select id="deviceTransType" class="form-control">
			    	<option value="0" >선택 하세요</option>
			    	<c:forEach items="${deviceTranTypeList}" var="list">
						<option value="${list.codeValue}" <c:if test="${list.codeValue eq franchiseeVo.deviceTransType}">selected</c:if>><c:out value="${list.codeName}" /></option>
					</c:forEach>
			    </select>
			</div>
		</div> --%>
		<div class="form-group row" id="colCountTypeDiv">
			<div class="col-xs-8">
				<label for="colCountType" class="control-label">열 갯수 <font class="fontSet">[필수]</font></label>
				<select id="colCountType" class="form-control">
			    	<option value="0" >선택 하세요</option>
			    	<c:forEach items="${colCountTypeList}" var="list">
						<option value="<c:out value="${list.codeValue}" />" <c:if test="${list.codeValue eq franchiseeVo.colCountType}">selected</c:if>><c:out value="${list.codeName}" /></option>
					</c:forEach>
			    </select>
			</div>
		</div>
		<div>
			<label>활성화여부</label>
			<div class="col-input-area">
				<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="activeYn" value="Y" data-reverse checked>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="kioskSave()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>
</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('#activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		$('#activeYn').prop('checked', "${franchiseeVo.activeYn}" == "N" ? false : true);


		if($("#deviceIdx").val() != "0")
			$("#deviceCode").attr("readonly",true);
		
		// 디바이스 타입 변경 시 처리
		$('#deviceType').change(function(){
			var selVal = $("#deviceType option:selected").val();
			// 단말타입이 스탠드 형 , 테이블 형일 경우 열갯수 추가 할수 있는 필드 노출
			if(selVal == 'DVC0001' || selVal == 'DVC0002'){
				$('#colCountTypeDiv').show();
			}else{
				$('#colCountType').val("0")
				$('#colCountTypeDiv').hide();
			}
		});


		var selVal = $("#deviceType option:selected").val();
		// 단말타입이 스탠드 형 , 테이블 형일 경우 열갯수 추가 할수 있는 필드 노출
		if(selVal == 'DVC0001' || selVal == 'DVC0002'){
			$('#colCountTypeDiv').show();
		}else{
			$('#colCountType').val("0")
			$('#colCountTypeDiv').hide();
		}

	});

	function kioskSave() {
		 var scValue = '';

		 if (!$.formCheck('#kioskform #deviceCode', 'Y', 30, 'N', '단말 코드')) return;
		 if (!$.formCheck('#kioskform #deviceName', 'Y', 50, 'N', '단말 명')) return;
		 
// 		 console.log($('#deviceType').val());
// 		 console.log($('#colCountType').val());
		 if($('#deviceType').val() == 0){
			 $.modalCommon.alertView('단말 종류를 선택해주세요.');
			 return false;
		 } 
		 if($('#deviceResType').val() == 0){
			 $.modalCommon.alertView('단말 해상도를 선택해주세요.');
			 return false;
		 } 
		 if($('#deviceSiteType').val() == 0){
			 $.modalCommon.alertView('매장 타입 선택해주세요.');
			 return false;
		 } 
		/*  if($('#deviceTransType').val() == 0){
			 $.modalCommon.alertView('이미지 전환 효과를 선택해주세요.');
			 return false;
		 }  */
		 
		 if($('#colCountType').val() == 0 && ($("#colCountTypeDiv").attr("style") == "" || $("#colCountTypeDiv").attr("style") == null)){
			 $.modalCommon.alertView('열 갯수를 선택해주세요.');
			 return false;
		 } 

		 var result;

		 var param = {
				 	domainIdx: $('#domainIdx').val(),
					brandIdx: $('#brandIdx').val(),
					francIdx: $('#francIdx').val(),
					deviceName: $('#kioskform #deviceName').val(),
					deviceCode: $('#kioskform #deviceCode').val(),
					deviceType: $('#kioskform #deviceType').val(),
					deviceResType: $('#kioskform #deviceResType').val(),
					//deviceSiteType: $('#kioskform #deviceSiteType').val(), //매장타입 보이지 않도록 처리
					deviceSiteType: '',
					/* deviceTransType: $('#kioskform #deviceTransType').val(), */
					colCountType: $('#kioskform #colCountType').val(),
					activeYn: $('#activeYn').prop('checked') ? "Y":"N",
					deviceIdx: $('#kioskform #deviceIdx').val()
				 };

		 var msgCode = $('#kioskform #deviceIdx').val() == '0' ?'C':'U';
		 var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/FranchiseeManagement/KioskCreate"/>', param);

				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'단말');
					kioskList();
			   }
			}
		}
		commonSave(msgCode, callBack);
	}
</script>