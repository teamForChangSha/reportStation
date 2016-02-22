﻿$(function() {

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
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.name).attr("value",
								this.areaId);
						leftEle.province.append(opt);
					});
			leftEle.province.parent().removeClass("hidden");
			initCity();
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
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.name).attr("value",
								this.areaId);
						leftEle.city.append(opt);
					});
			leftEle.city.parent().removeClass("hidden");
			initInstitutions();
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
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.branchName).attr(
								"value", this.branchId);
						leftEle.institutions.append(opt);
					});
			leftEle.institutions.parent().removeClass("hidden");
			leftEle.next.parent().removeClass("hidden");
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
		initCity();
	});

	leftEle.city.change(function() {
		leftEle.institutions.parent().addClass("hidden");
		initInstitutions();
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

});