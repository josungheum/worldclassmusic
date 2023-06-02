<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td{
	padding: 7px;
	vertical-align: middle;
}
.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td label{
	vertical-align: middle;
}
</style>
<form:form commandName="FloorVo" name="floorForm" id="floorForm" method="post" class="">
	<input type="hidden" id="logoImgfileContentIdx" />
	<input type="hidden" id="mapImgfileContentIdx" />

	<div class="modal-body" style="height:750px;">
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">층 단축표시명 <font class="fontSet">[필수]</font></label>
				<form:input path="dispShortNm" cssClass="form-control" maxlength="5" placeholder="층 단축표시명" style="width : 130px;" />
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">층 표시명(한) <font class="fontSet">[필수]</font></label>
				<form:input path="dispNmKr" cssClass="form-control" maxlength="50" placeholder="층 표시명(한)" style="width : 500px;" />
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">층 표시명(영) <font class="fontSet">[필수]</font></label>
				<form:input path="dispNmEn" cssClass="form-control" maxlength="50" placeholder="층 표시명(영)" style="width : 500px;" />
			</div>
		</div>
		<!-- div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">층 표시명(중) <font class="fontSet">[필수]</font></label>
				<input type="text" id="dispNmCh" name="dispNmCh" class="form-control" maxlength="50" placeholder="층 표시명(중)" required>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">층 표시명(일) <font class="fontSet">[필수]</font></label>
				<input type="text" id="dispNmJp" name="dispNmJp" class="form-control" maxlength="50" placeholder="층 표시명(일)" required>
			</div>
		</div-->
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">정렬순서 <font class="fontSet">[필수]</font></label>
				<form:input path="orderSeq" class="form-control autoNumber" style="ime-mode:active; width:130px;" maxlength="3" placeholder="정렬 순서" />
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-8 col-sm-8 col-xs-8 col-lg-8">
				<label for="name" class="control-label">층 매장 타입 <font class="fontSet">[필수]</font></label>				
				<form:select path="codeName" id="codeValue" items="${brandTypeCodeList}" itemValue="codeValue" itemLabel="codeName" cssClass="form-control" cssStyle="ime-mode:active; width:250px;" />
			</div>
		</div>
		<!-- div class="form-group">
			<label for="logoFile">층 로고</label>
			<img id="logoUploadImg" name="logoImg" src="/Content/images/empty.gif" style="cursor:pointer;" onclick="addLogoImgFile();"/>
		</div-->
		<div class="form-group">
			<label for="floorImg">층 이미지</label>
			<img id="mapUploadImg" name="mapImg" src="/Content/images/empty.gif" style="cursor:pointer;" onclick="addMapImgFile();"/>
		</div>
		<!-- 맵 이미지도 썸네일로 보이도록 처리 -->
		<!-- div style="width:100%; height:200px; overflow:auto;">
			<img id="mapImgArea" name="mapImgArea" src=""/>
		</div-->
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="save()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal" id="closeModal">닫기</button>
	</div>
	<form:hidden path="domainIdx" />
	<form:hidden path="brandIdx" />
	<form:hidden path="floorIdx" />
	<form:hidden path="mapImg" />
	<form:hidden path="mapThumbnailPath" />
	<form:hidden path="mapImgPath" />
	<form:hidden path="logoImg" />
	<form:hidden path="logoThumbnailPath" />
	<form:hidden path="logoImgPath" />
	<form:hidden path="floorType" />
</form:form>

