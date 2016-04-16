<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <meta http-equiv="cache-control" content="no-cache">
    <title></title>
    <link rel="stylesheet" type="text/css" href="jsp/css/sort-style.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<style>

    input::-moz-focus-inner {
        padding: 0;
        border: 0;

    }
    .form-control::-moz-placeholder {
        color: #999;
        opacity: 1;

    }
    .form-control {
        display: block;
        width: 100%;
        height: 16px;
        padding: 6px 12px;
        font-size: 14px;
        line-height: 1.42857143;
        color: #555;
        background-color: #fff;
        background-image: none;
        border: 1px solid #ccc;
        border-radius: 4px;
        -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
        box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
        -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
        -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
        transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    }
    input{
        outline: 0;
        margin: 0;
        font-family: inherit;
        font: inherit;
    }
</style>
<body>
<header class="fixed">
    <div class="header">
        <input id="company" type="text" placeholder="搜索企业名称" class="form-control"/>
    </div>
</header>
<div id="letter">
</div>
<div id="sortBox" class="sort_box">
</div>
<div class="initials">
    <ul id="initials">
    </ul>
</div>
<p id="loadData" style="text-align: center">数据加载中...</p>
</body>
<script>
    $(function () {
        $.post("company/getAllByName.do", "companyName=", function (res) {
            if (res == null || res.length < 0)
                return;
            $("#loadData").hide();
            $(JSON.parse(res)).each(function () {
                var part = $("<div/>").addClass("sort_list");
                var chil = $("<div/>").addClass("num_name").text(this.companyName).attr("data-id", this.companyId);
                chil.click(function () {
                    window.parent.window.hideModal($(this).text(),$(this).attr("data-id"));
                });
                part.append(chil);
                $("#sortBox").append(part);
            });
            initials();
        });

        $(document).bind('click', function (e) {
            if($(e.target).parent().get(0).id=="initials"){
                $("#sortBox .sort_list").show();
                $("#sortBox .sort_letter").show();
            }
        });

        function initials() {//公众号排序
            var SortList = $(".sort_list");
            var SortBox = $(".sort_box");
            SortList.sort(asc_sort).appendTo('.sort_box');//按首字母排序
            function asc_sort(a, b) {
                return makePy($(b).find('.num_name').text().charAt(0))[0].toUpperCase() < makePy($(a).find('.num_name').text().charAt(0))[0].toUpperCase() ? 1 : -1;
            }

            var initials = [];
            var num = 0;
            SortList.each(function (i) {
                var initial = makePy($(this).find('.num_name').text().charAt(0))[0].toUpperCase();
                if (initial >= 'A' && initial <= 'Z') {
                    if (initials.indexOf(initial) === -1)
                        initials.push(initial);
                } else {
                    num++;
                }

            });

            $.each(initials, function (index, value) {//添加首字母标签
                SortBox.append('<div class="sort_letter" id="' + value + '">' + value + '</div>');
            });
            if (num != 0) {
                SortBox.append('<div class="sort_letter" id="default">#</div>');
            }

            for (var i = 0; i < SortList.length; i++) {//插入到对应的首字母后面
                var letter = makePy(SortList.eq(i).find('.num_name').text().charAt(0))[0].toUpperCase();
                switch (letter) {
                    case "A":
                        $('#A').after(SortList.eq(i));
                        break;
                    case "B":
                        $('#B').after(SortList.eq(i));
                        break;
                    case "C":
                        $('#C').after(SortList.eq(i));
                        break;
                    case "D":
                        $('#D').after(SortList.eq(i));
                        break;
                    case "E":
                        $('#E').after(SortList.eq(i));
                        break;
                    case "F":
                        $('#F').after(SortList.eq(i));
                        break;
                    case "G":
                        $('#G').after(SortList.eq(i));
                        break;
                    case "H":
                        $('#H').after(SortList.eq(i));
                        break;
                    case "I":
                        $('#I').after(SortList.eq(i));
                        break;
                    case "J":
                        $('#J').after(SortList.eq(i));
                        break;
                    case "K":
                        $('#K').after(SortList.eq(i));
                        break;
                    case "L":
                        $('#L').after(SortList.eq(i));
                        break;
                    case "M":
                        $('#M').after(SortList.eq(i));
                        break;
                    case "N":
                        $('#N').after(SortList.eq(i));
                        break;
                    case "O":
                        $('#O').after(SortList.eq(i));
                        break;
                    case "P":
                        $('#P').after(SortList.eq(i));
                        break;
                    case "Q":
                        $('#Q').after(SortList.eq(i));
                        break;
                    case "R":
                        $('#R').after(SortList.eq(i));
                        break;
                    case "S":
                        $('#S').after(SortList.eq(i));
                        break;
                    case "T":
                        $('#T').after(SortList.eq(i));
                        break;
                    case "U":
                        $('#U').after(SortList.eq(i));
                        break;
                    case "V":
                        $('#V').after(SortList.eq(i));
                        break;
                    case "W":
                        $('#W').after(SortList.eq(i));
                        break;
                    case "X":
                        $('#X').after(SortList.eq(i));
                        break;
                    case "Y":
                        $('#Y').after(SortList.eq(i));
                        break;
                    case "Z":
                        $('#Z').after(SortList.eq(i));
                        break;
                    default:
                        $('#default').after(SortList.eq(i));
                        break;
                }
            }
            ;
            $("#company").keyup(function(){
                $("#sortBox .sort_list").hide();
                $("#sortBox .sort_letter").hide();
                console.log($(this).val());
                $("#sortBox .sort_list:contains("+$(this).val()+")").show();
            });
        }
    });
</script>
<script type="text/javascript" src="jsp/js/jquery.charfirst.pinyin.js"></script>
<script type="text/javascript" src="jsp/js/sort.js"></script>
</html>
