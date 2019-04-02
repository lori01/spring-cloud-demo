/**
 * ------------------------------------------------------------------
 * 用途：显示弹框相关
 *	@author Alon Cai
 * 使用前提：引入 jq
 * 功能说明：
 * 1. 显示滚动条：$.showProcessBar([标题],[信息提示]);
 * 2. 关闭滚动条：$.closeProcessBar();
 * 3. 显示提示框：$.alert([标题],[内容],[图标：可选，取值：success：成功，failure：失败，warning：警告，doubt：疑问，remind：提醒],[回调方法，可选，只有一个布尔参数])
 * 4. 显示确认框：$.confirm([标题],[内容],[图标：可选，取值：success：成功，failure：失败，warning：警告，doubt：疑问，remind：提醒],[回调方法，可选，参数：对象，布尔参数])
 * 5. 显示弹出框：$.showDialog({title: "弹出对话框",data: [json POST参数],url:[iframe的URL],width:400,height:300,callback: function(data) {}}),回调将把所有iframe中的所有input值返回
 * 6. 显示dialog弹出框：$.showDialog({title: "弹出对话框",_dialogId: div ID),将当期jsp界面的div以dialog的形式展示
 * ------------------------------------------------------------------
 */

var process_dialog;
var process_dialog_AJAX;
/**
 * 显示进度条
 * @param _title 标题
 * @param _message 信息内容
 */
$.showProcessBar = function(_title,_message,type){
	if(type == undefined || type == null){
		type = '';
	}
	if($('#__boxDiv'+type).length==0){
		var _boxDiv=$("<div id='_boxDiv"+type+"' title='"+_title+"' style='display:none;'><div class='textcenter mg10'>"
				+_message+"</div><div class='indicator_loading'></div></div>");
		$(document.body).append(_boxDiv);
	}else{
		var _boxDiv=$('#_boxDiv'+type);
		_boxDiv.find(".textcenter").html(_message);
		_boxDiv.attr("title",_title);
	}
	$(document.head).append($('<style>.ui-dialog-titlebar-close {display: none;}</style>'));
	dialog = $('#_boxDiv'+type).dialog({
		autoOpen: false,
		width: 310,
		height:150,
		modal: true,
		buttons: [
			
		]
	});
	dialog.dialog( "open" );
	if(type == undefined || type == null || type == ''){
		process_dialog=dialog;
	}else{
		process_dialog_AJAX = dialog;
	}
	return dialog;
}
/**
 * 关闭进度条
 * @param _title 标题
 * @param _message 信息内容
 */
$.closeProcessBar = function(dialog,type){
	if(type == undefined || type == null){
		type = '';
	}
	if(type == undefined || type == null || type == ''){
		if(process_dialog!=null){
			process_dialog.dialog( "close" );
		}
	}else{
		if(process_dialog_AJAX!=null){
			process_dialog_AJAX.dialog( "close" );
		}
	}
	
	var dialog2=arguments[0] ? arguments[0] :null;
	if(dialog2!=null){
		dialog2.dialog( "close" );
	}
}
/**
 * 显示对话框
 * @param _title 必须，标题
 * @param _message 必须，信息内容
 * @param _icon_type 可选，类型（取值：success：成功，failure：失败，warning：警告，doubt：疑问，remind：提醒）
 * @param _callback 可选，回调函数，只接受一个参数，用于接受按钮事件点击后的值回传
 */
$.alert = function(_title,_content){
	var iconDiv="";
	var _icon_type=arguments[2] ? arguments[2] : "";
	var _callback=arguments[3] ? arguments[3] : null;
	if(_icon_type=='success'|| _icon_type=='failure' || _icon_type=='warning' || _icon_type=='doubt'||_icon_type=='remind'){
		iconDiv="<div class='icon_operation_"+_icon_type+"'></div>";
	}
	if($('#_boxDiv2').length>=0){
		$('#_boxDiv2').remove();
	}
	var _boxDiv=$("<div id='_boxDiv2' title='"+_title+"' style='display:none;'><div class='Promptcontent'>" +iconDiv+
			"<div class='Prompttext'>"+_content+"</div></div></div>");
	$(document.body).append(_boxDiv);
	var dialog = $('#_boxDiv2').dialog({
		autoOpen: false,
		width: 400,
		modal: true,
		buttons: [
			{
				text: "确定",
				click: function() {
					$( this ).dialog( "close" );
					if(_callback!=null){
						_callback.call(this,true);
					}
				}
			}
		]
	});
	dialog.dialog( "open" );
}
/**
 * 显示确认框
 * @param _title 必须，标题
 * @param _message 必须，信息内容
 * @param _icon_type 可选，类型（取值：success：成功，failure：失败，warning：警告，doubt：疑问，remind：提醒）
 * @param _callback 可选，回调函数，只接受一个参数，用于接受按钮事件点击后的值回传
 */
