$(function() {

	// 初始化控件，清除缓存
	$("input[type=text]").val("");
	$("input[type=checkbox]").removeAttr("checked");
	$("input[type=radio]").removeAttr("checked");
	$("select").find("option[value='-1']").attr("selected", true);
	$("textarea").val("");

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
		reporterId : $("input[name=reporterId]"),
		name : $("input[name=name]"),
		idName : $("select[name=idName]"),
		idNo : $("input[name=idNo]"),
		mobile : $("input[name=mobile]"),
		code : $("input[name=verifyCode]"),
		email : $("input[name=email]"),
		bestContact : $("textarea[name=bestContact]"),

		agreed : $("input[name=agreed]"),
		getCode : $("#getCode")
	};

	/**
	 * 所有问题面板
	 */
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
		// TODO
		isEmployees : $("input[name='quest_1_value']"),
		isAnonymous : $("input[name='quest_2_value']"),
		personName1 : $("input[name='quest_3_value_name1']"),
		personPosition1 : $("input[name='quest_3_value_position1']"),
		personName2 : $("input[name='quest_3_value_name2']"),
		personPosition2 : $("input[name='quest_3_value_position2']"),
		personName3 : $("input[name='quest_3_value_name3']"),
		personPosition3 : $("input[name='quest_3_value_position3']"),
		regulators : $("input[name='quest_4_value']"),
		regulators1 : $("textarea[name='quest_4_value1']"),
		admin : $("input[name='quest_5_value']"),
		characteristics : $("textarea[name='quest_6_value']"),
		valuation1 : $("select[name='quest_7_value1']"),
		valuation2 : $("select[name='quest_7_value2']"),
		address : $("textarea[name='quest_8_value']"),
		date : $("textarea[name='quest_9_value']"),
		duration : $("select[name='quest_10_value']"),
		way : $("select[name='quest_11_value1']"),
		way1 : $("textarea[name='quest_11_value2']"),
		isReport : $("input[name='quest_12_value']"),
		isReport2 : $("textarea[name='quest_12_value1']"),
		hidden : $("textarea[name='quest_13_value']"),
		details : $("textarea[name='quest_14_value']"),
		pass : $("input[name='quest_15_value']"),
		repass : $("input[name='quest_16_value']")
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
		passport : /^[a-zA-Z0-9]{5,17}$/,
		idCard : /^\d{15}|(\d{17}(\d|x|X))$/
	};

	/**
	 * 控制页面需要显示的问题列表并设置是否为必填
	 */
	(function() {
		$.each(questionList, function(i, question) {
			var key = question.quest_key;
			var span = $("<span/>").attr("class", "xinghao").text("*");
			if (question.is_needed == 1) {
				$("#" + key).find("strong").prepend(span);
			}
			switch (key) {
			case "quest_1":
				$("#" + key).removeClass("hidden");
				break;
			case "quest_2":
				$("#" + key).removeClass("hidden");
				$("#" + key).next().removeClass("hidden");
				$("#" + key).next().next().removeClass("hidden");
				$("#" + key).next().next().next().removeClass("hidden");
				$("#" + key).next().next().next().next().removeClass("hidden");
				break;
			case "quest_3":
			case "quest_4":
			case "quest_9":
				$("#" + key).removeClass("hidden");
				break;
			case "quest_5":
			case "quest_6":
			case "quest_7":
			case "quest_8":
			case "quest_10":
			case "quest_11":
			case "quest_12":
			case "quest_13":
				$("#" + key).removeClass("hidden");
				$("#" + key).next().removeClass("hidden");
				break;
			}
		});
	})();

	/**
	 * 设置需要发送的问题列表
	 */
	function setAnswers() {
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
		if (!isAgreed(true)) {
			return;
		}
		if (!showRadioError(quest.isAnonymous)) {
			return;
		}
		if (quest.isAnonymous.filter(':checked').val() == "false") {
			if (!validationUser()) {
				return;
			}
		}
		if (!validationQuest()) {
			return;
		}
		if (!validationPass()) {
			return;
		}
		setSendForm();
		sendForm();
	});

	/**
	 * 设置隐藏问题表单的数据
	 */
	function setSendForm() {
		if (quest.isEmployees.filter(':checked').val() != undefined) {
			send.quest_1.val(quest.isEmployees.filter(':checked').val());
		}
		var aboutUser = "";
		if (!isEmty(quest.personName1.val())) {
			aboutUser += "姓名：" + quest.personName1.val() + "," + "职衔："
					+ quest.personPosition1.val();
		}
		if (!isEmty(quest.personName2.val())) {
			if (isEmty(aboutUser)) {
				aboutUser += "姓名：" + quest.personName2.val() + "," + "职衔："
						+ quest.personPosition2.val();
			} else {
				aboutUser += "," + "姓名：" + quest.personName2.val() + ","
						+ "职衔：" + quest.personPosition2.val();
			}
		}
		if (!isEmty(quest.personName3.val())) {
			if (isEmty(aboutUser)) {
				aboutUser += "姓名：" + quest.personName3.val() + "," + "职衔："
						+ quest.personPosition3.val();
			} else {
				aboutUser += "," + "姓名：" + quest.personName3.val() + ","
						+ "职衔：" + quest.personPosition3.val();
			}
		}
		send.quest_2.val(aboutUser);
		if (quest.regulators.filter(':checked').val() != undefined) {
			if (isEmty(quest.regulators1.val())) {
				send.quest_3.val(quest.regulators.filter(':checked').val());
			} else {
				send.quest_3.val(quest.regulators.filter(':checked').val()
						+ "," + quest.regulators1.val());
			}
		}
		if (quest.admin.filter(':checked').val() != undefined) {
			send.quest_4.val(quest.admin.filter(':checked').val());
		}
		send.quest_5.val(quest.characteristics.val());
		if (quest.valuation1.find("option:selected").val() != "-1") {
			send.quest_6.val(quest.valuation1.find("option:selected").text()
					+ "," + quest.valuation2.find("option:selected").text());
		}
		send.quest_7.val(quest.address.val());
		send.quest_8.val(quest.date.val());
		if (quest.duration.find("option:selected").val() != "-1") {
			send.quest_9.val(quest.duration.find("option:selected").text());
		}
		if (quest.way.find("option:selected").val() != "-1") {
			if (isEmty(quest.way1.val())) {
				send.quest_10.val(quest.way.find("option:selected").text());
			} else {
				send.quest_10.val(quest.way.find("option:selected").text()
						+ "," + quest.way1.val());
			}
		}
		if (quest.isReport.filter(':checked').val() != undefined) {
			if (isEmty(quest.isReport2.val())) {
				send.quest_11.val(quest.isReport.filter(':checked').val());
			} else {
				send.quest_11.val(quest.isReport.filter(':checked').val() + ","
						+ quest.isReport2.val());
			}
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
		var reporter = "reporter="
				+ JSON.stringify($("#userInfo").serializeJson());
		var verifyCode = "verifyCode=" + userAndOther.code.val();
		var anonymous = "isAnonymous="
				+ quest.isAnonymous.filter(':checked').val();
		var questions = "answers=" + setAnswers();
		var accessCode = "accessCode=" + quest.pass.val();
		var data = reporter + "&" + verifyCode + "&" + anonymous + "&"
				+ questions + "&" + trackingNo + "&" + accessCode + "&"
				+ rtList;
		$.post(url, data, function(res, status) {
			console.log("data:" + res + "status：" + status);
			if (status == "success") {
				if (res == "saveError") { // 案件提交失败
					return alert("举报信息提交失败，请重试!");
				}
				if (res == "checkError") { // 实名手机验证失败
					return alert("实名手机验证失败!");
				}
				location.href = "jsp/pages/reportSuccess.jsp";
			}
		});
	}

	/**
	 * 判断是否选择同意了条款
	 */
	isAgreed(false);

	function isAgreed(bool) {
		if (bool) {
			if (!userAndOther.agreed.is(':checked')) {
				userAndOther.agreed.next().addClass("has-error");
				return false;
			} else {
				return true;
			}
		} else {
			userAndOther.agreed.change(function() {
				userAndOther.agreed.next().removeClass("has-error");
			});
		}
	}

	/**
	 * 是否显示实名输入区域
	 */
	quest.isAnonymous.change(function() {
		quest.isAnonymous.parent().parent().find("strong").removeClass(
				"has-error");
		if ($(this).filter(':checked').val() == "true") {
			$("#userInfo").addClass("hidden");
		} else {
			$("#userInfo").removeClass("hidden");
		}
	});

	/**
	 * 获取验证码
	 */
	var t, i = 60;
	userAndOther.getCode.click(function() {
		if (!regFun(userAndOther.mobile, regStr.phoneReg)) {
			return;
		}
		userAndOther.getCode.attr("disabled", true);
		userAndOther.getCode.html("(" + i + ")秒后重试");
		t = setInterval(function() {
			if (i == 0) {
				clearInterval(t);
				t = null;
				userAndOther.getCode.removeAttr("disabled");
				userAndOther.getCode.html("获取临时密码");
				i = 60;
			} else {
				userAndOther.getCode.attr("disabled", true);
				i--;
				userAndOther.getCode.html("(" + i + ")秒后重试");
			}
		}, 1000);
		$.get("case/getVerifyCode.do?mobile=" + userAndOther.mobile.val(),
				function(res, status) {
					console.log("data:" + res + "status:" + status);
				});
	});

	userAndOther.mobile.keyup(function(){
		if(t==null){
			userAndOther.getCode.removeAttr("disabled");
		}
	});
	
	/**
	 * 验证验证码
	 */
	userAndOther.code.keyup(function() {
		userAndOther.code.next().removeClass("glyphicon-remove");
		userAndOther.code.next().removeClass("glyphicon-ok");
		if (userAndOther.code.val().length == 6) {
			var url = "case/checkVerifyCode.do?mobile="
					+ userAndOther.mobile.val() + "&verifyCode="
					+ userAndOther.code.val();
			$.get(url, function(res, status) {
				if (status == "success") {
					if (res == "error") {
						userAndOther.code.next().addClass("glyphicon-remove");
					} else {
						userAndOther.getCode.attr("disabled","");
						clearInterval(t);
						t = null;
						userAndOther.getCode.html("获取临时密码");
						i = 60;
						userAndOther.code.next().addClass("glyphicon-ok");
						if (res != "success") {
							res = JSON.parse(res);
							userAndOther.name.val(res.name);
							if (res.idName == "身份证") {
								console.log("身份证");
								userAndOther.idName.find("option[value='身份证']")
										.attr("selected", true);
							}
							if (res.idName == "护照") {
								console.log("护照");
								userAndOther.idName.find("option[value='护照']")
										.attr("selected", true);
							}
							userAndOther.idNo.val(res.idNo);
							userAndOther.email.val(res.email);
							userAndOther.bestContact.val(res.bestContact);
							userAndOther.reporterId.val(res.reporterId);
						} else {
							userAndOther.name.val("");
							userAndOther.idName.find("option[value='-1']")
									.attr("selected", true);
							userAndOther.idNo.val("");
							userAndOther.email.val("");
							userAndOther.bestContact.val("");
							userAndOther.reporterId.val("");
						}
					}
				}
			});
		}
	});

	/**
	 * 验证实名用户表单
	 */
	hiddenErrorIcon(userAndOther.mobile);
	hiddenErrorIcon(userAndOther.code);
	hiddenErrorIcon(userAndOther.name);
	hiddenErrorIcon(userAndOther.idNo);
	hiddenErrorIcon(userAndOther.email);
	userAndOther.idName.change(function() {
		if ($(this).find("option:selected").val() != "-1") {
			$(this).parent().removeClass("has-error");
			userAndOther.idNo.parent().removeClass("has-error");
		}
	});

	function validationUser() {
		if (!regFun(userAndOther.mobile, regStr.phoneReg)) {
			return false;
		}
		if (!showErrorIcon(userAndOther.code)) {
			return false;
		}
		if (!showErrorIcon(userAndOther.name)) {
			return false;
		}
		if (!showSelectError(userAndOther.idName)) {
			return false;
		}
		if (!regCardFun(userAndOther.idNo)) {
			return false;
		}
		if (!regFun(userAndOther.email, regStr.emailReg)) {
			return false;
		}
		return true;
	}

	/**
	 * 验证密码
	 */
	hiddenErrorIcon(quest.pass);
	hiddenErrorIcon(quest.repass);
	quest.repass.focus(function() {
		quest.repass.next().addClass("hidden");
		quest.repass.parent().removeClass("has-error");
	});

	function validationPass() {
		if ($.trim(quest.pass.val()).length < 6) {
			quest.pass.parent().addClass("has-error");
			return false;
		}
		if ($.trim(quest.repass.val()) != $.trim(quest.pass.val())) {
			quest.repass.parent().addClass("has-error");
			quest.repass.next().removeClass("hidden");
			return false;
		}
		return true;
	}

	/**
	 * 验证问题
	 */
	hiddeRadioError(quest.isEmployees);
	hiddeRadioError(quest.regulators);
	hiddeRadioError(quest.admin);
	hiddeRadioError(quest.isReport);
	hiddenTextareaIcon(quest.characteristics);
	hiddenTextareaIcon(quest.address);
	hiddenTextareaIcon(quest.date);
	hiddenTextareaIcon(quest.hidden);
	hiddenTextareaIcon(quest.details);
	hiddenSelectError(quest.valuation1, quest.valuation1.parent().parent()
			.prev().find("strong"));
	hiddenSelectError(quest.valuation2, quest.valuation2.parent().parent()
			.prev().find("strong"));
	hiddenSelectError(quest.duration, quest.duration.parent().parent().find(
			"strong"));
	hiddenSelectError(quest.way, quest.way.parent().parent().find("strong"));
	quest.personName1.focus(function() {
		questPanle.quest_2.find("strong").removeClass("has-error");
		$(this).parent().removeClass("has-error");
	});
	quest.personName2.focus(function() {
		questPanle.quest_2.find("strong").removeClass("has-error");
		$(this).parent().removeClass("has-error");
	});
	quest.personName3.focus(function() {
		questPanle.quest_2.find("strong").removeClass("has-error");
		$(this).parent().removeClass("has-error");
	});
	function validationQuest() {
		var bool = true;
		$
				.each(
						questionList,
						function(i, question) {
							var key = question.quest_key;
							var needed = question.is_needed;
							switch (key) {
							case "quest_1":
								if (needed == 1) {
									if (!showRadioError(quest.isEmployees))
										bool = false;
								}
								console.log("quest_1" + bool);
								break;
							case "quest_2":
								if (needed == 1) {
									if (isEmty(quest.personName1.val())
											&& isEmty(quest.personName2.val())
											&& isEmty(quest.personName3.val())) {
										questPanle.quest_2.find("strong")
												.addClass("has-error");
										bool = false;
									}
									if (!isEmty(quest.personPosition1.val())
											&& isEmty(quest.personName1.val())) {
										quest.personName1.parent().addClass(
												"has-error");
										bool = false;
									}
									if (!isEmty(quest.personPosition2.val())
											&& isEmty(quest.personName2.val())) {
										quest.personName2.parent().addClass(
												"has-error");
										bool = false;
									}
									if (!isEmty(quest.personPosition3.val())
											&& isEmty(quest.personName3.val())) {
										quest.personName3.parent().addClass(
												"has-error");
										bool = false;
									}
								}
								console.log("quest_2" + bool);
								break;
							case "quest_3":
								if (needed == 1) {
									if (!showRadioError(quest.regulators))
										bool = false;
								}
								console.log("quest_3" + bool);
								break;
							case "quest_4":
								if (needed == 1) {
									if (!showRadioError(quest.admin))
										bool = false;
								}
								console.log("quest_4" + bool);
								break;
							case "quest_5":
								if (needed == 1) {
									if (isEmty(quest.characteristics.val())) {
										quest.characteristics.parent().parent()
												.prev().find("strong")
												.addClass("has-error");
										bool = false;
									}
								}
								console.log("quest_5" + bool);
								break;
							case "quest_6":
								if (needed == 1) {
									if (quest.valuation1
											.find("option:selected").val() == "-1") {
										quest.valuation1.parent().parent()
												.prev().find("strong")
												.addClass("has-error");
										bool = false;
									}
									if (quest.valuation2
											.find("option:selected").val() == "-1") {
										quest.valuation2.parent().parent()
												.prev().find("strong")
												.addClass("has-error");
										bool = false;
									}
								} else {
									if (quest.valuation1
											.find("option:selected").val() != "-1") {
										if (quest.valuation2.find(
												"option:selected").val() == "-1") {
											quest.valuation2.parent().parent()
													.prev().find("strong")
													.addClass("has-error");
											bool = false;
										}
									}
								}
								console.log("quest_6" + bool);
								break;
							case "quest_7":
								if (needed == 1) {
									if (isEmty(quest.address.val())) {
										quest.address.parent().parent().prev()
												.find("strong").addClass(
														"has-error");
										bool = false;
									}
								}
								console.log("quest_7" + bool);
								break;
							case "quest_8":
								if (needed == 1) {
									if (isEmty(quest.date.val())) {
										quest.date.parent().parent().prev()
												.find("strong").addClass(
														"has-error");
										bool = false;
									}
								}
								console.log("quest_8" + bool);
								break;
							case "quest_9":
								if (needed == 1) {
									if (quest.duration.find("option:selected")
											.val() == "-1") {
										quest.duration.parent().parent().find(
												"strong").addClass("has-error");
										bool = false;
									}
								}
								console.log("quest_9" + bool);
								break;
							case "quest_10":
								if (needed == 1) {
									if (quest.way.find("option:selected").val() == "-1") {
										quest.way.parent().parent().find(
												"strong").addClass("has-error");
										bool = false;
									}
								}
								console.log("quest_10" + bool);
								break;
							case "quest_11":
								if (needed == 1) {
									if (!showRadioError(quest.isReport)) {
										bool = false;
									}
								}
								console.log("quest_11" + bool);
								break;
							case "quest_12":
								if (needed == 1) {
									if (isEmty(quest.hidden.val())) {
										quest.hidden.parent().parent().prev()
												.find("strong").addClass(
														"has-error");
										bool = false;
									}
								}
								console.log("quest_12" + bool);
								break;
							case "quest_13":
								if (needed == 1) {
									if (isEmty(quest.details.val())) {
										quest.details.parent().parent().prev()
												.find("strong").addClass(
														"has-error");
										bool = false;
									}
								}
								console.log("quest_13" + bool);
								break;
							}
						});
		console.log(bool);
		return bool;
	}

	/**
	 * 正则表达式验证手机邮箱
	 */
	function regFun(ele, str) {
		if (!str.test(ele.val())) {
			ele.parent().addClass("has-error");
			return false;
		}
		return true;
	}

	/**
	 * 正则表达式验证件号
	 */
	function regCardFun(ele) {
		var cardVal = userAndOther.idName.find("option:selected").val();
		if (cardVal == "身份证") {
			if (!regStr.idCard.test(ele.val())) {
				ele.parent().addClass("has-error");
				return false;
			}
		}
		if (cardVal == "护照") {
			if (!regStr.passport.test(ele.val())) {
				ele.parent().addClass("has-error");
				return false;
			}
		}
		return true;
	}

	/**
	 * 显示错误icon
	 */
	function showErrorIcon(ele) {
		if (isEmty(ele.val())) {
			ele.parent().addClass("has-error");
			return false;
		}
		return true;
	}

	/**
	 * 隐藏错误icon
	 */
	function hiddenErrorIcon(ele) {
		ele.focus(function() {
			ele.parent().removeClass("has-error");
		});
		ele.parent().removeClass("has-error");
	}

	/**
	 * 隐藏Textarea错误
	 */
	function hiddenTextareaIcon(ele) {
		ele.focus(function() {
			ele.parent().parent().prev().find("strong")
					.removeClass("has-error");
		});
		ele.parent().parent().prev().find("strong").removeClass("has-error");
	}

	/**
	 * 判断select控件是否选择正确
	 */
	function showSelectError(ele) {
		if (ele.find("option:selected").val() == "-1") {
			ele.parent().addClass("has-error");
			return false;
		}
		return true;
	}

	/**
	 * 隐藏select错误显示
	 */
	function hiddenSelectError(ele, rEle) {
		ele.change(function() {
			rEle.removeClass("has-error");
		});
	}

	/**
	 * 判断radio控件是否选择
	 */
	function showRadioError(ele) {
		// TODO
		if (ele.filter(':checked').val() == undefined) {
			ele.parent().parent().find("strong").addClass("has-error");
			return false;
		}
		return true;
	}

	/**
	 * 隐藏radio错误显示
	 */
	function hiddeRadioError(ele) {
		ele.change(function() {
			ele.parent().parent().find("strong").removeClass("has-error");
		});
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