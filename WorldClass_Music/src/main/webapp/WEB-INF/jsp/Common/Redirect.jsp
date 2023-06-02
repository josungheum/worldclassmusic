<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Enumeration"%> 
<form name="reFrm" id="reFrm" method="post">
    <input type="hidden" name="sendUrl" id="sendUrl" value="<%=request.getAttribute("sendUrl")%>" />
</form>
<script type="text/javascript">
	$(document).ready(function() {	
		var sendUrl = $('#sendUrl').val();		
		var $layerPopupObj = $('#layerPopupBox');
		var width = $(window).width() - ($layerPopupObj.width() / 2);
		var left = width;
		var top = ($(window).scrollTop() + ($(window).height() - $layerPopupObj.height()) / 2);		
				
		if (sendUrl != '') {
			$('#reFrm').attr('action', $('#sendUrl').val());
	   		$('#reFrm').submit();			
		}
		else {
			history.back();
		}
					
		$layerPopupObj.css({'left':left,'top':top, 'position':'absolute'});
		$('body').css('position','relative').append($layerPopupObj);	
	});
</script>
<div id="layerPopupBox"><img src="<c:url value='/Content/images/loading_map.gif'/>"/></div>
