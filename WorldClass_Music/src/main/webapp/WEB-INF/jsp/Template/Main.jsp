<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/site/template/Template.css'/>" />
<script type="text/javascript" src="<c:url value='/Content/site/template/LayerTemplate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/template/Template.js'/>"></script>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">템플릿 목록</div>
	</div>
	<div class="box-body">
		<table id="templateListTable" class="table table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="totCheckboxArr" /></th>
					<th>No</th>
					<th>템플릿명</th>
					<th>해상도</th>
					<!--th>사용여부</th-->
					<th>컨텐트 영역수</th>
					<th>최종 수정일</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<form:form commandName="TemplateVo" name="user" id="user" method="post">
				<form:hidden path="domainIdx" />
				<form:hidden path="brandIdx" />
				<form:hidden path="francIdx" />
			</form:form>
			<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="addTemplate();" id="addBtn"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="deleteTemplate();" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO01');
		treeHeight();
		btnDisabled();

		$(window).resize(function() {
			treeHeight('resize');
		});
	});

	function btnDisabled() {
		var brandIdx = $('#brandIdx').val();
		var francIdx = $('#francIdx').val();
		var userBrand = $('#user #brandIdx').val();
		var userFranc = $('#user #francIdx').val();

		// 서비스및 사이트 키가 없을때는 항상 등록/삭제 비활성화
		if(brandIdx == 0){
			$('#addBtn').attr('disabled', 'disabled');
			$('#delBtn').attr('disabled', 'disabled');
		} else if(brandIdx != 0){
			$('#addBtn').removeAttr('disabled');
			$('#delBtn').removeAttr('disabled');
		}
		if (userBrand != 0 && userFranc != 0) {
			if(userBrand == brandIdx && userFranc == francIdx) {
				$('#addBtn').removeAttr('disabled');
				$('#delBtn').removeAttr('disabled');
			} else {
				$('#addBtn').attr('disabled', 'disabled');
				$('#delBtn').attr('disabled', 'disabled');
			}
		}
	}

	// 트리 키 이벤트
	function callbackTreeSearchKeyEvt(e) {
		if (e.keyCode == 13) {
			treeSearchList(1, false, null, 'STO01');
			e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data) {
		$('#user #domainIdx').val(data.node.li_attr.domainidx);
		$('#user #brandIdx').val(data.node.li_attr.brandidx);
		$('#user #francIdx').val(data.node.li_attr.francidx);

		templateList(data.node.li_attr.domainidx, data.node.li_attr.brandidx, data.node.li_attr.francidx);
		btnDisabled();
	}

	//트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData) {
		templateList($('#domainIdx').val(), $('#brandIdx').val(), $('#francIdx').val());
	}

	//템플릿 리스트 조회
	function templateList(domainIdx, brandIdx, francIdx) {
		var optObject = {};
		//var domainIdx = $('#user #domainIdx').val();
		//var brandIdx = $('#user #brandIdx').val();
		//var francIdx = $('#user #francIdx').val();

		optObject.id = "#templateListTable";
		optObject.url = '<c:url value="/Template/List"/>';

		optObject.arrColumns = [
			{data: "templateIdx"},
			{data: "rowIdx"},
			{data: "templateName"},
			{data: "resolutionX"},
			{data: "delYn"},
			{data: "modDate"}
		];

		optObject.arrColumnDefs = [
			{
				'targets': 0,
				'data': 'templateIdx',
				"orderable": false,
				'className': 'text-center',
				'render': function (data, type, full, meta) {
					if(data != 0 && fnDataTableRenderText(full.delYn) != null  && fnDataTableRenderText(full.delYn) == '1') {
						return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
					} else {
						return '<input type="checkbox" disabled value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
					}
				}
			}, {
				'targets': 1,
				"orderable": false,
				'className': 'text-center',
				'render': function (data, type, full, meta) {
					return dataTableRowIdx(meta);
				}
			}, {
				'targets': 2,
				'className': 'text-center tl270',
				//'display': 'display',
				'render': function (data, type, full, meta) {
					return '<div class="ellipsis270">'+'<a id="name' + full.templateIdx + '" class="FileNameHover" onclick="editTemplate('+full.templateIdx+', '+full.domainIdx+', '+full.brandIdx+', '+full.francIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a>'+'</div>';
				}
			}, {
				'targets': 3,
				'className': 'text-center',
				'render': function (data, type, full, meta) {
					if(fnDataTableRenderText(data) != 0 && full.resolutionX != null) {
						return fnDataTableRenderText(full.resolutionX) + "X" + fnDataTableRenderText(full.resolutionY);
					} else {
						return "";
					}
				}
			}, {
				'targets': 4,
				'className': 'text-center',
				'render': function (data, type, full, meta) {
					if(fnDataTableRenderText(full.delYn) == '0') {
						return "미사용";
					} else {
						return "사용중";
					}
				}
			}, {
				'targets': 5,
				'className': 'text-center',
				'render': function (data, type, full, meta) {
					return fnDataTableRenderText(data);
				}
			}
		];

		optObject.serverParams = function (aoData) {
			aoData.push({"name": "domainIdx", "value": domainIdx } );
			aoData.push({"name": "brandIdx", "value": brandIdx } );
			aoData.push({"name": "francIdx", "value": francIdx } );
			aoData.push({"name": "defaultOrderName", "value": "modDate" } );
			aoData.push({"name": "defaultOrderType", "value": "DESC" } );
		}

		optObject.language = {"search": "템플릿 명"};

		templateListTable = commonPagingDateTable(optObject);
	}

	//템플릿 추가
	function addTemplate() {
		$.sessionCheck();
		$.modalCommon.loadDataView('템플릿 추가', '<c:url value="/Template/Form"/>' , {brandIdx:$('#brandIdx').val(), francIdx: $('#francIdx').val(), templateIdx:0});
	}

	//템플릿 수정
	function editTemplate(val, domainIdx, brandIdx, francIdx) {
		$('#user #domainIdx').val(domainIdx);
		$('#user #brandIdx').val(brandIdx);
		$('#user #francIdx').val(francIdx);

		$.sessionCheck();
		$.modalCommon.loadDataView('템플릿 수정', '<c:url value="/Template/Form"/>' , {brandIdx:brandIdx, francIdx: francIdx, templateIdx:val});
	}

	//템플릿 삭제
	function deleteTemplate() {
		$.sessionCheck();
		var arr = $.formOtherUtil.isArrCheck('checkboxArr');

		var callBack = function (result) {
			if (result) {
				var param = {checkboxArr: arr, domainIdx : $("#user #domainIdx").val(), brandIdx : $("#user #brandIdx").val(), francIdx : $("#user #francIdx").val()};
				var dResult = $.ajaxUtil.ajaxArray('<c:url value="/Template/Delete"/>', param);

				if (dResult.resultCode == 0 ) {
					templateList($('#user #domainIdx').val(), $('#user #brandIdx').val(), $('#user #francIdx').val());
					$.checkboxUtil.checkAllFalse();
					$.modalCommon.alertView('처리 되었습니다.');
				} else {
					//문구는 맞지만 두번 호출된다.
					//$.modalCommon.alertView(dResult.messages.title);
				}
			}
		}
		commonDelete(arr, callBack);
	}
	/*********************************************** Main Script End ***********************************************/
	function Load() {
		$.sessionCheck();
		var templateIdx = $('#templateIdx').val();
		var domainIdx = $('#user #domainIdx').val();
		var brandIdx = $('#user #brandIdx').val();
		var francIdx = $('#user #francIdx').val();

		var param = {
				templateIdx : templateIdx,
				domainIdx : domainIdx,
				brandIdx : brandIdx,
				francIdx : francIdx
		}
		var jsonData = $.ajaxUtil.ajaxDefault('<c:url value="/Template/Get"/>', param).data;

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
					if (jsonData[i].templateLayerIdx != jsonData[i-1].templateLayerIdx) {
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
							templateIdx : jsonData[i].templateIdx,
							templateName : jsonData[i].templateName,
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

	function saveTemplate() {
		$.sessionCheck();
		var templateName = $('#frm #templateName').val();
		var resolutionX = $('#frm #resolutionX').val();
		var resolutionY = $('#frm #resolutionY').val();
		var rowCnt = $('#frm #rowCnt').val();
		var colCnt = $('#frm #colCnt').val();
		var francIdx = $('#user #francIdx').val();
		var brandIdx = $('#user #brandIdx').val();
		var domainIdx = $('#user #domainIdx').val();
		var layerNum = $('.lEditorLayerlistItem').length;
		//var contentNum = $('.contentItem').length;

		if (!$.formCheck('#templateName', 'Y', 50, 'N', '템플릿 명')) return;
		if (!$.formCheck('#resolutionX', 'Y', 4, 'N', '해상도 X')) return;
		if (!$.formCheck('#resolutionY', 'Y', 4, 'N', '해상도 Y')) return;
		if (!$.formCheck('#rowCnt', 'Y', 2, 'N', '행')) return;
		if (!$.formCheck('#colCnt', 'Y', 2, 'N', '열')) return;

		if (layerNum == 0) {
			$.modalCommon.alertView('Layer를 생성해주세요.');

			return;
		}
		/*if (contentNum == 0) {
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
		}*/

		var guidArr = new Array();
		var totalPlayTime = 0;

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

			/*if(contentLength == 0){
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
			}*/
			var layerString = '<Layer><templatelayerName>'+alpha+'</templatelayerName><startRow>'+rcString[0]+'</startRow><startCol>'+rcString[1]
			 	 +'</startCol><rowSpan>'+rcString[2]+'</rowSpan><colSpan>'+rcString[3]+'</colSpan><playTime>'+layerplayTime
				  +'</playTime><orderSeq>'+Number(i+1)+'</orderSeq><ContentXml>'+contentStr+'</ContentXml></Layer>'

			totalPlayTime += layerplayTime;

			layerStr += layerString;
			layerLength = i;
		}
		layerStr += '';
		var param;
		var result;
		if ($('#frm #templateIdx').val() == 0) {
			param = {
					'templateName' : templateName,
					'rowCnt' : rowCnt,
					'colCnt' : colCnt,
					'domainIdx' : domainIdx,
					'brandIdx' : brandIdx,
					'francIdx' : francIdx,
					'resolutionX' : resolutionX,
					'resolutionY' : resolutionY,
					'playTime' : totalPlayTime,
					'layerLength' : layerLength,
					'layerXml' : layerStr
			}
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Template/Create"/>', param);
		}
		else {
			param = {
					'templateIdx' : $('#templateIdx').val(),
					'templateName' : templateName,
					'rowCnt' : rowCnt,
					'colCnt' : colCnt,
					'domainIdx' : domainIdx,
					'brandIdx' : brandIdx,
					'francIdx' : francIdx,
					'resolutionX' : resolutionX,
					'resolutionY' : resolutionY,
					'playTime' : totalPlayTime,
					'layerLength' : layerLength,
					'layerXml' : layerStr
			}
			result = $.ajaxUtil.ajaxDefault('<c:url value="/Template/Update"/>', param);
		}

		if (result.resultCode == 0) {
			templateList($('#user #domainIdx').val(), $('#user #brandIdx').val(), $('#user #francIdx').val());
            $.modalCommon.close();
			$.modalCommon.alertView('처리 되었습니다.');
		}
	}

	function saveDisabled() {
		var brandIdx = $('#brandIdx').val();
		var francIdx = $('#francIdx').val();
		var userBrand = $('#user #brandIdx').val();
		var userFranc = $('#user #francIdx').val();

		// 서비스및 사이트 키가 없을때는 항상 등록/삭제 비활성화
		if(brandIdx == 0) {
			$('#frm input').attr('disabled', 'disabled');
			$('#frm #contentCommandBar').off('click');
			$('#frm div').off('mousedown');
			$('#frm').find('div[ot="lEditorResizeHandle"]').remove();
		} else if(brandIdx != 0 && francIdx != 0){
			$('#frm input').removeAttr('disabled');
		}

		if (userBrand != 0 && userFranc != 0) {
			if(userBrand == brandIdx && userFranc == francIdx){
				$('#frm input').removeAttr('disabled');
			} else {
				$('#frm input').attr('disabled', 'disabled');
				$('#frm #contentCommandBar').off('click');
				$('#frm div').off('mousedown');
				$('#frm').find('div[ot="lEditorResizeHandle"]').remove();
			}
		}
	}
</script>