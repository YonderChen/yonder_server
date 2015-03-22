	//检验电子邮箱
	function isMail(email) {
		var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
        return reg.test(email);
	}
	
	//校验手机号码
	function isTelephone(number) {
	var reg = /^(1)[0-9]{10}$/;
    return reg.test(number);
	}
	
	//判断是否为正整数
	function isPositiveInteger(integer) {
		var reg = /^[1-9][0-9]*$/;
        return reg.test(integer);
	}
	
	//判断浮点数格式(2位小数)
	function isFloat(value) {
		var reg1 = /^[1-9][0-9]*(\.\d{1,2})?$/;
		var reg2 = /^[0-9]\.(\d{1,2})?$/;
        return reg1.test(value) || reg2.test(value);
	}
