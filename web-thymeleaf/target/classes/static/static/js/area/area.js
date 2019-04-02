(function($) {
   // 自动提交数据
   $.fn.place = function(areaInfo, callback) {
	  var _selector = $(this);
	  var _index = (100 - $(".store-selector").length).toString();
	  _selector.attr("style", "*z-index:" + _index);
	  _selector.addClass("store-selector");
	  var selector = $(this).selector;
	  _selector.append('<div class="text"><div></div><b></b></div>');
	  var _close = $('<div class="close"></div>');
	  _selector.append(_close);
	  _close.click(function() {
		  _selector.unbind("click");
		 _selector.removeClass('hover');
		 _selector.find(".text").on("click", function() {
			 _selector.addClass('hover');
			 $(selector + " .content").show();
			 $(selector + " .JD-stock").show();
		  }).find("dl").remove();
	  });
	  var maxTab = 5;// 选项卡限制
	  var currentAreaInfo = {
		 currentLevel : 1,
		 area : [ {
			"code" : "-1",
			"name" : ""
		 } ]
	  };// 数据初始值

	  var tabsHtml = '<li data-index="0" data-widget="tab-item" class="curr"><a href="javascript:void(0)" class="hover"><em>请选择</em><i></i></a></li>';
	  var itemsHtml = '<div class="mc stock_province_item" data-area="0" data-widget="tab-content" tid="stock_province_item"></div>';
	  for (var i = 1; i <= maxTab; i++) {
		 tabsHtml = tabsHtml + '<li data-index="' + i + '" data-widget="tab-item" style="display:none;"><a href="javascript:void(0)" class=""><em>请选择</em><i></i></a></li>';
		 itemsHtml = itemsHtml + '<div class="mc" data-area="' + i + '" data-widget="tab-content" tid="stock_' + i + '_item"></div>';
	  }
	  var provinceHtml = '<div class="arecontent"><div data-widget="tabs" class="m JD-stock">' + '<div class="mt">' + '    <ul class="tab">' + tabsHtml + '    </ul>' + '    <div class="stock-line"></div>' + '</div>' + itemsHtml + '</div></div>';
	  $(selector + " .text").after(provinceHtml);// 预先丰富html页面
	  var areaTabContainer = $(selector + " .JD-stock .tab li");// 选项卡dom
	  var areaItemContainer = $(selector + " .mc");// 内容区域dom

	  // ajax请求
	  function doAjax(code, callBack) {
		 code = code ? code : "0";
		 $.ajax({
			type : "post",
			url : $.basePath() + "/base/area",
			data : "code=" + code,
			dataType : "json",
			async : false,
			success : function(msgjsonobj) {
			   callBack.call(this, msgjsonobj);
			}
		 })
	  }

	  // 根据code获取名称
	  function getNameByCode(code, data) {
		 for ( var o in data) {
			if (data[o].CODE == code) {
			   return data[o].NAME;
			}
		 }
		 return "";
	  }

	  // 包装数据源
	  function getAreaList(result) {
		 var html = [ "<ul class='area-list'>" ];
		 var longhtml = [];
		 var longerhtml = [];
		 if (result && result.length > 0) {
			for (var i = 0, j = result.length; i < j; i++) {
			   result[i].NAME = result[i].NAME.replace(" ", "");
			   if (result[i].NAME.length > 12) {
				  longerhtml.push("<li class='longer-area'><a href='javascript:void(0)' data-value='" + result[i].CODE + "'>" + result[i].NAME + "</a></li>");
			   } else if (result[i].NAME.length > 5) {
				  longhtml.push("<li class='long-area'><a href='javascript:void(0)' data-value='" + result[i].CODE + "'>" + result[i].NAME + "</a></li>");
			   } else {
				  html.push("<li><a href='javascript:void(0)' data-value='" + result[i].CODE + "'>" + result[i].NAME + "</a></li>");
			   }
			}
		 } else {
			html.push("<li><a href='javascript:void(0)' data-value='" + currentAreaInfo.currentFid + "'> </a></li>");
		 }
		 html.push(longhtml.join(""));
		 html.push(longerhtml.join(""));
		 html.push("</ul>");
		 return html.join("");
	  }

	  // 翻译 currentAreaInfo对象
	  function getStockOpt() {
		 _selector.removeClass('hover');
		 var address = "";
		 for (var i = 0; i < maxTab && i < currentAreaInfo.currentLevel; i++) {
			address = address + currentAreaInfo.area[i].name;
		 }
		 $(selector + " .text div").html(address).attr("title", address);
		 _selector.bind("mouseout click", function() {
			//_selector.removeClass('hover');
			if (callback)
			   callback(currentAreaInfo);
		 });
	  }

	  // 绑定item内数据点击动作反应，即tab选项卡步进，item内容更新
	  // @param lv level序号
	  function bindClickTabContainers(index) {

		 areaTabContainer.eq(index).click(function() {
			page_load = false;
			_selector.unbind("mouseout");
			areaTabContainer.removeClass("curr");
			areaTabContainer.eq(index).addClass("curr").show();
			areaTabContainer.hide();
			for (var i = 0; i <= index; i++) {
			   areaTabContainer.eq(i).show();
			}
			areaItemContainer.hide();
			areaItemContainer.eq(index).show();
			// 处理数据
			var area_temp = [];
			for (var i = 0; i <= index; i++) {
			   area_temp.push(currentAreaInfo.area[i]);
			}
			currentAreaInfo = {};
			currentAreaInfo.area = area_temp;
			currentAreaInfo.currentLevel = index + 1;
		 })
	  }

	  // 步进处理下一级
	  function stepToNext() {
		 var lv = currentAreaInfo.currentLevel;
		 var index_ = lv - 1;
		 if (!page_load) {
			var area_temp = [];
			for (var i = 0; i <= index_; i++) {
			   area_temp.push(currentAreaInfo.area[i]);
			}
			currentAreaInfo = {};
			currentAreaInfo.area = area_temp;
		 }
		 currentAreaInfo.currentLevel = lv;
		 _selector.unbind("mouseout");
		 doAjax(currentAreaInfo.area[index_].code, function(data) {
			data = eval(data)
			if (data && data.length > 0 && lv < maxTab) {// 步进下一环，无数据或超出最大限制则回显信息

			   // 处理页面
			   areaTabContainer.eq(index_).removeClass("curr").find("em").html(currentAreaInfo.area[index_].name);
			   bindClickTabContainers(index_);
			   areaItemContainer.eq(index_).hide();
			   currentAreaInfo.currentLevel = lv + 1;// 控制步进
			   index_ = currentAreaInfo.currentLevel - 1;
			   areaTabContainer.eq(index_).addClass("curr").show().find("em").html("请选择");
			   areaItemContainer.eq(index_).show();
			   areaItemContainer.eq(index_).html(getAreaList(data));
			   areaItemContainer.eq(index_).find("a").click(function() {
				   if(currentAreaInfo.area.length>=currentAreaInfo.currentLevel){
					   currentAreaInfo.area.pop();
				   }
				  currentAreaInfo.area.push({
					 code : $(this).attr("data-value"),
					 name : $(this).text()
				  });
				  stepToNext();// 步进下一级
				 
			   });
			   if (page_load) { // 初始化加载
				  if (typeof (currentAreaInfo.area[index_]) == 'undefined') {
					 page_load = false;
				  } else {
					 var getname = getNameByCode(currentAreaInfo.area[index_].code, data);
					 if (typeof (getname) == 'undefined') {
						currentAreaInfo.currentLevel = currentAreaInfo.currentLevel - 1;
						getStockOpt();// 清理工作，回显信息
						return;
					 }
					 currentAreaInfo.area[index_].name = getname;
					 stepToNext();// 步进下一级
				  }
			   }
			} else {
			   getStockOpt();// 清理工作，回显信息
			   return;
			}
		 })
	  }

	  // 初始化当前地域信息
	  function CurrentAreaInfoInit(areaInfo) {

		 currentAreaInfo = {
			currentLevel : 1,
			area : [ {
			   "code" : "-1",
			   "name" : ""
			} ]
		 };

		 currentAreaInfo = $.extend(currentAreaInfo, areaInfo || {});
		 bindClickTabContainers(0);

		 var currentProvinceName = currentAreaInfo.area[0].name;
		 doAjax("0", function(data) {
			data = eval(data)
			if (data) {
			   currentProvinceName = getNameByCode(currentAreaInfo.area[0].code, data);
			   if (currentProvinceName == null || currentProvinceName == "") {
				  currentAreaInfo.area[0].name = "请选择";
			   } else {
				  currentAreaInfo.area[0].name = currentProvinceName;
				  stepToNext(1); // 步进下一级
			   }
			   areaItemContainer.eq(0).html(getAreaList(data));
			   // 初始绑定item中内容的点击事件，点击之后，page_load为false即通知后面的事件动作“已初始化”
			   areaItemContainer.eq(0).find("a").click(function() {
				  if (page_load) {
					 page_load = false;
				  }
				  currentAreaInfo.area[0].name = $(this).text();
				  currentAreaInfo.area[0].code = $(this).attr("data-value");
				  stepToNext();// 步进下一级
			   }).end();
			}
		 });
	  }

	  var page_load = true;// 标志是否初始化动作，true即未初始化
	  // 绑定地址输入框动作，即鼠标动作反应显示地址选择器
	  _selector.unbind("click").unbind("mouseout").bind("click", function() {
		 _selector.addClass('hover');
		 $(selector + " .content").show();
		 $(selector + " .JD-stock").show();
	  }).find("dl").remove();
	  
	  _selector.find(".arecontent").bind("mouseover", function() {
		  _selector.addClass('hover');
			 $(selector + " .content").show();
			 $(selector + " .JD-stock").show();
	  })
	  // 初始化，如隐藏域有值将取值处理
	  CurrentAreaInfoInit(areaInfo);
	  bindClickTabContainers(0);
   }

   // 地址选择框入口
   $.fn.initAddress = function() {
	  var callbackFun = arguments[0] ? arguments[0] : null;
	  var _addId = $(this).attr("id");
	  $(this).find(".arecontent").remove();
	  $(this).find(".text").remove();

	  if ($("#" + _addId + "_div").length > 0) {
		 $("#" + _addId + "_div").remove();
	  }
	  $('<div id="' + _addId + '_div" ></div>').appendTo("#" + _addId);
	  var province = "", city = "", district = "";
	  // 位权标志，以确定level值
	  var level_position = [ 0, 0, 0, 0, 0 ];
	  var level = 0;
	  $(this).find("input").each(function() {
		 if ($(this).attr("rel") == 'province') {
			province = $(this).val();
			if (province != null && province != '')
			   level_position[0] = 1;
		 }
		 if ($(this).attr("rel") == 'city') {
			city = $(this).val();
			if (city != null && city != '')
			   level_position[1] = 1;
		 }
		 if ($(this).attr("rel") == 'district') {
			district = $(this).val();
			if (district != null && district != '')
			   level_position[2] = 1;
		 }

	  })
	  for (var i = 0; i < level_position.length; i++) {

		 if (level_position[i] == 1) {
			level++;
		 } else {
			break;
		 }
	  }
	  var paramObj = {};
	  var areaTemp = [];
	  if (level >= 1) {
		 areaTemp.push({
			code : province
		 });
	  }
	  if (level >= 2) {
		 areaTemp.push({
			code : city
		 });
	  }
	  if (level >= 3) {
		 areaTemp.push({
			code : district
		 });
	  }
	  if (level > 0)
		 paramObj.area = areaTemp;
	  $("#" + _addId + "_div").place(paramObj, function(paramObj) {
		 if (paramObj.currentLevel >= 1) {
			$("#" + _addId).find("[rel='province']").val(paramObj.area[0].code);
		 }
		 if (paramObj.currentLevel >= 2) {
			$("#" + _addId).find("[rel='city']").val(paramObj.area[1].code);
		 }
		 if (paramObj.currentLevel >= 3) {
			$("#" + _addId).find("[rel='district']").val(paramObj.area[2].code);
		 }
		 if (callbackFun)
			callbackFun.call(this, paramObj);
	  })
   }

   // 地址选择框入口，不弹出地址下拉框，主要用于详情页面展示
   $.fn.initAddressNotChoice = function(obj, callbackFun) {
	  var _addId = $(this).attr("id");
	  $("#" + _addId).initAddress(obj, callbackFun);
	  $("#" + _addId).unbind("mouseover");
	  $("#" + _addId).unbind("mouseout");
   }

   // 清理地址选择器
   $.fn.removeAddress = function() {
	  $(this).children("div").remove();
   }

})(jQuery);