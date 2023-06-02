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
		<div class="user-block under-line">이벤트/행사 목록</div>
	</div>
	<div class="box-body">
		<table id="eventTable" class="table table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
					<th>No</th>
					<th>썸네일</th>
					<th>이벤트표시명</th>
					<!-- th>이벤트표시명(2열)</th-->
					<th>표시기간</th>
					<th>정렬순서</th>
					<th>활성화</th>
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
<script type="text/javaScript">
	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO01');
		treeBtnHeight("580");

		$(window).resize(function(){
			treeBtnHeight("580");
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

		optObject.id = "#eventTable";
		optObject.url = '<c:url value="/Event/List"/>';
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
		    	 'width' : '10%',
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
			    "targets": [3]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return '<a onclick="Edit('+full.eventIdx+', '+full.domainIdx+', '+full.brandIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a>';
               }
		     },
	         {
			    "targets": [4]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return data + ' ~ ' + full.endDate; 
               }
		     },
	         {
			    "targets": [5, 7]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     },
		     {
		    	 "targets": [6],
		    	 "className": "text-center tl270",
		    	 'render': function (data, type, full, meta) {
		    		 if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.eventIdx+'" onchange="Active(\'' + full.eventIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.eventIdx+'" onchange="Active(\'' + full.eventIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\');" data-reverse>';
		    	 }
		     }
	    ];
	   	optObject.arrColumns = [
            {data: "eventIdx"},
            {data: "eventIdx"},
            {data: "thumbnailPath"},
            {data: "dispNm1"},
            //{data: "dispNm2"},
            {data: "startDate"},
            {data: "orderSeq"},
            {data: "activeYn"},
            {data: "modDate"}


        ];

	   	optObject.language = {"search": "검색"};

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {offLabel: '비활성화', onLabel: '활성화'});
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "orderSeq" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		var mTable = commonPagingDateTable(optObject);
	}
	
	function Add() {
		$.sessionCheck();
		var param = {
				domainIdx : $('#domainIdx').val(), brandIdx:$('#brandIdx').val(),
				eventIdx : 0
		}
        $.modalCommon.loadDataView('이벤트/행사 등록', '<c:url value="/Event/Form"/>' , param);
	}
	
	function Edit(eventIdx, domainIdx, brandIdx) {
		$.sessionCheck();
		var param = {
				domainIdx : domainIdx, brandIdx : brandIdx,
				eventIdx : eventIdx
		}
        $.modalCommon.loadDataView('이벤트/행사 상세', '<c:url value="/Event/Form"/>' , param);
	}
	
	function Active(eventIdx, domainIdx, brandIdx){
		commonActive(eventIdx, { domainIdx: domainIdx, brandIdx: brandIdx, eventIdx: eventIdx}, '<c:url value="/Event/ActiveYn"/>');
	}
	
	function Delete() {
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('checkboxArr');

	    var callBack = function (result) {
	    	if (result) {
                var param = { checkboxArr: arr, domainIdx : $("#domainIdx").val(), brandIdx : $("#brandIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Event/Delete"/>', param);

                if (dResult.resultCode == 0 ) {
                	List();
					$.modalCommon.alertView('처리 되었습니다.');
				}
            }
        }
		commonDelete(arr, callBack);
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