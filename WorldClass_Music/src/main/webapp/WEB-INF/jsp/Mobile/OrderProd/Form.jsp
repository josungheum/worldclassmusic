<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="orderProdVo" name="frm" id="frm" method="post">
	<form:hidden path="brandIdx"/>
	<form:hidden path="francIdx"/>
	<form:hidden path="orderProdIdx"/>
	<form:hidden path="prodImage"/>
	<!-- 옵션추가 -->
<!-- 	<input type="hidden" id="disabledTable_optionProdTable" value="N" /> -->
	<textarea id="resultOptionProdList" style="display:none;">${orderProdVo.resultOptionProdList}</textarea>
	
	<div class="modal-body" style="height:750px;">
		<div class="col-md-6 col-sm-6 col-xs-6 col-lg-6">
			<div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="prodCode">상품 코드 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="prodCode" style="ime-mode:active; width:300px;" class="form-control clsNotKor" maxlength="30" placeholder="상품 코드" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="prodName">상품 명 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="prodName" style="ime-mode:active; width:300px;" class="form-control" maxlength="50" placeholder="상품 명" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="prodDisplayName">상품 표시명 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="prodDisplayName" style="ime-mode:active; width:300px;" class="form-control" maxlength="50" placeholder="상품 표시명" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="" for="prodEnName">상품 영문명 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="prodEnName" style="ime-mode:active; width:300px;" class="form-control" maxlength="50" placeholder="상품 영문명" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="prodAmount">상품금액 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="prodAmount" class="form-control autoComma" maxlength="9" placeholder="" />
		    </div>
		    <br>
		    <div class="form-inline">
				<div class="label-div">
		        	<label class="lb " for="prodDiscountAmount">할인금액 <font class="fontSet">[필수]</font></label>
		        </div>
		        <form:input path="prodDiscountAmount" class="form-control autoComma" maxlength="9" placeholder="" />
		    </div>
		    <br>
		    <div class="form-inline">
		    	<div class="label-div">
		        	<label for="logoFile">상품 이미지<font class="fontSet">[필수]</font>(430x430)</label>
		        </div>
				<img id="prodImageUrl" name="prodImageUrl" src="/Content/images/empty2.jpg" style="cursor: pointer;width: 230px;height: 230px;" onclick="FilePopAdd();"/>
			</div>
			<br>
			<div class="form-inline">
				<div class="label-div">
		        	<label class="lb" for="weight">무게</label>
		        </div>
		        <form:input path="weight" class="form-control autoComma" maxlength="9" placeholder="" />
		    </div>
		    <br>
			<div class="form-inline">
				<div class="label-div">
		        	<label class="lb" for="prodBarcode">바코드</label>
		        </div>
		        <form:input path="prodBarcode" class="form-control autoNumber" maxlength="15" placeholder="" />
		    </div>
		    <br>
		    
			<div class="form-inline">
				<div class="label-div">
		        	<label class="" for="eventProdYn">행사상품 여부</label>
		        </div>
		       		<c:if test="${orderProdVo.eventProdYn eq 'Y'}">
		        		<a is="0" disabled class="btn active btn-success">예</a>
		        	</c:if>
		        	<c:if test="${orderProdVo.eventProdYn ne 'Y'}">
		        		<a is="0" disabled class="btn active btn-danger">아니오</a>
		        	</c:if>
		        <form:hidden path="eventProdYn"/>
		        <c:if test="${orderProdVo.eventProdYn eq 'Y'}">
		        	<form:input path="eventProdScript" style="ime-mode:active; width:200px;" class="form-control" maxlength="200" placeholder="행사상품 계산 스크립트 작성" />
		        </c:if>
		        
			</div>
			<br>
			<div class="form-inline">
				<div class="label-div">
		        	<label class="" for="activeYn">상품 활성화 여부</label>
		        </div>
		        <input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="activeYn" value="Y" data-reverse checked>
			</div>
			<div class="form-inline" >
				<div class="label-div">
					<label>옵션 상품</label>
<!-- 					<font class="fontSet">[필수]</font> -->
				</div>
				<div id="DataTable" style="border:1px; border-color:#d2d6de; border-bottom-style:solid; min-height:265.8px; "> </div>
			</div>
			<br>
			<button type="button" class="btn btn-primary" onclick="FormSave()" id="procBtn">저장</button>
			<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
		</div>

		
		
		
	</div>
	<div class="modal-footer">
		
	</div>

</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		$('.modal-dialog').css('width', '600px').css('height', '1200px');;
		
		
		$('#activeYn').prop('checked', "${orderProdVo.activeYn}" == "N" ? false : true);
		$('#eventProdYn').prop('checked', "${orderProdVo.eventProdYn}" == "N" ? false : true);
		
		//썸네일
		if("${orderProdVo.prodImagePath}" != "")
			$("#prodImageUrl").attr("src","${orderProdVo.prodImagePath}");
		
		$($.fn.dataTable.tables(true)).DataTable().columns.adjust();
		
		if ($("#resultOptionProdList").val() != '') {
			var screenList = JSON.parse($("#resultOptionProdList").val());
			$('#optionProdTable').DataTable().rows.add(screenList).draw();
		}
	
