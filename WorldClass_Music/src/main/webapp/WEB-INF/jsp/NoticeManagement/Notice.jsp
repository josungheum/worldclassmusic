<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
.NoticeHover:hover {
	color:#FF0000;
}
td.text-centerTd {
	text-align: left;
}
th.text-centerTd {
	text-align: center;
}
</style>
<form:form commandName="noticeVo" name="notice" id="notice" method="post">
<form:hidden path="francIdx"/>
<div class="box-body">
	<table id="noticeTable" class="table table-striped">
	    <thead>
	        <tr>
	            <th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
	            <th>No</th>
	            <th>제목</th>
	            <th>시작날짜</th>
	            <th>종료날짜</th>
	            <th>활성화여부</th>
	            <th>작성자</th>
	            <th>수정일</th>
	        </tr>
	    </thead>
	</table>
</div>
<div class="box-footer" id="inputBtn">
	<div class="pull-right">
		<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="noticeAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
		<button type="button" class="btn btn-delete" onclick="noticeDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
	</div>
</div>
</form:form>
<script>
	$(document).ready(function () {
		noticeList();
	});

	function noticeList() {
		var optObject = {};

		optObject.id = "#noticeTable";
		optObject.url = '<c:url value="/NoticeManagement/NoticeList"/>';
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
		    	"targets": 2
		       ,"className": "text-center tl270"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270"><a id="name' + full.index + '" class="NoticeHover" onclick="noticeEdit('+full.noticeIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) +'</a></div>';
               }
		    },
	         {
	        	   'width': '15%',
	               'targets': 5,
	               'data': 'activeyn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="noticeActive(\'' + full.rowIdx + '\',\'' + full.noticeIdx + '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="noticeActive(\'' + full.rowIdx + '\',\'' + full.noticeIdx + '\');" data-reverse>';
	               }
	         },
	         {
			    "targets": [3, 4, 6, 7]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     }
	    ];
	   	optObject.arrColumns = [
	   		{data: "noticeIdx"},
	   		{data: "rowIdx"},
            {data: "title"},
            {data: "startDate"},
            {data: "endDate"},
            {data: "activeYn"},
            {data: "regUser"},
            {data: "modDate"}

        ];

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		optObject.language = {"search": "제목"};

		var mTable = commonPagingDateTable(optObject);
	}
	function noticeAdd() {
		var paramValue = {brandIdx:$("#brandIdx").val(), domainIdx: $("#domainIdx").val(), noticeIdx:0, francIdx:$("#francIdx").val()};
		$.modalCommon.loadFullDataView('공지사항 등록', '<c:url value="/NoticeManagement/NoticeFormNullTemp"/>', paramValue);
	}

	function noticeEdit(val) {
		var paramValue = {brandIdx:$("#brandIdx").val(), domainIdx: $("#domainIdx").val(), noticeIdx:val, francIdx:$("#francIdx").val()};
		$.modalCommon.loadFullDataView('공지사항 수정', '<c:url value="/NoticeManagement/NoticeFormNullTemp"/>', paramValue);
	}

	function noticeDelete() {
		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');

		var callBack = function (result) {
			if (result) {
                var param = {checkboxArr: checkboxArr, brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/NoticeManagement/NoticeDelete"/>', param);
                if (dResult.resultCode == 0){
                	commMessage('D','공지사항');
                	noticeList($('#brandIdx').val());
                }
            }
        }
		commonDelete(checkboxArr, callBack);

	}

	//활성화 공통으로 변경
	function noticeActive(rownum, val)
	{
		commonActive(rownum, { brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val(), noticeIdx: val}, '<c:url value="/NoticeManagement/NoticeActive"/>');
	}


</script>