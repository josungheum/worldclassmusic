/* Image List */
(function ($) {
	var fixSettings;
	var parentBody;
	var parentTitle;
	
	$.fn.ImageEditor = function(options) {
		var settings = $.extend({
			title : null,
			id : null,
			data : null,
			folder : 'Content',
			oriExt : null,
			imgTitle : false
		}, options);

		if (settings.id != null) {
			fixSettings = settings;
			if (settings.imgTitle) {
				$.fn.ImageList2(settings, $(this));
			}
			else {
				$.fn.ImageList(settings, $(this));
			}
		}
	}
	
	$.fn.ImageList = function(settings, obj) {
		$(this).on('mousedown', $.fn.RemoveActive);
		var parent = $('#'+settings.id);
		parent.html('');
		parent.css('min-height', '170px').css('max-width', '500px');
		
		var header = $('<div>').appendTo(parent)
								.css('min-width', '500px')
								.css('height', '40px')
								.css('display', 'block');
		
		if (settings.title != null) {
			parentTitle = $('<div>').appendTo(header)
									.css('margin', '2px 0 5px 0')
									.addClass('pull-left')
									.attr('ot', 'title')
									.attr('id', settings.id+'Title');
			
			$('<span>').appendTo(parentTitle)
						.text(settings.title)
						.css('font-weight', 'bold');
		}
		
		var btnDiv = $('<div>').appendTo(header)
								.attr('ot', 'btnDiv')
								.addClass('pull-right');
		
		var addBtn = $('<button>').appendTo(btnDiv)
								.on('click', $.fn.ImageUpload)
								.attr('type', 'button')
								.addClass('btn')
								.addClass('btn-default');
						
		$('<i>').appendTo(addBtn)
				.addClass('fa')
				.addClass('fa-plus');
		
		var leftBtn = $('<button>').appendTo(btnDiv)
									.on('click', $.fn.ContentMoveL)
									.attr('ot', 'moveBtn')
									.attr('type', 'button')
									.addClass('btn')
									.addClass('btn-default');
		
		$('<span>').appendTo(leftBtn)
					.attr('ot', 'glyphicon')
					.addClass('glyphicon')
					.addClass('glyphicon-arrow-left');
		
		var rightBtn = $('<button>').appendTo(btnDiv)
									.on('click', $.fn.ContentMoveR)
									.attr('ot', 'moveBtn')
									.attr('type', 'button')
									.addClass('btn')
									.addClass('btn-default');
		
		$('<span>').appendTo(rightBtn)
					.attr('ot', 'glyphicon')
					.addClass('glyphicon')
					.addClass('glyphicon-arrow-right');
									
		parentBody = $('<div>').appendTo(parent)
								.attr('ot', 'body')
								.attr('id', settings.id+'Body');
		
		var imageDiv = $('<div>').appendTo(parentBody)
								 .addClass('imageList')
								 .css('height', '120px')
								 .css('max-height', '120px');
		
		$('<div>').appendTo(imageDiv)
					 .attr('ot', 'imageDiv')
					 .addClass('imageDiv')
					 .css('max-height', '90px')
					 .css('display', 'inline-flex');
		
		if(settings.data != null && settings.data != '') {
			$.fn.ImageAddList(settings.data);
		}
	};
	
	
	$.fn.ImageList2 = function(settings, obj) {
		$(this).on('mousedown', $.fn.RemoveActive);
		var parent = $('#'+settings.id);
		parent.html('');
		parent.css('min-height', '270px').css('max-width', '500px');
		
		var header = $('<div>').appendTo(parent)
								.css('min-width', '500px')
								.css('height', '40px')
								.css('display', 'block');
		
		if (settings.title != null) {
			parentTitle = $('<div>').appendTo(header)
									.css('margin', '2px 0 5px 0')
									.addClass('pull-left')
									.attr('ot', 'title')
									.attr('id', settings.id+'Title');
			
			$('<span>').appendTo(parentTitle)
						.text(settings.title)
						.css('font-weight', 'bold');
		}
		
		var btnDiv = $('<div>').appendTo(header)
								.attr('ot', 'btnDiv')
								.addClass('pull-right');
		
		var addBtn = $('<button>').appendTo(btnDiv)
								.on('click', $.fn.ImageUpload2)
								.attr('type', 'button')
								.addClass('btn')
								.addClass('btn-default');
						
		$('<i>').appendTo(addBtn)
				.addClass('fa')
				.addClass('fa-plus');

		var leftBtn = $('<button>').appendTo(btnDiv)
									.on('click', $.fn.ContentMoveL)
									.attr('ot', 'moveBtn')
									.attr('type', 'button')
									.addClass('btn')
									.addClass('btn-default');
		
		$('<span>').appendTo(leftBtn)
					.attr('ot', 'glyphicon')
					.addClass('glyphicon')
					.addClass('glyphicon-arrow-left');
		
		var rightBtn = $('<button>').appendTo(btnDiv)
									.on('click', $.fn.ContentMoveR)
									.attr('ot', 'moveBtn')
									.attr('type', 'button')
									.addClass('btn')
									.addClass('btn-default');
		
		$('<span>').appendTo(rightBtn)
					.attr('ot', 'glyphicon')
					.addClass('glyphicon')
					.addClass('glyphicon-arrow-right');
		
		parentBody = $('<div>').appendTo(parent)
								.attr('ot', 'body')
								.attr('id', settings.id+'Body');
		
		var imageDiv = $('<div>').appendTo(parentBody)
								 .addClass('imageList')
								 .css('height', '320px')
								 .css('max-height', '320px');
		
		$('<div>').appendTo(imageDiv)
				 .attr('ot', 'imageDiv')
				 .addClass('imageDiv')
				 .css('max-height', '320px')
				 .css('display', 'inline-flex');
		
		if(settings.data != null && settings.data != '') {
			$.fn.ImageAddList2(settings.data);
		}
	};
	
	$.fn.ImageUpload = function(e) {
		var oriExt;
		if (fixSettings.oriExt == null) {
			oriExt = ['jpg,jpeg,png,avi,wmv,mpeg,mp4'];
		}
		else {
			oriExt = fixSettings.oriExt;
		}
		
		var parent = $(e.currentTarget).parent().parent().parent();
		parentBody = parent.find('div[ot="body"]');
		parentTitle = parent.find('div[ot="title"]').text();
		$.showUploadBox({
			url: '/Upload/View/'+fixSettings.folder,
			width: 500,
			height: 450,
			title: parentTitle + ' 컨텐츠 등록',
			ext: oriExt,
			Finished: FileUploadFinishedEH
		});

	   function FileUploadFinishedEH(data){
		   if(data == undefined){
		   }else{
			   var jsonData = $.FileUpload.Data;
			   if (jsonData.length > 0) {
				   for(var i = 0; i< jsonData.length; i++) {
				   var arr = [
				                {
				                	"fileContentIdx" : jsonData[i].fileContentIdx,
				        			"fileName" : jsonData[i].fileName,
				        			"fileSaveName" : jsonData[i].fileSaveName,
				        			"savePath" : jsonData[i].savePath,
				        			"thumbnailPath" : jsonData[i].thumbnailPath,
				        			"fileSize" : jsonData[i].fileSize,
				        			"checkSum" : jsonData[i].checkSum,
				        			"playTime" : jsonData[i].playTime
				                }
				            ];
				   $.fn.ImageAddList(arr);
				   }
			   }
		   }
	   }
	};
	
	$.fn.ImageAddList = function(content) {
		var ctList = parentBody.find('.imageDiv');
		
		for (var i = 0; i < content.length; i++) {
			var divImg = $("<div>").addClass("contentAdditionImage")
								  .attr("ot", "contentAdditionImage")
								  .on('click', $.fn.ImageMoveList)
								  .appendTo(ctList);

			$('<input>').appendTo(divImg)
						.attr('type', 'hidden')
						.val(content[i].fileContentIdx);
			
			$('<img>').appendTo(divImg)
						.attr('ot', 'ctImg')
						.css('width', '90px')
						.css('height', '90px')
						.attr("src", content[i].thumbnailPath);
			
			$('<button>').css('width', 20).css('height', 20)
						 .attr('ot', 'contentAdditionDelete')
						 .css('text-align', 'center')
						 .css('z-index', '999')
						 .addClass('contentAdditionDelete')
						 .css('background-image', 'url(/Content/images/details_close.png)')
						 .on('click', $.fn.DeleteImageList)
						 .appendTo(divImg);
		}
		return;
	};
	
	$.fn.DeleteImageList = function (e) {
		$(e.currentTarget).parent().remove();
	}
	
	$.fn.ImageMoveList = function(e) {
		var ct = $(e.currentTarget);
		if(ct.hasClass('ImageActive')){
			ct.removeClass('ImageActive');
		}
		else {
			ct.parent().find('.ImageActive').each(function() {
				$(this).removeClass('ImageActive');
			});
			ct.addClass('ImageActive');
		}
	}
	
	$.fn.RemoveActive = function (e) {
		var possableList = [
			'contentAdditionImage',
			'ctImg',
			'contentAdditionDelete',
			'glyphicon',
			'moveBtn',
			'glyphicon-arrow-right',
			'glyphicon-arrow-left',
			'btnDiv'
		];
		var isPossable = true;
		for (var i = 0; i < possableList.length; ++i)
		{
			isPossable = isPossable && $(e.target).attr('ot') != possableList[i];
			isPossable = isPossable && $(e.target).attr('class') != possableList[i];
			isPossable = isPossable && $(e.target).attr('id') != possableList[i];
		}
		if (isPossable)
		{
			$('.contentAdditionImage').each(function() {
				$(this).removeClass('ImageActive');
			});
		}
	}
	
	$.fn.ContentMoveR = function(e) {
		var parent = $(e.currentTarget).parent().parent().parent();
		parentBody = parent.find('div[ot="body"]');
		if (parentBody.find('.ImageActive').length > 0) {
			var active = parentBody.find('.ImageActive');
			if (active.next().length > 0) {
				active.next().after(active);
			}
		}
	}
	
	$.fn.ContentMoveL = function(e) {
		var parent = $(e.currentTarget).parent().parent().parent();
		parentBody = parent.find('div[ot="body"]');
		if (parentBody.find('.ImageActive').length > 0) {
			var active = parentBody.find('.ImageActive');
			if (active.prev().length > 0) {
				active.prev().before(active);
			}
		}
	}
	
	
	
	$.fn.ImageUpload2 = function(e) {
		if ($('.contentAdditionImage').length == 6) {
			$.modalCommon.alertView("대표 메뉴는 6개까지 등록 가능합니다.");
			return;
		}

		var oriExt;
		if (fixSettings.oriExt == null) {
			oriExt = ['jpg,jpeg,png,avi,wmv,mpeg,mp4'];
		}
		else {
			oriExt = fixSettings.oriExt;
		}
		
		var parent = $(e.currentTarget).parent().parent().parent();
		parentBody = parent.find('div[ot="body"]');
		parentTitle = parent.find('div[ot="title"]').text();
		$.showUploadBox({
			url: '/Upload/View/'+fixSettings.folder,
			width: 500,
			height: 450,
			title: parentTitle + ' 컨텐츠 등록',
			ext: oriExt,
			Finished: FileUploadFinishedEH2
		});

	   function FileUploadFinishedEH2(data){
		   if(data == undefined){
		   }else{
			   var jsonData = $.FileUpload.Data;
			   if (jsonData.length > 0) {
				   for(var i = 0; i< jsonData.length; i++) {
				   var arr = [
				                {
				                	"fileContentIdx" : jsonData[i].fileContentIdx,
				        			"fileName" : jsonData[i].fileName,
				        			"fileSaveName" : jsonData[i].fileSaveName,
				        			"savePath" : jsonData[i].savePath,
				        			"thumbnailPath" : jsonData[i].thumbnailPath,
				        			"fileSize" : jsonData[i].fileSize,
				        			"checkSum" : jsonData[i].checkSum,
				        			"playTime" : jsonData[i].playTime
				                }
				            ];
				   $.fn.ImageAddList2(arr);
				   }
			   }
		   }
	   }
	};
	
	$.fn.ImageAddList2 = function(content) {
		var ctList = parentBody.find('.imageDiv');
		
		for (var i = 0; i < content.length; i++) {
			var divImg = $("<div>").addClass("contentAdditionImage")
								  .attr("ot", "contentAdditionImage")
								  .on('click', $.fn.ImageMoveList)
								  .appendTo(ctList);

			$('<input>').appendTo(divImg)
						.attr('type', 'hidden')
						.val(content[i].fileContentIdx);
			
			$('<img>').appendTo(divImg)
						.attr('ot', 'ctImg')
						.css('width', '180px')
						.css('height', '120px')
						.attr("src", content[i].thumbnailPath);
			
			$('<input>').appendTo(divImg)
						 .attr('type', 'text')
						 .attr('ot', 'ctTitle')
						 .css('width', '180px')
						 .css('height', '25px')
						 .css('display', 'block')
						 .css('margin', '5px 0')
						 .attr('maxlength', '15')
						 .attr('placeholder', '메뉴 이름(한)')
						 .val(content[i].detailMenuTitle);
			
			$('<input>').appendTo(divImg)
						 .attr('type', 'text')
						 .attr('ot', 'ctTitleEn')
						 .css('width', '180px')
						 .css('height', '25px')
						 .css('display', 'block')
						 .css('margin', '5px 0')
						 .attr('maxlength', '15')
						 .attr('placeholder', '메뉴 이름(영)')
						 .val(content[i].detailMenuTitleEn);
			
			$('<textarea>').appendTo(divImg)
							.css('resize', 'none')
							.attr('ot', 'ctDetail')
							.attr('maxlength', '40')
							.css('width', '180px')
							.css('height', '50px')
							.css('display', 'block')
							.css('margin', '5px 0')
							.attr('placeholder', '메뉴 상세(한)')
							.val(content[i].detailMenuDesc);
			
			$('<textarea>').appendTo(divImg)
							.css('resize', 'none')
							.attr('ot', 'ctDetailEn')
							.attr('maxlength', '40')
							.css('width', '180px')
							.css('height', '50px')
							.attr('placeholder', '메뉴 상세(영)')
							.val(content[i].detailMenuDescEn);
			
			$('<button>').css('width', 20).css('height', 20)
							 .attr('ot', 'contentAdditionDelete')
							 .css('text-align', 'center')
							 .css('left', '168px')
							 .css('z-index', '999')
							 .addClass('contentAdditionDelete')
							 .css('background-image', 'url(/Content/images/details_close.png)')
							 .on('click', $.fn.DeleteImageList)
							 .appendTo(divImg);
		}
		
		return;
	};
})($);