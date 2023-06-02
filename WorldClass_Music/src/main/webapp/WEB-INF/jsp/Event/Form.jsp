<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.mt15{
		margin-top : 15px;
	}
</style>
<form:form commandName="eventVo" name="frm" id="frm" method="post">
	<form:hidden path="domainIdx"/>
	<form:hidden path="brandIdx"/>
	<form:hidden path="eventIdx"/>
	<div class="modal-body">
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">이벤트 표시 명 </label><font class="fontSet">[필수]</font>
		        </div>
		        <div class="col-sm-9">
			        <form:input path="dispNm1" id="dispNm1" cssClass="form-control" cssStyle="ime-mode:active; margin-top:5px; display: inline;" maxlength="50" placeholder="이벤트 표시 명" />
			        <!-- form:input path="dispNm2" id="dispNm2" cssClass="form-control" cssStyle="ime-mode:active; margin-top:5px; display: inline;" maxlength="50" placeholder="2열" /-->
		        </div>
		    </div>
	    </div>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">썸네일 등록 </label>
		        </div>
		        <div class="col-sm-9">
		        	<form:hidden path="thumbImg" id="thumbImg"/>
		        	<c:if test="${empty eventVo.thumbnailPath}">
		        		<img id="thumbnailFile" name="thumbnailFile" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="thumbnailFileAdd()"/>
		        	</c:if>
		        	<c:if test="${not empty eventVo.thumbnailPath}">
		        		<img id="thumbnailFile" name="thumbnailFile" src="${eventVo.thumbnailPath}" style="cursor: pointer;" onclick="thumbnailFileAdd()"/>
		        	</c:if>
		        </div>
		    </div>
	    </div>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">표시기간<font class="fontSet">[필수]</font></label>
		        </div>
		        <div class="col-sm-9">
		        	<div class="col-input-area" style="width:49%; display: inline-block;">
						<div class="input-group date">
							<form:input path="startDate" class="form-control" style="background-color: #fff" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					</div>
					<div class="col-input-area" style="width:49%; display: inline-block;">
						<div class="input-group date" >
							<form:input path="endDate" class="form-control" style="background-color: #fff" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					</div>
		        </div>
		    </div>
	    </div>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">정렬순서</label>
		        </div>
		        <div class="col-sm-9">
		        	<form:input path="orderSeq" id="orderSeq" cssClass="form-control" cssStyle="ime-mode:active; margin-top:5px; display: inline;" placeholder="정렬순서" />
		        </div>
		    </div>
	    </div>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">상세 컨텐츠(가로형) </label>
		        </div>
		        <div class="col-sm-9">
		        	<%-- <form:hidden path="detailHoriImg" id="detailHoriImg"/>
		        	<c:if test="${empty eventVo.detailHoriImgPath}">
		        		<img id="detailHoriFile" name="detailHoriFile" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="detailHoriImgAdd()"/>
		        	</c:if>
		        	<c:if test="${not empty eventVo.detailHoriImgPath}">
		        		<img id="detailHoriFile" name="detailHoriFile" src="${eventVo.detailHoriImgPath}" style="cursor: pointer;" onclick="detailHoriImgAdd()"/>
		        	</c:if> --%>
		        	<div id="detailHori" style="display: inline-block;"></div>
		        </div>
		    </div>
	    </div>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15" style="margin-bottom : 10px;">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">상세 컨텐츠(세로형) </label>
		        </div>
		        <div class="col-sm-9">
		        	<%-- <form:hidden path="detailVertImg" id="detailVertImg"/>
		        	<c:if test="${empty eventVo.detailVertImgPath}">
		        		<img id="detailVertFile" name="detailVertFile" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="detailVertImgAdd()"/>
		        	</c:if>
		        	<c:if test="${not empty eventVo.detailVertImgPath}">
		        		<img id="detailVertFile" name="detailVertFile" src="${eventVo.detailVertImgPath}" style="cursor: pointer;" onclick="detailVertImgAdd()"/>
		        	</c:if> --%>
		        	<div id="detailVert" style="display: inline-block;"></div>
		        </div>
		    </div>
	    </div>
	</div>
	<br/>
	<div style="clear:both;"></div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="Save()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>

