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

		<jsp:include page="/WEB-INF/jsp/Common/metaCommon.jsp" />
	</head>
	<!-- form id="pagesend" method="post">
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
	</form-->
	<body class="hold-transition skin-blue layout-top-nav">
		<div class="wrapper">
			<header class="main-header">
				<tiles:insertAttribute name="header" />
			</header>
			<div class="content-wrapper" style="min-height: 875px;">
				<section class="content">
					<div class="row">
						<c:if test="${sessionScope.PARENT_MENU_ID ne 'DSH00' && sessionScope.PARENT_MENU_ID ne 'SET00' && sessionScope.PARENT_MENU_ID ne 'RET00'}">
						<div class="col-md-3 col-sm-3 col-xs-3 col-lg-3">
							<div class="box box-widget">
								<div class="box-header">
									<div class="user-block under-line"><i class="fa fa-fw fa-gear"></i><c:out value="${sessionScope.SUB_MENU_NAME}"/></div>
								</div>
								<div class="box-body" id="scrollbody">
									<div class="box-tools pull">
										<div class="has-feedback">
											<input type="hidden" class="form-control input-sm notDisabled" placeholder="Search..." id="treeSearch">
<!-- 											<span class="glyphicon glyphicon-search form-control-feedback text-muted"></span> -->
										</div>
									</div>
									<div style="margin-top:4px;  overflow-x: hidden; overflow-y: auto;" class="proton-demo" id="treeList" ></div>
								</div>
								<c:if test="${sessionScope.PARENT_MENU_ID eq 'STO00'}">
									<%-- 시스템 관리자일때만 대쉬보드 링크 --%>
									<c:if test="${sessionScope.adminType ne null and sessionScope.adminType ne '' and sessionScope.adminType eq 'ADM0001'}">
										<c:if test="${sessionScope.MENU_ID ne 'STO02'}">
											<div class="box-footer">
												<div class="text-center">
													<button type="button" class="btn btn-primary2 btn-block2 notDisabled" data-toggle="modal" onclick="treeAdd('${sessionScope.MENU_ID}');"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
													<button type="button" class="btn btn-delete btn-block2 notDisabled" data-toggle="modal" onclick="treeDel('${sessionScope.MENU_ID}');"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
												</div>
											</div>
										</c:if>
										<c:if test="${sessionScope.MENU_ID eq 'STO02'}">
											<div class="box-footer">
												<div class="text-center">
													<button type="button" class="btn btn-primary2 btn-block4 notDisabled" data-toggle="modal" onclick="treeAdd('GROUP');"><i class="glyphicon glyphicon-plus mr-5"></i>그룹등록</button>
													<button type="button" class="btn btn-primary3 btn-block4 notDisabled" data-toggle="modal" onclick="treeEdit('GROUP');"><i class="glyphicon glyphicon-pencil mr-5"></i>그룹수정</button>
													<button type="button" class="btn btn-delete btn-block4 notDisabled" data-toggle="modal" onclick="treeDel('GROUP');"><i class="glyphicon glyphicon-trash mr-5"></i>그룹삭제</button>
												</div>
												<div class="text-center" style="margin-top: 10px;">
													<button type="button" class="btn btn-primary2 btn-block3 notDisabled" data-toggle="modal" onclick="treeAdd('${sessionScope.MENU_ID}');"><i class="glyphicon glyphicon-plus mr-5"></i>사이트등록</button>
													<button type="button" class="btn btn-delete btn-block3 notDisabled" data-toggle="modal" onclick="treeDel('${sessionScope.MENU_ID}');"><i class="glyphicon glyphicon-trash mr-5"></i>사이트삭제</button>
												</div>
											</div>
										</c:if>
									</c:if>

									<%--  브랜드관리자는 매장관리 화면에서 매장 추가/삭제 가능하도록--%>
									<c:if test="${sessionScope.adminType ne null and sessionScope.adminType ne '' and sessionScope.adminType ne 'ADM0001'}">
										<c:if test="${sessionScope.adminType eq 'ADM0002'}">
											<c:if test="${sessionScope.MENU_ID eq 'STO02'}">
												<div class="box-footer">
													<div class="text-center">
														<button type="button" class="btn btn-primary2 btn-block2 notDisabled" data-toggle="modal" onclick="treeAdd('${sessionScope.MENU_ID}');"><i class="glyphicon glyphicon-plus mr-5"></i>등록</button>
														<button type="button" class="btn btn-delete btn-block2 notDisabled" data-toggle="modal" onclick="treeDel('${sessionScope.MENU_ID}');"><i class="glyphicon glyphicon-trash mr-5"></i>삭제</button>
													</div>
												</div>
											</c:if>
										</c:if>
									</c:if>
								</c:if>
							</div>
						</div>
						<div class="col-md-9 col-sm-9 col-xs-9 col-lg-9" id="bodyDiv">
							<tiles:insertAttribute name="body" />
						</div>
						</c:if>
						<!-- 통합 상환판, 설정관리등에서 사용 -->
						<c:if test="${sessionScope.PARENT_MENU_ID eq 'DSH00' || sessionScope.PARENT_MENU_ID eq 'SET00' || sessionScope.PARENT_MENU_ID eq 'RET00'}">
						<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12" id="bodyDiv">
							<tiles:insertAttribute name="body" />
						</div>
						</c:if>
					</div>
				</section>
			</div>
			<footer class="main-footer">
				<tiles:insertAttribute name="footer" />
			</footer>
		</div>
	</body>
</html>