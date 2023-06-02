<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">매출 목록</div>
	</div>
	<form:form commandName="SalesHistoryVo" name="user" id="user" method="post">
		<form:hidden path="domainIdx" />
		<form:hidden path="brandIdx" />
		<form:hidden path="francIdx" />
		<form:hidden path="searchDay" />
		<form:hidden path="searchEndDay" />
		<form:hidden path="payType" />
		<form:hidden path="payProcCode" />
	</form:form>
	<div class="box-body">
		<table id="sumTable" class="table table-striped">
			<thead>
				<tr>
					<th style="text-align: center;">승인건수</th>
					<th style="text-align: center;">상품금액</th>
					<th style="text-align: center;">할인금액</th>
					<th style="text-align: center;">결제금액</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<h3><a id="anchorExcelDown" href="" hidden>Download Excel Document</a></h3>
	<div class="box-body">
		<table id="mTable" class="table table-striped dataTable">
			<thead>
				<tr>
					<th>순번</th>
					<th>단말명</th>
					<th>주문일시</th>
					<th>결제수단</th>
					<th>상품금액</th>
					<th>할인금액</th>
					<th>결제금액</th>
					<th>결제완료여부</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<input type="hidden" id="SM_PINDEX" value="">
<script type="text/javaScript">
	var date;
	var year;
	var month;
	var today;
	var endday;

	settingDate();

	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO02');
		treeHeight();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });

	});

	function settingDate(){
		date = new Date();
		year = date.getFullYear();
		month;
		if (date.getMonth()+1 < 10) {
			month = '0' + new String(date.getMonth()+1);
		}else {
			month =  new String(date.getMonth()+1);
		}
		var day = new String(date.getDate());

		if (day < 10) {
			day = '0' + day;
		}

		today = year + '-' + month + '-' + day;
		endday = today;

		$("#localSearchDay").val(today);
		$("#localSearchEndDay").val(today);
	}


	function excelDown(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectpayType = $("#selectpayType > option:selected").val();
		var selectpayProcCode = $("#selectPayProcCode > option:selected").val();

		$("#user #searchDay").val(localSearchDay);
		$("#user #searchEndDay").val(localSearchEndDay);
		$("#user #payType").val(selectpayType);
		$("#user #payProcCode").val(selectpayProcCode);

		$('#user').attr('action', '<c:url value="/SalesHistory/ExportExcel"/>');
   		$('#user').submit();
	}

	function payTypeChange(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectpayType = $("#selectpayType > option:selected").val();
		var selectpayProcCode = $("#selectPayProcCode > option:selected").val();

		var dfday = dateDiff(localSearchDay, localSearchEndDay)

		if(localSearchDay > localSearchEndDay){
			settingDate();
			$.modalCommon.alertView("조회 종료일은 시작일보다\n이후 일자이어야 합니다.");
			return false;
		}else if(dfday > 31){
			settingDate();
			$.modalCommon.alertView("조회 범위는 최대 31일 입니다.");
			return false;
		}else{
			$("#user #searchDay").val(localSearchDay);
			$("#user #searchEndDay").val(localSearchEndDay);
			$("#user #payType").val(selectpayType);
			$("#user #payProcCode").val(selectpayProcCode);

			salesHistoryList();
		}
	}

	function payProcCodeChange(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectpayType = $("#selectpayType > option:selected").val();
		var selectpayProcCode = $("#selectPayProcCode > option:selected").val();

		var dfday = dateDiff(localSearchDay, localSearchEndDay)

		if(localSearchDay > localSearchEndDay){
			settingDate();
			$.modalCommon.alertView("조회 종료일은 시작일보다\n이후 일자이어야 합니다.");
			return false;
		}else if(dfday > 31){
			settingDate();
			$.modalCommon.alertView("조회 범위는 최대 31일 입니다.");
			return false;
		}else{
			$("#user #searchDay").val(localSearchDay);
			$("#user #searchEndDay").val(localSearchEndDay);
			$("#user #payType").val(selectpayType);
			$("#user #payProcCode").val(selectpayProcCode);

			salesHistoryList();
		}
	}

	//트리 클릭 이벤트
	function callbackTreeSearchKeyEvt(e)
	{
        if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO01');
        	e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data){
		settingDate();
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectpayType = $("#selectpayType > option:selected").val();
		var selectpayProcCode = $("#selectPayProcCode > option:selected").val();
		$("#user #searchDay").val(localSearchDay);
		$("#user #searchEndDay").val(localSearchEndDay);
		$("#user #payType").val(selectpayType);
		$("#user #payProcCode").val(selectpayProcCode);

		$("#user #domainIdx").val(data.node.li_attr.domainidx);
        $("#user #brandIdx").val(data.node.li_attr.brandidx);
        $("#user #francIdx").val(data.node.li_attr.francidx);

        salesHistoryList();
	}

	// 	트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData)
	{
		$("#user #domainIdx").val(jsonData.IDX);
		salesHistoryList();
	}

	function salesHistoryList(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var payType = $("#payType").val();
		var payProcCode = $("#payProcCode").val();

		if(localSearchDay == '' || localSearchEndDay == '' || localSearchDay == null || localSearchEndDay == null || localSearchDay == 'undefined'|| localSearchEndDay == 'undefined' ){
			localSearchDay = today;
			localSearchEndDay = endday;
		}

		var optObject = {};
		optObject.id = "#mTable";
		optObject.url = '<c:url value="/SalesHistory/List"/>';

		optObject.arrColumns = [
			{
            	'width': '5%',
                'targets': 0,
                'data': 'rowIdx',
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return dataTableRowIdx(meta);
                }
            }, {
            	'width': '10%',
                'targets': 1,
                'data': 'deviceName',
                'className': 'text-center tl270',
                'render': function (data, type, full, meta) {
                	var html = '<a class="FileNameHover" onclick="detail(' + full.deviceIdx + ', ' +  full.orderMasterIdx + ')" style="cursor:pointer;text-decoration: underline;">'+'<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>' + '</a>';
                    return html ;
                }
            }, {
            	'width': '10%',
                'targets': 2,
                'data': 'orderDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	var html = '<span class="FileNameHover" onclick="detail(' + full.deviceIdx + ', ' +  full.orderMasterIdx + ')" style="cursor:pointer;">'+ fnDataTableRenderText(data) + '</span>';
                    return html ;
                }
            }, {
            	'width': '10%',
                'targets': 3,
                'data': 'payType',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	var mainPaymentName = '';
                	var html = '<span class="FileNameHover" onclick="detail(' + full.deviceIdx + ', ' +  full.orderMasterIdx + ')" style="cursor:pointer;">'+ fnDataTableRenderText(data) + '</span>';
                    return html ;
                }
            }, {
            	'width': '10%',
                'targets': 4,
                'data': 'orderAmt',
                'className': 'text-right',
                'render': function (data, type, full, meta) {
                	var html = '<span class="FileNameHover" onclick="detail(' + full.deviceIdx + ', ' +  full.orderMasterIdx + ')" style="cursor:pointer;">'+ Number(fnDataTableRenderText(data)).toLocaleString('kr') + '원' + '</span>';
                    return html ;
                }
            }, {
            	'width': '10%',
                'targets': 5,
                'data': 'discAmt',
                'className': 'text-right',
                'render': function (data, type, full, meta) {
                	var html = '<span class="FileNameHover" onclick="detail(' + full.deviceIdx + ', ' +  full.orderMasterIdx + ')" style="cursor:pointer;">'+ Number(fnDataTableRenderText(data)).toLocaleString('kr') + '원' + '</span>';
                    return html ;
                }
            }, {
            	'width': '10%',
                'targets': 6,
                'data': 'payAmt',
                'className': 'text-right',
                'render': function (data, type, full, meta) {
                	var html = '<span class="FileNameHover" onclick="detail(' + full.deviceIdx + ', ' +  full.orderMasterIdx + ')" style="cursor:pointer;">'+ Number(fnDataTableRenderText(data)).toLocaleString('kr') + '원' + '</span>';
                    return html ;
                }
            }, {
            	'width': '10%',
                'targets': 7,
                'data': 'payProcCode',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                    return fnDataTableRenderText(data);
                }
            }
        ];

		optObject.language = {"search": "단말 명"};

		optObject.fnServerData = function (sSource, aoData, fnCallback) {
			// 20190531 sort요청한 컬럼명 넘기기 추가
			var orderName = "", sortIndex = "";
			for(var idx=0; idx<aoData.length; idx++){
				if(aoData[idx].name == "iSortCol_0"){
					sortIndex = aoData[idx].value;
					break;
				}
			}

			for(var idx=0; idx<aoData.length; idx++){

				if(aoData[idx].name == "mDataProp_" + sortIndex){
					orderName = aoData[idx].value;
					aoData.push( { "name": "sSortName", "value": orderName } );
					break;
				}
			}

			$.post (sSource, aoData, function (json) {
				if(json.resultCode == 0){
					sumTableData(json.totalData);
					fnCallback (json);
					if(json.data == null || json.data.length == 0)
 						$(".dataTables_paginate.paging_simple_numbers").attr("style","display:none");
					else
 						$(".dataTables_paginate.paging_simple_numbers").attr("style","text-align: center;");
 				}else{
 				    if($('.wrap-loading').is(':visible')){
 				    	$('.wrap-loading').hide();
 				    }
 					$.modalCommon.alertView('네트워크가 끊겼습니다. 확인해주시기 바랍니다.');
 				}
			});
		 }

		optObject.fnRowCallback = function (row, data){
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx"		, "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx"		, "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx"		, "value": $("#francIdx").val() } );
            aoData.push( { "name": "searchDay"		, "value": localSearchDay } );
            aoData.push( { "name": "payType"		, "value": payType } );
            aoData.push( { "name": "payProcCode"		, "value": payProcCode } );
            aoData.push( { "name": "searchEndDay"	, "value": localSearchEndDay } );
            aoData.push( { "name": "defaultOrderName"   , "value": "orderDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		//optObject.type = "3";
		var mTable = commonPagingDateTable(optObject);

		$(".daterangepicker").remove();

		$("#mTable_length").parent().attr("class","col-md-2 col-sm-2 col-xs-2 col-lg-2");
		$("#mTable_filter").parent().attr("class","col-md-10 col-sm-10 col-xs-10 col-lg-10");
		$("#mTable_filter").prepend('<button type="button" class="btn btn-primary btn-sm" id="btn_excel_down" onclick="excelDown();">엑셀 다운로드</button>');
		$("#mTable_filter").prepend('~ <div class="input-group date"><input type="text" id="localSearchEndDay" class="form-control" style="background-color: #fff; height:30px; width:100px;" readonly="true" value="' + localSearchEndDay + '" onchange="salesDateChange();"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div> &nbsp;&nbsp;&nbsp;');
	    $("#mTable_filter").prepend('<label>검색일:</label> <div class="input-group date"><input type="text" id="localSearchDay" class="form-control" style="background-color: #fff; height:30px; width:100px;" readonly="true" value="' + localSearchDay + '" onchange="salesDateChange();"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div> &nbsp;&nbsp;&nbsp;');

		var html = '결제상태 : <select id="selectPayProcCode" onchange="payProcCodeChange();" aria-controls="mTable" class="form-control input-sm">';
		html += '<option value="" >전체</option>';
		if('${payProcCodeList}' != ""){
			var payProcCodeList = JSON.parse('${payProcCodeList}');
			for(var i = 0; i < payProcCodeList.length; i++)
				html += '<option value="'+fnDataTableRenderText(payProcCodeList[i].codeValue)+'" '+(fnDataTableRenderText(payProcCodeList[i].codeValue) == $("#payProcCode").val() ? 'selected':'')+'>'+fnDataTableRenderText(payProcCodeList[i].codeName)+'</option>';
		}
		html += '</select>&nbsp;';

		html += '결제수단 선택 : <select id="selectpayType" onchange="payTypeChange();" aria-controls="mTable" class="form-control input-sm">';
		html += '<option value="" >전체</option>';
		if('${SystemCodeList}' != ""){
			<c:forEach items="${SystemCodeList}" var="data" varStatus="inx">
				html += '<option value="<c:out value="${data.codeValue}"/>" '+('<c:out value="${data.codeValue}"/>' == $("#payType").val() ? 'selected':'')+'><c:out value="${data.codeName}"/></option>';
			</c:forEach>
		}
		html += '</select>&nbsp;';
		$("#mTable_filter").prepend(html);

	    $.datePickerCommon.createDatePicker('localSearchDay');
	    $.datePickerCommon.createDatePicker('localSearchEndDay');
		$('#localSearchDay').attr('readOnly', true);
		$('#localSearchEndDay').attr('readOnly', true);

		// 단말 조회조건 숨김처리
		$('#mTable_filter label').hide();
	}



	function salesDateChange(){
		var localSearchDay = $("#localSearchDay").val();
		var localSearchEndDay = $("#localSearchEndDay").val();
		var selectpayType = $("#selectpayType > option:selected").val();
		var selectpayProcCode = $("#selectPayProcCode > option:selected").val();

		var dfday = dateDiff(localSearchDay, localSearchEndDay)

		if(localSearchDay > localSearchEndDay){
			settingDate();
			$.modalCommon.alertView("조회 종료일은 시작일보다\n이후 일자이어야 합니다.");
			return false;
		}else if(dfday > 31){
			settingDate();
			$.modalCommon.alertView("조회 범위는 최대 31일 입니다.");
			return false;
		}else{
			$("#user #searchDay").val(localSearchDay);
			$("#user #searchEndDay").val(localSearchEndDay);
			$("#user #payType").val(selectpayType);
			$("#user #payProcCode").val(selectpayProcCode);

			salesHistoryList();
		}
	}

	// 두개의 날짜를 비교하여 차이를 알려준다.
	function dateDiff(_date1, _date2) {
	    var diffDate_1 = _date1 instanceof Date ? _date1 : new Date(_date1);
	    var diffDate_2 = _date2 instanceof Date ? _date2 : new Date(_date2);

	    diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
	    diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

	    var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
	    diff = Math.ceil(diff / (1000 * 3600 * 24));

	    return diff;
	}

	function detail(deviceIdx, orderMasterIdx){
		$.modalCommon.loadFullView('<i class="fa fa-fw fa-gear"></i> 매출 내역 상세', '<c:url value="/SalesHistory/SalesHistoryForm/"/>' + $('#brandIdx').val() +'/'+ $('#francIdx').val() + '/'+deviceIdx + '/'+orderMasterIdx);
	}

	function sumTableData(data) {
		var sum = 0, orderAmt = 0, discAmt = 0, payAmt = 0, pointAmt = 0;
		if (data != 'undefined' && data != null && data != 0) {
			var jsonTotalData = data;
			sum = jsonTotalData[0].orderCount;
			orderAmt = jsonTotalData[0].orderAmt;
			discAmt = jsonTotalData[0].discAmt;
			payAmt = jsonTotalData[0].payAmt;

		}

		var html = "";

		html = '<tr>'
			 + '<td class="text-center">' + Number(sum).toLocaleString('kr') + '건' + '</td>'
			 + '<td class="text-center">' + Number(orderAmt).toLocaleString('kr') + '원' + '</td>'
			 + '<td class="text-center">' + Number(discAmt).toLocaleString('kr') + '원' + '</td>'
			 + '<td class="text-center">' + Number(payAmt).toLocaleString('kr') + '원' + '</td>'
			 + '</tr>';
		$('#sumTable').find('tbody').html(html);


		// 매장정보가 없을 경우 모든 버튼 비활성화
		if($("#parentMenuId").val() != "SET00" && $("#parentMenuId").val() != "STO00" && ($('#brandIdx').val() == 0 || $('#francIdx').val() == 0)){
			$('button').not(".notDisabled").attr('disabled', true);
			$("input[type!='search']").not(".notDisabled").attr('disabled', true);
		}else{
			$('button').not(".notDisabled").attr('disabled', false);
			$("input[type!='search']").not(".notDisabled").attr('disabled', false);
		}
	}
</script>