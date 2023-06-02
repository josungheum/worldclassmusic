<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="userVo" name="userform" id="userform" method="post">
	<form:hidden path="brandIdx" />
	<form:hidden path="francIdx" />
	<div class="modal-body">
		<div id="modalId">
			<label class="lb ">사용자명 <font class="fontSet">[필수]</font></label>
			<input type="text" class="form-control" id="name" style="ime-mode:active" maxlength="30" placeholder="사용자명">
		</div><br/>
		<div>
			<label class="lb ">아이디 <font class="fontSet">[필수]</font></label>
			<input type="text" class="form-control clsNotKor" id="id" style="ime-mode:active" maxlength="30" placeholder="아이디">
		</div><br/>
		<div>
			<label>활성화여부</label>
			&nbsp;&nbsp;<input type="checkbox" data-group-cls="btn-group-sm" id="activeYn" data-reverse checked><br />
        </div><br />
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="userSave()" id="procBtn">저장</button>
		<button type="button" class="btn btn-default notDisabled" data-dismiss="modal">닫기</button>
	</div>
</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('#userform #activeYn').checkboxpicker(defaults = {	offLabel: '비활성화',	onLabel: '활성화'});

		$(".clsNotKor").keyup(function(e) {
			if (!(e.keyCode >=37 && e.keyCode<=40)) {
				var v = $(this).val();
				$(this).val(v.replace(/[^a-z0-9\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi,''));
			}
		}).focusout(function(){
			var v = $(this).val();
			$(this).val(v.replace(/[^a-z0-9\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi,''));
        });
	});

	function userSave() {
		var scValue = '';
		var sysAdmin = 'N';

		if ($('#userform #activeYn').prop('checked')) {
			scValue = 'Y';
		} else {
			scValue = 'N';
		}

		$("#userform #name").val($.trim($("#userform #name").val()));
		$("#userform #id").val($.trim($("#userform #id").val()));

		if (!$.formCheck('#userform #name', 'Y', 30, 'N', '사용자 명'))
			return;
		if (!$.formCheck('#userform #id', 'Y', 30, 'N', '아이디'))
			return;

		var adminType = "";
		if($("#userform #francIdx").val() == "0" && $("#userform #brandIdx").val() == "0")
	   		adminType = "ADM0001";
	   	else if($("#userform #francIdx").val() == "0")
   			adminType = "ADM0002";
	   	else
	   		adminType = "ADM0003";

		var param = {
				domainIdx : $('#domainIdx').val(),
				brandIdx : $('#userform #brandIdx').val(),
				francIdx: $("#userform #francIdx").val(),
				name : $('#userform #name').val(),
				id : $('#userform #id').val(),
				password : hex_sha512($('#userform #id').val()).toLowerCase(),
				activeYn : scValue,
				adminType : adminType
		};

		var msg = 'C';

		var callBack = function (result) {
			if (result) {
				var dResult = $.ajaxUtil.ajaxDefault('<c:url value="/UserManagement/UserCreate"/>', param);
				if (dResult.resultCode == 0){
					$.modalCommon.close();
					commMessage(msg,'사용자');
					userList(1);
			    }
			}
		}
		commonSave(msg, callBack);
	}
</script>