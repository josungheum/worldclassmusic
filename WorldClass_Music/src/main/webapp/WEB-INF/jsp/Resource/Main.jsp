<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 공통 Script, css 사용 -->
<jsp:include page="/WEB-INF/jsp/Common/metaCommon.jsp" />
<input type="hidden" id="serchKey" name="serchKey">
<input type="hidden" id="searhSystemCode" name="searhSystemCode" value="">
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block">
			<i class="fa fa-fw fa-gear"></i>통합 미디어 현황
		</div>
	</div>
	<div class="box-body">
		<div class="row">
			<ul class="resource-list" style="width: 1150px">
				<li>
					<div id="ALL">
						<div class="resource-type">
							<div class="pull-left">전체 단말</div>
							<div class="pull-right" id="allCountId">1</div>
						</div>
						<div class="resource-desc"></div>
					</div>
				</li>
				<li>
					<div id="CPU">
						<div class="resource-type">
							<div class="pull-left">CPU</div>
							<div class="pull-right" id="cpuCountId">2</div>
						</div>
						<div class="resource-desc">60% 이상</div>
					</div>
				</li>
				<li>
					<div id="RAM">
						<div class="resource-type">
							<div class="pull-left">메모리</div>
							<div class="pull-right" id="ramCountId">1</div>
						</div>
						<div class="resource-desc">60% 이상</div>
					</div>
				</li>
				<li>
					<div id="HDD">
						<div class="resource-type">
							<div class="pull-left">하드디스크</div>
							<div class="pull-right" id="hddCountId">1</div>
						</div>
						<div class="resource-desc">60% 이상</div>
					</div>
				</li>
				<!-- <li>
					<div id="PRINTER">
						<div class="resource-type">
							<div class="pull-left">프린터</div>
							<div class="pull-right" id="printCountId">1</div>
						</div>
						<div class="resource-desc">Error</div>
					</div>
				</li>
				<li>
					<div id="CARD">
						<div class="resource-type">
							<div class="pull-left">카드</div>
							<div class="pull-right" id="cardCountId">1</div>
						</div>
						<div class="resource-desc">Error</div>
					</div>
				</li>
				<li>
					<div id="BARCODE">
						<div class="resource-type">
							<div class="pull-left">바코드</div>
							<div class="pull-right" id="barCodeCountId">1</div>
						</div>
						<div class="resource-desc">Error</div>
					</div>
				</li> -->
				<li>
					<div id="NETWORK">
						<div class="resource-type">
							<div class="pull-left">네트워크</div>
							<div class="pull-right" id="networkCountId">1</div>
						</div>
						<div class="resource-desc">Error</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<div class="box-header">
		<div class="user-block">
			<i class="fa fa-fw fa-gear"></i>접속 상태 세부 현황
		</div>
	</div>
	<div class="box-body">
		<div >
			<table id="statisticsTable" class="table table-striped">
			    <thead>
			        <tr>
			            <th>번호</th>
			            <th>서비스명</th>
			            <th>사이트명</th>
			            <th>단말명</th>
			            <th width="5%">버전</th>
			            <th width="7%">접속유지시간</th>
			            <th width="7%">최종접속시간</th>
						<th>재생 스케줄</th>
						<th>썸네일</th>
			            <th>CPU</th>
			            <th>RAM</th>
			            <th>HDD</th>
			            <!-- <th>Printer</th>
			            <th>Card</th>
			            <th>Barcode</th> -->
			            <th>Network</th>
			        </tr>
			    </thead>
			</table>
		</div>
	</div>
</div>


