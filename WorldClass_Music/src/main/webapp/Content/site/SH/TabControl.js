/* Tab List */
(function ($) {
	$.tabControl = function(nameArr, urlArr, val, num) {
		if (num == null || num == 0) {
			var ul = $('#tabList');
			ul.html('');
			
			if (nameArr.length == urlArr.length) {
				if (nameArr.length > 0) {
					var html = '';
					var tabUrl = urlArr[val];
					
					for (var i = 0; i < nameArr.length; i++) {
						html +=  "<li><a onclick=\"tabView("+i+")\" data-toggle='tab' style='cursor:pointer;'>"+nameArr[i]+"</a></li>"
					}
					
					ul.append(html);
					$('#tabLab').val(val);
				    $('#tabLab').find('li').each(function () {
				        $(this).removeClass('active');
				    });
				    $('#tabLab').find('li').eq(val).addClass('active');
				    
				    $.sessionCheck();
				    $('#contentView').load(tabUrl);
				}
			}
		} 
		else {
			var ul = $('#tabList'+num);
			ul.html('');
			
			if (nameArr.length == urlArr.length) {
				if (nameArr.length > 0) {
					var html = '';
					var tabUrl = urlArr[val];
					
					for (var i = 0; i < nameArr.length; i++) {
						html +=  "<li><a onclick=\"tabView"+num+"("+i+")\" data-toggle='tab' style='cursor:pointer;'>"+nameArr[i]+"</a></li>"
					}
					
					ul.append(html);
					$('#tabLab'+num).val(val);
				    $('#tabLab'+num).find('li').each(function () {
				        $(this).removeClass('active');
				    });
				    $('#tabLab'+num).find('li').eq(val).addClass('active');
				    
				    $.sessionCheck();
				    $('#contentView'+num).load(tabUrl);
				}
			}
		}
		
	};
	
	/**
	 * 신규 추가 
	 * opt = 보낼 파라미터 
	 * 예) opt = {brandIdx : ''}
	 */
	$.newTabControl = function(tabDataArr, val) {
		var ul = $('#tabList');
		ul.html('');
		
		var html = '';
		var tabObj = tabDataArr[val];
		
		for (var i = 0; i < tabDataArr.length; i++) {
			html +=  "<li><a onclick=\"tabView("+i+")\" data-toggle='tab' style='cursor:pointer;'>"+tabDataArr[i].tabName+"</a></li>"
		}
		
		ul.append(html);
		$('#tabLab').val(val);
	    $('#tabLab').find('li').each(function () {
	        $(this).removeClass('active');
	    });
	    $('#tabLab').find('li').eq(val).addClass('active');
	    
	    $.sessionCheck();
	    $('#contentView').load(tabObj.tabUrl, tabObj.tabParam);
		
		
	};
})($);