// 		$('#frm #Price').val(numberWithCommas($('#frm #Price').val()));
// 		$('#frm #SubPrice').val(numberWithCommas($('#frm #SubPrice').val()));
// 		isNaN($(val).val())
	});
	
	//기본 정보 저장
	function FormSave(){
		
		if($('#prodAmount').val() != "")
			$('#prodAmount').val($.number($('#prodAmount').val()));	
		if($('#prodDiscountAmount').val() != "")
			$('#prodDiscountAmount').val($.number($('#prodDiscountAmount').val()));
		if($('#weight').val() != "")
			$('#weight').val($.number($('#weight').val()));
		$('#prodBarcode').val($('#prodBarcode').val().replace(/[^0-9]/g, ''));
		
		if (!$.formCheck('#prodCode', 'Y', 30, 'N', '상품 코드'))
			return;
		if (!$.formCheck('#prodName', 'Y', 50, 'Y', '상품 명'))
			return;
		if (!$.formCheck('#prodDisplayName', 'Y', 50, 'Y', '상품 표시 명'))
			return;
		if (!$.formCheck('#prodEnName', 'Y', 50, 'Y', '상품 영문 명'))
			return;
		if (!$.formCommaCheck('#prodAmount', 'Y', 7, '상품금액'))
			return;
		if (!$.formCommaCheck('#prodDiscountAmount', 'Y', 7, '할인 금액'))
			return;
		if (!$.formCommaCheck('#weight', 'N', 7, '무게'))
			return;
		if (!$.formCheck('#prodBarcode', 'N', 15, 'N', '바코드'))
			return;
		if (!$.formCheck('#prodImage', 'Y', 300, 'N', '상품 이미지'))
			return;
		
		// 옵션 상품 체크
// 		if ($('#optionProdTable').DataTable().row().length == 0) {
// 			$.modalCommon.alertView("1개 이상의 옵션 상품이 선택되어야 합니다.", null, null, null);
// 			return;
// 		}
		
		var optionProdIdxList = [];
		
		
		for (var i = 0; i < $('#optionProdTable').DataTable().rows().data().length; i++) {
			var optionProdRowData = $('#optionProdTable').DataTable().rows().data()[i];
// 			console.log(screenRowData);
			optionProdIdxList.push(optionProdRowData.optionProdIdx);
			
		}
		
		
		var param = {
				brandIdx : $("#frm #brandIdx").val(),
				francIdx : $("#frm #francIdx").val(),
				orderProdIdx : $("#frm #orderProdIdx").val(),
				prodCode: $('#frm #prodCode').val(),
				prodName: $('#frm #prodName').val(),
				prodDisplayName: $('#frm #prodDisplayName').val(),
				prodEnName: $('#frm #prodEnName').val(),
				prodAmount: uncomma($('#frm #prodAmount').val()),
				prodDiscountAmount: uncomma($('#frm #prodDiscountAmount').val()),
				prodImage: $('#frm #prodImage').val(),
				eventProdYn: $("#frm #eventProdYn").val(),
				activeYn: $("#frm #activeYn:checked").val() == "Y" ? "Y":"N",
				eventProdScript: $("#frm #eventProdScript").val(),
				weight: $("#frm #weight").val() == '' ? '0':uncomma($("#frm #weight").val()),
				prodBarcode: $("#frm #prodBarcode").val(),
				optionProdIdxList: optionProdIdxList
				
		};
		
		var msgCode = $("#frm #orderProdIdx").val() == '' ? 'C':'U';
		
		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxArray('<c:url value="/OrderProd/Insert"/>', param);
				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'상품');
					orderProdList();
			    }	
			}
		}
		commonSave(msgCode, callBack);
	}
	
	function FilePopAdd(){
		if(!($('#frm #brandIdx').val() == 0 || $('#frm #francIdx').val() == 0)){
			var ext = ['PNG', 'JPEG', 'JPG'];
			
			$.showUploadBox({
				url: "/Upload/View/orderProd/",
				width: 400,
				height: 200,
				title: '상품 이미지',
				ext: ext,
				Finished: PopFileUploadFinished
			});
		}
		
	}
	
	function addBtn(id){
	    $.modalCommon.loadFullDataView('<i class="fa fa-fw fa-gear"></i> 옵션 상품 추가', '<c:url value="/Mobile/OrderProd/OptionProdForm"/>' , {brandIdx: $('#user #brandIdx').val() , francIdx: $('#user #francIdx').val()});
	};
	
	function PopFileUploadFinished(data) {
		var jsonData = data.length > 0 ? data[0]:null;
// 		console.log(jsonData);
		$("#frm #prodImage").val(jsonData.fileContentIdx);
		$("#frm #prodImageUrl").attr("src",jsonData.savePath);
	//		<input type="hidden" id="fileIdx" value="' + jsonData.idx + '">');
	}
	
	$('#DataTable').crudDatatable({
		id : 'optionProdTable',
    	mainName : 'optionProdName',
    	seqKey : 'optionProdIdx',
		scrollHeight : '20vh',
    	headList : [{
				title : '옵션 상품 명'
			}, {
				title : '옵션 상품 표시 명'
			}, {
				title : '상품금액'
			}],
		jsonData : null,
		columnDefs : [
			{
				'targets': 0,
	            'data': 'optionProdName',
	            'className': 'text-center tl200',
			    'render': function (data, type, full, meta) {
			        return '<div class="ellipsis200">'+fnDataTableRenderText(data)+'</div>';
			    }
			}, 
			{
				'targets': 1,
	            'data': 'optionProdDisplayName',
	            'className': 'text-center',
			    'render': function (data, type, full, meta) {
			    	return '<div class="ellipsis200">'+fnDataTableRenderText(data)+'</div>';
			    }
			}, 
			{
				'targets': 2,
                'data': 'optionProdAmount',
                'className': 'text-center',
			    'render': function (data, type, full, meta) {
			    	return data;
			    }
			}
			],
		columns:[{
				width:'40%'
			},{
				width:'40%'
			},{
				width:'20%'
			}]
			
    });
	
</script>