<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="brandVo" name="frm" id="frm" method="post">
<form:hidden path="logoFile" id="logoFile"/>
<form:hidden path="brandImg" id="brandImg"/>
<style>
	.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td{
		padding: 7px;
		vertical-align: middle;
	}
	.col-md-12, .col-sm-12, .col-xs-12, .col-lg-12 table tr td label{
		vertical-align: middle;
	}
</style>

<div class="box-body">
	<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 form-inline ">
		<table>
			<tr>
				<td style="width: 150px">
					<label class="lb" style="margin:0px;">서비스명 <font class="fontSet">[필수]</font> </label>
				</td>
				<td style="width: 700px">
					<form:input path="name" class="form-control" maxlength="50" placeholder="서비스명"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lb " style="margin:0px;">서비스 코드 <font class="fontSet">[필수]</font></label>
				</td>
				<td>
					<form:input path="brandCode" class="form-control clsNotKor" maxlength="20" placeholder="서비스 코드"/>
				</td>
			</tr>
			<!-- <tr>
				<td>
					<label style="margin:0px;">서비스 로고</label>
				</td>
				<td>
					<img id="logoFilePath" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="brandUpFileAdd();"/>
				</td>
			</tr> -->
			<tr>
				<td>
					<label style="margin:0px;">서비스 이미지(250x90)</label>
				</td>
				<td>
					<img id="barndImgPath" src="/Content/images/empty.gif" style="cursor: pointer;" onclick="brandImgUpFileAdd();"/>
				</td>
			</tr>
			<tr>
				<td>
					<label style="margin:0px;">단말 종류</label>
				</td>
				<td>
					<c:forEach items="${brandDeviceType}" var="list">
						<label style="font-weight:normal; margin-right: 15px;">
							<input class="optiongroup" name="brandDeviceType" type="checkbox" value="<c:out value="${list.codeValue}" />" style="margin-right: 3px;"/><c:out value="${list.codeName}" />
						</label>
					</c:forEach>
				</td>
			</tr>
			<%-- <tr>
				<td>
					<label style="margin:0px;">결제 종류</label>
				</td>
				<td>
					<c:forEach items="${brandPaymentType01}" var="list">
						<label style="font-weight:normal; margin-right: 15px;">
							<input class="optiongroup" name="brandPaymentType01" type="checkbox" value="<c:out value="${list.codeValue}" />" style="margin-right: 3px;"/><c:out value="${list.codeName}" />
						</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>
					<label style="margin:0px;">포인트 종류</label>
				</td>
				<td>
					<c:forEach items="${brandPaymentType02}" var="list">
						<label style="font-weight:normal;  margin-right: 15px;">
							<input class="optiongroup" name="brandPaymentType02" type="checkbox" value="<c:out value="${list.codeValue}" />"  style="margin-right: 3px;"/><c:out value="${list.codeName}" />
						</label>
					</c:forEach>

				</td>
			</tr> --%>
			<%-- <tr>
				<td>
					<label style="margin:0px;">가맹점 수</label>
				</td>
				<td><c:out value="${brandVo.orderSheetType}" /></td>
			</tr> --%>
			<tr>
				<td>
					<label style="margin:0px;">생성일</label>
				</td>
				<td>${brandVo.regDate}</td>
			</tr>
			<tr>
				<td>
					<label style="margin:0px;">수정일</label>
				</td>
				<td>
					${brandVo.modDate}
				</td>
			</tr>
			<tr>
				<td>
					<label style="margin:0px;">활성화 여부</label>
				</td>
				<td>
					<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="activeYn" value="Y" data-reverse checked>
				</td>
			</tr>
		</table>
	</div>

</div>
<div class="box-footer" id="inputBtn">
	<div class="pull-right">
		<button type="button" class="btn btn-primary2" data-toggle="modal" id="saveBtn" onclick="basicAttrSave();"><i class="glyphicon glyphicon-saved mr-5"></i>저장</button>
	</div>
