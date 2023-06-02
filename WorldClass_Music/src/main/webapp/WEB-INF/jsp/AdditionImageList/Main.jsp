<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.userList thead tr th{
		text-align : center;
	}
</style>

<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">갤러리아 카드 메뉴 설정</div>
	</div>
	<div class="box-body addition">
		<div id="galleriaCard1" style="display: inline-block;"></div>
		<div id="galleriaCard2" style="display: inline-block; position: absolute; left: 650px;"></div>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2 additionBtn" data-toggle="modal" onclick="gcSave();"><i class="glyphicon glyphicon-plus mr-5"></i>저장</button>
		</div>
	</div>
</div>

<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">갤러리아 상품권 메뉴 설정</div>
	</div>
	<div class="box-body addition">
		<div id="galleriaGiftCard1" style="display: inline-block;"></div>
		<div id="galleriaGiftCard2" style="display: inline-block; position: absolute; left: 650px;"></div>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2 additionBtn" data-toggle="modal" onclick="ggSave();"><i class="glyphicon glyphicon-plus mr-5"></i>저장</button>
		</div>
	</div>
</div>

<div class="box box-widget">
	<div class="box-header">
		<div class="user-block under-line">문화센터 메뉴 설정</div>
	</div>
	<div class="box-body addition">
		<div id="moonhwa1" style="display: inline-block;"></div>
		<div id="moonhwa2" style="display: inline-block; position: absolute; left: 650px;"></div>
	</div>
	<div class="box-footer" id="inputBtn">
		<div class="pull-right">
			<button type="button" class="btn btn-primary2 additionBtn" data-toggle="modal" onclick="mhSave();"><i class="glyphicon glyphicon-plus mr-5"></i>저장</button>
		</div>
	</div>
