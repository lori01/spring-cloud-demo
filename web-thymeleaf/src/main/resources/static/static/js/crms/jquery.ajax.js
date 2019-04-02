(function($){
	
 /**
  * 对于单个ajax请求的调用
  * 
  * 参数： 
  * url 为请求的地址，
  * data 为请求的参数,
  * type 为请求方式,
  * success 响应成功时的回调函数
  */
 $.ajaxquery=function(options){
	 var defaultOptions = {
	    		type:"POST",
	    		url:null,
	    		dataType:"json",
	    		data:null,
	    		isLoading:true,
	    		cache:false,
	    		async:true,
	    		success:null 
	    		
	    	};
	    	defaultOptions = $.extend(defaultOptions,options||{});
	    	 
	    	if(defaultOptions.isLoading)
	    		$.showProcessBar("请稍等","数据加载中，请稍等...","ajax");  //显示进度条
	    	 
	    	$.ajax({
	    		type:defaultOptions.type,
    			url:defaultOptions.url,
    			dataType:defaultOptions.dataType,
    			data:defaultOptions.data,
    			cache:defaultOptions.cache,
    			async:defaultOptions.async,
    			success:function(msg){
    				if(defaultOptions.isLoading){
    					$.closeProcessBar(null,"ajax");//关闭进度条
    				} 
    				//若description不为空且无传入success函数，则提示description的内容
    				if(!isEmpty(msg) && !isEmpty(msg.description) && isEmpty(defaultOptions.success)){
						$.alert("提示", msg.description, "remind");
    				}
					defaultOptions.success(msg) ;
    				
    			},
    			error:function(text) {
    				$.closeProcessBar();//关闭进度条
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
		 };
})(jQuery);
