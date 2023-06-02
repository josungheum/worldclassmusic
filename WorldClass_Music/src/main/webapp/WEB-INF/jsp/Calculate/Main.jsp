<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	#jstree-selectscreen .jstree-wholerow-clicked{
		background : transparent;
	}

	#jstree-selectscreen .jstree-clicked{
		color : #000000;
	}
</style>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">정산 목록</div>
	</div>
	<div class="box-body">
		<table id="mTable" class="table table-striped">
			<thead>
				<tr>
					<th>순번</th>
					<th>정산일</th>
					<th>가맹점</th>
					<th>단말명</th>
					<th>정산상태</th>
					<th>정산일시</th>
					<th>작업자</th>
					<th>정산</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<input type="hidden" id="SM_PINDEX">
<script type="text/javaScript">
	var mTable;
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

		$("#calculateDate").val(today);
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
		$('#domainIdx').val(data.node.li_attr.domainidx);
        $('#brandIdx').val(data.node.li_attr.brandidx);
        $('#francIdx').val(data.node.li_attr.francidx);

    	dataList();
	}

	// 	트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData)
	{
		dataList();
	}

	function dataList(){
		var calculateDate = $("#calculateDate").val();

		if(calculateDate == '' || calculateDate == null ||  calculateDate == 'undefined'){
			calculateDate = today;
		}

		var optObject = {};
		optObject.id = "#mTable";
		optObject.url = '<c:url value="/Calculate/List"/>';
		optObject.arrColumnDefs = [
	    ];


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
					fnCallback (json);
					if($("#parentMenuId").val() != "SET00" && $("#parentMenuId").val() != "STO00" && ($('#brandIdx').val() == 0 || $('#francIdx').val() == 0)){

						$('button').not(".notDisabled").attr('disabled', true);
						$("input[type!='search']").not(".notDisabled").attr('disabled', true);
					}else{
						$('button').not(".notDisabled").attr('disabled', false);
						$("input[type!='search']").not(".notDisabled").attr('disabled', false);
					}

					if(json.data == null || json.data.length == 0)
 						$(".dataTables_paginate.paging_simple_numbers").attr("style","display:none");
					else
 						$(".dataTables_paginate.paging_simple_numbers").attr("style","text-align: center;");
 				}else{
 					$.modalCommon.alertView('네트워크가 끊겼습니다. 확인해주시기 바랍니다.');
 				}
			});
		 }

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
            },
            {
            	'width': '10%',
                'targets': 1,
                'data': 'calculateDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	if(data != null){
                		return data.substring(0,4) + '-' + data.substring(4,6) + '-' + data.substring(6,8);
                	}else{
                		return data;
                	}
                }
            },
            {
                'targets': 2,
                'data': 'francName',
                'className': 'text-center tl270',
                'render': function (data, type, full, meta) {
                	return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
                }
            },
            {
                'targets': 3,
                'data': 'deviceName',
                'className': 'text-center tl270',
                'render': function (data, type, full, meta) {
                	return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
                }
            },
            {
            	'width': '10%',
                'targets': 4,
                'data': 'calculateYn',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	if (fnDataTableRenderText(data) != 'N') {
						return '완료';
					} else {
						return '미완료';
					}
                }
            },
            {
            	'width': '10%',
                'targets': 5,
                'data': 'modDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                    return data;
                }
            },
            {
            	'width': '10%',
                'targets': 6,
                'data': 'modUser',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return data;
                }
            },
            {
            	'width': '10%',
                'targets': 7,
                'data': 'calculateYn',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	if (data != 'N') {
						return '<button type="button" class="btn btn-default" data-toggle="modal" onclick="recalculate(' + full.deviceIdx + ');"> 확 인 </button>';
					} else {
						return '<button type="button" class="btn btn-primary" data-toggle="modal" onclick="calculate(' + full.deviceIdx + ');"> 정 산 </button>';
					}
                }
            }

        ];

		optObject.language = {"search": "단말 명"};

		optObject.fnDrawCallback = function (data){
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
            aoData.push( { "name": "calculateDate", "value": calculateDate } );
            aoData.push( { "name": "defaultOrderName"   , "value": "francName" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "ASC" } );
        }

		mTable = commonPagingDateTable(optObject);

		//2019-04-10 김한기 화면 이동시 피커가 남는 경우가 생김(같은 DIV가 여러개 생성됨)
		$(".daterangepicker").remove();

	    $("#mTable_filter").prepend('<label>검색일:</label> <div class="input-group date"><input type="text" id="calculateDate" class="form-control" style="background-color: #fff; height:30px; width:100px;" readonly="true" value="' + calculateDate + '" onchange="salesDateChange();"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div> &nbsp;&nbsp;&nbsp;');

	    $.datePickerCommon.createDatePicker('calculateDate');
	    $('#calculateDate').attr('readOnly', true);

	 	// 단말 조회조건 숨김처리
		$('#mTable_filter label').hide();

	}

	function salesDateChange(){
		dataList();
	}

	// 미정산 된 디바이스에 대한 정산 처리를 윈한 팝업
	function calculate(deviceIdx){
		var paramValue = {brandIdx:$("#brandIdx").val(), domainIdx: $("#domainIdx").val(), deviceIdx:deviceIdx, francIdx:$("#francIdx").val(), calculateDate:$("#calculateDate").val()};
// 		$.modalCommon.loadFullDataView('정산내역 확인', '<c:url value="/Calculate/CalculateForm"/>', paramValue);
		 BootstrapDialog.show({
             title: '정산내역 확인',
             draggable: true,
             closable: false,
             size: BootstrapDialog.SIZE_WIDE,
             message: function (dialog) {
                 var $message = $('<div></div>');
                 var pageToLoad = dialog.getData('pageToLoad');
                 $message.load(pageToLoad,paramValue);

                 return $message;
             },
             data: {
                 'pageToLoad': '<c:url value="/Calculate/CalculateForm"/>'
             },
             onshown: function (dialog) {
                 $('.modal-dialog :input[type="text"]:enabled:visible:first').focus();
             }
             ,
             onhide: function (dialog) {
            	 mTable.draw( false );
             }
         });
	}

	// 정산 처리된 디바이스에 대한 확인 / 재정산 팝업
	function recalculate(deviceIdx){
		var paramValue = {brandIdx:$("#brandIdx").val(), domainIdx: $("#domainIdx").val(), deviceIdx:deviceIdx, francIdx:$("#francIdx").val(), calculateDate:$("#calculateDate").val()};
// 		$.modalCommon.loadFullDataView('정산내역 확인', '<c:url value="/Calculate/ReCalculateForm"/>', paramValue);
		BootstrapDialog.show({
            title: '정산내역 확인',
            draggable: true,
            closable: false,
            size: BootstrapDialog.SIZE_WIDE,
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad,paramValue);

                return $message;
            },
            data: {
                'pageToLoad': '<c:url value="/Calculate/ReCalculateForm"/>'
            },
            onshown: function (dialog) {
                $('.modal-dialog :input[type="text"]:enabled:visible:first').focus();
            }
            ,
            onhide: function (dialog) {
           	 dataList();
            }
        });
	}


	function calculateVo(deviceIdx){
		BootstrapDialog.confirm({
            title: '',
            message: '정산하시겠습니까?',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            draggable: true,
            btnCancelLabel: '취소',
            btnOKLabel: '저장',
            btnOKClass: 'btn-warning',
            callback: function (result) {
                if (result) {
                    var param = { domainIdx : $("#domainIdx").val(), brandIdx : $("#brandIdx").val(), francIdx : $("#francIdx").val(), deviceIdx : deviceIdx , calculateDate : $('#calculateDate').val()};
                    var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Calculate/InsertCalculate"/>', param);

                    if (dResult.resultCode == 0){
	                    salesDateChange();
	        			$.modalCommon.close();
                    	$.modalCommon.alertView('정산처리가 완료되었습니다.');
                    }
                    else {
                        $.modalCommon.alertView('처리 되지 않았습니다.');
                    }
                }
                else {
                }
            }
        });
	}

	function reCalculateVo(deviceIdx){
		BootstrapDialog.confirm({
            title: '',
            message: '재정산하시겠습니까?',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            draggable: true,
            btnCancelLabel: '취소',
            btnOKLabel: '저장',
            btnOKClass: 'btn-warning',
            callback: function (result) {
                if (result) {
                	var param = { domainIdx : $("#domainIdx").val(), brandIdx : $("#brandIdx").val(), francIdx : $("#francIdx").val(), deviceIdx : deviceIdx , calculateDate : $('#calculateDate').val()};
                    var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Calculate/UpdateCalculate"/>', param);

                    if (dResult.resultCode == 0){
	                    salesDateChange();
	        			$.modalCommon.close();
                    	$.modalCommon.alertView('재정산처리가 완료되었습니다.');
                    }
                    else {
                        $.modalCommon.alertView('처리 되지 않았습니다.');
                    }
                }
                else {
                }
            }
        });
	}
</script>