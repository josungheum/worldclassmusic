<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/css/login.css'/>" />
<script type="text/javaScript">
	$(document).ready(function () {
		if (document.referrer != '' && performance.navigation.type == 0) {
			$.modalCommon.alertView('로그아웃 되었습니다.');
		}

		$('#user').focus();

		$(".clsNotKor").keyup(function(e) {
			if (!(e.keyCode >=37 && e.keyCode<=40)) {
				var v = $(this).val();
				$(this).val(v.replace(/[^a-z0-9\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi,''));
			}
		});

		// width로 모바일 여부를 체크하는 로직 START - 만약 플랫폼으로만 하겠다면 빼도 됨
		window.isMobile = false;
		if($(window).width() < 768) {
			window.isMobile = true;
		}

		$(window).resize(function() {
			if($(this).width() < 768) {
				window.isMobile = true;
			}
		});
		// width로 모바일 여부를 체크하는 로직 END - 만약 플랫폼으로만 하겠다면 빼도 됨
	});

	function login() {
		if (!$.formCheck('#user', 'Y', 30, 'N', '아이디')) {
			return;
		}

		if (!$.formCheck('#pass', 'Y', 30, 'N', '비밀번호')) {
			return;
		}

		var url = '<c:url value="/LoginCheck"/>';
		var param = { id: $('#user').val(), MID: hex_sha512($('#user').val()).toLowerCase(), password: hex_sha512($('#pass').val()).toLowerCase(), login:true };
		var result = $.ajaxUtil.ajaxDefault(url, param, $('#user'), $('#pass'));

		if(result.resultCode == 0 && result.messages == null){
			$('#pageSend').attr('action', '${pageContext.request.contextPath}/MainFeed/Main');
			$('#user').val(null);
			$('#pass').val(null);
			$('#pageSend').submit();
		} else if (result.resultCode == 0){
		}

		return;
	}

	function pwdChange() {
	    if (!$.formCheck('#PW1', 'Y', 20, 'N', '신규 비밀번호')) return;
	    if (!$.formOtherUtil.isPwdCheck($('#PW1').val())) {
	        $('#PW1').focus();
	        $.modalCommon.alertView('비밀번호는 영문자, 숫자, 특수문자 포함 9~30자리여야 합니다.');
			return;
	    }
	    if ($.formOtherUtil.isPwdOverCheck($('#PW1').val())) {
	        $.modalCommon.alertView('4자리 이상의 반복 문자, 숫자는 사용하실 수 없습니다.');
	        return;
	    }
	    if ($('#MID').val().length >= 4) {
	        for (var i = 3; i < $('#MID').val().length; i++) {
	            if ($('#PW1').val().indexOf($('#MID').val().substring(i - 3, i + 1)) != -1) {
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

		var url = '<c:url value="/PwdChange"/>';
		var param = { ID: $('#MID').val(), IDX: $('#idx').val(), password: hex_sha512($('#password').val()), NEW_PWD: hex_sha512($('#PW1').val()), 'login':true };
		var result = $.ajaxUtil.ajaxDefault(url, param, $('#PW1'), [$('#PW1'), $('#PW2')]);

		if (result.resultCode == 0) {
		    $('#password').val($('#PW2').val());
    		$('#pwChange').val('1');
    		$('#myModal').modal('hide');
    		BootstrapDialog.confirm({
    		    title: '',
    		    message: '정상 처리 되었습니다. 로그인 하시겠습니까?',
    		    type: BootstrapDialog.TYPE_WARNING,
    		    closable: true,
    		    draggable: true,
    		    btnOKLabel: '로그인',
    		    btnCancelLabel: '닫기',
    		    btnOKClass: 'btn-warning',
    		    callback: function (result) {
    		        if (result) {
    		            if (result) {
    		                login();
    		            }
    		            else {
    		                $.modalCommon.close();
    		            }
    		        }
    		    }
    		});
		}
	}
</script>
<form:form commandName="IndexVo" id="pageSend" cssStyle="margin-top:10em;">
<input type="hidden" id="pwChange"/>
<input type="hidden" id="sendUrl"/>
<input type="hidden" id="IDX"/>
<div class="login-wrap">
	<div class="login-html">
		<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab" style="font-size: 38px !important;">WorldClass Music</label>
		<input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab"></label>
		<div class="login-form">
			<div class="sign-in-htm">
				<div class="group">
					<label for="user" class="label">Username</label>
					<input id="user" type="text" class="input inInput">
				</div>
				<div class="group">
					<label for="pass" class="label">Password</label>
					<input id="pass" type="password" class="input pwInput clsNotKor" data-type="password">
				</div>
				<div class="group">
					<label for="login" class="label"></label>
					<br>
					<br>
					<br>
					<button class="button" value="login" onclick="login();">로그인</button>
				</div>
				<div class="hr"></div>
			</div>
			
		</div>
	</div>
</div>
</form:form>
