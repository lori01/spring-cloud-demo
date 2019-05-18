var animation_time = 500;
var showEffect = "blind";
var hideEffect = "explode";
/*
 * 特效
 * 百叶窗特效（Blind Effect）
 * 反弹特效（Bounce Effect）
 * 剪辑特效（Clip Effect）
 * 降落特效（Drop Effect）
 * 爆炸特效（Explode Effect）
 * 折叠特效（Fold Effect）
 * 突出特效（Highlight Effect）
 * 膨胀特效（Puff Effect）
 * 跳动特效（Pulsate Effect）
 * 缩放特效（Scale Effect）
 * 震动特效（Shake Effect）
 * 尺寸特效（Size Effect）
 * 滑动特效（Slide Effect）
 */


/*dialog相关属性
 * 
 * autoOpen   初始化之后，是否立即显示对话框，默认为 true
 * modal        是否模式对话框，默认为 false
 * closeOnEscape   当用户按 Esc 键之后，是否应该关闭对话框，默认为 true
 * draggable  是否允许拖动，默认为 true
 * resizable    是否可以调整对话框的大小，默认为 true
 * title           对话框的标题，可以是 html 串，例如一个超级链接。
 * position     用来设置对话框的位置，有三种设置方法
 * position:  'center', 'left', 'right', 'top', 'bottom'
 * position: [100, 100]
 * position: ["left", "bottom"]
 * 
 * width     宽度, 默认 300
 * height    高度，默认 'auto'
 * minWidth     最小宽度，默认 150
 * minHeight    最小高度，默认 150
 * maxWidth   最大宽度
 * maxHeight   最大高度
 * 
 * hide       当对话框关闭时的效果，默认为 null, 例如， hide: 'slide'
 * show     当对话框打开时的效果。默认为 null
 * 
 * 堆叠
 * stack     对话框是否叠在其他对话框之上。默认为 true
 * zIndex   对话框的 z-index 值，一个整数，越大越在上面。默认 1000
 * 按钮
 * buttons   一个对象，属性名将会被作为按钮的提示文字，属性值为一个函数，即按钮的处理函数
 * buttons: {
 *       "Ok": function() { } ,
 *       "Cancel": function() {}
 *    }
 * */


function hideAll() {
	/*hidemenu();
	canclePwd();
	cancleUser();
	cancleme();
	cancle();
	cancle_comment();*/
	
		
	
}
/* 菜单 */
function showmenu() {
	/*hideAll();
	var litop = $('#show_user_menu_drop').offset().top;
	var lileft = $('#show_user_menu_drop').offset().left;
	var liright = $('#show_user_menu_drop').offset().right;
	var width = $('#show_user_menu_drop').width();
	//$("#user_menu_drop").css("left", lileft+width);
	//$("#user_menu_drop").css("top", litop);
	//$("#user_menu_drop").css("width", "100%");
	$("#user_menu_drop").fadeIn();
	//$("#user_menu_drop").delay(500).slideDown();
*/}
function hidemenu() {
	$(".menu_drop").fadeOut();
	//$("#user_menu_drop").slideUp();
}


/* 更新密码 */
function showPwd() {
	hideAll();
	/*$("#upd_pwd_div").fadeIn();
	$("#upd_pwd_div_box").delay(500).slideDown();*/
	$("#upd_pwd_div").dialog({
		width : 500,
		height : 230,
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
	
}
function canclePwd() {
	//$("#upd_pwd_div").fadeOut();
	$("#upd_pwd_div").dialog("close");
}
function updPwd(uid) {
	if ($("#old_pwd").val() == null || $("#old_pwd").val() == "") {
		alert("请输入原密码!");
		//$.messager.alert("提示信息","请输入原密码!","info");
		return;
	}
	if ($("#new_pwd").val() == null || $("#new_pwd").val() == "") {
		alert("请输入新密码!");
		return;
	}
	if($("#new_pwd").val().length <6 || $("#new_pwd").val().length > 20){
		alert("密码长度必须在6-20之间!");
		return;
	}
	if ($("#re_new_psd").val() == null || $("#re_new_psd").val() == "") {
		alert("请确认新密码!");
		return;
	}
	if ($("#re_new_psd").val() != $("#new_pwd").val()) {
		alert("新密码不匹配!");
		return;
	}
	$.ajax({
		"url" : "/user/updatePwd",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			uid : uid,
			oldPwd : $("#old_pwd").val(),
			newPwd : $("#new_pwd").val()
		},
		"success" : function(msgjsonobj) {
			if (msgjsonobj.status == 100) {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 修改成功!");
				//canclePwd();
				$.showAlertMessage("success","保存成功", msgjsonobj.status + "-" + msgjsonobj.desc + " 保存成功!" , function(){
					canclePwd();
				});
			} else {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				$.showAlertMessage("error","保存失败", msgjsonobj.status + "-" + msgjsonobj.desc ,function(){
					
				});
			}

		},
		"error" : function(msgjsonobj) {
			//alert("引发了莫名的错误?");
			$.showAlertMessage("error","保存失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
				
			});
		}
	});
}