$.confirm = function(_title,_content){
	var iconDiv="";
	var _icon_type=arguments[2] ? arguments[2] : "doubt";
	var _callback=arguments[3] ? arguments[3] : null;
	if(_icon_type=='success'||_icon_type=='failure'||_icon_type=='warning'||_icon_type=='doubt'||_icon_type=='remind'){
		iconDiv="<div class='icon_operation_"+_icon_type+"'></div>";
	}
	if($('#_boxDiv2').length>=0){
		$('#_boxDiv2').remove();
	}
	var _boxDiv=$("<div id='_boxDiv2' title='"+_title+"' style='display:none;'><div class='Promptcontent'>" +iconDiv+
			"<div class='Prompttext'>"+_content+"</div></div></div>");
	$(document.body).append(_boxDiv);
	var dialog = $('#_boxDiv2').dialog({
		autoOpen: false,
		width: 400,
		modal: true,
		buttons: [
			{
				text: "确定",
				click: function() {
					$( this ).dialog( "close" );
					if(_callback!=null){
						_callback.call(this,true);
					}
				}
			},
			{
				text: "取消",
				click: function() {
					$( this ).dialog( "close" );
					if(_callback!=null){
						_callback.call(this,false);
					}
				}
			}
		]
	});
	dialog.dialog( "open" );
}

/**
 * 显示对话框
 * 重要说明：如需要将 iframe 中的值传回父窗口，需要在 iframe 的页面内增加 input，传回的参数用{name:value}JSON传回
 * @param _title 必须，标题
 * @param _message 必须，信息内容
 * @param _width 可选，类型（默认200）
 * @param _heignt 可选，(默认500)
 * @param _callback 可选，(回调函数,参数是一个 json)
 */
$.showDialog = function(options){
	var defaultOptions = {
    	title: "弹出对话框",
    	data: null,
    	url:null,
    	width:400,
    	height:300,
    	method:"get",
    	buttonText:["确定","取消"],
    	callback: function(data) {},
    	divId: '_boxIframeDiv',
    	nonCloseCallback: null,
    	checkList : false,
    	listName : null
    };
	defaultOptions = $.extend(defaultOptions,options||{});
	var inputs="";
	if(defaultOptions.data!=null){
		for(var name in defaultOptions.data){
			inputs=inputs+"<input name=\""+name+"\" value=\""+defaultOptions.data[name]+"\" type='hidden' />";
		}
	}
	if($('#'+defaultOptions.divId).length!=0){
		$('#'+defaultOptions.divId).remove();
	}
	var iframeId=defaultOptions.divId+"_iframe";
	var formId=defaultOptions.divId+"_form";
	/*if(defaultOptions.url.indexOf("layout=no_menu")<0){
		defaultOptions.url=defaultOptions.url+(defaultOptions.url.indexOf("?")>0?"&":"?")+"layout=no_menu";
	}*/
	var _boxDiv=$("<div id='"+defaultOptions.divId+"' title='"+defaultOptions.title+"' style='display:none;'><div><iframe frameborder='0' id='"+iframeId+"' name='"+iframeId+"' width=\""
			+(defaultOptions.width-40)+"\" height=\""+(defaultOptions.height-120)+"\" src=\""+defaultOptions.url+"\" onload=\"setIframeHeight('"+iframeId+"')\"></iframe></div><form id=\""+formId
			+"\" action=\""+defaultOptions.url+"\" target='"+iframeId+"'>"+inputs+"</form></div>");
	$(document.body).append(_boxDiv);
	if(defaultOptions.method=='post'){
		$("#"+iframeId).attr("src","");
		$(_boxDiv.find("form")).submit();
	}else{
		$("#"+iframeId).attr("src",defaultOptions.url);
	}
	$($("#"+iframeId).contents().find("body")).attr("style","overflow-y:auto;");
	var dialog = $('#'+defaultOptions.divId).dialog({
		autoOpen: false,
		width: defaultOptions.width,
		height: defaultOptions.height,
		modal: true,
		buttons: [
			{
				text: defaultOptions.buttonText[0],
				click: function() {
					var tmp={"ret":true};
					if($("#"+iframeId)[0].contentWindow.validForm && !$("#"+iframeId)[0].contentWindow.validForm()){
						//alert('验证通过不了');
						return;
					}
					$("#"+iframeId).contents().find(":input").not(":button,:submit,:reset").each(function(){
						if($(this).attr("name")!=undefined &&$(this).attr("name")!=''){
							if(($(this).attr("type")=="checkbox"||$(this).attr("type")=="radio") &&$(this).prop("checked")==false){
								if(tmp[$(this).attr("name")]==null){
									tmp[ $(this).attr("name")]=[];
								}
								return;
							}
							if(tmp[$(this).attr("name")]==null){
								if($(this).attr("type")=="checkbox"||$(this).attr("type")=="radio"){
									var x=new Array();
									x.push($(this).val());
									tmp[$(this).attr("name")]=x;
								}else{
									tmp[$(this).attr("name")]=$(this).val();
								}
							}else{
								//转成数组
								if($(this).attr("type")=="checkbox"||$(this).attr("type")=="radio"){
									tmp[$(this).attr("name")].push($(this).val());
								}
							}
						}
					});
					if(defaultOptions.checkList){
						if(defaultOptions.listName != null && defaultOptions.listName != undefined){
							var check_name="tmp."+defaultOptions.listName;
							if(eval(check_name) == undefined || (eval(check_name) != undefined && eval(check_name+'.length') == 0)){
								$.alert('提示', '请选择一条记录!', 'failure');
								return;
							}
						}else{
							if(tmp.list_item != undefined && tmp.list_item.length == 0){
								$.alert('提示', '请选择一条记录!', 'failure');
								return;
							}
						}
						
					}
					if(defaultOptions.nonCloseCallback!=null){
						defaultOptions.nonCloseCallback.call(this,tmp);
					}else{
						if(defaultOptions.callback!=null){
							defaultOptions.callback.call(this,tmp);
						}
						$( this ).dialog( "close" );
					}
				}
			},
			{
				text: defaultOptions.buttonText[1],
				click: function() {
					$( this ).dialog( "close" );
					if(defaultOptions.callback!=null){
						defaultOptions.callback.call(this,{ret:false});
					}
				}
			}
		]
	});
	dialog.dialog( "open" );
	$.showProcessBar("请稍等","数据加载中，请稍等...");  //显示进度条, 界面重新加载会自动关闭
}

