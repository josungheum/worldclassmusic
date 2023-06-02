<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.userList thead tr th{
		text-align : center;
	}
</style>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">단말 버전 목록</div>
	</div>
	<div class="box-body">
		<table id="mTable" class="table table-striped userList">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
					<th>No</th>
					<th>버전 정보</th>
					<th>변경 내용</th>
					<th>수정일</th>
					<th>배포건수</th>
					<th>배포일</th>
					<th>배포</th>
					<th>상세보기</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="kioskClientAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="kioskClientDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>
<input type="hidden" id="SM_PINDEX">
<script type="text/javaScript">
	$(document).ready(function () {
		clientList();
	});

	function clientList() {
		var optObject = {};

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/KioskClient/ClientList"/>';
		optObject.arrColumnDefs = [
			{
	           	'width': '5%',
	               'targets': 0,
	               'data': 'idx',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	              		return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
	               }
	        },
	        {
			    "targets": [1]
		       ,"className": "text-center"
		       ,"orderable": false
		       ,'render': function (data, type, full, meta) {
	               	return dataTableRowIdx(meta);
	           }
		    },
	        {
	        	'width': '15%',
	            'targets': 7,
	            'data': 'idx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta)
	            {
	            	return '<button type="button" class="btn btn-warning" onclick="kioskClientDist(\'' + full.kioskClientIdx + '\');" id="Dist">배포</button>';
	            }
	        },
	        {
	        	'width': '15%',
	            'targets': 8,
	            'data': 'idx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta)
	            {
	            	return '<button type="button" class="btn btn-warning" onclick="kioskClientEdit(\'' + full.kioskClientIdx + '\');" id="Detail">상세보기</button>';
	            }
	        },
	        {
		    	"targets": [3]
		       ,"className": "text-center tl270"
	    	   ,'render': function (data, type, full, meta) {
	    		   return '<div class="ellipsis270">'+(fnDataTableRenderText(data) == null ? "" : fnDataTableRenderText(data))+'</div>';
               }
		    },
	        {
			    "targets": [2, 4, 5, 6]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		    }

	    ];
	   	optObject.arrColumns = [
	   		{data: "kioskClientIdx"},
	   		{data: "rowIdx"},
            {data: "version"},
            {data: "changes"},
            {data: "regDate"},
            {data: "distCount"},
            {data: "modDate"},
            {data: "kioskClientIdx"},
            {data: "kioskClientIdx"}

        ];

	   	optObject.language = {"search": "버전"};
	   	optObject.serverParams = function ( aoData ) {
            aoData.push( { "name": "defaultOrderName"	, "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"	, "value": "DESC" } );
        }
		var mTable = commonPagingDateTable(optObject);



	}

	function kioskClientDelete(){
		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');

		var callBack = function (result) {
			if (result) {
                var param = {checkboxArr: checkboxArr };
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/KioskClient/ClientDelete"/>', param);

                if (dResult.resultCode == 0) {
                	$.modalCommon.alertView('선택된 클라이언트가 삭제되었습니다.');
                	clientList();
                }
            }
        }
		commonDelete(checkboxArr, callBack);


	}

	function callbackTreeSearchKeyEvt(e)
	{
		if (e.keyCode == 13) {
        	treeSearchList(1);
        	e.keyCode = 0;
		}
	}

	function callbackSelectNodeJstree(data)
	{
// 		tabView(0);
	}

	function callbackFirstSelect(jsonData)
	{
// 		tabView(0);
	}

	function kioskClientAdd() {
		$.sessionCheck();
	    $.modalCommon.loadDataView('클라이언트 등록', '<c:url value="/KioskClient/KioskClientForm"/>');
	}

	function kioskClientEdit(kioskClientIdx) {
	    $.modalCommon.loadDataView('클라이언트 수정', '<c:url value="/KioskClient/KioskClientForm"/>',{kioskClientIdx:kioskClientIdx});
	}

	function kioskClientDist(kioskClientIdx) {
	    $.modalCommon.loadDataView('클라이언트 배포', '<c:url value="/KioskClient/KioskDistForm"/>',{kioskClientIdx:kioskClientIdx});
	}
</script>