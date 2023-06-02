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
<form:form commandName="uploadVo" name="form" id="form" method="post">
<form:hidden path="contType"/>
<div class="modal-body">
	<div class="form-group  row">
		<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
			<label for="fileName" class="control-label">컨텐츠 명 </label>
		    <input type="text" id="fileName" name="fileName" class="form-control" maxlength="50">
	    </div>
	</div>
	<div class="form-group  row">
		<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
			<label for="savePath" class="control-label">URL</label>
		    <input type="text" id="savePath" name="savePath" class="form-control" maxlength="255">
		</div>
	</div>
	<div class="form-group  row">
		<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
			<label for="playTime" class="control-label">재생시간(초)</label>
		    <input type="text" id="playTime" name="playTime" class="form-control" onKeyUp = "$.textBox(this, 'num', 'Y', 'Y');">
		</div>
	</div>
</div>
</form:form>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" onclick="FormSave()" id="procBtn">저장</button>
	<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
</div>
<script type="text/javaScript">
	$(document).ready(function() {
		$('#fileName, #savePath, #playTime, #procBtn').removeAttr('disabled');
		btnDisabled();
	});

	//기본 정보 저장
	function FormSave(){
		if (!$.formCheck('#form #fileName', 'Y', 50, 'N', '컨텐츠명'))
		if (!$.formCheck('#form #savePath', 'Y', 255, 'N', 'URL'))
		if (!$.formCheck('#form #playTime', 'Y', 50, 'N', '재생시간'))
			return;

		var param = {
				fileName: $('#form #fileName').val(),
				savePath: $('#form #savePath').val(),
				playTime: $('#form #playTime').val()+"000",
				groupIdx: $('#contGroupIdx').val(),
				contType: $('#contType').val()
		};
		var msgCode = 'C';
		var callBack = function (result) {
			if (result) {
				var dResult = '';
					dResult = $.ajaxUtil.ajaxDefault('<c:url value="/Contents/Create"/>', param);

				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'URL');
					var domainIdx = $('#contDomainIdx').val();
					var groupIdx = $('#contGroupIdx').val();
					ContentList(domainIdx, groupIdx);
			   }
			}
		}
		commonSave(msgCode, callBack);
	}

</script>