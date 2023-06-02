<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
<!--
.radioStyle1 {padding: 0 40px 0 10px; font-weight: normal; }
-->
</style>
<form:form commandName="SystemCodeVo" name="frm" id="frm" method="post">
	<form:hidden path="codeIdx"   />
	<form:hidden path="codeLevel" id="codeLevel" />
	<c:choose>
	  <c:when test="${SystemCodeVo.codeLevel =='0'}">
	    <c:set var="height" value="400" />
	  </c:when>
	  <c:otherwise>
	    <c:set var="height" value="500" />
	  </c:otherwise>
	</c:choose>

	<div class="modal-body" style="height: ${height};">
	    <%-- 등록 구분 RADIO 추가
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="codeLevel" class="control-label">등록 구분 <font class="fontSet">[필수]</font></label>
				<div>
		    	    <form:radiobutton path="codeLevel" id="codeLevel1" value="1" /><label for="codeLevel1" class="radioStyle1" >코드</label>
		    	    <form:radiobutton path="codeLevel" id="codeLevel0" value="0" /><label for="codeLevel0" class="radioStyle1" >코드 분류</label>
		    	</div>
			</div>
		</div>
	    --%>
		<div class="form-group  row codeLevel1_show">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label>코드 분류 <font class="fontSet">[필수]</font></label>
				<c:if test="${SystemCodeVo.modYn ne null and SystemCodeVo.modYn eq 'N'}">
					<input type="hidden" id="codeGroup" name="codeGroup" value="<c:out value="${SystemCodeVo.codeGroup}"/>"/>
					<input type="text" value="<c:out value="${SystemCodeVo.groupName}"/>" readonly="true"  class="form-control" style="ime-mode:active; width: 95%; display: inline;"/>
				</c:if>
				<c:if test="${SystemCodeVo.modYn eq null or SystemCodeVo.modYn eq 'Y'}">
				    <form:select path="codeGroup" id="codeGroup" items="${groupList}" itemValue="codeValue" itemLabel="codeName" cssClass="form-control" cssStyle="ime-mode:active; width: 95%; display: inline;" />
				</c:if>
			</div>
		</div>
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="codeValue" class="control-label">코드 ID <font class="fontSet">[필수]</font></label>
				<form:input path="codeValue" id="codeValue" cssClass="form-control autoUpperNumber" cssStyle="ime-mode:active; width: 95%; display: inline;" maxlength="30" placeholder="코드ID" />
			</div>
		</div>
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="codeName" class="control-label">코드 명 <font class="fontSet">[필수]</font></label>
		    	<form:input path="codeName" class="form-control" style="ime-mode:active; width: 95%; display: inline;" maxlength="30" placeholder="코드 명" />
			</div>
		</div>
		<div class="form-group  row codeLevel1_show">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="orderSeq" class="control-label">정렬 순서 <font class="fontSet">[필수]</font></label>
				<div>
		    	    <form:input path="orderSeq" class="form-control autoNumber" style="ime-mode:active; width: 20%; display: inline;" maxlength="3" placeholder="정렬 순서" />
				</div>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="memo" class="control-label">코드 설명</label>
		    	<form:input path="memo" class="form-control" style="ime-mode:active; width: 95%; display: inline;" maxlength="30" placeholder="코드 설명" />
			</div>
		</div>
		<div>
			<label>활성화여부</label>
			<div class="col-input-area">
				<input type="checkbox" data-group-cls="btn-group-sm" id="activeYn" data-reverse checked><br />
			</div>
		</div>

	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="Save()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
	</div>
</form:form>

