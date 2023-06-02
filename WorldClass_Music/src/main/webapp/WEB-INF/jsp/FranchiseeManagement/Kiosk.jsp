<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.FileNameHover:hover {
	color:#FF0000;
}
</style>
<form:form commandName="franchiseeVo" name="kiosk" id="kiosk" method="post">
	<div class="box-body">
		 <table id="mTable" class="table table-striped">
             <thead>
                 <tr>
                     <th><input type="checkbox" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" name="ARRIDX_ALL"/></th>
                     <th>No</th>
                     <th>단말 코드</th>
                     <th>단말 명</th>
                     <th>단말 종류</th>
                     <th>설치일</th>
                     <th>활성화</th>
                 </tr>
             </thead>
         </table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="kioskAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="kioskDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</form:form>
<script>
	$(document).ready(function () {
		kioskList();

	});

	function kioskList() {
		var optObject = {};

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/FranchiseeManagement/KioskList"/>';
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
			       ,"className": "text-center"
			       ,"orderable": false
			       ,'render': function (data, type, full, meta) {
		               	return dataTableRowIdx(meta);
		            }
			 },
	         {
		    	"targets": [3]
		       ,"className": "text-center tl270"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270"><a class="FileNameHover" onclick="kioskEdit('+full.deviceIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) +'</a></div>';
               }
		     },
	         {
			    "targets": [2,4,5]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		     },
		     {
	        	   'width': '15%',
	               'targets': 6,
	               'data': 'activeyn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y')
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="kioskActive(\'' + full.rowIdx + '\',\'' + full.deviceIdx + '\');" data-reverse checked>';
		            	else
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="kioskActive(\'' + full.rowIdx + '\',\'' + full.deviceIdx + '\');" data-reverse>';
	               }
	         }
	    ];
	   	optObject.arrColumns = [
	   		{data: "deviceIdx"},
	   		{data: "rowIdx"},
            {data: "deviceCode"},
            {data: "deviceName"},
            {data: "deviceType"},
            {data: "regDate"},
            {data: "activeYn"}

        ];

	   	optObject.language = {"search": "단말 명"};

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "deviceIdx", "value": $("#deviceIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "groupIdx", "value": $("#groupIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "regDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}
        var mTable = commonPagingDateTable(optObject)
	}

	function kioskAdd() {
		$.sessionCheck();
	    $.modalCommon.loadDataView('단말 등록', '<c:url value="/FranchiseeManagement/KioskFormNullTemp"/>' , {brandIdx:$('#brandIdx').val(),francIdx:$('#francIdx').val(), deviceIdx:0});
	}

	function kioskEdit(val) {
		$.sessionCheck();
		$.modalCommon.loadDataView('단말 상세보기', '<c:url value="/FranchiseeManagement/KioskFormNullTemp"/>' , {brandIdx:$('#brandIdx').val(), francIdx:$('#francIdx').val(), deviceIdx:val});
	}

	function kioskDelete() {
		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');

		var callBack = function (result) {
			 if (result) {
                 var param = { checkboxArr: checkboxArr, brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val()};
                 var dResult = $.ajaxUtil.ajaxArray('<c:url value="/FranchiseeManagement/KioskDelete"/>', param);
					if (dResult.resultCode == 0){
						commMessage('D','단말');
                 	 	kioskList();
                 }
             }
        }
		commonDelete(checkboxArr, callBack);

	}

	//활성화 공통으로 변경
	function kioskActive(rownum, val)
	{
		commonActive(rownum, { brandIdx: $("#brandIdx").val(), francIdx: $("#francIdx").val(), deviceIdx: val}, '<c:url value="/FranchiseeManagement/KioskActive"/>');
	}


</script>