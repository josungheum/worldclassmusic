<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="brandVo" name="user" id="user" method="post">
<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">운영자 관리</div>
	</div>
	<div class="box-body">
		<table id="userTable" class="table table-striped userList">
			<thead>
				<tr>
					<th><input type="checkbox" name="ARRIDX_ALL" onclick="$.checkboxUtil.checkAll(this, 'checkboxArr');" /></th>
					<th>No</th>
					<th>운영자 명</th>
					<th>아이디</th>
					<th>생성일</th>
					<th>활성화</th>
					<th>비밀번호 변경</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2" data-toggle="modal" id="addBtn" onclick="userAdd();"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
			<button type="button" class="btn btn-delete" onclick="userDelete()" id="delBtn"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
		</div>
	</div>
</div>




<input type="hidden" id="id"/>
<input type="hidden" id="usersIdx"/>
</form:form>

<div class="modal fade" id="myModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" >&times;</button>
	          <h4 class="modal-title" id="title">비밀번호 변경</h4>
	        </div>
	        <div class="modal-body">
	          	<div>
	          		<span class="icon-bar">신규 비밀번호(대/소문자, 숫자, 특수문자 포함 9~30자리)</span>
		          	<input type="password" id="PW1" class="form-control" style="ime-mode:disabled;" maxlength="30" onKeyPress="$.enterUtil.index(this.form, 2);"/>
	          	</div>
	          	<br/>
	          	<div>
	          		<span class="icon-bar">비밀번호 확인(대/소문자, 숫자, 특수문자 포함 9~30자리)</span>
		          	<input type="password" id="PW2" class="form-control" style="ime-mode:disabled;" maxlength="30" onKeyPress="$.enterUtil.index(this.form, 2);"/>
	          	</div>
	          	<br/>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-primary" onclick="pwdChange()">저장</button>
	          <button type="button" class="btn btn-default notDisabled" onclick="pwdClose()" data-dismiss="modal">닫기</button>
	        </div>
		</div>
	</div>
</div>

