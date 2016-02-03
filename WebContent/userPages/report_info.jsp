<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/common_top.css" />
		<script src="../bootstrap3/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../bootstrap3/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<title></title>
		<style type="text/css">
			.navbar-text {
				float: inherit;
			}
			
			label {
				font-weight: 400;
			}
			
			.form-info {
				display: block;
				width: 100%;
				height: 34px;
				padding: 8px 12px;
				font-size: 14px;
				line-height: 1.42857;
				color: #555;
			}
			
			#content {
				margin-top: 30px;
				padding: 20px;
				border: 1px solid #DDDDDD;
				border-radius: 4px;
				box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.075);
			}
		</style>
	</head>

	<body>
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation" id="top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
				</div>

				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav pull-right">
						<li><a href="../jsp/index.jsp">é¦é¡µ</a></li>
						<li><a href="userPages/privacy.html" target="_blank">åä¸è¡ä¸ºåéå¾·åå</a></li>
						<li><a href="#">å¸¸è§é®é¢</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="container">
			<div class="row">
				<div class="col-sm-2">

				</div>
				<div class="col-sm-8 form-horizontal" id="content">
					<div class="form-group text-center">
						<h3><span class="label label-danger" id="caseId">æ¡ä»¶ç¼å·ï¼AB09C80567</span></h3>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">ç»ç»/æºæï¼</label>
						<div class="col-sm-8">
							<span class="form-info" id="organization"><strong>Oracle Corporation</strong></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">åçäºæçå°åï¼</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseAddr">Other / Do Not Wish to Disclose </span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">çä»½ï¼</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseProvince">æ¹åç</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">åå¸ï¼</label>
						<div class="col-sm-8">
							<span class="form-info" id="caseCity">é¿æ²å¸</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">é®ç¼ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">421000</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æ¯å¦ä¸ºXXXçåå·¥ï¼</label>
						<div class="col-sm-8">
							<span class="form-info" id="isEmployees">æ¯</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">å§åï¼</label>
						<div class="col-sm-8">
							<span class="form-info">å¼ ä¸</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">è¯ä»¶å·ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">430421000000000000</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">ææºå·ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">18600000000</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æä½³èç³»æ¹å¼ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">æ©ä¸09ï¼00å°11ï¼00æè18ï¼00å°21ï¼00</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">äºä»¶ç¸å³äººåï¼</label>
						<div class="col-sm-8">
							<span class="form-info">å¼ ä¸ï¼æ»ç»çï¼ï¼æåï¼è´¢å¡ç»çï¼ï¼çäºï¼å¸åºç»çï¼</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">ç¸å³çç®¡äººå/ç®¡çäººåï¼</label>
						<div class="col-sm-8">
							<span class="form-info">æ </span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">ç®¡çäººåæ¯å¦æ³¨æå°æ­¤äºï¼</label>
						<div class="col-sm-8">
							<span class="form-info">æ¯</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æ­¤äºä»¶æ»ä½ç¹å¾ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">æ¶é´æ»ä½ç¹å¾æè¿°</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æ­¤äºä»¶é¢è®¡ä»·å¼ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">USD999-USD9999</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">è¯¥äºä»¶åççæ¶é´ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">2015-12-01</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">è¯¥äºä»¶æç»­çæ¶é´ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">1è³3ä¸ªæ</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æ­¤äºä»¶çè·åéå¾ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">æè§å¯å¾ç¥</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">ä¹åæ¯å¦æ¥åè¿æ­¤äºä»¶ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">åç®¡çäººååé¦è¿æ­¤äºä»¶</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">è¯å¾éèæ­¤é®é¢çäººä»¥åä»ä»¬éèéåçæ­¥éª¤ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">å¼ ä¸ä¸æ¿åè¯æåæåä¸æ¿åè¯çäº</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æ­¤äºä»¶çå·ä½æè¿°ç»èï¼</label>
						<div class="col-sm-8">
							<span class="form-info">å¼ ä¸ä¸æ¿åè¯æåæåä¸æ¿åè¯çäº</span>
						</div>
					</div>
					<div class="page-header"></div>
					<div class="form-group">
						<label class="col-sm-4 control-label">å¶ä»ææï¼</label>
						<div class="col-sm-8">
							<p><a href="#">äºä»¶å¾ç1.jpg</a></p>
							<p><a href="#">äºä»¶å¾ç1.jpg</a></p>
							<p><a href="#">äºä»¶å¾ç1.jpg</a></p>
							<p><a href="#">äºä»¶å¾ç1.jpg</a></p>
						</div>
					</div>
					<div class="page-header"></div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æ¡ä»¶ç¶æï¼</label>
						<div class="col-sm-8">
							<span class="form-info">è¢«ä¸¾æ¥æ¹å¤çä¸­</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">æ¡ä»¶å¤çæµç¨ï¼</label>
						<div class="col-sm-8">
							<span class="form-info">ç®¡çåå·²å¤ç<time class="pull-right">2015-12-01</time></span>
							<span class="form-info">è¢«ä¸¾æ¥äººå·²å¤ç<time class="pull-right">2015-12-01</time></span>
							<span class="form-info">ä¸¾æ¥äººå¤ç<time class="pull-right">2015-12-01</time></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">ç®¡çåï¼</label>
						<div class="col-sm-8">
							<textarea rows="3" readonly class="form-control">ç®¡çåå·²å¤ç</textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">è¢«ä¸¾æ¥æ¹ï¼</label>
						<div class="col-sm-8">
							<textarea rows="3" disabled class="form-control">è¢«ä¸¾æ¥æ¹æ­£å¨æ ¸å®</textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-4"></div>
						<div class="col-sm-8 text-right">
							<input id="addNote" type="button" class="btn btn-primary" value="å¢å " />
						</div>
					</div>
				</div>
			</div>
		</div>
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container text-center">
				<p class="navbar-text">Copyright Â© 2016-2018 ç¨æ·ä¸¾æ¥ç³»ç»</p>
			</div>
		</nav>
	</body>
	<script type="text/javascript">
		$(function() {
			var ele = {
				addNote: $("#addNote")
			}
			ele.addNote.click(function() {
				var rootDiv = $("<div/>").attr("class", "form-group");
				var lable = $("<label/>").attr("class", "col-sm-4 control-label").text("ä¸¾æ¥äºº");
				var div = $("<div/>").attr("class", "col-sm-8");
				var textarea = $("<textarea/>").attr("class", "form-control").attr("rows", 3);
				rootDiv.append(lable);
				div.append(textarea);
				rootDiv.append(div);
				$(this).parent().parent().before(rootDiv);
			});
		});
	</script>

</html>