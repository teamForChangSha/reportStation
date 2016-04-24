$(function () {

    /*获取企业列表*/
    $.get("admin/companyBack/getCompanyPage.do", function (res, state) {
        if (state == "success") {
            var json = JSON.parse(res);
            setCompanyListData(json.companyList);
            setPageBar(json.page);
        }
    });
    function getUserList(pageNum) {
        $.get("admin/companyBack/getCompanyPage.do?pageNow=" + pageNum, function (res, state) {
            if (state == "success") {
                var json = JSON.parse(res);
                setCompanyListData(json.companyList);
            }
        });
    }

    /*设置企业数据列表*/
    function setCompanyListData(a) {
        $("#companyList").empty();
        $.each(a, function (i, company) {
            var tr = $("<tr/>");
            var td1 = $("<td/>").text(company.companyName);
            var td2 = $("<td/>").text("暂无字段");
            var td3 = $("<td/>").text("暂无字段");
            var td4 = $("<td/>");
            switch (company.companyState) {
                case 1:
                    td4.text("正常");
                    break;
                case 2:
                    td4.text("待审核");
                    break;
                default :
                    td4.text("默认0");
                    break;
            }
            var td5 = $("<td/>").text("暂无字段");
            var td6 = $("<td/>").text(company.phone);
            var td7 = $("<td/>");
            var div = $("<div/>").addClass("btn-group");
            var btn = $("<button/>").addClass("btn btn-default dropdown-toggle")
                .attr("data-toggle", "dropdown").attr("aria-haspopup", "true").attr("aria-expanded", "false").text("操作");
            var span = $("<span/>").addClass("caret");
            var menu = $("<ul/>").addClass("dropdown-menu");

            var li1 = $("<li/>");
            var a1 = $("<a/>").text("修改信息");
            li1.append(a1);
            var li2 = $("<li/>");
            var a2 = $("<a/>").text("删除");
            li2.append(a2);
            menu.append(li1).append(li2);
            btn.append(span);
            div.append(btn).append(menu);
            td7.append(div);
            tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
            $("#companyList").append(tr);
        });
    }

    /*设置分页*/
    function setPageBar(str) {
        $("#pageBar").createPage({
            pageCount: str.totalPageCount,
            current: str.pageNow,
            backFn: function (p) {
                getUserList(p);
            }
        });
        $("#pageText").text('共' + str.totalPageCount + "页，" + str.totalCount + "条");
    }

    /*格式化时间*/
    function formatDate(str) {
        if (str == null) return;
        var date = new Date(str);
        var mounth = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        return date.getFullYear() + "-" + mounth + "-" + day + " " + h + ":" + m + ":" + s;
    }

    for (var i = 0; i < 3; i++) {
        var tr = $("<tr/>");
        var td1 = $("<td/>").text("2015-12-01 14:20:30");
        var td2 = $("<td/>").text("苹果中国");
        var td3 = $("<td/>").text("18600000000");
        var td4 = $("<td/>");
        var a1 = $("<a/>").attr("class", "btn btn-link").text("修改信息");
        a1.click(function () {
            hiddenPanle();
            panle.updataPwd.removeClass("hidden");
            //TODO
        });
        var a2 = $("<a/>").attr("class", "btn btn-link").text("停用");
        a2.click(function () {
            hiddenPanle();
            panle.updataUserInfo.removeClass("hidden");
            //TODO
        });
        td4.append(a1).append(a2);
        tr.append(td1).append(td2).append(td3).append(td4);
//            $("tbody").append(tr);
    }


    var t = 0;
    var addBool = true, upBool = true;
    var temp = $("#hangye");
    /*添加企业*/
    $("input[name=addCompany]").click(function () {
        $("#addCompanPanel").modal("show");
    });
    window.addSelctedIndustry = function () {
        $("#addForm").hide();
        t = 1;
        addBool = false;
        $("#addCompanHtml").append(temp);
        temp.removeClass("hide");
    };
    window.addClose = function () {
        if (addBool) {
            $("#addCompanPanel").modal("hide");
        } else {
            $("#addCompanHtml").children("#hangye").remove();
            $("#addForm").show();
            addBool = true;
        }
    };
    window.addLogoChange = function (ele) {
        if (window.File && window.FileList && window.FileReader) {
            var oFReader = new FileReader();
            oFReader.onload = function (file) {
                var image = new Image();
                image.src = file.target.result;
                var h = image.height;
                var w = image.width;
                var fileSize = $(ele).get(0).files[0].size / 1024;
                if (h > 200 || w > 200 || fileSize > 1024) {
                    $("#addLogo").val("");
                    return alert("图片宽高不能超过200像素，大小不能超过1M");
                }
                $("#addImg").attr("src", file.target.result);
            };
            oFReader.readAsDataURL($(ele).get(0).files[0]);
        }
    };
    window.sendAdd = function () {
        $("#addForm").submit();
    };
    /*添加企业 END*/

    /*更新企业*/
    $("input[name=import]").click(function () {
        $("#upCompanPanel").modal("show");
    });
    window.updataCompany = function (id) {
        //TODO
        $("#upCompanPanel").modal("show");
    };
    window.upSelctedIndustry = function () {
        $("#upForm").hide();
        t = 2;
        upBool = false;
        $("#upCompanHtml").append(temp);
        temp.removeClass("hide");
    };
    window.upClose = function () {
        if (upBool) {
            $("#upCompanPanel").modal("hide");
        } else {
            $("#upCompanHtml").children("#hangye").remove();
            $("#upForm").show();
            upBool = true;
        }
    };
    window.upLogoChange = function (ele) {
        if (window.File && window.FileList && window.FileReader) {
            var oFReader = new FileReader();
            oFReader.onload = function (file) {
                var image = new Image();
                image.src = file.target.result;
                var h = image.height;
                var w = image.width;
                var fileSize = $(ele).get(0).files[0].size / 1024;
                if (h > 200 || w > 200 || fileSize > 1024) {
                    $("#upLogo").val("");
                    return alert("图片宽高不能超过200像素，大小不能超过1M");
                }
                $("#upImg").attr("src", file.target.result);
            };
            oFReader.readAsDataURL($(ele).get(0).files[0]);
        }
    };
    window.sendUp = function () {
        $("#upForm").submit();
    };
    /*更新企业 END*/

    /*行业表格每一项被点击事件*/
    window.getInd = function (ele) {
        if (t == 1) {
            $("#addIndustry").val($(ele).text());
            $("#addCompanHtml").children("#hangye").remove();
            $("#addForm").show();
            addBool = true;
        }
        if (t == 2) {
            $("#upIndustry").val($(ele).text());
            $("#upCompanHtml").children("#hangye").remove();
            $("#upForm").show();
            upBool = true;
        }
    };

    /*删除企业*/
    window.delCompany = function (id) {
        Modal.confirm({
            title: '警告',
            msg: '你确定要删除吗?',
        }).on(function (e) {
            if (e) {
                $.get("admin/companyBack/deleteCompanyBranch.do?branchId=" + id, function (res, status) {
                    alertMsg(res, status);
                });
            }
        });
    };

    function alr(masg) {
        alert(masg);
    }

    /* 弹出操作消息 */
    function alertMsg(res, status) {
        if (status == "success") {
            if (res == "success") {
                Modal.alert({
                    msg: '操作成功！',
                }).on(function (e) {
                    location.reload();
                });
            } else {
                Modal.alert({
                    msg: '操作失败！',
                });
            }
        } else {
            Modal.alert({
                msg: '操作失败！',
            });
        }
    }
});