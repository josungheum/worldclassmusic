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
		<div class="user-block under-line">스케줄 운영 현황 보고서</div>
	</div>
	<div class="box-body" style="min-height: 580px;">
		<div class="row">
			<div class="col-sm-3 mobile-label searchCondition" style="width: 28%;">
				<div class="col-sm-2 mobile-label" style="margin-top: 1%;">기간  :</div>
				<div class="col-sm-4 mobile-wd-100" style="padding: 0px; width: 37%;">
					<div class="input-group date"> 
						<input type="text" id="startDt" class="form-control" style="background-color: #fff; cursor: pointer !important;" readonly="readonly" placeholder="0000-00-00"/>
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>
				</div>
				<div class="col-sm-1" style="line-height: 38px;">~</div>
				<div class="col-sm-4 mobile-wd-100" style="padding: 0px; width: 37%;">
					<div class="input-group date">
						<input id="endDt" class="form-control" style="background-color: #fff; cursor: pointer !important;" readonly="readonly" placeholder="0000-00-00"/>
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
			<div class="col-sm-2 mobile-label" style="text-align: right; padding-right: 0px;">
				<button type="button" class="btn btn-primary2 notDisabled" onclick="ScheduleList()" id="searchBtn" style="margin-right: 5%;">조회</button>
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
	                    <th class="text-center rightBorder" colspan="3">스케줄</th>
	                    <th class="text-center" colspan="3">플레이어</th>
	                    <th rowspan="2">결과</th>
	                 </tr>
	                 <tr>
		                 <th class="text-center">시작</th>
		                 <th class="text-center">종료</th>
		                 <th class="rightBorder text-center">재생시간</th>
		                 <th class="text-center">시작</th>
		                 <th class="text-center">종료</th>
		                 <th class="text-center">재생시간</th>
	                </tr>
				</thead>
			</table>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		$.datePickerCommon.createDatePicker('startDt');
		$.datePickerCommon.createDatePicker('endDt');
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
			var arrIdx = francId.split('_');

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
	function ScheduleList(){
		if(!$.formCheck('#startDt', 'Y', 50, 'N', '기간'))
			return;
		if(!$.formCheck('#endDt', 'Y', 50, 'N', '기간'))
			return;
		var startDate = new Date($('#startDt').val());
		var endDate = new Date($('#endDt').val());
		if(startDate > endDate){
			$.modalCommon.alertView("종료일이 시작일보다 빠를 수 없습니다.", null, null, null);
			return;
		}
		
		
		if(mTable != "")
			mTable.destroy();
		var optObject = {};

		optObject.id = "#mTable";
		optObject.url = '<c:url value="/ScheduleReport/List"/>';
		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
			{
				'width': '3%',
	            'targets': 0,
	            "orderable": false,
	            'data': 'deviceIdx',
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
	               'data': 'spDate',
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.substr(0,4)+"-"+data.substr(4,2)+"-"+data.substr(6,2); 
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 5,
	               'data': 'spSchedstarttime',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
		            	  return data.substr(0,2)+":"+data.substr(2,2)+":"+data.substr(4,2); 
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 6,
	               'data': 'spSchedendtime',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
		            	  return data.substr(0,2)+":"+data.substr(2,2)+":"+data.substr(4,2); 
	               }
	         },
	         {
	        	   'width': '5%',
	               'targets': 7,
	               'data': 'spSchedplaytime',
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
	         },
	         {
	        	   'width': '3%',
	               'targets': 8,
	               'data': 'spRealstarttime',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            	  return data.substr(0,2)+":"+data.substr(2,2)+":"+data.substr(4,2); 
	               }
	         },
	         {
	        	   'width': '3%',
	               'targets': 9,
	               'data': 'spRealendtime',
	               "orderable": false,
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
		            	  return data.substr(0,2)+":"+data.substr(2,2)+":"+data.substr(4,2); 
	               }
	         },
	         {
	        	   'width': '5%',
	               'targets': 10,
	               'data': 'spRealplaytime',
	               "orderable": false, 
	               'className': 'text-center',
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
	         },
	         {
	               'targets': 11,
	               'data': 'spStatus',
	               "orderable": false, 
	               'className': 'text-center',
	               'render': function (data, type, full, meta) {
	            		var myNum = parseInt(full.spDiff, 10);
	            		var result = '';
	            		if (myNum < 0) {
							myNum = String(myNum).split('-')[1];
							result = "-";
						}
	            		else if(myNum > 0) {
	            			result = '+';
	            		}
	            		
						var hours   = Math.floor(myNum / 3600);
						var minutes = Math.floor((myNum - (hours * 3600)) / 60);
						var seconds = myNum - (hours * 3600) - (minutes * 60);
						
						if (hours  < 10 && hours >= 0) {hours   = "0"+hours;}
						if (minutes < 10) {minutes = "0"+minutes;}
						if (seconds < 10) {seconds = "0"+seconds;}
						
						if (hours < 0 && hours > -10) {
							var str = String(hours).split('-');
							hours = '-0'+str[1];
						}
	            	   
	            	  return result+hours+':'+minutes+':'+seconds;
	               }
	         }
        ];
		
		optObject.pageLen = 30;
		optObject.iDisplayLength = 30;
		
		optObject.searching = false;
		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "searchStart", "value": $("#startDt").val().substring(0, 4)+$("#startDt").val().substring(5, 7)+$("#startDt").val().substring(8, 10) } );
			aoData.push( { "name": "searchEnd", "value": $("#endDt").val().substring(0, 4)+$("#endDt").val().substring(5, 7)+$("#endDt").val().substring(8, 10)} );
			aoData.push( { "name": "brandIdx", "value": $("#searchBR").val() } );
            aoData.push( { "name": "francIdx", "value": $("#searchFR").val() } );
            aoData.push( { "name": "deviceIdx", "value": $("#searchKI").val() } );
            aoData.push( { "name": "defaultOrderName"   , "value": "SP_DATE, BRAND_IDX, FRANC_IDX, DEVICE_IDX, SP_SCHEDSTARTTIME" } ); 
            aoData.push( { "name": "defaultOrderType"   , "value": "ASC" } );
        }

		mTable = commonPagingDateTable(optObject);
	}
	
	function Excel() {
		var searchStart = $("#startDt").val().substring(0, 4)+$("#startDt").val().substring(5, 7)+$("#startDt").val().substring(8, 10);
		var searchEnd = $("#endDt").val().substring(0, 4)+$("#endDt").val().substring(5, 7)+$("#endDt").val().substring(8, 10);
		var brandIdx = $("#searchBR").val();
		var francIdx = $("#searchFR").val();
		var deviceIdx = $("#searchKI").val();
		var sSortName = $('#sSortName').val();
		var sSortDir = $('#sSortDir_0').val();
		if(sSortName == '' || typeof sSrotName == 'undefined') sSortName = 'SP_DATE, BRAND_IDX, FRANC_IDX, DEVICE_IDX, SP_SCHEDSTARTTIME'; 
		if(sSortDir == '' || typeof sSortDir == 'undefined') sSortDir = 'asc'; 
		
		var params = {searchStart: searchStart, searchEnd: searchEnd, brandIdx: brandIdx, francIdx: francIdx, deviceIdx : deviceIdx};
		var result = $.ajaxUtil.ajaxArray('<c:url value="/ScheduleReport/ExportExcelCnt/"/>', params);
		
		if(result.resultCode == 0){
			 for (var i = 0; i < result.iTotalDisplayRecords; i++){
				var cntTotal = i;
				if(i==0){
					window.location.href = '<c:url value="/ScheduleReport/ExportExcel/"/>'
						+ searchStart + '/' + searchEnd + '/' + brandIdx + '/' + francIdx + '/' + deviceIdx + '/' + cntTotal+'/'+sSortName+'/'+sSortDir;
				}
				else {
					setTimeout(function() {
						window.location.href = '<c:url value="/ScheduleReport/ExportExcel/"/>'
							+ searchStart + '/' + searchEnd + '/' + brandIdx + francIdx + '/' + deviceIdx + '/' + cntTotal+'/'+sSortName+'/'+sSortDir;
					}, 12000);
					
				}
			} 
		}
	}
</script>