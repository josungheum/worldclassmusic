<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">제어 스케줄 목록</div>
	</div>
	<div class="box-body">
		<table id="mTable" class="table table-striped">
			<thead>

				<tr>
					<th rowspan="2"><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="AllCheckboxArr" /></th>
                    <th rowspan="2">No</th>
                    <th rowspan="2">제어 스케줄 명</th>
                    <th rowspan="2">시작일</th>
                    <th rowspan="2">종료일</th>
                    <th rowspan="2">시작시간</th>
                    <th class="text-center" colspan="7">요일</th>
                    <th rowspan="2" width="10%">등록일</th>
                    <th rowspan="2" width="10%">활성화</th>
                 </tr>
                 <tr>
                 <th class="text-danger">일</th>
                 <th>월</th>
                 <th>화</th>
                 <th>수</th>
                 <th>목</th>
                 <th>금</th>
                 <th class="text-success">토</th>
                </tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<form:form commandName="ControlScheduleVo" name="user" id="user" method="post">
				<form:hidden path="domainIdx" />
				<form:hidden path="brandIdx" />
				<form:hidden path="francIdx" />
			</form:form>
			<button type="button" id="addScheduleBtn" class="btn btn-primary2" data-toggle="modal" onclick="Add();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" id="delScheduleBtn" class="btn btn-delete" onclick="Delete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>


