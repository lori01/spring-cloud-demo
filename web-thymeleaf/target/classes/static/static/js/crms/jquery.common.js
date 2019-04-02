(function($){
	/**
	 * 获取basePath,例子："http://localhost:8080/jfast-webadmin"
	 */
	$.basePath=function(){
		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
		return basePath ;
	};
	/**
	 * 获取contextPath,例子："/jfast-webadmin"
	 */
	$.contextPath=function(){
		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		return contextPath ;
	};
})(jQuery);

function addPortalPanel(url,title){
	/*if(isEmpty(url)||isEmpty(title)){
		alert("url或title不能为空！");
		return;
	}*/
	var ifram_dom=arguments[2] ? arguments[2] : "iframe_proxy"; //回调函数名称
	if($('#'+ifram_dom).length>0){
		$('#'+ifram_dom).remove();
	}
	var exec_obj = document.createElement('iframe');  
    exec_obj.name = ifram_dom;  
    exec_obj.id = ifram_dom; 
    exec_obj.src = commonUrl.portal + '/portal/proxy.jsp?proxy_title='+encodeURI(encodeURI(title))+'&proxy_url='+escape(url)+'';  
    exec_obj.style.display = 'none';  
    document.body.appendChild(exec_obj); 
}

function closePortalPanel(title){
	/*if(isEmpty(url)||isEmpty(title)){
		alert("url或title不能为空！");
		return;
	}*/
	var ifram_dom=arguments[1] ? arguments[1] : "iframe_proxy1"; //回调函数名称
	if($('#'+ifram_dom).length>0){
		$('#'+ifram_dom).remove();
	}
	var exec_obj = document.createElement('iframe');  
	exec_obj.name = ifram_dom;  
    exec_obj.id = ifram_dom; 
    exec_obj.src = commonUrl.portal + '/portal/proxy.jsp?proxy_title='+encodeURI(encodeURI(title))+'&act=close';  
    exec_obj.style.display = 'none';  
    document.body.appendChild(exec_obj); 
}

/**
 * textarea文本域长度对齐  部分 start 
 */ 
//界面初始化时触发
$(document).ready(function(){
	if(isCanChangeAreaWidth()){
		textarea();
	};
	//浏览器键盘监听
	interceptKeyboard();
});
// 浏览器大小变更时，触发
$(window).resize(function() {  
	if(isCanChangeAreaWidth()){
		textarea();
	}
});

function changeAreaWidth(){
	if(isCanChangeAreaWidth()){
		textarea();
	}
}

function isCanChangeAreaWidth(){
	var flag = false;
	
	if ($.isFunction(window.textareaFunc)){
		var ret = textareaFunc();
		if(ret == false){
			// console.log("此界面不需要进行参数控制");
			return false;
		}
	}
	
	$("textarea").each(function(i){
		var tdColCount = $(this).parent().attr("colspan");
		if(tdColCount>1){// 有进行列合并的文本域才进行长度处理，否则沿用原有的长度设置
			flag = true;
		}
	})
	
	return flag;
}

function textarea(){
	var inputWidth = $('.inputclass').width();
    var areaWidth = 0;
    // 获取当前显示的第一个文本域的长度，用于其他隐藏起来的文本域长度
    // 场景：界面多个页签，只有第一个展示的文本域会进行长度处理，隐藏的文本域不会被变更
	 $("textarea").each(function(i){
		var tdColCount = $(this).parent().attr("colspan");
		if(tdColCount>1){// 有进行列合并的文本域才进行长度处理，否则沿用原有的长度设置
			var tableWidth= $(this).parents("table").width();// 所属table的宽度
			var width = 0;
			width = tableWidth/2+inputWidth;
			if($(this).is(":visible")){
				areaWidth = width;
			}
		}
	})
	// 进行界面所有文本域的长度处理
	$("textarea").each(function(i){
		var tdColCount = $(this).parent().attr("colspan");
		if(tdColCount>1){// 有进行列合并的文本域才进行长度处理，否则沿用原有的长度设置
			if($(this).is(":hidden")){
//				$(this).width(areaWidth);
			}else{
				var tableWidth= $(this).parents("table").width();// 所属table的宽度
				var width = 0;
				width = tableWidth/2+inputWidth;
				$(this).width(width);
			}
		}
	})
}
/**
 *  textarea文本域长度对齐  部分 end
 */

