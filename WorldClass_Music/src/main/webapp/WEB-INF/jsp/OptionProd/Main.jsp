<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">옵션 상품 목록</div>
	</div>
	<div class="box-body">
		<table id="optionProdListTable" class="table table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
					<th>No</th>
					<th>옵션 상품 명</th>
					<th>옵션 상품표시 명</th>
					<th>상품금액</th>
					<th>수정일</th>
					<th>활성화</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="mainAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="mainDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
	    treeSearchList(0);
		treeHeight();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });
	});

	//트리 클릭 이벤트
	function callbackTreeSearchKeyEvt(e)
	{
		if (e.keyCode == 13) {
        	treeSearchList(1);
        	e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data)
	{
		optionProdList();
	}

	//트리가 처음 그려지는 경우 콜백
	function callbackFirstSelect(jsonData)
	{

		optionProdList();
	}

	//조회
	function optionProdList() {
		var optObject = {};

		optObject.id = "#optionProdListTable";
		optObject.url = '<c:url value="/OptionProd/OptionProdList"/>';
		optObject.arrColumnDefs = [
			{
		    	"targets": 3
		       ,"className": "text-center tl270"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
			{
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
	           	   'targets': 2,
	               'data': 'title',
	               "className": "text-center tl130",
	               'render': function (data, type, full, meta) {
	            	   return '<div class="ellipsis130"><a id="name' + full.index + '" class="NoticeHover" onclick="mainEdit(\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + full.optionProdIdx+ '\')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a></div>';
	         		}
	         },
			{
	        	   'targets': 6,
	               'data': 'activeyn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="mainActive(\'' + full.rowIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + full.optionProdIdx+ '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="mainActive(\'' + full.rowIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + full.optionProdIdx+ '\');" data-reverse>';
	               }
	         },
	         {
	           	   'targets': 2,
	               'data': 'prodCode',
	               "className": "text-center tl130",
	               'render': function (data, type, full, meta) {
	            	   return '<div class="ellipsis130">'+fnDataTableRenderText(data)+'</div>';
	         		}
	         },
	         {
	        	"targets": [4, 5]
		       ,"className": "text-center"
		       ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     }
	    ];
	   	optObject.arrColumns = [
            {data: "optionProdIdx"},
            {data: "rowIdx"},
            {data: "optionProdName"},
            {data: "optionProdDisplayName"},
            {data: "optionProdAmount"},
            {data: "modDate"},
            {data: "activeYn"}
        ];

	   	optObject.language = {"search": "옵션 상품표시 명"};

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );

        }

		var mTable = commonPagingDateTable(optObject);

	}

	//추가
	function mainAdd() {
		$.sessionCheck();
        $.modalCommon.loadDataView('옵션 상품 등록', '<c:url value="/OptionProd/Form"/>',{brandIdx:$('#brandIdx').val(), francIdx:$('#francIdx').val()});
	}


	//수정
	function mainEdit(brand, franc, optionProdIdx) {
		
		$.sessionCheck();
		$.modalCommon.loadDataView('옵션 상품 상세보기', '<c:url value="/OptionProd/Form"/>',{brandIdx:brand, francIdx:franc, optionProdIdx:optionProdIdx});

	}

	//삭제
	function mainDelete() {

		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');

		var callBack = function (result) {
        	if (result) {
                var param = {checkboxArr: checkboxArr, brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/OptionProd/OptionProdDelete"/>', param);
                if (dResult.resultCode == 0){
                	commMessage('D','상품');
                	optionProdList();
                }
            }
        }

		commonDelete(checkboxArr, callBack);
	}

	function numberWithCommas(val) {
		return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	//활성화 공통으로 변경
	function mainActive(rownum, brandIdx, francIdx, optionProdIdx)
	{
		commonActive(rownum, { brandIdx: brandIdx, francIdx: francIdx, optionProdIdx: optionProdIdx}, '<c:url value="/OptionProd/OptionProdActive"/>');
	}


</script>