<input type="hidden" id="SM_PINDEX" value="">
<script type="text/javaScript">
	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO02');
		treeHeight();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });
	});

	//트리 클릭 이벤트
	function callbackTreeSearchKeyEvt(e)
	{
        if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO01');
        	e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data){
		$('#user #domainIdx').val(data.node.li_attr.domainidx);
        $('#user #brandIdx').val(data.node.li_attr.brandidx);
        $('#user #francIdx').val(data.node.li_attr.francidx);

    	scheduleList();
//         btnDisabled();
	}

	// 	트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData)
	{
		scheduleList();
	}

	function Delete() {
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('checkboxArr');

	    var callBack = function (result) {
	    	if (result) {
                var param = { checkboxArr: arr, domainIdx : $("#user #domainIdx").val(), brandIdx : $("#user #brandIdx").val(), francIdx : $("#user #francIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/ControlSchedule/ScheduleDelete"/>', param);

                if (dResult.resultCode == 0 ) {
                	scheduleList();
				}
            }
        }
		commonDelete(arr, callBack);
	}

	var mTable = "";
	function scheduleList(){
		if(mTable != "")
			mTable.destroy();
		var optObject = {};

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/ControlSchedule/List"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{
				'width': '3%',
	            'targets': 0,
	            "orderable": false,
	            'data': 'controlScheduleIdx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta) {
	        		return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
            	}
			},
			{
            	'width': '3%',
                'targets': 1,
                "orderable": false,
                'data': 'rowIdx',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return dataTableRowIdx(meta)+'<input type="hidden" value="' + full.screenIdx + '">';
                }
            },
			{
                'targets': 2,
                'className': 'text-center tl270',
                'data': 'controlScheduleName',
                'render': function (data, type, full, meta) {
//                 	console.log(full);
                    return '<div class="ellipsis270">'+'<a id="name' + full.controlScheduleIdx + '" class="FileNameHover" onclick="Edit(\'' + full.controlScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a>'+'</div>';
                }
            },
            {
				'width': '120px',
                'targets': 3,
                'data': 'startDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
				'width': '120px',
                'targets': 4,
                'data': 'endDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
					'width': '120px',
	               'targets': 5,
	               'data': 'startTime',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data;
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 6,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[0] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 7,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[1] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 8,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[2] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 9,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[3] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 10,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[4] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 11,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[5] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 12,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[6] == "1" ? "O":"X";
	               }
	         },
	         {
					'width': '120px',
	               'targets': 13,
	               'data': 'regDate',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return fnDataTableRenderText(data);
	               }
	         },
	         {
					'width': '150px',
	        	   'targets': 14,
	               'data': 'activeYn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y'){
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="Active(\'' + full.rowIdx + '\',\''+ full.controlScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse checked>';
	               		}
		            	else{
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.rowIdx+'" onchange="Active(\'' + full.rowIdx + '\',\''+ full.controlScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse>';
		            	}
	               }
	         }
        ];

		optObject.language = {"search": "스케줄 명"};

		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "startDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		mTable = commonPagingDateTable(optObject);
	}

	//활성화 공통으로 변경
	function Active(rowIdx, controlScheduleIdx, domainIdx, brandIdx, francIdx){
		commonActive(rowIdx, { domainIdx: domainIdx, brandIdx: brandIdx, francIdx: francIdx, controlScheduleIdx: controlScheduleIdx}, '<c:url value="/ControlSchedule/ActiveYn"/>');
	}

	function Add(){
		if($('#user #domainIdx').val() == 0 || $('#user #brandIdx').val() == 0 || $('#user #francIdx').val() == 0) {
			$.modalCommon.alertView("사이트 선택 후 등록 가능합니다.");
			return;
		}

	    $.modalCommon.loadFullDataView('<i class="fa fa-fw fa-gear"></i> 제어 스케줄 등록', '<c:url value="/ControlSchedule/ScheduleForm"/>' ,{brandIdx: $('#user #brandIdx').val() , francIdx: $('#user #francIdx').val(), controlScheduleIdx:0});
	}


	function Edit(controlScheduleIdx, domainIdx, brandIdx, francIdx){
	    $.modalCommon.loadFullDataView('<i class="fa fa-fw fa-gear"></i> 제어 스케줄 수정', '<c:url value="/ControlSchedule/ScheduleForm"/>' ,{ brandIdx:brandIdx , francIdx: francIdx, controlScheduleIdx:controlScheduleIdx});
	}

	function ScheduleTree(){
		var param = {
				domainIdx : $("#scheduleForm #domainIdx").val(),
		   		brandIdx : $("#scheduleForm #brandIdx").val(),
		   		francIdx : $("#scheduleForm #francIdx").val(),
		   		controlScheduleIdx : $("#scheduleForm #controlScheduleIdx").val()
		}

		treeSearchList(0,'<c:url value="/ControlSchedule/SearchKioskTree"/>',param);

	}
	function saveSchedule() {
		var scValue = '';

		if(!$.formCheck('#scheduleForm #controlScheduleName', 'Y', 50, 'N', '스케줄명'))
			return;

		if(!$.formCheck('#scheduleForm #startDate', 'Y', 50, 'N', '시작일'))
			return;

		if(!$.formCheck('#scheduleForm #endDate', 'Y', 50, 'N', '종료일'))
			return;

		var startDate = new Date($('#scheduleForm #startDate').val());
		var endDate = new Date($('#scheduleForm #endDate').val());

		if(startDate > endDate){
			$.modalCommon.alertView("종료일이 시작일보다 빠를 수 없습니다.", null, null, null);
			return;
		}

		if(!$.formCheck('#scheduleForm #startTime', 'Y', 50, 'N', '시간'))
			return;

		var weekArr = [];
		$("input[name=dayofweekCheckBox]:checked").each(function() {
			weekArr.push(Number($(this).val()));
		});

		if(weekArr.length == 0){
			$.modalCommon.alertView("요일을 선택해주세요.");
			return;
		}

		var dayOfWeek = "";

		for(var i = 0; i < 7; i++){
			if(weekArr.indexOf(i)!== -1)
				dayOfWeek += "1";
			else
				dayOfWeek += "0";
		}

		if ($('#activeYn').prop('checked')) {
			scValue = 'Y';
		} else {
			scValue = 'N';
		}



		// 체크 된 디바이스 확인
		var deviceList = [];
		$.each($('#targetTreeList').jstree('get_checked', true), function ()
		{
			if(this.li_attr.deviceidx != null && this.li_attr.deviceidx != 0)
				deviceList.push(this.li_attr.deviceidx);
	    });

		if(deviceList.length == 0){
			$.modalCommon.alertView("1개 이상의 단말이 선택되어야 합니다.", null, null, null);
			return;
		}

		var screenList = [];
		var screenTypeList = [];

		for (var i = 0; i < $('#scheduleTable').DataTable().rows().data().length; i++) {
			screenList.push($('#scheduleTable').DataTable().rows().data()[i].screenIdx);
			screenTypeList.push($('#scheduleTable').DataTable().rows().data()[i].screenType);

		}
		var controlType = $("#scheduleForm [name='controlType']:checked").val();
		var controlVal = $("#scheduleForm [name='"+controlType+"_val']:checked").val();
		if(controlType=='CTL0005'){
			controlVal = $('#scheduleForm #volume').text();
		}
		
		if(!controlType){
			$.modalCommon.alertView("제어명령을 선택해주세요.", null, null, null);
			return;
		}
		if(!controlVal){
			$.modalCommon.alertView("제어값을 설정해주세요.", null, null, null);
			return;
		}
		var params = {
			domainIdx : $('#user #domainIdx').val(),
			brandIdx : $('#user #brandIdx').val(),
			francIdx : $('#user #francIdx').val(),
			dayOfWeek: dayOfWeek,
			controlScheduleIdx : parseInt($('#scheduleForm #controlScheduleIdx').val()),
			controlScheduleName : $('#scheduleForm #controlScheduleName').val(),
			startDate : $('#scheduleForm #startDate').val(),
			endDate : $('#scheduleForm #endDate').val(),
			startTime : $('#scheduleForm #startTime').val(),
 			controlType:  controlType,
 			controlValue:  controlVal,
			activeYn : scValue,
			deviceList : deviceList

		};

		var result;
		var msg;
		var endMsg;
		var url;

		if ($('#scheduleForm #controlScheduleIdx').val() == 0) {
			msg = '저장하시겠습니까?';
			endMsg = '저장되었습니다.';
			url = '<c:url value="/ControlSchedule/CreateSchedule"/>';
		} else {
			msg = '저장하시겠습니까?';
			endMsg = '저장되었습니다.';
			url = '<c:url value="/ControlSchedule/UpdateSchedule"/>';
		}

		BootstrapDialog.confirm({
			title : '',
			message : msg,
			type : BootstrapDialog.TYPE_WARNING,
			closable : false,
			draggable : true,
			btnCancelLabel: '취소',
		    btnOKLabel: '저장',
			btnOKClass : 'btn-warning',
			callback : function(confirmResult) {
				if (confirmResult) {
					result = $.ajaxUtil.ajaxArray(url, params);

					if(result.resultCode == 0){
						$.modalCommon.close();
						$.modalCommon.alertView(endMsg, null, null, null);

						scheduleList();
					}
				}
			}
		});
	}



	function changeDisable(){
		$('#SaveSchedule').attr('disabled', true);
		$('#controlScheduleName').attr('disabled', true);
		$('#startDate').attr('disabled', true);
		$('#endDate').attr('disabled', true);
		$('#startTime').prop('disabled', true);
		$('#activeYn').prop('disabled', true);
		$(' .input-group.date').off('click');
	};


</script>