/* 新增用户 */
function showAddUser() {
	hideAll();
	/*$("#add_user_div").fadeIn();
	$("#add_user_div_box").delay(500).slideDown();*/
	$("#add_user_div").dialog({
		width : 500,
		height : 310,
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
}
function cancleUser() {
	//$("#add_user_div").fadeOut();
	$("#add_user_div").dialog("close");
}
function addUser() {
	if ($("#new_loginName").val() == null || $("#new_loginName").val() == "") {
		alert("请输入登录号!");
		return;
	}
	if ($("#new_realname").val() == null || $("#new_realname").val() == "") {
		alert("请输入姓名!");
		return;
	}
	if ($("#new_password").val() == null || $("#new_password").val() == "") {
		alert("请输入密码!");
		return;
	}
	if($("#new_password").val().length <6 || $("#new_password").val().length > 20){
		alert("密码长度必须在6-20之间!");
		return;
	}
	if ($("#re_new_password").val() == null
			|| $("#re_new_password").val() == "") {
		alert("请确认密码!");
		return;
	}
	if ($("#new_password").val() != $("#re_new_password").val()) {
		alert("密码不匹配!");
		return;
	}
	$.ajax({
		"url" : "/user/add",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			loginName : $("#new_loginName").val(),
			password : $("#new_password").val(),
			realname : $("#new_realname").val(),
			permission : $("#new_role_id").val()
		},
		"success" : function(msgjsonobj) {
			if (msgjsonobj.status == 100) {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 新增成功!");
				//cancleUser();
				$.showAlertMessage("success","保存成功", msgjsonobj.status + "-" + msgjsonobj.desc + " 保存成功!" , function(){
					cancleUser();
				});
			} else {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				$.showAlertMessage("error","保存失败", msgjsonobj.status + "-" + msgjsonobj.desc ,function(){
					
				});
			}

		},
		"error" : function(msgjsonobj) {
			//alert("引发了莫名的错误?");
			$.showAlertMessage("error","保存失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
				
			});
		}
	});
}

/* 个人信息 */
function showme() {
	hideAll();
	/*$("#me_div").fadeIn();
	$("#me_div_box").delay(500).slideDown();*/
	$("#me_div").dialog({
		width : 800,
		height : 500,
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
}
function cancleme() {
	//$("#me_div").fadeOut();
	$("#me_div").dialog("close");
}
function saveuser(uid) {
	if ($("#realname_s").val() == null || $("#realname_s").val() == "") {
		alert("请输入用户名称!");
		return;
	}
	if ($("#img_s").val() == null || $("#img_s").val() == "") {
		alert("请输入头像!");
		return;
	}
	$.ajax({
		"url" : "/user/update",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			id : uid,
			realname : $("#realname_s").val(),
			email : $("#email_s").val(),
			phone : $("#phone_s").val(),
			img : $("#img_s").val(),
			sexCd : $("input[name='sexCd_s']:checked").val()
		},
		"success" : function(msgjsonobj) {
			if (msgjsonobj.status == 100) {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 修改成功!");
				//cancleme();
				$.showAlertMessage("success","保存成功", msgjsonobj.status + "-" + msgjsonobj.desc + " 保存成功!" , function(){
					cancleme();
				});
			} else {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				$.showAlertMessage("error","保存失败", msgjsonobj.status + "-" + msgjsonobj.desc ,function(){
					
				});
			}

		},
		"error" : function(msgjsonobj) {
			//alert("引发了莫名的错误?");
			$.showAlertMessage("error","保存失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
				
			});
		}
	});
}

