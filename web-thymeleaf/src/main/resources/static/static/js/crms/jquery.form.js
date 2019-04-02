/**
 * 用途：适配易开表单提交
 * 	@author Alon Cai
 * 用法说明：
 *  $("#formId").initSumit({});
 * 	引入 jq, 页面中 form 增加属性：ajax="[true/false:是否走AJAX]" submitType="[submit/submitx:对应易开中的值]"
 * 	[jq表单对象]._submit({ids:[可选，只传的表单元素ID表，空提交所有],currPage:[可选，当前页],pageSize:[可选，每页记录],start:[可选，起始], callback: [回调函数(参数xml对象)])
 * 其它说明：
 *  本方法调用需要登录状态，否则会跳转到登录页
 */
var formDataArray={};

(function($){
    /**
     * 初始化提交，绑定form表单的“提交”和“重置”事件
     */
	$.fn.initSubmit = function(options){
		var _formId=$(this).attr("id");
		if(options.leaveConfirm==true){
			$(this).leaveConfirm();
		}
		$("#"+_formId).unbind("submit");
		$("#"+_formId).bind("submit",function(){
			//console.log("calll submit.....");
			try{
				$("#"+_formId)._submit(options);
			}catch(e){
				if (window.console){
					console.error("JS 提交报错："+e.message);
				}
			}
			return false;
		});
		//绑定“重置”按钮的点击事件
		$("#"+_formId).find(":reset").unbind("click");
		$("#"+_formId).find(":reset").bind("click",function(){
			try{
				var hidden2=$("#"+_formId).find(":input:hidden");
				/*$.each(hidden2,function(){
					$(this).attr("initVal",$(this).getValue());
				});*/
				//把搜索框的值清空，再还原隐藏输入框的值
				$("#"+_formId)[0].reset();
				$.each(hidden2,function(){
					///if($(this).attr("initVal")!=null && $(this).attr("initVal")!=undefined && $(this).attr("initVal")!='undefined'){
					$(this).setValue($(this).attr("initVal"));
						//$(this).setValue("");
					//}
				});
			}catch(e){
				if (window.console){
					console.error("JS 提交报错2："+e.message);
				}
			}
			return false;
		});
	}
    $.fn._submit= function(options){
    	var defaultOptions = {
    		validator: null,
    		tableID: "",
    		ids:null,
    		currPage:1,
    		pageSize:10,
    		start:0,
    		ignoreField:[],
    		jsonFiled:[],
    		ignoreEmpty:false,
    		callback: function(data){
    			//回调函数，若ResponseVO中的description不为空，则提示
    			if(data!=null && data['responseVO']!=null && data['responseVO'].description!=null
    					      &&data['responseVO'].description!=undefined){
    				var description = data['responseVO'].description;
    				$.alert("提示",description, description.indexOf("成功")>=0?'success':'failure');
    			}
    		}
    	};
    	defaultOptions = $.extend(defaultOptions,options||{});
    	
    	//每页条数
    	if(defaultOptions.tableID!="" && $("#"+defaultOptions.tableID)!=null && $("#"+defaultOptions.tableID)!=undefined){
    		defaultOptions.pageSize=$("#"+defaultOptions.tableID).attr("pageSize")?$("#"+defaultOptions.tableID).attr("pageSize"):defaultOptions.pageSize;
    	}
    	//检验器验证
    	if(defaultOptions.validator!=null && false==defaultOptions.validator.form()){
    		return;
    	}
    	var useAjax=$(this).attr("ajax");
    	if(useAjax==null || useAjax==undefined){
    		useAjax=true;
    	}
    	// 如果设置了预处理操作,即js的函数名，在form提交前，先执行
    	var beforeSubmitOpe = $(this).attr("beforeSubmitOpe");
    	if(beforeSubmitOpe !=null && beforeSubmitOpe != undefined && beforeSubmitOpe.length>0){
    		var flag = eval(beforeSubmitOpe+'()');
    		if(flag != null && flag != undefined && flag == false ){
    			// 返回结果为失败
    			return  ;
    		}
    	}
    	var ignoreField="page,limit,start"+(defaultOptions.ignoreField.length>0?",":"")+defaultOptions.ignoreField.join(",");
    	if(useAjax){
    		//表单提交的数据
    		var dataValue = $(this).getFormData(defaultOptions.ignoreEmpty,'',defaultOptions.jsonFiled);   
    		if(defaultOptions.tableID!=''){ //有表格则需要分页
    			if(dataValue["page"]==null){
    				dataValue["page"]=defaultOptions.currPage;//当前页数
    			}
    			if(dataValue["limit"]==null){
    				dataValue["limit"]=defaultOptions.pageSize;//每页条数
    			}
//    			if(dataValue["start"]==null){
//    				dataValue["start"]=defaultOptions.start;//本页起始
//    			}
    		}
    		
    		var _dialog=$.showProcessBar("请稍等","数据加载中，请稍等...");  //显示进度条
    		var action=$(this).attr("action");
    		if(action==undefined||action==""){
    			action=location.href.replace(location.search,"");
    		}
    		var method=$(this).attr("method");
    		if(method==undefined||method==""){
    			method="post";
    		}
    		if($(this).attr("action")==null||$(this).attr("action")==undefined){
    			action=location.href.replace(location.search,"");
    		}else{
    			action=$(this).attr("action");
    		}
    		$.ajax({
    			type: method,
    			url: action,
    			dataType:"json",
    			data: dataValue,
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
    	}else{
    		$(window).off("beforeunload");
    		$(this).submit();
    	}
    }
    //input
    $.fn.getValue = function(){
    	if($(this).attr("data-inputmask")!=undefined){
    		var tmp=eval("({"+$(this).attr("data-inputmask")+"})");
    		if(tmp.alias=='numeric'){
    			// 因为金额的格式回退到最初版本，修改内容也撤销。
    			return $(this).inputmask('unmaskedvalue');
    		}
    	}
    	if($(this).attr("json")!=undefined && $(this).attr("json")!=null && $(this).attr("json")=='true'){
    		return eval("("+$(this).val()+")");
    	}else{    		
    		return $(this).val();
    	}
    }
    $.fn.setValue = function(_val){
    	$(this).each(function(){
	    	var name=$(this).attr("name");
	    	if($(this).attr("name")=="processOpinion" && _val==""){
	    		_val="同意";
	    	}
	    	if($(this).attr('type')=='radio'){
	    		$(":radio[name='"+name+"']").prop("checked",false);
	    		$(":radio[name='"+name+"']").each(function(){
	    			if(typeof(_val)=='object' && compareJsonObject(_val,eval("("+$(this).val()+")"))){
	    				$(this).prop("checked",true);
	    			}else if(""+_val==$(this).val()){
	    				$(this).prop("checked",true);
	    			}
	    		});
	    	}else if($(this).attr('type')=='checkbox'){
	    		var tmp=new Array();
	    		if(_val instanceof Array){
	    			tmp=_val;
	    		}else if(_val instanceof Object){
	    			tmp.push(_val);
	    		}else{
	    			if(_val.indexOf(",")>0){
	    				tmp=_val.split(',');
	    			}else{
	    				tmp.push(_val);
	    			}
	    		}
	    		$(":checkbox[name='"+name+"']").prop("checked",false);
	    		$(":checkbox[name='"+name+"']").each(function(){
	    			for(var i=0;i<tmp.length;i++){
	    				if(typeof(tmp[i])=='object' && compareJsonObject(tmp[i],eval("("+$(this).val()+")"))){
	    					$(this).prop("checked",true);
	    					break;
	    				}else{
	    					if(""+tmp[i]==$(this).val()){
	    						$(this).prop("checked",true);
	    						break;
	    					}
	    				}
	    			}
	    		});
	    	}
	    	// 因为金额的格式回退到最初版本，修改内容也撤销。
//	    	else if($(this).attr("data-inputmask")!=undefined){
//	    		// 进行金额类型的转换，因为目前data-inputmask采用'placeholder': ''的形式
//	    		// 此方案中，如果不转换，假设传入的值为“100”，界面输入框会展示成：“100.”，格式不完整
//	    		if(_val == null || _val == undefined || _val.length==0 || (_val.length==1 && _val == '.')){
//	    			_val=0;
//				}
//	    		if( typeof(_val) == 'string'){
//	    			//因为后台的字段可能是字符串类型，不支持toFixed方法，需要进行转换一下。
//	    			_val = parseFloat(_val);
//	    		}
//	    		var fixVal = _val.toFixed(2); 
//	    		$(this).val(fixVal);
//	    	}
	    	else{
	    		if(typeof(_val)=='object'){
	    			try{
	    				$(this).val(JSON.stringify(_val));
	    			}catch(e){
	    				$(this).val(_val);
	    			}
	    		}else{
	    			$(this).val(_val);
	    		}
	    	}
    	});
    }
    
    
    /**
     * 获取form的json（返回值为对象）
     * @param ignoreEmpty 忽略空。 可选，默认 false
     * @param ignoreFieldName 忽略名称，多个用逗号相隔
     */
    $.fn.getFormData= function(){
    	var _ignoreEmpty=arguments[0] ? arguments[0] : false;
    	var _ignoreFieldName=arguments[1] ? arguments[1] : "";
    	var jsonFiled=arguments[2] ? arguments[2] : [];
		var data = {};
    	$(this).find(':text,:hidden,:password,textarea,:checkbox:checked,:radio:checked,select').not(':button,:submit,:reset').each(function(i){
		    if($(this).attr('name') != null && $(this).attr('name') != "" && contain(_ignoreFieldName,$(this).attr('name'))<0){
		        var _obj = $(this);
		        if(_ignoreEmpty==false || _obj.getValue()!=''){
		        	if(contain(jsonFiled.join(","),_obj.attr("name"))>=0){
		        		//_setJsonData(data,_obj.attr("name"),eval("("+_obj.getValue()+")"));
		        		//在设定了json对象参数后，此obj可能传入空对象，如数组的"[]"和对象的"{}"，
		        		//在这种情形下，走上面的代码，在封装数据的时候，会将数据转换成null，赋值给data中的参数，导致提交的时候相应的参数值是null，而不是对应的空对象
		        		//而且typeof(jsonData[jsonName])=="object",同时导致$.fn._submit 方法中，不进入if(defaultOptions.jsonFiled.length>0){}代码块中的最里层处理，
		        		//现在修改，将json 设定的对象放到外层处理，即$.fn._submit 方法中的代码块的数据处理。
		        		_setJsonData(data,_obj.attr("name"),_obj.getValue());
		        	}else{
		        		_setJsonData(data,_obj.attr("name"),_obj.getValue());
		        	}
    			}
		    }
	 	});
		return data;
    };
    /**
     * 根据ID获取JSON数据
     * @param ignoreEmpty 忽略空。 可选，默认 false
     */
    $.fn.getIdsData= function(ids){
    	var _ignoreEmpty=arguments[1] ? arguments[1] : false;
    	var _ignoreFieldName=arguments[2] ? arguments[2] : "";
    	var _jsonFields=arguments[3] ? arguments[3] : [];
    	var data = {};
		if(ids){
			$.each(ids,function(n,id){
    			var _obj = $("#"+id);
    			if((_ignoreEmpty==false || _obj.getValue()!='')&& contain(_ignoreFieldName,$(this).attr('name'))<0){ //不忽略或有值
    				if(contain(jsonFiled.join(","),_obj.attr("name"))>=0){
		        		_setJsonData(data,_obj.attr("name"),eval(_obj.getValue()));
		        	}else{
		        		_setJsonData(data,_obj.attr("name"),_obj.getValue());
		        	}
    			}
    		});
		}
		return data;
    };
    /**
     * 根据ID获取JSON数据
     * @param ignoreEmpty 忽略空。 可选，默认 false
     */
    $.fn.getNamesData= function(names){
    	var _ignoreEmpty=arguments[1] ? arguments[1] : false;
    	var _ignoreFieldName=arguments[2] ? arguments[2] : "";
    	var data = {};
    	var obj=$(this);
    	if(names){
    		$.each(names,function(n,name){
    			var _obj = obj.find("input[name='"+name+"']");
    			if((_ignoreEmpty==false || _obj.getValue()!='')&& contain(_ignoreFieldName,$(this).attr('name'))<0){ //不忽略或有值
    				_setJsonData(data,_obj.attr("name"),_obj.getValue());
    			}
    		});
    	}
    	return data;
    };
    /**
     * 将JSON数据填充至表单
     */
    $.fn.setFormData= function(jsonObj){
    	var _dontClean=arguments[1] ? arguments[1] : false;
    	var ignoreField="_type,_comp,_event,_field,Request-from,page,limit,start,wfopinions";
    	$(this).each(function(){
			if(jsonObj){
				var target = $(this);
				target.find("[field],:input").not(':button,:submit,:reset').each(function(i){
					var field = $(this).attr("field");
					if(field!=null && field!=undefined){
						var _field = field.match(/\{\w*\d*\}/g);
			    		if(_field!=null){
			    			$.each(_field,function(k,d){
			            		var _field1 = d.substr(1,d.length-2);
			            		field = field.replace(/\{\w*\d*\}/,jsonObj[_field1]);
			            	});
			            	try{
			            		if(/<(\/)*(\w+)([^<]*)(\/*)>/g.test(field)){ //html 代码，忽略
			            		}else{
			            			field = eval(field);
			            		}
			            	}catch(e) {
			            		if (window.console){
			            			console.error("JS 报错："+e.message);
			            		}
			            	}
			    		}
					}else if(($(this).is("input") || $(this).is("select") || $(this).is("textarea"))&& 
							!($(this).attr("field")!=null&&$(this).attr("field")!=undefined)){
						field = jsonObj[$(this).attr('name')];
					}
		    		if(null == field || undefined == field){
		    			field = "";
		    		}
		    		if(_dontClean && field==""){
		    			if(parseInt(field)!=0){
		    				return;
		    			}
		    		}
		    		if($(this).is("input") || $(this).is("select") || $(this).is("textarea")){ 
		    			//对于意见框，如果值为空，默认一个“同意”
		    			if($(this).is("textarea") && ($(this).attr("name")=="opion"||$(this).attr("name")=="opin") && field==""){
		    				field="同意";
		    			}
		    			if(contain(ignoreField,$(this).attr('name'))>=0){return;}
		    			$(this).setValue(field);
		    		}else if($(this).is("img")){ //图片
		    			$(this).attr("src",field);
		    		}else if(field.indexOf('<')>=0 && field.indexOf('>')>field.indexOf('<')){ //html标签
	    				$(this).html(field);
	    			}else{ //普通文字显示
	    				$(this).text(field);
	    			}
				});
			}
    	});
    };

      /**
       * reset Form 元素中的值，对于hidden清空不掉
       */
	  $.fn.resetForm=function(){
		  var target =$(this) ;
		  target[0].reset();
		  target.find(':input[type=hidden]').each(function(i){
			  var _obj = $(this);
			  _obj.val('');
		  }) ;
	  } ;
    
    /**
     * 表单未保存离开提示
     */
    $.fn.leaveConfirm = function(){
    	var _confirmMsg=arguments[0] ? arguments[0] : "你的表单数据未保存";
    	var monitform = $(this);
    	var _key=monitform.attr("id")+"_initData";
    	formDataArray[_key]=monitform.getFormData();
    	$(this).find(":input").not("button").on("change",function(){
    		var newformData=monitform.getFormData();
    		if(compareJsonObject(formDataArray[_key],newformData)==false){
    			$(window).on("beforeunload", function(){
    				return _confirmMsg;
    			});
    		}else{
    			$(window).off("beforeunload");
    		}
    	});
    	if(this.attr("ajax")==null || this.attr("ajax")==undefined){
    		$(this).find(":submit").on("click",function(){
    			$(window).off("beforeunload");
    			return true;
    		});
    	}
    };
    /**
     * 取消表单未保存离开提示
     */
    $.fn.noleaveConfirm = function(){
    	$(window).off("beforeunload");
    }
    /**
     * 名称带点的转JSON对象中包含对象
     */
    function _setJsonData(_json,_name,_val){
    	if(_name!=null && _name!=""){
    		var narr = _name.split(".");
    		var o = _json;
    		for (var i=0,l=narr.length;i<l;i++){
    			if((i+1)==l){
    				if(o[narr[i]]!=null&&o[narr[i]]!=undefined){
    					if(typeof(_val)=='object'){ //值为对象时
    						if(!(o[narr[i]] instanceof Array)){
    							var jsonObj=o[narr[i]];
    							o[narr[i]]=[];
    							o[narr[i]].push(jsonObj);
    						}
    						o[narr[i]].push(_val);
    					}else{
    						if(	_val.charAt(0)=="{" && _val.charAt(_val.length-1)=="}"){
    							o[narr[i]]=o[narr[i]]+","+_val;
    						}else{
    							var tmp=_val.split(",");
    							for(var j=0;j<tmp.length;j++){
    								if(contain(o[narr[i]],tmp[j])<0){ //值已存在则放弃
    									o[narr[i]]=o[narr[i]]+","+tmp[j];
    								}
    							}
    						}
    					}
    				}else{
    					if(_val==''||_val==undefined){
    						o[narr[i]]=null;
    					}else{
    						o[narr[i]]=_val;
    					}
    				}
    			}else{
    				if(o[narr[i]]==null){
    					o[narr[i]]={};
    				}
    				o=o[narr[i]];
    			}
    		}
    	}
    };
    /**
     * 判断字符串1是否包含后面的值
     * @param arrStr 字符串1（各个值用逗号相隔）
     * @param str 字符串2
     * @returns 返回索引，始于0，不存在返回-1
     * 如：
     * 	constain('abcd,xyz,dd','xyz')==1
     * 	constain('abcdxyz,dd','xyz')==-1
     * 	constain('xyz,abcd,dd','xyz')==0
     */
    function contain(arrStr,str){
    	if(arrStr==''){return -1;}
    	var arr=arrStr.split(",");
    	for(var i=0;i<arr.length;i++){
    		if(arr[i]==str) return i;
    	}
    	return -1;
    };
})(jQuery);


