<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/jsp/Common/metaCommon.jsp" />
<style>
	.userList thead tr th{
		text-align : center;
	}
</style>
<div class="box box-widget">
	<div class="box-header with-border">
		<div class="user-block">
			<i class="fa fa-fw fa-gear"></i>이용내역 목록
		</div>
		<div class="box-tools">
			<button type="button" class="btn btn-box-tool" data-widget="collapse">
				<i class="fa fa-minus"></i>
			</button>
		</div>
	</div>
	<div class="box-body">
		<table id="mTable" class="table table-bordered table-striped userList">
			<thead>
				<tr>
					<th>No</th>
					<th>사용자ID</th>
					<th>Main</th>
					<th>Sub</th>
					<th>날짜</th>
					<th>IP</th>
					<th>Detail</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<input type="hidden" id="SM_PINDEX">

<script type="text/javaScript">
	$(document).ready(function () {
		LogList();
	});
	
	function LogList() {
		var mTable = $('#mTable').DataTable({
			destroy:true,
			responsive:true,
			ordering:false,
			searching:true,
			processing:true,
			serverSide:true,
			lengthMenu:[[10, 20, 50, 100, -1], [10, 20, 50, 100, 'ALL']],
			ajax: {
				'url' : '<c:url value="/UserActionLog/PagingList"/>',
				'type' : 'POST',
				'data' : function(param) {
					param.STARTNUM = param.start;
					param.PAGELENGTH = param.length;
					param.SEARCHKEY = param.search.value;
					param.DRAWNUM = param.draw;
				}
			},
			columnDefs : [
				{
					'width': '5%',
					'targets' : 0,
					'data' : 'rnum',
					'className': 'text-center',
					'render' : function(data, type, full, meta) {
						return data;
					}
				},
				{
					'width': '20%',
					'targets' : 1,
					'data' : 'userid',
					'render' : function(data, type, full, meta) {
						return fnDataTableRenderText(data);
					}
				},
				{
					'width': '20%',
					'targets' : 2,
					'data' : 'ctrlname',
					'render' : function(data, type, full, meta) {
						return fnDataTableRenderText(data);
					}
				},
				{
					'width': '20%',
					'targets' : 3,
					'data' : 'actionname',
					'render' : function(data, type, full, meta) {
						return fnDataTableRenderText(data);
					}
				},
				{
					'width': '15%',
					'targets' : 4,
					'data' : 'logdate',
					'className': 'text-center',
					'render' : function(data, type, full, meta) {
						return fnDataTableRenderText(data);
					}
				},
				{
					'width': '15%',
					'targets' : 5,
					'data' : 'userip',
					'className': 'text-center',
					'render' : function(data, type, full, meta) {
						return fnDataTableRenderText(data);
					}
				},
				{
					'width': '5%',
					'targets' : 6,
					'data' : 'log_IDX',
					'className': 'text-center',
					'render' : function(data, type, full, meta) {
						return '<button type="button" class="btn btn-primary" data-toggle="modal" onclick="Detail('+fnDataTableRenderText(data)+');">View</button>';
					}
				}
			]
		});
	}
	
	function Detail(val) {
		$.sessionCheck();
	    $.modalCommon.loadView('Detail View', '<c:url value="/UserActionLog/Form/"/>'+val);
	}
	
</script>