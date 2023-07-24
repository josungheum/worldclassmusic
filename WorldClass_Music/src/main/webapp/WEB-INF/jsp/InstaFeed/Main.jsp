<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">Insta Feed 컨텐츠 목록</div>
	</div>
	<div>
		<div class="box-body">
			<table id="instaFeedTable" class="table table-striped">
				<thead>
					<tr>
						<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="totCheckboxArr" /></th>
						<th>No.</th>
						<th>썸네일</th>
						<th style="width:10%;">파일 명</th>
						<th style="width:20%;">인스타 URL</th>
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
		InstaFeedList();
	});

	function InstaFeedList(){
		var optObject = {};
		

		optObject.id = "#instaFeedTable";
		optObject.url = '<c:url value="/InstaFeed/InstaFeedList"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{data: "instaFeedIdx"},
            {data: "instaFeedIdx"},
            {data: "thumbnailPath"},
            {data: "fileName"},
            {data: "instaUrl"},
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
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                   	return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 4,
                'className': 'text-center tl270',
                'display': 'display',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }

            },
            {
                'targets': 5,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 6,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	 if(fnDataTableRenderText(data) == 'Y'){
		            	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.instaFeedIdx+'" onchange="Active(\'' + full.instaFeedIdx + '\',\''+ full.instaFeedIdx + '\');" data-reverse checked>';
	               	}
		            else{
		               	return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.instaFeedIdx+'" onchange="Active(\'' + full.instaFeedIdx + '\',\''+ full.instaFeedIdx + '\');" data-reverse>';
		            }
                }
            },
            {
                'targets': 7,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            }
        ];
		
		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: 'OFF',	onLabel: 'ON'});
		}
		optObject.searching = false;
		optObject.serverParams = function ( aoData ) {
            aoData.push( { "name": "defaultOrderName"   , "value": "orderSeq" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

	    mTable = commonPagingDateTable(optObject);
	}


	function ContentFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG', 'MP4'];

		$.showUploadBox({
			url: "/Upload/View/InstaFeed/",
			width: 400,
			height: 200,
			title: 'InstaFeed컨텐츠 등록',
			ext: ext,
			Finished: contentFileUploadFinishedEH
		});
	}

	function contentFileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		InstaFeedList();
	}

	function Add(val) {
		$.sessionCheck();
        $.modalCommon.loadDataView('컨텐츠 등록', '<c:url value="/InstaFeed/Form"/>' , {contType: val});
	}


	function Delete() {
		console.log("zzz");
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('contentCheckboxArr');

	    if (arr.length == 0) {
	        $.modalCommon.alertView('컨텐츠를 선택하세요.');
	    }
	    else {        
	    	var callBack = function (result) {
		    	if (result) {
	                var param = { checkboxArr: arr};
	                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/InstaFeed/Delete"/>', param);

	                if (dResult.resultCode == 0 ) {
	                	InstaFeedList();
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
	
	function Active(rowIdx, instaFeedIdx){
		commonActive(rowIdx, { instaFeedIdx: instaFeedIdx}, '<c:url value="/InstaFeed/ActiveYn"/>');
	}
	
	function orderChange(val) {
		$.sessionCheck();
        $.modalCommon.loadDataView('순서 변경', '<c:url value="/InstaFeed/SortForm"/>' , {contType: val});
	}
</script>