<script type="text/javaScript">
	var mTable = "";
	$(document).ready(function () {
		// 로그인 페이지로 뒤로가기 방지
		history.pushState(null, null, location.href);
		window.onpopstate = function(event) {

			if(document.referrer){
				var url = document.referrer.split('//');
				var host = url[1].substr(0, url[1].indexOf('/'));
				var hostUrl = url[1].split('/');

				if(hostUrl[2] == null || hostUrl[2] == ""){
					history.go(1);
				}else{
					document.location.replace(document.referrer);
				}
			}else{
				history.go(1);
			}
		};

		$(".resource-list > li > div").off("click").on("click", function() {
			$(".resource-list > li > div.selected").removeClass("selected");
			$(this).addClass("selected");
			$("#serchKey").val($(this).attr("id"));
			//쿼리 조회
			resourceList();			//접속 상태 리스트
		});

		setInterval(function() {
// 			console.log('실행');
			if(alertAjaxOnOff){
				mTable.draw( false );
			}else{
// 				console.log('현재 네트워크 오류로 잠시 스케줄을 중단합니다.');
			}
		}, 20000);

		resourceList();			//접속 상태 리스트

	});

	//접속 상태 리스트
	function resourceList()
	{

		var optObject = {};

		optObject.id = "#statisticsTable";

		optObject.url = '<c:url value="/Resource/KioskResource"/>';

		optObject.arrColumnDefs = [
			{
	        		"targets": [0],
	        		"className": "text-center",
	        		"orderable": false,
	        		'render': function (data, type, full, meta) {
	        			return dataTableRowIdx(meta);
	           }
		    },
			{
				"targets": [1, 2, 3],
				"className": "text-center tl200",
				'render': function (data, type, full, meta) {
					return '<div class="ellipsis200">'+fnDataTableRenderText(data)+'</div>';
				}
			},
			{
				"targets": [4],
				"className": "text-center",
				"render": function(data, type, full, meta){
					return fnDataTableRenderText(data);
				}
			},
			{
				"targets": [5],
				"className": "text-center",
				"render": function(data, type, full, meta) {
					var connectTime = data;
					var strData = "";
					if(full.NETWORK == "0") {
						connectTime = 0;
						strData = "분";
					} else {
						if(connectTime < 60) {
							strData = "분";
						} else if(connectTime >= 60 && connectTime < 1440) {
							strData = "시간(" + connectTime + "분)";
							connectTime = parseInt(connectTime / 60);
						} else if(connectTime >= 1440 && connectTime < 525600) {
							strData = "일(" + connectTime + "분)";
							connectTime = parseInt(connectTime / 1440);
						}
					}
					
					return fnDataTableRenderText(connectTime) + strData;
				}
			},
			{
				"targets": [6],
				"className": "text-center tl200",
				"render": function(data, type, full, meta){
					return fnDataTableRenderText(data);
				}
			},
			{
				"targets": [7],
				"className": "text-center tl200",
				"render": function(data, type, full, meta){
					if(full.NETWORK == "1") {
						return fnDataTableRenderText(data);
					}
					
					return "";
				}
			},
			{
				"targets": [8],
				"className": "text-center tl200",
				"render": function(data, type, full, meta){
					var html = '';

					if(full.NETWORK == "1") {
						if(data) {							
							//return fnDataTableRenderText(data);
							html="<img style='width:120px;height:120px;' src='"+data+"'>";
						} else {
							html="<span>운영 템플릿</span>";
						}
					}
					
					return html;
				}
			},
			{
				"targets": [9, 10, 11],
				"className": "text-center",
				"render": function(data, type, full, meta) {
					var returnValue = "";
					if(full.NETWORK == "0") {
						returnValue = 0;
					} else {
						returnValue = fnDataTableRenderText(data);
					}
					
					return returnValue;
				}
	        },
	        {
	        		"targets": [12],
	        		"className": "text-center",
	        		"render": function(data, type, full, meta) {
	        			var buttonOnOff = "";
	        			if(fnDataTableRenderText(data) == "1" &&  full.NETWORK == "1") {
	        				buttonOnOff  = '<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" id="addBtn">ON</button>';
	        			} else {
	        				buttonOnOff  = '<button type="button" class="btn btn-sm btn-danger" data-toggle="modal" id="addBtn">OFF</button>';
	        			}
	        			
	        			return buttonOnOff;
	        		}
	        }
	    ];

		optObject.arrColumns = [
            {data: "ROW_IDX"},
            {data: "BRAND_NAME"},
            {data: "FRANC_NAME"},
            {data: "DEVICE_NAME"},
            {data: "DEVICE_VERSION"},
            {data: "CONNECT_START_TIME"},
            {data: "MOD_DATE"},
			{data: "CURRENT_SCHEDULE_NAME"},
			{data: "THUMBNAIL_PATH"},
            {data: "CPU"},
            {data: "RAM"},
            {data: "HDD"},
            /* {data: "PRINTER"},
            {data: "CARD"},
            {data: "BARCODE"}, */
            {data: "NETWORK"}
        ];


		optObject.serverParams = function ( aoData ) {

			aoData.push( { "name": "serchKey", "value": $("#serchKey").val() } );
			aoData.push( { "name": "searhSystemCode", "value": $("#searhSystemCode").val() } );
        }

		optObject.language = {"search": ""};

		optObject.fnServerData = function (sSource, aoData, fnCallback) {
			// 20190531 sort요청한 컬럼명 넘기기 추가
			var orderName = "", sortIndex = "";
			for(var idx=0; idx<aoData.length; idx++){
				if(aoData[idx].name == "iSortCol_0"){
					sortIndex = aoData[idx].value;
					break;
				}
			}

			for(var idx=0; idx<aoData.length; idx++){

				if(aoData[idx].name == "mDataProp_" + sortIndex){
					orderName = aoData[idx].value;
					aoData.push( { "name": "sSortName", "value": orderName } );
					break;
				}
			}

			$.post (sSource, aoData, function (json) {
				if(json.resultCode == 0){
					fnCallback (json);
					resourceStatistics();
					if(json.data == null || json.data.length == 0)
 						$(".dataTables_paginate.paging_simple_numbers").attr("style","display:none");
					else
 						$(".dataTables_paginate.paging_simple_numbers").attr("style","text-align: center;");
 				}else{
 					alertAjaxOnOff = false;
 				    if($('.wrap-loading').is(':visible')){
 				    	$('.wrap-loading').hide();
 				    }
 					$.modalCommon.alertView('네트워크가 끊겼습니다. 확인해주시기 바랍니다.');
 				}
			});
		 }
		mTable = commonPagingDateTable(optObject);



	    var html  = '<label>조회 조건 <select name="mTable_length" id="systemCodeSelect" onchange="searchChange();" aria-controls="mTable" class="form-control input-sm">';

	    if('${codeList}' != ""){
			var codeList = JSON.parse('${codeList}');
			for(var i = 0; i < codeList.length; i++)
				html += '<option value="'+fnDataTableRenderText(codeList[i].codeValue)+'" '+(fnDataTableRenderText(codeList[i].codeValue) == $("#searhSystemCode").val() ? 'selected':'')+'>'+fnDataTableRenderText(codeList[i].codeName)+'</option>';
		}

	    html  += '</select> </label>&nbsp;&nbsp;';

	    $("#statisticsTable_filter").prepend(html );
	}

	//서비스 변경
	function searchChange(){
		$("#searhSystemCode").val($("#systemCodeSelect > option:selected").val());
		resourceList();
	}

	//통합 미디어 현황
	function resourceStatistics()
	{
		var param = {};
		var jsonData = $.ajaxUtil.ajaxDefault('<c:url value="/Resource/ResourceStatistics"/>', param).data;
		var TOTAL = jsonData.length;
		var CPU = 0;
		var RAM = 0;
		var HDD = 0;
		var PRINTER = 0;
		var CARD = 0;
		var BARCODE = 0;
		var NETWORK = 0;
		for(var i = 0; i < jsonData.length; i++){
			if(jsonData[i].CPU >= 60 && jsonData[i].NETWORK == 1)
				CPU += 1;
			if(jsonData[i].RAM >= 60 && jsonData[i].NETWORK == 1)
				RAM += 1;
			if(jsonData[i].HDD >= 60 && jsonData[i].NETWORK == 1)
				HDD += 1;
			if(jsonData[i].PRINTER == 0 || jsonData[i].NETWORK == 0)
				PRINTER += 1;
			if(jsonData[i].CARD == 0 || jsonData[i].NETWORK == 0)
				CARD += 1;
			if(jsonData[i].BARCODE == 0 || jsonData[i].NETWORK == 0)
				BARCODE += 1;
			if(jsonData[i].NETWORK == 0)
				NETWORK += 1;
		}
		$("#allCountId").html    (TOTAL);
	    $("#cpuCountId").html    (CPU);
	    $("#ramCountId").html    (RAM);
	    $("#hddCountId").html    (HDD);
	    $("#printCountId").html  (PRINTER);
	    $("#cardCountId").html   (CARD);
	    $("#barCodeCountId").html(BARCODE);
	    $("#networkCountId").html(NETWORK);

	}

</script>