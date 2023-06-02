(function($) {
	// device 상태 정보
    $.fn.deviceStatus = function(jsonList) {

    	//$('#device-status')[0].hidden = true;
    	var mainDiv = document.createElement('div');
    	mainDiv.setAttribute('id', 'device-states');
    	for (var j = 0; j < jsonList.length; j++) {
			var jsonData = jsonList[j];

			var dsDiv = document.createElement('div');
			dsDiv.setAttribute('class','col-xs-12 col-md-6');

			var box = document.createElement('div');
			box.setAttribute('class', 'box box-widget');
			box.setAttribute('id', 'box_' + j);
			box.setAttribute("style", 'border:4px solid; border-color:lightgray; max-width:600px; margin:10px auto; cursor: pointer;');
			//box.setAttribute('onClick', 'deviceFnc(' + jsonData.idx + ')');

			var boxHeader = document.createElement('div');
			boxHeader.setAttribute('class', 'box-header with-border')
			boxHeader.setAttribute('style', 'font-size:15px;')

			var leftTitle = document.createElement('div');
			leftTitle.setAttribute('class','col-xs-12 col-md-6');

			var RightTitle = document.createElement('div');
			RightTitle.setAttribute('class','col-xs-12 col-md-6');

			var i= document.createElement('i');
			i.setAttribute('class', 'fa fa-fw fa-gear');

			var title = document.createTextNode(jsonData.devicename);

			leftTitle.appendChild(i);
			leftTitle.appendChild(title);

			RightTitle.appendChild(document.createTextNode('Last Access : ' + jsonData.lastAccess))

			boxHeader.appendChild(leftTitle);
			boxHeader.appendChild(RightTitle);

			box.appendChild(boxHeader);

			var boxBody = document.createElement('div');
			boxBody.setAttribute('class', 'box-body');

			var warningArr = new Array();

			// Resource별 MAX값 설정
			var cpuMax = 75;
			var memoryMax = 75;
			var RamMax = 75;

			warningArr[0] = createResource('CPU',jsonData.cpu,'per', cpuMax, '#3c8dbc', box, boxBody,j);
			warningArr[1] = createResource('Memory',jsonData.memory,'per', memoryMax, '#00A65A', box, boxBody,j);
			warningArr[2] = createResource('Ram',jsonData.ram,'per', RamMax, '#B0D588', box, boxBody,j);
			warningArr[3] = createResource('Printer',jsonData.printer,'conn', '', '', box, boxBody,j);
			warningArr[4] = createResource('Card',jsonData.card,'conn', '', '', box, boxBody,j);
			warningArr[5] = createResource('Barcode',jsonData.barcode,'conn', '', '', box, boxBody,j);
			warningArr[6] = createResource('Network',jsonData.network,'conn', '', '', box, boxBody,j);

			var warningResult = true;
			warningResult = warningArr.some(findWarnning);

			if (warningResult) {
				//box.setAttribute("style", 'border:4px solid; border-color:red; max-width: 600px; margin:10px auto; cursor: pointer;');
				box.setAttribute("style", 'border:4px solid; border-color:red; max-width: 600px; margin:10px auto;');
				$("#box_" + j).css('border-color','red');
			}else{
				//box.setAttribute("style", 'border:4px solid; border-color:lightgray; max-width:600px; margin:10px auto; cursor: pointer;');
				box.setAttribute("style", 'border:4px solid; border-color:lightgray; max-width:600px; margin:10px auto;');
				$("#box_" + j).css('border-color','lightgray');
			}
			dsDiv.appendChild(box);
			mainDiv.appendChild(dsDiv);

		}

    	$(function () {
            $('.knob').knob({
                draw: function () {
                    if (this.$.data('skin') == 'tron') {
                        var a = this.angle(this.cv)  // Angle
                            , sa = this.startAngle          // Previous start angle
                            , sat = this.startAngle         // Start angle
                            , ea                            // Previous end angle
                            , eat = sat + a                 // End angle
                            , r = true;
                        this.g.lineWidth = this.lineWidth;
                        this.g.beginPath();
                        this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
                        this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sat, eat, false);
                        this.g.stroke();
                        this.g.lineWidth = 2;
                        this.g.beginPath();
                        this.g.strokeStyle = this.o.fgColor;
                        this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                        this.g.stroke();

                        return false;
                    }
                    if(this.$[0].defaultValue == -1 ){
                    	this.cv = 0;
                    	this.$[0].value = '-'
                    }
                }
            });
         //$('#device-status')[0].hidden = false;
        });
    	if($("#device-states").length == 0){
    		this[0].appendChild(mainDiv);
    	}
        //return mainDiv;
    };

    function findWarnning(arr){
    	var result = false;
    	if(arr){
    		result = true;
    	}
    	return result;
    }

    /* Deviece Method */
    /**
     * createResource(resourceName, ResourceValue, Type, maxValue, defaultColor,target1,target2,index)
     * createResource(리소스명, 리소스 값, 타입(per,conn), 리소스 맥스값, 기본색상, div, div, 인덱스)
     * Percent : per
     * connection : conn
     * */
    function createResource(resourceName, resourceValue, Type, maxValue, defaultColor,target1,target2,j){

    	var warning = false;
    	var div = document.createElement('div');
		div.setAttribute('class','col-xs-12 col-md-1 text-center col-centered');
		div.setAttribute('style','width:80px; padding:0px !important; padding-top:5px !important;');

		if ( Type == 'per' ) {

			if(resourceName == "CPU" && $('#CPU_' + j).length == 1){
				$("#CPU_" + j)[0].value = resourceValue > -1 ? resourceValue : '-'
				$("#CPU_" + j)[0].setAttribute('value',resourceValue > -1 ? resourceValue : '-');
				if ( maxValue >= resourceValue && resourceValue > -1) {
					$("#CPU_" + j)[0].value = resourceValue;
					$("#CPU_" + j)[0].setAttribute('data-fgColor', defaultColor);
				} else {
					$("#CPU_" + j)[0].setAttribute('data-fgColor', '#ff0000');
					warning = true;
				}
				$("#CPU_" + j).trigger('change');
				$("#CPU_" + j).trigger(
					     'configure',
					      {
					        "fgColor":$("#CPU_" + j)[0].getAttribute('data-fgColor')
					    });
				$("#CPU_" + j).css('color',$("#CPU_" + j)[0].getAttribute('data-fgColor'));
				if(resourceValue == -1){
					$("#CPU_" + j)[0].value = '-'
				}
			}else if(resourceName == "Memory" && $('#Memory_' + j).length == 1){
				$("#Memory_" + j)[0].value = resourceValue > -1 ? resourceValue : '-'
				$("#Memory_" + j)[0].setAttribute('value',resourceValue > -1 ? resourceValue : '-')
				if ( maxValue >= resourceValue && resourceValue > -1) {
					$("#Memory_" + j)[0].setAttribute('data-fgColor', defaultColor);
				} else {
					$("#Memory_" + j)[0].setAttribute('data-fgColor', '#ff0000');
					warning = true;
				}

				$("#Memory_" + j).trigger('change');
				$("#Memory_" + j).trigger(
						 'configure',
						  {
						    "fgColor":$("#Memory_" + j)[0].getAttribute('data-fgColor')
						});
				$("#Memory_" + j).css('color',$("#Memory_" + j)[0].getAttribute('data-fgColor'));
				if(resourceValue == -1){
					$("#Memory_" + j)[0].value = '-'
				}
			}else if(resourceName == "Ram" && $('#Ram_' + j).length == 1){
				$("#Ram_" + j)[0].value = resourceValue > -1 ? resourceValue : '-'
				$("#Ram_" + j)[0].setAttribute('value',resourceValue > -1 ? resourceValue : '-')
				if ( maxValue >= resourceValue && resourceValue > -1) {
					$("#Ram_" + j)[0].setAttribute('data-fgColor', defaultColor);
				} else {
					$("#Ram_" + j)[0].setAttribute('data-fgColor', '#ff0000');
					warning = true;
				}
				$("#Ram_" + j).trigger('change');
				$("#Ram_" + j).trigger(
					     'configure',
					      {
					        "fgColor":$("#Ram_" + j)[0].getAttribute('data-fgColor')
					    });
				$("#Ram_" + j).css('color',$("#Ram_" + j)[0].getAttribute('data-fgColor'));
				if(resourceValue == -1){
					$("#Ram_" + j)[0].value = '-'
				}
			}else{
				var data = document.createElement('input');
	//			data.setAttribute('type','text');
				data.setAttribute('id',resourceName+'_'+j);
				data.setAttribute('class','knob deviceStatus');
				data.setAttribute('value',resourceValue);
				data.setAttribute('data-thickness','0.2');
				data.setAttribute('data-width',70);
				data.setAttribute('data-height',70);

				if ( maxValue >= resourceValue && resourceValue > 0) {
					data.setAttribute('data-fgColor', defaultColor);
				} else {
					data.setAttribute('data-fgColor', '#ff0000');
					warning = true;
				}

				data.setAttribute('data-readonly','true');

				var dataName = document.createElement('div');
				dataName.setAttribute('style', 'text-align:center;')
				dataName.appendChild(document.createTextNode(resourceName));

				div.appendChild(data);
				div.appendChild(dataName);

				target2.appendChild(div);

			}
		}else if( Type='conn' ){
			if($('#circle_' + resourceName +  j).length == 1){
				if (resourceValue == 1) {
					$('#circle_' + resourceName + j).attr('class', 'onCircle');
					$('#circle2_' + resourceName + j).attr('class', 'onCircle2');
					$('#circleText_' + resourceName + j).attr('class', 'onText');
					$('#circleText_' + resourceName + j).text('ON_LINE');
				}else if(resourceValue == -1){
					warning = true;
					$('#circle_' + resourceName + j).attr('class', 'offCircle');
					$('#circle2_' + resourceName + j).attr('class', 'offCircle2');
					$('#circleText_' + resourceName + j).attr('class', 'offText');
					$('#circleText_' + resourceName + j).text('OFF_LINE');
				}
			}else{
				var div1 = document.createElement('div');
				var div2 = document.createElement('div');
				var div3 = document.createElement('div');

				div1.setAttribute('id', 'circle_' + resourceName + j);
				div2.setAttribute('id', 'circle2_' + resourceName + j);
				div3.setAttribute('id', 'circleText_' + resourceName + j);

				if (resourceValue == 1) {
					div1.setAttribute('class', 'onCircle');
					div2.setAttribute('class', 'onCircle2');
					div3.setAttribute('class', 'onText');

					div3.appendChild(document.createTextNode('ON_LINE'))
				}else if(resourceValue == -1){
					warning = true;

					div1.setAttribute('class', 'offCircle');
					div2.setAttribute('class', 'offCircle2');
					div3.setAttribute('class', 'offText');

					div3.appendChild(document.createTextNode('OFF_LINE'))
				}

				div2.appendChild(div3);
				div1.appendChild(div2);

				var dataName = document.createElement('div');
				dataName.setAttribute('style', 'text-align:center;')
				dataName.appendChild(document.createTextNode(resourceName));

				div.appendChild(div1);
				div.appendChild(dataName);

				target2.appendChild(div);
			}
		}
		target1.appendChild(target2);
		return warning;
    }

    // 데이터 테이블 수정 처리
    $.fn.crudDatatable = function(data) {
    	var id = data.id;

    	var topMenu = document.createElement('div');

    	var buttonGroup = document.createElement('div');
    	buttonGroup.setAttribute('id', id + '_btns');
    	buttonGroup.setAttribute('class', 'btn-group');
    	buttonGroup.setAttribute('role', 'group');
    	buttonGroup.setAttribute('aria-label', '...');
    	buttonGroup.setAttribute('style', 'float: right;');

    	var addButton = document.createElement('button');
    	var removeButton = document.createElement('button');
    	var upButton = document.createElement('button');
    	var downButton = document.createElement('button');

    	addButton.setAttribute('type', 'button');
    	addButton.setAttribute('class', 'btn btn-default');
    	addButton.setAttribute('id', 'addbtn');
    	addButton.setAttribute('onClick', 'addBtn(\''+ id +'\');');
    	var plusIcon = document.createElement('span');
    	plusIcon.setAttribute('class', 'glyphicon glyphicon-plus');
    	plusIcon.setAttribute('aria-hidden', 'true');
    	addButton.appendChild(plusIcon);

    	removeButton.setAttribute('type', 'button');
    	removeButton.setAttribute('class', 'btn btn-default');
    	removeButton.setAttribute('id', 'remove');
    	var minusIcon = document.createElement('span');
    	minusIcon.setAttribute('class', 'glyphicon glyphicon-minus');
    	minusIcon.setAttribute('aria-hidden', 'true');
    	removeButton.appendChild(minusIcon);

    	upButton.setAttribute('type', 'button');
    	upButton.setAttribute('class', 'btn btn-default');
    	upButton.setAttribute('id', 'rowUp');
    	upButton.setAttribute('disabled', 'disabled');
    	var upIcon = document.createElement('span');
    	upIcon.setAttribute('class', 'glyphicon glyphicon-arrow-up');
    	upIcon.setAttribute('aria-hidden', 'true');
    	upButton.appendChild(upIcon);

    	downButton.setAttribute('type', 'button');
    	downButton.setAttribute('class', 'btn btn-default');
    	downButton.setAttribute('id', 'rowDown');
    	downButton.setAttribute('disabled', 'disabled');
    	var downIcon = document.createElement('span');
    	downIcon.setAttribute('class', 'glyphicon glyphicon-arrow-down');
    	downIcon.setAttribute('aria-hidden', 'true');
    	downButton.appendChild(downIcon);

    	buttonGroup.appendChild(addButton);
    	buttonGroup.appendChild(removeButton);
    	buttonGroup.appendChild(upButton);
    	buttonGroup.appendChild(downButton);

    	topMenu.appendChild(buttonGroup);

    	var mainDiv = document.createElement('div');

       	var table = document.createElement('table');
    	table.setAttribute('id', id);
    	table.setAttribute('class', 'table table-bordered table-striped datatable-custom');

    	var thead = document.createElement('thead');
    	var tr = document.createElement('tr');

    	var unvisibleList = new Array();

    	var headList = data.headList;
    	for (var i = 0; i < headList.length; i++) {
    		var th = document.createElement('th');
    		th.appendChild(document.createTextNode(headList[i].title));
    		tr.appendChild(th);

    		if (headList[i].visible == false) {
    			unvisibleList.push(i)
			}
		}

    	thead.appendChild(tr);
    	table.appendChild(thead);
    	mainDiv.appendChild(table);
    	this[0].appendChild(topMenu);
    	this[0].appendChild(mainDiv);
    
	    var crudTable = $('#'+id).DataTable({
	    	drawCallback: function () {
	    		$('.time').mask('00:00:00');
	    		// 시간 변경 시 처리
	    		$("[name='screenPlayTime']").change(function(){
	    			timeMaxData(this);
	    		});
	        },
            data: data.jsonData,
            destroy: true,
	        responsive: false,
            ordering: false,
            searching: false,
            bPaginate: false,
            bInfo: false,
            scrollY: data.scrollHeight,
	        columnDefs: data.columnDefs,
	        columns: data.columns
        });

	    if (unvisibleList.length != 0) {
			for (var i = 0; i < unvisibleList.length; i++) {
				$('#'+id).DataTable().column(unvisibleList[i]).visible(false);
			}
		}

	    $(document).ready(function(){
	    	var table = $('#'+id).DataTable();
	    	if ($('#disabledTable_'+id).val() != 'Y'){
		    	$('#' + id + ' tbody').on('click', 'tr', function(){
		    		if($(this).hasClass('selected')){
		    			$(this).removeClass('selected');

		    			$('#' + id + '_btns #rowUp').attr('disabled', true);
		    			$('#' + id + '_btns #rowDown').attr('disabled', true);
		    		}else{
		    			table.$('tr.selected').removeClass('selected');
		    			$(this).addClass('selected');

		    			if ($(this).index() ==  0) {
		    				if ($(this).parent().children().length == 1) {
		    					$('#' + id + '_btns #rowUp').attr('disabled', true);
								$('#' + id + '_btns #rowDown').attr('disabled', true);
							}else{
								$('#' + id + '_btns #rowUp').attr('disabled', true);
								$('#' + id + '_btns #rowDown').attr('disabled', false);
							}
						} else if ($(this).parent().children().length == $(this).index() + 1 ){
							$('#' + id + '_btns #rowUp').attr('disabled', false);
							$('#' + id + '_btns #rowDown').attr('disabled', true);
						} else {
							$('#' + id + '_btns #rowUp').attr('disabled', false);
			    			$('#' + id + '_btns #rowDown').attr('disabled', false);
						}
		    		}
		    	});
	    	}

	    	$('#' + id + '_btns #rowUp').on('click', function(){
	    		var selectRow = $('#' + id + ' tbody tr.selected');
	    		var orignalDataList = $('#'+id).DataTable().data();
	    		var changeDataList = [];
	    		var targetData = {};
	    		var selectRowIndex = selectRow.index();

	    		for (var i = 0; i < orignalDataList.length; i++) {
					if (i == selectRowIndex - 1) {
						targetData = orignalDataList[i];
					} else if (i == selectRowIndex) {
						changeDataList.push(orignalDataList[i]);
						changeDataList.push(targetData);
					} else{
						changeDataList.push(orignalDataList[i]);
					}
				}

	    		orignalDataList.clear();
	    		orignalDataList.rows.add(changeDataList).draw();

	    		$('#' + id + ' tbody tr')[selectRowIndex - 1].click();

	    		if($('#' + id + ' tbody tr.selected').index() == 0){
	    			$('#' + id + '_btns #rowUp').attr('disabled', true);
	    		} else {
	    			$('#' + id + '_btns #rowUp').attr('disabled', false);
	    			$('#' + id + '_btns #rowDown').attr('disabled', false);
	    		}
	    	});

	    	$('#' + id + '_btns #rowDown').on('click', function(){
	    		var selectRow = $('#' + id + ' tbody tr.selected');

	    		var orignalDataList = $('#'+id).DataTable().data();
	    		var changeDataList = [];
	    		var targetData = {};
	    		var selectRowIndex = selectRow.index();

	    		for (var i = 0; i < orignalDataList.length; i++) {
					if (i == selectRowIndex ) {
						targetData = orignalDataList[i];
					} else if (i == selectRowIndex + 1) {
						changeDataList.push(orignalDataList[i]);
						changeDataList.push(targetData);
					} else{
						changeDataList.push(orignalDataList[i]);
					}
				}

	    		orignalDataList.clear();
	    		orignalDataList.rows.add(changeDataList).draw();

	    		$('#' + id + ' tbody tr')[selectRowIndex + 1].click();

	    		if(orignalDataList.length == $('#' + id + ' tbody tr.selected').index()+1){
	    			$('#' + id + '_btns #rowDown').attr('disabled', true);
	    		}else {
	    			$('#' + id + '_btns #rowUp').attr('disabled', false);
	    			$('#' + id + '_btns #rowDown').attr('disabled', false);
	    		}
	    	});

	    	$('#' + id + '_btns #remove').on('click', function(){
	    		if($('#' + id + ' tbody tr.selected').length == 0){
	    			$.modalCommon.alertView('선택된 항목이 없습니다.', null, null, null)
	    			return;
	    		}

	    		if ($('#'+id).DataTable().row('.selected').data() == null) {
	    			$.modalCommon.alertView('선택된 항목이 없습니다.', null, null, null)
					return;
				}

	    		if (data.mainName != null) {
	    			BootstrapDialog.confirm({
	    				title : '',
	    				message : $('#'+id).DataTable().row('.selected').data()[data.mainName] + "을(를) 삭제하시겠습니까?",
	    				type : BootstrapDialog.TYPE_WARNING,
	    				closable : false,
	    				draggable : true,
	    				btnCancelLabel: '취소',
	    			    btnOKLabel: '확인',
	    				btnOKClass : 'btn-warning',
	    				callback : function(confirmResult) {
	    					if (confirmResult) {
	    						$('#'+id).DataTable().row('.selected').remove().draw();

	    	    				var orignalDataList = $('#'+id).DataTable().data().toArray();
	    	    				$('#'+id).DataTable().data().clear();
	    	    				$('#'+id).DataTable().data().rows.add(orignalDataList).draw();
	    					}
	    				}
	    			});
	    			$('#' + id + '_btns #rowUp').attr('disabled', true);
	    			$('#' + id + '_btns #rowDown').attr('disabled', true);
				} else {
					BootstrapDialog.confirm({
	    				title : '',
	    				message : "선택한 항목을 삭제하시겠습니까?",
	    				type : BootstrapDialog.TYPE_WARNING,
	    				closable : false,
	    				draggable : true,
	    				btnCancelLabel: '취소',
	    			    btnOKLabel: '확인',
	    				btnOKClass : 'btn-warning',
	    				callback : function(confirmResult) {
	    					if (confirmResult) {
	    						$.modalCommon.alertView('삭제하였습니다.', null, null, null);

	    		    			$('#' + id + '_btns #rowUp').attr('disabled', true);
	    		    			$('#' + id + '_btns #rowDown').attr('disabled', true);
	    					}
	    				}
	    			});
				}
	    	});
	    });
	}
}(jQuery));