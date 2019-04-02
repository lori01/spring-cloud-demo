/**
 * 项目url跳转的记录和指向
 * 目前现状：
 * 目前有返回按钮的地方，都在相应的处理界面中，触发返回的时候，是返回到菜单配置的搜索列表中。
 * 目前机制：
 * 在每个菜单url刷新进入的时候，通过jquery.buss.js中的$(function(){})方法，将当前url压入indexmain.jsp缓存的数组中。
 * 因此在获取搜索列表的时候，需要将缓存数租pop两次，在第二次拿到的才是要跳转的url
 */
var defaultOptions = {
		type: "",
		url: "",
		data: {},
		callback: function(data){
			if($(data).find("d").length>0){
				var dstr = $(data).find("d").text();
				var _data = eval("("+dstr+")");
				if(_data._tipMsg!=null && _data._tipMsg!=undefined && _data._tipMsg.msg!=null && _data._tipMsg.msg!=undefined){
					$.alert("提示",_data._tipMsg.msg, _data._tipMsg.msg.indexOf("成功")>=0?'success':'failure');
				}
			}
			if($(data).find("s").length>0){
				var sstr = $(data).find("s").text();
				$('body').append($("<script type='text/javascript'>"+sstr+"</script>"));
			}
		}
	};

/**
 * 将url压入缓存数组中
 */
$.addUrl = function(options){
	var httpUrlArray = getParentHttpUrlArr();
	if(httpUrlArray == null){
		return ;
	}
	defaultOptions = $.extend(defaultOptions,options||{});
	httpUrlArray.push(defaultOptions);
	
	setParentHttpUrlArr(httpUrlArray);
}

/**
 * 获取url数组中，相应的要跳转的列表url进行数据跳转 start
 */
$.redirectUrl = function(){
	var httpUrlArray = getParentHttpUrlArr();
	defaultOptions = getUrlArrayOptions(httpUrlArray);
	
	if(defaultOptions.type == 'get'){
		redirectUrlForwat(defaultOptions);
	}else{
		redirectUrlAjax(defaultOptions);
	}
	setParentHttpUrlArr(httpUrlArray);
}
function redirectUrlSubmit(){
	var httpUrlArray = getParentHttpUrlArr();
	defaultOptions = getUrlArrayOptions(httpUrlArray);
	
	if(defaultOptions.type == 'get'){
		redirectUrlForwat(defaultOptions);
	}else{
		redirectUrlAjax(defaultOptions);
	}
	
	setParentHttpUrlArr(httpUrlArray);
}

function getUrlArrayOptions(httpUrlArray){
	if(httpUrlArray == null || httpUrlArray == undefined || httpUrlArray.length < 2){
//		$.alert("提示","界面跳转异常，返回首页!","remind");
		defaultOptions = {
			type: "get",
			url: basePath+"/pages/indexnew.jsp"
		};
	}else{
		httpUrlArray.pop();// 将当前界面的url弹出
		defaultOptions = httpUrlArray.pop();//获取前一界面的url
	}
	return defaultOptions;
}

//刷新当前页面
function refreshCurrView(){
	var httpUrlArray = getParentHttpUrlArr();
	if(httpUrlArray == null || httpUrlArray == undefined || httpUrlArray.length < 1){
//		$.alert("提示","界面跳转异常，返回首页!","remind");
		defaultOptions = {
			type: "get",
			url: basePath+"/pages/indexnew.jsp"
		};
	}else{
		defaultOptions = httpUrlArray.pop();// 将当前界面的url弹出
	}
	
	if(defaultOptions.type == 'get'){
		redirectUrlForwat(defaultOptions);
	}else{
		redirectUrlAjax(defaultOptions);
	}
	
	setParentHttpUrlArr(httpUrlArray);
}
/**
 * 获取url数组中，相应的要跳转的列表url进行数据跳转 end
 */

/**
 * 根据拿到的url进行最后的界面刷新 start
 */
function redirectUrlAjax(defaultOptions){
	var _dialog=$.showProcessBar("请稍等","数据加载中，请稍等...");  //显示进度条
	$.ajax({
		type: defaultOptions.method,
		url: defaultOptions.url,
		dataType:"xml",
		data: defaultOptions.data,
		complete:function(XMLHttpRequest,textStatus) {
			if(XMLHttpRequest.status == 535){
		      //如果超时就处理 ，指定要跳转的页面 
		      dialog({
					title:"提醒信息",
					width:773,
					content:"登陆超时，请重新登陆！",
					okvalue:"确定",
					ok:function(){
					 	window.location.replace("${base}/loginAction/login.do");
					 },
					 onclose:function(){
					  window.location.replace("${base}/loginAction/login.do");
					 }
				}).showModal(); 
			}
		},
		success: function(data){
			$.closeProcessBar(_dialog);
			defaultOptions.callback(data);
		},
		error:function(text) {
			$.closeProcessBar(_dialog);//关闭进度条
			if(typeof(text)=='object'){
				if($("#credential",text.responseText).length>0){
					$.alert('出错了','用户超时，请重新登陆.','failure',function(){
						location.href=commonUrl.portal+"/portal/logout";
					});
					return;
				}
			}
			$.alert('出错了','请求后台出错.','failure');
		} 
	});
}
// 界面跳转，利用href直接界面刷新
function redirectUrlForwat(defaultOptions){
	$.showProcessBar("请稍等","数据加载中，请稍等...");  //显示进度条, 界面重新加载会自动关闭
	location.href=defaultOptions.url;
}
/**
 * 根据拿到的url进行最后的界面刷新 end
 */

// 获取父窗口的url缓存数组，目前只记录菜单对应的url，即在indexmain.jsp包含的iframe（以下简称fa）的url，
// 至于fa界面里面再次引用的iframe地址，不需要保理，因为此功能，只暂时用于返回按钮，而返回按钮直接返回到前搜索列表
function getParentHttpUrlArr(){
	if($.isFunction(window.parent.getHttpUrlArray)){
		return window.parent.getHttpUrlArray() ;
	}else{
		return null;
	}
}
//重新将变更的url数组替换到最外层iframe的父类窗口的变量中
function setParentHttpUrlArr(httpUrlArray){
	if($.isFunction(window.parent.setHttpUrlArray)){
		window.parent.setHttpUrlArray(httpUrlArray);
	}
}