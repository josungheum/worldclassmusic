<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.label-div {
	   	width: 170px;
	   	display: inline-block;
	}
</style>
<form:form commandName="optionProdVo" name="frm" id="frm" method="post">
	<form:hidden path="brandIdx"/>
	<form:hidden path="francIdx"/>
	<form:hidden path="optionProdIdx"/>
	<div class="modal-body" style="height:380px;">
		<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
			<div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="optionProdCode">옵션 상품 코드 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="optionProdCode" style="ime-mode:active; width:200px;" class="form-control clsNotKor" maxlength="30" placeholder="옵션 상품 코드" />
		    </div>
		    <br>
			<div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="optionProdName">옵션 상품 명 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="optionProdName" style="ime-mode:active; width:200px;" class="form-control" maxlength="50" placeholder="옵션 상품 명" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="optionProdDisplayName">옵션 상품 표시명 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="optionProdDisplayName" style="ime-mode:active; width:200px;" class="form-control" maxlength="50" placeholder="옵션 상품 표시명" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="" for="optionProdEnName">옵션 상품 영문명 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="optionProdEnName" style="ime-mode:active; width:200px;" class="form-control" maxlength="50" placeholder="옵션 상품 영문명" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="optionProdAmount">옵션 상품 금액 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="optionProdAmount" style="width:200px;" class="form-control autoComma" maxlength="9" placeholder="" />
		    </div>
		    <br>
		   
		    
			
			<div class="form-inline">
				<div class="label-div">
		        	<label class="" for="activeYn">상품 활성화 여부</label>
		        </div>
		        <input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="activeYn" value="Y" data-reverse checked>
			</div>
		</div>
		
		
	</div><br/>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="FormSave()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>

</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		$('#activeYn').prop('checked', "${optionProdVo.activeYn}" == "N" ? false : true);
		
	});
	
	//기본 정보 저장
	function FormSave(){
		
		if($('#optionProdAmount').val() != "")
			$('#optionProdAmount').val($.number($('#optionProdAmount').val()));	
	
		
		
		if (!$.formCheck('#optionProdName', 'Y', 50, 'Y', '옵션 상품 명'))
			return;
		if (!$.formCheck('#optionProdDisplayName', 'Y', 50, 'Y', '옵션 상품 표시 명'))
			return;
		if (!$.formCheck('#optionProdEnName', 'Y', 50, 'Y', '옵션 상품 영문 명'))
			return;
		if (!$.formCommaCheck('#optionProdAmount', 'Y', 7, '옵션 상품금액'))
			return;
		
		
		var param = {
				brandIdx : $("#frm #brandIdx").val(),
				francIdx : $("#frm #francIdx").val(),
				optionProdIdx : $("#frm #optionProdIdx").val(),
				optionProdName: $('#frm #optionProdName').val(),
				optionProdDisplayName: $('#frm #optionProdDisplayName').val(),
				optionProdEnName: $('#frm #optionProdEnName').val(),
				optionProdAmount: uncomma($('#frm #optionProdAmount').val()),
				activeYn: $("#frm #activeYn:checked").val() == "Y" ? "Y":"N",
				optionProdCode: $("#frm #optionProdCode").val()
				
		};
		
		var msgCode = $("#frm #optionProdIdx").val() == '' ? 'C':'U';
		
		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/OptionProd/Insert"/>', param);
				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'상품');
					optionProdList();
			    }	
			}
		}
		commonSave(msgCode, callBack);
	}
	

	
</script>