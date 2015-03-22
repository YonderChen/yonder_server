	function addBlankSelect(selectId, defaultValue, value, text) {
		var select = document.getElementById(selectId);
		var option = new Option("", "");
		select.options.add(option);
		for ( var i = select.length - 1; i > 0; i--) {
			select.options[i].value = select.options[i - 1].value;
			select.options[i].text = select.options[i - 1].text;
		}
		select.options[0].value = value;
		select.options[0].text = text;
		select.value = defaultValue;
		if (select.selectedIndex == -1) {
			select.value = defaultValue;
		}
	}
	
	function clearSelect(selectId) {
		var select = document.getElementById(selectId);
		select.length = 0;
	}
	
	
	function removeSelect(selectId, value) {
		var select = document.getElementById(selectId);
		for (var i = 0; i < select.length - 1; i++) {
			if (select.options[i].value == value) {
				select.options[i].value = select.options[i + 1].value;
				select.options[i].text = select.options[i + 1].text;
			}
		}
		select.length--;
	}

	function myArrayToString(array) {
		var values = "";
		for (var i = 0; i < array.length; i++) {
			values += array[i];
			if (i < array.length - 1) {
				values += ",";
			}
		}
		return values;
	}
	
	function myStringToArray(array, values) {
		if (values == "") {
			return;
		}
		var value = values.split(",");
		for (var i = 0; i < value.length; i++) {
			array.push(value[i]);
		}
	}
	
	function myContain(array, value) {
		for (var i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return true;
			}
		}
		return false;
	}
	
	function myRemove(array, value) {
		for (var i = 0; i < array.length; i++) {
			if (array[i] == value) {
				array.splice(i,1); //删除从指定位置deletePos开始的指定数量deleteCount的元素，数组形式返回所移除的元素
				break;
			}
		}
	}
	
	function goback() {
		window.history.go(-1);
	}