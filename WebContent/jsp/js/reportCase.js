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
		idName : $("select[name=idName]"),
		idNo : $("input[name=idNo]"),
		mobile : $("input[name=mobile]"),
		code : $("input[name=code]"),
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
		isEmployees : $("input[name=quest_1_value]"),
		isAnonymous : $("input[name='quest_2_value']"),
		personName1 : $("input[name=quest_3_value_name1]"),
		personPosition1 : $("input[name=quest_3_value_position1]"),
		personName2 : $("input[name=quest_3_value_name2]"),
		personPosition2 : $("input[name=quest_3_value_position2]"),
		personName3 : $("input[name=quest_3_value_name3]"),
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

	/**
	 * 控制页面需要显示的问题
	 */
	(function() {
		$.each(questionList, function(i, question) {
			var key = question.quest_key;
			var span = $("<span/>").attr("class","xinghao").text("*");
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
	 * 设置需要发送的问题
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
		send.quest_1.val(quest.isEmployees.filter(':checked').val());
		var aboutUser = "";
		if (!isEmty(quest.personName1.val())) {
			aboutUser += quest.personName1.val() + ","
					+ quest.personPosition1.val();
		}
		if (!isEmty(quest.personName2.val())) {
			if (isEmty(aboutUser)) {
				aboutUser += quest.personName2.val() + ","
						+ quest.personPosition2.val();
			} else {
				aboutUser += "," + quest.personName2.val() + ","
						+ quest.personPosition2.val();
			}
		}
		if (!isEmty(quest.personName3.val())) {
			if (isEmty(aboutUser)) {
				aboutUser += quest.personName3.val() + ","
						+ quest.personPosition3.val();
			} else {
				aboutUser += "," + quest.personName3.val() + ","
						+ quest.personPosition3.val();
			}
		}
		send.quest_2.val(aboutUser);
		if (isEmty(quest.regulators1.val())) {
			send.quest_3.val(quest.regulators.filter(':checked').val());
		} else {
			send.quest_3.val(quest.regulators.filter(':checked').val() + ","
					+ quest.regulators1.val());
		}
		send.quest_4.val(quest.admin.filter(':checked').val());
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
			send.quest_11.val(quest.isReport.filter(':checked').val());
		} else {
			send.quest_11.val(quest.isReport.filter(':checked').val() + ","
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
		var reporter = "reporter="
				+ JSON.stringify($("#userInfo").serializeJson());
		var anonymous = "isAnonymous="
				+ quest.isAnonymous.filter(':checked').val();
		var questions = "answers=" + setAnswers();
		var trackingNo = "trackingNo=" + quest.trackingNo.val();
		var accessCode = "accessCode=" + quest.pass.val();
		var rtList = "rtList=" + quest.rtList.val();
		var data = reporter + "&" + anonymous + "&" + questions + "&"
				+ trackingNo + "&" + accessCode + "&" + rtList;

		$.post(url, data, function(res, status) {
			if (status == "success" && res != "") {
				location.href = "jsp/pages/reportSuccess.jsp";
			}
		});
	}

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
		if ($(this).filter(':checked').val() == "true") {
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