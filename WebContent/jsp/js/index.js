$(function() {
	var etpList = $("#etpList");
	$.getJSON("getAllCompany.do", function(res) {
		if (res == null || res.length < 0)
			return;
		$.each(res, function(i, company) {
			console.log(company.companyName);
			var li = $("<li/>");
			var a = $("<a/>").attr("data-id", company.companyId).text(
					company.companyName);
			a.click(function() {
				hiddenEle();
				keyword.val(this.innerHTML);
				companyId.val($(this).attr("data-id"));
				changeClass(keyword, keywordIcon, false);
			});
			li.append(a);
			etpList.append(li);
		});
	});
	
	var addSelOption = function(jq) {
		var opt = $("<option/>").text("请选择").attr("value", "-1");
		jq.append(opt);
	};

	var province = $("#seachprov"), city = $("#seachcity"), institutions = $("#institutions"), companyId = $("input[name=companyId]");

	function initProvince() {
		var url = "getProvinceByCompany.do?companyId=" + companyId.val();
		$.getJSON(url, function(result) {
			if (result == null || result.length < 0) {
				return;
			}
			province.empty();
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.name).attr("value",
								this.areaId);
						province.append(opt);
					});
			initCity();
		});
	}
	;

	var initCity = function() {
		var url = "getCityByCompany.do?companyId=" + companyId.val()
				+ "&parentId=" + $("#seachprov  option:selected").val();
		$.getJSON(url, function(result) {
			if (result == null || result.length < 0) {
				return;
			}
			city.empty();
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.name).attr("value",
								this.areaId);
						city.append(opt);
					});
			city.parent().removeClass("hidden");
			initInstitutions();
		});
	};

	var initInstitutions = function() {
		var url = "getCompanyBranch.do?companyId=" + companyId.val()
				+ "&areaId=" + $("#seachcity  option:selected").val();
		$.getJSON(url, function(result) {
			if (result == null || result.length < 0) {
				return;
			}
			institutions.empty();
			$(result).each(
					function() {
						var opt = $("<option/>").text(this.branchName).attr(
								"value", this.branchId);
						institutions.append(opt);
					});
			institutions.parent().removeClass("hidden");
			next.parent().removeClass("hidden");
		});
	};

	var setData = function(data, ele) {
		ele.empty();
		$(data).each(
				function() {
					var opt = $("<option/>").text(this.name).attr("value",
							this.areaId);
					ele.append(opt);
				});
	}

	province.change(function() {
		initCity();
	});

	city.change(function() {
		initInstitutions();
	});

	var num = $("#num"), phone = $("#phone"), pwd = $("#pwd"), keyword = $("#keyword");
	var numIcon = $("#numIcon"), phoneIcon = $("#phoneIcon"), pwdIcon = $("#pwdIcon"), keywordIcon = $("#keywordIcon");
	var numBtn = $("#numBtn"), phoneBtn = $("#phoneBtn"), pwdBtn = $("#pwdBtn"), getArea = $("#getArea"), next = $("#next");
	var regStr = /(1[3-9]\d{9}$)/;
	var t, i = 60;

	changeClass(num, numIcon, false);
	changeClass(pwd, pwdIcon, false);
	changeClass(phone, phoneIcon, false);
	changeClass(keyword, keywordIcon, false);
	changeSelectClass(province, false);
	changeSelectClass(city, false);
	/**
	 * 判断手机号是否合法
	 * 
	 * @param {Object}
	 *            inp
	 * @param {Object}
	 *            iconId
	 */
	function reg(ele, iconId) {
		if (!regStr.test($.trim(ele.val()))) {
			iconId.addClass("glyphicon-remove");
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 判断是否为空
	 * 
	 * @param {Object}
	 *            inp
	 * @param {Object}
	 *            iconId
	 * @param {Object}
	 *            bool
	 */
	function changeClass(ele, iconId, bool) {
		if (bool) {
			if ($.trim(ele.val()) == null || $.trim(ele.val()) == ""
					|| $.trim(ele.val()).length <= 0) {
				iconId.addClass("glyphicon-remove");
				return false;
			} else {
				return true;
			}
		} else {
			ele.focus(function() {
				iconId.removeClass("glyphicon-remove");
				return;
			});
			iconId.removeClass("glyphicon-remove");
		}
	}

	function changeSelectClass(ele, bool) {
		if (bool) {
			if (ele.val() == "-1" || ele.val() == "0") {
				ele.parent().addClass("has-error");
				return false;
			} else {
				return true;
			}
		} else {
			ele.change(function() {
				if (ele.val() != "-1" && ele.val() != "0") {
					ele.parent().removeClass("has-error");
				} else {
					ele.parent().addClass("has-error");
				}
			});
		}
	}

	keyword.get(0).onkeyup = hiddenEle;
	
	function hiddenEle() {
		province.parent().addClass("hidden");
		city.parent().addClass("hidden");
		institutions.parent().addClass("hidden");
		next.parent().addClass("hidden");
	};

	/**
	 * 根据单号匿名查询
	 */
	numBtn.click(function() {
		if (changeClass(num, numIcon, true)) {
			$("#reportPanel").modal('hide');
			 $("#selectByNum").submit();
//			location.href = "userPages/report_info.html";
		}
	});
	/**
	 * 获取临时密码
	 */
	phoneBtn.click(function() {
		if (!reg(phone, phoneIcon)) {
			return;
		}
		phoneBtn.attr("disabled", true);
		phoneBtn.html("(" + i + ")秒后重试");
		t = setInterval(function() {
			if (i == 0) {
				clearInterval(t);
				t = null;
				phoneBtn.removeAttr("disabled");
				phoneBtn.html("获取临时密码");
				i = 60;
			} else {
				phoneBtn.attr("disabled", true);
				i--;
				phoneBtn.html("(" + i + ")秒后重试");
			}
		}, 1000);
	})
	/**
	 * 发送查询请求
	 */
	pwdBtn.click(function() {
		if (reg(phone, phoneIcon) && changeClass(pwd, pwdIcon, true)) {
			clearInterval(t);
			t = null;
			phoneBtn.removeAttr("disabled");
			phoneBtn.html("获取临时密码");
			i = 60;
			$("#reportPanel").modal('hide');
			 $("#selectByPhone").submit();
			 
		}
	});
	/**
	 * 选择企业发送举报请求
	 */
	getArea.click(function() {
		if (changeClass(keyword, keywordIcon, true)) {
			province.parent().removeClass("hidden");
			initProvince();
		}
	});
	next.click(function() {
		if (changeClass(keyword, keywordIcon, true)
				&& changeSelectClass(province, true)
				&& changeSelectClass(city, true)) {
			$("#sendCompany").submit();
		}
	});
	/**
	 * 跳转到找回密码页面
	 */
	$("#forgetPwd").click(function() {
		$("#loginPanel").modal('hide');
	});
});