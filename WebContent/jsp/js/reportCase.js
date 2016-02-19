$(function() {
	/**
	 * 将表单转换成json对象
	 */
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};

	/**
	 * 实名用户表单控件及其他控件
	 */
	var userAndOther = {
		name : $("input[name=name]"),
		surnames : $("#surnames"),
		idName : $("select[name=idName]"),
		idNo : $("input[name=idNo]"),
		mobile : $("input[name=mobile]"),
		code : $("input[name=code]"),
		email : $("input[name=email]"),
		bestContact : $("textarea[name=bestContact]"),

		agreed : $("input[name=agreed]"),
		getCode : $("#getCode")
	};

	var questPanle = {
		quest_1 : $("#quest_1"),
		quest_2 : $("#quest_2"),
		quest_3 : $("#quest_3"),
		quest_4 : $("#quest_4"),
		quest_5 : $("#quest_5"),
		quest_6 : $("#quest_6"),
		quest_7 : $("#quest_7"),
		quest_8 : $("#quest_8"),
		quest_9 : $("#quest_9"),
		quest_10 : $("#quest_10"),
		quest_11 : $("#quest_11"),
		quest_12 : $("#quest_12"),
		quest_13 : $("#quest_13")
	};

	/**
	 * 问题表单控件
	 */
	var quest = {
		isEmployees : $("input[name=quest_1_value]"),
		isAnonymous : $("input[name='quest_2_value']:checked"),
		personName1 : $("input[name=quest_3_value_name1]"),
		personSurname1 : $("input[name='quest_3_value_surname1']"),
		personPosition1 : $("input[name=quest_3_value_position1]"),
		personName2 : $("input[name=quest_3_value_name2]"),
		personSurname2 : $("input[name='quest_3_value_surname2']"),
		personPosition2 : $("input[name=quest_3_value_position2]"),
		personName3 : $("input[name=quest_3_value_name3]"),
		personSurname3 : $("input[name='quest_3_value_surname3']"),
		personPosition3 : $("input[name=quest_3_value_position3]"),
		regulators : $("input[name='quest_4_value']"),
		regulators1 : $("textarea[name=quest_4_value1]"),
		admin : $("input[name='quest_5_value']"),
		characteristics : $("textarea[name=quest_6_value]"),
		valuation1 : $("select[name='quest_7_value1']"),
		valuation2 : $("select[name=quest_7_value2]"),
		address : $("textarea[name='quest_8_value']"),
		date : $("textarea[name=quest_9_value]"),
		duration : $("select[name='quest_10_value']"),
		way : $("select[name='quest_11_value1']"),
		way1 : $("textarea[name=quest_11_value2]"),
		isReport : $("input[name='quest_12_value']"),
		isReport2 : $("textarea[name=quest_12_value1]"),
		hidden : $("textarea[name='quest_13_value']"),
		details : $("textarea[name=quest_14_value]"),
		pass : $("input[name='quest_15_value']"),
		repass : $("input[name='quest_16_value']"),
		trackingNo : $("input[name='trackingNo']"),
		rtList : $("input[name='rtList']")
	};

	/**
	 * 隐藏的问题表单控件
	 */
	var send = {
		quest_1 : $("input[name=quest_1]"),
		quest_2 : $("input[name=quest_2]"),
		quest_3 : $("input[name=quest_3]"),
		quest_4 : $("input[name=quest_4]"),
		quest_5 : $("input[name=quest_5]"),
		quest_6 : $("input[name=quest_6]"),
		quest_7 : $("input[name=quest_7]"),
		quest_8 : $("input[name=quest_8]"),
		quest_9 : $("input[name=quest_9]"),
		quest_10 : $("input[name=quest_10]"),
		quest_11 : $("input[name=quest_11]"),
		quest_12 : $("input[name=quest_12]"),
		quest_13 : $("input[name=quest_13]")
	};

	var regStr = {
		phoneReg : /(1[3-9]\d{9}$)/,
		emailReg : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
		passport : /(P\d{7})|(G\d{8})/,
		idCard : /^\d{15}|(\d{17}(\d|x|X))$/
	};

	(function() {
		$.each(questionList, function(i, question) {
			var key = question.quest_key;
			switch (key) {
			case "quest_1":
				// TODO
				if (question.is_needed == 1) {
					$("#" + key).children("p:first");
				}
				$("#" + key).removeClass("hidden");
				break;
			case "quest_3":
			case "quest_6":
			case "quest_7":
			case "quest_8":
			case "quest_9":
			case "quest_11":
			case "quest_12":
			case "quest_13":
				$("#" + key).removeClass("hidden");
				$("#" + key).next().removeClass("hidden");
				break;
			case "quest_2":
				$("#" + key).removeClass("hidden");
				$("#" + key).next().removeClass("hidden");
				$("#" + key).next().next().removeClass("hidden");
				$("#" + key).next().next().next().removeClass("hidden");
				$("#" + key).next().next().next().next().removeClass("hidden");
				break;
			}
		});
	})();

	function notSend() {
		var list = [];
		$.each(questionList, function(i, question) {
			var data = {};
			data["questKey"] = question.quest_key;
			data["questValue"] = $("input[name=" + question.quest_key + "]")
					.val();
			list.unshift(data);
		});
		return JSON.stringify(list);
	}
	;

	$("#submitReport").click(function(e) {
		setSendForm();
		sendForm();
		// TODO
		// isAgreed(true);
		// changeClass(formEle.name, true);
		// changeClass(formEle.surnames, true);
		// changeClass(formEle.code, true);
		// regClass(formEle.phone, phoneReg);
		// regClass(formEle.email, emailReg);
		// changeSelectClass(formEle.certificate, true);
		// certificateRegClass(formEle.certificateNum);
	});

	/**
	 * 设置隐藏问题表单的数据
	 */
	function setSendForm() {
		send.quest_1.val(quest.isEmployees.val());
		var aboutUser = "";
		if (!isEmty(quest.personName1.val())) {
			aboutUser += quest.personName1.val() + ","
					+ quest.personSurname1.val() + ","
					+ quest.personPosition1.val();
		}
		if (!isEmty(quest.personName2.val())) {
			if (isEmty(aboutUser)) {
				aboutUser += quest.personName2.val() + ","
						+ quest.personSurname2.val() + ","
						+ quest.personPosition2.val();
			} else {
				aboutUser += "," + quest.personName2.val() + ","
						+ quest.personSurname2.val() + ","
						+ quest.personPosition2.val();
			}
		}
		if (!isEmty(quest.personName3.val())) {
			if (isEmty(aboutUser)) {
				aboutUser += quest.personName3.val() + ","
						+ quest.personSurname3.val() + ","
						+ quest.personPosition3.val();
			} else {
				aboutUser += "," + quest.personName3.val() + ","
						+ quest.personSurname3.val() + ","
						+ quest.personPosition3.val();
			}
		}
		send.quest_2.val(aboutUser);
		if (isEmty(quest.regulators1.val())) {
			send.quest_3.val(quest.regulators.val());
		} else {
			send.quest_3.val(quest.regulators.val() + ","
					+ quest.regulators1.val());
		}
		send.quest_4.val(quest.admin.val());
		send.quest_5.val(quest.characteristics.val());
		send.quest_6.val(quest.valuation1.find("option:selected").text() + ","
				+ quest.valuation2.find("option:selected").text());
		send.quest_7.val(quest.address.val());
		send.quest_8.val(quest.date.val());
		send.quest_9.val(quest.duration.find("option:selected").text());
		if (isEmty(quest.way1.val())) {
			send.quest_10.val(quest.way.find("option:selected").text());
		} else {
			send.quest_10.val(quest.way.find("option:selected").text() + ","
					+ quest.way1.val());
		}
		if (isEmty(quest.isReport2.val())) {
			send.quest_11.val(quest.isReport.val());
		} else {
			send.quest_11.val(quest.isReport.val() + ","
					+ quest.isReport2.val());
		}
		send.quest_12.val(quest.hidden.val());
		send.quest_13.val(quest.details.val());
	}
	;

	/**
	 * 合并表单数据发送请求
	 */
	function sendForm() {
		var url = "case/addCase.do";
		userAndOther.name.val(userAndOther.surnames.val()
				+ userAndOther.name.val());
		var reporter = "reporter="
				+ JSON.stringify($("#userInfo").serializeJson());
		var anonymous = "isAnonymous=" + quest.isAnonymous.val();
		var questions = "answers=" + notSend();
		var trackingNo = "trackingNo=" + quest.trackingNo.val();
		var accessCode = "accessCode=" + md5(quest.pass.val());
		var rtList = "rtList=" + quest.rtList.val();
		var data = reporter + "&" + anonymous + "&" + questions + "&"
				+ trackingNo + "&" + accessCode + "&" + rtList;

		$.post(url, data, function(data, status, xhr) {

		}, "json");
		console.log(data);
	}

	// TODO
	/*
	 * isAgreed(false); changeClass(formEle.name, false);
	 * changeClass(formEle.surnames, false); changeClass(formEle.certificateNum,
	 * false); changeClass(formEle.phone, false); changeClass(formEle.code,
	 * false); changeClass(formEle.email, false);
	 * changeSelectClass(formEle.certificate, false);
	 */

	function isAgreed(bool) {
		if (bool) {
			if (!userAndOther.agreed.is(':checked')) {
				userAndOther.agreed.next().addClass("has-error");
				return;
			}
		} else {
			userAndOther.agreed.change(function() {
				userAndOther.agreed.next().removeClass("has-error");
			});
		}
	}

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
		if (!passport.test($.trim(ele.val()))
				&& !idCard.test($.trim(ele.val()))) {
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

	quest.isAnonymous.change(function() {
		if ($(this).val() == "true") {
			$("#userInfo").addClass("hidden");
		} else {
			$("#userInfo").removeClass("hidden");
		}
	});

	userAndOther.getCode.click(function() {
		if (regClass(userAndOther.phone, phoneReg)) {
			return;
		} else {
			// TODO 发送获取验证码的请求
		}
	});
});

