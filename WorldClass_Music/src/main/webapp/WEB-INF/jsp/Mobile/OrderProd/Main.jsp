<%--
  Name : Main.jsp
  Description : 모바일 상품관리
  Modification Information
    수정일                    수정자                  수정내용
  -------      --------    ---------------------------
  2019.06.19   김한기                  최초작성
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form:form commandName="OrderProdVo" name="orderProdVo" id="orderProdVo" method="post">
	<form:hidden path="domainIdx" />
	<form:hidden path="brandIdx" />
	<form:hidden path="francIdx" />
</form:form>


<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">상품 목록</div>
	</div>
	<div class="box-body">
		<table id="orderProdListTable" class="table table-striped">
			<thead>
				<tr>
					<th></th>
					<th>상품표시 명</th>
					<th>금액</th>
					<th>활성화</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="mainAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>추가</button>
<!-- 			<button type="button" class="btn btn-delete" onclick="mainDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button> -->
		</div>
	</div>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
		orderProdList();

		$('#btn_back').off('click').on('click', function(){
			$('#orderProdVo').attr('action', '/Mobile/Resource/Main');
            $('#orderProdVo').submit();
		});

	});

	//조회
	function orderProdList() {
		var optObject = {};


		optObject.type = "4";
		optObject.id = "#orderProdListTable";
		optObject.url = '<c:url value="/OrderProd/OrderProdList"/>';
		optObject.arrColumnDefs = [
		    {
		        	"targets": 0
			       ,"className": "text-center"
			       ,"orderable": false
			       ,'render': function (data, type, full, meta) {
			    	   return '<img style="width: 40px;height: 40px;" src="'+fnDataTableRenderText(full.thumbnailPath)+'">';
		           }
			 },
	         {
	           	   'targets': 1,
	               'data': 'title',
	               "className": "text-center tl130",
	               'render': function (data, type, full, meta) {
	            	   return '<div class="ellipsis130"><a id="name' + full.index + '" class="NoticeHover" onclick="mainEdit(\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + full.orderProdIdx+ '\')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a></div>';
	         		}
	         },
			{
	        	   'targets': 3,
	               'data': 'activeyn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="mainActive(\'' + full.rowIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + full.orderProdIdx+ '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="mainActive(\'' + full.rowIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + full.orderProdIdx+ '\');" data-reverse>';
	               }
	         },
	         {
	        	"targets": [2]
		       ,"className": "text-center"
		       ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     }
	    ];
	   	optObject.arrColumns = [
            {data: "rowIdx"},
            {data: "prodDisplayName"},
            {data: "prodAmount"},
            {data: "activeYn"}


        ];

	   	optObject.language = {"search": "상품표시 명"};

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

	  	//검색 제외
		optObject.searching = true;
		//?개씩 보기 제외
		optObject.bLengthChange = false;
		//한 화면에 5개씩으로 고정
// 		optObject.pageLen = 5;

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#orderProdVo #domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#orderProdVo #brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#orderProdVo #francIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );

        }

		var mTable = commonPagingDateTable(optObject);

	}

	//추가
	function mainAdd() {
		$.sessionCheck();
        $.modalCommon.loadDataView('상품 등록', '<c:url value="/Mobile/OrderProd/Form"/>',{brandIdx:$('#mobileForm #brandIdx').val(), francIdx:$('#mobileForm #francIdx').val()});
	}


	//수정
	function mainEdit(brand, franc, orderProd) {
		$.sessionCheck();
		$.modalCommon.loadDataView('상품 상세보기', '<c:url value="/Mobile/OrderProd/Form"/>',{brandIdx:brand, francIdx:franc, orderProdIdx:orderProd});

	}

	//활성화 공통으로 변경
	function mainActive(rownum, brandIdx, francIdx, orderProdIdx)
	{
		commonActive(rownum, { brandIdx: brandIdx, francIdx: francIdx, orderProdIdx: orderProdIdx}, '<c:url value="/OrderProd/OrderProdActive"/>');
	}


</script>