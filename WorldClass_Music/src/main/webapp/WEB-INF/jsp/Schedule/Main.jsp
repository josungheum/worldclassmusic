<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.btn-group-sm>.btn {
	padding-left: 3px !important;
	padding-right: 3px !important;
}
</style>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">메인 스케줄 목록</div>
	</div>
	<div class="box-body">
		<table id="mTable" class="table table-striped">
			<thead>

				<tr>
					<th rowspan="2" style="width: 2%;"><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="AllCheckboxArr" /></th>
                    <th rowspan="2" style="width: 3%;">No</th>
                    <th rowspan="2" style="width: 10%;">스케줄 명</th>
                    <th rowspan="2" width="8%">시작일</th>
                    <th rowspan="2" width="8%">종료일</th>
                    <th rowspan="2" width="6%">시작시간</th>
                    <th rowspan="2" width="6%">종료시간</th>
                    <th class="text-center" colspan="7">요일</th>
                    <th rowspan="2" width="6%">재생<br>시간</th>
                    <th rowspan="2" width="11%">활성화</th>
                    <th rowspan="2" width="6%">제어</th>
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
			<form:form commandName="ScheduleVo" name="user" id="user" method="post">
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
	var selectData = [];
	var test= "";
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
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Schedule/ScheduleDelete"/>', param);

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
		optObject.url = '<c:url value="/Schedule/List"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{
				'width': '2%',
	            'targets': 0,
	            "orderable": false,
	            'data': 'mainScheduleIdx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta) {
	        		return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
            	}
			},
			{
            	'width': '2%',
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
                'width': '17%',
                'className': 'text-center tl270',
                'data': 'mainScheduleName',
                'render': function (data, type, full, meta) {
                	var html = "";
                	if(full.scheduleType == "R")
                		html = "<i class='fa fa-refresh'></i>";
                	else if(full.scheduleType == "T")
                		html = "<i class='fa fa-clock-o'></i>";
                	else
                		html = "<i class='fa fa-book'></i>";

                    return '<div style="font-size : 12px; white-space: normal;">'+html+'<a id="name' + full.mainScheduleIdx + '" class="FileNameHover" onclick="Edit(\'' + full.mainScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\')" style="cursor:pointer;text-decoration: underline;margin-left:3px;">'+ fnDataTableRenderText(data) + '</a>'+'</div>';
                }
            },
            {
                'targets': 3,
                'width': '120px',
                'data': 'startDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 4,
                'width': '120px',
                'data': 'endDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
	               'targets': 5,
	               'width': '120px',
	               'data': 'startTime',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(data == "::" || data == null || data == "") {
	            		   data = "00:00:00";
	            	   }
	            	   return data;
	               }
	         },
	         {
	               'targets': 6,
	               'width': '120px',
	               'data': 'endTime',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(data == "::" || data == null || data == "") {
	            		   data = "23:59:59";
	            	   }
	            	   return data;
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 7,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[0] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 8,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[1] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 9,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[2] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 10,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[3] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 11,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[4] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 12,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[5] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 13,
	               'data': 'dayOfWeek',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.split("")[6] == "1" ? "O":"X";
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 14,
	               'data': 'screenPlayTime',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   var time = new Date(0, 0, 0, 0, 0, 0, data);
	            		var hour = time.getHours().toString().length > 1 ? time.getHours().toString() : "0" + time.getHours().toString();
	            		var min = time.getMinutes().toString().length > 1 ? time.getMinutes().toString() : "0" + time.getMinutes().toString();
	            		var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();
	                    return hour + ':' + min + ':' + sec;

	               }
	         },
	         {
	        	   'targets': 15,
	        	   'width': '12%',
	               'data': 'activeYn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(fnDataTableRenderText(data) == 'Y'){
		            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.mainScheduleIdx+'" onchange="Active(\'' + full.mainScheduleIdx + '\',\''+ full.mainScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse checked>';
	               		}
		            	else{
		               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.mainScheduleIdx+'" onchange="Active(\'' + full.mainScheduleIdx + '\',\''+ full.mainScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse>';
		            	}
	               }
	         } ,
	         {
	        	   'targets': 16,
	        	   'width': '120px',
	               'data': 'distributeYn',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	   if(full.scheduleType == "E"){
	            		   return '<button class="btn btn-sm btn-warning" id="distributeYn'+full.rowIdx+'" onclick="mqqtServer(\'' + full.rowIdx + '\',\''+ full.mainScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + 'Y'+ '\');">시작</button>'
	            		    + '<button class="btn btn-sm btn-danger" style="border-radius:20px;" id="distributeYn'+full.rowIdx+'" onclick="mqqtServer(\'' + full.rowIdx + '\',\''+ full.mainScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\',\'' + 'N'+ '\');">종료</button>';
	            		   
	            		    /*if(fnDataTableRenderText(data) == 'Y'){
			            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="distributeYn" id="distributeYn'+full.rowIdx+'" onchange="mqqtServer(\'' + full.rowIdx + '\',\''+ full.mainScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse checked>';
		               		}
			            	else{
			               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="distributeYn" id="distributeYn'+full.rowIdx+'" onchange="mqqtServer(\'' + full.rowIdx + '\',\''+ full.mainScheduleIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\',\'' + full.francIdx + '\');" data-reverse>';
			            	}*/
	            	   }else{
	            		   return '';
	            	   }

	               }
	         }

        ];

		optObject.language = {"search": "스케줄 명"};

		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
			$('.distributeYn').checkboxpicker(defaults = {	offLabel: '종료',	onLabel: '시작'});
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "startDate, endDate, mainScheduleName" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		mTable = commonPagingDateTable(optObject);
	}

	//활성화 공통으로 변경
	function Active(rowIdx, mainScheduleIdx, domainIdx, brandIdx, francIdx){
		commonActive(rowIdx, { domainIdx: domainIdx, brandIdx: brandIdx, francIdx: francIdx, mainScheduleIdx: mainScheduleIdx}, '<c:url value="/Schedule/ActiveYn"/>');
	}

	function Add(){
		if($('#user #domainIdx').val() == 0 || $('#user #brandIdx').val() == 0 || $('#user #francIdx').val() == 0) {
			$.modalCommon.alertView("사이트 선택 후 등록 가능합니다.");
			return;
		}

	    $.modalCommon.loadFullDataView('<i class="fa fa-fw fa-gear"></i> 스케줄 등록', '<c:url value="/Schedule/ScheduleForm"/>' ,{domainIdx:$('#user #domainIdx').val(), brandIdx: $('#user #brandIdx').val() , francIdx: $('#user #francIdx').val(), mainScheduleIdx:0});
	}

	//활성화 공통으로 변경
	function mainActive(rowIdx, mainScheduleIdx, domainIdx, brandIdx, francIdx){
		commonActive(rowIdx, { domainIdx: domainIdx, brandIdx: brandIdx, francIdx: francIdx, mainScheduleIdx: mainScheduleIdx}, '<c:url value="/Schedule/ActiveYn"/>');
	}

	//서버 시작
	function mqqtServer(rowIdx, mainScheduleIdx, domainIdx, brandIdx, francIdx, isStart){

		var params = {
			domainIdx : domainIdx,
			brandIdx : brandIdx,
			francIdx : francIdx,
			mainScheduleIdx : mainScheduleIdx,
			//distributeYn : $('#distributeYn'+rowIdx).prop('checked') ? "Y":"N"
			distributeYn : isStart
		};

		if (params.distributeYn == "Y") {
			msg = '서버를 시작하시겠습니까?';
			endMsg = '시작되었습니다.';
			url = '<c:url value="/Schedule/mqqtYn"/>';
		} else {
			msg = '서버를 종료하시겠습니까?';
			endMsg = '종료되었습니다.';
			url = '<c:url value="/Schedule/mqqtYn"/>';
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


					}
				}
				returnDataTable.ajax.reload(null, false);
			}
		});
	}


	function Edit(mainScheduleIdx, domainIdx, brandIdx, francIdx){
	    $.modalCommon.loadFullDataView('<i class="fa fa-fw fa-gear"></i> 스케줄 수정', '<c:url value="/Schedule/ScheduleForm"/>' ,{ domainIdx: domainIdx, brandIdx:brandIdx , francIdx: francIdx, mainScheduleIdx:mainScheduleIdx});
	}

	function ScheduleTree(){
		var param = {
				domainIdx : $("#scheduleForm #domainIdx").val(),
		   		brandIdx : $("#scheduleForm #brandIdx").val(),
		   		francIdx : $("#scheduleForm #francIdx").val(),
		   		mainScheduleIdx : $("#scheduleForm #mainScheduleIdx").val(),
		   		searchDvType: $('#scheduleForm #deviceType').val(),
		   		searchDvRes: $('#scheduleForm #deviceResType').val(),
		   		//searchDvSite: $('#scheduleForm #deviceSiteType').val()	//매장구분은 쓰지 않음
		   		searchDvSite: ""
		}

		treeSearchList(0,'<c:url value="/Schedule/SearchKioskTree"/>',param);

	}
	function saveSchedule() {
		var scValue = '';

		var scheduleType = $("#scheduleForm [name='scheduleType']:checked").val();

		if(!$.formCheck('#scheduleForm #mainScheduleName', 'Y', 50, 'N', '스케줄명'))
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

		if(scheduleType != "R"){
			if(!$.formCheck('#scheduleForm #startTime', 'Y', 50, 'N', '시작시간'))
				return;

			if(!$.formCheck('#scheduleForm #endTime', 'Y', 50, 'N', '종료시간'))
				return;

			if(Number($("#endTime").val().replace(/:/g,"")) >= 240000 || Number($("#startTime").val().replace(/:/g,"")) >= 240000){
				$.modalCommon.alertView("시간은 23:59:59 이하로 지정해야합니다.", null, null, null);
				return;
			}

			if(Number($("#startTime").val().replace(/:/g,"")) > Number($("#endTime").val().replace(/:/g,""))){
				$.modalCommon.alertView("종료시간이 시작시간보다 빠를 수 없습니다.", null, null, null);
				return;
			}
		}



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

		// 스크린 체크
		if ($('#scheduleTable').DataTable().row().length == 0) {
			$.modalCommon.alertView("1개 이상의 스크린이 선택되어야 합니다.", null, null, null);
			return;
		}

		// 체크 된 디바이스 확인
		var deviceList = [];
		$.each($('#targetTreeList').jstree('get_selected', true), function ()
		{
			if(this.li_attr.deviceidx != null && this.li_attr.deviceidx != 0)
				deviceList.push(this.li_attr.deviceidx);
	    });

		if(deviceList.length == 0){
			$.modalCommon.alertView("1개 이상의 단말이 선택되어야 합니다.", null, null, null);
			return;
		}

		var screenList = [];
		var screenPlayTimeList = [];
		var screenTypeList = [];

		for (var i = 0; i < $('#scheduleTable').DataTable().rows().data().length; i++) {
			var screenRowData = $('#scheduleTable').DataTable().rows().data()[i];
			screenList.push(screenRowData.screenIdx);
			screenTypeList.push(screenRowData.screenType);
			var screenPlayTimeNum = $("[name='screenPlayTime']:eq("+i+")").val().replace(/:/gi,"");
			var screenPlayTimeResult = screenPlayTimeNum.substring(0,2) * (1000 * 60 * 60) + screenPlayTimeNum.substring(2,4) * (1000 * 60) + screenPlayTimeNum.substring(4,6) * (1000);

			screenPlayTimeList.push(screenPlayTimeResult);
		}

		if(scheduleType == "R"){
			eventType = "";
			countType = "";
			startTime = "";
			endTime = "";
			dayOfWeek = "";
		}else if(scheduleType == "T"){
			eventType = "";
			countType = "";
			startTime = $('#scheduleForm #startTime').val();
			endTime = $('#scheduleForm #endTime').val();

		}else{
			eventType = $("#scheduleForm [name='eventType']:checked").val();
			countType = $("#scheduleForm [name='countType']:checked").val();
			startTime = $('#scheduleForm #startTime').val();
			endTime = $('#scheduleForm #endTime').val();
			dayOfWeek= "";
		}

		var params = {
			domainIdx : $('#user #domainIdx').val(),
			brandIdx : $('#user #brandIdx').val(),
			francIdx : $('#user #francIdx').val(),
			scheduleType : $("#scheduleForm [name='scheduleType']:checked").val(),
			dayOfWeek: dayOfWeek,
			eventType : eventType,
			countType : countType,
			mainScheduleIdx : parseInt($('#scheduleForm #mainScheduleIdx').val()),
			mainScheduleName : $('#scheduleForm #mainScheduleName').val(),
			startDate : $('#scheduleForm #startDate').val(),
			endDate : $('#scheduleForm #endDate').val(),
			startTime : startTime,
			endTime : endTime,
			activeYn : scValue,
			deviceList : deviceList,
			screenList : screenList,
			screenTypeList: screenTypeList,
			screenPlayTimeList: screenPlayTimeList

		};

		//var result = $.ajaxUtil.ajaxArray("<c:url value="/Schedule/overlapScreenschedule"/>", params);
// 		console.log(result.data);
		/* if(result.data == 0){ */
			var result;
			var msg;
			var endMsg;
			var url;

			if ($('#scheduleForm #mainScheduleIdx').val() == 0) {
				msg = '저장하시겠습니까?';
				endMsg = '저장되었습니다.';
				url = '<c:url value="/Schedule/CreateSchedule"/>';
			} else {
				msg = '저장하시겠습니까?';
				endMsg = '저장되었습니다.';
				url = '<c:url value="/Schedule/UpdateSchedule"/>';
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
		/* }else{
			$.modalCommon.alertView("중복되는 스케줄이 존재합니다.");
		} */


	}

	function addBtn(id){
	    $.modalCommon.loadFullDataView('<i class="fa fa-fw fa-gear"></i> 스크린 추가', '<c:url value="/Schedule/ScreenForm"/>' , {brandIdx: $('#user #brandIdx').val() , francIdx: $('#user #francIdx').val()});
	};

	function changeDisable(){
		$('#SaveSchedule').attr('disabled', true);
		$('#addbtn').attr('disabled', true);
		$('#remove').attr('disabled', true);
		$('#rowUp').attr('disabled', true);
		$('#rowDown').attr('disabled', true);
		$('#mainScheduleName').attr('disabled', true);
		$('#activeYn').prop('disabled', true);
		$('#disabledTable_scheduleTable').attr('value', 'Y');
		$(' .input-group.date').off('click');
	};

	function addScreen(){
		var tr = $('#screenFind').find('table[id="screenTable"]').find('tbody').find('tr.selected');
		var selectData = [];

		for (var i = 0; i < tr.length; i++) {
			var parent = $(tr[i]);
			var data = $('#screenTable').DataTable().rows().data();
			for(var j = 0; j < data.length; j++){
				if($(tr[i].children[0]).find('input').val() == data[j].screenIdx){
					selectData.push({screenIdx:data[j].screenIdx, screenName:data[j].screenName, playTime:data[j].playTime, screenType:"S", screenPlayTime:data[j].playTime});
				}
			}
		}

		var opTr = $('#screenFind').find('table[id="operationTable"]').find('tbody').find('tr.selected');


		for (var i = 0; i < opTr.length; i++) {
			var parent = $(opTr[i]);
			var data = $('#operationTable').DataTable().rows().data();
			for(var j = 0; j < data.length; j++){
				if($(opTr[i].children[0]).find('input').val() == data[j].screenIdx){
					selectData.push({screenIdx:data[j].screenIdx, screenName:data[j].screenName, playTime:data[j].playTime, screenType:"O", screenPlayTime:data[j].playTime});
				}
			}
		}
		

		var ctTr = $('#screenFind').find('table[id="contentTable"]').find('tbody').find('tr.selected');


		for (var i = 0; i < ctTr.length; i++) {
			var parent = $(ctTr[i]);
			var data = $('#contentTable').DataTable().rows().data();
			for(var j = 0; j < data.length; j++){
				if($(ctTr[i].children[0]).find('input').val() == data[j].fileContentIdx){
					selectData.push({screenIdx:data[j].fileContentIdx, screenName:data[j].fileName, playTime:data[j].playTime, screenType:"C", screenPlayTime:data[j].playTime});
				}
			}
		}

    	if (selectData.length == 0) {
    		$.modalCommon.alertView('스크린을 선택해주세요.');
    		return;
		}


		// 스케줄 테이블에 추가
    	$('#scheduleTable').DataTable().rows.add(selectData).draw();

		// 스크린 화면 닫기
		$(" .screenmodal").click();
	}
</script>