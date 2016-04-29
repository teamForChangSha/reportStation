$(function () {
    var companyName = $("#companyName");

    /*获取企业列表*/
    $.get("admin/companyBack/getCompanyPage.do", function (res, state) {
        if (state == "success") {
            var json = JSON.parse(res);
            setCompanyListData(json.companyList);
            setPageBar(json.page);
        }
    });
    function getCompanyList(pageNum, companyName) {
        $.post("admin/companyBack/getCompanyPage.do", "pageNow=" + pageNum + "&companyName=" + companyName, function (res, state) {
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
            var td = $("<td/>");
            var checkbox = $("<input/>").attr("type", "checkbox").attr("data-id", company.companyId);
            td.append(checkbox);
            var td1 = $("<td/>").text(company.companyName);
            var td2 = $("<td/>").text(company.clientCompany == null ? '' : formatDate(company.clientCompany.createTime));
            var td3 = $("<td/>").text(company.clientCompany == null ? "否" : "是");
            var td4 = $("<td/>");
            switch (company.companyState) {
                case 1:
                    td4.text("正常");
                    break;
                case 2:
                    td4.text("待审核");
                    break;
            }
            var td5 = $("<td/>").text(company.clientCompany == null ? '' : formatDate(company.clientCompany.expiryDate));
            var td6 = $("<td/>").text(company.phone == null ? "" : company.phone);
            var td7 = $("<td/>");
            var div = $("<div/>").addClass("btn-group");
            var btn = $("<button/>").addClass("btn btn-default dropdown-toggle")
                .attr("data-toggle", "dropdown").attr("aria-haspopup", "true").attr("aria-expanded", "false").text("操作");
            var span = $("<span/>").addClass("caret");
            var menu = $("<ul/>").addClass("dropdown-menu");

            var li1 = $("<li/>");
            var a1 = $("<a/>").text("修改信息").attr("data-toggle", "modal").attr("data-target", "#upCompanPanel");
            a1.click(function () {
                setUpFormData(company.companyId, company.companyName, company.companyCode, company.stockCode, company.phone, company.companyState
                    , company.companyType, company.industries, company.description
                    , company.otherInfo == null ? "" : company.otherInfo.serviceProtocol, company.otherInfo == null ? "" : company.otherInfo.spHtml
                    , company.otherInfo == null ? "" : company.otherInfo.logoUrl);
            });
            li1.append(a1);
            var li2 = $("<li/>");
            var a2 = $("<a/>").text("删除");
            a2.click(function () {
                delCompany(company.companyId);
            });
            li2.append(a2);
            menu.append(li1).append(li2);
            btn.append(span);
            div.append(btn).append(menu);
            td7.append(div);
            tr.append(td).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
            $("#companyList").append(tr);
        });
    }

    /**
     * 设置更新表单数据
     */
    function setUpFormData(id, name, code, stockCode, phone, state, type, industry, desc, spr, spHtml, path) {
        $("#upCompanyId").val(id);
        $("#upCompanyName").val(name);
        $("#upCompanyCode").val(code);
        $("#upStockCode").val(stockCode);
        $("#upPhone").val(phone);
        $("#upCompanyState").get(0).value = state;
        $("#upCompanyType").get(0).value = type;
        $("#upIndustry").val(industry);
        $("#upDescription").val(desc);
        $("#upServiceProtocol").val(spr);
        $("#upSpHtml").val(spHtml);
        if (path != null && path != "") {
            $("#upImg").attr("src", path);
        }
    }

    /*设置分页*/
    function setPageBar(str) {
        $("#pageBar").createPage({
            pageCount: str.totalPageCount,
            current: str.pageNow,
            backFn: function (p) {
                getCompanyList(p, companyName.val());
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

    var t = 0;
    var bool = true;

    /**
     * 选择企业
     * @param a form表单
     * @param b 1为选择企业，2为修改企业
     * @param c 内容区域
     */
    function selctedIndustry(a, b, c) {
        a.hide();
        t = b;
        bool = false;
        c.append($("#hangye").html());
    };

    /**
     * 关闭对话框
     * @param a 对话框本身
     * @param b 内容区域
     * @param c form表单
     */
    function close(a, b, c) {
        if (bool) {
            a.modal("hide");
        } else {
            b.children("table").remove();
            c.show();
            bool = true;
        }
    };

    /**
     * 选择企业图片
     * @param a 图片元素 img
     * @param b 选择图片的input
     */
    function logoChange(a, b) {
        if (window.File && window.FileList && window.FileReader) {
            var oFReader = new FileReader();
            oFReader.onload = function (file) {
                var image = new Image();
                image.src = file.target.result;
                var h = image.height;
                var w = image.width;
                var fileSize = $(b).get(0).files[0].size / 1024;
                if (h > 200 || w > 200 || fileSize > 1024) {
                    $(b).val("");
                    return alert("图片宽高不能超过200像素，大小不能超过1M");
                }
                a.attr("src", file.target.result);
            };
            oFReader.readAsDataURL($(b).get(0).files[0]);
        }
    };

    $("#addSelctedIndustry").click(function () {
        selctedIndustry($("#addForm"), 1, $("#addCompanHtml"));
    });
    $("#addClose").click(function () {
        close($("#addCompanPanel"), $("#addCompanHtml"), $("#addForm"));
    });
    $("#addLogoChange").change(function () {
        logoChange($("#addImg"), this);
    });
    window.sendAdd = function () {
        if (isEmty($("#addCompanyName").val())) {
            return alert("请输入公司名称");
        }
        if (!rePhone($("#addPhone").val())) {
            return alert("请输入正确的联系电话");
        }
        if ($("#addCompanyType").find("option:selected").val() == "0") {
            return alert("请选择公司类型");
        }
        if (isEmty($("#addIndustry").val())) {
            return alert("请选择所属行业");
        }
        $("#addCompanPanel").modal("hide");
        $("#addForm").ajaxSubmit(function (res, state) {
            if (state == "success") {
                if (res == "success") {
                    Modal.alert({
                        msg: '操作成功！',
                    }).on(function () {
                        var pageNum = parseInt($("#pageBar li.active").children().text());
                        getCompanyList(pageNum, companyName.val());
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
        });
    };

    $("#upSelctedIndustry").click(function () {
        selctedIndustry($("#upForm"), 2, $("#upCompanHtml"));
    });
    $("#upClose").click(function () {
        close($("#upCompanPanel"), $("#upCompanHtml"), $("#upForm"));
    });
    $("#upLogoChange").change(function () {
        logoChange($("#upImg"), this);
    });
    window.sendUp = function () {
        if (isEmty($("#upCompanyName").val())) {
            return alert("请输入公司名称");
        }
        if (!rePhone($("#upPhone").val())) {
            return alert("请输入正确的联系电话");
        }
        if ($("#upCompanyType").find("option:selected").val() == "0") {
            return alert("请选择公司类型");
        }
        if (isEmty($("#upIndustry").val())) {
            return alert("请选择所属行业");
        }
        $("#upCompanPanel").modal("hide");
        $("#upForm").ajaxSubmit(function (res, state) {
            if (state == "success") {
                if (res == "success") {
                    Modal.alert({
                        msg: '操作成功！',
                    }).on(function () {
                        var pageNum = parseInt($("#pageBar li.active").children().text());
                        getCompanyList(pageNum, companyName.val());
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
        });
    };

    /*行业表格每一项被点击事件*/
    window.getInd = function (ele) {
        if (t == 1) {
            $("#addIndustry").val($(ele).text());
            $("#addCompanHtml").children("table").remove();
            $("#addForm").show();
            bool = true;
        }
        if (t == 2) {
            $("#upIndustry").val($(ele).text());
            $("#upCompanHtml").children("table").remove();
            $("#upForm").show();
            bool = true;
        }
    };

    $("#selected").click(function () {
        getCompanyList(1, companyName.val());
    });

    /**
     * 删除企业
     */
    function delCompany(id) {
        Modal.confirm({
            title: '警告',
            msg: '你确定要删除吗?',
        }).on(function (e) {
            if (e) {
                $.post("admin/companyBack/delCompanyByIds.do", "companyId=" + id, function (res, state) {
                    if (state == "success") {
                        if (res == "success") {
                            Modal.alert({
                                msg: '操作成功！',
                            }).on(function () {
                                var pageNum = parseInt($("#pageBar li.active").children().text());
                                getCompanyList(pageNum, companyName.val());
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
                });
            }
        });
    };

    /**
     * 批量删除企业
     */
    window.delAll = function () {
        var i = 0;
        var ids = [];
        $('tbody tr td:nth-child(1) input').each(function () {
            if ($(this).filter(':checked').val()) {
                i++;
                ids.push($(this).attr("data-id"));
            }
        });
        if (i > 0) {
            alert(ids)
            $.get("admin/companyBack/delCompanyByIds.do?companyIds=" + ids, function (res, state) {
                if (state == "success") {
                    if (res == "success") {
                        Modal.alert({msg: '操作成功!'}).on(function () {
                            var pageNum = parseInt($("#pageBar li.active").children().text());
                            getCompanyList(pageNum, companyName.val());
                        });
                    } else {
                        Modal.alert({msg: '操作失败!'});
                    }
                } else {
                    Modal.alert({msg: '操作失败!'});
                }
            });
        } else {
            Modal.alert({msg: '请选择需要删除的企业!'});
        }
    }

    /* 判断是否为空 */
    function isEmty(str) {
        str = $.trim(str);
        if (str == null || str.length <= 0 || str == "") {
            return true;
        }
        return false;
    }

    var phoneReg = /^((0?1[358]\d{9})|((0(10|2[1-3]|[3-9]\d{2}))?[1-9]\d{6,7}))$/;

    function rePhone(str) {
        if (!phoneReg.test(str)) {
            return false;
        }
        return true;
    }
});