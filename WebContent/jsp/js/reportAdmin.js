$(function () {
    var search = {
        startTime: $("input[name=startTime]"),
        endTime: $("input[name=endTime]"),
        rtList: $("input[name=rtList]"),
        keyword: $("input[name=keyWord]"),
        trackingNo: $("input[name=trackingNo]"),
        caseState: $("select[name=caseState]")
    }
    search.startTime.datepicker({
        language: 'zh',
        autoClose: true,
        navTitles: {
            days: '<h6>当前日期:yyyy年mm月dd日</h6> '
        }
    });
    search.endTime.datepicker({
        language: 'zh',
        autoClose: true,
        navTitles: {
            days: '<h6>当前日期:yyyy年mm月dd日</h6> '
        }
    });
    search.endTime.blur(function () {
        if (new Date(search.endTime.val()) < new Date(search.startTime.val())) {
            search.endTime.val("");
            return alert("结束时间应大于开始时间!");
        }
    });
    search.startTime.blur(function () {
        if (new Date(search.endTime.val()) < new Date(search.startTime.val())) {
            search.startTime.val("");
            return alert("开始时间应小于结束时间!");
        }
    });

    search.startTime.keyup(function () {
        search.startTime.val("");
    });
    search.endTime.keyup(function () {
        search.endTime.val("");
    });

    /**
     * 获取举报初始化数据
     */
    $.get("admin/caseBack/showCaseByCompany.do", function (res, state) {
        if (state == "success") {
            var json = JSON.parse(res);
            setReportData(json.caseList);
            setPageBar(json.page);
        }
    });

    /**
     * 获取分页数据
     * @param pageNum
     */
    function getReports(pageNum) {
        var data = "startTime=" + search.startTime.val() + "&endTime=" + search.endTime.val() + "&caseState=" + search.caseState.find("option:selected").val()
            + "&trackingNo=" + search.trackingNo.val() + "&rtList=" + search.rtList.val() + "&keyWord=" + search.keyword.val() + "&pageNow=" + pageNum;
        $.post("admin/caseBack/showCaseByCompany.do", data, function (res, state) {
            if (state == "success") {
                var json = JSON.parse(res);
                setReportData(json.caseList);
            }
        });
    }

    /**
     * 设置举报列表数据
     * @param a
     */
    function setReportData(a) {
        $("#reports").empty();
        $.each(a, function (i, caseInfo) {
            var tr = $("<tr/>");
            var td1 = $("<td/>").css("width", "50px");
            var input1 = $("<input/>").attr("type", "checkbox").attr("data-id", caseInfo.rcId).val(true);
            td1.append(input1);
            var td2 = $("<td/>").css("width", "60px").text(caseInfo.reporter == null ? "匿名" : caseInfo.reporter.name);
            var td3 = $("<td/>").text(caseInfo.trackingNo);
            var td4 = $("<td/>").css("width", "180px").text(formatDate(caseInfo.createTime));
            var td5 = $("<td/>").css("width", "80px").text(caseInfo.caseState == 1 ? "新建" : caseInfo.caseState == 2 ? "已查看" : caseInfo.caseState == 3 ? "处理中" :
                caseInfo.caseState == 4 ? "处理完毕" : caseInfo.caseState == 5 ? "关闭案件" : "");
            var td6 = $("<td/>").css("width", "240px").text(caseInfo.company.companyName);
            var td7 = $("<td/>").text(caseInfo.rtList);
            var td8 = $("<td/>").css("width", "130px");
            var up = $("<input/>").attr("type", "button").addClass("btn btn-link").val("修改");
            up.click(function () {
                if(userCompanyId!=caseInfo.currentHandler.companyId){
                    if (caseInfo.currentHandler.companyId <= 1) {
                        return alert('案件已交由平台方处理，请耐心等待处理结果，或联系平台管理方');
                    } else {
                        return alert('案件未交由平台方处理，您目前只能查看');
                    }
                }

                var upStatePanle = $("#upStatePanle");
                $("#upStateId").val(caseInfo.rcId);
                $("#upStateCompanyId").val(caseInfo.company.companyId);
                upStatePanle.find("p").text("案件编号："+caseInfo.trackingNo);
                upStatePanle.find("select").get(0).value = caseInfo.caseState;
                if(caseInfo.currentHandler.companyId<=1){
                    upStatePanle.find("input[type=checkbox]").get(0).checked = true;
                }else{
                    upStatePanle.find("input[type=checkbox]").get(0).checked = false;
                }

                $("#updataReportStatus").modal('show');
            });
            var look = $("<input/>").attr("type", "button").addClass("btn btn-link").val("查看");
            look.click(function () {
                var url = "admin/caseBack/updateCaseState.do";
                var data = "rcId=" + caseInfo.rcId + "&state=" + 2
                    + "&sendToPlatform=0&companyId=" + caseInfo.company.companyId;
                if (caseInfo.caseState < 2 && "${user.userCompany.companyId}" == caseInfo.currentHandler.companyId) {
                    $.post(url, data, function (res, status) {
                        if (status == "success") {
                            if (res == "success") {
                                var pageNum = parseInt($("#pageBar li.active").children().text());
                                getReports(pageNum);
                            }
                        }
                    });
                }
                $("#reprotInfoPanel iframe").attr("src", "admin/caseBack/showCaseById.do?rcId=" + caseInfo.rcId);
                $("#reprotInfoPanel").modal('show');
            });
            td8.append(up).append(look);
            tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
            $("#reports").append(tr);
        });
    }

    /**
     * 分页操作
     */
    function setPageBar(str) {
        $("#pageBar").bs_pagination({
            totalPages: str.totalPageCount,
            totalCount: str.totalCount,
            onChangePage: function (event, data) {
                getReports(data.currentPage);
            }
        });
    }

    /**
     * 格式化时间
     */
    function formatDate(str) {
        if (str == null) return "";
        var date = new Date(str);
        var mounth = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        return date.getFullYear() + "-" + mounth + "-" + day + " " + h + ":" + m + ":" + s;
    }


    $("#submitSelect").click(function () {
        getReports(1);
    });


    /**
     * 导出操作
     */
    $("#exportBtn").click(function () {
        var i = 0;
        var ids = [];
        $('tbody tr td:nth-child(1) input').each(function () {
            if ($(this).filter(':checked').val()) {
                i++;
                ids.push($(this).attr("data-id"));
            }
        });
        if (i > 0) {
            $.get("admin/caseBack/downloadCases.do?case=" + ids, function (res, state) {
                if (state == "success") {
                    console.log(res);
                    location.href = res;
                } else {
                    alert('导出失败!');
                }
            });
            //TODO
        } else {
            alert("请选择需要导出的举报信息!");
        }
    });


//        $(window.parent.document).find(".badge").text("" + i);
    $("#updataCancel").click(function () {
        $("#updataReportStatus").modal('hide');
    });
    /**
     * 更新案件状态按钮被点击
     */
    $("#updataBtn").click(function () {
        if ($("#status").find("option:selected").val() == "") {
            return alert("请选择需要改变的状态!");
        }
        var url = "admin/caseBack/updateCaseState.do";
        var sendToPlatform = "0";
        if ($("#sendToPlatform").is(':checked')) {
            sendToPlatform = "1";
        }
        var data = "rcId=" + $("#upStateId").val() + "&state=" + $("#status").find("option:selected").val()
            + "&sendToPlatform=" + sendToPlatform + "&companyId=" + $("#upStateCompanyId").val();
        console.log(data);
        $("#updataReportStatus").modal('hide');
        $.post(url, data, function (res, status) {
            if (status == "success") {
                if (res == "success") {
                    Modal.alert({msg: "操作成功!"})
                        .on(function (e) {
                            var pageNum = parseInt($("#pageBar li.active").children().text());
                            getReports(pageNum);
                        });

                } else {
                    alert("操作失败!");
                }
            } else {
                alert("操作失败!");
            }
        });
    });

    /*$("#selectStatus").change(function () {
     $("tbody tr td:nth-child(3)").each(function () {
     if ($(this).text() == $("#selectStatus").find("option:selected").text()) {
     $(this).parent().removeClass("hidden");
     } else {
     $(this).parent().addClass("hidden");
     }
     if ($("#selectStatus").find("option:selected").val() == "-1") {
     $(this).parent().removeClass("hidden");
     }
     });
     });*/

    //按时间段查看案件
    var dateBtn = {
        oneMounth: $("#oneMounth"),
        threeMounth: $("#threeMounth"),
        oneYear: $("#oneYear")
    }
    dateBtn.oneMounth.click(function () {
        selectByDate(1);
    });
    dateBtn.threeMounth.click(function () {
        selectByDate(3);
    });
    dateBtn.oneYear.click(function () {
        var cDate = new Date();
        var year = cDate.getFullYear();
        var mounth = cDate.getMonth() + 1;
        var day = cDate.getDate();
        var cTime = year + "-" + (mounth < 10 ? "0" + mounth : mounth) + "-" + (day < 10 ? "0" + day : day);
        var nTime = (year - 1) + "-" + (mounth < 10 ? "0" + mounth : mounth) + "-" + (day < 10 ? "0" + day : day);
        search.startTime.val(nTime);
        search.endTime.val(cTime);
        getReports(1);
    });

    function selectByDate(n) {
        var cDate = new Date();
        var year = cDate.getFullYear();
        var mounth = cDate.getMonth() + 1;
        var day = cDate.getDate();
        var cTime = year + "-" + (mounth < 10 ? "0" + mounth : mounth) + "-" + (day < 10 ? "0" + day : day);
        var nTime = year + "-" + (mounth - n < 10 ? "0" + (mounth - n) : mounth - n) + "-" + (day < 10 ? "0" + day : day);
        search.startTime.val(nTime);
        search.endTime.val(cTime);
        getReports(1);
    }

    //按时间段 END
});