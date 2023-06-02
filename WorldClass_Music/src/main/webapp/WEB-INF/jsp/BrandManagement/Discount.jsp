<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="brandVo" name="discount" id="discount" method="post">
<style>
.DiscountHover:hover {
	color:#FF0000;
}
</style>
<div class="box-body">
	<table id="mTable" class="table table-striped">
	    <thead>
	        <tr>
	            <th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
	            <th>No</th>
	            <th>할인명</th>
	            <th>할인등급</th>
	            <th>할인율</th>
	            <th>시작일</th>
	            <th>종료일</th>
	            <th>활성화</th>
	        </tr>
	    </thead>
	</table>
</div>
<div class="box-footer" id="inputBtn">
	<div class="pull-right">
		<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="discountAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
		<button type="button" class="btn btn-delete" onclick="discountDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
	</div>
</div>
</form:form>
<script>
	$(document).ready(function () {
		discountList();

	});

	function discountAdd() {
	    $.modalCommon.loadDataView('브랜드 할인율 편집', '<c:url value="/BrandManagement/DiscountFormNullTemp"/>' , {brandIdx:$('#brandIdx').val()});
	}

	function discountEdit(val) {
	    $.modalCommon.loadDataView('브랜드 할인율 편집', '<c:url value="/BrandManagement/DiscountFormNullTemp/"/>' , {brandIdx:$('#brandIdx').val(), discountIdx:val});
	}

	function discountList() {
		var optObject = {};

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/BrandManagement/DiscountList"/>';

	   	optObject.arrColumns = [
            {data: "discountIdx"},
            {data: "rowIdx"},
            {data: "discountName"},
            {data: "discountLevel"},
            {data: "discountPercent"},
            {data: "startDate"},
            {data: "endDate"},
            {data: "activeYn"}

        ];

	   	optObject.arrColumnDefs = [
	   		{
	           	'width': '5%',
	               'targets': 0,
	               "orderable": false,
	               'data': 'idx',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	              		return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
	               }
	        },
	        {
		    	"targets": [1]
	           ,"orderable": false
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	                   return dataTableRowIdx(meta);
	               }
		    },
	        {
		    	"targets": [2]
		       ,"className": "text-center tl270"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270"><a class="FileNameHover" onclick="discountEdit('+full.discountIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) +'</a></div>';
               }
		    },
	        {
		    	"targets": [3, 4, 5, 6]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		    },
		    {
	        	   'width': '15%',
	               'targets': 7,
	               'data': 'activeyn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="discountActive(\'' + full.rowIdx + '\',\'' + full.discountIdx + '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="discountActive(\'' + full.rowIdx + '\',\'' + full.discountIdx + '\');" data-reverse>';
	               }
	         }

	    ];

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
			aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
			aoData.push( { "name": "defaultOrderName"   , "value": "endDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );

        }

		optObject.language = {"search": "할인율"};

		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		var mTable = commonPagingDateTable(optObject);
	}

	//활성화 공통으로 변경
	function discountActive(rownum, val)
	{
		commonActive(rownum, { discountIdx: val, brandIdx : $("#brandIdx").val()}, '<c:url value="/BrandManagement/DiscountActive"/>');
	}

	function discountDelete() {
		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');

		var callBack = function (result) {
        	if (result) {
                var param = {checkboxArr: checkboxArr, brandIdx : $("#brandIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/BrandManagement/DiscountDelete"/>', param);
                if (dResult.resultCode == 0){
                	commMessage('D','할인율');
                	discountList($('#brandIdx').val());
                }
            }
        }
		commonDelete(checkboxArr, callBack);
	}

</script>