</div>
</form:form>
<script>
	$(document).ready(function () {

		//썸네일
		if('<c:out value="${brandVo.thumbnailPath}" />' != ""){
			$("#logoFilePath").attr("src",'<c:out value="${brandVo.thumbnailPath}" />');
		}

		// 서비스이미지 파일 썸네일
		if('<c:out value="${brandVo.brandThumbnailPath}" />' != "")
			$("#barndImgPath").attr("src",'<c:out value="${brandVo.brandThumbnailPath}" />');

		//활성화여부
		$('#activeYn').prop('checked', "${brandVo.activeYn}" == "N" ? false : true);
		$('#activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});

		//결제, 포인트 종류
		if('${brandPaymentTypeList}' != "")
		{
			var brandPaymentTypeArr = JSON.parse('${brandPaymentTypeList}');
			
			for(var i = 0; i < brandPaymentTypeArr.length; i++)
			{
				//결제
				if(brandPaymentTypeArr[i].payTypeGroup == "PAY0000")
					$("input[name=brandPaymentType01][value=" + brandPaymentTypeArr[i].payType + "]").attr("checked", true);
				//포인트
				if(brandPaymentTypeArr[i].payTypeGroup == "PNT0000")
					$("input[name=brandPaymentType02][value=" + brandPaymentTypeArr[i].payType + "]").attr("checked", true);
			}
		}

		//디바이스 종류
		if('${brandDeviceTypeList}' != "")
		{
			var brandDeviceTypeArr = JSON.parse('${brandDeviceTypeList}');

			for(var i = 0; i < brandDeviceTypeArr.length; i++){
				$("input[name=brandDeviceType][value=" + brandDeviceTypeArr[i].deviceType + "]").attr("checked", true);
			}
		}

	});

	//기본 정보 저장
	function basicAttrSave(){
		if (!$.formCheck('#frm #name', 'Y', 50, 'N', '서비스명'))
			return;
		if (!$.formCheck('#frm #brandCode', 'Y', 20, 'N', '서비스 코드'))
			return;

		var barndPaymentType01Val = "";
		$("[name='brandPaymentType01']:checked").each(function ()
		{
			barndPaymentType01Val += $(this).val() + ",";
		});

		var barndPaymentType02Val = "";
		$("[name='brandPaymentType02']:checked").each(function ()
		{
			barndPaymentType02Val += $(this).val() + ",";
		});

		var barndDeviceTypeVal = "";
		$("[name='brandDeviceType']:checked").each(function ()
		{
			barndDeviceTypeVal += $(this).val() + ",";
		});

		/* barndPaymentType01Val = barndPaymentType01Val.substr(0, barndPaymentType01Val.length -1);
		barndPaymentType02Val = barndPaymentType02Val.substr(0, barndPaymentType02Val.length -1);; */
		barndDeviceTypeVal = barndDeviceTypeVal.substr(0, barndDeviceTypeVal.length -1);

		var param = {
				domainIdx: $('#domainIdx').val(),
				brandIdx: $('#brandIdx').val(),
				brandCode: $('#frm #brandCode').val(),
				name: $('#frm #name').val(),
				logoFile: $('#logoFile').val(),
				brandImg: $('#brandImg').val(),
				activeYn: $("#frm #activeYn:checked").val() == "Y" ? "Y":"N",
				brandDeviceType: barndDeviceTypeVal
				/* brandPaymentType01: barndPaymentType01Val,
				brandPaymentType02: barndPaymentType02Val,
				 */
		};

		var msgCode = 'U';

		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/BrandManagement/Update"/>', param);
				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'서비스 기본속성');
					treeSearchList(1, false, null, 'STO01');
					$('#' + $("#domainIdx").val() + "_" + $("#brandIdx").val() + '_anchor').click();

					if('${sessionScope.adminType}' != null && '${sessionScope.adminType}' != '' && '${sessionScope.adminType}' != 'ADM0001'){
						 var reloadTime = new Date();
						$("#brandMainImgPath").attr("src",'${sessionScope.brandMainImgPath}'+ '?' + reloadTime.getTime());
					}
			    }
			}
		}

		commonSave(msgCode, callBack);

	}

	function brandUpFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Brand/",
			width: 400,
			height: 200,
			title: '서비스 로고',
			ext: ext,
			Finished: brandUpFileUploadFinishedEH
		});
	}

	function brandUpFileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#logoFile").val(jsonData.fileContentIdx);
		$("#logoFilePath").attr("src",jsonData.thumbnailPath);
	}

	function brandImgUpFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG'];

		$.showUploadBox({
			url: "/Upload/View/Brand/",
			width: 250,
			height: 90,
			title: '서비스 이미지',
			ext: ext,
			Finished: brandImgUpFileUpload
		});
	}


	function brandImgUpFileUpload(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		$("#brandImg").val(jsonData.fileContentIdx);
		$("#barndImgPath").attr("src",jsonData.thumbnailPath);
	}

</script>