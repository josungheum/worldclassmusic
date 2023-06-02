(function ($) {

	$.timePickerCommon = {
		createTimePicker: function (id,date,maxHour) {
			var inputId = $('#'+id);

			inputId.timepicker({
				maxHour: maxHour,
    			minuteStep: 1,
                showSeconds: false,
                showMeridian: false,
                defaultTime: date
            });
		},
		createSecondTimePicker: function (id,date,maxHour) {
			var inputId = $('#'+id);

			inputId.timepicker({
				maxHour: maxHour,
    			minuteStep: 1,
                showSeconds: true,
                secondStep: 1,
                showMeridian: false,
                defaultTime: date
            });
		}
	},
    $.datePickerCommon = {
        //일반 datePicker
    	createDatePicker: function (id) {
    		var textBox = $('#'+id);
    		var $obj = $('#'+id).parent();

    		$obj.daterangepicker({
    			todayBtn: "linked",
    			singleDatePicker: true,
    			todayHighlight: true,
    			forceParse: false,
    			calendarWeeks: true,
    		    autoclose: true,
    		    format: "YYYY-MM-DD",
    		    locale:{
    			    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    			    daysOfWeek: ['일', '월','화','수','목','금','토']
    			},
    			onSelect: function(dateText) {
    			    display("Selected date: " + dateText + "; input's current value: " + this.value);
    			}
    		});

    		$obj.on('apply.daterangepicker', function(ev, picker) {
    			textBox.val(picker.startDate.format('YYYY-MM-DD'));
    			if(typeof(window["salesDateChange"]) == "function"){
    				salesDateChange();
    			}
    		});
        },

      //일반 datePicker
    	createDatePicker2: function (id) {
    		var textBox = $('#'+id);
    		var $obj = $('#'+id).parent();

    		$obj.daterangepicker({
    			todayBtn: "linked",
    			singleDatePicker: true,
    			todayHighlight: true,
    			forceParse: false,
    			calendarWeeks: true,
    		    autoclose: true,
    		    format: "YYYY-MM",
    		    locale:{
    			    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    			    daysOfWeek: ['일', '월','화','수','목','금','토']
    			},
    			onSelect: function(dateText) {
    			    display("Selected date: " + dateText + "; input's current value: " + this.value);
    			}
    		});

    		$obj.on('apply.daterangepicker', function(ev, picker) {
    			textBox.val(picker.startDate.format('YYYY-MM'));
    			if(typeof(window["salesDateChange"]) == "function"){
    				salesDateChange();
    			}
    		});
        },

        createDateRangePicker: function (id1,id2) {
        	var textBox1=$('#'+id1);
        	var textBox2=$('#'+id2);
        	var $obj1=$('#'+id1).parent();
        	var $obj2=$('#'+id2).parent();

        	$obj1.daterangepicker({
    			autoUpdateInput: false,
    			locale: {
    				cancelLabel: 'Clear',
    				format: "YYYY-MM-DD"
    			}
    		});
        	$obj2.daterangepicker({
    			autoUpdateInput: false,
    			locale: {
    				cancelLabel: 'Clear',
    				format: "YYYY-MM-DD"
    			}
    		});
        	$obj1.on('apply.daterangepicker', function(ev, picker) {
        		textBox1.val(picker.startDate.format('YYYY-MM-DD'));
        		textBox2.val(picker.endDate.format('YYYY-MM-DD'));
    		});
        	$obj2.on('apply.daterangepicker', function(ev, picker) {
        		textBox1.val(picker.startDate.format('YYYY-MM-DD'));
        		textBox2.val(picker.endDate.format('YYYY-MM-DD'));
    		});
        	$obj1.on('cancel.daterangepicker', function(ev, picker) {
        		picker.setStartDate(new Date);
        		picker.setEndDate(new Date);
        		textBox1.val('');
        		textBox2.val('');
    		});
        	$obj2.on('cancel.daterangepicker', function(ev, picker) {
        		picker.setStartDate(new Date);
        		picker.setEndDate(new Date);
        		textBox1.val('');
        		textBox2.val('');
    		});
        }
    }
})($);