/**
 * 判断是否为空
 * 
 * @param str
 * @returns {Boolean}
 */
function isEmty(str) {
	if (str == null || str.length <= 0 || str == "") {
		return true;
	}
	return false;
}

/**
 * MD5加密
 * 
 * @param {Object}
 *            string
 */
function md5(string) {
	function md5_RotateLeft(lValue, iShiftBits) {
		return (lValue << iShiftBits) | (lValue >>> (32 - iShiftBits));
	}

	function md5_AddUnsigned(lX, lY) {
		var lX4, lY4, lX8, lY8, lResult;
		lX8 = (lX & 0x80000000);
		lY8 = (lY & 0x80000000);
		lX4 = (lX & 0x40000000);
		lY4 = (lY & 0x40000000);
		lResult = (lX & 0x3FFFFFFF) + (lY & 0x3FFFFFFF);
		if (lX4 & lY4) {
			return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
		}
		if (lX4 | lY4) {
			if (lResult & 0x40000000) {
				return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
			} else {
				return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
			}
		} else {
			return (lResult ^ lX8 ^ lY8);
		}
	}

	function md5_F(x, y, z) {
		return (x & y) | ((~x) & z);
	}

	function md5_G(x, y, z) {
		return (x & z) | (y & (~z));
	}

	function md5_H(x, y, z) {
		return (x ^ y ^ z);
	}

	function md5_I(x, y, z) {
		return (y ^ (x | (~z)));
	}

	function md5_FF(a, b, c, d, x, s, ac) {
		a = md5_AddUnsigned(a, md5_AddUnsigned(md5_AddUnsigned(md5_F(b, c, d),
				x), ac));
		return md5_AddUnsigned(md5_RotateLeft(a, s), b);
	}
	;

	function md5_GG(a, b, c, d, x, s, ac) {
		a = md5_AddUnsigned(a, md5_AddUnsigned(md5_AddUnsigned(md5_G(b, c, d),
				x), ac));
		return md5_AddUnsigned(md5_RotateLeft(a, s), b);
	}
	;

	function md5_HH(a, b, c, d, x, s, ac) {
		a = md5_AddUnsigned(a, md5_AddUnsigned(md5_AddUnsigned(md5_H(b, c, d),
				x), ac));
		return md5_AddUnsigned(md5_RotateLeft(a, s), b);
	}
	;

	function md5_II(a, b, c, d, x, s, ac) {
		a = md5_AddUnsigned(a, md5_AddUnsigned(md5_AddUnsigned(md5_I(b, c, d),
				x), ac));
		return md5_AddUnsigned(md5_RotateLeft(a, s), b);
	}
	;

	function md5_ConvertToWordArray(string) {
		var lWordCount;
		var lMessageLength = string.length;
		var lNumberOfWords_temp1 = lMessageLength + 8;
		var lNumberOfWords_temp2 = (lNumberOfWords_temp1 - (lNumberOfWords_temp1 % 64)) / 64;
		var lNumberOfWords = (lNumberOfWords_temp2 + 1) * 16;
		var lWordArray = Array(lNumberOfWords - 1);
		var lBytePosition = 0;
		var lByteCount = 0;
		while (lByteCount < lMessageLength) {
			lWordCount = (lByteCount - (lByteCount % 4)) / 4;
			lBytePosition = (lByteCount % 4) * 8;
			lWordArray[lWordCount] = (lWordArray[lWordCount] | (string
					.charCodeAt(lByteCount) << lBytePosition));
			lByteCount++;
		}
		lWordCount = (lByteCount - (lByteCount % 4)) / 4;
		lBytePosition = (lByteCount % 4) * 8;
		lWordArray[lWordCount] = lWordArray[lWordCount]
				| (0x80 << lBytePosition);
		lWordArray[lNumberOfWords - 2] = lMessageLength << 3;
		lWordArray[lNumberOfWords - 1] = lMessageLength >>> 29;
		return lWordArray;
	}
	;

	function md5_WordToHex(lValue) {
		var WordToHexValue = "", WordToHexValue_temp = "", lByte, lCount;
		for (lCount = 0; lCount <= 3; lCount++) {
			lByte = (lValue >>> (lCount * 8)) & 255;
			WordToHexValue_temp = "0" + lByte.toString(16);
			WordToHexValue = WordToHexValue
					+ WordToHexValue_temp.substr(
							WordToHexValue_temp.length - 2, 2);
		}
		return WordToHexValue;
	}
	;

	function md5_Utf8Encode(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for ( var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
		}
		return utftext;
	}
	;
	var x = Array();
	var k, AA, BB, CC, DD, a, b, c, d;
	var S11 = 7, S12 = 12, S13 = 17, S14 = 22;
	var S21 = 5, S22 = 9, S23 = 14, S24 = 20;
	var S31 = 4, S32 = 11, S33 = 16, S34 = 23;
	var S41 = 6, S42 = 10, S43 = 15, S44 = 21;
	string = md5_Utf8Encode(string);
	x = md5_ConvertToWordArray(string);
	a = 0x67452301;
	b = 0xEFCDAB89;
	c = 0x98BADCFE;
	d = 0x10325476;
	for (k = 0; k < x.length; k += 16) {
		AA = a;
		BB = b;
		CC = c;
		DD = d;
		a = md5_FF(a, b, c, d, x[k + 0], S11, 0xD76AA478);
		d = md5_FF(d, a, b, c, x[k + 1], S12, 0xE8C7B756);
		c = md5_FF(c, d, a, b, x[k + 2], S13, 0x242070DB);
		b = md5_FF(b, c, d, a, x[k + 3], S14, 0xC1BDCEEE);
		a = md5_FF(a, b, c, d, x[k + 4], S11, 0xF57C0FAF);
		d = md5_FF(d, a, b, c, x[k + 5], S12, 0x4787C62A);
		c = md5_FF(c, d, a, b, x[k + 6], S13, 0xA8304613);
		b = md5_FF(b, c, d, a, x[k + 7], S14, 0xFD469501);
		a = md5_FF(a, b, c, d, x[k + 8], S11, 0x698098D8);
		d = md5_FF(d, a, b, c, x[k + 9], S12, 0x8B44F7AF);
		c = md5_FF(c, d, a, b, x[k + 10], S13, 0xFFFF5BB1);
		b = md5_FF(b, c, d, a, x[k + 11], S14, 0x895CD7BE);
		a = md5_FF(a, b, c, d, x[k + 12], S11, 0x6B901122);
		d = md5_FF(d, a, b, c, x[k + 13], S12, 0xFD987193);
		c = md5_FF(c, d, a, b, x[k + 14], S13, 0xA679438E);
		b = md5_FF(b, c, d, a, x[k + 15], S14, 0x49B40821);
		a = md5_GG(a, b, c, d, x[k + 1], S21, 0xF61E2562);
		d = md5_GG(d, a, b, c, x[k + 6], S22, 0xC040B340);
		c = md5_GG(c, d, a, b, x[k + 11], S23, 0x265E5A51);
		b = md5_GG(b, c, d, a, x[k + 0], S24, 0xE9B6C7AA);
		a = md5_GG(a, b, c, d, x[k + 5], S21, 0xD62F105D);
		d = md5_GG(d, a, b, c, x[k + 10], S22, 0x2441453);
		c = md5_GG(c, d, a, b, x[k + 15], S23, 0xD8A1E681);
		b = md5_GG(b, c, d, a, x[k + 4], S24, 0xE7D3FBC8);
		a = md5_GG(a, b, c, d, x[k + 9], S21, 0x21E1CDE6);
		d = md5_GG(d, a, b, c, x[k + 14], S22, 0xC33707D6);
		c = md5_GG(c, d, a, b, x[k + 3], S23, 0xF4D50D87);
		b = md5_GG(b, c, d, a, x[k + 8], S24, 0x455A14ED);
		a = md5_GG(a, b, c, d, x[k + 13], S21, 0xA9E3E905);
		d = md5_GG(d, a, b, c, x[k + 2], S22, 0xFCEFA3F8);
		c = md5_GG(c, d, a, b, x[k + 7], S23, 0x676F02D9);
		b = md5_GG(b, c, d, a, x[k + 12], S24, 0x8D2A4C8A);
		a = md5_HH(a, b, c, d, x[k + 5], S31, 0xFFFA3942);
		d = md5_HH(d, a, b, c, x[k + 8], S32, 0x8771F681);
		c = md5_HH(c, d, a, b, x[k + 11], S33, 0x6D9D6122);
		b = md5_HH(b, c, d, a, x[k + 14], S34, 0xFDE5380C);
		a = md5_HH(a, b, c, d, x[k + 1], S31, 0xA4BEEA44);
		d = md5_HH(d, a, b, c, x[k + 4], S32, 0x4BDECFA9);
		c = md5_HH(c, d, a, b, x[k + 7], S33, 0xF6BB4B60);
		b = md5_HH(b, c, d, a, x[k + 10], S34, 0xBEBFBC70);
		a = md5_HH(a, b, c, d, x[k + 13], S31, 0x289B7EC6);
		d = md5_HH(d, a, b, c, x[k + 0], S32, 0xEAA127FA);
		c = md5_HH(c, d, a, b, x[k + 3], S33, 0xD4EF3085);
		b = md5_HH(b, c, d, a, x[k + 6], S34, 0x4881D05);
		a = md5_HH(a, b, c, d, x[k + 9], S31, 0xD9D4D039);
		d = md5_HH(d, a, b, c, x[k + 12], S32, 0xE6DB99E5);
		c = md5_HH(c, d, a, b, x[k + 15], S33, 0x1FA27CF8);
		b = md5_HH(b, c, d, a, x[k + 2], S34, 0xC4AC5665);
		a = md5_II(a, b, c, d, x[k + 0], S41, 0xF4292244);
		d = md5_II(d, a, b, c, x[k + 7], S42, 0x432AFF97);
		c = md5_II(c, d, a, b, x[k + 14], S43, 0xAB9423A7);
		b = md5_II(b, c, d, a, x[k + 5], S44, 0xFC93A039);
		a = md5_II(a, b, c, d, x[k + 12], S41, 0x655B59C3);
		d = md5_II(d, a, b, c, x[k + 3], S42, 0x8F0CCC92);
		c = md5_II(c, d, a, b, x[k + 10], S43, 0xFFEFF47D);
		b = md5_II(b, c, d, a, x[k + 1], S44, 0x85845DD1);
		a = md5_II(a, b, c, d, x[k + 8], S41, 0x6FA87E4F);
		d = md5_II(d, a, b, c, x[k + 15], S42, 0xFE2CE6E0);
		c = md5_II(c, d, a, b, x[k + 6], S43, 0xA3014314);
		b = md5_II(b, c, d, a, x[k + 13], S44, 0x4E0811A1);
		a = md5_II(a, b, c, d, x[k + 4], S41, 0xF7537E82);
		d = md5_II(d, a, b, c, x[k + 11], S42, 0xBD3AF235);
		c = md5_II(c, d, a, b, x[k + 2], S43, 0x2AD7D2BB);
		b = md5_II(b, c, d, a, x[k + 9], S44, 0xEB86D391);
		a = md5_AddUnsigned(a, AA);
		b = md5_AddUnsigned(b, BB);
		c = md5_AddUnsigned(c, CC);
		d = md5_AddUnsigned(d, DD);
	}
	return (md5_WordToHex(a) + md5_WordToHex(b) + md5_WordToHex(c) + md5_WordToHex(d))
			.toLowerCase();
}