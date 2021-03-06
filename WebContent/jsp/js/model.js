//alert弹出框
// ==============
$(function() {
	var wh = $(window).height();
	var modal = $("<div/>").attr("id", "ycf-alert").addClass(
			"modal bs-example-modal-sm").css("padding-top", wh / 4 + "px");
	var dialog = $("<div/>").addClass("modal-dialog modal-sm");
	var content = $("<div/>").addClass("modal-content");
	var header = $("<div/>").addClass("modal-header");
	var button = $("<button/>").attr("type", "button").addClass("close").attr(
			"data-dismiss", "modal");
	var span = $("<span/>").attr("aria-hidden", "true").text("x");
	var span1 = $("<span/>").addClass("sr-only").text("Close");
	button.append(span).append(span1);
	var icon = "<span class='glyphicon glyphicon-exclamation-sign' style='margin-right: 4px' aria-hidden='true'></span>"
	var h5 = $("<h5/>").addClass("modal-title").append(icon);
	header.append(button).append(h5);
	var body = $("<div/>").addClass("modal-body small text-center");
	var p = $("<p/>");
	var input = $("<input/>").attr("type", "password").addClass("form-control")
			.hide();
	body.append(p).append(input);
	var footer = $("<div/>").addClass("modal-footer").css("text-align",
			"center");
	var ok = $("<button/>").attr("type", "button").addClass(
			"btn btn-warning ok").attr("data-dismiss", "modal").text("确定");
	var cancel = $("<button/>").attr("type", "button").addClass(
			"btn btn-default cancel").attr("data-dismiss", "modal").text("取消");
	footer.append(ok).append(cancel);
	content.append(header).append(body).append(footer);
	dialog.append(content);
	modal.append(dialog);
	$("body").append(modal);

	window.Modal = function() {
		var alr = $("#ycf-alert");
		var ahtml = alr.html();
		var _alert = function(options) {
			alr.html(ahtml);
			_dialog(options);
			alr.find(".ok").removeClass("btn-warning").addClass("btn-default");
			alr.find(".cancel").addClass("hidden");
			alr.find("input").hide();
			alr.modal('show');
			return {
				on : function(callback) {
					if (callback && callback instanceof Function) {
						alr.find('.ok').click(function() {
							callback(true)
						});
					}
				}
			};
		};

		var _confirm = function(options) {
			alr.html(ahtml);
			_dialog(options);
			alr.find(".ok").removeClass("btn-default").addClass("btn-warning");
			alr.find(".cancel").removeClass("hidden");
			alr.find("input").hide();
			alr.modal('show');
			return {
				on : function(callback) {
					if (callback && callback instanceof Function) {
						alr.find('.ok').click(function() {
							callback(true);
						});
						alr.find('.cancel').click(function() {
							callback(false);
						});
					}
				}
			};
		};

		var _prompt = function(options) {
			alr.html(ahtml);
			_dialog(options);
			alr.find(".ok").removeClass("btn-default").addClass("btn-warning");
			alr.find(".cancel").removeClass("hidden");
			alr.find("input").show();
			alr.modal('show');
			return {
				on : function(callback) {
					if (callback && callback instanceof Function) {
						alr.find('.ok').click(function() {
							callback(true, alr.find("input").val());
						});
						alr.find('.cancel').click(function() {
							callback(false);
						});
					}
				}
			};
		}

		var _dialog = function(options) {
			var opt = {
				title : '提示',
				msg : '内容'
			};
			$.extend(opt, options);
			alr.find("h5").append(opt.title);
			alr.find("p").text(opt.msg);
		}

		return {
			alert : _alert,
			confirm : _confirm,
			prompt : _prompt
		}

	}();
});