</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.modal-dialog').css('width', '900px');
		
		$.datePickerCommon.createDatePicker('startDate');
		$.datePickerCommon.createDatePicker('endDate');
		$('#startDate').attr('readOnly', true);
		$('#endDate').attr('readOnly', true);

		$("#orderSeq").on("keyup", function() {
		    $(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		var param = {
				domainIdx : $('#domainIdx').val(), brandIdx : $('#brandIdx').val(),
				eventIdx : $('#eventIdx').val()
		}
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/Event/ImageList"/>', param).data;
		
		$.fn.ImageEditor({
			title : null,
			id : 'detailHori',
			data : result[0].imageList,
			folder : 'Detail',
			oriExt : null
		});
		
		$.fn.ImageEditor({
			title : null,
			id : 'detailVert',
			data : result[1].imageList,
			folder : 'Detail',
			oriExt : null
		});
		
		$('#detailHori').css('max-width', '100%').css('width', '100%');
		$('#detailVert').css('max-width', '100%').css('width', '100%');
		$('.imageList').css('min-width', '95%');
	});
	
	
	function Save() {
		var dispNm1 = $('#dispNm1').val();
		//var dispNm2 = $('#dispNm2').val(); //2열은 필수값이 아니므로 disabled
		var dispNm2 = '';
		var thumbImg = $('#thumbImg').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var orderSeq = $('#orderSeq').val();
		
		if (!$.formCheck('#frm #dispNm1', 'Y', 50, 'N', '이벤트 표시명(1열)'))
			return;
		
		if (startDate == null || startDate == '') {
			$.modalCommon.alertView('시작 일을 확인해주세요.');
			return;
		}
		
		if (endDate == null || endDate == '') {
			$.modalCommon.alertView('종료 일을 확인해주세요.');
			return;
		}
		
		if (!startDate < endDate) {
			$.modalCommon.alertView('표시 기간을 확인해주세요.');
			return;
		}
		
		var horiXML;
		$('#detailHori').find('input[type="hidden"]').each(function(index, e) {
			horiXML += '<Hori><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '</Hori>';
		});
		
		var vertXML;
		$('#detailVert').find('input[type="hidden"]').each(function(index, e) {
			vertXML += '<Vert><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '</Vert>';
		});
		
		var param;
		var result;
		if ($('#frm #eventIdx').val() == 0) {
			param = {
					dispNm1 : dispNm1, dispNm2 : dispNm2, thumbImg : thumbImg, startDate : startDate, endDate : endDate,
					orderSeq : orderSeq, horiXML : horiXML, vertXML : vertXML,
					domainIdx : $('#domainIdx').val(), brandIdx : $('#brandIdx').val()
			};
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Event/Create"/>', param);
		}
		else {
			param = {
					dispNm1 : dispNm1, dispNm2 : dispNm2, thumbImg : thumbImg, startDate : startDate, endDate : endDate,
					orderSeq : orderSeq, horiXML : horiXML, vertXML : vertXML,
					domainIdx : $('#domainIdx').val(), brandIdx : $('#brandIdx').val(), eventIdx : $('#frm #eventIdx').val()
			};
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Event/Update"/>', param);
		}
		
		if (result.resultCode == 0) {
            $.modalCommon.close();
			$.modalCommon.alertView('처리 되었습니다.');
			List();
		}
	}
	
	
	
	function thumbnailFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Event/",
			width: 400,
			height: 200,
			title: '로고',
			ext: ext,
			Finished: thumbnailFileAddFinishedEH
		});
	}

	function thumbnailFileAddFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#thumbImg").val(jsonData.fileContentIdx);
		$("#thumbnailFile").attr("src",jsonData.thumbnailPath);
	}
	
	function detailHoriImgAdd(){
		var ext = ['PNG', 'JPEG', 'JPG', 'MP4', 'WMV', 'AVI', 'MPEG'];

		$.showUploadBox({
			url: "/Upload/View/Content/",
			width: 400,
			height: 200,
			title: '상세 컨텐츠(가로)',
			ext: ext,
			Finished: detailHoriImgAddFinishedEH
		});
	}

	function detailHoriImgAddFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#detailHoriImg").val(jsonData.fileContentIdx);
		$("#detailHoriFile").attr("src",jsonData.thumbnailPath);
	}
	
	function detailVertImgAdd(){
		var ext = ['PNG', 'JPEG', 'JPG', 'MP4', 'WMV', 'AVI', 'MPEG'];

		$.showUploadBox({
			url: "/Upload/View/Content/",
			width: 400,
			height: 200,
			title: '상세 컨텐츠(세로)',
			ext: ext,
			Finished: detailVertImgAddFinishedEH
		});
	}

	function detailVertImgAddFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#detailVertImg").val(jsonData.fileContentIdx);
		$("#detailVertFile").attr("src",jsonData.thumbnailPath);
	}
</script>