/**
 * 显示窗口
 * 重要说明：如需要将 iframe 中的值传回父窗口，需要在 iframe 的页面内增加 input，传回的参数用{name:value}JSON传回
 * @param _title 必须，标题
 * @param _message 必须，信息内容
 * @param _width 可选，类型（默认200）
 * @param _heignt 可选，(默认500)
 * @param _callback 可选，(回调函数,参数是一个 json)
 * 说明，在弹出的页面上需要关闭该弹出窗口时，需要调用：window.parent.colsePopWin(_data); 其中 _data 为易开信息的json格式，不需要显示信息则放空
 */
$.showWin = function(options){
	var defaultOptions = {
    	title: "弹出对话框",
    	data: null,
    	url:null,
    	width:400,
    	height:300,
    	method:"get",
    	callback: function(data) {},
    	divId: '_boxIframeDiv2'
    };
	defaultOptions = $.extend(defaultOptions,options||{});
	var inputs="";
	if(defaultOptions.data!=null){
		for(var name in defaultOptions.data){
			inputs=inputs+"<input name=\""+name+"\" value=\""+defaultOptions.data[name]+"\" type='hidden' />";
		}
	}
	if($('#'+defaultOptions.divId).length!=0){
		$('#'+defaultOptions.divId).remove();
	}
	/*if(defaultOptions.url.indexOf("layout=no_menu")<0){
		defaultOptions.url=defaultOptions.url+(defaultOptions.url.indexOf("?")>0?"&":"?")+"layout=no_menu";
	}*/
	var iframeId=defaultOptions.divId+"_iframe";
	var formId=defaultOptions.divId+"_form";
	var _boxDiv=$("<div id='"+defaultOptions.divId+"' title='"+defaultOptions.title+"' style='display:none;'><button id='btn_"+defaultOptions.divId
			+"' style='display:none;'></button><div><iframe frameborder='0' onload=\"setIframeHeight('"+iframeId+"')\" id='"+iframeId+"' name='"+iframeId+"' width=\""
			+(defaultOptions.width-40)+"\" height=\""+(defaultOptions.height-100)+"\" ></iframe></div><form id=\""+formId+"\" action=\""+defaultOptions.url
			+"\" target='"+iframeId+"' method=\"post\">"+inputs+"<script type='text/javascript'>function colsePopWin(_data){if(_data!=null&&_data!=undefined){" 
			+"$('#callback_data').val(_data);}$('#btn_"+defaultOptions.divId+"').trigger('click');}</script></form><input id='callback_data' type='hidden'/></div>");
	
	$(document.body).append(_boxDiv);
	$($("#"+iframeId).contents().find("body")).attr("style","overflow-y:auto;");
	var dialog = $('#'+defaultOptions.divId).dialog({
		autoOpen: false,
		width: defaultOptions.width,
		height: defaultOptions.height,
		modal: true
	});
	if(defaultOptions.method=='post'){
		$("#"+iframeId).attr("src","");
		$(_boxDiv.find("form")).submit();
	}else{
		$("#"+iframeId).attr("src",defaultOptions.url);
	}
	$('#btn_'+defaultOptions.divId).bind("click",function(){
		$('#'+defaultOptions.divId).dialog('close');
		var tmp={ret:false};
		if($('#callback_data').val()!=null&&$('#callback_data').val()!=undefined && $('#callback_data').val()!=""){
			tmp['ret']=true;
			tmp['data']=$('#callback_data').val();
		}
		if(defaultOptions.callback!=null){
			defaultOptions.callback.call(this,tmp);
		}
	});
	dialog.dialog( "open" );
	$.showProcessBar("请稍等","数据加载中，请稍等...");  //显示进度条, 界面重新加载会自动关闭
}
/**
 * 显示dialog选择框
 * @param title 必须，标题
 * @param dialogId 必须，要弹出的dailogid
 * @param buttonText 可选，确认按钮的名称
 * @param callback 可选，确认按钮点击后的回调函数
 */
