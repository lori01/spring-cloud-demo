/**
 * 产品选择函数-crm
 * @param serverUrl 服务器地址+项目名称
 * @returns 用户对象
 */
function commonChooseProduct (serverUrl,_callbackFunNm) {
	var iHeight = 500;
	var iWidth = 1100;
	$.showDialog({
		title: "产品页面",
    	data: null,
    	url:basePath+"/productmanage/product_list",
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
        				_callbackFunNm(retObj);
        			}
        		}else{
        			$.alert('提示', '请选择一个产品!', 'failure');
        		}
    		}
    	},
    	divId: '_boxIframeDiv2'
	});
}