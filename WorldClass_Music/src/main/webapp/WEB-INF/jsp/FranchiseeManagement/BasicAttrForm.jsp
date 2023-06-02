<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td{
	vertical-align: middle;
}
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td label{
	vertical-align: middle;
}
</style>
<form:form commandName="franchiseeVo" name="francform" id="francform" method="post">
	<div class="modal-body">
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">사이트명  <font class="fontSet">[필수]</font></label>
		    	<input type="text" id="francName" name="francName" class="form-control" maxlength="50" placeholder="사이트명" required>
			</div>

		</div>
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="brandCode" class="control-label">사이트 코드 <font class="fontSet">[필수]</font></label>
			    <input type="text" id="francCode" name="francCode" class="form-control clsNotKor"  maxlength="30" placeholder="사이트 코드">
			</div>
		</div>
		<!-- <div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="brandCode" class="control-label">사업자 가맹코드 <font class="fontSet">[필수]</font></label>
			    <input type="text" id="sapMsCd" name="sapMsCd" class="form-control clsNotKor"  maxlength="30" placeholder="사업자 가맹코드">
			</div>
		</div> -->
		<div>
			<label>활성화여부</label>
			<div class="col-input-area">
				<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="activeYn" value="Y" data-reverse checked>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="FormSave()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>

</form:form>
<script type="text/javaScript">
	$(document).ready(function() {
		$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
	});

	//기본 정보 저장
	function FormSave(){
		if (!$.formCheck('#francform #francName', 'Y', 50, 'N', '사이트명'))
			return;
		if (!$.formCheck('#francform #francCode', 'Y', 30, 'N', '사이트 코드'))
			return;
		/* if (!$.formCheck('#francform #sapMsCd', 'Y', 30, 'N', '사업자 가맹코드'))
			return; */

		var param = {
				domainIdx: $('#domainIdx').val(),
				brandIdx: $('#brandIdx').val(),
				groupIdx: $('#groupIdx').val(),
				francCode: $('#francform #francCode').val(),
				/* sapMsCd: $('#francform #sapMsCd').val(), */
				francName: $('#francform #francName').val(),
				activeYn: $("#francform #activeYn:checked").val() == "Y" ? "Y":"N"
		};

		var msgCode = 'C';
		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/FranchiseeManagement/Insert"/>', param);

				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'사이트');
					treeSearchList(1, false, null, 'STO02');
					$('#'+$('#domainIdx').val()+'_'+$('#brandIdx').val()+'_'+$('#groupIdx').val()+'_anchor').click();
			   }
			}
		}
		commonSave(msgCode, callBack);
	}

</script>