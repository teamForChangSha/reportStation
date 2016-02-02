$(function() {
	var formEle = {
		reportType: $("input[name='reportType']"),
		name: $("#name"),
		surnames: $("#surnames"),
		certificate: $("#certificate"),
		certificateNum: $("#certificateNum"),
		phone: $("#phone"),
		getCode: $("#getCode"),
		code: $("#code"),
		email: $("#email")
	};

	var phoneReg = /(1[3-9]\d{9}$)/,
		emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
		passport = /(P\d{7})|(G\d{8})/,
		idCard = /^\d{15}|(\d{17}(\d|x|X))$/;

	$("#submitReport").click(function() {
		changeClass(formEle.name, true);
		changeClass(formEle.surnames, true);
		changeClass(formEle.code, true);
		regClass(formEle.phone, phoneReg);
		regClass(formEle.email, emailReg);
		changeSelectClass(formEle.certificate, true);
		certificateRegClass(formEle.certificateNum);
	});

	changeClass(formEle.name, false);
	changeClass(formEle.surnames, false);
	changeClass(formEle.certificateNum, false);
	changeClass(formEle.phone, false);
	changeClass(formEle.code, false);
	changeClass(formEle.email, false);
	changeSelectClass(formEle.certificate, false);

	function changeClass(ele, bool) {
		if (bool) {
			if (isEmty($.trim(ele.val()))) {
				ele.parent().addClass("has-error");
			}
		} else {
			ele.focus(function() {
				ele.parent().removeClass("has-error");
			});
		}
	}

	function regClass(ele, regStr) {
		if (!regStr.test($.trim(ele.val()))) {
			ele.parent().addClass("has-error");
			return true;
		}
	}

	function certificateRegClass(ele) {
		if (!passport.test($.trim(ele.val())) && !idCard.test($.trim(ele.val()))) {
			ele.parent().addClass("has-error");
		}
	}

	function changeSelectClass(ele, bool) {
		if (bool) {
			if (ele.get(0).options.selectedIndex == 0) {
				ele.parent().addClass("has-error");
			}
		} else {
			ele.change(function() {
				if (ele.get(0).options.selectedIndex != 0) {
					ele.parent().removeClass("has-error");
				} else {
					ele.parent().addClass("has-error");
				}
			});
		}
	}

	formEle.reportType.change(function() {
		if ($(this).val() == "true") {
			$("#userInfo").css("display", "none");
		} else {
			$("#userInfo").css("display", "block");
		}
	});
	
	formEle.getCode.click(function(){
		if (regClass(formEle.phone, phoneReg)) {
			return;
		}else{
			//TODO 发送获取验证码的请求
		}
	});
});

function isEmty(str) {
	if (str == null || str.length <= 0 || str == "") {
		return true;
	}
	return false;
}