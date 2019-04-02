/**
 * ------------------------------------------------------------------
 * 用途：文档操作js
 *	@author kangyingcai
 * 使用前提：引入 jq 及 jquery.box.js
 * 功能说明：
 * 1. 显示滚动条：$.showProcessBar([标题],[信息提示]);
 * 2. 关闭滚动条：$.closeProcessBar();
 * 3. 显示提示框：$.alert([标题],[内容],[图标：可选，取值：success：成功，failure：失败，warning：警告，doubt：疑问，remind：提醒],[回调方法，可选，只有一个布尔参数])
 * 4. 显示确认框：$.confirm([标题],[内容],[图标：可选，取值：success：成功，failure：失败，warning：警告，doubt：疑问，remind：提醒],[回调方法，可选，参数：对象，布尔参数])
 * 5. 显示弹出框：$.showDialog({title: "弹出对话框",data: [json POST参数],url:[iframe的URL],width:400,height:300,callback: function(data) {}}),回调将把所有iframe中的所有input值返回
 * 6. 显示dialog弹出框：$.showDialog({title: "弹出对话框",_dialogId: div ID),将当期jsp界面的div以dialog的形式展示
 * ------------------------------------------------------------------
 */

var _boxDiv,dialog;
/**
 * 显示文档上传dialog
 * @param mdlid 必须，模块标识
 * @param tblblid 必须，表单标识
 */
$.showFileDialog = function(options){
	var defaultOptions = {
	    	mdlid:null,
	    	tblblid:null,
	    	loginName:null,
	    	width:400,
	    	height:300,
	    	callback: function(json){}
	    };
	defaultOptions = $.extend(defaultOptions,options||{});
	
	$('#_fileDiv input[name="mdlid"]').val(defaultOptions.mdlid);
	$('#_fileDiv input[name="tblblid"]').val(defaultOptions.tblblid);
	$('#_fileDiv input[name="loginName"]').val(defaultOptions.loginName);
	$.dialogDivShow({
		title: "文件上传",
    	dialogId: "_fileDiv",
    	width: defaultOptions.width,
    	height: defaultOptions.height,
    	callback: function(){
    		uploadFile("_fileDiv",defaultOptions.callback);
    	}
    })
//    
//	dialog = $('#_fileDiv').dialog({
//		autoOpen: false,
//		title: '文档上传',
//		width: defaultOptions.width,
//		height: defaultOptions.height,
//		modal: true,
//		buttons: [
//			{
//				text: '上传',
//				click: function() {
//					uploadFile("_fileDiv",defaultOptions.callback);
//				}
//			},
//			{
//				text: '取消',
//				click: function() {
//					$( this ).dialog( "close" );
//				}
//			}
//		]
//	});
//	dialog.dialog( "open" );
	
}

function uploadFile(divId,callback){
	var uploadfile = $("#uploadfile").val();
	if(uploadfile == null || uploadfile == undefined || uploadfile.length<=0 ){
		$.alert('提示','请选择上传文件','remind');
		return ;
	}
	var mdlid = $("#mdlid").val();
	var tblblid = $("#tblblid").val();
	var docTp = $("#docTp").val();
	var loginName = $("#loginName").val();
	if(mdlid == null || mdlid == undefined || mdlid.length <=0
			|| tblblid == null || tblblid == undefined || tblblid.length <=0 ){
		$.alert('提示','模块信息需要传入','remind');
		return ;
	}
	$.showProcessBar("请稍等","数据加载中，请稍等...");  //显示进度条
	$.ajaxFileUpload({
		url : $.basePath()+'/fileUpload', //用于文件上传的服务器端请求地址
		fileElementId : 'uploadfile', //文件上传域的ID
		data:{"mdlid":mdlid,"tblblid":tblblid,"docTp":docTp,"loginName":loginName},
		secureuri:false,
		dataType : 'json', //返回值类型 一般设置为json
		success : function(data, status) {
			$.closeProcessBar();//关闭进度条
			$("#"+divId).dialog( "close" );
			$.alert('提示',data.fileName+'  上传成功!','success');
			if(callback!=null){
				callback.call(this,true);
			}
		},
		error : function(data, status, e) {
			$.closeProcessBar();//关闭进度条
			$.alert('提示',data+' '+e,'failure');
		}
	});
}
// 文件下载  调用 servlet
function fileDown(fileNum){
	window.open($.basePath()+"/FileDown?fileNum="+fileNum,"_blank");
}
//文件删除  调用控制层代码
function fileDelete(options){
	var defaultOptions = {
			fileNum: null,
	    	callback: function() {}
	    };
	defaultOptions = $.extend(defaultOptions,options||{});
	var requesturl =$.basePath()+'/fileDown.mo?';
	var requestdata ='_type=field_event&_comp=main&_event=onClick&_field=doDeleteFileInfoByNum&Request-from=dhtmlx&num='+defaultOptions.fileNum ;
	$.ajaxquery({"url":requesturl,"data":requestdata,"isLoading":true,"success":function(msgjsonobj){
		if(msgjsonobj._tipMsg!=null && msgjsonobj._tipMsg!=undefined && msgjsonobj._tipMsg.msg !=null 
				&& msgjsonobj._tipMsg.msg !=undefined &&  msgjsonobj._tipMsg.msg.indexOf('成功') <=0 ){
	    	  $.alert('提示',msgjsonobj._tipMsg.msg,'doubt');
	    	  return ;
	      }
	      $.alert('提示',msgjsonobj._tipMsg.msg,'success');
	      if(defaultOptions.callback!=null){
	    	  defaultOptions.callback.call(this,true);
		  }
	}});
}