/* 写文章 */ 
function show() {
	//hideAll();
	/*$("#add_div").fadeIn();
	$("#add_div_box").delay(500).slideDown();*/
	$("#add_div").dialog({
		width : 800,
		height : 680,
		autoOpen : true,
		show : {
			effect : showEffect,
			duration : animation_time
		},
		hide : {
			effect : hideEffect,
			duration : animation_time
		},
		/*buttons: {
		      "Ok": function() {
		    	  $(this).dialog("close");  
		      } ,
		      "Cancel": function() {
		    	  $(this).dialog("close");  
		      }
		   }*/
	});
}
function cancle() {
	$("#article_id_s").val("");
	$("#title_s").val("");
	$("#shortContext_s").val("");
	$("#context_s").val("");
	//$("#add_div").fadeOut();
	$("#add_div").dialog("close");  
}
function add() {
	if ($("#title_s").val() == null || $("#title_s").val() == "") {
		alert("请输入标题!");
		return;
	}
	if ($("#shortContext_s").val() == null || $("#shortContext_s").val() == "") {
		alert("请输入简介!");
		return;
	}
	if ($("#context_s").val() == null || $("#context_s").val() == "") {
		alert("请输入内容!");
		return;
	}
	var isSendMail = "0";
	if (document.getElementsByName("isSendMail")[0].checked) {
		isSendMail = "1";
	}
	$.ajax({
		"url" : "/article/add",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			id : $("#article_id_s").val(),
			title : $("#title_s").val(),
			shortContext : $("#shortContext_s").val(),
			context : $("#context_s").val(),
			isSendMail : isSendMail
		},
		"success" : function(msgjsonobj) {
			if (msgjsonobj.status == 100) {
				if($("#article_id_s").val() != null && $("#article_id_s").val() != ""){
					//alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 修改成功!");
					//location.href = window.location.href;
					$.showAlertMessage("success","保存成功", msgjsonobj.status + "-" + msgjsonobj.desc + " 修改成功!" , function(){
						location.href = window.location.href;
					});
				}
				else if ($("#add_content_replace_div").html() == undefined) {
					//alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 发布成功!");
					$.showAlertMessage("success","保存成功", msgjsonobj.status + "-" + msgjsonobj.desc + " 修改成功!" , function(){
						cancle();
					});
				} else {
					append(msgjsonobj.obj, msgjsonobj.desc);
					cancle();
				}
				//cancle();
				/*var check = confirm(msgjsonobj.status + "-" + msgjsonobj.desc);
				if (check) {

				}*/
			} else {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				$.showAlertMessage("error","保存失败",msgjsonobj.status + "-" + msgjsonobj.desc,function(){
					
				});
			}

		},
		"error" : function(msgjsonobj) {
			//alert("引发了莫名的错误?");
			$.showAlertMessage("error","保存失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
				
			});
		}
	});
}
function append(obj, date) {
	var html = $("#content_main_append").html();

	var newHtml = html.replace(/{title}/g, obj.title).replace(/{realname}/g,
			obj.createUser.realname).replace(/{id}/g, obj.id).replace(
			/{shortContext}/g, obj.shortContext).replace(/{img}/g,
			obj.createUser.img).replace(/{uid}/g, obj.createUser.id).replace(
			/{createTm}/g, date)
	//$("#content_main_apl").before(newHtml);
	$("#add_content_replace_div").after(newHtml);
}

/*
 * 文章/评论操作
 */
