<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="brandVo" name="baform" id="baform" method="post" class="">
	<input type="hidden" id="fileContentIdx" />
	<input type="hidden" id="fileContentIdx2" />
	<div class="modal-body">
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">서비스명 <font class="fontSet">[필수]</font></label>
		    	<input type="text" id="name" name="name" class="form-control" maxlength="50" placeholder="서비스명" required>
			</div>
		</div>
		<div class="form-group  row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="brandCode" class="control-label">서비스 코드 <font class="fontSet">[필수]</font></label>
			    <input type="text" id="brandCode" name="brandCode" class="form-control clsNotKor"  maxlength="20" placeholder="서비스 코드">
			</div>
		</div>
		<!-- <div class="form-group">
			<label for="logoFile">서비스 로고</label>
		    <img id="logoFile" name="logoFile" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="brandInsFilePopAdd();"/>
		</div> -->
		<div class="form-group">
			<label for="brandImg">서비스 대표 이미지</label>
		    <img id="brandImg" name="brandImg" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="brandMainImgAdd();"/>
		</div>
		<div>
			<label>활성화여부</label>
			<div class="col-input-area">
				<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="activeYn" value="Y" data-reverse checked>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="basicAttrFormCreate()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>
</form:form>
<script type="text/javaScript">
	$(document).ready(function() {
		$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
	});

	//기본 정보 저장
	function basicAttrFormCreate(){
		if (!$.formCheck('#baform #name', 'Y', 50, 'N', '서비스명'))
			return;
		if (!$.formCheck('#baform #brandCode', 'Y', 20, 'N', '서비스 코드'))
			return;

		var param = {
				domainIdx: $('#domainIdx').val(),
				brandCode: $('#baform #brandCode').val(),
				name: $('#baform #name').val(),
				logoFile: $('#baform #fileContentIdx').val(),
				brandImg: $('#baform #fileContentIdx2').val(),
				activeYn: $("#baform #activeYn:checked").val() == "Y" ? "Y":"N"
		};

		var msgCode = 'C';
		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/BrandManagement/Insert"/>', param);

				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'서비스');
					treeSearchList(1, false, null, 'STO01');
					$('#'+$('#domainIdx').val()+'_anchor').click();
			   }
			}
		}
		commonSave(msgCode, callBack);
	}

	function brandInsFilePopAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Brand/",
			width: 400,
			height: 200,
			title: '서비스 로고',
			ext: ext,
			Finished: brandInsPopFileUploadFinished
		});
	}

	function brandInsPopFileUploadFinished(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#baform #fileContentIdx").val(jsonData.fileContentIdx);
		$("#baform #logoFile").attr("src",jsonData.thumbnailPath);
	}


	function brandMainImgAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Brand/",
			width: 250,
			height: 90,
			title: '서비스 이미지',
			ext: ext,
			Finished: brandInsPopImgUploadFinished
		});
	}

	function brandInsPopImgUploadFinished(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#baform #fileContentIdx2").val(jsonData.fileContentIdx);
		$("#baform #brandImg").attr("src",jsonData.thumbnailPath);
	}
</script>