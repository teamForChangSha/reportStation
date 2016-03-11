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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="Window-target" content="_top">
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="jsp/css/bootstrap-theme.min.css"/>
    <script src="jsp/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="jsp/js/md5.js" type="text/javascript" charset="utf-8"></script>
    <title>后台管理</title>
    <style type="text/css">
        body.lock {
            background: #393939;
        }

        .row {
            padding-top: 80px;
        }

        .brand {
            color: #fff;
        }

        input[name='loginName'] {
            background-image: url(jsp/css/img/input_icons.png);
            background-repeat: no-repeat;
            background-position: 0 -64px;
            padding: 6px 12px 6px 32px;
        }

        input[name='loginName']:focus {
            background-position: 0 -104px;
        }

        input[name='userPwd'] {
            background-image: url(jsp/css/img/input_icons.png);
            background-repeat: no-repeat;
            background-position: 0 -144px;
            padding: 6px 12px 6px 32px;
        }

        input[name='userPwd']:focus {
            background-position: 0 -184px;
        }

        .btn-link,
        .btn-link:hover {
            color: #fff;
        }

        .glyphicon-remove {
            color: #C12E2A;
        }

        .glyphicon {
            right: 12px;
        }
    </style>
    <script>
        if (window != top)
            top.location.href = location.href;
    </script>
</head>

<body class="lock">
<div class="container">
    <div class="row text-center">
        <h2 class="brand">
            后台管理系统
        </h2>
    </div>
    <div class="row">
        <div class="col-sm-4">

        </div>
        <div class="col-sm-4 text-center">
            <form>
                <div class="form-group">
                    <input type="text" name="loginName" class="form-control" placeholder="手机/用户名">
                    <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                </div>
                <div class="form-group">
                    <input type="password" name="userPwd" class="form-control" placeholder="密码">
                    <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
                </div>
                <div class="form-group">
                    <input type="button" name="loginBtn" value="登陆" class="btn btn-success form-control"/>
                </div>
                <div class="form-group">
                    <a href="javascript:;" id="forgetPwd" class="btn btn-link">忘记密码</a>
                    <a href="mailto:178972841@qq.com" class="btn btn-link">联系管理</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="jsp/js/model.js" type="text/javascript" charset="utf-8"></script>
</body>
<script type="text/javascript">
    $(function () {
        var user = {
            name: $("input[name=loginName]"),
            pwd: $("input[name=userPwd]"),
            btn: $("input[name=loginBtn]")
        }
        hiddenError(user.name);
        hiddenError(user.pwd);

        $("#forgetPwd").click(function () {
            Modal.alert({msg:'请联系管理员重置密码'});
        });

        user.pwd.get(0).onkeydown = function (e) {
            if (e.keyCode == 13) {
                login();
            }
        };
        user.btn.get(0).onclick = login;
        function login() {
            if (!showError(user.name)) {
                return;
            }
            if (!showError(user.pwd)) {
                return;
            }
            var data = "loginName=" + user.name.val() + "&userPwd=" + md5(user.pwd.val());
            $.post("admin/user/login.do", data, function (res, status) {
                if (status == "success") {
                    if (res == "success") {
                        location.href = "jsp/admin/admin.jsp";
                    } else {
                        Modal.alert({msg:res});
                    }
                }
            });
        };

        function showError(ele) {
            if (isEmty(ele.val())) {
                return false;
            }
            return true;
        }

        function hiddenError(ele) {
            ele.focus(function () {
                ele.next().removeClass("glyphicon-remove");
            });
        }

        function isEmty(str) {
            str = $.trim(str);
            if (str == null || str.length <= 0 || str == "") {
                return true;
            }
            return false;
        }
    });
</script>

</html>