<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
<!--
.radioStyle1 {padding: 0 40px 0 10px; font-weight: normal; }
-->
</style>
<div class="box box-widget">
  <form id="mainForm">
	<input id="codeGroup"   name="codeGroup"  type="hidden" />
	<input id="hCodeLevel"  name="hCodeLevel" type="hidden" value="1" />
	<div class="box-header">
		<div class="user-block under-line">코드 목록</div>
	</div>
	<div class="box-body">
		<table id="systemCodeTable" class="table table-striped userList">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" id="ARRIDX_ALL" /></th>
					<th>번호</th>
					<th>코드 분류</th>
					<th>코드ID</th>
					<th>코드 명</th>
					<th>정렬순서</th>
					<th>상세설명</th>
					<th>수정일</th>
					<th>상세보기</th>
					<th>활성화</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
<!-- 			<button type="button" class="btn btn-primary" data-toggle="modal" id="addBtn" onclick="order();">정렬</button> -->
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="systemCodeGoupAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>코드분류 등록</button>
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="systemCodeAdd();"    ><i class="glyphicon glyphicon-plus mr-5"></i>코드 등록</button>
			<button type="button" class="btn btn-delete" onclick="systemCodeDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
  </form>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
		systemCodeList();
	});

	function systemCodeList() {
		var optObject = {};

		optObject.id = "#systemCodeTable";
		optObject.url = '<c:url value="/SystemCode/SystemCodeList"/>';

	   	optObject.arrColumns = [
	   		{data: "codeIdx"},
            {data: "rowIdx"},
            {data: "groupName"},
            {data: "codeValue"},
            {data: "codeName"},
            {data: "orderSeq"},
            {data: "memo"},
            {data: "modDate"},
            {data: "codeIdx"},
            {data: "activeYn"}

        ];

	   	optObject.arrColumnDefs = [
	   		{
	        	'width': '5%',
	            'targets': 0,
	            "orderable": false,
	            'data': 'idx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta)
	            {
	            	if(full.modYn == 'N') {
						return '<input type="checkbox" value="' + data + '" id="checkboxArr" disabled  />';
	            	} else {
	            		return '<input type="checkbox" value="' + data + '" id="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
	            	}
	            }
	        },
	        {
		    	"targets": [1]
		       ,"className": "text-center"
		       ,"orderable": false
		       ,'render': function (data, type, full, meta) {
	               	return dataTableRowIdx(meta);
	           }
		    },
	        {
		    	"targets": [3,4,6]
		       ,"className": "text-center tl130"
	    	   ,'render': function (data, type, full, meta) {
	    		   return '<div class="ellipsis130">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
	        {
		    	"targets": [1, 2, 5, 7]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return '<div class="ellipsis130">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
	   		{
	        	'width': '10%',
	            'targets': 8,
	            'data': 'idx',
	            'orderable': false,
	            'className': 'text-center',
	            'render': function (data, type, full, meta)
	            {
	            	return '<button type="button" class="btn btn-warning" onclick="systemCodeEdit(\'' + full.codeIdx + '\');" id="detail">상세보기</button>';
	            }
	        },
	        {
	        	'width': '15%',
	            'targets': 9,
	            'data': 'activeYn',
	            'type': 'date',
	            'className': 'text-center',
	            'render': function (data, type, full, meta) {
	            	if(fnDataTableRenderText(data) == 'Y')
	            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.codeIdx+'" onchange="systemCodeActive(\'' + full.codeIdx + '\');" data-reverse checked>';
	            	else
	               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.codeIdx+'" onchange="systemCodeActive(\'' + full.codeIdx + '\');" data-reverse>';
	            }
	        }

	    ];

	   	optObject.language = {"search": "코드 명"};

	   	optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
			var hCodeLevel = $('#mainForm #hCodeLevel').val();
		    if(hCodeLevel != '0') {
				fnDisplayGroupCode();
		    }
		}

		optObject.serverParams = function ( aoData ) {
			<%-- ?? 쓰이는 곳 모르겠으므로 주석처리(2019.06.11 이재환)  aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val()  } ); --%>
			aoData.push( { "name": "codeGroup"			, "value": $("#mainForm #codeGroup").val()  } );
			aoData.push( { "name": "codeLevel"			, "value": $("#mainForm #hCodeLevel").val() } );
			aoData.push( { "name": "defaultOrderName"   , "value": "modDate" } );
            aoData.push( { "name": "defaultOrderType"   , "value": "DESC" } );
        }
		var mTable = commonPagingDateTable(optObject);

	    var systemCodeHtml = '';
	    <%-- 코드 구분 추가 --%>
	    systemCodeHtml += '<label>코드 구분';
	    systemCodeHtml += '<input type="radio" name="sCodeLevel" id="sCodeLevel1"  value="1" onclick="fnClickCodeLevel(this.value);" /><label for="sCodeLevel1" class="radioStyle1">코드</label>';
	    systemCodeHtml += '<input type="radio" name="sCodeLevel" id="sCodeLevel0"  value="0" onclick="fnClickCodeLevel(this.value);" /><label for="sCodeLevel0" class="radioStyle1">코드 분류</label>';
	    systemCodeHtml += '</label>';

	    systemCodeHtml += '<label id="lCodeGroup">코드 분류 <select name="mTable_length" id="codeGroupSelect" onchange="systemCodeChange();" aria-controls="mTable" class="form-control input-sm">';
	    systemCodeHtml += '<option value="" >전체</option>';
	    if('${SystemCodeList}' != "")
		{
			var systemCodeList = JSON.parse('${SystemCodeList}');
			for(var i = 0; i < systemCodeList.length; i++)
				systemCodeHtml += '<option value="'+fnDataTableRenderText(systemCodeList[i].codeValue)+'" '+(fnDataTableRenderText(systemCodeList[i].codeValue) == $("#mainForm #codeGroup").val() ? 'selected':'')+'>'+fnDataTableRenderText(systemCodeList[i].codeName)+'</option>';
		}

	    systemCodeHtml += '</select> </label>&nbsp;&nbsp;';

	    $("#systemCodeTable_filter").prepend(systemCodeHtml);

	    var hCodeLevel = $('#mainForm #hCodeLevel').val();
	    if(hCodeLevel == '0') {
			<%-- 코드 분류 검색조건 숨김처리 --%>
	    	$('#mainForm #lCodeGroup').css('visibility', 'hidden');
	    } else {
			<%-- 코드 분류 검색조건 표시처리 --%>
	    	$('#mainForm #lCodeGroup').css('visibility', 'visible');
	    }
	    $('#mainForm #sCodeLevel'+hCodeLevel).attr('checked', true);
	}

	<%-- 그룹코드 표시 --%>
	function fnDisplayGroupCode() {
		 $.ajax({
	            url: '<c:url value="/SystemCode/GroupCode"/>',
	            data: {},
	            type: 'post',
	            dataType: 'json',
	            async: false,
	            success: function (data) {
	            	var systemCodeList = JSON.parse(data);
	            	$('#mainForm #codeGroupSelect').empty();
	            	var option = $('<option>',{value: '', text: '전체'});
					$('#mainForm #codeGroupSelect').append(option);
					$.each(systemCodeList, function(index, entry){
						option = $('<option>',{value:entry.codeValue, text:entry.codeName});
						$('#mainForm #codeGroupSelect').append(option);
					});
					if($("#mainForm #codeGroup").val()) {
						$('#mainForm #codeGroupSelect').val($("#mainForm #codeGroup").val());
					}

	            }
	        });

	}

	<%-- 코드 구분 이벤트 추가 --%>
	function fnClickCodeLevel(value) {
		$('#mainForm #hCodeLevel').val(value);
		if(value == '0') {
			$('#mainForm #codeGroup').val('');
	    } else {
			<%-- 코드 분류 검색조건 표시처리 --%>
			var codeGroup = $('#mainForm #codeGroupSelect').val();
// 			console.log(codeGroup);
			if(codeGroup && $.trim(codeGroup) != ''){
				$('#mainForm #codeGroup').val(codeGroup);
			} else {
				$('#mainForm #codeGroup').val(null);
			}

	    }


		systemCodeList();
	}

	//코드 분류 변경
	function systemCodeChange(){

		$("#mainForm #codeGroup").val($("#mainForm #codeGroupSelect").val());

		systemCodeList();
	}

	//활성화 공통으로 변경
	function systemCodeActive(val)
	{
		commonActive(val, { codeIdx: val}, '<c:url value="/SystemCode/SystemCodeActive"/>');
	}

	//삭제
	function systemCodeDelete() {
		var checkboxArr = $.formOtherUtil.isArrCheck('checkboxArr');
		var callBack = function (result) {
        	if (result) {
                var param = {checkboxArr: checkboxArr};
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/SystemCode/SystemCodeDelete"/>', param);
                if (dResult.resultCode == 0){
                	systemCodeList();
                }
            }
        }
		commonDelete(checkboxArr, callBack);
	}

	function systemCodeAdd() {
		var paramValue = {codeIdx:0, codeLevel:'1'};
		$.modalCommon.loadFullDataView('코드 등록', '<c:url value="/SystemCode/FormNullTemp"/>', paramValue);
	}

	<%-- 코드 분류 등록 --%>
	function systemCodeGoupAdd() {
		var paramValue = {codeIdx:0, codeLevel:'0'};
		$.modalCommon.loadFullDataView('코드 분류 등록', '<c:url value="/SystemCode/FormNullTemp"/>', paramValue);
	}

	function systemCodeEdit(val) {
		var paramValue = {codeIdx:val};
		$.modalCommon.loadFullDataView('코드 수정', '<c:url value="/SystemCode/FormNullTemp"/>', paramValue);
	}

</script>