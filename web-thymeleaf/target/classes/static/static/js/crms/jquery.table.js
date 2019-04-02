/**
 * 用途：表格显示数据，自动增加分页
 * @author Alon Cai
 * 使用前提：引入jq
 * 用法:
 *  [jq表格对象].refresh(表单ID) 刷新表格
 * 	[jq表格对象].initData(初始化JSON数据, 表单ID) ---通常用于易开页面载入时的已有数据，表单ID作用仅用于分页提交
 *  [jq表格对象].setData(返回的JSON数据, 表单ID)  ---通常用于ajax请求返回的处理，表单ID作用仅用于分页提交
 *  [jq表格对象].addData(返回的JSON数据)  ---添加一行记录，不分页
 *  [jq表格对象].getSelected([参数可选，仅一个属性]) ---仅有单选框或复选框时有效，返回选中行的 json 对象列表:[{id:xxx}]，即使返回一个也是一个 json列表，
 *  [jq表格对象].getSelectedFiledValues([仅一个属性]) ---仅有单选框或复选框时有效，返回字符串，多个则用逗号隔开
 *  [jq表格对象].deleteSelected() ---仅有单选框或复选框时有效，删除选中行
 *  
 *  注意：表格的	ID要与json数据中的数据的列表ID一致，否则数据将无法载入。
 */
