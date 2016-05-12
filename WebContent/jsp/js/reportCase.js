$(function () {

    // 初始化控件，清除缓存
    $("input[type=text]").val("");
    $("input[type=checkbox]").removeAttr("checked");
    $("input[type=radio]").removeAttr("checked");
    $("select").find("option[value='-1']").attr("selected", true);
    $("textarea").val("");
    $("#getCode").removeAttr("disabled");

    /**
     * 将表单转换成json对象
     */
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(
            function () {
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [
                            serializeObj[this.name], this.value];
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
        reporterId: $("input[name=reporterId]"),
        name: $("input[name=name]"),
        idName: $("select[name=idName]"),
        idNo: $("input[name=idNo]"),
        mobile: $("input[name=mobile]"),
        code: $("input[name=verifyCode]"),
        email: $("input[name=email]"),
        bestContact: $("textarea[name=bestContact]"),
        contactWay: $("textarea[name=contactWay]"),

        agreed: $("input[name=agreed]"),
        getCode: $("#getCode")
    };

    var province = $("select[name=province]");
    var city = $("select[name=city]");

    /**
     * 所有问题面板
     */
    var questPanle = {
        quest_1: $("#quest_1"),
        quest_2: $("#quest_2"),
        quest_3: $("#quest_3"),
        quest_4: $("#quest_4"),
        quest_5: $("#quest_5"),
        quest_6: $("#quest_6"),
        quest_7: $("#quest_7"),
        quest_8: $("#quest_8"),
        quest_9: $("#quest_9"),
        quest_10: $("#quest_10"),
        quest_11: $("#quest_11"),
        quest_12: $("#quest_12"),
        quest_13: $("#quest_13")
    };

    /**
     * 问题表单控件
     */
    var quest = {
        isAnonymous: $("input[name=isAnonymous]"),

        quest_1_select: $("select[name='quest_1_select']"),
        quest_1_textarea: $("textarea[name='quest_1_textarea']"),

        quest_2_input1_name: $("input[name='quest_2_input1_name']"),
        quest_2_input1_position: $("input[name='quest_2_input1_position']"),
        quest_2_input1_department: $("input[name='quest_2_input1_department']"),
        quest_2_input2_name: $("input[name='quest_2_input2_name']"),
        quest_2_input2_position: $("input[name='quest_2_input2_position']"),
        quest_2_input2_department: $("input[name='quest_2_input2_department']"),
        quest_2_input3_name: $("input[name='quest_2_input3_name']"),
        quest_2_input3_position: $("input[name='quest_2_input3_position']"),
        quest_2_input3_department: $("input[name='quest_2_input3_department']"),

        quest_3_radio: $("input[name='quest_3_radio']"),
        quest_3_textarea: $("textarea[name='quest_3_textarea']"),

        quest_4_radio: $("input[name='quest_4_radio']"),

        quest_5_textarea: $("textarea[name='quest_5_textarea']"),

        quest_6_select: $("select[name='quest_6_select']"),
        quest_6_textarea: $("textarea[name='quest_6_textarea']"),

        quest_7_textarea: $("textarea[name='quest_7_textarea']"),

        quest_8_select1: $("select[name='quest_8_select1']"),
        quest_8_select2: $("select[name='quest_8_select2']"),

        quest_9_select: $("select[name='quest_9_select']"),
        quest_9_textarea: $("textarea[name='quest_9_textarea']"),

        quest_10_radio: $("input[name='quest_10_radio']"),
        quest_10_textarea: $("textarea[name='quest_10_textarea']"),

        quest_11_textarea: $("textarea[name='quest_11_textarea']"),

        quest_12_textarea: $("textarea[name='quest_12_textarea']"),

        pass: $("input[name='quest_15_value']"),
        repass: $("input[name='quest_16_value']")
    };

    /**
     * 隐藏的问题表单控件
     */
    var send = {
        quest_1: $("input[name=quest_1]"),
        quest_2: $("input[name=quest_2]"),
        quest_3: $("input[name=quest_3]"),
        quest_4: $("input[name=quest_4]"),
        quest_5: $("input[name=quest_5]"),
        quest_6: $("input[name=quest_6]"),
        quest_7: $("input[name=quest_7]"),
        quest_8: $("input[name=quest_8]"),
        quest_9: $("input[name=quest_9]"),
        quest_10: $("input[name=quest_10]"),
        quest_11: $("input[name=quest_11]"),
        quest_12: $("input[name=quest_12]")
    };

    var regStr = {
        phoneReg: /(1[3-9]\d{9}$)/,
        emailReg: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
        passport: /^[a-zA-Z0-9]{5,17}$/,
        idCard: /^\d{15}|(\d{17}(\d|x|X))$/
    };

    /**
     * 控制页面需要显示的问题列表并设置是否为必填
     */
    (function () {
        if (questionList.length <= 0) {
            for (var i = 1; i < 13; i++) {
                $("#quest_" + i).removeClass("hidden");
            }
            return;
        }
        $.each(questionList, function (i, question) {
            var key = question.quest_key;
            $("#" + key).removeClass("hidden");
            var span = $("<span/>").attr("class", "xinghao").text("*");
            if (question.is_needed == 1) {
                $("#" + key).find("strong").prepend(span);
            }
        });
    })();

    /**
     * 设置需要发送的问题列表
     */
    function setAnswers() {
        var list = [];
        if (questionList.length <= 0) {
            for (var i = 1; i < 13; i++) {
                var ans = $("input[name=quest_" + i + "]").val();
                if (ans != null && ans != "") {
                    var data = {};
                    data["questKey"] = "quest_" + i;
                    data["questValue"] = ans;
                    list.push(data);
                }
            }
        } else {
            $.each(questionList, function (i, question) {
                var ans = $("input[name=" + question.quest_key + "]").val();
                if (ans != null && ans != "") {
                    var data = {};
                    data["questKey"] = question.quest_key;
                    data["questValue"] = ans;
                    list.unshift(data);
                }
            });
        }
        return JSON.stringify(list);
    }
    ;

    $("#submitReport").click(function (e) {
        if (!isAgreed(true)) {
            $("html,body").animate({scrollTop: $("#agreed").offset().top - 60}, 500);
            return Modal.alert({msg: "同意条款才能继续!"});
        }
        if (!showRadioError(quest.isAnonymous)) {
            $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top - 80}, 500);
            return Modal.alert({msg: "请选择是否实名或匿名提交!"});
        }
        if (quest.isAnonymous.filter(':checked').val() == "false") {
            if (!validationUser()) {
                return;
            }
        } else {
            if (!showErrorIcon(userAndOther.contactWay)) {
                $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top - 20}, 500);
                return Modal.alert({msg: "匿名请提供一种联系方式!"});
            }
        }
        if (validationCompanyAddr()) {
            $("html,body").animate({scrollTop: $("select[name=province]").offset().top - 120}, 500);
            return Modal.alert({msg: "请选择该公司所在的省和市!"});
        }
        if (!validationQuest()) {
            return;
        }
        if (!validationPass()) {
            return Modal.alert({msg: "您的密码填写有误!"});
        }
        setSendForm();
        sendForm();
    });

    hiddenErrorIcon(userAndOther.contactWay);

    /**
     * 设置隐藏问题表单的数据
     */
    function setSendForm() {
        if (quest.quest_1_select.find("option:selected").val() != "-1") {
            send.quest_1.val(quest.quest_1_select.find("option:selected").text());
        }
        if (quest.quest_1_select.find("option:selected").text() == "其它") {
            send.quest_1.val(send.quest_1.val() + "；" + quest.quest_1_textarea.val());
        }

        var aboutUser = "";
        if (!isEmty(quest.quest_2_input1_name.val())) {
            aboutUser += "姓名：" + quest.quest_2_input1_name.val() + "，职位："
                + quest.quest_2_input1_position.val() + "，部门：" + quest.quest_2_input1_department.val();
        }
        if (!isEmty(quest.quest_2_input2_name.val())) {
            if (isEmty(aboutUser)) {
                aboutUser += "姓名：" + quest.quest_2_input2_name.val() + "，职位："
                    + quest.quest_2_input2_position.val() + ",部门：" + quest.quest_2_input2_department.val();
            } else {
                aboutUser += "；姓名：" + quest.quest_2_input2_name.val() + "，职位："
                    + quest.quest_2_input2_position.val() + "，部门：" + quest.quest_2_input2_department.val();
            }
        }
        if (!isEmty(quest.quest_2_input3_name.val())) {
            if (isEmty(aboutUser)) {
                aboutUser += "姓名：" + quest.quest_2_input3_name.val() + "，" + "职位："
                    + quest.quest_2_input3_position.val() + "，部门：" + quest.quest_2_input3_department.val();
            } else {
                aboutUser += "；姓名：" + quest.quest_2_input3_name.val() + "，" + "职位："
                    + quest.quest_2_input3_position.val() + "，部门：" + quest.quest_2_input3_department.val();
            }
        }
        send.quest_2.val(aboutUser);

        if (quest.quest_3_radio.filter(':checked').val() != undefined) {
            send.quest_3.val(quest.quest_3_radio.filter(':checked').val());
        }
        if (quest.quest_3_radio.filter(':checked').val() == "是") {
            send.quest_3.val(send.quest_3.val() + "；" + quest.quest_3_textarea.val());
        }


        if (quest.quest_4_radio.filter(':checked').val() != undefined) {
            send.quest_4.val(quest.quest_4_radio.filter(':checked').val());
        }

        send.quest_5.val(quest.quest_5_textarea.val());

        if (quest.quest_6_select.find("option:selected").val() != "-1") {
            send.quest_6.val(quest.quest_6_select.find("option:selected").text());
        }
        if (quest.quest_6_select.find("option:selected").text() == "其它") {
            send.quest_6.val(send.quest_6.val() + "；" + quest.quest_6_textarea.val());
        }

        send.quest_7.val(quest.quest_7_textarea.val());

        if (quest.quest_8_select1.find("option:selected").val() != "-1") {
            send.quest_8.val(quest.quest_8_select1.find("option:selected").text() + "：" + quest.quest_8_select2.find("option:selected").text());
        }

        if (quest.quest_9_select.find("option:selected").val() != "-1") {
            send.quest_9.val(quest.quest_9_select.find("option:selected").text());
        }
        if (quest.quest_9_select.find("option:selected").text() == "其它") {
            send.quest_9.val(send.quest_9.val() + "；" + quest.quest_9_textarea.val());
        }

        if (quest.quest_10_radio.filter(':checked').val() != undefined) {
            send.quest_10.val(quest.quest_10_radio.filter(':checked').val());
        }
        if (quest.quest_10_radio.filter(':checked').val() == "是") {
            send.quest_10.val(send.quest_10.val() + "；" + quest.quest_10_textarea.val());
        }

        send.quest_11.val(quest.quest_11_textarea.val());
        send.quest_12.val(quest.quest_12_textarea.val());
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
        var contactWay = "contactWay=" + userAndOther.contactWay.val();
        var anonymous = "isAnonymous="
            + quest.isAnonymous.filter(':checked').val();
        var questions = "answers=" + setAnswers();
        var accessCode = "accessCode=" + quest.pass.val();
        var pro = "province=" + province.find("option:selected").text();
        var cit = "city=" + city.find("option:selected").text();
        var data = reporter + "&" + contactWay + "&" + verifyCode + "&"
            + anonymous + "&" + pro + "&" + cit + "&" + questions + "&" + trackingNo + "&"
            + accessCode + "&" + rtList;
        $.post(url, data, function (res, status) {
            if (status == "success") {
                if (res == "saveError") { // 案件提交失败
                    return Modal.alert({msg: "举报信息提交失败，请重试!"});
                }
                if (res == "checkError") { // 实名手机验证失败
                    return Modal.alert({msg: "实名手机验证失败!"});
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
            userAndOther.agreed.change(function () {
                userAndOther.agreed.next().removeClass("has-error");
            });
        }
    }

    /**
     * 是否显示实名输入区域
     */
    quest.isAnonymous.change(function () {
        quest.isAnonymous.parent().parent().find("strong").removeClass(
            "has-error");
        if ($(this).filter(':checked').val() == "true") {
            $("#userInfo").addClass("hidden");
            $("#contactInfo").removeClass("hidden");
        } else {
            $("#contactInfo").addClass("hidden");
            $("#userInfo").removeClass("hidden");
        }
    });

    /**
     * 获取验证码
     */
    var t, i = 60;
    userAndOther.getCode.click(function () {
        if (!regFun(userAndOther.mobile, regStr.phoneReg)) {
            return Modal.alert({msg:'请输入正确的手机号!'});
        }
        userAndOther.getCode.attr("disabled", true);
        userAndOther.getCode.html("(" + i + ")秒后重试");
        t = setInterval(function () {
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
            function (res, status) {
            });
    });

    userAndOther.mobile.keyup(function () {
        if (t == null) {
            userAndOther.getCode.removeAttr("disabled");
        }
    });

    /**
     * 验证验证码
     */
    userAndOther.code.keyup(function () {
        userAndOther.code.next().removeClass("glyphicon-remove");
        userAndOther.code.next().removeClass("glyphicon-ok");
        if (userAndOther.code.val().length == 6) {
            var url = "case/checkVerifyCode.do?mobile="
                + userAndOther.mobile.val() + "&verifyCode="
                + userAndOther.code.val();
            $.get(url, function (res, status) {
                if (status == "success") {
                    if (res == "error") {
                        userAndOther.code.next().addClass("glyphicon-remove");
                    } else {
                        userAndOther.getCode.attr("disabled", "");
                        clearInterval(t);
                        t = null;
                        userAndOther.getCode.html("获取临时密码");
                        i = 60;
                        userAndOther.code.next().addClass("glyphicon-ok");
                        if (res != "success") {
                            res = JSON.parse(res);
                            userAndOther.name.val(res.name);
                            userAndOther.idName.get(0).value = res.idName;
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
    userAndOther.idName.change(function () {
        if ($(this).find("option:selected").val() != "-1") {
            $(this).parent().removeClass("has-error");
            userAndOther.idNo.parent().removeClass("has-error");
        }
    });

    function validationUser() {
        if (!regFun(userAndOther.mobile, regStr.phoneReg)) {
            $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top}, 500);
            Modal.alert({msg: "请填写正确的手机号!"});
            return false;
        }
        if (!showErrorIcon(userAndOther.code)) {
            $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top}, 500);
            Modal.alert({msg: "请输入正确的验证码!"});
            return false;
        }
        if (!showErrorIcon(userAndOther.name)) {
            $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top}, 500);
            Modal.alert({msg: "请填写姓名!"});
            return false;
        }
        if (!showSelectError(userAndOther.idName)) {
            $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top}, 500);
            Modal.alert({msg: "请选择证件类型!"});
            return false;
        }
        if (!regCardFun(userAndOther.idNo)) {
            $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top}, 500);
            Modal.alert({msg: "证件号与选择的类型不匹配!"});
            return false;
        }
        if (!regFun(userAndOther.email, regStr.emailReg)) {
            $("html,body").animate({scrollTop: $("input[name=isAnonymous]").offset().top}, 500);
            Modal.alert({msg: "请填写正确的E-mail地址!"});
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     */
    hiddenErrorIcon(quest.pass);
    hiddenErrorIcon(quest.repass);
    quest.repass.focus(function () {
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
     * 验证被举报公司的所在省市
     */
    hiddenErrorIcon(province);
    hiddenErrorIcon(city);
    function validationCompanyAddr() {
        if (province.find("option:selected").val() == "-1") {
            province.parent().addClass("has-error");
            return true;
        }
        if (city.find("option:selected").val() == "-1") {
            city.parent().addClass("has-error");
            return true;
        }
        return false;
    }

    /**
     * 验证问题
     */
    removeChangeError(quest.quest_1_select, questPanle.quest_1);
    removeFocusError(quest.quest_1_textarea, questPanle.quest_1);
    quest.quest_2_input1_name.get(0).onfocus = removeQuest2;
    quest.quest_2_input1_position.get(0).onfocus = removeQuest2;
    quest.quest_2_input1_department.get(0).onfocus = removeQuest2;
    quest.quest_2_input2_name.get(0).onfocus = removeQuest2;
    quest.quest_2_input2_position.get(0).onfocus = removeQuest2;
    quest.quest_2_input2_department.get(0).onfocus = removeQuest2;
    quest.quest_2_input3_name.get(0).onfocus = removeQuest2;
    quest.quest_2_input3_position.get(0).onfocus = removeQuest2;
    quest.quest_2_input3_department.get(0).onfocus = removeQuest2;
    function removeQuest2() {
        questPanle.quest_2.find("strong").removeClass("has-error");
    }

    removeChangeError(quest.quest_3_radio, questPanle.quest_3);
    removeFocusError(quest.quest_3_textarea, questPanle.quest_3);
    removeChangeError(quest.quest_4_radio, questPanle.quest_4);
    removeFocusError(quest.quest_5_textarea, questPanle.quest_5);
    removeChangeError(quest.quest_6_select, questPanle.quest_6);
    removeFocusError(quest.quest_6_textarea, questPanle.quest_6);
    removeFocusError(quest.quest_7_textarea, questPanle.quest_7);
    removeChangeError(quest.quest_8_select1, questPanle.quest_8);
    removeChangeError(quest.quest_9_select, questPanle.quest_9);
    removeFocusError(quest.quest_9_textarea, questPanle.quest_9);
    removeChangeError(quest.quest_10_radio, questPanle.quest_10);
    removeFocusError(quest.quest_10_textarea, questPanle.quest_10);
    removeFocusError(quest.quest_11_textarea, questPanle.quest_11);
    removeFocusError(quest.quest_12_textarea, questPanle.quest_12);
    function removeChangeError(a, b) {
        a.change(function () {
            b.find("strong").removeClass("has-error");
        });
    };
    function removeFocusError(a, b) {
        a.focus(function () {
            b.find("strong").removeClass("has-error");
        });
    };
    function validationQuest() {
        var bool = true;
        $.each(questionList, function (i, question) {
            var key = question.quest_key;
            var needed = question.is_needed;
            switch (key) {
                case "quest_1":
                    if (needed == 1) {
                        if (quest.quest_1_select.find("option:selected").val() == "-1") {
                            bool = false;
                            questPanle.quest_1.find("strong").addClass("has-error");
                            $("html,body").animate({scrollTop: $("#quest_1").offset().top - 50}, 500);
                            Modal.alert({msg: '请选择你与该公司的关系,选择其他请说明!'});
                        }
                        if (quest.quest_1_select.find("option:selected").text() == "其它") {
                            if (isEmty(quest.quest_1_textarea.val())) {
                                bool = false;
                                questPanle.quest_1.find("strong").addClass("has-error");
                                $("html,body").animate({scrollTop: $("#quest_1").offset().top - 50}, 500);
                                Modal.alert({msg: '您选择了其他请说明!'});
                            }
                        }
                    } else {
                        if (quest.quest_1_select.find("option:selected").text() == "其它") {
                            if (isEmty(quest.quest_1_textarea.val())) {
                                bool = false;
                                questPanle.quest_1.find("strong").addClass("has-error");
                                $("html,body").animate({scrollTop: $("#quest_1").offset().top - 50}, 500);
                                Modal.alert({msg: '您选择了其他请说明!'});
                            }
                        }
                    }
                    return bool;
                case "quest_2":
                    if (needed == 1) {
                        if (isEmty(quest.quest_2_input1_name.val()) && isEmty(quest.quest_2_input2_name.val())
                            && isEmty(quest.quest_2_input3_name.val())) {
                            bool = false;
                        }

                        if (!isEmty(quest.quest_2_input1_department.val())) {
                            if (isEmty(quest.quest_2_input1_position.val()) || isEmty(quest.quest_2_input1_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input1_position.val())) {
                            if (isEmty(quest.quest_2_input1_department.val()) || isEmty(quest.quest_2_input1_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input1_name.val())) {
                            if (isEmty(quest.quest_2_input1_position.val()) || isEmty(quest.quest_2_input1_department)) {
                                bool = false;
                            }
                        }

                        if (!isEmty(quest.quest_2_input2_department.val())) {
                            if (isEmty(quest.quest_2_input2_position.val()) || isEmty(quest.quest_2_input2_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input2_position.val())) {
                            if (isEmty(quest.quest_2_input2_department.val()) || isEmty(quest.quest_2_input2_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input2_name.val())) {
                            if (isEmty(quest.quest_2_input2_position.val()) || isEmty(quest.quest_2_input2_department)) {
                                bool = false;
                            }
                        }

                        if (!isEmty(quest.quest_2_input3_department.val())) {
                            if (isEmty(quest.quest_2_input3_position.val()) || isEmty(quest.quest_2_input3_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input3_position.val())) {
                            if (isEmty(quest.quest_2_input3_department.val()) || isEmty(quest.quest_2_input3_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input3_name.val())) {
                            if (isEmty(quest.quest_2_input3_position.val()) || isEmty(quest.quest_2_input3_department)) {
                                bool = false;
                            }
                        }
                        if (!bool) {
                            showErrorMsg(questPanle.quest_2, '#quest_2', '请说明该事件或违规行为主要相关人的身份,至少一个!');
                        }
                    } else {
                        if (!isEmty(quest.quest_2_input1_department.val())) {
                            if (isEmty(quest.quest_2_input1_position.val()) || isEmty(quest.quest_2_input1_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input1_position.val())) {
                            if (isEmty(quest.quest_2_input1_department.val()) || isEmty(quest.quest_2_input1_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input1_name.val())) {
                            if (isEmty(quest.quest_2_input1_position.val()) || isEmty(quest.quest_2_input1_department)) {
                                bool = false;
                            }
                        }

                        if (!isEmty(quest.quest_2_input2_department.val())) {
                            if (isEmty(quest.quest_2_input2_position.val()) || isEmty(quest.quest_2_input2_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input2_position.val())) {
                            if (isEmty(quest.quest_2_input2_department.val()) || isEmty(quest.quest_2_input2_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input2_name.val())) {
                            if (isEmty(quest.quest_2_input2_position.val()) || isEmty(quest.quest_2_input2_department)) {
                                bool = false;
                            }
                        }

                        if (!isEmty(quest.quest_2_input3_department.val())) {
                            if (isEmty(quest.quest_2_input3_position.val()) || isEmty(quest.quest_2_input3_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input3_position.val())) {
                            if (isEmty(quest.quest_2_input3_department.val()) || isEmty(quest.quest_2_input3_name)) {
                                bool = false;
                            }
                        }
                        if (!isEmty(quest.quest_2_input3_name.val())) {
                            if (isEmty(quest.quest_2_input3_position.val()) || isEmty(quest.quest_2_input3_department)) {
                                bool = false;
                            }
                        }
                        if (!bool) {
                            showErrorMsg(questPanle.quest_2, '#quest_2', '您填写的身份信息有误!');
                        }
                    }
                    return bool;
                case "quest_3":
                    if (needed == 1) {
                        if (quest.quest_3_radio.filter(':checked').val() == undefined) {
                            bool = false;
                            showErrorMsg(questPanle.quest_3, '#quest_3',
                                '请选择是否怀疑或知道有领导或管理人员与该事件或违规行为有关,如果是请说明是谁!');
                        }
                        if (quest.quest_3_radio.filter(':checked').val() == "是") {
                            if (isEmty(quest.quest_3_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_3, '#quest_3', '您选择了是请说明是谁!');
                            }
                        }
                    } else {
                        if (quest.quest_3_radio.filter(':checked').val() == "是") {
                            if (isEmty(quest.quest_3_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_3, '#quest_3', '您选择了是请说明是谁!');
                            }
                        }
                    }
                    return bool;
                case "quest_4":
                    if (needed == 1) {
                        if (quest.quest_4_radio.filter(':checked').val() == undefined) {
                            bool = false;
                            showErrorMsg(questPanle.quest_4, '#quest_4', '请选择是否有公司高级管理人员注意到该事件或违规行为!');
                        }
                    }
                    return bool;
                case "quest_5":
                    if (needed == 1) {
                        if (isEmty(quest.quest_5_textarea.val())) {
                            bool = false;
                            showErrorMsg(questPanle.quest_5, '#quest_5', '请说明该事件或违规行为发生的时间!');
                        }
                    }
                    return bool;
                case "quest_6":
                    if (needed == 1) {
                        if (quest.quest_6_select.find("option:selected").val() == "-1") {
                            bool = false;
                            showErrorMsg(questPanle.quest_6, '#quest_6', '请选择此问题持续了多少时间,如果为其他请说明!');
                        }
                        if (quest.quest_6_select.find("option:selected").text() == "其它") {
                            if (isEmty(quest.quest_6_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_6, '#quest_6', '您选择了其他请说明!');
                            }
                        }
                    } else {
                        if (quest.quest_6_select.find("option:selected").text() == "其它") {
                            if (isEmty(quest.quest_6_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_6, '#quest_6', '您选择了其他请说明!');
                            }
                        }
                    }
                    return bool;
                case "quest_7":
                    if (needed == 1) {
                        if (isEmty(quest.quest_7_textarea.val())) {
                            bool = false;
                            showErrorMsg(questPanle.quest_7, '#quest_7', '请说明该事件或违规行为在哪里发生的!');
                        }
                    }
                    return bool;
                case "quest_8":
                    if (needed == 1) {
                        if (quest.quest_8_select1.find("option:selected").val() == "-1") {
                            bool = false;
                        }
                        if (quest.quest_8_select2.find("option:selected").val() == "-1") {
                            bool = false;
                        }
                        if (!bool) {
                            showErrorMsg(questPanle.quest_8, '#quest_8', '请估计该事件或违规行为涉及金额是多少!');
                        }
                    } else {
                        if (quest.quest_8_select2.find("option:selected").val() != "-1") {
                            if (quest.quest_8_select1.find("option:selected").val() == "-1") {
                                bool = false;
                                showErrorMsg(questPanle.quest_8, '#quest_8', '您选择了金额,请选择货币种类!');
                            }
                        }
                        if (quest.quest_8_select1.find("option:selected").val() != "-1") {
                            if (quest.quest_8_select2.find("option:selected").val() == "-1") {
                                bool = false;
                                showErrorMsg(questPanle.quest_8, '#quest_8', '您选择了货币种类,请选择金额!');
                            }
                        }
                    }
                    return bool;
                case "quest_9":
                    if (needed == 1) {
                        if (quest.quest_9_select.find("option:selected").val() == "-1") {
                            bool = false;
                            showErrorMsg(questPanle.quest_9, '#quest_9', '请选择您是如何发现该事件或违规行为的,如果为其他请说明!');
                        }
                        if (quest.quest_9_select.find("option:selected").text() == "其它") {
                            if (isEmty(quest.quest_9_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_9, '#quest_9', '您选择了其他请说明!');
                            }
                        }
                    } else {
                        if (quest.quest_9_select.find("option:selected").text() == "其它") {
                            if (isEmty(quest.quest_9_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_9, '#quest_9', '您选择了其他请说明!');
                            }
                        }
                    }
                    return bool;
                case "quest_10":
                    if (needed == 1) {
                        if (quest.quest_10_radio.filter(':checked').val() == undefined) {
                            bool = false;
                            showErrorMsg(questPanle.quest_10, '#quest_10', '请选择您之前是否报告过该事件或违规行为,如果为是请说明!');
                        }
                        if (quest.quest_10_radio.filter(':checked').val() == "是") {
                            if (isEmty(quest.quest_10_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_10, '#quest_10', '您选择了是请说明!');
                            }
                        }
                    } else {
                        if (quest.quest_10_radio.filter(':checked').val() == "是") {
                            if (isEmty(quest.quest_10_textarea.val())) {
                                bool = false;
                                showErrorMsg(questPanle.quest_10, '#quest_10', '您选择了是请说明!');
                            }
                        }
                    }
                    return bool;
                case "quest_11":
                    if (needed == 1) {
                        if (isEmty(quest.quest_11_textarea.val())) {
                            bool = false;
                            showErrorMsg(questPanle.quest_11, '#quest_11', '请说明试图隐瞒该事件或违规行为的人以及他们的隐瞒方式!');
                        }
                    }
                    return bool;
                case "quest_12":
                    if (needed == 1) {
                        if (isEmty(quest.quest_12_textarea.val())) {
                            bool = false;
                            showErrorMsg(questPanle.quest_12, '#quest_12', '请详细阐述该事件或违规行为的完整细节!');
                        }
                    }
                    return bool;
            }
        });
        return bool;
    }

    function showErrorMsg(a, b, c) {
        a.find("strong").addClass("has-error");
        $("html,body").animate({scrollTop: $(b).offset().top}, 500);
        Modal.alert({msg: c});
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
        ele.focus(function () {
            ele.parent().removeClass("has-error");
        });
        ele.parent().removeClass("has-error");
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