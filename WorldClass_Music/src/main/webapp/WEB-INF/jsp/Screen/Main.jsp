<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/site/SH/ssgfTemplate.css'/>" />
<script type="text/javascript" src="<c:url value='/Content/site/SH/ssgfLayerTemplate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/SH/ssgfTemplate.js'/>"></script>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">메인스크린 목록</div>
	</div>
	<div class="box-body">
		<table id="mTable" class="table table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="totCheckboxArr" /></th>
					<th>No</th>
					<th>스크린명</th>
					<th>해상도</th>
					<th>재생시간</th>
					<th>사용여부</th>
					<th>최종 수정일</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<form:form commandName="ScreenVo" name="user" id="user" method="post">
				<form:hidden path="domainIdx" />
				<form:hidden path="brandIdx" />
				<form:hidden path="francIdx" />
			</form:form>
			<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="Add();" id="addBtn"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="Delete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO01');
	    treeHeight();
		btnDisabled();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });

	});


	/*********************************************** Main Script Start ***********************************************/

	function btnDisabled() {
		var brandIdx = $('#brandIdx').val();
		var francIdx = $('#francIdx').val();
		var userBrand = $('#user #brandIdx').val();
		var userFranc = $('#user #francIdx').val();

		// 서비스및 사이트 키가 없을때느느 항상 등록/삭제 비활성화
		if(brandIdx == 0){
			$('#addBtn').attr('disabled', 'disabled');
     		$('#delBtn').attr('disabled', 'disabled');
     	}
		else if(brandIdx != 0){
			$('#addBtn').removeAttr('disabled');
			$('#delBtn').removeAttr('disabled');
		}
		if (userBrand != 0 && userFranc != 0) {
			if(userBrand == brandIdx && userFranc == francIdx){
				$('#addBtn').removeAttr('disabled');
				$('#delBtn').removeAttr('disabled');
	     	}
			else {
				$('#addBtn').attr('disabled', 'disabled');
	     		$('#delBtn').attr('disabled', 'disabled');
			}
		}
	}



	// 	키 이벤트
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

    	ScreenList(data.node.li_attr.domainidx, data.node.li_attr.brandidx, data.node.li_attr.francidx);
        btnDisabled();
	}

	// 	트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData)
	{
		ScreenList($('#domainIdx').val(), $('#brandIdx').val(), $('#francIdx').val());
	}

	function ScreenList(domainIdx, brandIdx, francIdx){
		var optObject = {};
		var domainIdx = $('#user #domainIdx').val();
		var brandIdx = $('#user #brandIdx').val();
		var francIdx = $('#user #francIdx').val();

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/Screen/List"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{data: "screenIdx"},
            {data: "rowIdx"},
            {data: "screenName"},
            {data: "resolutionX"},
            {data: "playTime"},
            {data: "delYn"},
            {data: "modDate"}
        ];


		optObject.arrColumnDefs = [
			{
                'targets': 0,
                'data': 'screenIdx',
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	if(data != 0 && fnDataTableRenderText(full.delYn) != null  && fnDataTableRenderText(full.delYn) == '0'){
                		return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
                	}
                	else{
                		return '<input type="checkbox" disabled value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
                	}
                }
            },
            {
                'targets': 1,
                'data': 'rowIdx',
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                   	return dataTableRowIdx(meta);
                }
            },
            {
                'targets': 2,
                'data': 'screenName',
                'className': 'text-center tl270',
                'display': 'display',
                'render': function (data, type, full, meta) {
                	return '<div class="ellipsis270">'+'<a id="name' + full.screenIdx + '" class="FileNameHover" onclick="Edit('+full.screenIdx+', '+full.domainIdx+', '+full.brandIdx+', '+full.francIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a>'+'</div>';
                }

            },
            {
                'targets': 3,
                'data': 'resolutionX',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	if(fnDataTableRenderText(data) != 0 && full.resolutionX != null){
                		return fnDataTableRenderText(full.resolutionX) + "X" + fnDataTableRenderText(full.resolutionY);
                	}
                	else{
                		return "";
                	}
                }
            },
            {
                'targets': 4,
                'data': 'playTime',
                'className': 'text-center',
                'type': 'date',
                'render': function (data, type, full, meta) {
                	var time = new Date(0, 0, 0, 0, 0, 0, fnDataTableRenderText(data));
            		var hour = time.getHours().toString().length > 1 ? time.getHours().toString() : "0" + time.getHours().toString();
            		var min = time.getMinutes().toString().length > 1 ? time.getMinutes().toString() : "0" + time.getMinutes().toString();
            		var sec = time.getSeconds().toString().length > 1 ? time.getSeconds().toString() : "0" + time.getSeconds().toString();
                    return hour + ':' + min + ':' + sec;
                }
            },
            {
                'targets': 5,
                'data': 'delYn',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	if(fnDataTableRenderText(full.delYn) == '0'){
                		return "미사용";
                	}
                	else{
                		return "사용중";
                	}
                }
            },
            {
                'targets': 6,
                'data': 'modDate',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            }
        ];

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": domainIdx } );
            aoData.push( { "name": "brandIdx", "value": brandIdx } );
            aoData.push( { "name": "francIdx", "value": francIdx } );
            aoData.push( { "name": "defaultOrderName"   , "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }

		optObject.language = {"search": "스크린 명"};

	    mTable = commonPagingDateTable(optObject);
	}

	function Add() {
		$.sessionCheck();
        $.modalCommon.loadDataView('스크린 추가', '<c:url value="/Screen/Form"/>' , {brandIdx:$('#brandIdx').val(), francIdx: $('#francIdx').val(), screenIdx:0});
	}

	function Edit(val, domainIdx, brandIdx, francIdx) {
		$('#user #domainIdx').val(domainIdx);
		$('#user #brandIdx').val(brandIdx);
		$('#user #francIdx').val(francIdx);

		$.sessionCheck();
        $.modalCommon.loadDataView('Edit Screen', '<c:url value="/Screen/Form"/>' , {brandIdx:brandIdx, francIdx: francIdx, screenIdx:val});
	}

	function Delete() {
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('checkboxArr');

	    var callBack = function (result) {
	    	if (result) {
                var param = { checkboxArr: arr, domainIdx : $("#user #domainIdx").val(), brandIdx : $("#user #brandIdx").val(), francIdx : $("#user #francIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Screen/Delete"/>', param);

                if (dResult.resultCode == 0 ) {
                	ScreenList($('#user #domainIdx').val(), $('#user #brandIdx').val(), $('#user #francIdx').val());
                	$.checkboxUtil.checkAllFalse();
					$.modalCommon.alertView('처리 되었습니다.');
				} else {
					//문구는 맞지만 두번 호출된다.
// 					$.modalCommon.alertView(dResult.messages.title);
				}
            }
        }
		commonDelete(arr, callBack);


	}

	/*********************************************** Main Script End ***********************************************/

	/*********************************************** Form Script Start ***********************************************/
	function Load() {
		$.sessionCheck();
		var screenIdx = $('#screenIdx').val();
		var domainIdx = $('#user #domainIdx').val();
		var brandIdx = $('#user #brandIdx').val();
		var francIdx = $('#user #francIdx').val();

		var param = {
				screenIdx : screenIdx,
				domainIdx : domainIdx,
				brandIdx : brandIdx,
				francIdx : francIdx
		}
		var jsonData = $.ajaxUtil.ajaxDefault('<c:url value="/Screen/Get"/>', param).data;

		var data;
		if (jsonData != null && jsonData.length > 0) {
			var layerArr = new Array();
			var contentArr = new Array();

			var ContentNum = 0;
			var LayerNum = 0;

			$('#frm #brandIdx').val(jsonData[0].brandIdx);
			$('#frm #francIdx').val(jsonData[0].francIdx);

			for (var i = 0; i < jsonData.length; i++) {

				if (i != 0) {
					if (jsonData[i].screenLayerIdx != jsonData[i-1].screenLayerIdx) {
						ContentNum = 0;
						contentArr = [];
						LayerNum++;
					}
				}

				if(jsonData[i].savePath != null){
					var split = jsonData[i].savePath.split('.');
					split[1] = split[1].toLowerCase();
					if (split[1] != 'jpg' && split[1] != 'png' && split[1] != 'jpeg') {
						split[1] = 'png';
					}

					//  레이어 컨텐츠
					var content = {
							fileContentIdx : jsonData[i].fileContentIdx,
							fileName : jsonData[i].fileName,
							fileSaveName : jsonData[i].fileSaveName,
							savePath : jsonData[i].savePath,
							thumbnailPath : split[0]+'_thumb.'+split[1],
							fileSize : jsonData[i].fileSize,
							checkSum : jsonData[i].checkSum,
							playTime : jsonData[i].playTime,
							orderSeq : jsonData[i].orderSeq
					}

					contentArr[ContentNum] = content;
				}

				// 레이어
				layerArr[LayerNum] = {
						startRow : jsonData[i].startRow,
						startCol : jsonData[i].startCol,
						rowSpan : jsonData[i].rowSpan,
						colSpan : jsonData[i].colSpan,
						content : contentArr
				}

				ContentNum++;

				if (i == jsonData.length-1) {
					data = {
							screenIdx : jsonData[i].screenIdx,
							screenName : jsonData[i].screenName,
							resolutionX : jsonData[i].resolutionX,
							resolutionY : jsonData[i].resolutionY,
							rowCnt : jsonData[i].rowCnt,
							colCnt : jsonData[i].colCnt,
							layer : layerArr
					}
				}
			}

			$.fn.Load(data);
		}
	}

	//템플릿 적용
	function applyTemplate(domainIdx, brandIdx, francIdx, templateIdx) {
		//기 적용된 내역 모두 삭제
		$('#lList').children().remove();
		$('.layoutEditorCanvas').children().remove();
		//$('#contentList').children().remove();

		$.sessionCheck();
		var param = {
			domainIdx : domainIdx,
			brandIdx : brandIdx,
			francIdx : francIdx,
			templateIdx : templateIdx
		}
		var jsonData = $.ajaxUtil.ajaxDefault('<c:url value="/Template/Get"/>', param).data;

		var data;
		if (jsonData != null && jsonData.length > 0) {
			var layerArr = new Array();
			var contentArr = new Array();
			var LayerNum = 0;
			var ContentNum = 0;

			$('#frm #brandIdx').val(jsonData[0].brandIdx);
			$('#frm #francIdx').val(jsonData[0].francIdx);

			for (var i = 0; i < jsonData.length; i++) {
				if (i != 0) {
					if (jsonData[i].templateLayerIdx != jsonData[i-1].templateLayerIdx) {
						LayerNum++;
						ContentNum = 0;
						contentArr = [];
					}
				}

				// 레이어
				layerArr[LayerNum] = {
					startRow : jsonData[i].startRow,
					startCol : jsonData[i].startCol,
					rowSpan : jsonData[i].rowSpan,
					colSpan : jsonData[i].colSpan,
					content : {}
				}

				if (i == jsonData.length-1) {
					data = {
						//screenIdx : jsonData[i].templateIdx,
						screenIdx : 0,
						screenName : jsonData[i].templateName,
						resolutionX : jsonData[i].resolutionX,
						resolutionY : jsonData[i].resolutionY,
						rowCnt : jsonData[i].rowCnt,
						colCnt : jsonData[i].colCnt,
						layer : layerArr
					}
				}
			}

			$.fn.Load(data);
		}
	}

	function Save() {
		$.sessionCheck();
		var screenName = $('#frm #screenName').val();
		var resolutionX = $('#frm #resolutionX').val();
		var resolutionY = $('#frm #resolutionY').val();
		var rowCnt = $('#frm #rowCnt').val();
		var colCnt = $('#frm #colCnt').val();
		var francIdx = $('#user #francIdx').val();
		var brandIdx = $('#user #brandIdx').val();
		var domainIdx = $('#user #domainIdx').val();
		var layerNum = $('.lEditorLayerlistItem').length;
		var contentNum = $('.contentItem').length;

		if (!$.formCheck('#screenName', 'Y', 50, 'N', '스크린 명')) return;

		if (!$.formCheck('#resolutionX', 'Y', 4, 'N', '해상도 X')) return;

		if (!$.formCheck('#resolutionY', 'Y', 4, 'N', '해상도 Y')) return;

		if (!$.formCheck('#rowCnt', 'Y', 2, 'N', '행')) return;

		if (!$.formCheck('#colCnt', 'Y', 2, 'N', '열')) return;

		if (layerNum == 0) {
			$.modalCommon.alertView('Layer를 생성해주세요.');

			return;
		}
		if (contentNum == 0) {
			$.modalCommon.alertView('Content를 등록해주세요.');

			return;
		}
		else {
			for (var i = 0; i < contentNum; i++) {
				var ptime = $('.contentItem').eq(i).attr('ptime');
				if (ptime <= 0) {
					$.modalCommon.alertView('playTime이 잘못된 Content가 존재합니다.\n playTime을 수정해주세요.');
					return;
				}
			}
		}

		var guidArr = new Array();
		var totalPlayTime = 0;
		var maxPlayTime = 0; //20200508 레이어 중 가장 최대 플레이시간 체크

		var layerStr = '';
		var layerLength = 0;

		for (var i = 0; i < layerNum; i++) {
			guidArr[i] = $('.lEditorLayerlistItem').eq(i).attr('tlayer');
			var layer = $('#CanvasDiv').find('div[class="lEditorLayer"][guid="'+guidArr[i]+'"]');

			var rc = layer.attr('rc');
			var alpha = layer.attr('alpha');

			var rcString = rc.split('_');
			var layerplayTime = 0;

			var contentLength = $('#contentDiv').find('div[id="contentItemList"][tlayer="'+guidArr[i]+'"]').find('div[ot="contentItem"]').length;

			var contentStr = '';

			if(contentLength == 0){
				$.modalCommon.alertView('Content를 등록해주세요.');

				return;
			}
			for (var j = 0; j < contentLength; j++) {
				var content = $('#contentDiv').find('div[id="contentItemList"][tlayer="'+guidArr[i]+'"]').find('div[ot="contentItem"]').eq(j);
				var contentId = content.attr('contid');
				var pTime = content.attr('ptime');

				var contentString = '<Content><fileContentIdx>'+contentId+'</fileContentIdx><playTime>'+pTime+'</playTime><orderSeq>'+Number(j+1)+'</orderSeq></Content>';

				layerplayTime += Number(pTime);
				contentStr += contentString;
			}

			var layerString = '<Layer><screenlayerName>'+alpha+'</screenlayerName><startRow>'+rcString[0]+'</startRow><startCol>'+rcString[1]
			 	 +'</startCol><rowSpan>'+rcString[2]+'</rowSpan><colSpan>'+rcString[3]+'</colSpan><playTime>'+layerplayTime
				  +'</playTime><orderSeq>'+Number(i+1)+'</orderSeq><ContentXml>'+contentStr+'</ContentXml></Layer>'


			totalPlayTime += layerplayTime;
			if(maxPlayTime < layerplayTime) {
				maxPlayTime = layerplayTime;
			}

			layerStr += layerString;
			layerLength = i;
		}
		layerStr += '';
		var param;
		var result;
		if ($('#frm #screenIdx').val() == 0) {
			param = {
					'screenName' : screenName,
					'rowCnt' : rowCnt,
					'colCnt' : colCnt,
					'domainIdx' : domainIdx,
					'brandIdx' : brandIdx,
					'francIdx' : francIdx,
					'resolutionX' : resolutionX,
					'resolutionY' : resolutionY,
					//'playTime' : totalPlayTime, //총 플레이시간이 아닌 레이어 중 가장 최대 플레이시간 으로 변경
					'playTime' : maxPlayTime,
					'layerLength' : layerLength,
					'layerXml' : layerStr
			}
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Screen/Create"/>', param);
		}
		else {
			param = {
					'screenIdx' : $('#screenIdx').val(),
					'screenName' : screenName,
					'rowCnt' : rowCnt,
					'colCnt' : colCnt,
					'domainIdx' : domainIdx,
					'brandIdx' : brandIdx,
					'francIdx' : francIdx,
					'resolutionX' : resolutionX,
					'resolutionY' : resolutionY,
					//'playTime' : totalPlayTime,
					'playTime' : maxPlayTime,
					'layerLength' : layerLength,
					'layerXml' : layerStr
			}
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Screen/Update"/>', param);
		}

		if (result.resultCode == 0) {
			ScreenList($('#user #domainIdx').val(), $('#user #brandIdx').val(), $('#user #francIdx').val());
            $.modalCommon.close();
			$.modalCommon.alertView('처리 되었습니다.');
		}
	}

	function saveDisabled() {
		var brandIdx = $('#brandIdx').val();
		var francIdx = $('#francIdx').val();
		var userBrand = $('#user #brandIdx').val();
		var userFranc = $('#user #francIdx').val();

		// 서비스및 사이트 키가 없을때느느 항상 등록/삭제 비활성화
		if(brandIdx == 0){
			$('#frm input').attr('disabled', 'disabled');
			$('#frm #contentCommandBar').off('click');
			$('#frm div').off('mousedown');
			$('#frm').find('div[ot="lEditorResizeHandle"]').remove();
     	}
		else if(brandIdx != 0 && francIdx != 0){
			$('#frm input').removeAttr('disabled');
		}
		if (userBrand != 0 && userFranc != 0) {
			if(userBrand == brandIdx && userFranc == francIdx){
				$('#frm input').removeAttr('disabled');
	     	}
			else {
				$('#frm input').attr('disabled', 'disabled');
				$('#frm #contentCommandBar').off('click');
				$('#frm div').off('mousedown');
				$('#frm').find('div[ot="lEditorResizeHandle"]').remove();
			}
		}
	}
	
	function ContentForm(){
		console.log('~~');
	}
	/*********************************************** Form Script End ***********************************************/
</script>