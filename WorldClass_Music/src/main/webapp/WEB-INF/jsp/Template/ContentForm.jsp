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
		<button type="button" class="btn btn-primary" onclick="ContentFileAdd()" id="procBtn">업로드</button>
		<button type="button" class="btn btn-primary" onclick="addContent()" id="procBtn">추가</button>
		<button type="button" class="btn btn-default contentmodal notDisabled" data-dismiss="modal">닫기</button>
	</div>
</div>
<script type="text/javaScript">
$(document).ready(function () {
	addTableSelected();
	$('.modal-dialog').css('width', '1400px');
	contentTreeSearchList(0, false, null, 'FORM');
});

function addTableSelected() {
    var table = $('#contentTable').DataTable();

    $('#contentTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
        	$(this).addClass('selected');
        }
    });
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
            		return (meta.row+1)+'<input type="hidden" value="' + data + '"><input type="hidden" value="'+full.savePath+'"><input type="hidden" value="'+full.thumbnailPath+'">';
            	}
            },
            {
		    	"targets": 1
		       ,'data': 'fileName'
		       ,"className": "text-center fileName"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return fnDataTableRenderText(data);
               }
		    },
            {
                'targets': 2,
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
                'targets': 3,
 		        'data': 'fileSize',
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
                'targets': 4,
 		        'data': 'playTime',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return data/1000;
                    
                }
            },
            {
                'targets': 5,
                'width' : '10%',
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

function addContent(){
	var selectData = [];
	var ctTr = $('#screenFind').find('table[id="contentTable"]').find('tbody').find('tr.selected');


	for (var i = 0; i < ctTr.length; i++) {
		var parent = $(ctTr[i]);
		var data = $('#contentTable').DataTable().rows().data();
		for(var j = 0; j < data.length; j++){
			if($(ctTr[i].children[0]).find('input').val() == data[j].fileContentIdx){
				console.log(data[j]);
				console.log(this);
				selectData.push({fileContentIdx:data[j].fileContentIdx, fileName:data[j].fileName, 
					playTime:data[j].playTime, savePath : data[j].savePath, thumbnailPath : data[j].thumbnailPath});
			}
		}
	}

	if (selectData.length == 0) {
		$.modalCommon.alertView('컨텐츠을 선택해주세요.');
		return;
	}

	$.fn.AddContentList(selectData);
	
	$(" .contentmodal").click();
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
</script>