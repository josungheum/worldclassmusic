<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form commandName="OrderScreenVo" name="orderScreenVo" id="orderScreenVo" method="post">
	<form:hidden path="domainIdx" />
	<form:hidden path="brandIdx" />
	<form:hidden path="francIdx" />
</form:form>


<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">주문화면 목록</div>
	</div>
	<div class="box-body">
		<table id="orderScreenListTable" class="table table-striped">
			<thead>
				<tr>
					<th>주문화면명</th>
					<th>상태</th>
					<th>적용 단말</th>
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
		orderScreenList();

		$('#btn_back').off('click').on('click', function(){
			//console.log($("#orderScreenVo").serialize());
 			$('#orderScreenVo').attr('action', '/Mobile/Resource/Main');
            $('#orderScreenVo').submit();
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
		orderScreenList();
	}

	//트리가 처음 그려지는 경우 콜백
	function callbackFirstSelect(jsonData)
	{

		orderScreenList();
	}

	//조회
	function orderScreenList() {
		var optObject = {};

		optObject.type = "4";
		optObject.bLengthChange = false;
		optObject.id = "#orderScreenListTable";
		optObject.url = '<c:url value="/OrderScreen/OrderScreenList"/>';
		optObject.arrColumnDefs = [

	         {
		    	 "targets": 0
		        ,"className": "text-center tl130"
	    	    ,'render': function (data, type, full, meta) {
           	   	    return '<div class="ellipsis130"><a id="name' + full.index + '" class="NoticeHover" onclick="mainEdit(\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + full.orderScreenIdx+ '\')" style="cursor:pointer;text-decoration: underline;">'+fnDataTableRenderText(data)+'</a></div>';
                }
		     },
	         {

	               'targets': 1,
	               'data': 'targetCount',

	               'render': function (data, type, full, meta) {
	            	   if(data == 0)
		            		return '미적용';
		            	else
		               		return '적용';
	         		}
		     },
	         {
			    "targets": 2
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     }
	    ];
	   	optObject.arrColumns = [
            {data: "orderScreenName"},
            {data: "targetCount"},
            {data: "targetCount"}

        ];

	   	optObject.language = {"search": "주문화면명"};


		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#orderScreenVo #domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#orderScreenVo #brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#orderScreenVo #francIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "regDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		var mTable = commonPagingDateTable(optObject);

	}

	function demo_create() {
		var ref = $('#treeList').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = ref.create_node(sel[0], {"type":"file"});
		if(sel) {
			ref.edit(sel);
		}
	};

	//추가
	function mainAdd() {
		$.sessionCheck();
        $.modalCommon.loadDataView('주문화면 등록', '<c:url value="/Mobile/OrderScreen/Form"/>',{brandIdx:$('#brandIdx').val(), francIdx:$('#francIdx').val()});
	}


	//수정
	function mainEdit(brand, franc, orderScreenIdx) {

		$.sessionCheck();
		$.modalCommon.loadDataView('주문화면 수정', '<c:url value="/Mobile/OrderScreen/Form"/>',{brandIdx:brand, francIdx:franc, orderScreenIdx:orderScreenIdx});

	}

	//삭제
	function mainDelete() {

		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');

		var callBack = function (result) {
        	if (result) {
                var param = {checkboxArr: checkboxArr, brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/OrderScreen/OrderScreenDelete"/>', param);
                if (dResult.resultCode == 0){
                	commMessage('D','주문화면');
                	orderScreenList();
                }
            }
        }

		commonDelete(checkboxArr, callBack);
	}

	function numberWithCommas(val) {
		return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	//활성화 공통으로 변경
	function orderScreenActive(rownum, brandIdx, francIdx, orderProdIdx)
	{
		commonActive(rownum, { brandIdx: brandIdx, francIdx: francIdx, orderProdIdx: orderProdIdx}, '<c:url value="/OrderProd/OrderProdActive"/>');
	}

</script>