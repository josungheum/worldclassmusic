<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">Main Video 목록</div>
	</div>
	<div>
		<div class="box-body">
			<table id="mainVideoTable" class="table table-striped">
				<thead>
					<tr>
						<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="totCheckboxArr" /></th>
						<th>No.</th>
						<th>썸네일</th>
						<th>컨텐츠 명</th>
						<th>순서</th>
						<th>활성화</th>
						<th>최종 수정일</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="box-footer" id="inputBtn">
			<div class="pull-right">
				<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="orderChange();" id="contAddBtn"><i class="glyphicon mr-5"></i>순서 변경</button>
				<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="ContentFileAdd();" id="contAddBtn"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
				<button type="button" class="btn btn-delete" onclick="Delete()" id="contDelBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		MainVideoList();
	});

	function MainVideoList(){
		var optObject = {};

		optObject.id = "#mainVideoTable";
		optObject.url = '<c:url value="/MainVideo/MainVideoList"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{data: "mainVideoIdx"},
            {data: "mainVideoIdx"},
            {data: "thumbnailPath"},
            {data: "fileName"},
            {data: "orderSeq"},
            {data: "activeYn"},
            {data: "modDate"}
        ];


		optObject.arrColumnDefs = [
			{
                'targets': 0,
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
               		return '<input type="checkbox" value="' + data + '" id="contentCheckboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'contentCheckboxArr\');"/>';
                }
            },
            {
                'targets': 1,
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                   	return dataTableRowIdx(meta);
                }
            },
            {
                'targets': 2,
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	var html = '';
                	if(data){
                		html="<img style='width:120px;height:120px;' src='"+data+"'>"
                	}
                   	return html;
                }
            },
            {
                'targets': 3,
                'className': 'text-center tl270',
                'display': 'display',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }

            },
            {
                'targets': 4,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 5,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	 if(fnDataTableRenderText(data) == 'Y'){
		            	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.mainVideoIdx+'" onchange="Active(\'' + full.mainVideoIdx + '\',\''+ full.mainVideoIdx + '\');" data-reverse checked>';
	               	}
		            else{
		               	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.mainVideoIdx+'" onchange="Active(\'' + full.mainVideoIdx + '\',\''+ full.mainVideoIdx + '\');" data-reverse>';
		            }
                }
            },
            {
                'targets': 6,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            }
        ];
		
		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: 'OFF',	onLabel: 'ON'});
		}
		optObject.searching = true;
		optObject.language = {"search": "MainVideo 명  "}
		optObject.serverParams = function ( aoData ) {
            aoData.push( { "name": "defaultOrderName"   , "value": "orderSeq" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

	    mTable = commonPagingDateTable(optObject);
	}


	function ContentFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG', 'MP4'];

		$.showUploadBox({
			url: "/Upload/View/MainVideo/",
			width: 400,
			height: 200,
			title: 'MainVideo컨텐츠 등록',
			ext: ext,
			Finished: contentFileUploadFinishedEH
		});
	}

	function contentFileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		MainVideoList();
	}

	function Add(val) {
		$.sessionCheck();
        $.modalCommon.loadDataView('컨텐츠 등록', '<c:url value="/MainVideo/Form"/>' , {contType: val});
	}


	function Delete() {
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('contentCheckboxArr');

	    if (arr.length == 0) {
	        $.modalCommon.alertView('컨텐츠를 선택하세요.');
	    }
	    else {        
	    	var callBack = function (result) {
		    	if (result) {
	                var param = { checkboxArr: arr};
	                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/MainVideo/Delete"/>', param);

	                if (dResult.resultCode == 0 ) {
	                	MainVideoList();
						$.modalCommon.alertView('처리 되었습니다.');
					} else {
						$.modalCommon.close();
						$.modalCommon.alertView('처리 되지 않았습니다.');
					}
	            }
	        }
			commonDelete(arr, callBack);
	    }
	}
	
	function Active(rowIdx, mainVideoIdx){
		commonActive(rowIdx, { mainVideoIdx: mainVideoIdx}, '<c:url value="/MainVideo/ActiveYn"/>');
	}
	
	function orderChange(val) {
		$.sessionCheck();
        $.modalCommon.loadDataView('순서 변경', '<c:url value="/MainVideo/SortForm"/>' , {contType: val});
	}
</script>