(function($){
	/**
     * 表单搜索框初始化
     */
    $.fn.tableSearchForm = function(options){
    	var _formId=$(this).attr("id");
    	var defaultOptions = {
    		validator: null,
    		tableID: "table",
    		ids:null,
    		currPage:1,
    		pageSize:10,
    		start:0,
    		ignoreField:[],
    		jsonFiled:[],
    		ignoreEmpty:false,
    		showPage:true,
    		tableSelClick: function(data){},
    		callback: function(data) {
    			//_submit()的回调方法
    		   if(data!=null)$.extend(true,data,data["pageInfo"]);
    			$("#"+this.tableID).next(".pagerBar").remove();//删除分页组件，调用initData方法会重新生成
    			if(data!=null && data!=null){
    				if(this.tableID!=null && this.tableID!=undefined && this.tableID!="" && $("#"+this.tableID).length>0){
    					$("#"+this.tableID).initData(data, _formId,false); //设置表值
    				}
    				defaultOptions.tableSelClick.call(this,data,true);
    			}
    			// 根据分页查询到的数据，进行弹出框的自适应高度修改
    			reLoadedIframeHeigth();
    		}
    	};
    	defaultOptions = $.extend(defaultOptions,options||{});
		if(defaultOptions.tableID!=null && defaultOptions.tableID!=undefined && defaultOptions.tableID!="" && $("#"+defaultOptions.tableID).length>0){
			if($("#"+defaultOptions.tableID).attr("currPage")!=null && $("#"+defaultOptions.tableID).attr("currPage")!=undefined){
				$("#"+defaultOptions.tableID).attr("currPage",defaultOptions.currPage);
			}
			if($("#"+defaultOptions.tableID).attr("pageSize")!=null && $("#"+defaultOptions.tableID).attr("pageSize")!=undefined){
				$("#"+defaultOptions.tableID).attr("pageSize",defaultOptions.pageSize); 
			}
			
			$("#"+defaultOptions.tableID).attr("formId",_formId);
			$("#"+defaultOptions.tableID).initEmptyData();
		}
        $(this).initSubmit(defaultOptions);
    };
    
    /**
     * 初始化列表数据
     */
    $.fn.initListData = function(){
    	$(this).refreshPage();
    }
	
	/**
     * 刷新表格
     */
	$.fn.refreshPage = function (){
		var _callbackFunNm=arguments[0] ? arguments[0] : ""; //回调函数名称
		var tableID=$(this).attr("id");
		var _formId=$("#"+tableID).attr("formId");
		if(!isEmpty(_formId)){
			var currPage=$("#"+tableID).attr("currPage");
			if(isEmpty(currPage)){currPage=1;}
			var pageSize=$("#"+tableID).attr("pageSize");
			if(isEmpty(pageSize)){pageSize=10;}
			var showPage=$("#"+tableID).attr("showPage");
			$("#"+_formId)._submit({tableID:tableID,currPage:currPage,pageSize:pageSize,callback: function(data) {
				//若刷新列表无数据，则回到上一页
			    if(data!=null)$.extend(true,data,data["pageInfo"]);
				if(data==null ||(data.list==null ||data.list.length==0)){
					if(currPage > 1){
						$("#"+tableID).attr("currPage",currPage-1); //上一页
						$("#"+this.tableID).refreshPage();
						return;
					}else{
						$("#"+this.tableID).setEmptyData();
						return;
					}
				}
				$("#"+this.tableID).initData(data,_formId,showPage); //设置表值
				if(!isEmpty(_callbackFunNm)){
					eval(_callbackFunNm+"()");
				}
			}});
		}
	}
	
	/**
     * 封装列表数据，传进来的可以是json串或对象
     */
    $.fn.initData = function (data){
    	var tableID=$(this).attr("id");
    	var _formId=arguments[1] ? arguments[1] : "";
    	var showPage=arguments[2] ? arguments[2] : true;
    	if(typeof(data) == "object"){//如果传进来的是对象，先转成JSON，再包一层“tableID”
    		var data1 = JSON.stringify(data);
    		var str="{"+tableID+":"+data1+"}";
    		$('#'+tableID).setData(eval("("+str+")"), _formId ,showPage);
    	}else{
    		var str="{"+tableID+":"+data+"}";
        	$('#'+tableID).setData(eval("("+str+")"),_formId,showPage);
    	}
    	
    }
    
    /**
     * 添加列表数据
     */
    $.fn.addData = function(data){
    	var _trid=arguments[1] ? arguments[1] : -1;
    	var tableID=$(this).attr("id");
    	var _tbody = $(this).find("tbody").first();
    	//1.设置值（拿出列表的所有列，放到fields中）
		var fields = [];
		var sizes = [];
		var amts = [];
		$(this).find("th").each(function(i){
			if($(this).attr('field')!=null && $(this).attr('field')!=undefined){
				//对于复选框，添加绑定事件（当勾全选时，把列表中的所有数据都勾上）
				if($(this).attr('field')=='checkbox'){
					$(this).html('');
					var b=$("<input type='checkbox' class='"+tableID+"_all'/>");
					$(this).append(b);
					//复选框，绑定列表左上角那个“全选”框的变化事件，若此框勾上，则列表数据全勾选上
					b.bind("change",function(){var x=this.checked;$("."+tableID+"_item").each(function(){this.checked=x});});
				}
				fields.push($(this).attr('field'));
				if($(this).attr('size') != null && $(this).attr('size') != undefined){
					sizes.push($(this).attr('size'));
				}else sizes.push(20);
				if($(this).attr('amt') != null && $(this).attr('amt') != undefined && $(this).attr('amt') != ''){
					amts.push($(this).attr('amt'));
				}else amts.push(false);
			}
		});
		var row = $("<tr></tr>");
		var i=_tbody.find("tr").length;
		//2.赋值开始
    	$.each(fields,function(k,field){
    		var hide=false;
        	if("index" == field){
        		if(_trid!=-1){
        			field=_tbody.find("tr[trid='"+_trid+"']").find("td[field='index']").text();
        		}else{
        			field = i+1;
        		}
        	}else if("hidden" == field){
        		var x=(i+1);
        		field="<input type='hidden' class='"+tableID+"_item' value='"+JSON.stringify(data)+"' name='"+tableID+"_item'/>";
        		hide=true;
        	}else if("checkbox" == field){
        		var x=(i+1);
        		field="<input type='checkbox' class='"+tableID+"_item' value='"+JSON.stringify(data)+"' name='"+tableID+"_item'/>";
        	}else if("radiobox" == field){
        		var x=(i+1);
        		field="<input type='radio' name='"+tableID+"_item' class='"+tableID+"_item' value='"+JSON.stringify(data)+"'/>";
        	}else{
        		var _field = field.match(/\{\w*\d*\}/g);
        		if(_field!=null){
        			$.each(_field,function(k,d){
                		var _field1 = d.substr(1,d.length-2);
                		if(null == data[_field1] || undefined == data[_field1]){
                			data[_field1] = "";
                		}
                		field = field.replace(/\{\w*\d*\}/,data[_field1]);
                	});
                	try{
                		field = eval(field);
                		if(field==undefined){
                			field='';
                		}
                	}catch(exception) {
                	}
                	if(isEmpty(_field))field="";
            		//数据千分位分割
            		if(amts[k]){
            			field = formatAmt(field);
            		}else{
            			//数据太长截取
	            		var maxSize = sizes[k];
	            		if(maxSize > -1 && field != null && field != undefined && field.length > maxSize){
	            			var fullField = field;
	            			var hiddenField = "<input type='hidden' value=\""+fullField+"\"/>";;
	            			field = field.substr(0,maxSize)+"...";
	            			field = hiddenField + field;
	            		}
            		}
        		}
        	}
        	var col=null;
        	if(/@([\d\.]*)$/.test(''+field)){
        		field=fmoney(RegExp.$1);
        		col=$("<td class='textright'>"+field+"</td>");
        	}else if(/^@(.*)$/.test(''+field)){
        		field=RegExp.$1;
        		col=$("<td class='textright'>"+field+"</td>");
        	}else{
        		col=$("<td class='textcenter'>"+field+"</td>");
        	}
        	if(hide){
        		$(_tbody.parent().find("th")[k]).hide();
        		col.hide();
        	}
        	row.append(col);
        });
    	_tbody.find("tr").each(function(){
    		if($(this).text()=='暂无相关数据'){
    			$(this).remove();
    		}
    	});
    	if(_trid!=-1 ){
    		$(_tbody.find("tr[trid='"+_trid+"']").first()).html(row.html()); //修改
    	}else{
    		_tbody.append(row);//添加到模板的容器中
    	}
        _tbody.find(":radio").unbind("click").bind("click",function(event){
			event.stopPropagation();
		});
		_tbody.find(":checkbox").unbind("click").bind("click",function(event){
			event.stopPropagation();
		});
		//3.绑定单选框和复选框的点击事件
		row.bind("click",function(){
			$(this).find(":checkbox").each(function(){
				if(this.checked==false){
					//原来是没勾选上的，现在勾选上
					this.checked=true;
				}else{
					//原来是勾选上的，现在把勾去掉
					this.checked=false;
					$("."+tableID+"_all")[0].checked='';//把“全选”的那个勾去掉
				}
			});
			$(this).find(":radio").each(function(){
				if($(this).attr('autocheck') != undefined && $(this).attr('autocheck') != null && $(this).attr('autocheck') == 'false' ){
					
				}else{
					if(this.checked==false){
						this.checked=true;
					}
				}
			});
		});
		//4.重新初始化trid
		$(this).reInitTrid();
		bubble_mouse();
    }
    
    /**
     * 根据trid属性删除列表数据
     */
    $.fn.deleteByTrid = function(_trid){
    	$(this).find("tr[trid='"+_trid+"']").remove();
    	$(this).reInitTrid();//remove后重新初始化trid属性
    }
    
    /**
     * 给每一行添加“trid”属性、以及重建索引
     */
    $.fn.reInitTrid = function(){
    	var tableID=$(this).attr("id");
    	var _tbody = $(this).find("tbody").first();
    	var i=1;
    	//根据“ableID+_tr_+i”的格式，给每一行添加“trid”属性
    	_tbody.find("tr").each(function(){
    		$(this).attr("trid",tableID+"_tr_"+i);
    		i++;
    	});
    	i=1;
    	//为filed="indxe"(一般是列表的序号)重建索引
    	/*$(this).find("thead>tr>th").each(function(x){
    		if($(this).attr("field")=='index'){ //重建索引
    			_tbody.find("tr").each(function(){
    				$(this).find("td").eq(x).text(i++);
    			});
    			return;
    		}
    	});*/
    }
    
    /**
     * 列表样式初始化(列表斑马线效果、鼠标移到列表某条数据上时出现阴影效果)
     */
	$.fn.initStyle = function() {
		//列表斑马线效果（奇数行去除zebra，偶数行添加）
		$(this).parent().find('.footable tbody tr:odd').removeClass('zebra');
		$(this).parent().find('.footable tbody tr:even').addClass('zebra');
		//鼠标移到列表某条数据上时，出现阴影效果
		$(this).parent().find('.footable > tbody > tr').hover(function() {
			$(this).addClass("footable_trhover");
		}, function() {
			$(this).removeClass("footable_trhover");
		});
	}
	
	/**
	 * 初始化列表为无数据
	 */
	$.fn.initEmptyData = function(){
		var _thead = $(this).find("thead").first();
		var _tbody = $(this).find("tbody").first();
		var colcount=_thead.find("th").length;
		if(_tbody.find("tr").length<=0){
			var row = $("<tr><td style='height: 50px;' align='center' colspan='"+colcount+"'>暂无相关数据</td></tr>");
			_tbody.append(row);//添加到模板的容器中
		}
		$(this).initStyle();
	}
	
	/**
     * 设置无数据列表
     */
    $.fn.setEmptyData = function(){
    	$(this).next(".pagerBar").remove();
    	var _thead = $(this).find("thead").first();
    	var _tbody = $(this).find("tbody").first();
    	var colcount=_thead.find("th").length;
    	if(_tbody.find("tr").length>0){
    		_tbody.find("tr").remove();
    	}
    	var row = $("<tr><td style='height: 50px;' align='center' colspan='"+colcount+"'>暂无相关数据</td></tr>");
    	_tbody.append(row);//添加到模板的容器中
        $(this).initStyle();
    }
    
    /**
     * 不分页的列表塞值
     */
    $.fn.setDataWithNotPage  = function(data){
    	var _formId=arguments[1] ? arguments[1] : '';
    	var tableID=$(this).attr("id");
    	//设置值
		var fields = [];
		//1.遍历每一列，放入fields中
		$(this).find("th").each(function(i){
			if($(this).attr('field')!=null && $(this).attr('field')!=undefined){
				//对于复选框，添加绑定事件（当勾全选时，把列表中的所有数据都勾上）
				if($(this).attr('field')=='checkbox'){
					$(this).html('');
					var b=$("<input type='checkbox' class='"+tableID+"_all'/>");
					$(this).append(b);
					//复选框，绑定列表左上角那个“全选”框的变化事件，若此框勾上，则列表数据全勾选上
					b.bind("change",function(){var x=this.checked;$("."+tableID+"_item").each(function(){this.checked=x});});
				}
				fields.push($(this).attr('field'));
			}
		});
		var _tbody = $(this).find("tbody").first();
		var _thead = $(this).find("thead").first();
		_tbody.children().remove();
		//2.赋值开始
		if(!isEmpty(data)){
			if(isEmpty(data[tableID]) || isEmpty(data[tableID].content)){
				$(this).setEmptyData();
		        return;
			}
			//遍历列表的数据
			$.each(data[tableID].content, function(i, n){
	            var row = $("<tr></tr>");
	            //对每个field赋值
	            $.each(fields,function(k,field){
	            	var hide=false;
	            	if("index" == field){
	            		field = i+1;
	            	}else if("hidden" == field){
	            		var x=(i+1);
	            		field="<input type='hidden' class='"+tableID+"_item' name='"+tableID+"_item' value='"+JSON.stringify(n)+"'/>";
	            		hide=true;
	            	}else if("checkbox" == field){
	            		var x=(i+1);
	            		field="<input type='checkbox' class='"+tableID+"_item' value='"+JSON.stringify(n)+"' name='"+tableID+"_item'/>";
	            	}else if("radiobox" == field){
	            		var x=(i+1);
	            		field="<input type='radio' name='"+tableID+"_item' class='"+tableID+"_item' value='"+JSON.stringify(n)+"'/>";
	            	}else{
	            		var _field = field.match(/\{\w*\d*\}/g);
	            		if(_field!=null && _field!=undefined){
	            			$.each(_field,function(k,d){
	                    		var _field1 = d.substr(1,d.length-2);
	                    		if(n[_field1]!=undefined){
	                    			field = field.replace(/\{\w*\d*\}/,n[_field1]);
	                    		}else{
	                    			field = field.replace(/\{\w*\d*\}/,"");
	                    		}
	                    	});
	                    	try{
	                    		field = eval(field);
	                    	}catch(exception) {
	                    	}
	            		}else{
	            			_field=field;
	            		}
	            		if(isEmpty(_field)){
	            			field="";
	            		}
	            	}
	            	if(isEmpty(field)){
	            		field="";
	            	}
	            	var  col=null;
	            	if(/@([\d\.]*)$/.test(''+field)){
	            		field=fmoney(RegExp.$1);
	            		col=$("<td class='textright'>"+field+"</td>");
	            	}else if(/^@(.*)$/.test(''+field)){
	            		field=RegExp.$1;
	            		col=$("<td class='textright'>"+field+"</td>");
	            	}else{
	            		col=$("<td class='textcenter'>"+field+"</td>");
	            	}
	            	if(hide){
	            		$(_tbody.parent().find("th")[k]).hide();
	            		col.hide();
	            	}
	            	row.append(col);
	            });
	            _tbody.append(row);//添加到模板的容器中
//	            $(this).initStyle();
	        });
		}else{
			$(this).setEmptyData();
	        return;
		}
		_tbody.find(":radio").unbind("click").bind("click",function(event){
			event.stopPropagation();
		});
		_tbody.find(":checkbox").unbind("click").bind("click",function(event){
			event.stopPropagation();
		});
		//3.绑定单选框和复选框的点击事件
		_tbody.find("tr").unbind("click").bind("click",function(){
			$(this).find(":checkbox").each(function(){
				if(this.checked==false){
					//原来是没勾选上的，现在勾选上
					this.checked=true;
				}else{
					//原来是勾选上的，现在把勾去掉
					this.checked=false;
					$("."+tableID+"_all")[0].checked='';//把“全选”的那个勾去掉
				}
			});
			$(this).find(":radio").each(function(){
				if($(this).attr('autocheck') != undefined && $(this).attr('autocheck') != null && $(this).attr('autocheck') == 'false' ){
					
				}else{
					if(this.checked==false){
						this.checked=true;
					}
				}
			});
		});
		//4.绑定change事件，当列表中有一条数据被勾选掉，全选的那个勾要去掉
		$("."+tableID+"_item").each(function(){
			$(this).bind("change",function(){if(this.checked==false)$("."+tableID+"_all")[0].checked='';});
		});
		//5.初始化提交，即绑定表单提交事件和表单提交事件的回调处理
		if(_formId!=null && _formId!='' && _formId!=undefined){
			$("#"+_formId).initSubmit({tableID:tableID,callback: function(data) {
				if(data!=null && data['pageInfo']!=null){
					$("#"+this.tableID).setDataWithNotPage(data['pageInfo']); //设置表值
				}
			}});
		}
		$(this).initStyle();
		$(this).reInitTrid();
    }
    
    /**
     * 列表塞值方法
     */
    $.fn.setData = function(data){
    	var _formId1=arguments[1] ? arguments[1] : "";
    	var showPage=arguments[2] ? arguments[2] : true;
    	var tableID=$(this).attr("id");
    	if(_formId1!=null && _formId1!=undefined && _formId1!=""){
    		$("#"+tableID).attr("formId",_formId1);
    	}
		//1.设置值（拿出列表的所有列，放到fields中）
		var fields = [];
		var sizes = [];
		var amts = [];
		$(this).find("th").each(function(i){
			if($(this).attr('field')!=null && $(this).attr('field')!=undefined){
				//对于复选框，添加绑定事件（当勾全选时，把列表中的所有数据都勾上）
				if($(this).attr('field')=='checkbox'){
					$(this).html('');
					var b=$("<input type='checkbox' class='"+tableID+"_all'/>");
					$(this).append(b);
					//复选框，绑定列表左上角那个“全选”框的变化事件，若此框勾上，则列表数据全勾选上
					b.bind("change",function(){var x=this.checked;$("."+tableID+"_item").each(function(){this.checked=x});});
				}
				fields.push($(this).attr('field'));
				if($(this).attr('size') != null && $(this).attr('size') != undefined){
					sizes.push($(this).attr('size'));
				}else sizes.push(20);
				if($(this).attr('amt') != null && $(this).attr('amt') != undefined && $(this).attr('amt') != ''){
					amts.push($(this).attr('amt'));
				}else amts.push(false);
			}
		});
		var _tbody = $(this).find("tbody").first();
		var _thead = $(this).find("thead").first();
		_tbody.children().remove();
		//2.赋值开始
		var pageNum_=data[tableID].pageSize*(data[tableID].pageNum-1);
		if(data!=null && data!=undefined){
			var list=null;
			if(data[tableID]!=null && data[tableID].content!=null){
				list=data[tableID].content;//分页器pageInfo中的content属性即为具体数据
			}
			if(isEmpty(list)){
				$(this).setEmptyData();
				var html_='<div class="pagerBar" style="width: 1346px;"><div class="pagerLeftBar"><span class="space">每页显示记录</span><select><option value="10">10</option><option value="20">20</option><option value="30">30</option></select><a href="javascript:void(0)" class="btn-xs space first disabled">首页</a><a href="javascript:void(0)" class="btn-xs space previous disabled">&lt;上一页</a><span class="pageNoBar"><a href="javascript:void(0)" class="btn-xs space numBtn pageSelector-current">01</a></span><a href="javascript:void(0)" class="btn-xs space next disabled">下一页&gt;</a><a href="javascript:void(0)" class="btn-xs space last disabled">末页</a></div><div class="pagerRightBar"><span class="totalPage-pre">共</span><span class="totalPage">1</span><span class="totalPage-suf">页</span><span class="space">到第</span><input size="5" class="gotoInput" type="text"><span>页</span><input class="space  submit-btn goto" value="转到" type="button"></div></div>';
				$(this).after(html_);
		        return;
			}
			//遍历列表的数据
			$.each(list, function(i, n){
	            var row = $("<tr></tr>");
	            //对每个field赋值
	            $.each(fields,function(k,field){
	            	var hide=false;
	            	if("index" == field){
	            		
	            		field = i+1;
	            		field=field+pageNum_;
	            	}else if("hidden" == field){
	            		var x=(i+1);
	            		field="<input type='hidden' class='"+tableID+"_item' value='"+JSON.stringify(data)+"' name='"+tableID+"_item'/>";
	            		hide=true;
	            	}else if("checkbox" == field){
	            		var x=(i+1);
	            		field="<input type='checkbox' class='"+tableID+"_item' name='"+tableID+"_item' id='"+tableID+"_item_"+i+"' value='"+JSON.stringify(n)+"'/>";
	            	}else if("radiobox" == field){
	            		var x=(i+1);
	            		field="<input type='radio' name='"+tableID+"_item' id='"+tableID+"_item_"+i+"' class='"+tableID+"_item' value='"+JSON.stringify(n)+"'/>";
	            	}else{
	            		var _field = field.match(/\{[\w\.\d]*\}/g);
	            		if(!isEmpty(_field)){
	            			$.each(_field,function(k,d){
	                    		var _field1 = d.substr(1,d.length-2);
	                    		var _field_=[];
	                    		//field = field.replace(/\{\w*\d*\}/,n[_field1]);
	                    		//支持 . 对象传值 daniel添加
	                    		if(_field1.indexOf(".")>-1){
	                    			_field_=_field1.split(".");
	                    			
	                    			var node=n;
	                    			for(var i=0;i<_field_.length;i++){
	                    				 node=node[_field_[i]];
	                    			}
	                    			if(!isEmpty(node)){
	                    				field = field.replace(/\{[\w\.\d]*\}/,node);
	                    			}else{
	                    				field = field.replace(/\{[\w\.\d]*\}/,"");
	                    			}
	                    		}else{
	                    			if(!isEmpty(n[_field1])){
	                    				field = field.replace(/\{\w*\d*\}/,n[_field1]);
	                    				if(field.indexOf("('*')")>-1){
											field = "*";
										}
	                    			}else{
	                    				field = field.replace(/\{\w*\d*\}/,"");
	                    			}
	                    			
	                    		}
	                    		
	                    	});
	                    	try{
	                    		field = eval(field);
	                    	}catch(exception) {
	                    		
	                    	}
	            		}else{
	            			_field=field;
	            		}
	            		if(isEmpty(_field))field="";
	            		//数据千分位分割
	            		if(amts[k]){
	            			field = formatAmt(field);
	            		}else{
	            			//数据太长截取
		            		var maxSize = sizes[k];
		            		if(maxSize > -1 && field != null && field != undefined && field.length > maxSize){
		            			var fullField = field;
		            			var hiddenField = "<input type='hidden' value=\""+fullField+"\"/>";;
		            			field = field.substr(0,maxSize)+"...";
		            			field = hiddenField + field;
		            		}
	            		}
	            	}
	            	if(isEmpty(field))field="";
	            	var  col=null;
	            	if(/@([\d\.]*)$/.test(''+field)){
	            		field=fmoney(RegExp.$1);
	            		col=$("<td class='textright'>"+field+"</td>");
	            	}else if(/^@(.*)$/.test(''+field)){
	            		field=RegExp.$1;
	            		col=$("<td class='textright'>"+field+"</td>");
	            	}else{
	            		col=$("<td class='textcenter'>"+field+"</td>");
	            	}
	            	if(hide){
	            		$(_tbody.parent().find("th")[k]).hide();
	            		col.hide();
	            	}
	            	row.append(col);
	            });
	            _tbody.append(row);//添加到模板的容器中
	            bubble_mouse();
	            //$(this).initStyle();
	        });
		}else{
			$(this).setEmptyData();
	        return;
		}
		_tbody.find(":radio").unbind("click").bind("click",function(event){
			event.stopPropagation();
		});
		_tbody.find(":checkbox").unbind("click").bind("click",function(event){
			event.stopPropagation();
		});
		//3.绑定单选框和复选框的点击事件
		_tbody.find("tr").unbind("click").bind("click",function(){
			$(this).find(":checkbox").each(function(){
				if(this.checked==false){
					//原来是没勾选上的，现在勾选上
					this.checked=true;
				}else{
					//原来是勾选上的，现在把勾去掉
					this.checked=false;
					$("."+tableID+"_all")[0].checked='';//把“全选”的那个勾去掉
				}
			});
			$(this).find(":radio").each(function(){
				if($(this).attr('autocheck') != undefined && $(this).attr('autocheck') != null && $(this).attr('autocheck') == 'false' ){
					
				}else{
					if(this.checked==false){
						this.checked=true;
					}
				}
			});
		});
		//4.绑定change事件，当列表中有一条数据被勾选掉，全选的那个勾要去掉
		$("."+tableID+"_item").each(function(){
			$(this).bind("change",function(){if(this.checked==false)$("."+tableID+"_all")[0].checked='';});
		});
		//5.初始化分页(主要是走进.pager方法),绑定页面的“上一页”、“下一页”，“首页”、“末页”等的点击事件，
		//  当点击时，走onselectpage事件，最终走_submit方法提交表单，拿到返回的列表数据调用initData完成列表塞值
		if(true/*$("#"+tableID).next(".pagerBar").length<=0*/){
			if(data!=null&&data[tableID]!=null){
				if(data[tableID].content!=null){
					$("#"+tableID).next(".pagerBar").remove();
//					if(data[tableID].pages>1){ //大于1页时显示分页(pages为PageInfo类的属性)
						//(总条数total为PageInfo类的属性)
						//$(this).pager({"length":data[tableID].pageSize,"total":data[tableID].total,"currentPage":data[tableID].pageNum, "start": data[tableID].startRow-1,"onselectpage":function(i,len){
						$(this).pager({"showPage":showPage,"length":data[tableID].pageSize,"total":data[tableID].total,"currentPage":data[tableID].pageNum, "start": data[tableID].startRow-1,"onselectpage":function(i,len){
								
							var currPage = Math.floor(i / len + 1);
							var start = i;
							var pageSize = len;
							this.pageSize= pageSize;
							var _formId=$("#"+tableID).attr("formId");
							$("#"+tableID).attr("pageSize",pageSize);
							$("#"+tableID).attr("clear","0");
							$("#"+tableID).attr("formId",_formId);
							if(_formId!=null && _formId!=undefined && _formId!=""){
								$("#"+_formId)._submit({tableID:tableID,currPage:currPage,start:start,pageSize:pageSize,callback:function(data) {
								   if(data!=null)$.extend(true,data,data["pageInfo"]);
									$("#"+tableID).attr("currPage",currPage); //默认当前页为第1页
									$("#"+tableID).attr("pageSize",pageSize);
									$("#"+tableID).attr("formId",_formId);
				    				$("#"+tableID).initData(data, _formId, showPage); //设置表值
				    				// 根据分页查询到的数据，进行弹出框的自适应高度修改
				    				reLoadedIframeHeigth();
				    			}});
							}
						}});
//					}
				}
			}
		}else{
			if(isEmpty(data)||isEmpty(data[tableID])|| data[tableID].pages <=1){
				$("#"+tableID).attr("currPage","1");
				$("#"+tableID).next(".pagerBar").remove();
			}
		}
		$(this).initStyle();
		$(this).reInitTrid();
    };
    
    $.fn.getPageData=function(){
    	var tableID=$(this).attr("id");
    	var colName=arguments[0] ? arguments[0] : "";
    	var ids=null;
    	$(this).find("tr input[class='"+tableID+"_item']:first").each(function() {
    		if ($(this).val() != 'on') {
    			var val=$(this).val();
    			if(colName!=''){
    				val="{'"+colName+"':'"+eval("("+$(this).val()+")")[colName]+"'}";
    			}
    			if (ids != null)
    				ids = ids + "," + val;
    			else
    				ids = val;
    		}
    	});
    	if(ids==null)return "[]";
    	return "["+ids+"]";
    };
    
    /**
     * 获取列表所有的数据
     */
    $.fn.getAllPage = function(){
    	var tableID=$(this).attr("id");
    	var colName=arguments[0] ? arguments[0] : "";
    	var ids=null;
    	$(this).find("input[class='"+tableID+"_item']").each(function() {
			var val=$(this).val();
			if(colName!=''){
				val="{'"+colName+"':'"+eval("("+$(this).val()+")")[colName]+"'}";
			}
			if (ids != null)
				ids = ids + "," + val;
			else
				ids = val;
    	});
    	if(ids==null)return "[]";
    	return "["+ids+"]";
    }
    
    /**
     * 获取列表所有勾选的数据
     */
    $.fn.getSelected=function(){
    	var tableID=$(this).attr("id");
    	var colName=arguments[0] ? arguments[0] : "";
    	var ids=null;
    	$(this).find("input[class='"+tableID+"_item']:checked:checked").each(function() {
			var val=$(this).val();
			if(colName!=''){
				val="{"+colName+":'"+eval("("+$(this).val()+")")[colName]+"'}";
			}
			if (ids != null)
				ids = ids + "," + val;
			else
				ids = val;
    	});
    	if(ids==null)return "[]";
    	return "["+ids+"]";
    };
    
    /**
     * 获取选中列表数据的指定字段的值
     */
    $.fn.getSelectedFiledValues=function(colName){
    	var obj=eval($(this).getSelected(colName));
    	var value="";
    	for(var i=0;i<obj.length;i++){
    		if(i!=0)
    			value+=","+obj[i][colName];
    		else
    			value=obj[i][colName];
    	}
    	return value;
    };
    
    /**
     * 删除选中的列表数据
     */
    $.fn.deleteSelected = function(){
    	var tableID=$(this).attr("id");
    	$(this).find("input[class='"+tableID+"_item']:checked:checked").each(function() {
			var tr=$(this).parents("tr");
			$(tr).remove();
    	});
    }
    
})(jQuery);
function fmoney(_number){
	if(_number=='')return '';
	if(_number=='*') return '*';
   var _jingdu=arguments[1] ? arguments[1] : 0;
   _number = _number.replace(/,/g, "");
  // if((""+_number).indexOf(".")>0){
	   _jingdu=_jingdu>0 && _jingdu<=20? _jingdu :2;
  // }
   _number = parseFloat((_number + "").replace(/[^\d\.-]/g, "")).toFixed(_jingdu) + "";   
   var l =_number.indexOf(".")>0?(_number.split(".")[0].split("").reverse()):(_number.split("").reverse()),   
   r =_number.indexOf(".")>0?("."+ _number.split(".")[1]):"";  
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("")+ r;
}
//table超长字段的气泡功能
function bubble_mouse(){
	$(".textcenter").mouseover(function(){
		bubble_init(this);
	}).mouseout(function(){
		$("#box_1").hide();
		$("#box_2").hide();
	}).click(function(){
		showTextDtl(this);
	})
}
function showTextDtl(obj){
	var textLen = $(obj).find("[type='hidden']").length;
	if(textLen == 1){
		var text = $(obj).find("[type='hidden']").val();
		$.alert("详情",text);
	}
}
function bubble_init(obj){
	var maxLeft = 950;
	var textLen = $(obj).find("[type='hidden']").length;
	if(textLen == 1){
		var top = $(obj).offset().top; 
		var left = $(obj).offset().left; 
		var width = $(obj).width();//$(obj).css('width');
		var height = $(obj).height();//$(obj).css('height');
		var text = $(obj).find("[type='hidden']").val();
		left = left + width/2;
		top = top + height + 25;
		if(left < maxLeft){
			var obj = document.getElementById("box_1");
			if(!obj){							
				var html = "<div class='bubble_box' style='display:none;z-index: 9999;' id='box_1'><span class='bubble_bot_cor_left'></span><div id='box_1s'></div></div>";
				$("body").append(html);
			}
			left = left - 60;
			$("#box_1").css({'top':top});
			$("#box_1").css({'left':left});
			//document.getElementById("box_1s").innerHTML = "top="+top+",left="+left+",length="+textLen+",value="+text;
			document.getElementById("box_1s").innerHTML = text;
			$("#box_1").show();
		}else{
			var obj = document.getElementById("box_2");
			if(!obj){							
				var html = "<div class='bubble_box' style='display:none;z-index: 9999;' id='box_2'><span class='bubble_bot_cor_right'></span><div id='box_2s'></div></div>";
				$("body").append(html);
			}
			left = left - 240;
			$("#box_2").css({'top':top});
			$("#box_2").css({'left':left});
			//document.getElementById("box_2s").innerHTML = "top="+top+",left="+left+",length="+textLen+",value="+text;
			document.getElementById("box_2s").innerHTML = text;
			$("#box_2").show();
		}					
	}				

}