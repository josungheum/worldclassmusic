<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	$(document).ready(function() {	
		var $layerPopupObj = $('#layerPopupBox');
		var width = $(window).width() - ($layerPopupObj.width() / 2);
		var left = width;
		var top = ($(window).scrollTop() + ($(window).height() - $layerPopupObj.height()) / 2);
				
		$layerPopupObj.css({'left':left,'top':top, 'position':'absolute'});
		$('body').css('position','relative').append($layerPopupObj);
		
		parent.location.href = '/';
	});
</script>
<div id="layerPopupBox"><img src="<c:url value='/Content/images/loading_map.gif'/>"/></div>



