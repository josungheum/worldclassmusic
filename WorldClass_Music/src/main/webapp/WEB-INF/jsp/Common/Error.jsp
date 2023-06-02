<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>갤러리아 몰 안내</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/css/default.css'/>" />
</head>
<body>
<div>
	<div class="err_wrapper">
		<div class="div_err" style="margin-top:12%;  ">
			<div class="tl">
				죄송합니다.<br />
				요청하신 페이지를 찾을 수 없습니다.
			</div>
			<div class="desc">
				방문하시려는 페이지의 주소가 잘못 입력되었거나,<br />
				페이지의 주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.<br /><br />
				입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.<br /><br />
				<div class="f_center padt15">
					<input type="button" class="btn_bk w120" value="홈으로" onclick="top.location.href='/'"/>&nbsp;&nbsp;&nbsp;
				</div>
			</div>
		</div>
		<br /><br />
		<span class="fc99">COPYRIGHT ⓒ QVOSS SYSTEM CO., LTD. ALL RIGHTS RESERVED</span>
	</div>
</div>
</body>
</html>



