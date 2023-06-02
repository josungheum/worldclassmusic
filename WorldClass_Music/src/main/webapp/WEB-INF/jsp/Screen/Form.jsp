<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form:form commandName="ScreenVo" name="frm" id="frm" method="post">
<form:hidden path="domainIdx"/>
<form:hidden path="francIdx"/>
<form:hidden path="brandIdx"/>
<form:hidden path="screenIdx"/>
	<div class="modal-body">
		<div class="contentEditor" id="contentEditor">
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="loadTemplate()" id="loadBtn">템플릿 불러오기</button>
		<button type="button" class="btn btn-primary" onclick="Save()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>
</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.modal-dialog').css('width', '1300px');

		$('.contentEditor').ContentEditor({
			resolutionW : 1080,
			resolutionH : 1920,
			rows : 4,
			cols : 2,
			headerH : 50,
			url : '<c:url value="/Screen/ContentForm"/>'
		});


		if ($('#screenIdx').val() != 0) {
			Load();
			//$('#loadBtn').prop('disabled', true);
			$('#loadBtn').hide();
		}

		saveDisabled();
	});

	//템플릿 불러오기
	function loadTemplate() {
		var domainIdx = $("#domainIdx").val();
		var brandIdx = $("#brandIdx").val();
		var francIdx = $("#francIdx").val();

		var param = {domainIdx : domainIdx, brandIdx : brandIdx, francIdx : francIdx, templateIdx : 0};
		$.modalCommon.loadFullDataView('템플릿 불러오기', '<c:url value="/Screen/TemplateAddForm"/>', param);
	}
</script>