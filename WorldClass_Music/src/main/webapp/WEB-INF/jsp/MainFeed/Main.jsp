<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">Main Feed 컨텐츠 목록</div>
	</div>
	<div>
		<div class="box-body">
			<table id="mainFeedTable" class="table table-striped">
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
		MainFeedList();
	});
	
	var mTable = "";
	function MainFeedList(){
		if(mTable != "")
			mTable.destroy();
		
		var optObject = {};

		optObject.id = "#mainFeedTable";
		optObject.url = '<c:url value="/MainFeed/MainFeedList"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{data: "mainFeedIdx"},
            {data: "mainFeedIdx"},
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
		            	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.mainFeedIdx+'" onchange="Active(\'' + full.mainFeedIdx + '\',\''+ full.mainFeedIdx + '\');" data-reverse checked>';
	               	}
		            else{
		               	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.mainFeedIdx+'" onchange="Active(\'' + full.mainFeedIdx + '\',\''+ full.mainFeedIdx + '\');" data-reverse>';
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
		optObject.language = {"search": "MainFeed 명  "}
		optObject.serverParams = function ( aoData ) {
            aoData.push( { "name": "defaultOrderName"   , "value": "orderSeq" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

	    mTable = commonPagingDateTable(optObject);
	}


	function ContentFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG', 'MP4'];

		$.showUploadBox({
			url: "/Upload/View/MainFeed/",
			width: 400,
			height: 200,
			title: 'MainFeed컨텐츠 등록',
			ext: ext,
			Finished: contentFileUploadFinishedEH
		});
	}

	function contentFileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		MainFeedList();
	}

	function Add(val) {
		$.sessionCheck();
        $.modalCommon.loadDataView('컨텐츠 등록', '<c:url value="/MainFeed/Form"/>' , {contType: val});
	}
	
	function orderChange(val) {
		$.sessionCheck();
        $.modalCommon.loadDataView('순서 변경', '<c:url value="/MainFeed/SortForm"/>' , {contType: val});
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
	                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/MainFeed/Delete"/>', param);

	                if (dResult.resultCode == 0 ) {
	                	MainFeedList();
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
	
	function Active(rowIdx, mainFeedIdx){
		commonActive(rowIdx, { mainFeedIdx: mainFeedIdx}, '<c:url value="/MainFeed/ActiveYn"/>');
	}
</script>