<script type="text/javaScript">
	$(document).ready(function () {
		userList();
	});

	function pwdClose(){
		$('#PW1').val("");
		$('#PW2').val("");
	}

	function userAdd() {
		$.sessionCheck();
		$.modalCommon.loadDataView('운영자 생성', '<c:url value="/UserManagement/UserFormNullTemp"/>', {brandIdx:'0', francIdx:'0'});
	}

	function userDelete() {
		var checkboxArr = $.formOtherUtil.isArrNameCheck('checkboxArr');

		var callBack = function (result) {
			if (result) {
                var param = {checkboxArr: checkboxArr };
                var dResult = $.ajaxUtil.ajaxArray('<c:url value="/UserManagement/UserDelete"/>', param);

                if (dResult.resultCode == 0){
                	commMessage('D','운영자');
                	userList($('#brandIdx').val());
                }
                else {
                }
            }
        }
		commonDelete(checkboxArr, callBack);


	}

	function userList() {

		var optObject = {};

		optObject.id = "#userTable";
		optObject.url = '<c:url value="/UserManagement/UserList"/>';
		optObject.arrColumnDefs = [
			{
		    	"targets": [2,3]
		       ,"className": "text-center tl270"
	    	   ,'render': function (data, type, full, meta) {
           	   	   return '<div class="ellipsis270">'+fnDataTableRenderText(data)+'</div>';
               }
		    },
			{
	        	'width': '5%',
	            'targets': 0,
	            "orderable": false,
	            'data': 'idx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta)
	            {
	            	return '<input type="checkbox" value="' + data + '" id="checkboxArr" name="checkboxArr" onclick="$.checkboxUtil.checkAllChk(this, \'checkboxArr\');"/>';
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
	        	'width': '15%',
	            'targets': 5,
	            'data': 'activeYn',
	            'type': 'date',
	            'className': 'text-center',
	            'render': function (data, type, full, meta) {
	            	if(fnDataTableRenderText(data) == 'Y')
	            		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.usersIdx+'" onchange="userActive(\'' + full.usersIdx + '\');" data-reverse checked>';
	            	else
	               		return '<input type="checkbox" data-group-cls="btn-group-sm" class="activeYn" id="active'+full.usersIdx+'" onchange="userActive(\'' + full.usersIdx + '\');" data-reverse>';
	            }
	        },
	        {
	        	'width': '15%',
	            'targets': 6,
	            'data': 'idx',
	            'className': 'text-center',
	            'render': function (data, type, full, meta)
	            {
	            	return '<button type="button" class="btn btn-warning" onclick="PwdReset(\'' + full.id + '\', \'' + full.usersIdx + '\');" >비밀번호 변경</button>';
	            }
	        },{
		    	"targets": [4]
		       ,"className": "text-center"
	    	   ,'render': function (data, type, full, meta) {
	    		   return fnDataTableRenderText(data);
               }
		    }
	    ];
	   	optObject.arrColumns = [
	   		{data: "usersIdx"},
	   		{data: "rowIdx"},
            {data: "name"},
            {data: "id"},
            {data: "regDate"},
            {data: "activeYn"}
        ];

	   	var adminType = "ADM0001";

		optObject.serverParams = function ( aoData ) {
			aoData.push( { "name": "domainIdx", "value": $("#domainIdx").val() } );
            aoData.push( { "name": "adminType", "value": adminType } );
            aoData.push( { "name": "defaultOrderName"	, "value": "name" } );
            aoData.push( { "name": "defaultOrderType"	, "value": "ASC" } );
        }

		optObject.fnDrawCallback = function (data){
			$('.activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});
		}

		optObject.language = {"search": "아이디"};

		var mTable = commonPagingDateTable(optObject);


	}

	//활성화 공통으로 변경
	function userActive(val)
	{
		commonActive(val, { usersIdx: val, domainIdx : $("#domainIdx").val() }, '<c:url value="/UserManagement/UserActive"/>');
	}

	function PwdReset(id, usersIdx) {
        $('#id').val(id);
        pwdClose();
        $('#usersIdx').val(usersIdx);
		$('#myModal').modal('show');
		$('#myModal').on('shown.bs.modal', function () {
		    $('#PW1').focus();
		});
	}

	function pwdChange() {
	    if (!$.formCheck('#PW1', 'Y', 30, 'N', '신규 비밀번호')) return;
	    if (!$.formOtherUtil.isPwdCheck($('#PW1').val())) {
	        $('#PW1').focus();
	        $.modalCommon.alertView('비밀번호는 영문자, 숫자, 특수문자 포함 9~30자리여야 합니다.');
			return;
	    }
	    if ($.formOtherUtil.isPwdOverCheck($('#PW1').val())) {
	        $.modalCommon.alertView('4자리 이상의 반복 문자, 숫자는 사용하실 수 없습니다.');
	        return;
	    }
	    if ($('#id').val().length >= 4) {
	        for (var i = 3; i < $('#id').val().length; i++) {
	            if ($('#PW1').val().indexOf($('#id').val().substring(i - 3, i + 1)) != -1) {
	                $.modalCommon.alertView('아이디와 4자리수 이상 동일한 비밀번호를 사용할수 없습니다.');
	                return;
	            }
	        }
	    }
	    if (!$.formCheck('#PW2', 'Y', 20, 'N', '비밀번호 확인')) return;
	    if ($('#PW1').val() != $('#PW2').val()) {
	        $('#PW2').focus();
	        $.modalCommon.alertView( '비밀번호를 확인 하세요.');
			return;
		}

		var param = {usersIdx: $('#usersIdx').val(), password: hex_sha512($('#PW1').val()), domainIdx : $("#domainIdx").val()};
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/UserManagement/PwdChange/"/>', param);

		if (result.resultCode == 0){
			$('#myModal').modal('hide');
            $.modalCommon.alertView('처리되었습니다.');
            $('#PW1').val('');
            $('#PW2').val('');
            userList();
        }
	}
</script>