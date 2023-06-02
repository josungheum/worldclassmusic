<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">재고 목록</div>
	</div>
	<div class="box-body">
		<table id="orderProdListTable" class="table table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>상품 코드</th>
					<th>상품 명</th>
					<th>가격</th>
					<th>수량</th>
					<th>수정일</th>
				</tr>
			</thead>
		</table>
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
		orderStockList();
	}

	//트리가 처음 그려지는 경우 콜백
	function callbackFirstSelect(jsonData)
	{

		orderStockList();
	}

	//조회
	function orderStockList() {
		var optObject = {};

		optObject.id = "#orderProdListTable";
		optObject.url = '<c:url value="/OrderProd/OrderProdList"/>';
		optObject.arrColumnDefs = [
			{
			    "targets": [0]
		       ,"className": "text-center"
		       ,"orderable": false
		       ,'render': function (data, type, full, meta) {
	               	return dataTableRowIdx(meta);
	           }
		     },
		     {
				    "targets": [1, 3, 4, 5]
			       ,"className": "text-center"
		    	   ,'render': function (data, type, full, meta) {
		    		   return fnDataTableRenderText(data);
	               }
			 },
		     {
			    "targets": [2]
		       ,"className": "text-left"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     }
	    ];
	   	optObject.arrColumns = [
            {data: "rowIdx"},
            {data: "prodCode"},
            {data: "prodName"},
            {data: "prodAmount"},
            {data: "prodStock"},
            {data: "modDate"}


        ];

	   	optObject.language = {"search": "상품 명"};



		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
        }

		var mTable = commonPagingDateTable(optObject);

	}

	function numberWithCommas(val) {
		return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
</script>