function delArticle(id){
	$.showConfirmMessage("确认删除","确认删除吗?",function(e){delArticleAction(id);});
}
function delArticleAction(id){
	var check = true;//confirm("确认删除吗?");
	if (check) {
		$.ajax({
			"url" : "/article/delete",
			"type" : "POST",
			"dataType" : "json",
			"data" : {
				id : id
			},
			"success" : function(msgjsonobj) {
				if (msgjsonobj.status == 100) {
					//alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 删除成功!");
					//location.href = window.location.href;
					$.showAlertMessage("success","删除成功",msgjsonobj.status + "-" + msgjsonobj.desc + " 删除成功!",function(){
						location.href = window.location.href;
					});
				} else {
					//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
					$.showAlertMessage("error","删除失败",msgjsonobj.status + "-" + msgjsonobj.desc,function(){
						
					});
				}

			},
			"error" : function(msgjsonobj) {
				//alert("引发了莫名的错误?");
				$.showAlertMessage("error","删除失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
					
				});
			}
		});
	}
}
function editArticle(id){
	show();
	$.ajax({
		"url" : "/article/findOne",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			id : id
		},
		"success" : function(msgjsonobj) {
			if (msgjsonobj.status == 100) {
				$("#article_id_s").val(id);
				$("#title_s").val(msgjsonobj.obj.title);
				$("#shortContext_s").val(msgjsonobj.obj.shortContext);
				$("#context_s").val(msgjsonobj.obj.context);
			} else {
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				$.showAlertMessage("error","查询失败",msgjsonobj.status + "-" + msgjsonobj.desc,function(){
					
				});
			}

		},
		"error" : function(msgjsonobj) {
			//alert("引发了莫名的错误?");
			$.showAlertMessage("error","查询失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
				
			});
		}
	});
}
function delComment(id){
	$.showConfirmMessage("确认删除","确认删除吗?",function(e){delCommentAction(id);});
}
function delCommentAction(id){
	var check = true;//confirm("确认删除吗?");
	if (check) {
		$.ajax({
			"url" : "/comment/delete",
			"type" : "POST",
			"dataType" : "json",
			"data" : {
				id : id
			},
			"success" : function(msgjsonobj) {
				if (msgjsonobj.status == 100) {
					//alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 删除成功!");
					//location.href = window.location.href;
					$.showAlertMessage("success","删除成功",msgjsonobj.status + "-" + msgjsonobj.desc + " 删除成功!",function(){
						location.href = window.location.href;
					});
				} else {
					//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
					$.showAlertMessage("error","删除失败",msgjsonobj.status + "-" + msgjsonobj.desc,function(){
						
					});
				}

			},
			"error" : function(msgjsonobj) {
				//alert("引发了莫名的错误?");
				$.showAlertMessage("error","删除失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
					
				});
			}
		});
	}
}
function editComment(id){
	hideAll();
	$("#comment_id_s").val(id);
	$("#comment_context_s").val($("#comment_contxt_id_"+id).text());
	/*$("#edit_comment_div").fadeIn();
	$("#edit_comment_div_box").delay(500).slideDown();*/
	$("#edit_comment_div").dialog({
		width : 800,
		height : 400,
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
}
function cancle_comment(){
	//$("#edit_comment_div").fadeOut();
	$("#edit_comment_div").dialog("close");
}
function updateComment(){
	if($("#comment_context_s").val() == null || $("#comment_context_s").val() == ""){
		alert("请输入评论再提交!");
		return;
	}
	$.ajax({
		"url" : "/comment/update",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			id : $("#comment_id_s").val(),
			context : $("#comment_context_s").val()
		},
		"success" : function(msgjsonobj) {
			if(msgjsonobj.status == 100){
				//alert(msgjsonobj.status +"-"+ msgjsonobj.desc +" 修改成功!");
				$.showAlertMessage("success","修改成功",msgjsonobj.status +"-"+ msgjsonobj.desc +" 修改成功!",function(){
					location.href = window.location.href;
				});
				
			}else{
				//alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				$.showAlertMessage("error","修改失败",msgjsonobj.status + "-" + msgjsonobj.desc,function(){
					
				});
			}
			
		},
		"error" : function(msgjsonobj) {
			//alert("引发了莫名的错误?");
			$.showAlertMessage("error","修改失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
				
			});
		}
	});
}

//查询角色列表
function selectRole(){
	/*<select class="inputclass" id="new_role_id" name="new_role_id">
	<option value="100001">管理员</option>
	<option value="100002">普通用户</option>
	</select>*/
	$.ajax({
		"url" : "/user/rolelist",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			
		},
		"success" : function(data) {
			var html = "";
			for(var i = 0; i < data.length; i++){
				html += "<option value='" + data[i].id + "'>" + data[i].description + "</option>";
			}
			$("#new_role_id").html(html);
		},
		"error" : function(msgjsonobj) {
			//alert("引发了莫名的错误?");
			$.showAlertMessage("error","查询角色列表失败",msgjsonobj.responseJSON.exception +"-"+ msgjsonobj.responseJSON.message,function(){
				
			});
		}
	});
}

