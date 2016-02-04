<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="../bootstrap3/css/common_top.css" />
		<script src="../bootstrap3/js/jquery-1.12.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../bootstrap3/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
			.navbar-text {
				float: inherit;
			}
			
			#phone-input-group {
				padding-right: 15px;
				padding-left: 15px;
			}
			
			.form-file {
				padding: 6px 0;
				opacity: 0;
				filter: alpha(opacity=0);
				cursor: pointer;
			}
			
			.btn-default {
				position: absolute;
				z-index: -1;
			}
			
			.col-xs-6 {
				margin-bottom: 10px;
			}
			
			.glyphicon-remove-circle {
				position: absolute;
				right: 16px;
				top: 2px;
				cursor: pointer;
				color: #C12E2A;
			}
			
			.xinghao {
				color: #C12E2A;
				font-weight: bold;
				padding: 4px;
			}
			
			input[type=text].form-control {
				padding: 6px 32px 6px 12px;
			}
			
			label {
				font-weight: 400;
			}
			
			.navbar-right {
				margin-right: 15px;
			}
			
			#content,
			#bom {
				margin-top: 30px;
				padding: 20px;
				border: 1px solid #DDDDDD;
				border-radius: 4px;
				box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.075);
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
			
			.bg-info,
			.bg-danger {
				border-radius: 6px;
				padding: 6px;
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
			<form action="" method="post" class="form-horizontal">
				<div class="row">
					<div class="col-sm-2">

					</div>
					<div class="col-sm-8" id="content">
						<div class="form-group text-center">
							<span>(<span class="xinghao">*</span>ä¸ºå¿å¡«é¡¹)</span>
						</div>
						<div class="form-group text-center">
							<input type="checkbox" value="" />
							<label><span class="xinghao">*</span>æ¯ â æåæè¿é¡¹æ¥åç¸å³ç
								<a href="#" class="btn-link" data-toggle="modal" data-target="#agreeMentPanel">æ¡æ¬¾ä¸æ¡ä»¶</a>
							</label>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>è¯·æä¾ä¸ä¸ä¿¡æ¯</strong>
							</span>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">ç»ç»/æºæï¼</label>
							<div class="col-sm-8">
								<span class="form-info"><strong>Oracle Corporation</strong></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">åçäºæçå°åï¼</label>
							<div class="col-sm-8">
								<span class="form-info">Other / Do Not Wish to Disclose </span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">çä»½ï¼</label>
							<div class="col-sm-8">
								<span class="form-info">æ¹åç</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">åå¸ï¼</label>
							<div class="col-sm-8">
								<span class="form-info">é¿æ²å¸</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">é®ç¼ï¼</label>
							<div class="col-sm-8">
								<span class="form-info">421000</span>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>æ¨æ¯ XXX çåå·¥åï¼</strong>
							</span>
							<br />
							<label class="radio-inline">
								<input type="radio" name="employees" value="true" /> æ¯
							</label>
							<label class="radio-inline">
								<input type="radio" checked="true" name="employees" value="false" /> å¦
							</label>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>å¯¹äºè¿é¡¹æ¥åï¼æ¨æ¯å¦å¸æä¿æå¿åèº«ä»½ï¼</strong>
							</span>
							<br />
							<label class="radio-inline">
								<input type="radio" name="reportType" value="true" /> æ¯
							</label>
							<label class="radio-inline">
								<input type="radio" checked="true" name="reportType" value="false" /> å¦
							</label>
						</div>
						<div id="userInfo">
							<div class="form-group text-center">
								<span><strong>å¦ææ¨å¸æ Hyatt ç¥éæ¨çèº«ä»½ï¼è¯·å®æä»¥ä¸åå®¹ï¼</strong></span>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">æ¨çå§åï¼</label>
								<div class="col-sm-3">
									<input id="name" type="text" class="form-control" placeholder="åå­"></input>
								</div>
								<div class="col-sm-3">
									<input id="surnames" type="text" class="form-control" placeholder="å§æ°"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">æ¨çè¯ä»¶å·ï¼</label>
								<div class="col-sm-3">
									<select id="certificate" class="form-control">
										<option>-è¯·éæ©è¯ä»¶ç±»å-</option>
										<option>èº«ä»½è¯</option>
										<option>æ¤ç§</option>
									</select>
								</div>
								<div class="col-sm-4">
									<input id="certificateNum" type="text" class="form-control" placeholder="è¯ä»¶å·"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">ææºå·ï¼</label>
								<div class="input-group col-sm-5" id="phone-input-group">
									<input id="phone" type="text" maxlength="11" class="form-control">
									<span class="input-group-btn">
        								<button id="getCode" class="btn btn-default" type="button">è·åéªè¯ç </button>
      								</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">éªè¯ç ï¼</label>
								<div class="col-sm-3">
									<input id="code" type="text" maxlength="6" class="form-control"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">é®ç®±ï¼</label>
								<div class="col-sm-4">
									<input id="email" type="text" class="form-control"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">ä¸æ¨èç³»çæä½³æ¶é´åæ¹å¼ï¼</label>
								<div class="col-sm-7">
									<textarea type="text" rows="6" class="form-control"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--ä¸é¢ç-->
				<div class="row">
					<div class="col-sm-2">

					</div>
					<div class="col-sm-8" id="bom">
						<div class="form-group text-center">
							<h4 class="bg-info"><strong>æ¥å - ä¸å½çæ¸¸è¯´åæ¿æ²»æ´»å¨</strong></h4>
						</div>
						<div class="form-group text-center">
							<span><span class="xinghao">*</span><strong>è¯·è¯´æä¸æ­¤è¡ä¸ºç¸å³çäººçèº«ä»½</strong></span>
							<br />
							<span> 	ä¾å¦ï¼æ åæ°ï¼åé¨å®¡è®¡ä¸»ç®¡ï¼æªç¥ï¼æé´çç£å</span>
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-2">
								<span class="form-info">åå­</span>
							</div>
							<div class="col-sm-2">
								<span class="form-info">å§æ°</span>
							</div>
							<div class="col-sm-3">
								<span class="form-info">èè¡	</span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-3 control-label">
								#1
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" />
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" />
							</div>
							<div class="col-sm-3">
								<input type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-3 control-label">
								#2
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" />
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" />
							</div>
							<div class="col-sm-3">
								<input type="text" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-3 control-label">
								#3
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" />
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" />
							</div>
							<div class="col-sm-3">
								<input type="text" class="form-control" />
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong>æ¨æ¯å¦æçæç¥éçç£æç®¡çäººåä¸æ­¤æå³ï¼</strong></span>
							<br />
							<label class="radio-inline">
								<input type="radio" id="niming" name="reportType" /> æ¯
							</label>
							<label class="radio-inline">
								<input type="radio" id="shiming" name="reportType" /> å¦
							</label>
							<label class="radio-inline">
								<input type="radio" id="shiming" name="reportType" /> ä¸ç¥é / ä¸æ¿æéé²
							</label>
							<p>å¦æéæ©æ¯ï¼è¯·æåºæ¯è°ï¼</p>
							<div class="col-sm-3">

							</div>
							<div class="col-sm-7">
								<textarea type="text" rows="6" class="form-control" placeholder="ä¾å¦ï¼æ åæ°ï¼åé¨å®¡è®¡ä¸»ç®¡"></textarea>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong>ç®¡çäººåæ³¨æå°æ­¤é®é¢äºåï¼</strong></span>
							<br />
							<label class="radio-inline">
								<input type="radio" id="niming" name="reportType" /> æ¯
							</label>
							<label class="radio-inline">
								<input type="radio" id="shiming" name="reportType" /> å¦
							</label>
							<label class="radio-inline">
								<input type="radio" id="shiming" name="reportType" /> ä¸ç¥é / ä¸æ¿æéé²
							</label>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong>æ­¤äºä»¶çæ»ä½ç¹å¾æ¯ä»ä¹ï¼</strong></span>
							<br />
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-7">
								<textarea type="text" rows="6" class="form-control"></textarea>
								<p>è¿ä»ä¸ºä¸è¬è¯´æï¼ç¨åæä»¬å°è¯¢é®è¯¦ç»æåµã</p>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong>æ¨ä¼°è®¡è¿ä¸è¡ä¸ºçè´§å¸ä»·å¼æ¯å¤å°ï¼</strong></span>
							<br />
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-2">
								<select id="enterprise" class="form-control">
									<option value="currency0">USD</option>
									<option value="currency1">CAD</option>
									<option value="currency2">MXN</option>
									<option value="currency3">BRL</option>
									<option value="currency4">CNY</option>
									<option value="currency5">EUR</option>
									<option value="currency6">GBP</option>
									<option value="currency7">IDR</option>
									<option value="currency8">INR</option>
									<option value="currency9">JPY</option>
									<option value="currency10">NGN</option>
									<option value="currency11">RUB</option>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="enterprise" class="form-control">
									<option>USD&lt;99</option>
									<option>USD100-USD999</option>
									<option>CAD100-CAD999</option>
								</select>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong>è¯¥äºä»¶æè¿è§è¡ä¸ºå¨åªéåççï¼</strong></span>
							<br />
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-7">
								<textarea type="text" rows="6" class="form-control"></textarea>
								<p>æä»¬äºè§£å°è¯¥äºä»¶å¯è½æ²¡æç¡®åçåçå°ç¹ï¼ä½å¦æäºä»¶ææäºææ¡£æä¸å¡äº¤æçè®°å½ï¼è¯·ç¸åºææã</p>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong>è¯·æåºè¯¥äºä»¶åççç¡®åæå¤§è´æ¶é´ï¼</strong></span>
							<br />
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-7">
								<textarea type="text" rows="6" class="form-control"></textarea>
								<p>ä¾å¦ï¼2002 å¹´ 5 æ 3 æ¥ï¼ææäºï¼ä¸¤å¨åï¼å¤§çº¦ä¸ä¸ªæä»¥å</p>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>æ¨è®¤ä¸ºæ­¤é®é¢æç»­äºå¤å°æ¶é´ï¼</strong>
							</span>
							<br />
							<br />
							<div class="col-sm-4">

							</div>
							<div class="col-sm-4">
								<select id="enterprise" class="form-control">
									<option>-è¯·éæ©ä¸é¡¹-</option>
									<option>ä¸æ¬¡</option>
									<option>ä¸å¨</option>
									<option>1è³3ä¸ªæ</option>
									<option>ä¸ä¸ªæè³1å¹´</option>
									<option>ä¸å¹´ä»¥ä¸</option>
									<option>ä¸ç¥é</option>
								</select>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>æ¨æ¯å¦ä½åç°æ­¤è¿è§è¡ä¸ºçï¼</strong>
							</span>
							<br />
							<br />
							<div class="col-sm-4">

							</div>
							<div class="col-sm-4">
								<select id="enterprise" class="form-control">
									<option>-è¯·éæ©ä¸é¡¹-</option>
									<option>è¿æ¯åçå¨æèº«ä¸</option>
									<option>æè§å¯å¾ç¥</option>
									<option>æå¬å°ç</option>
									<option>åäºåç¥</option>
									<option>å¬å¸ä»¥å¤çäººåç¥</option>
									<option>æ æä¸­å¬å°ç</option>
									<option>å¶ç¶åç°äºææ¡£ææä»¶</option>
									<option>å¶ä»</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-7">
								<p>å¦æéæ©å¶å®ï¼è¯·è¯´æï¼</p>
								<textarea type="text" rows="6" class="form-control"></textarea>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>æ¨ååæ¯å¦æ¥åè¿è¿ä¸é®é¢ï¼</strong>
							</span>
							<br />
							<label class="radio-inline">
								<input type="radio" id="niming" name="reportType" /> æ¯
							</label>
							<label class="radio-inline">
								<input type="radio" id="shiming" name="reportType" /> å¦
							</label>
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-7">
								<p>å¦æç­æ¡ä¸ºâæ¯âï¼æ¨æ¯ä½æ¶ä»¥ä½ç§æ¹å¼åè°æ¥åçï¼</p>
								<textarea type="text" rows="6" class="form-control"></textarea>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>è¯·æåºè¯å¾éèæ­¤é®é¢çäººä»¥åä»ä»¬éèéåçæ­¥éª¤ï¼</strong>
							</span>
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-7">
								<textarea type="text" rows="6" class="form-control" placeholder="ä¾å¦ï¼å¿½ç¥äºä»¶ï¼æ´æ¹ææ¡£ï¼è¯´è¿ä¸æ¯ä¸ªé®é¢ï¼è¯´ä»ä»¬ä¼è°æ¥"></textarea>
								<p>è¯·æåºå¶å§ååèä½ã</p>
							</div>
						</div>
						<div class="page-header"></div>
						<div class="form-group text-center">
							<span><strong><span class="xinghao">*</span>è¯·æä¾å³äºæè°è¿è§è¡ä¸ºçææç»èï¼åæ¬ç®å»èçä½ç½®ä»¥åä»»ä½å¯¹äºæ­¤æå½¢çè¯ä¼°åæç»è§£å³æä»·å¼çå¶å®ä¿¡æ¯ã </strong>
							</span>
						</div>
						<div class="form-group">
							<div class="col-sm-3">

							</div>
							<div class="col-sm-6">
								<textarea type="text" rows="6" class="form-control"></textarea>
							</div>
							<div class="col-sm-3">
								<p>è¯·è±ä¸äºæ¶é´å°½å¯è½æä¾ç»èï¼ä½è¯·å°å¿ä¸è¦æä¾æ³æ¼æ¨èº«ä»½çç»èï¼é¤éæ¨èªå·±æ¿æãå¦ææ¨æ¯å¯ä¸çç¥æäººï¼äºè§£è¿ä¸ç¹éå¸¸éè¦ã</p>
							</div>
						</div>
						<div class="form-group text-center">
							<p class="bg-danger">
								æ¨æäº¤æ¥åæ¶ï¼å°ä¼åç»æ¨ä¸ä¸ªä¸¾æ¥ç¼å·ãè¯·è®°ä¸æ­¤ç¼å·å¹¶å­æ¾å¨å®å¨çå°æ¹ãå¨5-6ä¸ªè¥ä¸æ¥åï¼æ¨éè¿ç½ç«æç­çº¿çµè¯è¿å EthicsPoint æ¶ï¼æä»¬ä¼è¦æ±æ¨ä½¿ç¨æ­¤ä¸¾æ¥ç¼å·ä»¥åæ¨éæ©çå¯ç ãå¨5-6ä¸ªè¥ä¸æ¥åè¿åï¼æ¨å°ææºä¼æ¥éä»»ä½"åç»­é®é¢"ææäº¤å³äºæ­¤äºä»¶çæ´å¤ä¿¡æ¯ã
							</p>
						</div>
						<div class="form-group text-center">
							<div class="page-header"></div>
							<span><strong> 	æ­¤å¤ï¼å¨å¯è½çæåµä¸ï¼è¯·éä¸ä»»ä½æ¯ææ¨å¨æ¬æ¬¡æ¥åä¸­ææä¾ä¿¡æ¯çææ¡£ã</strong></span>
							<br />
							<a href="#" class="btn-link" data-toggle="modal" data-target="#upLoadPanel">åå»æ­¤å¤ä¸ä¼ æä»¶</a>
							<br />
							<p>ï¼å¯ä¸ä¼ å¤§å¤æ°å¸¸è§çæä»¶ç±»åãï¼</p>
						</div>
						<div class="form-group text-center">
							<div class="page-header"></div>
							<h3><span class="label label-danger">å½åæ¡ä»¶ç¼å·ä¸ºï¼AB09C80567</span></h3>
							<br />
							<span><strong><span class="xinghao">*</span>è¯·è¾å¥ä¸¾æ¥å¯ç ï¼</strong>
							</span>
							<p>æ¨çå¯ç å¿é¡»ç¸å¹éèä¸é¿åº¦ä¸å¾å°äºåä¸ªå­ç¬¦ã</p>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">å¯ç <span class="xinghao">*</span></label>
							<div class="col-sm-4">
								<input type="password" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">åæ¬¡è¾å¥å¯ç <span class="xinghao">*</span></label>
							<div class="col-sm-4">
								<input type="password" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-4">

							</div>
							<div class="col-sm-4">
								<input type="button" id="submitReport" class="form-control btn btn-primary" value="æäº¤æ¥å" />
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="page-header"></div>
		<p class="navbar-text text-center">Copyright Â© 2016-2018 ç¨æ·ä¸¾æ¥ç³»ç»</p>

		<!--æ¡æ¬¾å¯¹è¯æ¡-->
		<div class="modal fade bs-example-modal-lg" id="agreeMentPanel" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h5 class="modal-title" id="exampleModalLabel">æ¡æ¬¾ä¸æ¡ä»¶</h5>
					</div>
					<div class="modal-body">
						<iframe src="agreement.html" frameborder="0" width="100%" height="400px"></iframe>
					</div>
				</div>
			</div>
		</div>

		<!--ä¸ä¼ æä»¶å¯¹è¯æ¡-->
		<div class="modal fade bs-example-modal-lg" id="upLoadPanel" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h5 class="modal-title" id="exampleModalLabel">ä¸ä¼ æä»¶</h5>
					</div>
					<div class="modal-body">
						<iframe src="upLoadFile.html" frameborder="0" width="100%" height="400px"></iframe>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="../bootstrap3/js/reportCase.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function() {
			$("#shiming").change(function() {
				$("#userInfo").css("display", "block");
			});
			$("#niming").change(function() {
				$("#userInfo").css("display", "none");
			});
		});
	</script>

</html>