<script type="text/javascript">
	$(function() {
		$('.modal-dialog').css('width', '600px');
		//로고 이미지 - 숨김 처리
		/*var logoImgPath = $('#logoImgPath').val();
		var logoThumbnailPath = $('#logoThumbnailPath').val();

		if(logoThumbnailPath != null && logoThumbnailPath != "") {
			$("#logoUploadImg").attr("src" , logoThumbnailPath);
		}*/
		
		$("#logoImgfileContentIdx").val($('#logoImg').val());

		//층 이미지
		var mapImgPath = $('#mapImgPath').val();
		var mapThumbnailPath = $('#mapThumbnailPath').val();

		$("#mapImgfileContentIdx").val($('#mapImg').val());

		if(mapImgPath != null && mapImgPath != "") {
			//$("#mapImgArea").attr("src" , mapImgPath);
			//$("#mapUploadImg").attr("src" , mapThumbnailPath);
			$("#mapUploadImg").attr("src" , mapImgPath);
			$("#mapUploadImg").css({'width' : '430px' , 'height' : '344px'});
		}

		//매장 타입
		if($('#floorType').val() != null && $('#floorType').val() != '') {
			$("#codeValue").val($('#floorType').val());
		} else {
			$("#codeValue").val('DVS0000');
		}

		//$("#codeValue option:selected").val();
	});

	//층 정보 저장
	function save() {
		var brandIdx = $('#floorForm #brandIdx').val();
		var floorIdx = $('#floorForm #floorIdx').val();
		var dispShortNm = $('#floorForm #dispShortNm').val();
		var dispNmKr = $('#floorForm #dispNmKr').val();
		var dispNmEn = $('#floorForm #dispNmEn').val();
		var dispNmCh = '';
		var dispNmJp = '';
		//var dispNmCh = $('#floorForm #dispNmCh').val();	//추후 추가될 수도 있음
		//var dispNmJp = $('#floorForm #dispNmJp').val();
		var orderSeq = $('#floorForm #orderSeq').val();
		var logoImg = $('#floorForm #logoImgfileContentIdx').val();
		var mapImg = $('#floorForm #mapImgfileContentIdx').val();
		var floorType = $("#codeValue option:selected").val();
		
		if(floorType == null || floorType == "DVS0000") {
			$.modalCommon.alertView('층 매장타입은 필수 선택값 입니다.');
			$("#codeValue").focus();
			return false;
		}

		if (!$.formCheck('#floorForm #dispShortNm', 'Y', 30, 'N', '층 단축표시명') ||
				!$.formCheck('#floorForm #dispNmKr', 'Y', 50, 'N', '층 표시명(한)') ||
				!$.formCheck('#floorForm #dispNmEn', 'Y', 50, 'N', '층 표시명(영)') ||
				//!$.formCheck('#floorForm #dispNmCh', 'Y', 50, 'N', '층 표시명(중)') ||
				//!$.formCheck('#floorForm #dispNmJp', 'Y', 50, 'N', '층 표시명(일)') ||
				!$.formCheck('#floorForm #orderSeq', 'Y', 11, 'N', '정렬순서')) {
				return;
		}

		var param = {
			'brandIdx' 	: brandIdx,
			'floorIdx' 	: floorIdx,
			'dispShortNm': dispShortNm,
			'dispNmKr' 	: dispNmKr,
			'dispNmEn'	: dispNmEn,
			'dispNmCh'	: '',
			'dispNmJp' 	: '',
			'orderSeq'	: orderSeq,
			'logoImg'	: logoImg,
			'mapImg'	: mapImg,
			'floorType' : floorType
		};

		var result = $.ajaxUtil.ajaxDefault('<c:url value="/FloorManagement/FloorUpdate"/>', param);

		if (result.resultCode == 0) {
			$.modalCommon.close();
			commMessage($("#floorForm #floorIdx").val() == ''? 'C' : 'U' ,'층 정보');
			makeFloorListTable();
		}
	}

	//층 로고 이미지 업로드
	function addLogoImgFile(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Floor/",
			width: 400,
			height: 200,
			title: '층 로고 이미지',
			ext: ext,
			Finished: floorLogoFileUploadFinished
		});
	}

	function floorLogoFileUploadFinished(data) {
		var jsonData = (data.length > 0) ? data[0] : null;
		$("#logoImgfileContentIdx").val(jsonData.fileContentIdx);
		$("#logoUploadImg").attr("src" , jsonData.thumbnailPath);
	}

	//층 이미지 업로드
	function addMapImgFile(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Floor/",
			width: 400,
			height: 200,
			title: '층 이미지',
			ext: ext,
			Finished: floorMapImgFileUploadFinished
		});
	}

	function floorMapImgFileUploadFinished(data) {
		var jsonData = data.length > 0 ? data[0] : null;
		$("#mapImgfileContentIdx").val(jsonData.fileContentIdx);
		//$("#mapImgArea").attr("src" , jsonData.savePath);
		//$("#mapUploadImg").attr("src" , jsonData.thumbnailPath);
		$("#mapUploadImg").attr("src" , jsonData.savePath);
		$("#mapUploadImg").css({'width' : '430px' , 'height' : '344px'});
	}
</script>
