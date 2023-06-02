<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String sNames = "";
	String sNamesValue = "";

	out.println("<form name=\"authFrm\" id=\"authFrm\" method=\"post\">");
	out.println("<input type=\"hidden\" name=\"sendUrl\" id=\"sendUrl\" value=\"" + request.getAttribute("sendUrl") + "\">");
	out.println("</form>");
%>
<script type="text/javascript">
	$(document).ready(function() {
		var sendUrl = $('#sendUrl').val();
		var $layerPopupObj = $('#layerPopupBox');
		var width = $(window).width() - ($layerPopupObj.width() / 2);
		var left = width;
		var top = ($(window).scrollTop() + ($(window).height() - $layerPopupObj.height()) / 2);

		if (sendUrl != '') {
			$('#authFrm').attr('target', '_parent');
			$('#authFrm').attr('action', $('#sendUrl').val());
	   		$('#authFrm').submit();
		}
		else {
			$.modalCommon.alertView("접속권한이 없습니다.\n\n유지보수팀에 문의 하세요.");
			history.back();
		}

		$layerPopupObj.css({'left':left,'top':top, 'position':'absolute'});
		$('body').css('position','relative').append($layerPopupObj);
	});
</script>
<div id="layerPopupBox"><img src="<c:url value='/Content/images/loading_map.gif'/>"/></div>



