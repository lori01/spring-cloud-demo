/**
 * 用途：用于页面规则处理
 * @author youjunguo
 * 使用前提：引入jquery
 * 方法:
 *  [setCombBox]：           下拉框在数据字典的取值
 * 	[setMoneyFormat]:       金额格式初始化
 * 	[setDateFormat]:        class="inputdatepicker"的控件做初始化
 * 	[bindChangeEvent]:      有onchange属性控件的change事件的绑定
 * 	[setRadioCheckBox]:     单选框、复选框在数据字典的取值
 * 	[setVisitMode]:         显隐控制
 * 	[setFieldValue]:        数据赋值
 * 	[setMessage]:           消息提示
 * 	[bindButtonClickEvent]: 按钮单击事件绑定
 *  
 */


/**
 * 下拉框在数据字典的取值
 * @param cdi
 */
function setCombBox(cdi){
	$("select").each(function(i){
		var id = $(this).attr("id");
		if(!isEmpty(id)){
			var _data = cdi[id];
			if(!isEmpty(_data)){
				var val = _data;
				$(this).html('');//下拉框，每次清掉内容再重新赋值
			   	for(j in val){
			   		 var bodyContent = '';
			   		 if(val[j].selected == true){
						  bodyContent='<option value="'+val[j].strVal+'" selected="selected">'+ val[j].name+'</option>';
					 }else{
						  bodyContent='<option value="'+val[j].strVal+'">'+ val[j].name+'</option>';
					 }
		 			 $('#'+id).append(bodyContent);
			 	}
			}
		}
	});
}

/**
 * 金额格式初始化
 */
function setMoneyFormat(){
	//class=inputclassmoney的控件添加金额格式化
	$(".inputclassmoney").each(function(i){
		$(this).removeClass("inputclassmoney");
		$(this).addClass("inputclass");
		$(this).attr("data-inputmask","'alias': 'numeric','repeat': 18, 'groupSeparator': ',', 'autoGroup': true, 'digits': 2, 'digitsOptional': false, 'placeholder': '0'");
	});

	//金额格式
	$(":input").inputmask();
}

/**
 * class="inputdatepicker"的控件做日期格式初始化
 */
function setDateFormat(){
	$(".inputdatepicker").each(function(i){
		$(this).datepicker({
			inline: true,
			showButtonPanel: true,
			changeYear: true,
			changeMonth: true
		});
	});
}

/**
 * 有onchange属性控件的change事件的绑定
 */
function bindChangeEvent(){
	$("input[onchange='true'],select[onchange='true']").each(function(i){
		var action = $(this).attr("action");
		if(!isEmpty(action)){
			$(this).bind("change",function(){
				try{
					var id = $(this).attr("id");    
					var data = '';
					if(!isEmpty($(this).closest('form'))){
						data = $(this).closest('form').getFormData();
					}
				    var requesturl = $.basePath()+action;
					var requestdata = 'data='+$.jsonObjToStr(data); 
					$.ajaxquery({"url":requesturl,"data":requestdata});
				}catch(e){
					if (window.console){
						console.error("JS 提交报错："+e.message);
					}
				}
			});
		}
	});
}

/**
 * 单选框、复选框在数据字典的取值
 * @param cdi
 */
function setRadioCheckBox(cdi){
	$("input[type='radio'],input[type='checkbox']").each(function(i){
		var id = $(this).attr("id");
		var name = $(this).attr("name");
		var type = $(this).attr("type");
		if(!isEmpty(id)){
			var _data = cdi[name];
			if(!isEmpty(_data)){
			   	var val = _data;
			   	var bodyContent = '';
			   	var parent = null;
			   	if($("input[name='"+name+"']").length>0){
			   		parent = $($("input[name='"+name+"']")[0]).parent();
			   	}else{
			   		parent = $(this).parent();
			   	}
			   	if(!isEmpty(parent)){
			   		parent.html('');
			   		for(j in val){
			   			if(j==0)continue;
			   			if(val[j].selected == true){
			   				bodyContent= bodyContent+'<input type="'+type+'" name="'+name+'" id= "'+id+j+'" value="'+val[j].strVal+'" checked="checked" >'+ val[j].name+'</input>';
			   			}else{
			   				bodyContent= bodyContent+'<input type="'+type+'" name="'+name+'" id= "'+id+j+'" value="'+val[j].strVal+'">'+ val[j].name+'</input>';
			   			}
			   		}
			   		$(parent).append(bodyContent);//把原来的控件的代码替换成从数据字典拼装的代码
			   	}
			}
		}
	});
}

