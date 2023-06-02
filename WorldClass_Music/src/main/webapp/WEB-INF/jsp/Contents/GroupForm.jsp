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
<form:form commandName="uploadVo" name="groupform" id="groupform" method="post">
<form:hidden path="groupIdx"/>
<form:hidden path="GROUP_DEPTH"/>
	<div class="modal-body">
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="parentName" class="control-label">분류 </label>
			    <input type="text" id="parentName" name="parentName" class="form-control" readonly>
		    </div>
		</div>
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="groupName" class="control-label">폴더명<font class="fontSet">[필수]</font></label>
				<form:input path="groupName" class="form-control" maxlength="50"/>
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
		if($('#contGroupIdx').val() > 0){
			$('#parentName').val($('#'+$('#contDomainIdx').val()+'_anchor').text() + ' > ' +$('#'+$('#contDomainIdx').val()+'_'+$('#contGroupIdx').val()+'_anchor').text());
			$('#GROUP_DEPTH').val($('#'+$('#contDomainIdx').val()+'_'+$('#contGroupIdx').val())[0].attributes.depth.value - 1);
		}
		else {
			$('#parentName').val($('#'+$('#contDomainIdx').val()+'_anchor').text());
			$('#GROUP_DEPTH').val('0');
		}
		$('#procBtn, #groupName').removeAttr('disabled');
		btnDisabled();
	});

	//기본 정보 저장
	function FormSave(){
		if (!$.formCheck('#groupform #groupName', 'Y', 50, 'N', '폴더명'))
			return;

		var param = {
				DOMAIN_IDX: $('#contDomainIdx').val(),
				PARENT_GROUP_IDX: $('#contGroupIdx').val(),
				GROUP_NAME: $('#groupform #groupName').val(),
				GROUP_IDX: $('#groupform #groupIdx').val(),
				GROUP_DEPTH: $('#GROUP_DEPTH').val()
		};
		var groupIdx = $('#groupform #groupIdx').val();
		var msgCode = groupIdx == "0" ? 'C' : 'U';
		var callBack = function (result) {
			if (result) {
				var dResult = '';
				if(groupIdx == '0'){
					dResult = $.ajaxUtil.ajaxDefault('<c:url value="/Contents/CreateGroup"/>', param);
				}
				else {
					dResult = $.ajaxUtil.ajaxDefault('<c:url value="/Contents/UpdateGroup"/>', param);
				}

				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'폴더');
					contentTreeSearchList(1, false, null, 'CONTENT');
					if($('#contGroupIdx').val() == '0'){
						$('#'+$('#contDomainIdx').val()+'_anchor').click();
					}
					else {
						$('#'+$('#contDomainIdx').val()+'_'+$('#contGroupIdx').val()+'_anchor').click();
					}
			   }
			}
		}
		commonSave(msgCode, callBack);
	}

</script>