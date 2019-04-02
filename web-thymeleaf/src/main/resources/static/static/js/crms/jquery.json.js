
(function($){
	/**
	 * json转对象
	 */
	 $.jsonObjToStr=function(jsonObj){
		return  JSON.stringify(jsonObj) ;
	 }
	 
	 /**
	  * 对象转json
	  */
	 $.jsonStrToObj=function(jsonStr){
			  var dataObj = eval("("+jsonStr+")");  ;
			  return dataObj;
	 }
	 
	 /**
	  * 重写form serialize 方法对于checkbox 可识别多个值
	  */
	 $.fn.serializeJson=function(){
	 	var serializeObj={};
	 	var array=this.serializeArray();
	 	var str=this.serialize();
	 	$(array).each(function(){
	 		if(serializeObj[this.name]){
	 			if($.isArray(serializeObj[this.name])){
	 				serializeObj[this.name].push(this.value);
	 			}else{
	 				serializeObj[this.name]=[serializeObj[this.name],this.value];
	 			}
	 		}else{
	 			serializeObj[this.name]=this.value;	
	 		}
	 	});
	 	return serializeObj;
	 };
})(jQuery);

/**
 * JSON 对象合并
 * @author 龙艺 2015-05-12
 * @param _json1 基准对象
 * @param _json2 合并对象
 * @param _cover 是否覆盖基准对象同属性值（可选，不传默认 false）
 * @returns
 */
function jsonMerge(_json1,_json2){
	var _cover=arguments[2] ? arguments[2] : false;
	for(var x in _json2){
		if(_cover!=true && !isEmpty(_json1[x])){ //不覆盖
			continue;
		}else{
			_json1[x]=_json2[x];
		}
	}
	return _json1;
}
/**
 * JSON 对象是否相等
 * @author 龙艺 2015-05-12
 * @param _o1 JSON对象1
 * @param _o2 JSON对象2
 * @returns true 相等，false 不相等
 */
function compareJsonObject(o1, o2) {
	if (typeof o1 != typeof o2)
		return false;
	if (typeof o1 == 'object') {
		var p=0,z=0;
		for ( var o in o1) {
			if (typeof o2[o] == 'undefined')
				return false;
			if (!compareJsonObject(o1[o], o2[o]))
				return false;
			p++;
		}
		for (var o in o2) {
			z++;
		}
		if(p!=z){
			return false;
		}
		return true;
	} else {
		return o1 === o2;
	}
}

