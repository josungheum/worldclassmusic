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
<form:form commandName="shopVo" name="frm" id="frm" method="post">
	<form:hidden path="domainIdx"/>
	<form:hidden path="brandIdx"/>
	<form:hidden path="shopIdx"/>
	<div class="modal-body">
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">브랜드 명 </label><font class="fontSet">[필수]</font>
		        </div>
		        <div class="col-sm-9">
			        (한)&nbsp;<form:input path="dispNmKr" id="dispNmKr" cssClass="form-control" cssStyle="ime-mode:active; margin-top:5px; width: 90%; display: inline;" maxlength="50" placeholder="한글(필수)" />
			        <br>
			        (영)&nbsp;<form:input path="dispNmEn" id="dispNmEn" cssClass="form-control" cssStyle="ime-mode:active; margin-top:5px; width: 90%; display: inline;" maxlength="50" placeholder="영어" />
			        <%-- <br>
			        (중)&nbsp;<form:input path="DISP_NM_CH" id="DISP_NM_CH" cssClass="form-control" cssStyle="ime-mode:active; margin-top:5px; width: 90%; display: inline;" maxlength="100" placeholder="중국어" />
			        <br>
			        (일)&nbsp;<form:input path="DISP_NM_JP" id="DISP_NM_JP" cssClass="form-control" cssStyle="ime-mode:active; margin-top:5px; width: 90%; display: inline;" maxlength="100" placeholder="일본어" /> --%>
		        </div>
		    </div>
	    </div>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">브랜드 로고 </label>
		        </div>
		        <div class="col-sm-9">
		        	<form:hidden path="logoImg" id="logoImg"/>
		        	<c:if test="${empty shopVo.logoThumbnailPath}">
		        		<img id="logoFile" name="logoFile" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="logoFileAdd()"/>
		        	</c:if>
		        	<c:if test="${not empty shopVo.logoThumbnailPath}">
		        		<img id="logoFile" name="logoFile" src="${shopVo.logoThumbnailPath}" style="cursor: pointer;" onclick="logoFileAdd()"/>
		        	</c:if>
		        </div>
		    </div>
	    </div>
	    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
	    	<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">전화번호</label>
		        </div>
		        <div class="col-sm-9">
		        	<form:input path="telNum" id="telNum" cssClass="form-control" cssStyle="ime-mode:active; width: 96%; display: inline;" maxlength="30" placeholder="000-000-0000" />
		        </div>
		    </div>
	    </div>
		<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
			<div class="form-group">
				<div class="label-div col-sm-3">
		        	<label class="lb">상세정보 노출 여부</label>
			    </div>
			    <div class="col-sm-9">
			        <form:hidden path="detailDispYn" id="detailDispYn" /><!-- onclick="DetailYN();" -->
			        <c:if test="${shopVo.detailDispYn eq 'Y'}">
			        	<input type="checkbox" data-group-cls="btn-group-sm" class="dispYn" onchange="DetailYN();" data-reverse checked>
			        </c:if>
			        <c:if test="${shopVo.detailDispYn ne 'Y'}">
			        	<input type="checkbox" data-group-cls="btn-group-sm" class="dispYn" onchange="DetailYN();" data-reverse>
			        </c:if>
			    </div>
		   	</div>
		</div>
		    <br>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15" id="DetailDisplay" style="border:1px solid #D5D5D5; height: 700px; display:block;">
			<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
		    	<div class="form-group">
					<div class="label-div col-sm-3">
			        	<label class="lb">브랜드 컨셉 텍스트</label>
			        </div>
			        <div class="col-sm-9">
			        	<form:textarea path="detailConceptTxt" maxlength="300" class="form-control" style="ime-mode:active; min-height:90px; resize:none; width:90%; margin-bottom:10px;" placeholder="컨셉 텍스트(한)"/>
			        	<form:textarea path="detailConceptTxtEn" maxlength="300" class="form-control" style="ime-mode:active; min-height:90px; resize:none; width:90%;" placeholder="컨셉 텍스트(영)"/>
			        </div>
			    </div>
		    </div>
		    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
		    	<div class="form-group">
					<div class="label-div col-sm-3">
			        	<label class="lb">브랜드 상세 이미지 </label>
			        </div>
			        <div class="col-sm-9">
			        	<form:hidden path="detailImg" id="detailImg"/>
			        	<c:if test="${empty shopVo.detailThumbnailPath}">
		        			<img id="detailFile" name="detailFile" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="detailFileAdd()"/>
			        	</c:if>
			        	<c:if test="${not empty shopVo.detailThumbnailPath}">
			        		<img id="detailFile" name="detailFile" src="${shopVo.detailThumbnailPath}" style="cursor: pointer;" onclick="detailFileAdd()"/>
			        	</c:if>
			        </div>
			    </div>
		    </div>
		    <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 mt15">
		    	<div class="form-group">
					<div class="label-div col-sm-3">
			        	<label class="lb">대표 메뉴 </label>
			        </div>
			        <div class="col-sm-9">
			        	<div id="DetailMenuList"></div>
			        </div>
			    </div>
		    </div>
		</div>
	<div style="clear:both;"></div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="Save()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>

