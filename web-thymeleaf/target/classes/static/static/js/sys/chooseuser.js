/**
 * 选人函数-crm
 * @param serverUrl 选人服务器地址+项目名称
 * @param orgCode 机构编号
 * @param roleCode 角色编号
 * @returns 用户对象
 */
function commonChooseUser (serverUrl,orgCode,roleCode,statusCd,_callbackFunNm) {
	var iHeight = 500;
	var iWidth = 1100;
//	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    //获得窗口的水平位置 
//    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
	/*window.open(basePath+"/sys/chooseuser/choose_user?orgCode="
			+orgCode+"&roleCode="+roleCode,"","width="+iWidth
				+",height="+iHeight+",top=" + iTop + ",left=" + iLeft 
				+",status=no,help=no,scroll=no,resizable=no,location=no,toolbar=no");*/
	$.showDialog({
		title: "选人页面",
    	data: null,
    	url:basePath+"/sys/chooseuser/choose_user?orgCode="+orgCode+"&roleCode="+roleCode,
    	width:iWidth,
    	height:iHeight,
    	method:"get",
    	checkList : true,
    	buttonText:["确定","关闭"],
    	callback: function(data) {
    		if(data!=null && data!="" && data.list_item!=undefined){
    			if(data.list_item!=null&&data.list_item!=""){
        			if(_callbackFunNm!=null){
        				var retObj = eval("("+ data.list_item[0] +")");
        				_callbackFunNm(retObj,orgCode,roleCode);
        			}
        		}else{
        			$.alert('提示', '请选择一个人员!', 'failure');
        		}
    		}
    		
    	},
    	divId: '_boxIframeDiv2'
	});
}
//多选
function commonChooseUserBatch (serverUrl,orgCode,roleCode,statusCd,_callbackFunNm) {
	var iHeight = 500;
	var iWidth = 1100;
//	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    //获得窗口的水平位置 
//    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
	/*window.open(basePath+"/sys/chooseuser/choose_user?orgCode="
			+orgCode+"&roleCode="+roleCode,"","width="+iWidth
				+",height="+iHeight+",top=" + iTop + ",left=" + iLeft 
				+",status=no,help=no,scroll=no,resizable=no,location=no,toolbar=no");*/
	$.showDialog({
		title: "选人页面",
    	data: null,
    	url:basePath+"/sys/chooseuser/choose_user_batch?orgCode="+orgCode+"&roleCode="+roleCode,
    	width:iWidth,
    	height:iHeight,
    	method:"get",
    	checkList : true,
    	buttonText:["确定","关闭"],
    	callback: function(data) {
    		if(data!=null && data!="" && data.list_item!=undefined){
    			if(data.list_item!=null&&data.list_item!=""){
        			if(_callbackFunNm!=null){
        				var retObj = eval("("+ data.list_item +")");
        				_callbackFunNm(data.list_item,orgCode,roleCode);
        			}
        		}else{
        			$.alert('提示', '请选择一个人员!', 'failure');
        		}
    		}
    		
    	},
    	divId: '_boxIframeDiv2'
	});
}

/**
 * 选人函数
 * @param serverUrl 选人服务器地址+项目名称
 * @param orgCode 机构编号
 * @param roleCode 角色编号
 * @returns 用户对象
 */
function commonChooseOrg (serverUrl,orgCode,roleCode,statusCd,_callbackFunNm) {
	var iHeight = 580;
	var iWidth = 280;
//	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    //获得窗口的水平位置 
//    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
	/*window.open(basePath+"/sys/chooseuser/choose_org?orgCode="
			+orgCode+"&roleCode="+roleCode,"","width="+iWidth
				+",height="+iHeight+",top=" + iTop + ",left=" + iLeft 
				+",status=no,help=no,scroll=no,resizable=no,location=no,toolbar=no");*/
    $.showDialog({
		title: "集团组织机构",
    	data: null,
    	url:basePath+"/sys/chooseuser/choose_org?orgCode="+orgCode+"&roleCode="+roleCode,
    	width:iWidth,
    	height:iHeight,
    	method:"get",
    	buttonText:["确定","关闭"],
    	callback: function(data) {
    		
    		if(data!=null && data!="" && data.orgStr!=undefined){
    			if(data.orgStr!=null&&data.orgStr!=""){
        			if(_callbackFunNm!=null){
        				var retObj = eval("("+ data.orgStr +")");
        				_callbackFunNm(retObj,orgCode,roleCode);
        			}
        		}else{
        			$.alert('提示', '请选择一个机构!', 'failure');
        		}
    		}
    		
    	},
    	divId: '_boxIframeDiv2'
	});
}

/**
 * 多选组织
 * @param serverUrl 选人服务器地址+项目名称
 * @param orgCode 机构编号
 * @param roleCode 角色编号
 * @returns 用户对象
 */
function commonChooseOrgBatch (serverUrl,orgCode,roleCode,statusCd,_callbackFunNm) {
	var iHeight = 580;
	var iWidth = 280;
//	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
    //获得窗口的水平位置 
//    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
	/*window.open(basePath+"/sys/chooseuser/choose_org?orgCode="
			+orgCode+"&roleCode="+roleCode,"","width="+iWidth
				+",height="+iHeight+",top=" + iTop + ",left=" + iLeft 
				+",status=no,help=no,scroll=no,resizable=no,location=no,toolbar=no");*/
    $.showDialog({
		title: "集团组织机构",
    	data: null,
    	url:basePath+"/sys/chooseuser/choose_org_batch?orgCode="+orgCode+"&roleCode="+roleCode,
    	width:iWidth,
    	height:iHeight,
    	method:"get",
    	buttonText:["确定","关闭"],
    	callback: function(data) {
    		
    		if(data!=null && data!="" && data.orgStr!=undefined){
    			if(data.orgStr!=null&&data.orgStr!=""){
        			if(_callbackFunNm!=null){
        				var retObj = eval("("+ data.orgStr +")");
        				_callbackFunNm(retObj,orgCode,roleCode);
        			}
        		}else{
        			$.alert('提示', '请选择一个机构!', 'failure');
        		}
    		}
    		
    	},
    	divId: '_boxIframeDiv2'
	});
}

/**
 * 根据机构编号查询一级机构信息
 * @param serverUrl 服务器地址+项目名称
 * @param orgCode 机构编号
 * @returns 机构对象
 */
function commonGetCTopOrg (serverUrl,orgCode) {
	var topOrg;
	var orgUrl=serverUrl+"/sys/chooseuser/getParentOrg";
	var orgParams = {orgCode:orgCode};
	var orgOptions = {
		type:"POST",
		url:orgUrl,
		dataType:"json",
		data:orgParams,
		isLoading:false,
		cache:false,
		async:true,
		success:function(result){
			topOrg = result;
	    }
    };
	$.ajaxquery(orgOptions);
	return topOrg;
}