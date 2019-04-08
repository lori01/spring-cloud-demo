function hideAll() {
	hidemenu();
	canclePwd();
	cancleUser();
	cancleme();
	cancle();
	cancle_comment();
}
/* 菜单 */
function showmenu() {
	//hideAll();
	var litop = $('#showmenu_li').offset().top;
	var lileft = $('#showmenu_li').offset().left;
	var liright = $('#showmenu_li').offset().right;
	var width = $('#showmenu_li').width();
	//$("#menu_me").css("left", lileft+width);
	//$("#menu_me").css("top", litop);
	//$("#menu_me").css("width", "100%");
	$("#menu_me").fadeIn();
	$("#menu_me").delay(500).slideDown();
	//$("#menu_me").show();
}
function hidemenu() {
	$("#menu_me").fadeOut();
	//$("#menu_me").slideUp();
}

/* 更新密码 */
function showPwd() {
	hideAll();
	$("#upd_pwd_div").fadeIn();
	$("#upd_pwd_div_box").delay(500).slideDown();
}
function canclePwd() {
	$("#upd_pwd_div").fadeOut();
}
function updPwd(uid) {
	if ($("#old_pwd").val() == null || $("#old_pwd").val() == "") {
		alert("请输入原密码!");
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
				alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 修改成功!");
				canclePwd();
			} else {
				alert(msgjsonobj.status + "-" + msgjsonobj.desc);
			}

		},
		"error" : function() {
			alert("引发了莫名的错误?");
		}
	});
}

/* 新增用户 */
function showAddUser() {
	hideAll();
	$("#add_user_div").fadeIn();
	$("#add_user_div_box").delay(500).slideDown();
}
function cancleUser() {
	$("#add_user_div").fadeOut();
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
			realname : $("#new_realname").val()
		},
		"success" : function(msgjsonobj) {
			if (msgjsonobj.status == 100) {
				alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 新增成功!");
				cancleUser();
			} else {
				alert(msgjsonobj.status + "-" + msgjsonobj.desc);
			}

		},
		"error" : function() {
			alert("引发了莫名的错误?");
		}
	});
}

/* 个人信息 */
function showme() {
	hideAll();
	$("#me_div").fadeIn();
	$("#me_div_box").delay(500).slideDown();
}
function cancleme() {
	$("#me_div").fadeOut();
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
				alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 修改成功!");
				cancleme();
				/*var check = confirm(msgjsonobj.status + "-" + msgjsonobj.desc);
				if (check) {

				}*/
			} else {
				alert(msgjsonobj.status + "-" + msgjsonobj.desc);
			}

		},
		"error" : function() {
			alert("引发了莫名的错误?");
		}
	});
}

/* 写文章 */
function show() {
	hideAll();
	$("#add_div").fadeIn();
	$("#add_div_box").delay(500).slideDown();
}
function cancle() {
	$("#article_id_s").val("");
	$("#title_s").val("");
	$("#shortContext_s").val("");
	$("#context_s").val("");
	$("#add_div").fadeOut();
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
	$.ajax({
		"url" : "/article/add",
		"type" : "POST",
		"dataType" : "json",
		"data" : {
			id : $("#article_id_s").val(),
			title : $("#title_s").val(),
			shortContext : $("#shortContext_s").val(),
			context : $("#context_s").val()
		},
		"success" : function(msgjsonobj) {
			if (msgjsonobj.status == 100) {
				if($("#article_id_s").val() != null && $("#article_id_s").val() != ""){
					alert(msgjsonobj.status + "-" + msgjsonobj.desc
							+ " 修改成功!");
					location.href = window.location.href;
				}
				else if ($("#add_content_replace_div").html() == undefined) {
					alert(msgjsonobj.status + "-" + msgjsonobj.desc
							+ " 发布成功!");
				} else {
					append(msgjsonobj.obj, msgjsonobj.desc);
				}
				cancle();
				/*var check = confirm(msgjsonobj.status + "-" + msgjsonobj.desc);
				if (check) {

				}*/
			} else {
				alert(msgjsonobj.status + "-" + msgjsonobj.desc);
			}

		},
		"error" : function() {
			alert("引发了莫名的错误?");
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
	var check = confirm("确认删除吗?");
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
					alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 删除成功!");
					location.href = window.location.href;
				} else {
					alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				}

			},
			"error" : function() {
				alert("引发了莫名的错误?");
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
				alert(msgjsonobj.status + "-" + msgjsonobj.desc);
			}

		},
		"error" : function() {
			alert("引发了莫名的错误?");
		}
	});
}
function delComment(id){
	var check = confirm("确认删除吗?");
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
					alert(msgjsonobj.status + "-" + msgjsonobj.desc + " 删除成功!");
					location.href = window.location.href;
				} else {
					alert(msgjsonobj.status + "-" + msgjsonobj.desc);
				}

			},
			"error" : function() {
				alert("引发了莫名的错误?");
			}
		});
	}
}
function editComment(id){
	hideAll();
	$("#comment_id_s").val(id);
	$("#comment_context_s").val($("#comment_contxt_id_"+id).text());
	$("#edit_comment_div").fadeIn();
	$("#edit_comment_div_box").delay(500).slideDown();
}
function cancle_comment(){
	$("#edit_comment_div").fadeOut();
}
function updateComment(){
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
				alert(msgjsonobj.status +"-"+ msgjsonobj.desc +" 修改成功!");
				location.href = window.location.href;
			}else{
				alert(msgjsonobj.status + "-" + msgjsonobj.desc);
			}
			
		},
		"error" : function() {
			alert("引发了莫名的错误?");
		}
	});
}