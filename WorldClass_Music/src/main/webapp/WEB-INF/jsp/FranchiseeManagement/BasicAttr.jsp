<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="franchiseeVo" name="frm" id="frm" method="post">
	<form:hidden path="domainIdx"/>
	<form:hidden path="brandIdx"/>
	<form:hidden path="francIdx"/>

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
	<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 form-inline">
		<table>
			<tr>
				<td style="width: 200px">
					<label style="margin:0px;">적용그룹</label>
				</td>
				<td style="width: 700px">
					<form:hidden path="groupIdx" id="groupIdx"/>
					<form:input path="groupName" class="form-control" maxlength="50" placeholder="적용그룹" readonly="true"/>
					<button type="button" class="btn btn-default notDisabled" onclick="FindGroup()">변경</button>
				</td>
			</tr>
			<tr>
				<td style="width: 200px">
					<label style="margin:0px;">사이트명 <font class="fontSet">[필수]</font></label>
				</td>
				<td style="width: 700px">
					<form:input path="francName" class="form-control" maxlength="50" placeholder="사이트명"/>
				</td>
			</tr>
			<tr>
				<td>
					<label style="margin:0px;">사이트 코드 <font class="fontSet">[필수]</font></label>
				</td>
				<td>
					<form:input path="francCode" class="form-control clsNotKor" maxlength="30" placeholder="사이트 코드"/>
				</td>
			</tr>
			<%-- <tr>
				<td>
					<label style="margin:0px;">사업자 가맹코드 <font class="fontSet">[필수]</font></label>
				</td>
				<td>
					<form:input path="sapMsCd" class="form-control clsNotKor" maxlength="30" placeholder="사업자 가맹코드"/>
				</td>
			</tr> --%>
			<tr style="display: none;">
				<td>
					<label style="margin:0px;">결제 종류</label>
				</td>
				<td>
					<c:forEach items="${francPaymentType01}" var="list">
						<label style="font-weight:normal; margin-right: 10px;">
							<input class="optiongroup" name="francPaymentType01" type="checkbox" value="<c:out value="${list.codeValue}" />" style="margin-right: 3px;"/><c:out value="${list.codeName}" />
						</label>
					</c:forEach>

				</td>
			</tr>
			<tr style="display: none;">
				<td>
					<label style="margin:0px;">포인트 종류</label>
				</td>
				<td>
					<c:forEach items="${francPaymentType02}" var="list">
						<label style="font-weight:normal; margin-right: 10px;">
							<input class="optiongroup" name="francPaymentType02" type="checkbox" value="<c:out value="${list.codeValue}" />" style="margin-right: 3px;"/><c:out value="${list.codeName}" />
						</label>
					</c:forEach>

				</td>
			</tr>
			<tr>
				<td>
					<label style="margin:0px;">생성일</label>
				</td>
				<td>
					${franchiseeVo.regDate}
				</td>
			</tr>
			<tr>
				<td>
					<label style="margin:0px;">수정일</label>
				</td>
				<td>
					${franchiseeVo.modDate}
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
	<div class="pull-right" id="btnGroup">
		<button type="button" class="btn btn-primary2" data-toggle="modal" id="saveBtn" onclick="basicAttrSave();"><i class="glyphicon glyphicon-saved mr-5"></i>저장</button>
	</div>
</div>
</form:form>
<script>
	$(document).ready(function () {
		//활성화여부
		$('#activeYn').prop('checked', "${franchiseeVo.activeYn}" == "N" ? false : true);

		$('#activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});

		//결제, 포인트 종류
		var francPaymentTypeArr = JSON.parse('${francPaymentTypeList}');
		for(var i = 0; i < francPaymentTypeArr.length; i++)
		{
			//결제
			if(francPaymentTypeArr[i].payTypeGroup == "PAY0000")
				$("input[name=francPaymentType01][value=" + francPaymentTypeArr[i].payType + "]").attr("checked", true);
			//포인트
			if(francPaymentTypeArr[i].payTypeGroup == "PNT0000")
				$("input[name=francPaymentType02][value=" + francPaymentTypeArr[i].payType + "]").attr("checked", true);
		}
	});



	//기본 정보 저장
	function basicAttrSave(){
		if (!$.formCheck('#frm #groupIdx', 'Y', 50, 'N', '적용그룹'))
			return;
		if (!$.formCheck('#frm #francName', 'Y', 50, 'N', '사이트명'))
			return;
		if (!$.formCheck('#frm #francCode', 'Y', 30, 'N', '사이트 코드'))
			return;
		/* if (!$.formCheck('#frm #sapMsCd', 'Y', 30, 'N', '사업자 가맹코드'))
			return; */

		var francPaymentType01Val = "";
		$("[name='francPaymentType01']:checked").each(function ()
		{
			francPaymentType01Val += $(this).val() + ",";
		});

		var francPaymentType02Val = "";
		$("[name='francPaymentType02']:checked").each(function ()
		{
			francPaymentType02Val += $(this).val() + ",";
		});

		francPaymentType01Val = francPaymentType01Val.substr(0, francPaymentType01Val.length -1);
		francPaymentType02Val = francPaymentType02Val.substr(0, francPaymentType02Val.length -1);
		
		var param = {
				domainIdx: $('#frm #domainIdx').val(),
				brandIdx: $('#frm #brandIdx').val(),
				groupIdx: $('#frm #groupIdx').val(),
				francIdx: $('#frm #francIdx').val(),
				francCode: $('#frm #francCode').val(),
				/* sapMsCd: $('#frm #sapMsCd').val(), */
				francName: $('#frm #francName').val(),
				activeYn: $("#frm #activeYn:checked").val() == "Y" ? "Y":"N",
			    francPaymentType01: francPaymentType01Val,
			    francPaymentType02: francPaymentType02Val
		};

		var msgCode = 'U';

		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/FranchiseeManagement/Update"/>', param);
				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'사이트 기본속성');
					treeSearchList(1, false, null, 'STO02');
					$('#' + $("#domainIdx").val() + "_" + $("#brandIdx").val() + "_" + $("#francIdx").val() + '_anchor').click();
			    }
			}
		}
		commonSave(msgCode, callBack);
	}

	function FindGroup(){
		BootstrapDialog.show({
	        title: '사이트 그룹',
	        draggable: true,
	        closable: true,
            closeByBackdrop:false,
	        message: function (dialog) {
	            var $message = $('<div></div>');
	            var pageToLoad = dialog.getData('pageToLoad');
	
	            $.sessionCheck();	            
	            $message.load('<c:url value="/FranchiseeManagement/GroupFind"/>');
	
	            return $message;
	        },
	        /* data: {
	            'pageToLoad': url
	        }, */
	        buttons: [{
	            label: '확인',
	            cssClass:'btn-primary',
	            action: function (dialog) {
            		var selectedTarget = $('#findTreeList').jstree('get_selected', true);
            		console.log(selectedTarget);
            		if (selectedTarget[0].li_attr.groupidx == 0) {
	        	    	$.modalCommon.alertView('사이트 그룹을 선택해주세요.');
	        	    	return;
	        		}
            		$('#frm #groupIdx').val(selectedTarget[0].li_attr.groupidx);
            		$('#frm #groupName').val(selectedTarget[0].text);
	        		
	            	dialog.close();
	            }
	        },
	        {
	            label: '취소',
	            cssClass:'btn-gray',
	            action: function (dialog) {
	                dialog.close();
	            }
	        }]
	    });
	}



</script>