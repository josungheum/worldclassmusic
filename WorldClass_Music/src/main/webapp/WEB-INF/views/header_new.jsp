<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.Enumeration"%>
<%@ page session="true" %>
<%-- <%
	String referer = request.getHeader("REFERER");

	if(referer != null && referer.length() > 0 && (String)session.getAttribute("id") != null) {
	}
	else {
		out.println("<script type=\"text/javaScript\">");
		out.println("top.location.href = '/Logout';");
		out.println("history.back();");
		out.println("</script>");
	}
%> --%>
<script type="text/javaScript">
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

	$(document).ready(function(){
		/* var width = $(".navbar").width();
		$("#menuList > .dropdown > .dropdown-menu").css({'width' : width + "px"}); */

		$("#menuList > .dropdown").each(function() {
			var width = $(".navbar").width();
			var offset = $(this).offset();
			$(this).children(".dropdown-menu").css({
				'width' : width + "px",
				"margin-left" : "-" + offset.left + "px",
				"padding-left" : offset.left + "px",
				"background-color": "transparent",
				"border" : "none",
				"border-radius" : "0px"
			});
		});

		$("#menuList").hover(
	        function() {
	            $('#menuBg').stop(true, true).slideDown("400");
	            $(this).toggleClass('open');
	        },
	        function() {
	            $('#menuBg').stop(true, true).slideUp("400");
	            $(this).toggleClass('open');
	        }
	    );

	    $(".dropdown").hover(
	        function() {
	            $('.dropdown-menu', this).not('.in .dropdown-menu').stop(true, true).slideDown("400");
	            $(this).toggleClass('open');
	        },
	        function() {
	            $('.dropdown-menu', this).not('.in .dropdown-menu').stop(true, true).hide();
	            $(this).toggleClass('open');
	        }
	    );
	    $(".dropdown").on("click", function(){
	    		return;
	    });
	});
</script>
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
<input type="hidden" id="groupIdx" name="groupIdx" value="0"/>
<input type="hidden" id="francIdx" name="francIdx" value="<c:out value="${sessionScope.francIdx}"/>"/>
<input type="hidden" id="parentMenuId" name="parentMenuId" value="<c:out value="${sessionScope.PARENT_MENU_ID}"/>"/>
<input type="hidden" id="menuId" name="menuId" value="<c:out value="${sessionScope.MENU_ID}"/>"/>

</form>
<style type="text/css">
#findTable.dataTable tbody tr.selected {
  background-color: #B2CCFF;
}

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
	top: 50%;
	left: 50%;
	width: 200px;
	font-size: 14px;
	line-height: 14px;
	padding: 10px;
	background-color: white !important;
	border: 2px solid #5AA2DD;
	font-weight: bold;
	color: #5AA2DD;
}
li.iconChange > a .jstree-themeicon {background-image: none;}
</style>
<div class="wrap-loading" style="display: none;"><div>DATA LOADING...</div></div>