<script type="text/javascript">
	$(document).ready(function () {
		$('#frm #activeYn').checkboxpicker(	defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		$('#frm #activeYn').prop('checked', "${SystemCodeVo.activeYn}" == "N" ? false : true);
		<c:if test="${not empty SystemCodeVo.codeGroup}">
		$('#frm #codeGroup').val('<c:out value="${SystemCodeVo.codeGroup}" />');
		</c:if>
		<c:if test="${SystemCodeVo.codeIdx ne null and SystemCodeVo.modYn eq 'N'}">
		$('#frm #codeValue').attr('readonly', 'true');
		$('#frm #codeValue').removeClass('form-control').addClass('form-control');
		</c:if>

		<%-- 등록 구분 변경 시 폼 변경 --%>
		var codeLevel = $('#frm #codeLevel').val();
		<%-- 코드 분류 등록 시는                  코드 분류 ID,  코드 분류 명,         코드 분류 설명, 활성화여부 를 저장 --%>
		<%-- 코드 등록 시는        코드 분류, 코드ID,     코드 명,     정렬 순서, 코드 설명,    활성화여부 를 저장 --%>
		if(codeLevel == '0') {
			$('.codeLevel1_show').hide();
			$('#frm label[for=codeValue]' ).html('코드분류 ID <font class="fontSet">[필수]');
			$('#frm label[for=codeName]'  ).html('코드분류 명<font class="fontSet">[필수]');
			$('#frm label[for=memo]'      ).html('코드분류 설명');
			$('#frm input[name=codeValue]').attr('placeholder', '코드분류 ID');
			$('#frm input[name=codeName]' ).attr('placeholder', '코드분류 명');
			$('#frm input[name=memo]'     ).attr('placeholder', '코드분류 설명');
		} else {
			$('.codeLevel1_show').show();
			$('#frm label[for=codeValue]' ).html('코드 ID <font class="fontSet">[필수]');
			$('#frm label[for=codeName]'  ).html('코드 명<font class="fontSet">[필수]');
			$('#frm label[for=memo]'      ).html('코드 설명');
			$('#frm input[name=codeValue]').attr('placeholder', '코드 ID');
			$('#frm input[name=codeName]' ).attr('placeholder', '코드 명');
			$('#frm input[name=memo]'     ).attr('placeholder', '코드 설명');
		}
	});

	function Save() {
		var codeLevel 	= $('#frm #codeLevel').val();
		var codeIdx 	= $('#frm #codeIdx').val();
		var codeGroup	= $('#frm #codeGroup').val();
		var codeValue   = $('#frm #codeValue').val();
		var codeName	= $('#frm #codeName').val();
		var orderSeq	= $('#frm #orderSeq').val();
		var memo		= $('#frm #memo').val();
		var activeYn	= $('#activeYn').prop('checked') ? "Y" : "N";


		if(codeLevel == '0') {
			if (!$.formCheck('#frm #codeValue', 'Y', 30, 'N', '코드분류 ID')	||
				!$.formCheck('#frm #codeName',  'Y', 30, 'N', '코드분류 명') 	||
				!$.formCheck('#frm #memo',      'N', 30, 'N', '코드분류 설명')) {
				return;
			}
			codeGroup	= codeValue;

		} else {
			if (!$.formCheck('#frm #codeValue', 'Y', 30, 'N', '코드 ID')	||
				!$.formCheck('#frm #codeName',  'Y', 30, 'N', '코드 명') 	||
				!$.formCheck('#frm #memo',      'N', 30, 'N', '코드 설명')		||
				!$.formCheck('#frm #orderSeq',  'Y', 3,  'N', '정렬 순서')) {
				return;
			}
		}


		var param = {
			'codeLevel'	: codeLevel,
			'codeIdx' 	: codeIdx,
			'codeGroup'	: codeGroup,
			'codeValue'	: codeValue,
			'codeName' 	: codeName,
			'orderSeq'	: orderSeq,
			'memo' 		: memo,
			'activeYn' 	: activeYn
		};

		var result = $.ajaxUtil.ajaxDefault(
				'<c:url value="/SystemCode/SystemCodeCreate"/>', param);

		if (result.resultCode == 0) {
			$.modalCommon.close();
			commMessage($("#frm #codeIdx").val() == ''?'C':'U','공통코드');
			systemCodeList();
		}

	}
</script>
