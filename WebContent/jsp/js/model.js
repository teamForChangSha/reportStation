$(function() {
	var wh = $(window).height();
	var modal = $("<div/>").attr("id", "ycf-alert").addClass("modal bs-example-modal-sm").css("padding-top",wh/4+"px");
	var dialog = $("<div/>").addClass("modal-dialog modal-sm");
	var content = $("<div/>").addClass("modal-content");
	var header = $("<div/>").addClass("modal-header");
	var button = $("<button/>").attr("type", "button").addClass("close").attr(
			"data-dismiss", "modal");
	var span = $("<span/>").attr("aria-hidden", "true").text("x");
	var span1 = $("<span/>").addClass("sr-only").text("Close");
	button.append(span).append(span1);
	var h4 = $("<h5/>").addClass("modal-title");
	var i = $("<i/>").addClass("fa fa-exclamation-circle");
	h4.append(i);
	header.append(button).append(h4);
	var body = $("<div/>").addClass("modal-body small");
	var p = $("<p/>");
	body.append(p);
	var footer = $("<div/>").addClass("modal-footer").css("text-align","center");
	var ok = $("<button/>").attr("type", "button").addClass(
			"btn btn-primary ok").attr("data-dismiss", "modal").text("确定");
	var cancel = $("<button/>").attr("type", "button").addClass(
			"btn btn-default cancel").attr("data-dismiss", "modal").text(
			"取消");
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
			alr.find(".ok").removeClass("btn-primary").addClass("btn-default");
			alr.find(".cancel").addClass("hidden");
			console.log(alr.html());
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
			alr.find(".ok").removeClass("btn-default").addClass("btn-primary");
			alr.find(".cancel").removeClass("hidden");
			console.log(alr.html());
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
		
		var _dialog = function(options){
			var opt = {
					title:'提示',
					msg:'内容'
			};
			$.extend(opt,options);
			alr.find("h5").text(opt.title);
			alr.find("p").text(opt.msg);
		}

		return {
			alert : _alert,
			confirm : _confirm
		}

	}();
});