</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.modal-dialog').css('width', '900px');
		
		var data = null;
		if($('#frm #shopIdx').val() != 0) {
			var param = {
					shopIdx : $('#frm #shopIdx').val(), domainIdx : $('#frm #domainIdx').val(), brandIdx : $('#frm #brandIdx').val()
			}
			data = $.ajaxUtil.ajaxDefault('<c:url value="/Shop/DetailList"/>', param).data;
		}
		
		$.fn.ImageEditor({
			title : null,
			id : 'DetailMenuList',
			data : data,
			folder : 'Shop',
			oriExt : ['jpg,jpeg,png'],
			imgTitle : true
		});
		
		if ($('#detailDispYn').val() != 'Y') {
			$('#detailDispYn').val('N');
			$('#DetailDisplay').css('display', 'none');
		}
		else {
			$('#detailDispYn').val('Y');
			$('#DetailDisplay').css('display', 'inline');
		}
		
		$('.dispYn').checkboxpicker(defaults = {offLabel: '비활성화', onLabel: '활성화'});
	});
	
	function Save() {
		var krNm = $('#dispNmKr').val();
		var enNm = $('#dispNmEn').val();
		var logoImg = $('#logoImg').val();
		var telNum = $('#telNum').val();
		var detailDispYn = $('#detailDispYn').val();
		var detailConceptTxt = $('#detailConceptTxt').val();
		var detailConceptTxtEn = $('#detailConceptTxtEn').val();
		var detailImg = $('#detailImg').val();
		var detailMenuXML;
		$('#DetailMenuList').find('input[type="hidden"]').each(function(index, e) {
			detailMenuXML += '<Detail><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '<title>'+$(this).parent().find('input[ot="ctTitle"]').val()+'</title>'
						 + '<titleEn>'+$(this).parent().find('input[ot="ctTitleEn"]').val()+'</titleEn>'
						 + '<desc>'+$(this).parent().find('textarea[ot="ctDetail"]').val()+'</desc>'
						 + '<descEn>'+$(this).parent().find('textarea[ot="ctDetailEn"]').val()+'</descEn>'
						 + '</Detail>';
		});
		
		if (!$.formCheck('#frm #dispNmKr', 'Y', 50, 'N', '한글명'))
			return;
		var param;
		var result;
		if ($('#frm #shopIdx').val() == 0) {
			param = {
					dispNmKr : krNm, dispNmEn : enNm, logoImg : logoImg, telNum : telNum, detailDispYn : detailDispYn,
					detailConceptTxt : detailConceptTxt, detailImg : detailImg, detailMenuXML : detailMenuXML, detailConceptTxtEn : detailConceptTxtEn,
					domainIdx : $('#domainIdx').val(), brandIdx : $('#brandIdx').val()
			};
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Shop/Create"/>', param);
		}
		else {
			param = {
					dispNmKr : krNm, dispNmEn : enNm, logoImg : logoImg, telNum : telNum, detailDispYn : detailDispYn,
					detailConceptTxt : detailConceptTxt, detailImg : detailImg, detailMenuXML : detailMenuXML, detailConceptTxtEn : detailConceptTxtEn,
					domainIdx : $('#domainIdx').val(), brandIdx : $('#brandIdx').val(), shopIdx : $('#frm #shopIdx').val()
			};
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Shop/Update"/>', param);
		}
		
		if (result.resultCode == 0) {
            $.modalCommon.close();
			$.modalCommon.alertView('처리 되었습니다.');
			List();
		}
	}
	
	function DetailYN() {
		var detailyn = $('#detailDispYn').val();
		
		if (detailyn != 'Y') {
			$('#checkYN').html('<i class="fa fa-check"></i>');
			$('#detailDispYn').val('Y');
			$('#DetailDisplay').css('display', 'inline');
		}
		else {
			$('#checkYN').html('');
			$('#detailDispYn').val('N');
			$('#DetailDisplay').css('display', 'none');
		}
	}
	
	function logoFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Logo/",
			width: 400,
			height: 200,
			title: '로고',
			ext: ext,
			Finished: logoFileUploadFinishedEH
		});
	}

	function logoFileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#logoImg").val(jsonData.fileContentIdx);
		$("#logoFile").attr("src",jsonData.thumbnailPath);
	}
	
	function detailFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Logo/",
			width: 400,
			height: 200,
			title: '로고',
			ext: ext,
			Finished: detailFileUploadFinishedEH
		});
	}

	function detailFileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#detailImg").val(jsonData.fileContentIdx);
		$("#detailFile").attr("src",jsonData.thumbnailPath);
	}
</script>