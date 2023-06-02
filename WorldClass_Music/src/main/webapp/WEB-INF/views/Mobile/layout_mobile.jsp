<%--
  Name : XXXX.jsp
  Description : JSP 설명
  Modification Information
   수정일     수정자           수정내용
  -------      --------    ---------------------------

--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<!-- 캐시 방지 -->
		<meta http-equiv="Cache-Control" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<title>WorldClass Music</title>
		<jsp:include page="/WEB-INF/jsp/Common/metaCommonMobile.jsp" />
	</head>
	<body class="hold-transition skin-blue layout-top-nav">
		<div class="wrapper">
			<header class="main-header">
				<tiles:insertAttribute name="header" />
			</header>
			<div class="content-wrapper">
				<section class="content mx-auto">
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12" id="bodyDiv">
							<tiles:insertAttribute name="body" />
						</div>
					</div>
				</section>
			</div>
			<footer class="main-footer navbar-fixed-bottom">
				<tiles:insertAttribute name="footer" />
			</footer>
		</div>
	</body>
</html>