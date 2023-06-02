<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
	#screenTable.dataTable tbody tr.selected { background-color: #B2CCFF; }
	#operationTable.dataTable tbody tr.selected { background-color: #B2CCFF; }
	#contentTable.dataTable tbody tr.selected { background-color: #B2CCFF; }
	#screenTable_wrapper, #operationTable_wrapper, #contents_wrapper { border-top: 1px solid rgb(154, 162, 173); padding: 20px; }
	.nav-pills > li > a { border-radius: 0px !important; }
</style>

<div class="modal-body">
	<div class="box-body" style="border-bottom-style: solid !important; border-bottom:2px; border-bottom-color: #d2d6de;" id="screenFind">
		<div class="row nav-tabs" id="tabLab" style="margin: 0px;">
				<ul class="nav nav-pills" id="tabList">
					<li class="active">
						<a onclick="screenView('screenTable')" data-toggle="tab" style="cursor:pointer;">스크린</a>
					</li>
					<li>
						<a onclick="screenView('operationTable')" data-toggle="tab" style="cursor:pointer;">운영</a>
					</li>
					<li>
						<a onclick="screenView('contents')" data-toggle="tab" style="cursor:pointer;">컨텐츠</a>
					</li>
				</ul>
			</div>
		
		<table id="screenTable" class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>스크린명</th>
					<th>재생시간(초)</th>
				</tr>
			</thead>
		</table>
		
		<table id="operationTable" class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>No</th>
					<th>운영템플릿명</th>
					<th>재생시간(초)</th>
				</tr>
			</thead>
		</table>
		<div id="contents_wrapper">
		<input type="hidden" id="contDomainIdx">
		<input type="hidden" id="contGroupIdx">
			<div class="col-sm-4">
				<label>폴더 목록</label>
				<div style=" border-color:#d2d6de; border-style: solid; margin-top:4px;  overflow-x: hidden; overflow-y: auto; min-height: 45vh;" class="proton-demo" id="formtreeList" ></div>
			</div>
			<div class="col-sm-8" style="margin-top: 28px; padding-left: 0px; padding-right: 0px;">
				<table id="contentTable" class="table table-bordered table-striped">
					<thead>
						<tr>
							<th>No</th>
							<th>썸네일</th>
							<th>컨텐츠명</th>
							<th>종류</th>
							<th>크기</th>
							<th>재생시간(초)</th>
							<th>생성일</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="addScreen()" id="procBtn">추가</button>
		<button type="button" class="btn btn-default screenmodal notDisabled" data-dismiss="modal">닫기</button>
	</div>
</div>
<script type="text/javaScript">
$(document).ready(function () {
	$('.modal-dialog').css('width', '1200px');
	screenList();
	addTableSelected();
	//운영은 조회조건 우선 제외
	$("#operationTable_filter").hide();
	screenView("screenTable");
	contentTreeSearchList(0, false, null, 'FORM');
});

function screenView(selectedView){
	if(selectedView == "screenTable"){
		noSelectedView = "operationTable";
		
		$("#operationTable_wrapper").hide();
		$("#contents_wrapper").hide();
	}
	else if(selectedView == "contents"){

		$("#operationTable_wrapper").hide();
		$("#screenTable_wrapper").hide();
	}
	else
	{
		$("#screenTable_wrapper").hide();
		$("#contents_wrapper").hide();
	} 
	$("#"+selectedView+"_wrapper").show();
}

function addTableSelected() {
	var table = $('#screenTable').DataTable();

    $('#screenTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
        	$(this).addClass('selected');
        }
    });
    
    var table2 = $('#operationTable').DataTable();

    $('#operationTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
        	$(this).addClass('selected');
        }
    });

    var table2 = $('#contentTable').DataTable();

    $('#contentTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
        	$(this).addClass('selected');
        }
    });
}