/**
 * 显隐控制
 * @param vm
 */
function setVisitMode(vm){
	$.each(vm, function(key, value){
		if(!isEmpty(value.oper) && !isEmpty(value.mode)){
    	   if('S' == value.mode){
    		   if('T' == value.oper){
				   $("#"+key).show(); //显示控件
    		   }else if('F' == value.oper){
	    		   $("#"+key).hide();//隐藏控件 
    		   }
    	   }else if('E' == value.mode){
    		   if('F' == value.oper){
			 		//先判断是否为div
		    		if(!isEmpty($("#"+key)) && !isEmpty($("#"+key)[0]) && $("#"+key)[0].tagName=="DIV"){
						$("#"+key).find(":input").not(':button,:submit,:reset').each(function(i){//循环处理div中的控件
			    		    //单选框、复选框的处理(因为单选框和复选框是input标签，在取数据字典的时候，替换了input标签，也重新定义了id的值，所以要特殊判断)
		    			    if(isEmpty($(this)) || $($(this)).length==0){
		    				   $("input[name='"+$(this).name+"']").each(function(){
		    					   if($(this).attr("type")=="radio" || $(this).attr("type")=="checkbox"){
			    					   $(this).attr("disabled","disabled");
		    					   }
		    				   });
		    			    }else{
		    			    	$(this).attr("disabled","disabled");//设置为不可编辑
		    			    }
						});
		        	}else{//单个控件的情况
		        		//单选框、复选框的处理(因为单选框和复选框是input标签，在取数据字典的时候，替换了input标签，也重新定义了id的值，所以要特殊判断)
	    			    if(isEmpty($("#"+key)) || $("#"+key).length==0){
	    				   $("input[name='"+key+"']").each(function(){
	    					   if($(this).attr("type")=="radio" || $(this).attr("type")=="checkbox"){
		    					   $(this).attr("disabled","disabled");
	    					   }
	    				   });
	    			    }else{
	    			    	$("#"+key).attr("disabled","disabled");//设置为不可编辑
	    			    }
		        	}
    		   }else if('T' == value.rule){
    			 	//先判断是否为div
		    		if(!isEmpty($("#"+key)) && !isEmpty($("#"+key)[0]) && $("#"+key)[0].tagName=="DIV"){
						$("#"+key).find(":input").not(':button,:submit,:reset').each(function(i){//循环处理div中的控件
						   //单选框、复选框的处理(因为单选框和复选框是input标签，在取数据字典的时候，替换了input标签，也重新定义了id的值，所以要特殊判断)
		    			   if(isEmpty($(this)) || $($(this)).length==0){
		    				   $("input[name='"+$(this).name+"']").each(function(){
		    					   if($(this).attr("type")=="radio" || $(this).attr("type")=="checkbox"){
		    						   $(this).removeAttr("disabled");
		    					   }
		    				   });
		    			   }else{
		    				   $(this).removeAttr("disabled");
		    			   }
						});
		        	}else{//单个控件的情况
		        		//单选框、复选框的处理(因为单选框和复选框是input标签，在取数据字典的时候，替换了input标签，也重新定义了id的值，所以要特殊判断)
	    			   	if(isEmpty($("#"+key)) || $("#"+key).length==0){
	    				   $("input[name='"+key+"']").each(function(){
	    					   if($(this).attr("type")=="radio" || $(this).attr("type")=="checkbox"){
		    					   $(this).removeAttr("disabled");
	    					   }
	    				   });
	    			    }else{
	    			    	$("#"+key).removeAttr("disabled");//设置为可编辑
	    			    }
		        	}
    		   }
    	   }
       }
    });
}

/**
 * 数据赋值
 * @param data
 * @param cdgi 列表中文取值数据字典的json对象
 */
