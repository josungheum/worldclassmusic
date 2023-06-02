	$.showPageLoading = function (callback) {
		var body = $('body');
		var div = $('<div>').addClass('pageDimmed')
									.css({
										position: 'absolute',
										top: 0,
										left: 0,
										width: '100%',
										height: '100%',
										opacity: .5,
										background: '#000000'
									}).appendTo(body);
		var img = $('<img>').addClass('pageDimmed')
									.attr('src', '/Content/images/loading.gif')
									.css({
										position: 'absolute',
										top: 0,
										left: 0,
										width: 100,
										height: 100,
										top: '50%',
										left: '50%',
										'margin-left': -50,
										'margin-top': -50
									}).appendTo(body);
	};

	$.hidePageLoading = function () {
		var id = window.setTimeout(function () {
			$('body').find('div.pageDimmed').remove();
			$('body').find('img.pageDimmed').remove();
			window.clearTimeout(id);
		}, 200);
	};

	$.bindComboBox = function (id, arrData) {
		var select = $('#' + id);

		select.empty();

		for (var i = 0; i < arrData.length; ++i) {
			var data = arrData[i];
			var opt = $('<option>').attr({ 'value': data.Code })
						 					.text(data.Name)
						 					.appendTo(select);

			if (data.Data != null)
				opt.attr(data.Data);
		}

		select.val(arrData[0].Code);
	};

	$.FileUpload = {};

	$.showUploadBox = function (opt) {
		$.FileUpload.Finished = opt.Finished;

		BootstrapDialog.show({
			title: opt.title,
			draggable: true,
			closable: false,
			message: function (dialog) {
				var $message = $('<div class="msgBodyContainer"></div>');
				var pageToLoad = dialog.getData('pageToLoad');
				$message.load(pageToLoad);

				return $message;
			},
			data: {
				'pageToLoad': opt.url + '/' + opt.ext
			},
			buttons: [
				{
					label: '업로드',
					cssClass: 'btn-primary',
					action: function (dialogRef) {
						$.FileUpload.StartUpload();
					}
				},
				{
					label: '닫기',
					cssClass: 'btn-default',
					action: function (dialogRef) {
						$.FileUpload.Dialog.close();
					}
				}
			],
			onshown: function (d) {
				$.FileUpload.Dialog = d;
				var dialogContainer = d.getModalBody().parent().parent().css({ width: opt.width + 20, height: opt.height + 20 });
				var body = d.getModalBody().css({
					width: '100%',
					height: '100%',
					padding: 0
				});

				body.find('div.msgBodyContainer').css({
					'overflow-y': 'auto',
					'overflow-x': 'hidden',
					padding: 10
				});
				dialogContainer.find('#fileUpload').css({
					width: opt.width,
					height: opt.height
				});

				$('.modal-footer button').prop('disabled', false);
			}
		});
	};