function screenList(){
	var param = {domainIdx: $('#user #domainIdx').val(), brandIdx:$('#user #brandIdx').val(), francIdx:$('#user #francIdx').val()}
	var screenJsonData = $.ajaxUtil.ajaxDefault('<c:url value="/Schedule/ScreenList"/>', param);
	var operationData = $.ajaxUtil.ajaxDefault('<c:url value="/Schedule/OperationList"/>', param);
	

	var mTable = $('#screenTable').DataTable({
        data: screenJsonData.data,
        destroy: true,
        responsive: true,
        searching: true,
        bPaginate: false,
        language: {"search": "스크린 명"},
        bInfo: false,
        ordering: false,
        scrollY: '35vh',
        columnDefs: [
            {
            	'width': '5%',
                'targets': 0,
                'data': 'screenIdx',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return data+'<input type="hidden" value="' + full.screenIdx + '">';;
            	}
            },
            {
		    	"targets": 1
		       ,'data': 'screenName'
		       ,"className": "text-center tl270 screenName"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
            {
            	'width': '25%',
                'targets': 2,
                'data': 'playTime',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                    return data/1000;
                }
            }
        ]
    });
	
	var mTable2 = $('#operationTable').DataTable({
        data: operationData.data,
        destroy: true,
        responsive: true,
        searching: true,
        bPaginate: false,
        language: {"search": "운영 템플릿 명"},
        bInfo: false,
        ordering: false,
        scrollY: '35vh',
        columnDefs: [
            {
            	'width': '5%',
                'targets': 0,
                'data': 'screenIdx',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
            		return data+'<input type="hidden" value="' + full.screenIdx + '">';;
            	}
            },
            {
		    	"targets": 1
		       ,'data': 'screenName'
		       ,"className": "text-center tl270 screenName"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
            {
            	'width': '25%',
                'targets': 2,
                'data': 'playTime',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                    return data/1000;
                }
            }
        ]
    });
	
	$('#screenTable_filter > leabel > .form-control.input-sm').on( 'keyup', function () {
		mTable.column('.screenName').search( this.value );
		mTable.draw();
	} );
}


//노드가 선택될 경우
function callbackContentSelectNodeJstree(data){
	$('#contDomainIdx').val(data.node.li_attr.domainidx);
    $('#contGroupIdx').val(data.node.li_attr.groupidx);

	ContentList(data.node.li_attr.domainidx, data.node.li_attr.groupidx);
}

// 	트리 첫번째 조회 콜백
function callbackcontentFirstSelect(jsonData)
{
	ContentList($('#contDomainIdx').val(), $('#contGroupIdx').val());
}


function ContentList(domainIdx, groupIdx){
	var param = {domainIdx: $('#contDomainIdx').val(), groupIdx:$('#contGroupIdx').val()}
	var contentData = $.ajaxUtil.ajaxDefault('<c:url value="/Schedule/ContentList"/>', param);
	
	var mTable3 = $('#contentTable').DataTable({
        data: contentData.data,
        destroy: true,
        responsive: true,
        searching: true,
        bPaginate: false,
        language: {"search": "컨텐츠 명"},
        bInfo: false,
        ordering: false,
        scrollY: '35vh',
        columnDefs: [
            {
            	'width': '5%',
                'targets': 0,
                'data': 'fileContentIdx',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
            		return (meta.row+1)+'<input type="hidden" value="' + data + '">';;
            	}
            },
            {
                'targets': 1,
                'data': 'thumbnailPath',
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	var html = '';
                	if(data){
                		//html="<img style='max-width: 110px; height:100px; padding: 10px 0px;' src='"+"/"+ pName + "/" +data+"'>"
                		html="<img style='max-width: 110px; height:100px; padding: 10px 0px;' src='" + data + "'>";
                	}
                   	return html;
                }
            },
            {
		    	"targets": 2
		       ,'data': 'fileName'
		       ,"className": "text-center fileName"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return fnDataTableRenderText(data);
               }
		    },
            {
                'targets': 3,
 		        'data': 'extens',
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
                'targets': 4,
 		        'data': 'fileSize',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	var bytes = parseInt(data);

                	var s = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];

                	var e = Math.floor(Math.log(bytes)/Math.log(1024));

                	if(e == "-Infinity") {
                		return "0 "+s[0];
                	} 
                	else {
                		var time = (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];
                		return time.split('.')[0];
                	}

                }
            },
            {
                'targets': 5,
 		        'data': 'playTime',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return data/1000;
                    
                }
            },
            {
                'targets': 6,
 		        'data': 'regDt',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            }
        ]
    });
	$('.dataTables_scrollHeadInner').css('width','100%');
	$('.table-bordered').css('width','100%');
	$('#contentTable_filter > leabel > .form-control.input-sm').on( 'keyup', function () {
		mTable3.column('.fileName').search( this.value );
		mTable3.draw();
	} );
}
</script>