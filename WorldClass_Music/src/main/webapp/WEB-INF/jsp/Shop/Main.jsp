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
		<div class="user-block under-line">브랜드 목록</div>
	</div>
	<div class="box-body">
		<table id="shopTable" class="table table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
					<th>No</th>
					<th>로고</th>
					<th>브랜드명</th>
					<th>층명</th>
					<th>전화번호</th>
					<th>상세정보 노출 여부</th>
					<!--th>활성화</th-->
					<th>수정일</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="Add();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="Delete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>
<input type="hidden" id="SM_PINDEX">
<script type="text/javaScript">
	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO01');
		treeHeight();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });
	});
	
	function List(){
		if ($('#brandIdx').val() == 0) {
			$('.btn').attr('disabled', true);
		}
		else {
			$('.btn').attr('disabled', false);
		}
		
		var optObject = {};

		optObject.id = "#shopTable";
		optObject.url = '<c:url value="/Shop/List"/>';
		optObject.arrColumnDefs = [
			{
				'width': '5%',
	            'targets': 0,
	            "orderable": false,
	            'className': 'text-center',
	            'render': function (data, type, full, meta) {
	            	return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
	            }
	         },
	         {
	        	 "targets": [1],
	        	 "className": "text-center",
	        	 "orderable": false,
	        	 'render': function (data, type, full, meta) {
	        		 return dataTableRowIdx(meta);
	        	 }
			 },
	         {
		    	 "targets": [2],
		    	 "width" : '10%',
		    	 "className": "text-center tl270",
		    	 'render': function (data, type, full, meta) {
		    		 if (data != null) {
		    			 return html = '<img src="'+data+'" style="width:50px; heigth:50px;">';	 
		    		 }
		    		 else {
		    			 return html = '<img src="" style="width:50px; heigth:50px;">';
		    		 }
		    		 
		    	 }
		     },
	         {
		    	'width' : '20%',
			    "targets": [3]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return '<a onclick="Edit('+full.shopIdx+', '+full.domainIdx+', '+full.brandIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a>';
               }
		     },
	         {
			    "targets": [4, 5, 6, 7]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     }
		     //{
		    	 //"targets": [7],
		    	 //"className": "text-center tl270",
		    	 //'render': function (data, type, full, meta) {
		    		 //if(fnDataTableRenderText(data) == 'Y')
		            	//	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.shopIdx+'" onchange="mainActive(\'' + full.shopIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\');" data-reverse checked>';
		            	//else
		               	//	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.shopIdx+'" onchange="mainActive(\'' + full.shopIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\');" data-reverse>';
		    	 //}
		     //}
	    ];
	   	optObject.arrColumns = [
            {data: "shopIdx"},
            {data: "shopIdx"},
            {data: "logoThumbnailPath"},
            {data: "dispNmKr"},
            {data: "floorNm"},
            {data: "telNum"},
            {data: "detailDispYn"},
            //{data: "activeYn"},
            {data: "modDate"}


        ];

	   	optObject.language = {"search": "브랜드 명"};

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {offLabel: '비활성화', onLabel: '활성화'});
		}
	   	
	   	var floor = $("#searchFloorNm").val();
	   	
		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "floorNm", "value":  floor} );
            aoData.push( { "name": "defaultOrderName"   , "value": "shopIdx" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }
		
		var mTable = commonPagingDateTable(optObject);
		FloorList(floor);
	}
	
	function FloorList(floor) {
		var searchFloor = '';

		searchFloor += '<label style="margin-right : 10px;">층<select name="mTable_length" id="searchFloorNm" onchange="List();" aria-controls="mTable" class="form-control input-sm">';
		searchFloor += '<option value="" >전체</option>';

		var data = $.ajaxUtil.ajaxDefault('<c:url value="/Shop/FloorList"/>', {domainIdx : $('#domainIdx').val(), brandIdx:$('#brandIdx').val()}).data;
		for (var i = 0; i < data.length; i++) {
			if (floor == data[i].floorNm) {
				searchFloor += '<option value="'+data[i].floorNm+'" selected >'+data[i].floorNm+'</option>';
			}
			else {
				searchFloor += '<option value="'+data[i].floorNm+'" >'+data[i].floorNm+'</option>';
			}
			
		}
		
		searchFloor += '</select> </label>&nbsp;&nbsp;';
		
		$("#shopTable_filter").prepend(searchFloor);
	}
	
	function Add() {
		$.sessionCheck();
		var param = {
				shopIdx : 0, domainIdx : $('#domainIdx').val(), brandIdx:$('#brandIdx').val()
		}
        $.modalCommon.loadFullDataView('매장 등록', '<c:url value="/Shop/Form"/>' , param);
	}
	
	function Edit(shop, domain, brand) {
		$.sessionCheck();
		var param = {
				shopIdx : shop, domainIdx : $('#domainIdx').val(), brandIdx:$('#brandIdx').val()
		}
        $.modalCommon.loadFullDataView('매장 상세', '<c:url value="/Shop/Form"/>' , param);
	}
	
	function Delete() {
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('checkboxArr');

	    var callBack = function (result) {
	    	if (result) {
                var param = { checkboxArr: arr, domainIdx : $("#domainIdx").val(), brandIdx : $("#brandIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Shop/Delete"/>', param);

                if (dResult.resultCode == 0 ) {
                	List();
					$.modalCommon.alertView('처리 되었습니다.');
				}
            }
        }
		commonDelete(arr, callBack);
	}
	
	function mainActive(shopIdx, domainIdx, brandIdx){
		commonActive(shopIdx, { domainIdx: domainIdx, brandIdx: brandIdx, shopIdx: shopIdx}, '<c:url value="/Shop/ActiveYn"/>');
	}
	
	
	function callbackTreeSearchKeyEvt(e)
	{
		if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO01');
        	e.keyCode = 0;
		}
	}

	function callbackSelectNodeJstree(data)
	{
		List();
	}

	function callbackFirstSelect(jsonData)
	{
		List();
	}
</script>