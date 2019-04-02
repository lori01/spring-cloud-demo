/**
 * 客户选择函数-crm
 * @param serverUrl 服务器地址+项目名称
 *  aplObjCd 代表查询对公（2）还是对私（1）
 *  queryType 代表查询类型：我的客户1 全量客户2 管理团队所辖 3
 * @returns 客户对象
 */
function commonChooseCst (serverUrl,aplObjCd,queryType,_callbackFunNm) {
	var iHeight = 500;
	var iWidth = 1100;
	if(aplObjCd==1&&queryType==1) { 	var URL=basePath+"/customeraffilation/cust_search_person";}	
	else if (aplObjCd==2&&queryType==1) { 	var URL=basePath+"/customeraffilation/cust_search_public";}	
	else if(aplObjCd==1&&queryType==2) { 	var URL=basePath+"/common/allcust_search?aplObjCd="+aplObjCd;}	
	else if(aplObjCd==2&&queryType==2) { 	var URL=basePath+"/publiccustomers/public_customers_list";}	
	
	$.showDialog({
		title: "客户放大镜",
    	data: null,
    	url:URL,
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
        				alert(retObj);
        				_callbackFunNm(retObj);
        			}
        			
        		}else{
        			$.alert('提示', '请选择一个客户!', 'failure');
        		}
    		}
    	},
    	divId: '_boxIframeDiv2'
	});
}