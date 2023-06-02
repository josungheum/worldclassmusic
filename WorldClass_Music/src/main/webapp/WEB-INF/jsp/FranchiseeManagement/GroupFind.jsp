<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <div style="margin-top:4px; height:330px;" class="proton-demo" id="groupFind">
		<div style="height:330px; border-color:#d2d6de; border-style:solid; overflow: auto; margin-top: 10px;" id="findTreeList" ></div>
    </div>
</div><br />
<script type="text/javaScript">
    $(document).ready(function () {
    		GroupTree();
    });
    
    function GroupTree(){
    	findTreeSearchList(0, false, null, 'find');
	}

    
</script>