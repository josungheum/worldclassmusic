<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<input type="hidden" id="contDomainIdx">
<input type="hidden" id="contGroupIdx">
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">컨텐츠 목록</div>
	</div>
	<div>
		<div class="box-body">
			<table id="groupTable" class="table table-striped">
				<thead>
					<tr>
						<th><input type="checkbox" name="GROUPARRIDX_ALL" class="checkBox" onclick="$.checkboxUtil.checkAll(this, 'groupCheckboxArr');" id="totGroupCheckboxArr" /></th>
						<th>No.</th>
						<th>폴더 명</th>
						<th>하위 폴더 수</th>
						<th>파일 수</th>
						<th>수정일</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="box-footer" id="inputBtn">
			<div class="pull-right">
				<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="GroupAdd();" id="groupAddBtn"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
				<button type="button" class="btn btn-delete" onclick="GroupDelete()" id="groupDelBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
			</div>
		</div>
	</div>
	<div style="margin-top:10px;">
		<div class="box-body">
			<table id="contentTable" class="table table-striped">
				<thead>
					<tr>
						<th><input type="checkbox" name="CONTENTARRIDX_ALL" class="checkBox" onclick="$.checkboxUtil.checkAll(this, 'contentCheckboxArr');" id="totContentCheckboxArr" /></th>
						<th>No.</th>
						<th>썸네일</th>
						<th>컨텐츠 명</th>
						<th>종류</th>
						<th>크기</th>
						<th>재생시간</th>
						<th>등록일</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="box-footer" id="inputBtn">
			<div class="pull-right">
				<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="ContentFileAdd();" id="contAddBtn"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
				<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="Add('U');" id="urlAddBtn"><i class="glyphicon glyphicon-plus mr-5"></i>URL 등록</button>
				<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="Add('S');" id="streamAddBtn"><i class="glyphicon glyphicon-plus mr-5"></i>스트리밍 등록</button>
				<button type="button" class="btn btn-delete" onclick="Delete()" id="contDelBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		contentTreeSearchList(0, false, null, 'CONTENT');
	    treeHeight();
		btnDisabled();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });

	});

	function btnDisabled() {
		var domainIdx = $('#contDomainIdx').val();
		var groupIdx = $('#contGroupIdx').val();
		$('.checkBox').removeAttr('disabled');
		
		if(domainIdx == 0){
			$('#groupAddBtn').attr('disabled', 'disabled');
     		$('#groupDelBtn').attr('disabled', 'disabled');
     	}
		else{
			$('#groupAddBtn').removeAttr('disabled');
			$('#groupDelBtn').removeAttr('disabled');
		}
		$('#contAddBtn').removeAttr('disabled');
		$('#urlAddBtn').removeAttr('disabled');
		$('#streamAddBtn').removeAttr('disabled');
		$('#contDelBtn').removeAttr('disabled');
			
		/* if(groupIdx == 0){
			$('#contAddBtn').attr('disabled', 'disabled');
			$('#urlAddBtn').attr('disabled', 'disabled');
			$('#streamAddBtn').attr('disabled', 'disabled');
     		$('#contDelBtn').attr('disabled', 'disabled');
     	}
		else{
			$('#contAddBtn').removeAttr('disabled');
			$('#urlAddBtn').removeAttr('disabled');
			$('#streamAddBtn').removeAttr('disabled');
			$('#contDelBtn').removeAttr('disabled');
		} */
	}

	//노드가 선택될 경우
	function callbackContentSelectNodeJstree(data){
		$('#contDomainIdx').val(data.node.li_attr.domainidx);
        $('#contGroupIdx').val(data.node.li_attr.groupidx);

    	GroupList(data.node.li_attr.domainidx, data.node.li_attr.groupidx);
    	ContentList(data.node.li_attr.domainidx, data.node.li_attr.groupidx);
        btnDisabled();
	}

	// 	트리 첫번째 조회 콜백
	function callbackcontentFirstSelect(jsonData)
	{
		GroupList($('#contDomainIdx').val(), $('#contGroupIdx').val());
		ContentList($('#contDomainIdx').val(), $('#contGroupIdx').val());
	}

	function GroupList(domainIdx, groupIdx){
		var optObject = {};
		var domainIdx = $('#contDomainIdx').val();
		var groupIdx = $('#contGroupIdx').val();

		optObject.id = "#groupTable";
		optObject.url = '<c:url value="/Contents/GroupList"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{data: "groupIdx"},
            {data: "rowIdx"},
            {data: "groupName"},
            {data: "groupCnt"},
            {data: "fileCnt"},
            {data: "modDate"}
        ];


		optObject.arrColumnDefs = [
			{
                'targets': 0,
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
               		return '<input type="checkbox" value="' + data + '" id="groupCheckboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'groupCheckboxArr\');"/>';
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
                'className': 'text-center tl270',
                'display': 'display',
                'render': function (data, type, full, meta) {
                	return '<div class="ellipsis270">'+'<a id="name' + full.groupIdx + '" class="FileNameHover" onclick="GroupEdit('+full.groupIdx+', '+full.domainIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a>'+'</div>';
                }

            },
            {
                'targets': [3,4,5],
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            }
        ];
		optObject.searching = false;
		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "DOMAIN_IDX", "value": domainIdx } );
            aoData.push( { "name": "GROUP_IDX", "value": groupIdx } );
            aoData.push( { "name": "defaultOrderName"   , "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		//optObject.language = {"search": "스크린 명"};

	    mTable = commonPagingDateTable(optObject);
	}

	function GroupAdd() {
		$.sessionCheck();
        $.modalCommon.loadDataView('폴더 등록', '<c:url value="/Contents/GroupForm"/>' , {groupIdx:0});
	}

	function GroupEdit(val,domainIdx) {
		$.sessionCheck();
        $.modalCommon.loadDataView('폴더 수정', '<c:url value="/Contents/GroupForm"/>' , {groupIdx:val});
	}

	function GroupDelete() {
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('groupCheckboxArr');

	    if (arr.length == 0) {
	        $.modalCommon.alertView('그룹을 선택하세요.');
	    }
	    else {        
	    	var callBack = function (result) {
		    	if (result) {
	                var param = { checkboxArr: arr};
	                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Contents/DeleteGroup"/>', param);

	                if (dResult.resultCode == 0 ) {
	            		contentTreeSearchList(1, false, null, 'CONTENT');
	            		if($('#contGroupIdx').val() > 0){
							$('#'+$('#contDomainIdx').val()+'_'+$('#contGroupIdx').val()+'_anchor').click();
	            		}
	            		else {
	            			$('#'+$('#contDomainIdx').val()+'_anchor').click();
	            		}
	                	$.checkboxUtil.checkAllFalse();
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
	

	function ContentList(domainIdx, groupIdx){
		var optObject = {};
		var domainIdx = $('#contDomainIdx').val();
		var groupIdx = $('#contGroupIdx').val();

		optObject.id = "#contentTable";
		optObject.url = '<c:url value="/Contents/ContentList"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{data: "fileContentIdx"},
            {data: "rowIdx"},
            {data: "thumbnailPath"},
            {data: "fileName"},
            {data: "extens"},
            {data: "fileSize"},
            {data: "playTime"},
            {data: "regDt"}
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
                	return '<div class="ellipsis270">'+ fnDataTableRenderText(data) + '</div>';
                }

            },
            {
                'targets': 4,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	if(full.contType == "U"){
                		data = "URL";
                	}
                	else if(full.contType == "S"){
                		data = "스트리밍";
                	}
                	
                	return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 5,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	var bytes = parseInt(data);

                	var s = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];

                	var e = Math.floor(Math.log(bytes)/Math.log(1024));

                	if(e == "-Infinity") return "0 "+s[0]; 

                	else return (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];

                }
            },
            {
                'targets': 6,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	var time = new Date(0, 0, 0, 0, 0, 0, fnDataTableRenderText(data));
            		var hour = time.getHours().toString().length > 1 ? time.getHours().toString() : "0" + time.getHours().toString();
            		var min = time.getMinutes().toString().length > 1 ? time.getMinutes().toString() : "0" + time.getMinutes().toString();
            		var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();
                    return hour + ':' + min + ':' + sec;
                    
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
		optObject.searching = false;
		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "DOMAIN_IDX", "value": domainIdx } );
            aoData.push( { "name": "GROUP_IDX", "value": groupIdx } );
            aoData.push( { "name": "defaultOrderName"   , "value": "regDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		//optObject.language = {"search": "스크린 명"};

	    mTable = commonPagingDateTable(optObject);
	}


	function ContentFileAdd(){
		var ext = ['PNG', 'JPEG', 'JPG', 'MP4'];

		$.showUploadBox({
			url: "/Upload/View/CommonContent/",
			width: 400,
			height: 200,
			title: '컨텐츠 등록',
			ext: ext,
			Finished: contentFileUploadFinishedEH
		});
	}

	function contentFileUploadFinishedEH(data) {
		var jsonData = data.length > 0 ? data[0]:null;
		var domainIdx = $('#contDomainIdx').val();
		var groupIdx = $('#contGroupIdx').val();
		ContentList(domainIdx, groupIdx);
	}

	function Add(val) {
		$.sessionCheck();
		var str = (val == 'U' ? 'Url' : '스트리밍');
        $.modalCommon.loadDataView(str+' 컨텐츠 등록', '<c:url value="/Contents/Form"/>' , {contType: val});
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
	                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Contents/Delete"/>', param);

	                if (dResult.resultCode == 0 ) {
	            		contentTreeSearchList(1, false, null, 'CONTENT');
	            		if($('#contGroupIdx').val() > 0){
							$('#'+$('#contDomainIdx').val()+'_'+$('#contGroupIdx').val()+'_anchor').click();
	            		}
	            		else {
	            			$('#'+$('#contDomainIdx').val()+'_anchor').click();
	            		}
	                	$.checkboxUtil.checkAllFalse();
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
</script>