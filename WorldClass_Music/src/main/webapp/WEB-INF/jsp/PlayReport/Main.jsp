<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.daterangepicker table {
	color: black;
}

.rightBorder {
	border-right : 1px solid #FFFFFF !important;
}
@media (min-width: 320px) and (max-width: 480px) {
	.mobile-wd-100 {
		width : 100% !important;
	}
	
	.mobile-label {
		padding-top : 10px !important;
		padding-left: 0px !important;
		padding-right: 0px !important;
		margin : 0px !important;
		width : 100% !important;
	}
	
	.mobile-ft{
		font-size: 16px !important;
	}
}
</style>
<div class="box box-widget pc-pb-7" style="min-height: 740px;">
	<div class="box-header">
		<div class="user-block under-line">광고 재생 보고서</div>
	</div>
	<div class="box-body" style="min-height: 580px;">
		<div class="row">
			<div class="col-sm-2 mobile-label searchCondition">
				<div class="col-sm-5 mobile-label" style="margin-top: 1px;" >적용일자  :</div>
				<div class="col-sm-7 mobile-wd-100" style="padding: 0px;">
					<div class="input-group date"> 
						<input type="text" id="playDt" class="form-control" style="background-color: #fff; cursor: pointer !important;" readonly="readonly" placeholder="0000-00-00"/>
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>
				</div>
			</div>
			<div class="col-sm-2 mobile-label searchCondition">서비스 :
				<select class="form-control mobile-wd-100" id="searchBR" onchange="DistListChange('B')" style="width: 180px; display: inline;">
					<c:forEach items="${serviceList}" var="list">
						<option value="${list.brandIdx}"><c:out value="${list.brandName}" /></option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2 mobile-label searchCondition">사이트 :
				<select class="form-control mobile-wd-100" id="searchFR" onchange="DistListChange('F')" style="width: 180px; display: inline;">
					<option id="st0" value=0 selected>전체</option>
					<c:forEach items="${siteList}" var="list">
						<option id="${list.brandIdx}_${list.francIdx}" value="${list.francIdx}" ><c:out value="${list.francName}" /></option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2 mobile-label searchCondition">단말 :
				<select class="form-control mobile-wd-100" id="searchKI" onchange="DistListChange('D')" style="width: 180px; display: inline;">
					<option id="pl0" value=0 selected>전체</option>
					<c:forEach items="${playerList}" var="list">
						<option id="${list.brandIdx}_${list.francIdx}_${list.deviceIdx}" value="${list.deviceIdx}" ><c:out value="${list.deviceName}" /></option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2 mobile-label searchCondition">스크린명 :
				<input type="text" class="form-control notDisabled" id="searchScreen" style="display: inline; width: 70%;"/>
			</div>
			<div class="col-sm-2 mobile-label" style="text-align: right; padding-right: 0px;">
				<button type="button" class="btn btn-primary2 notDisabled" onclick="PlayReportList()" id="searchBtn" style="margin-right: 5%;">조회</button>
				<button type="button" class="btn btn-primary2 notDisabled" onclick="Excel()" id="excelBtn" style="display: inline;">엑셀저장</button>
			</div>
		</div>
		<br>
		<div class="col-sm-12" style="overflow-x: auto;">
			<table id="mTable" class="table" style="width: 100%;">
				<thead>
					<tr>
						<th rowspan="2">NO</th>
	                    <th rowspan="2">서비스</th>
	                    <th rowspan="2">사이트</th>
	                    <th rowspan="2">플레이어</th>
	                    <th rowspan="2">일자</th>
	                    <th rowspan="2">스크린명</th>
	                    <th rowspan="2">파일명</th>
	                    <th class="text-center" colspan="3">플레이어</th>
	                 </tr>
	                 <tr>
		                 <th class="text-center">시작</th>
		                 <th class="text-center">종료</th>
		                 <th class="text-center">길이</th>
	                </tr>
				</thead>
			</table>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		$.datePickerCommon.createDatePicker('playDt');
	});
	

	function DistListChange(type) {
		switch (type) {
		case 'B':
			$('#searchFR').val(0);
			$('#searchKI').val(0);
			var brandIdx = $('#searchBR').val();
			if (brandIdx != 0) {
				 $('#searchFR').find('option').each(function (idx) {
					var stId = $(this).attr('id');
					if (stId != 'st0') {
						$(this).show();
						if (stId.indexOf(brandIdx+'_') != 0) {
							$(this).hide();
						}
					}
				});
				$('#searchKI').find('option').each(function (idx) {
					var plId = $(this).attr('id');
					if (plId != 'pl0') {
						$(this).show();
						if (plId.indexOf(brandIdx+'_') != 0) {
							$(this).hide();
						}
					}
				});
			}
			else {
				$('#searchFR').find('option').each(function (idx) {
					$(this).show();
				});
				$('#searchKI').find('option').each(function (idx) {
					$(this).show();
				});
			}
			break;
		case 'F':
			var francId = $('#searchFR option:selected').attr('id');
			var francIdx = $('#searchFR').val();
			var arrIdx = francIdx.split('_');

			if(francId!=0){
				$('#searchBR').val(arrIdx[0]);
			}
			$('#searchKI').val(0);
			
			if (francIdx != 0) {
				$('#searchKI').find('option').each(function (idx) {
					var plId = $(this).attr('id');
					if (plId != 'pl0') {
						$(this).show();
						if (plId.indexOf(francId+'_') != 0) {
							$(this).hide();
						}
					}
				});
			}
			else {
				$('#searchKI').find('option').each(function (idx) {
					$(this).show();
				});
			}
			break;
		case 'D':
			var plId = $('#searchKI option:selected').attr('id');
			var plIdx = $('#searchKI').val();
			var arrIdx = plId.split('_');

			if(plIdx!=0){
				$('#searchBR').val(arrIdx[0]);
				$('#searchFR').val(arrIdx[1]);
			}
			break;
		}
	}

	var mTable = "";
	function PlayReportList(){
		var playDt = new Date($('#playDt').val());
		
		if(mTable != "")
			mTable.destroy();
		var optObject = {};

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/PlayReport/List"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{
				'width': '3%',
	            'targets': 0,
	            "orderable": false,
	            'data': 'playlogIdx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta) {
	            	return meta.row + 1;
            	}
			},
			{
                'targets': 1,
                'className': 'text-center',
                'data': 'serviceName',
                'render': function (data, type, full, meta) {
                    return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 2,
                'data': 'siteName',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
            {
                'targets': 3,
                'data': 'playerName',
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                	return fnDataTableRenderText(data);
                }
            },
	        {
	               'targets': 4,
	               'data': 'plPlaydate',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.substr(0,4)+"-"+data.substr(4,2)+"-"+data.substr(6,2); 
	               }
	         },
	        {
	               'targets': 5,
	               'data': 'screenName',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return fnDataTableRenderText(data);
	               }
	         },
	        {
	               'targets': 6,
	               'data': 'fileName',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return fnDataTableRenderText(data);
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 7,
	               'data': 'plStarttime',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
		            	  return data.substr(0,2)+":"+data.substr(2,2)+":"+data.substr(4,2); 
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 8,
	               'data': 'plEndtime',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
		            	  return data.substr(0,2)+":"+data.substr(2,2)+":"+data.substr(4,2); 
	               }
	         },
	         {
	        	   'width': '4%',
	               'targets': 9,
	               'data': 'plPlaytime',
	               "orderable": false,
	               'className': 'text-center rightBorder',
	               'render': function (data, type, full, meta) {
			        	var myNum = parseInt(data, 10);
			            var hours   = Math.floor(myNum / 3600);
			            var minutes = Math.floor((myNum - (hours * 3600)) / 60);
			            var seconds = myNum - (hours * 3600) - (minutes * 60);
	
			            if (hours   < 10) {hours   = "0"+hours;}
			            if (minutes < 10) {minutes = "0"+minutes;}
			            if (seconds < 10) {seconds = "0"+seconds;}
	
	            	  	return hours+':'+minutes+':'+seconds;
	               }
	         }
        ];
		
		optObject.pageLen = 30;
		optObject.iDisplayLength = 30;
		optObject.searching = false;
		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "plPlaydate", "value": $("#playDt").val().substring(0, 4)+$("#playDt").val().substring(5, 7)+$("#playDt").val().substring(8, 10) } );
			aoData.push( { "name": "brandIdx", "value": $("#searchBR").val() } );
            aoData.push( { "name": "francIdx", "value": $("#searchFR").val() } );
            aoData.push( { "name": "deviceIdx", "value": $("#searchKI").val() } );
            aoData.push( { "name": "screenName", "value": $("#searchScreen").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "PL_PLAYDATE, BRAND_IDX, FRANC_IDX, DEVICE_IDX, SCREEN_IDX" } ); 
            aoData.push( { "name": "defaultOrderType"   , "value": "ASC" } );
        }

		mTable = commonPagingDateTable(optObject);
	}
	
	function Excel() {
		if ($("#playDt").val() == '') {
			$.modalCommon.alertView('일자를 선택해주세요.');
			return;
		}
		var plPlaydate = $("#playDt").val().substring(0, 4)+$("#playDt").val().substring(5, 7)+$("#playDt").val().substring(8, 10);
		var brandIdx = $("#searchBR").val();
		var francIdx = $("#searchFR").val();
		var deviceIdx = $("#searchKI").val();
		var screenName = $('#searchScreen').val();
		var sSortName = $('#sSortName').val();
		var sSortDir = $('#sSortDir_0').val();
		if(sSortName == '' || typeof sSrotName == 'undefined') sSortName = 'PL_PLAYDATE, BRAND_IDX, FRANC_IDX, DEVICE_IDX, SCREEN_IDX'; 
		if(sSortDir == '' || typeof sSortDir == 'undefined') sSortDir = 'asc'; 
		if (screenName == '') screenName = '$ALL';
		
		var params = {plPlaydate : plPlaydate, brandIdx: brandIdx, francIdx: francIdx, deviceIdx : deviceIdx, screenName: screenName};
		var result = $.ajaxUtil.ajaxArray('<c:url value="/PlayReport/ExportExcelCnt/"/>', params);
		
		if(result.resultCode == 0){
			 for (var i = 0; i < result.iTotalDisplayRecords; i++){
				var cntTotal = i;
				if(i==0){
					window.location.href = '<c:url value="/PlayReport/ExportExcel/"/>'
						+ plPlaydate + '/' + brandIdx + '/' + francIdx + '/' + deviceIdx + '/' + screenName + '/' + cntTotal+'/'+sSortName+'/'+sSortDir;
				}
				else {
					setTimeout(function() {
						window.location.href = '<c:url value="/PlayReport/ExportExcel/"/>'
							+ plPlaydate + '/' + brandIdx + '/' + francIdx + '/' + deviceIdx + '/' + screenName + '/' + cntTotal+'/'+sSortName+'/'+sSortDir;
					}, 12000);
					
				}
			} 
		}
	}
</script>