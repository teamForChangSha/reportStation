$(function() {

	var leftEle = {
		companys : $("#companys"),
		province : $("select[name=province]"),
		city : $("select[name=city]"),
		institutions : $("select[name=branchId]"),
		companyName : $("input[name=companyName]"),
		getArea : $("#getArea"),
		next : $("#next")
	};

	var rightEle = {
		trankingNo : $("input[name=trankingNo]"),
		accecCode : $("input[name=accecCode]"),
		getCaseInfoByNo : $("#getCaseInfoByNo"),
		mobile : $("input[name=mobile]"),
		getTempPwd : $("#getTempPwd"),
		tempPwd : $("input[name=tempPwd]"),
		getCaseInfoByPhone : $("#getCaseInfoByPhone")
	};

	var regStr = /(1[3-9]\d{9}$)/;
	var t, i = 60;

	/**
	 * 获取所有企业
	 */
	$.getJSON("getAllCompany.do", function(res) {
		if (res == null || res.length < 0)
			return;
		$.each(res, function(i, company) {
			var li = $("<li/>");
			var a = $("<a/>").attr("data-id", company.companyId).text(
					company.companyName);
			a.click(function() {
				hiddenEle();
				leftEle.companyName.val(this.innerHTML);
				leftEle.companyName.attr("data-id", $(this).attr("data-id"));
				hiddenErrorIcon(leftEle.companyName);
			});
			li.append(a);
			leftEle.companys.append(li);
		});
	});

	/**
	 * 初始化省份
	 */
	function initProvince() {
		var url = "getProvinceByCompany.do?companyId="
				+ leftEle.companyName.attr("data-id");
		$.getJSON(url, function(result) {
			if (result == null || result.length <= 0) {
				return;
			}
			leftEle.province.empty();
			var opt = $("<option/>").text("--请选择--").attr("value","-1");
			leftEle.province.append(opt);
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.name).attr("value",
								this.areaId);
						leftEle.province.append(opt);
					});
			leftEle.province.parent().removeClass("hidden");
		});
	}
	;

	/**
	 * 初始化市
	 */
	function initCity() {
		var url = "getCityByCompany.do?companyId="
				+ leftEle.companyName.attr("data-id") + "&parentId="
				+ leftEle.province.find("option:selected").val();
		$.getJSON(url, function(result) {
			if (result == null || result.length <= 0) {
				return;
			}
			leftEle.city.empty();
			var opt = $("<option/>").text("--请选择--").attr("value","-1");
			leftEle.city.append(opt);
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.name).attr("value",
								this.areaId);
						leftEle.city.append(opt);
					});
			leftEle.city.parent().removeClass("hidden");
		});
	}
	;

	/**
	 * 初始化机构
	 */
	function initInstitutions() {
		var url = "getCompanyBranch.do?companyId="
				+ leftEle.companyName.attr("data-id") + "&areaId="
				+ leftEle.city.find("option:selected").val();
		$.getJSON(url, function(result) {
			if (result == null || result.length <= 0) {
				return;
			}
			leftEle.institutions.empty();
			var opt = $("<option/>").text("--请选择--").attr("value","-1");
			leftEle.institutions.append(opt);
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.branchName).attr(
								"value", this.branchId);
						leftEle.institutions.append(opt);
					});
			leftEle.institutions.parent().removeClass("hidden");
		});
	}
	;

	/**
	 * 获取企业下的机构
	 */
	leftEle.getArea.click(function() {
		showErrorIcon(leftEle.companyName)
		initProvince();
	});

	/**
	 * 转到企业问题类型页面
	 */
	leftEle.next.click(function() {
		$("#sendCompany").submit();
	});

	leftEle.province.change(function() {
		leftEle.city.parent().addClass("hidden");
		leftEle.institutions.parent().addClass("hidden");
		leftEle.next.parent().addClass("hidden");
		if($(this).find("option:selected").val()!="-1"){
			initCity();
		}
	});

	leftEle.city.change(function() {
		leftEle.institutions.parent().addClass("hidden");
		leftEle.next.parent().addClass("hidden");
		if($(this).find("option:selected").val()!="-1"){
			initInstitutions();
		}
	});
	
	leftEle.institutions.change(function() {
		leftEle.next.parent().addClass("hidden");
		if($(this).find("option:selected").val()!="-1"){
			leftEle.next.parent().removeClass("hidden");
		}
	});

	leftEle.companyName.get(0).onkeyup = hiddenEle;
	hiddenErrorIcon(leftEle.companyName);
	hiddenErrorIcon(rightEle.trankingNo);
	hiddenErrorIcon(rightEle.accecCode);
	hiddenErrorIcon(rightEle.mobile);
	hiddenErrorIcon(rightEle.tempPwd);

	/**
	 * 隐藏企业机构控件
	 */
	function hiddenEle() {
		leftEle.companyName.removeAttr("data-id");
		leftEle.province.parent().addClass("hidden");
		leftEle.city.parent().addClass("hidden");
		leftEle.institutions.parent().addClass("hidden");
		leftEle.next.parent().addClass("hidden");
	}
	;

	rightEle.trankingNo.focus(function() {
		rightEle.accecCode.removeAttr("disabled");
		rightEle.getCaseInfoByNo.removeAttr("disabled");
		rightEle.getTempPwd.attr("disabled", "");
		rightEle.tempPwd.attr("disabled", "");
		rightEle.getCaseInfoByPhone.attr("disabled", "");
	});
	rightEle.mobile.focus(function() {
		rightEle.accecCode.attr("disabled", "");
		rightEle.getCaseInfoByNo.attr("disabled", "");
		rightEle.getTempPwd.removeAttr("disabled");
		rightEle.tempPwd.removeAttr("disabled");
		rightEle.getCaseInfoByPhone.removeAttr("disabled");
	});

	/**
	 * 通过案件编号查询
	 */
	rightEle.getCaseInfoByNo.click(function() {
		if (!showErrorIcon(rightEle.trankingNo)
				&& !showErrorIcon(rightEle.accecCode)) {
			$("#reportPanel").modal('hide');
			$("#selectByNum").submit();
		}
	});

	/**
	 * 获取临时密码
	 */
	rightEle.getTempPwd.click(function() {
		if (!reg(rightEle.mobile)) {
			return;
		}
		rightEle.getTempPwd.attr("disabled", true);
		rightEle.getTempPwd.html("(" + i + ")秒后重试");
		t = setInterval(function() {
			if (i == 0) {
				clearInterval(t);
				t = null;
				rightEle.getTempPwd.removeAttr("disabled");
				rightEle.getTempPwd.html("获取临时密码");
				i = 60;
			} else {
				rightEle.getTempPwd.attr("disabled", true);
				i--;
				rightEle.getTempPwd.html("(" + i + ")秒后重试");
			}
		}, 1000);
	});

	/**
	 * 通过实名手机号查询
	 */
	rightEle.getCaseInfoByPhone.click(function() {
		if (reg(rightEle.mobile) && !showErrorIcon(rightEle.tempPwd)) {
			clearInterval(t);
			t = null;
			rightEle.getTempPwd.removeAttr("disabled");
			rightEle.getTempPwd.html("获取临时密码");
			i = 60;
			$("#reportPanel").modal('hide');
			$("#selectByPhone").submit();
		}
	});

	/**
	 * 验证手机号
	 */
	function reg(ele) {
		if (!regStr.test(ele.val())) {
			ele.next().addClass("glyphicon-remove");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 显示错误icon
	 */
	function showErrorIcon(ele) {
		if (isEmty(ele.val())) {
			ele.next().addClass("glyphicon-remove");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 隐藏错误icon
	 */
	function hiddenErrorIcon(ele) {
		ele.focus(function() {
			ele.next().removeClass("glyphicon-remove");
		});
		ele.next().removeClass("glyphicon-remove");
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @returns {Boolean}
	 */
	function isEmty(str) {
		str = $.trim(str);
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
});