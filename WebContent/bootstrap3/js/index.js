$(function() {
	var addSelOption = function(jq) {
		var opt = $("<option/>").text("请选择").attr("value", "-1");
		jq.append(opt);
	};

	var province = $("#seachprov"),
		city = $("#seachcity");

	function initProvince() {
		$.getJSON("bootstrap3/js/area.json", function(result) {
			province.empty();
			if (result.length == 0) {
				$(area_array).each(function(i) {
					if (area_array[i] != undefined) {
						var opt = $("<option/>").text(area_array[i]).attr("value", i);
						province.append(opt);
					}
				});
			} else {
				$(result).each(function() {
					var opt = $("<option/>").text(this.p).attr("value", this.p);
					province.append(opt);
				});
			}
			initCity();
		});
	};

	var initCity = function() {
		$.getJSON("bootstrap3/js/area.json", function(result) {
			city.empty();
			if (result.length == 0) {
				var index = province.val();
				$(sub_array[index]).each(function(i) {
					if (sub_array[index][i] != undefined) {
						var opt = $("<option/>").text(sub_array[index][i]).attr("value", i);
						city.append(opt);
					}
				});
			} else {
				$(result).each(function() {
					var opt = $("<option/>").text(this.p).attr("value", this.p);
					city.append(opt);
				});
			}
		});
	};

	var countries = function() {

	};

	province.change(function() {
		initCity();
	});





	var etpList = $("#etpList");
	var num = $("#num"),
		phone = $("#phone"),
		pwd = $("#pwd"),
		keyword = $("#keyword");
	var numIcon = $("#numIcon"),
		phoneIcon = $("#phoneIcon"),
		pwdIcon = $("#pwdIcon"),
		keywordIcon = $("#keywordIcon");
	var numBtn = $("#numBtn"),
		phoneBtn = $("#phoneBtn"),
		pwdBtn = $("#pwdBtn"),
		getArea = $("#getArea"),
		next = $("#next");
	var regStr = /(1[3-9]\d{9}$)/;
	var t, i = 60;

	changeClass(num, numIcon, false);
	changeClass(pwd, pwdIcon, false);
	changeClass(phone, phoneIcon, false);
	changeClass(keyword, keywordIcon, false);
	changeSelectClass(province, false);
	changeSelectClass(city, false);
	getEtpList();
	/**
	 * 判断手机号是否合法 
	 * @param {Object} inp
	 * @param {Object} iconId
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
	 * @param {Object} inp
	 * @param {Object} iconId
	 * @param {Object} bool
	 */
	function changeClass(ele, iconId, bool) {
		if (bool) {
			if ($.trim(ele.val()) == null || $.trim(ele.val()) == "" || $.trim(ele.val()).length <= 0) {
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

	keyword.keyup(function() {
		province.parent().addClass("hidden");
		city.parent().addClass("hidden");
		next.parent().addClass("hidden");
	});

	/**
	 * 根据单号匿名查询
	 */
	numBtn.click(function() {
		if (changeClass(num, numIcon, true)) {
			$("#reportPanel").modal('hide');
			//					$("form").submit();
			location.href = "userPages/report_info.html";
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
			//					$("form").submit();
			location.href = "userPages/report_list.html";
		}
	});
	/**
	 * 获取所有企业 
	 */
	function getEtpList() {
		etpList.css("display","none");
		$.getJSON("url", function(data) {
			etpList.css("display","block");
			$(data).each(function() {
				var li = $("<li/>");
				var a = $("<a/>").text(data.name).attr("href", "#");
				a.click(function() {
					keyword.val(this.innerHTML);
					changeClass(keyword, keywordIcon, false);
				});
				li.append(a)
				etpList.append(li);
			});
		});
	}
	/**
	 * 选择企业发送举报请求 
	 */
	getArea.click(function() {
		if (changeClass(keyword, keywordIcon, true)) {
			province.parent().removeClass("hidden");
			city.parent().removeClass("hidden");
			next.parent().removeClass("hidden");
			initProvince();
		}
	});
	next.click(function() {
		if (changeClass(keyword, keywordIcon, true) && changeSelectClass(province, true) && changeSelectClass(city, true)) {
			//					$("form").submit();
			console.log($("#seachprov  option:selected").text());
			location.href = "userPages/reportType.html";
		}
	});
	/**
	 * 跳转到找回密码页面 
	 */
	$("#forgetPwd").click(function() {
		$("#loginPanel").modal('hide');
	});
});