/**
 * 销售任务值复制功能
 * add by 邱欢胜 2017-8-4
 */
$(function(){ 
	var copyContent;
	$("#paste").click(function (){
		 if(isAllEmpty()){
				//复制的当前行的指标至少需要填写一个指标任务值
				 $.alert('提示', '复制的内容至少需要填写一个指标值,请先填写，再进行复制粘贴操作!', 'warning');
				 return;
			 }
		//获取要粘贴的行索引
		$("#tskList input[type='checkbox']:checked").each(function(){
			//当前要选中要粘贴的行索引
			var currentSelectRowIndex=$(this).parents('tr').index();
			for(var i=0;i<copyContent.length;i++){
				if(i==0){
					$("#tskList tbody tr").eq(currentSelectRowIndex+i).children().eq(4).find("input").val(copyContent[i]);
				}else{
					$("#tskList tbody tr").eq(currentSelectRowIndex+i).children().eq(1).find("input").val(copyContent[i]);
				}
			}
		});
		
	});
	
	$("#copy").click(function (){
			//只能选择一个组织的指标进行复制.
			var selectedCount=$("#tskList input[type='checkbox']:checked").length;
			if(selectedCount==0){
				 $.alert('提示', '请选择一个指标进行复制操作!', 'warning');
				return;
			}else if(selectedCount>1){
				 $.alert('提示', '只能选择一个组织的指标进行复制!', 'warning');
				return;
			}else{
				 copyContent=[];
				 $("input[type='checkbox']:checked").each(function(){
					 //获取当前行的指标.
					 var current=$(this).parents('tr').children().eq(4).find("input").val();
					 //获取rowspan数量.
					 var rowspanVal=$(this).parents('tr').children().eq(0).attr("rowspan");
					 copyContent.push($.trim(current));
					 //当前选中行的索引(第一行从0开始计算).
					 var currentRowIndex=$(this).parents('tr').index();
					 //需要拷贝的行数.
					 var len=parseInt(currentRowIndex)+parseInt(rowspanVal);
					 var next;
					 var nextIndex=currentRowIndex+1;
					 //获取选中行所有的指标值.
					 for(nextIndex;currentRowIndex<len-1;currentRowIndex++){
						 next=$("#tskList tbody tr").eq(currentRowIndex+1).children().eq(1).find("input").val();
						 copyContent.push($.trim(next));
					 }
				 });
				 if(isAllEmpty()){
					//复制的当前行至少需要填写一个任务值
					 $.alert('提示', '复制的当前行至少需要填写一个任务值!', 'warning');
					 return;
				 }else{
					 $.alert('提示', '成功复制一个组织的指标任务值到粘贴板中!', 'warning');
				 }
			}
		});
	
	function isAllEmpty(){
		var allEmptyFlag=true;
		 for(var i=0;i<copyContent.length;i++){
			 if(copyContent[i]!=""){
				 allEmptyFlag=false;
				 break;
			 }else{
				 continue;
			 }
		 }
		 return allEmptyFlag;
	}
	}); 
/**
 * 设置包含rowspan的td单元格去掉鼠标经过时间，和背景色
 */
function setRowSpanStyle(){
	$("#tskList tbody tr td").each(function(){
		if($(this).attr("rowspan") != undefined){
			$(this).css("background","white");
			$(this).unbind('mouseenter').unbind('mouseleave');
		}
	});	
}

/**
 * 清空表格的内容，用在改变适用组织,任务指标时,清空原表格内容,填入新的组织和指标
 */
function cleanTable(){
	$("#tskList  thead").html("");  
	$("#tskList  tbody").html("");  
	$("#tskList  thead").append("<tr></tr>");
}
