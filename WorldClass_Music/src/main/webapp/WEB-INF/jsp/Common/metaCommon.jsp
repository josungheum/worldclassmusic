<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <link type="text/css" rel="stylesheet" href="<c:url value='/Content/BootStrap/css/mbr-additional.css'/>" /> --%>


<link rel="shortcut icon" href="<c:url value='/Content/images/favicon.ico'/>">

<!-- 버튼 이미지 -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
<link rel="stylesheet" type="text/css" href="/Content/css/font-awesome.css">

<!-- 관리자 css -->
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/AdminLTE/dist/css/AdminLTE.min.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/AdminLTE/dist/css/skins/skin-blue.min.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/AdminLTE/plugins/datatables/dataTables.bootstrap.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/AdminLTE/plugins/daterangepicker/daterangepicker.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/AdminLTE/plugins/timepicker/bootstrap-timepicker.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/AdminLTE/plugins/timepicker/bootstrap-timepicker.min.css'/>" />

<!-- js 트리 테마 -->
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/jstree/assets/dist/themes/default/style.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/jstree/assets/dist/themes/proton/style.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/jstree/assets/dist/themes/default-dark/style.css'/>" />

<!-- 드래그 앤 드롭 파일 업로드 -->
<%-- <link type="text/css" rel="stylesheet" href="<c:url value='/Content/Dropzone/basic.min.css'/>" /> --%>
<%-- <link type="text/css" rel="stylesheet" href="<c:url value='/Content/Dropzone/dropzone.min.css'/>" /> --%>

<!-- 기존 리소스 화면 -->
<%-- <link type="text/css" rel="stylesheet" href="<c:url value='/Content/circle.css'/>" /> --%>

<!-- jquery -->
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/jquery-ui-1.8.css'/>" />

<!-- 공통 css -->
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/css/default.css'/>" />

<!-- bootstrap -->
<link type="text/css" rel="stylesheet" href="<c:url value='/Content/BootStrap/css/bootstrap.min.css'/>" />

<!-- ssgfTemplate -->
<!--  link rel="stylesheet" type="text/css" href="<c:url value='/Content/site/SH/ssgfTemplate.css'/>" /-->
<link rel="stylesheet" type="text/css" href="<c:url value='/Content/site/SH/ImageList.css'/>" />

<!-- jquery -->
<script type="text/javascript" src="<c:url value='/Content/Jquery/jquery-3.1.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/Jquery/jquery.number.min.js'/>"></script>
<!-- bootstrap -->
<script type="text/javascript" src="<c:url value='/Content/BootStrap/js/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/BootStrap/js/bootstrap-dialog.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/BootStrap/js/bootstrap-checkbox.js'/>"></script>

<!-- adminlte -->
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/dist/js/app.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/fastclick/fastclick.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/datatables/jquery.dataTables.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/datatables/dataTables.bootstrap.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/jQueryUI/jquery-ui.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/knob/jquery.knob.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/morris/morris.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/fastclick/fastclick.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/daterangepicker/moment.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/daterangepicker/daterangepicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/datepicker/bootstrap-datepicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/timepicker/bootstrap-timepicker.js'/>"></script>

<!-- 공통 -->
<script type="text/javascript" src="<c:url value='/Content/site/sha512.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/Jquery/jquery.form.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/SH/TabControl.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/datePicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/fileupload.js'/>"></script>

<!-- js tree -->
<script type="text/javascript" src="<c:url value='/Content/jstree/assets/dist/jstree.min.js'/>"></script>

<!-- form태그  -->
<script type="text/javascript" src="<c:url value='/Content/Jquery/commonPlugin.form.js'/>"></script>

<!-- 드래그 앤 드롭 파일 업로드 -->
<%-- <script type="text/javascript" src="<c:url value='/Content/Dropzone/dropzone.js'/>"></script> --%>

<!-- 엑셀 버튼  -->
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/datatables/dataTables.buttons.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/datatables/jszip.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/datatables/buttons.html5.min.js'/>"></script>

<!-- jquery.mask.js -->
<script type="text/javascript" src="<c:url value='/Content/AdminLTE/plugins/jquery.mask.js'/>"></script>

<!-- ssgfTemplate -->
<script type="text/javascript" src="<c:url value='/Content/site/SH/jquery.fittext.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/SH/Please-compressed.js'/>"></script>
<!-- script type="text/javascript" src="<c:url value='/Content/site/SH/ssgfLayerTemplate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/SH/ssgfTemplate.js'/>"></script-->
<script type="text/javascript" src="<c:url value='/Content/site/SH/ImageList.js'/>"></script>
<script type="text/javascript" src="<c:url value='/Content/site/tablednd/tablednd.js'/>"></script>


