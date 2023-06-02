<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- 캐시 방지 -->
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<title>WorldClass Music</title>
<link rel="shortcut icon" href="<c:url value='/Content/images/favicon.ico'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/BootStrap/css/bootstrap.min.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/AdminLTE/dist/css/AdminLTE.min.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/AdminLTE/dist/css/skins/skin-blue.min.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/css/default.css'/>" />
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css"> -->
<script src="<c:url value='/Content/Jquery/jquery-3.1.1.min.js'/>"></script>
<script src="<c:url value='/Content/BootStrap/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/Content/BootStrap/js/bootstrap-dialog.js'/>"></script>
<script src="<c:url value='/Content/AdminLTE/dist/js/app.min.js'/>"></script>
<script src="<c:url value='/Content/site/common.js'/>"></script>
<script src="<c:url value='/Content/site/sha512.js'/>"></script>
</head>
<body class="hold-transition ">
<div class="">
  	<tiles:insertAttribute name="body" />
</div>
</body>
</html>