</div>
<script type="text/javaScript">
	$(document).ready(function () {
		treeSearchList(0, false, null, 'STO01');
		treeHeight();

		$(window).resize(function(){
	    	treeHeight('resize');
	    });
	});
	
	function ImageList() {
		var domain = $('#domainIdx').val();
		var brand = $('#brandIdx').val();
		
		if (brand == 0) {
			$('.addition').find('div').each(function() {
				$(this).html('');
			});
			$('.additionBtn').attr('disabled', 'disabled');
			return;
		}
		else {
			$('.additionBtn').removeAttr('disabled');
		}
		
		var param = {
				domainIdx : domain, brandIdx : brand
		}
		var result = $.ajaxUtil.ajaxDefault('<c:url value="/Addition/List"/>', param).data;
		var folder = 'Addition';
		
		$.fn.ImageEditor({
			title : '갤러리아 카드(가로형)',
			id : 'galleriaCard1',
			data : result[0].additionList,
			folder : folder,
			oriExt : null
		});
		
		$.fn.ImageEditor({
			title : '갤러리아 카드(세로형)',
			id : 'galleriaCard2',
			data : result[1].additionList,
			folder : folder,
			oriExt : null
		});
		
		$.fn.ImageEditor({
			title : '갤러리아 상품권(가로형)',
			id : 'galleriaGiftCard1',
			data : result[2].additionList,
			folder : folder,
			oriExt : null
		});
		
		$.fn.ImageEditor({
			title : '갤러리아 상품권(세로형)',
			id : 'galleriaGiftCard2',
			data : result[3].additionList,
			folder : folder,
			oriExt : null
		});
		
		$.fn.ImageEditor({
			title : '문화센터(가로형)',
			id : 'moonhwa1',
			data : result[4].additionList,
			folder : folder,
			oriExt : null
		});
		
		$.fn.ImageEditor({
			title : '문화센터(세로형)',
			id : 'moonhwa2',
			data : result[5].additionList,
			folder : folder,
			oriExt : null
		});
	}

	function gcSave() {
		var domain = $('#domainIdx').val();
		var brand = $('#brandIdx').val();
		var arr = new Array();
		var param;
		var additionXML = '';
		$('#galleriaCard1').find('input[type="hidden"]').each(function(index, e) {
			additionXML += '<Addition><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '<type>ADD0001</type></Addition>';
		});
		$('#galleriaCard2').find('input[type="hidden"]').each(function(index, e) {
			additionXML += '<Addition><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '<type>ADD0002</type></Addition>';
		});
		
		var vo = {
				additionXML : additionXML, additionType : 'ADD0001,ADD0002', domainIdx : domain, brandIdx : brand
		}
		
		if (additionXML.length == 0) {
			var result = $.ajaxUtil.ajaxDefault('<c:url value="/Addition/Delete"/>', vo);
			if (result.resultCode == 0) {
				ImageList();
	            $.modalCommon.close();
				$.modalCommon.alertView('처리 되었습니다.');
			}
		}
		else {
			var result = $.ajaxUtil.ajaxDefault('<c:url value="/Addition/Create"/>', vo);
			if (result.resultCode == 0) {
				ImageList();
	            $.modalCommon.close();
				$.modalCommon.alertView('처리 되었습니다.');
			}
		}
	}
	
	function ggSave() {
		var domain = $('#domainIdx').val();
		var brand = $('#brandIdx').val();
		var arr = new Array();
		var param;
		var additionXML = '';
		$('#galleriaGiftCard1').find('input[type="hidden"]').each(function(index, e) {
			additionXML += '<Addition><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '<type>ADD0003</type></Addition>';
		});
		$('#galleriaGiftCard2').find('input[type="hidden"]').each(function(index, e) {
			additionXML += '<Addition><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '<type>ADD0004</type></Addition>';
		});
		
		var vo = {
				additionXML : additionXML, additionType : 'ADD0003,ADD0004', domainIdx : domain, brandIdx : brand
		}
		
		if (additionXML.length == 0) {
			var result = $.ajaxUtil.ajaxDefault('<c:url value="/Addition/Delete"/>', vo);
			if (result.resultCode == 0) {
				ImageList();
	            $.modalCommon.close();
				$.modalCommon.alertView('처리 되었습니다.');
			}
		}
		else {
			var result = $.ajaxUtil.ajaxDefault('<c:url value="/Addition/Create"/>', vo);
			if (result.resultCode == 0) {
				ImageList();
	            $.modalCommon.close();
				$.modalCommon.alertView('처리 되었습니다.');
			}
		}
	}
	
	function mhSave() {
		var domain = $('#domainIdx').val();
		var brand = $('#brandIdx').val();
		var arr = new Array();
		var param;
		var additionXML='';
		$('#moonhwa1').find('input[type="hidden"]').each(function(index, e) {
			additionXML += '<Addition><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '<type>ADD0005</type></Addition>';
		});
		$('#moonhwa2').find('input[type="hidden"]').each(function(index, e) {
			additionXML += '<Addition><fileIdx>'+$(this).val()+'</fileIdx>'
						 + '<orderSeq>'+index+'</orderSeq>'
						 + '<type>ADD0006</type></Addition>';
		});
		
		var vo = {
				additionXML : additionXML, additionType : 'ADD0005,ADD0006', domainIdx : domain, brandIdx : brand
		}
		
		if (additionXML.length == 0) {
			var result = $.ajaxUtil.ajaxDefault('<c:url value="/Addition/Delete"/>', vo);
			if (result.resultCode == 0) {
				ImageList();
	            $.modalCommon.close();
				$.modalCommon.alertView('처리 되었습니다.');
			}
		}
		else {
			var result = $.ajaxUtil.ajaxDefault('<c:url value="/Addition/Create"/>', vo);
			if (result.resultCode == 0) {
				ImageList();
	            $.modalCommon.close();
				$.modalCommon.alertView('처리 되었습니다.');
			}
		}
	}
	
	
	
	
	
	
	
	function callbackTreeSearchKeyEvt(e)
	{
		if (e.keyCode == 13) {
        	treeSearchList(1, false, null, 'STO01');
        	e.keyCode = 0;
		}
	}

	function callbackSelectNodeJstree(data)
	{
		ImageList();
	}

	function callbackFirstSelect(jsonData)
	{
		ImageList();
	}
	
</script>