/**
 * 列表的扩展方法,对列表自定的一些扩展方法写在这里
 * 
 */

 /**
 * 列表单击、双击事件处理方法
 */
$.tableSelect = function(options){
	var defaultOptions = {
		tableID: "tableId",
		tableType: "checkbox",
		firstTrCall : function(data,chk){},
		ondblclick: function (data,chk){},
    	callback: null
    };
	defaultOptions = $.extend(defaultOptions,options||{});
	var tableID = defaultOptions.tableID;
	var tableType = defaultOptions.tableType;// 默认为复选框类型
	// 设置table列表的事件处理。如多选框选中的时，调用设置的js方法
	if(defaultOptions.firstTrCall != null){
		$("."+tableID+"_all").unbind("click").bind("click",function(event){
			defaultOptions.firstTrCall.call(this,$(this).getValue(),$(this).prop("checked"));
		});
	}
	
	// 双击table的事件
	if(defaultOptions.ondblclick != null){
		$("#"+tableID).find("tbody").find("tr").unbind("dblclick").bind("dblclick",function(event){
			defaultOptions.ondblclick.call(this,$(this).find(":"+tableType).getValue(),$(this).find(":"+tableType).prop("checked"));
		});
	}
	
	if(defaultOptions.callback != null){
		$("#"+tableID).find("tbody").find(":"+tableType).unbind("click").bind("click",function(event){
			defaultOptions.callback.call(this,$(this).getValue(),$(this).prop("checked"));
			event.stopPropagation();
		});
		
		$("#"+tableID).find("tbody").find("tr").bind("click",function(event){
			defaultOptions.callback.call(this,$(this).find(":"+tableType).getValue(),$(this).find(":"+tableType).prop("checked"));
		});
	}
}

/**
 * 设置列表默认勾选
 */
$.defTableLineCheck = function(options){
	var defaultOptions = {
			tableId: "formId",
			comparList : ""
	    };
	
	defaultOptions = $.extend(defaultOptions,options||{});
	var tableId = defaultOptions.tableId;
	var comparList = defaultOptions.comparList;
	// 比对当前分页的界面数据和传入的数据，
	// 如果当前分页存在在传入数据中，将相应的那条数据设置勾选状态。
	var allSelect = $("#"+tableId).getPageAllSelected();
	var allObj = eval("("+allSelect+")");
	if(allObj != null && allObj != undefined && allObj.length > 0){
		var comparListObj = eval("("+comparList+")");
		if(comparListObj != null && comparListObj != undefined && comparListObj.length > 0){
			for(var i =0;i<allObj.length;i++){
				var selectObj = allObj[i];
				for(var j = 0;j<comparListObj.length;j++){
					var comparObj = comparListObj[j];
					if(comparObj.ID == selectObj.ID){
						var index = selectObj.INNERINDEX-1;
						$("#"+tableId+"_item_"+index).attr("checked",true);
					}
				}
			}
		}
	}
}

$.changeTableCheckRecordForPaging = function(options){
	// 获取界面中隐藏的list，将其元素迁移到新的list，并替换原先旧的信息
	// 其中迁移过程中，需要判断元素是否迁移：
	// 1. 如果传入的元素在旧list中，只有选中的情况下才需迁移
	// 2. 如果不存在，不管是没有找到，还是旧list为空，都将传入元素加入到新list
	var defaultOptions = {
			formId: "formId",
			hiddenName : "",
			checkData: data,
			checkFlag: false
	    };
	
	defaultOptions = $.extend(defaultOptions,options||{});
	var formId = defaultOptions.formId;
	var hiddenName = defaultOptions.hiddenName;
	var data = defaultOptions.checkData;
	var chk = defaultOptions.checkFlag;
	
	var subList = $('#'+formId+' input[name="'+hiddenName+'"]').getValue();
	var oldList = "[]"; // 旧数据list
	var newList = []; // 新数据list ，保存比较后最新的数据
	if(subList != null && subList != undefined && subList.length > 0){
		oldList = subList;
	}
	var oldListObj = eval("("+oldList+")");
	
	var impleFlag = true;// 是否将当期元素迁移到新list
	if(oldListObj != null && oldListObj != undefined && oldListObj.length > 0){
		for(var i =0,j=oldListObj.length;i<j;i++){
			var obj = oldListObj[i];
			if(data.ID == obj.ID){
				impleFlag = false; // 已经在旧list找到，是否迁移由此时判断，外部不能在重新执行一次插入。
				if(chk){// 选框是选中状态，出现的场景为list某笔选中后，再次全选
					newList.push(obj);
				}
			}else{
				newList.push(obj);
			}
		}
	}
	if(impleFlag){
		if(chk){
			var newItem = {};
			newItem.ID = data.ID;
			newList.push(newItem);
		}
	}
	$('#'+formId+' input[name="'+hiddenName+'"]').val(JSON.stringify(newList));
	
}