function setFieldValue(data, cdgi){
	$.each(data, function(key,value){
		//1.对数据做处理
		if($("#"+key).length > 0){
			//目前只有三种情况：1.整个form表单塞值  2.列表塞值 3.单个控件塞值
			if($("#"+key).is("form")){
				 $("#"+key).setFormData(value);
				 return true;
			}else if($("#"+key).is("table")){
				 $("#"+key).initData($.jsonObjToStr(value),$("#"+key).attr('formId'),cdgi);
	   			 $('#'+$("#"+key).attr('formId')).tableSearchForm({tableID:key});
    			 return true;
			}else{
				$("#"+key).setValue(value);
			} 
		}
	});
}

/**
 * 消息提示
 * @param msg
 */
function setMessage(msg){
	$.each(msg, function(key, value){
		 $.alert('提示', value, key);
	});
}

/**
 * 执行脚本
 * @param script
 */
function handleScript(script){
	$.each(script, function(key,value){
		$('body').append($("<script type='text/javascript'>"+value+"</script>"));
	});
}

/**
 * 按钮单击事件绑定
 */
function bindButtonClickEvent(){
	//按钮处理
	$(":button[mode]").each(function(i){
		var defaultOptions = {
		    	title: "弹出对话框",
		    	message:"确认要操作吗？",
		    	buttonText:["确定","取消"],
		    	url:"/abc.mo",
		    	divId:"divId",
		    	width:950,
		    	height:600
	    };
	
		var btnName = $(this).attr("name");
		var btnId = $(this).attr("id");
	
		if(!isEmpty(btnName) && btnName == "backBtn" && !isEmpty(lastUrl)){
			$(this).bind("click",function(){
				location.href = lastUrl;
				return true;
			});
		}
	
		if(!isEmpty($(this).attr("mode"))){
			var mode = $(this).attr("mode");
			if(mode == 'form'){//按钮提交表单的处理
				if(!isEmpty($(this).closest('form').attr("id"))){
					var formButton = $(this);
					var action = $(this).closest('form').attr("action");
					var printFlag = $(this).attr("print");
					var _formId = $(this).closest('form').attr("id");
					$(this).bind("click",function(){
						try{
							disabledButton(formButton);////设置按钮不可编辑，并把按钮置成灰色背景
							dealHidField();//对有ref属性的隐藏域赋值处理
							if(!isEmpty(printFlag) && printFlag=='doc'){
								var data = '';
								if(!isEmpty($(this).closest('form'))){
									data = $(this).closest('form').getFormData();
								}
							    var requesturl = action;
							    var requestdata = '_type=field_event&_comp=main&data='+$.jsonObjToStr(data)+'&_event=onClick&_field='+btnId+'&Request-from=dhtmlx&fun_id='+fun_id  ; 
							    window.open(requesturl+'?'+requestdata);//打印模板
							    removeDisButton(formButton);//设置按钮可编辑，并移除按钮灰色背景
						  	}else{
						  		var validator = $(this).closest('form').attr("validate");
								if(!isEmpty(validator) && !$("#"+_formId).valid()){
									removeDisButton(formButton);//设置按钮可编辑，并移除按钮灰色背景
									return;
								}
						 		$("#"+_formId)._submit();
						 		removeDisButton(formButton);//设置按钮可编辑，并移除按钮灰色背景
						  	}
						
						}catch(e){
							if (window.console){
								console.error("JS 提交报错："+e.message);
							}
						}
						return false;
					});
				}
			}else if(mode == 'list'){//按钮操作列表的处理
				if(!isEmpty($(this).attr("action"))){
					var listButton = $(this);
					var printFlag = $(this).attr("print");
					var action = $(this).attr("action");
					var talbeId = $(this).attr("tableId");
					if(!isEmpty($(this).attr("confirmMsg")))defaultOptions.message=$(this).attr("confirmMsg");
					if(!isEmpty(talbeId)){
						$(this).bind("click",function(){
							try{
								var list = $('#'+talbeId).getSelected(); 
								var d = $.jsonStrToObj(list) ;
								if(d.length == 0 ){
									 $.alert('提示','请选择一笔数据！','remind');
									 return false;
								}
								if($(this).attr("confirmMsg")){//是否要弹确认框标识
									$.confirm("确定框",defaultOptions.message,'remind',function(result){
										  if(result == true){
											  var requesturl = $.basePath()+action;
											  var requestdata = 'data='+list+'&fun_id='+fun_id ; 
											  if(!isEmpty(printFlag) && printFlag=='doc'){
												  window.open(requesturl+'?'+requestdata);//打印模板
											  }else{
												  $.ajaxquery({"url":requesturl,"data":requestdata}); 
											  }
										  }
									});
								}else{
									 var requesturl = $.basePath()+action;
									 var requestdata = 'data='+list+'&fun_id='+fun_id ; 
									 if(!isEmpty(printFlag) && printFlag=='doc'){
										window.open(requesturl+'?'+requestdata);//打印模板
									 }else{
										$.ajaxquery({"url":requesturl,"data":requestdata});
									 }
								}
						    
							}catch(e){
								if (window.console){
									console.error("JS 提交报错："+e.message);
								}
							}
							return false;
						});
					}
				}
			}else if(mode == 'ajax'){//按钮处理ajax
				if(!isEmpty($(this).closest('form').attr("action"))){
					var ajaxButton = $(this);
					var action = $(this).closest('form').attr("action");
					$(this).bind("click",function(){
						try{
							disabledButton(ajaxButton);////设置按钮不可编辑，并把按钮置成灰色背景
							dealHidField();//对有ref属性的隐藏域赋值处理
							var data = '';
							if(!isEmpty($(this).closest('form'))){
								data = $(this).closest('form').getFormData();
							}
						    var requesturl = action;
							var requestdata = '_type=field_event&_comp=main&data='+$.jsonObjToStr(data)+'&_event=onClick&_field='+btnId+'&Request-from=dhtmlx&fun_id='+fun_id ; 
							$.ajaxquery({"url":requesturl,"data":requestdata,"success":function(){
								  removeDisButton(ajaxButton);//设置按钮可编辑，并移除按钮灰色背景
							}});
						}catch(e){
							if (window.console){
								console.error("JS 提交报错："+e.message);
							}
						}
						return false;
					});
				}
			}else if(mode == 'showwin'){//按钮弹出框
				if(!isEmpty($(this).closest('form').attr("action"))){
					var action = $(this).closest('form').attr("action");
					if(!isEmpty($(this).attr("winwidth")))defaultOptions.width=$(this).attr("winwidth");
					if(!isEmpty($(this).attr("winheight")))defaultOptions.height=$(this).attr("winheight");
					if(!isEmpty($(this).attr("wintitle")))defaultOptions.title=$(this).attr("wintitle");
					if(!isEmpty($(this).attr("winbutton")) && $(this).attr("winbutton").indexOf(",")>1){//自定义按钮名称
						defaultOptions.buttonText[0] = $(this).attr("winbutton").split(",")[0];
						defaultOptions.buttonText[1] = $(this).attr("winbutton").split(",")[1];
					}
					if(!isEmpty($(this).attr("action")))defaultOptions.url=$(this).attr("action");//
				
					$(this).bind("click",function(){
					
						//div弹出之前执行方法
						var method = $(this).attr("beforeOpen");
						if(!isEmpty(method)){
				    		var flag = eval(method+"('"+$(this).attr("id")+"')");
				    		if(flag == false ){
				    			return false;// 返回结果为失败
				    		}
				    	}
					
						var params = "";
						//弹框前处理url及参数
						if(!isEmpty($(this).attr("action"))){
							var url = $(this).attr("action");
							if(url.indexOf("?") > 1){//若需要传参数
								var requesturl = url.substr(0,url.indexOf("?"));
								var temp = url.substr(url.indexOf("?")+1);//截取参数
								var array = temp.split("&");//可以传多个参数
								for(i in array){
									var value = $("#"+array[i]).getValue();
									if(!isEmpty(value)){//若控件的值不为空则传到后台
										params = params +"&"+array[i]+"="+value;
									}
								}
								if(!isEmpty(params)){
									defaultOptions.url = requesturl + "?1=1" + params;
								}else{
									defaultOptions.url = requesturl;
								}
							}
						}
					
						try{
							$.showDialog({
						    	title: defaultOptions.title,
						    	url: $.basePath()+defaultOptions.url,
						    	width:defaultOptions.width,
						    	height:defaultOptions.height,
						    	buttonText: defaultOptions.buttonText,
						    	nonCloseCallback: function (data){
						    		var button1 = null;
					 	    		$('span[class=ui-button-text]').each(function(){
										if($(this).text()==defaultOptions.buttonText[0]){
											button1 = $(this).parent();
										}
									});
					 	    		if(!isEmpty(button1)){
						 	    		disabledButton(button1);////设置按钮不可编辑，并把按钮置成灰色背景
					 	    		}
						    		if(data.ret && data._list){
						    			var dataStr = "["+data._list+"]" ;	
					 				    var requesturl = action;
					 					var requestdata = '_type=field_event&_comp=main&data='+dataStr+'&_event=onClick&_field='+btnId+'&Request-from=dhtmlx&action='+action ;
					 					if(!isEmpty(params)){
					 						requestdata = requestdata + params;
					 					}
					 					$.ajaxquery({"url":requesturl,"data":requestdata,"success":function(){
											  removeDisButton(button1);//设置按钮可编辑，并移除按钮灰色背景
										}});
						    		}else{
						    			$.alert('提示', "请选择一笔数据！", 'remind');
						    			removeDisButton(button1);//设置按钮可编辑，并移除按钮灰色背景
						    		}
								}
							})
						}catch(e){
							if (window.console){
								console.error("JS 提交报错："+e.message);
							}
						}
						return false;
					});
				}
			}else if(mode == 'showdiv'){//按钮弹出div
				if(!isEmpty($(this).attr("divId"))){
					var divId = $(this).attr("divId");
					if(!isEmpty($("#"+divId).attr("action"))){
						var action = $("#"+divId).attr("action");
						defaultOptions.divId=divId;
						defaultOptions.width = "450";
						defaultOptions.height = "300";
						if(!isEmpty($("#"+divId).attr("divwidth")))defaultOptions.width=$("#"+divId).attr("divwidth");
						if(!isEmpty($("#"+divId).attr("divheight")))defaultOptions.height=$("#"+divId).attr("divheight");
						if(!isEmpty($("#"+divId).attr("divtitle")))defaultOptions.title=$("#"+divId).attr("divtitle");
						if(!isEmpty($("#"+divId).attr("divbutton")) && $("#"+divId).attr("divbutton").indexOf(",")>1){//自定义按钮名称
							defaultOptions.buttonText[0] = $("#"+divId).attr("divbutton").split(",")[0];
							defaultOptions.buttonText[1] = $("#"+divId).attr("divbutton").split(",")[1];
						}
						$(this).bind("click",function(){
						
							$("#"+divId).resetDiv();//弹出前，清空div的内容
							//div弹出之前执行方法
							var method = $("#"+defaultOptions.divId).attr("beforeOpen");
							if(!isEmpty(method)){
					    		var flag = eval(method+'('+$(this).attr("id")+')');
					    		if(flag == false ){
					    			return false;// 返回结果为失败
					    		}
					    	}
						
							try{
						 		$.dialogDivShow({
						 			title: defaultOptions.title,
						 	    	dialogId: defaultOptions.divId,
						 	    	width: defaultOptions.width,
						 	    	height: defaultOptions.height,
						 	    	buttonText: defaultOptions.buttonText,
						 	    	callback: function(data){
						 	    		var button1 = null;
						 	    		$('span[class=ui-button-text]').each(function(){
											if($(this).text()==defaultOptions.buttonText[0]){
												button1 = $(this).parent();
											}
										});
						 	    		//表单校验
										var divForm = $("#"+defaultOptions.divId).find("form");
										var validator = divForm.attr("validate");
										if(!isEmpty(validator) && !$("#"+divForm.attr("id")).valid()){
											return;
										}
						 	    		if(data){
							    			var dataStr = $.jsonObjToStr(data);	
						 				    var requesturl = $.basePath()+action;
						 					var requestdata = 'data='+dataStr+'&divId='+defaultOptions.divId; 
						 					$.ajaxquery({"url":requesturl,"data":requestdata});
							    		}
						 	    	}
						 	    })
							}catch(e){
								if (window.console){
									console.error("JS 提交报错："+e.message);
								}
							}
							return false;
						});
					}
					
				}
			}
		}
	});
}

