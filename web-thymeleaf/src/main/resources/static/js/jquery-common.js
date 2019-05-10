(function ($) {
	
	/*
	 * (function ($) {
	 * *****
	 * })(jQuery);
	 * 
	 * 扩展jquery的方法
	 * 1.$.myfun = function(){};
	 * 用法：$.myfun();
	 * 
	 * 2.$.fn.myfun = function(){};
	 * 用法：$("#id").myfun();
	 * 
	 * */
	
	//var xx = $.getUrlParam('reurl');
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    };
    
    //打印日志
    $.printLog = function(name){            //print1是自己定义的函数名字，括号中的name是参数
        console.log(name)
    };
    
	//打开弹出框
    $.showAlertMessage = function(type, title, message, _backfunction) {
		if (type == "error") {
			$("#alert_message_success_img").hide();
			$("#alert_message_error_img").show();
		} else {
			$("#alert_message_success_img").show();
			$("#alert_message_error_img").hide();
		}
		$("#alert_message_span").html(message);
		$("#alert_message_btn").click(function(e) {
			$.cancleMessageDiv();
			_backfunction();
		});
		$("#alert_message").dialog({
			title : title,
			zIndex : 99999,
			width : 500,
			height : 300,
			autoOpen : true,
			show : {
				effect : showEffect,
				duration : animation_time
			},
			hide : {
				effect : hideEffect,
				duration : animation_time
			}
		});
	};
    
    //关闭弹出框
    $.cancleMessageDiv = function() {
    	//$("#upd_pwd_div").fadeOut();
    	$("#alert_message").dialog("close");
    };
    
	//判断框
    $.showConfirmMessage = function(title, message, _backfunction) {
		$("#confirm_message_span").html(message);
		$("#confirm_message_btn").click(function(e) {
			$.cancle_confirm_message();
			_backfunction();
		});
		$("#confirm_message").dialog({
			title : title,
			zIndex : 99999,
			width : 500,
			height : 300,
			autoOpen : true,
			show : {
				effect : showEffect,
				duration : animation_time
			},
			hide : {
				effect : hideEffect,
				duration : animation_time
			}
		});
	};
    
    //关闭判断框
    $.cancle_confirm_message = function(){
    	$("#confirm_message").dialog("close");
    };
    
    
})(jQuery);

//日期
function Format(now, mask) {
	var d = now;
	var zeroize = function(value, length) {
		if (!length)
			length = 2;
		value = String(value);
		for (var i = 0, zeros = ''; i < (length - value.length); i++) {
			zeros += '0';
		}
		return zeros + value;
	};

	return mask
			.replace(
					/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g,
					function($0) {
						switch ($0) {
						case 'd':
							return d.getDate();
						case 'dd':
							return zeroize(d.getDate());
						case 'ddd':
							return [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri',
									'Sat' ][d.getDay()];
						case 'dddd':
							return [ 'Sunday', 'Monday', 'Tuesday',
									'Wednesday', 'Thursday', 'Friday',
									'Saturday' ][d.getDay()];
						case 'M':
							return d.getMonth() + 1;
						case 'MM':
							return zeroize(d.getMonth() + 1);
						case 'MMM':
							return [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
									'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ][d
									.getMonth()];
						case 'MMMM':
							return [ 'January', 'February', 'March', 'April',
									'May', 'June', 'July', 'August',
									'September', 'October', 'November',
									'December' ][d.getMonth()];
						case 'yy':
							return String(d.getFullYear()).substr(2);
						case 'yyyy':
							return d.getFullYear();
						case 'h':
							return d.getHours() % 12 || 12;
						case 'hh':
							return zeroize(d.getHours() % 12 || 12);
						case 'H':
							return d.getHours();
						case 'HH':
							return zeroize(d.getHours());
						case 'm':
							return d.getMinutes();
						case 'mm':
							return zeroize(d.getMinutes());
						case 's':
							return d.getSeconds();
						case 'ss':
							return zeroize(d.getSeconds());
						case 'l':
							return zeroize(d.getMilliseconds(), 3);
						case 'L':
							var m = d.getMilliseconds();
							if (m > 99)
								m = Math.round(m / 10);
							return zeroize(m);
						case 'tt':
							return d.getHours() < 12 ? 'am' : 'pm';
						case 'TT':
							return d.getHours() < 12 ? 'AM' : 'PM';
						case 'Z':
							return d.toUTCString().match(/[A-Z]+$/);
							// Return quoted strings with the surrounding quotes removed
						default:
							return $0.substr(1, $0.length - 2);
						}
					});
};

Date.prototype.format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

/*
 * 格式化数据，小数部分不做处理，对整数部分进行千分位格式化的处理，如果有符号，正常保留
 */
function formatAmt(num) {
	if (num && num.toString().indexOf('*') == -1) {
		//将num中的$,去掉，将num变成一个纯粹的数据格式字符串
		num = num.toString().replace(/\$|\,/g, '');
		//如果num不是数字，则将num置0，并返回
		if ('' == num || isNaN(num)) {
			return 'Not a Number ! ';
		}
		//如果num是负数，则获取她的符号
		var sign = num.indexOf("-") > 0 ? '-' : '';
		//如果存在小数点，则获取数字的小数部分
		var cents = num.indexOf(".") > 0 ? num.substr(num.indexOf(".")) : '';
		cents = cents.length > 1 ? cents : '';//注意：这里如果是使用change方法不断的调用，小数是输入不了的
		//获取数字的整数数部分
		num = num.indexOf(".") > 0 ? num.substring(0, (num.indexOf("."))) : num;
		//如果没有小数点，整数部分不能以0开头
		if ('' == cents) {
			if (num.length > 1 && '0' == num.substr(0, 1)) {
				return 'Not a Number ! ';
			}
		}
		//如果有小数点，且整数的部分的长度大于1，则整数部分不能以0开头
		else {
			if (num.length > 1 && '0' == num.substr(0, 1)) {
				return 'Not a Number ! ';
			}
		}
		//针对整数部分进行格式化处理，这是此方法的核心，也是稍难理解的一个地方，逆向的来思考或者采用简单的事例来实现就容易多了
		/*
		  也可以这样想象，现在有一串数字字符串在你面前，如果让你给他家千分位的逗号的话，你是怎么来思考和操作的?
		  字符串长度为0/1/2/3时都不用添加
		  字符串长度大于3的时候，从右往左数，有三位字符就加一个逗号，然后继续往前数，直到不到往前数少于三位字符为止
		 */
		for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
			num = num.substring(0, num.length - (4 * i + 3)) + ','
					+ num.substring(num.length - (4 * i + 3));
		}
		//将数据（符号、整数部分、小数部分）整体组合返回
		return (sign + num + cents);
	} else if (num.toString().indexOf('*') > -1) {
		return '*';
	}

}

//获取url参数
function getUrlParString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