$.dialogDivShow = function(options){
	var defaultOptions = {
    	title: "弹出对话框",
    	dialogId:"_boxIframeDiv",
    	width:400,
    	height:300,
    	buttonText:["确定","取消"],
    	callback: function() {}
    };
	defaultOptions = $.extend(defaultOptions,options||{});
	var dialog2 = $('#'+defaultOptions.dialogId).dialog({
		//autoOpen: false,
		title: defaultOptions.title,
		width: defaultOptions.width,
		height: defaultOptions.height,
		modal: true,
		buttons: [
			{
				text: defaultOptions.buttonText[0],
				click: function() {
					if(defaultOptions.callback!=null){
						defaultOptions.callback.call(this,true);
					}else{
						$( this ).dialog( "close" );
					}	
				}
			},
			{
				text: defaultOptions.buttonText[1],
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
	dialog2.dialog( "open" );
	$(".ui-dialog-buttonpane").show();
	return dialog2;
}


/**  iframe 根据table查询信息 自适应高度，支持ajax查询后，高度变化 start */
function setIframeHeight(iframeId) {
	$.closeProcessBar();
	var iframe= document.getElementById(iframeId);

	iframeLoaded(iframe);
};

//公共方法：设置iframe的高度以保证全部显示数据
var iframeLoaded = function (iframe) {
  if (iframe.src.length > 0) {
      if (!iframe.readyState || iframe.readyState == "complete") {
          var bHeight =  iframe.contentWindow.document.body.scrollHeight;
          var dHeight =  iframe.contentWindow.document.documentElement.scrollHeight;
          var height = Math.max(bHeight, dHeight);
          iframe.height = height;
      }
  }
}
//公共方法：根据传入的长度，设置iframe的高度以保证全部显示数据
var iframeLoadedWithLength = function (iframe,length) {
  if (iframe.src.length > 0) {
      if (!iframe.readyState || iframe.readyState == "complete") {
          var bHeight =  iframe.contentWindow.document.body.scrollHeight;
          var dHeight =  iframe.contentWindow.document.documentElement.scrollHeight;
          var height = Math.max(bHeight, dHeight);
          iframe.height = Math.max(height, length);
      }
  }
}

// 公共方法：重新设置弹出框的自适应高度
function reLoadedIframeHeigth(length){
	if(parent !=null && parent != undefined && window.name!=''){
		// 弹出框的自适应高度修改，根据当前弹出框内容的改变，进行高度变更 ，目前适应于iframe的弹出框，其他场景如果异常需要修改。
		var parframe = parent.document.getElementById(window.name);
	    if(parframe !=null && parframe != undefined){
	    	// 分页时重新设置 iframe 高度 ； 修改后：iframe.name = iframe.id
	    	// 调用jquery.box.js，jsp界面需要引入
	    	if(length != null && length != undefined){
	    		iframeLoadedWithLength(parframe,length);
	    	}else{
	    		iframeLoaded(parframe);
	    	}
	    	
	    }
	}
}

/**  iframe 根据table查询信息 自适应高度，支持ajax查询后，高度变化 end */
