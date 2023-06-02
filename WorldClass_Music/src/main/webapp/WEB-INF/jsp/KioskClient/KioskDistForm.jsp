<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="modal-body" >
	<form name="frm" id="frm" method="post" >
		<input type="hidden" id="kioskClientIdx" name="kioskClientIdx" value="${kioskClientVo.kioskClientIdx}">
		<textarea id="resultScreenList" style="display:none;"></textarea>
		<div style="float: left;width:49%; display:inline;">
			<div>
				<div class="col-title-area">
					<label>버전<font class="fontSet">[필수]</font></label>
				</div>
				<div style="float: left;width:79%;display:inline;padding-bottom: 5px;">
					<input type="text" class="form-control" id="version" name="version" value="<c:out value="${kioskClientVo.version}"/>" <c:if test="${kioskClientVo.version != null}">readonly=true</c:if> style="ime-mode:active" maxlength="20" placeholder="1.0.0.0"/>
				</div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<div class="col-title-area">
					<label>변경 내용</label>
				</div>
				<div class="col-input-area">
					<textarea id="changes" name="changes"  style="border: 1px solid #EAEAEA; width: 95%; height: 330px; overflow-y: scroll;"></textarea>
				</div>
			</div>



		</div>
		<div style="float: left;width:49%;display:inline;">
			<label>적용 단말 목록</label>
			<div style="height:405px; border-color:#d2d6de; border-style:solid; overflow: auto;" id="targetTreeList" ></div>
		</div>
		<br/>
		<div class="modal-footer" style="border-top-color: #ffffff; padding-top:420px;">
			<button type="button" id="SaveSchedule" class="btn btn-primary" onclick="kioskDistSave()" id="procBtn">배포</button>
			<button type="button" id="closeBtn" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
		</div>
	</form>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
		var param = {
				kioskClientIdx : $('#kioskClientIdx').val()
		}

		treeSearchList(0,'<c:url value="/KioskClient/SearchKioskTree"/>',param);
	});

	function kioskDistSave() {
		var arr = [];
		$('#closeBtn').attr('disabled', true);

		if(!$.formCheck('#changes', 'N', 300, 'N', '변경 내용')){
			$('#closeBtn').attr('disabled', false);
			return;
		}

		$.each($('#targetTreeList').jstree('get_selected', true), function ()
		{
			if(this.li_attr.deviceidx != null && this.li_attr.deviceidx != 0)
				arr.push(this.li_attr.idx);
	    });

		if(arr.length == 0){
			$('#closeBtn').attr('disabled', false);
			$.modalCommon.alertView('단말을 하나라도 선택해주세요.');
			return false;
		}

		var param = {checkboxArr: arr, kioskClientIdx:$("#kioskClientIdx").val(), changes:$("#changes").val()};
		var dResult = $.ajaxUtil.ajaxArray('<c:url value="/KioskClient/DistributeCreate"/>', param);

		if (dResult.resultCode == 0) {
			$.modalCommon.close();
			$.modalCommon.alertView('배포가 완료되었습니다.');
			$('#closeBtn').attr('disabled', false);
			clientList();
		}else{
			$('#closeBtn').attr('disabled', false);
			$.modalCommon.close();
			$.modalCommon.alertView('배포를 실패하였습니다.\n다시 배포해 주시기 바랍니다.');
		}

	}
</script>