/**
 * 获取数据字典
 */
function getDictionaryData(param) {
	
	var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var contextPath = pathName.substr(0,index+1);
	$.ajax({
		url :contextPath+"/dataDictionary/onLoadGetDictionary",
		type : 'post',
		contentType : "application/json",
		dataType : 'json',
		timeout : 1000 * 60,
		data : JSON.stringify(param),
		success : function(data, textStatus) {
			for (var i in data) {
				//获取下拉框节点
				var parentNode = document
						.getElementById(data[i].rendToId);
				var valueList = data[i].list;
				for (j in valueList) {
					var option = document.createElement('option');
					// 设置默认值
					if (valueList[j].code == data[i].defaultValue) {
						option.selected = "selected";
					}
					option.value = valueList[j].code;
					option.innerHTML = valueList[j].name;
					parentNode.appendChild(option);
				}
			}
		}
	});
};