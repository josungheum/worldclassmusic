<%--
  Name : header_mobile.jsp
  Description : 모바일 Header
  Modification Information
    수정일                    수정자                 수정내용
  -------      --------    ---------------------------
  2019.06.18   이재환                 최초작성
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Enumeration"%>
<%@ page session="true" %>
<style>
<!--
.projectFont { color: white !important;}

.wrap-loading {
	position: fixed;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	background: rgba(0,0,0,0.2);
	filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr='#20000000', endColorstr='#20000000');
	z-index: 1040;
}
.wrap-loading div {
	position: fixed;
	top: 40%;
	left: 40%;
	width: 200px;
	font-size: 14px;
	line-height: 14px;
	padding: 10px;
	background-color: white !important;
	border: 2px solid #5AA2DD;
	font-weight: bold;
	color: #5AA2DD;
}
-->
</style>
<form id="pagesend" method="post">
<input type="hidden" name="sendUrl" id="sendUrl"/>
<input type="hidden" id="aId" value="<c:out value="${sessionScope.id}"/>"/>
<input type="hidden" id="aUid" value="<c:out value="${sessionScope.A_AUID}"/>"/>
<input type="hidden" id="uGid" value="<c:out value="${sessionScope.A_UGID}"/>"/>
<input type="hidden" name="SM_INDEX" id="SM_INDEX"/>
<input type="hidden" id="ldomainIdx" name="ldomainIdx" value="<c:out value="${sessionScope.domainIdx}"/>"/>
<input type="hidden" id="lbrandIdx" name="lbrandIdx" value="<c:out value="${sessionScope.brandIdx}"/>"/>
<input type="hidden" id="lfrancIdx" name="lfrancIdx" value="<c:out value="${sessionScope.francIdx}"/>"/>
<input type="hidden" id="ladminType" name="ladminType" value="<c:out value="${sessionScope.adminType}"/>"/>
<input type="hidden" id="domainIdx" name="domainIdx" value="<c:out value="${sessionScope.domainIdx}"/>"/>
<input type="hidden" id="brandIdx" name="brandIdx" value="<c:out value="${sessionScope.brandIdx}"/>"/>
<input type="hidden" id="francIdx" name="francIdx" value="<c:out value="${sessionScope.francIdx}"/>"/>
<input type="hidden" id="parentMenuId" name="parentMenuId" value="<c:out value="${sessionScope.PARENT_MENU_ID}"/>"/>
<input type="hidden" id="menuId" name="menuId" value="<c:out value="${sessionScope.MENU_ID}"/>"/>
<input type="hidden" id="mobileYn" name="mobileYn" value="Y"/>
</form>
<c:set var="pTitle" value="${sessionScope.MENU_KOR_NAME}" />
<nav class="navbar navbar-inverse navbar-static-top" role="navigation">
	<div class="container text-center">
	    <c:if test="${pTitle != 'QSS' }">
	      <span class="navbar-brand pull-left">
	        <a class="btn btn-default mobile" href="#" role="button" id="btn_back"><span class="glyphicon glyphicon-arrow-left">뒤로</span></a>
	      </span>
	    </c:if>
		<span class="navbar-brand projectFont">
		    <c:out value="${pTitle}"/>
		</span>
	 	<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
			    <li class="navbar-brand" style="font-size: 15px;" >
		        	<a href="javascript:clickLogout();"><i class="glyphicon glyphicon-off"></i> 로그아웃</a>
		        </li>
	        </ul>
		</div>
	</div>
</nav>
<script type="text/javaScript">
<!--
	$(document).ready(function () {
		eventHandle();
		$.sessionCheck();
		loginChk();


	});

	function loginChk() {
		if ($('#aId').val() == '') {
			top.location.href = '/Common/Logout';
			return;
		}
	}
	function clickLogout(){
		BootstrapDialog.confirm({
	        title: '',
	        message: '로그아웃 하시겠습니까?',
	        type: BootstrapDialog.TYPE_WARNING,
	        closable: false,
	        draggable: true,
	        btnCancelLabel: '취소',
	        btnOKLabel: '로그아웃',
	        btnOKClass: 'btn-warning',
	        callback: function (result) {
	            if (result) {
	            	$('#pagesend').attr('action', '/Logout');
	                $('#pagesend').submit();
	            }
	        }
	    });
	}
//-->
</script>