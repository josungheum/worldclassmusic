<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="CalculateVo" name="frm" id="frm" method="post">
<input type="hidden" id="deviceIdx" value="<c:out value="${calculateVo.deviceIdx}"/>"/>
<div class="modal-body">
	<div>
		<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 row">
			<div class="col-lg-8 col-md-8 col-sm-6 col-xs-12">
				<h1 style="color: #000; font-size: 30px; padding-top:0px;">
				${fn:substring(calculateVo.calculateDate, 0, 4)}년 ${fn:substring(calculateVo.calculateDate, 4, 6)}월 ${fn:substring(calculateVo.calculateDate, 6, 8)}일</h1>
			</div>
		</div>
		<div class="box-body">
			<table id="DataTable" class="table table-striped">
				<thead>
					<tr>
						<th>거래구분</th>
						<th>금액</th>
						<th>거래건수</th>
						<th>금액(변경)</th>
						<th>거래건수(변경)</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div class="modal-footer" style="border-top-color: #ffffff;">
		<button type="button" class="btn btn-primary" onclick="reCalculateVo(${calculateVo.deviceIdx})" id="procBtn">재정산</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>
</div>
</form:form>

<script>
	$(document).ready(function () {
		dataReList();

		var date = new Date();
		var year = date.getFullYear();
		var month;
		if (date.getMonth()+1 < 10) {
			month = '0' + new String(date.getMonth()+1);
		}else {
			month =  new String(date.getMonth()+1);
		}
		var day = new String(date.getDate());

		if (day < 10) {
			day = '0' + day;
		}

		today = year + '-' + month + '-' + day;
		var calculateDate = $("#calculateDate").val();
		if(today != calculateDate){
			$("#procBtn").hide();
		}
	});

	function dataReList(){
		var calculateDate = $("#calculateDate").val();
		var optObject = {};
		optObject.id = "#DataTable";
		optObject.url = '<c:url value="/Calculate/ReCalculateFormDetail"/>';

		optObject.strDom =  '<"top"i>Brt<"bottom"l><"middle"p>';
		optObject.arrButtons=  [{
						extend: 'excelHtml5'
						,text: 'Excel'
		 	            ,className: 'btn btn-primary btn-sm'
		 	            ,filename: '정산내역'
	 	        	    ,customize: function(xlsx) {
	 	        		 	var sheet = xlsx.xl.worksheets['sheet1.xml'];
	 	                	$('row c', sheet).attr('s', '25');
		 				}
		 	           /* ,customize:function(data, config){
		 	        	   data = '\ufeff' + data;
		 	        	   return data;
		 	           } */
		 	      }];

		optObject.arrColumnDefs = [
	    ];

		optObject.arrColumns = [
		 	{
            	'width': '10%',
                'targets': 0,
                'data': 'orderSeq',
                "orderable": false,
                'className': 'text-center',
                'render': function (data, type, full, meta) {
                    return fnDataTableRenderText(full.itemTypeName);
                }
            },
            {
            	'width': '10%',
                'targets': 1,
                'data': 'itemMount',
                'className': 'text-right',
                'render': function (data, type, full, meta) {
                	return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"원";
                }
            },
            {
            	'width': '10%',
                'targets': 2,
                'data': 'itemCount',
                'className': 'text-right',
                'render': function (data, type, full, meta) {
                	return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"건";
                }
            },
            {
            	'width': '10%',
                'targets': 1,
                'data': 'itemAfMount',
                'className': 'text-right',
                'render': function (data, type, full, meta) {
                	return (full.itemMount + data).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"원";
                	//return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"원";
                }
            },
            {
            	'width': '10%',
                'targets': 2,
                'data': 'itemAfCount',
                'className': 'text-right',
                'render': function (data, type, full, meta) {
                	return (full.itemCount + data).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"건";
//                 	return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+"건";
                }
            }

        ];

		optObject.language = {"search": "단말 명"};

		optObject.fnDrawCallback = function (data){
		}

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "brandIdx", "value": $("#brandIdx").val() } );
            aoData.push( { "name": "francIdx", "value": $("#francIdx").val() } );
            aoData.push( { "name": "deviceIdx", "value": $("#deviceIdx").val() } );
            aoData.push( { "name": "calculateDate", "value": calculateDate } );
        }

		optObject.type = "2";
		var mTable = commonPagingDateTable(optObject);
	}
</script>