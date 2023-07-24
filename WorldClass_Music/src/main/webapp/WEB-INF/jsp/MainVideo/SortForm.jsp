<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
	#contentTable.dataTable tbody tr.selected { background-color: #B2CCFF; }
	.nav-pills > li > a { border-radius: 0px !important; }
	
	table.dataTable tbody > tr > td {
    	padding: 0px;
	}
	
	.orderSeq{
		width:50%;
		padding: 5px 5px;
	}
	
	.dragRow {
		background-color: #B2CCFF;
	}
</style>

<div class="modal-body">
	<div class="box-body" style="border-bottom-style: solid !important; border-bottom:2px; border-bottom-color: #d2d6de;">
		<div class="col-sm-12" style="margin-top: 28px; padding-left: 0px; padding-right: 0px;">
			<table id="mainVideoFormTable" class="table table-striped">
				<thead>
					<tr>
						<th>No.</th>
						<th>컨텐츠 명</th>
						<th>순서</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div class="modal-footer">
		<div class="pull-right">
			<button type="button" class="btn btn-save procBtn" onclick="updateOrderSeq()" id="procBtn">추가</button>
			<button type="button" class="btn btn-cancel contentmodal notDisabled" data-dismiss="modal">닫기</button>
		</div>
	</div>
</div>
<script type="text/javaScript">
$(document).ready(function () {
	MainVideoFormList();
});

function moveTable(){
	if(!(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent))){
		$('#mainVideoFormTable').tableDnD({
			onDragClass: "dragRow"
		});
	}
}

function MainVideoFormList(){
	var optObject = {};

	optObject.id = "#mainVideoFormTable";
	optObject.url = '<c:url value="/MainVideo/MainVideoList"/>';
	optObject.arrColumnDefs = [
    ];

	optObject.arrColumns = [
		{data: "mainVideoIdx"},
        {data: "fileName"},
        {data: "orderSeq"}
    ];


	optObject.arrColumnDefs = [
        {
            'targets': 0,
            "orderable": false,
            'className': 'text-center',
            'render': function (data, type, full, meta) {
               	return dataTableRowIdx2(meta) + '<input type="hidden" id="mainVideoIdx" value="' + full.mainVideoIdx + '">';
            }
        },
        {
            'targets': 1,
            'className': 'text-center tl270',
            "orderable": false,
            'display': 'display',
            'render': function (data, type, full, meta) {
            	return fnDataTableRenderText(data);
            }

        },
        {
            'targets': 2,
            "orderable": false,
            'className': 'text-center',
            'render': function (data, type, full, meta) {
            	return fnDataTableRenderText(data);
            }
        }
    ];
	
	optObject.fnDrawCallback = function (data){
		moveTable();
	}
	optObject.type="2"
	optObject.searching = false;
	optObject.iDisplayLength = false;
	
	
	optObject.serverParams = function ( aoData ) {
        aoData.push( { "name": "defaultOrderName"   , "value": "orderSeq" } );
        aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
    }

    mTable = commonPagingDateTable(optObject);
}

function updateOrderSeq(){
	var orderList = new Array();
	var tableLength = $('#mainVideoFormTable').find('tbody').find('tr').length;
	
	$('#mainVideoFormTable').find('tbody').find('tr').each(function(e) {
		var mainVideoIdx = $(this).find('[id="mainVideoIdx"]').val();
		var orderSeq = tableLength - e;
		var param = {'mainVideoIdx': mainVideoIdx, 'orderSeq': orderSeq}
		orderList.push(param);
	});
	
	var param = {"list" : orderList};
	
	var msgCode = 'U';
	var url = '/MainVideo/SortUpdate';
	
	var callBack = function (result) {
		if (result) {
			ajaxToJsonBody(url, param, msgCode);
		}
	}
	commonSave(msgCode, callBack);
}

function ajaxToJsonBody(url, param, msgCode) {
	$.ajax({
        url: url,
        data: JSON.stringify(param),
        type: 'post',
        dataType: 'json',
        postBody : true,
        contentType:"application/json; charset=UTF-8",
        success: function (data) {
            if (data.resultCode == 0){
            	isResult = data;
            	if (data.resultCode == 0){
					$.modalCommon.close();
					commMessage(msgCode,'메인비디오 순서를');
					MainVideoList();
			    }
            } else {
            	$.modalCommon.alertView(data.messages.title, focus, data.messages.detail, null);
            	isResult = data;
            }
        }, error:function(request, status, error){
    
        	$.modalCommon.alertView(request.status + " : " + error, focus, null, null);
        	isResult = {"resultCode" : request.status, "messages" : {"title" : error}};
        }
    });
}
</script>