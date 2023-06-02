<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">Hall Bar LED 전광판 목록</div>
	</div>
	<div class="box-body">
		<table id="mTable" class="table table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="totCheckboxArr" /></th>
					<th>No</th>
					<th>텍스트</th>
					<th>글꼴</th>
					<th>크기</th>
					<!-- <th>모션</th> -->
					<th>배경 색상</th>
					<th>글꼴 색상</th>
					<th>정렬 순서</th>
					<th>등록일</th>
					<th>활성화</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<form:form commandName="BarLedVo" name="user" id="user" method="post">
				<form:hidden path="domainIdx" />
				<form:hidden path="brandIdx" />
				<form:hidden path="francIdx" />
				<form:hidden path="deviceIdx" />
			</form:form>
			<button type="button" class="btn btn-primary2" data-toggle="modal" onclick="Add();" id="addBtn"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="Delete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO03');
	    treeHeight();
		btnDisabled();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });

	});

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
        	treeSearchList(1, false, null, 'STO03');
        	e.keyCode = 0;
		}
	}

	//노드가 선택될 경우
	function callbackSelectNodeJstree(data){
		$('#user #domainIdx').val(data.node.li_attr.domainidx);
        $('#user #brandIdx').val(data.node.li_attr.brandidx);
        $('#user #francIdx').val(data.node.li_attr.francidx);
        $('#user #deviceIdx').val(data.node.li_attr.deviceidx);

    	MarqueeList(data.node.li_attr.domainidx, data.node.li_attr.brandidx, data.node.li_attr.francidx, data.node.li_attr.deviceidx);
        btnDisabled();
	}

	// 	트리 첫번째 조회 콜백
	function callbackFirstSelect(jsonData)
	{
		MarqueeList($('#user #domainIdx').val(), $('#user #brandIdx').val(), $('#user #francIdx').val(), $('#user #deviceIdx').val());
	}

	var mTable = "";
	function MarqueeList(domainIdx, brandIdx, francIdx, deviceIdx){
		if(mTable != "")
			mTable.destroy();
		var optObject = {};
		var domainIdx = $('#user #domainIdx').val();
		var brandIdx = $('#user #brandIdx').val();
		var francIdx = $('#user #francIdx').val();
		var deviceIdx = $('#user #deviceIdx').val();

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/BarLed/List"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{data: "mqIdx"},
            {data: "rowIdx"},
            {data: "mqText"},
            {data: "mqFont"},
            {data: "mqSize"},
            /* {data: "mqMotion"}, */
            {data: "mqBgColor"},
            {data: "mqTextColor"},
            {data: "orderSeq"},
            {data: "modDate"},
            {data: "activeYn"}
        ];


		optObject.arrColumnDefs = [
			{
                'targets': 0,
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
               		return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
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
                	return '<div class="ellipsis270">'+'<a id="name' + full.mqIdx + '" class="FileNameHover" onclick="Edit('+full.mqIdx+', '+full.domainIdx+', '+full.brandIdx+', '+full.francIdx+')" style="cursor:pointer;text-decoration: underline;">'+ fnDataTableRenderText(data) + '</a>'+'</div>';
                }

            },
            {
                'targets': 3,
                'width' : '10%',
                'className': 'text-center tl270',
                'render': function (data, type, full, meta) {
                	var result;
                	switch (data) {
                	case 'Gungsuh':
						result = '궁서체';
						break;
					case 'Moebius':
						result = '제목체';
						break;
					case 'RidiBatang':
						result = '바탕체';
						break;
					case 'TheJung160':
						result = '굵은고딕체';
						break;
					default:
						result = '고딕체';
						break;
					}
                	
                	return result;
                }
            },
            /* {
                'targets': 5,
                'className': 'text-center tl270',
                'render': function (data, type, full, meta) {
                	var result;
                	switch (data) {
					case 'left':
						result = '우에서 좌';
						break;
					default:
						result = '좌에서 우';
						break;
					}
                	
                	return result;
                }
            }, */
            {
                'targets': 5,
                'className': 'text-center tl270',
                'render': function (data, type, full, meta) {
                	var result;
                	if(data.indexOf('IMG') == -1){
                		result = fnDataTableRenderText(data) + '<img style="width:20px; height:20px; background-color:'+data+'; margin-left:10px; border:1px solid #000000;"/>';
                	}
                	else {
                		var bgImg;
                		switch (data) {
						case 'IMG_SKY':
							bgImg = '<c:url value="/Content/images/YGImage/sky.jpg"/>';
							data = '맑은하늘';
							break;
						case 'IMG_SMOKE':
							bgImg = '<c:url value="/Content/images/YGImage/smoke.jpg"/>';
							data = '블랙스모크';
							break;
						case 'IMG_NIGHT':
							bgImg = '<c:url value="/Content/images/YGImage/night.jpg"/>';
							data = '밤하늘';
							break;
						default:
							bgImg = '<c:url value="/Content/images/YGImage/gradation.jpg"/>';
							data = '그라데이션';
							break;
						}
                		result = fnDataTableRenderText(data) + '<img style="width:20px; height:20px; background-image:url('+bgImg+'); margin-left:10px; border:1px solid #000000;"/>'
                	}
                	
                	return result;
                }
            },
            {
                'targets': [4,7,8],
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 6,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data) + '<img style="width:20px; height:20px; background-color:'+data+'; margin-left:10px; border:1px solid #000000;"/>';
                }
            },
            {
                'targets': 9,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
  	              var checkText = '';
	              if(data == 'Y') {
	            	  checkText = 'checked="checked"';
	              }
	              return '<input type="checkbox" name="activeYn" id="active'+full.mqIdx+'" onchange="Active(\'' + full.mqIdx + '\',\''+ full.mqIdx + '\',\'' + full.domainIdx + '\',\'' + full.brandIdx + '\');"  value="'+full.mqIdx+'" '+checkText+' data-group-cls="btn-group-sm" class="activeYn" data-reverse />';
    	      }
            }
        ];
		
		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": domainIdx } );
            aoData.push( { "name": "brandIdx", "value": brandIdx } );
            aoData.push( { "name": "francIdx", "value": francIdx } );
            aoData.push( { "name": "deviceIdx", "value": deviceIdx } );
            aoData.push( { "name": "defaultOrderName"   , "value": "orderSeq, modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "ASC" } );
        }

		optObject.language = {"search": "검색"};

	    mTable = commonPagingDateTable(optObject);
	}

	function Add() {
		$.sessionCheck();
        $.modalCommon.loadDataView('Hall Bar LED 전광판 추가', '<c:url value="/BarLed/Form"/>' , {brandIdx:$('#brandIdx').val(), francIdx: $('#francIdx').val(), mqIdx:0});
	}

	function Edit(val, domainIdx, brandIdx, francIdx) {
		/* $('#user #domainIdx').val(domainIdx);
		$('#user #brandIdx').val(brandIdx);
		$('#user #francIdx').val(francIdx); */

		$.sessionCheck();
        $.modalCommon.loadDataView('Hall Bar LED 전광판 수정', '<c:url value="/BarLed/Form"/>' , {brandIdx:brandIdx, francIdx: francIdx, mqIdx : val});
	}

	function Delete() {
		$.sessionCheck();
	    var arr = $.formOtherUtil.isArrCheck('checkboxArr');

	    var callBack = function (result) {
	    	if (result) {
                var param = { checkboxArr: arr, domainIdx : $("#user #domainIdx").val(), brandIdx : $("#user #brandIdx").val(), francIdx : $("#user #francIdx").val()};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/BarLed//Delete"/>', param);

                if (dResult.resultCode == 0 ) {
                	MarqueeList($('#user #domainIdx').val(), $('#user #brandIdx').val(), $('#user #francIdx').val());
                	//$.checkboxUtil.checkAllFalse();
					$.modalCommon.alertView('처리 되었습니다.');
				}
            }
        }
		commonDelete(arr, callBack);
	}

	function Active(rowIdx, mqIdx, domainIdx, brandIdx){
		commonActive(rowIdx, { domainIdx: domainIdx, brandIdx: brandIdx, mqIdx: mqIdx}, '<c:url value="/BarLed/ActiveYn"/>');
	}
</script>