<nav class="navbar navbar-static-top" role="navigation">
	<div class="navbar-header" style="background-color: white;">
		<img onclick="javascript:location.href='<c:url value='/Resource/Main'/>'" style="cursor:pointer; height: 50px; background-size: cover;" src="<c:url value='/Content/images/logo.png'/>"/>
		
		<%-- 시스템 관리자일때만 대쉬보드 링크 --%>
		<%-- <c:if test="${sessionScope.adminType ne null and sessionScope.adminType ne '' and sessionScope.adminType eq 'ADM0001'}">
			<img onclick="javascript:location.href='<c:url value='/Resource/Main'/>'" style="cursor:pointer; height: 75px;" src="<c:url value='/Content/images/logo.png'/>"/>
		</c:if> --%>

		<%--  브랜드, 매장 관리자일때는 브랜드 또는 매장 메인 화면 --%>
		<%-- <c:if test="${sessionScope.adminType ne null and sessionScope.adminType ne '' and sessionScope.adminType ne 'ADM0001'}">
			<c:if test="${sessionScope.adminType eq 'ADM0002'}">
					<c:if test="${sessionScope.brandMainImgPath ne null and sessionScope.brandMainImgPath ne ''}">
						<img id="brandMainImgPath" onclick="clickMenuGoHome(1); return false;" style="cursor:pointer; height: 75px;" src="${sessionScope.brandMainImgPath}"/>
					</c:if>

					<c:if test="${sessionScope.brandMainImgPath eq null or sessionScope.brandMainImgPath eq ''}">
						<img onclick="javascript:location.href='<c:url value='/BrandManagement/Main'/>'" style="cursor:pointer; height: 75px;" src="<c:url value='/Content/images/logo.png'/>"/>
					</c:if>
			</c:if>

			<c:if test="${sessionScope.adminType eq 'ADM0003'}">
					<c:if test="${sessionScope.brandMainImgPath ne null and sessionScope.brandMainImgPath ne ''}">
						<img id="brandMainImgPath" onclick="clickMenuGoHome(2); return false;" style="cursor:pointer; height: 75px;" src="${sessionScope.brandMainImgPath}"/>
					</c:if>

					<c:if test="${sessionScope.brandMainImgPath eq null or sessionScope.brandMainImgPath eq ''}">
						<img onclick="javascript:location.href='<c:url value='/FranchiseeManagement/Main'/>'" style="cursor:pointer; height: 75px;" src="<c:url value='/Content/images/logo.png'/>"/>
					</c:if>
			</c:if>
		</c:if> --%>
    </div>
  	<div class="collapse navbar-collapse pull-left" id="navbar-collapse">
  		<c:set var="menuParentList" value="${sessionScope.menuList}"/>
  		<c:set var="menuSubList" value="${sessionScope.menuList}"/>
		<ul class="nav navbar-nav" id="menuList">
		<c:forEach items="${menuParentList}" var="parentList">
			<c:if test="${parentList.parentMenuId eq null or parentList.parentMenuId eq ''}">
				<c:set var="menuParentId" value="${parentList.menuId}"/>
				<li class="dropdown <c:if test="${parentList.menuId eq sessionScope.PARENT_MENU_ID}">selected</c:if>" id="MAIN_MENU_${parentList.menuId}">
					<div class="active-bar"></div>
					<a href="#" class="dropdown-toggle collapsed" data-toggle="dropdown" role="button" aria-expanded="false"><c:out value="${parentList.menuKorName}"/></a>
					<ul class="dropdown-menu" role="menu">
					<c:forEach items="${menuSubList}" var="subList">
						<c:if test="${menuParentId eq subList.parentMenuId}">
							<li><a id="SUB_MENU_${subList.menuId}" href="<c:url value='${subList.menuLinkAddr}'/>"><c:out value="${subList.menuKorName}"/></a></li>
						</c:if>
					</c:forEach>
					</ul>
				</li>
			</c:if>
	  	</c:forEach>
		</ul>
    </div>
  	<div class="navbar-custom-menu">
		<ul class="nav navbar-nav">
			<li class="navbar-brand" style="font-size: 15px;">
				<a href="#" style="display: inline; text-align: center;"><i class="glyphicon glyphicon-user"></i><span style="color:#ddd;"> <c:out value="${sessionScope.name}" />님</span></a>
        		</li>
        		<c:if test="${sessionScope.adminType ne null and sessionScope.adminType ne '' and sessionScope.adminType eq 'ADM0001'}">
        			<li class="navbar-brand" style="font-size: 15px; background-color:#444444;">
		        		<a href="<c:url value='/UserManagement/Main'/>" style="color:#FFFFFF;"><i class="glyphicon glyphicon-cog"></i><span style="color:#fff;"> 정보수정</span></a>
		        	</li>
        		</c:if>

	        	<li class="navbar-brand" style="font-size: 15px; background-color:#ddd;" >
	        		<a href="javascript:clickLogout();"><i class="glyphicon glyphicon-off"></i><span style="color:#000;"> 로그아웃</span></a>
	        	</li>
        </ul>
	</div>
</nav>
<div class="clear:both;"></div>
<div id="menuBg" class="shadow" style="width: 100%; height: 76px; background-color: #ffffff; margin: 0px; display: none;"></div>

<script>
function clickMenuGoHome(type){
	if(type == '1'){
		$('#pagesend').attr('action', '/BrandManagement/Main');
        $('#pagesend').submit();
	}else{
		$('#pagesend').attr('action', '/FranchiseeManagement/Main');
        $('#pagesend').submit();
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
</script>