//拦截键盘按钮功能
function interceptKeyboard(){
	$(document).bind("keydown",function(event){
		event = window.event||event;
		//屏蔽 Alt+ 方向键 ← 
		//屏蔽 Alt+ 方向键 →
		if ((event.altKey)&&((event.keyCode==37)||(event.keyCode==39)))   
		{
			event.returnValue=false; 
			return false;
		};
		//屏蔽退格删除键
		if(event.keyCode==8){
			var d= event.srcElement||event.target; 
	        if(d.tagName.toUpperCase()=='INPUT'||d.tagName.toUpperCase()=='TEXTAREA'){ 
	        	// 如果是输入框，可以正常执行退格键作用
	        	return true;
	        }else{ 
	        	// 其他位置的退格，原本是回退上一url，屏蔽
	        	return false;
	        } 
		};
		//屏蔽F5刷新键，替换成刷新iframe里面的值
		if(event.keyCode==116){
			refreshCurrView();
			return false; 
		};
		//屏蔽alt+R 
		if((event.ctrlKey) && (event.keyCode==82)){
			return false;
		};
	});
}

/**
 * 切换页签方法
 * @param name
 * @param cursel
 * @param n
 */
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
	   var menu=document.getElementById(name+i);
	   var con=document.getElementById("con_"+name+"_"+i);
	   menu.className=i==cursel?"hover":"";
	   con.style.display=i==cursel?"block":"none";
	}
}

/**
 * 判断对象是否为空
 * @param _val
 * @returns {Boolean}
 */
function isEmpty(_val){
	if(typeof(_val)=="number" && _val==0)return false;
	if(typeof(_val)=="object" && _val instanceof Array){
		return _val.length<1;
	}
	return _val==null||_val==undefined || _val=="" || _val=="undefined";
}

/*
格式化数据，小数部分不做处理，对整数部分进行千分位格式化的处理，如果有符号，正常保留
*/
function formatAmt(num) 
{
  if(num && num.toString().indexOf('*') == -1)
  {
      //将num中的$,去掉，将num变成一个纯粹的数据格式字符串
      num = num.toString().replace(/\$|\,/g,'');
      //如果num不是数字，则将num置0，并返回
      if(''==num || isNaN(num)){return 'Not a Number ! ';}
      //如果num是负数，则获取她的符号
      var sign = num.indexOf("-")> 0 ? '-' : '';
      //如果存在小数点，则获取数字的小数部分
      var cents = num.indexOf(".")> 0 ? num.substr(num.indexOf(".")) : '';
      cents = cents.length>1 ? cents : '' ;//注意：这里如果是使用change方法不断的调用，小数是输入不了的
      //获取数字的整数数部分
      num = num.indexOf(".")>0 ? num.substring(0,(num.indexOf("."))) : num ;
      //如果没有小数点，整数部分不能以0开头
      if('' == cents){ if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
      //如果有小数点，且整数的部分的长度大于1，则整数部分不能以0开头
      else{if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
      //针对整数部分进行格式化处理，这是此方法的核心，也是稍难理解的一个地方，逆向的来思考或者采用简单的事例来实现就容易多了
      /*
        也可以这样想象，现在有一串数字字符串在你面前，如果让你给他家千分位的逗号的话，你是怎么来思考和操作的?
        字符串长度为0/1/2/3时都不用添加
        字符串长度大于3的时候，从右往左数，有三位字符就加一个逗号，然后继续往前数，直到不到往前数少于三位字符为止
       */
      for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
      {
          num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
      }
      //将数据（符号、整数部分、小数部分）整体组合返回
      return (sign + num + cents);    
  }else if(num.toString().indexOf('*') > -1){
	  return '*';
  }

}
function amtListenerFun(obj){
	var val = $(obj).val();
	if(val != null && val != undefined){
		val = val.replace(/,/g, "")
		var hasPoint = false;
		if(val.substr(val.length-1,1) == '.'){
			hasPoint = true;
		}
		val = formatAmt(val);
		if(hasPoint){
			$(obj).val(val + ".");
		}else $(obj).val(val);
	}
}
function amtListener(){
	$(".amt_input").bind('input propertychange', function() { 
		amtListenerFun(this);
	});
}

//对Date的扩展，将 Date 转化为指定格式的String 
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function(fmt) 
{ //author: meizz 
var o = { 
 "M+" : this.getMonth()+1,                 //月份 
 "d+" : this.getDate(),                    //日 
 "h+" : this.getHours(),                   //小时 
 "m+" : this.getMinutes(),                 //分 
 "s+" : this.getSeconds(),                 //秒 
 "q+" : Math.floor((this.getMonth()+3)/3), //季度 
 "S"  : this.getMilliseconds()             //毫秒 
}; 
if(/(y+)/.test(fmt)) 
 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
for(var k in o) 
 if(new RegExp("("+ k +")").test(fmt)) 
fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
return fmt; 
}

//获取url参数
function getUrlParString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
} 

//小数位不够，用0补足位数
function changeDecimalBuZero(number, bitNum) {
	var f_x = parseFloat(number);
	if (isNaN(f_x)) {
		return 0;
	}
	var s_x = number.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + bitNum) {
		s_x += '0';
	}
	return s_x;
}