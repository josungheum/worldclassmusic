<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form:form commandName="CmsqVo" name="previewFrm" id="previewFrm" method="post">
<div id="bgimg" style="width:100%; height:100%; background-size: cover;">
   	<img src="" id="imagepreview" style="width:100%; height:100%;">
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
</div>
</form:form>
<script type="text/javaScript">
	$(document).ready(function () {
		$('.modal-dialog').css('width', '850px');
	});
</script>