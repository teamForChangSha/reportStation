/**
 * @fileOverview bs_pagination: jQuery pagination plugin, based on Twitter Bootstrap.
 *               <p>License MIT
 *               <br />Copyright Christos Pontikis <a href="http://pontikis.net">http://pontikis.net</a>
 *               <br />Project page <a href="http://www.pontikis.net/labs/bs_pagination">http://www.pontikis.net/labs/bs_pagination</a>
 *               <br />Minified using Online YUI Compressor http://www.refresh-sf.com/yui/
 * @version 1.0.2 (09 May 2014)
 * @author Christos Pontikis http://www.pontikis.net
 * @requires jquery >= 1.8, twitter bootstrap >= 2
 */
"use strict";
(function (e) {
    var d = "bs_pagination";
    var b = {
        init: function (g) {
            var h = this;
            return this.each(function () {
                var C = h.data(d);
                if (typeof C == "undefined") {
                    var E = "3";
                    if (g.hasOwnProperty("bootstrap_version") && g.bootstrap_version == "2") {
                        E = "2"
                    }
                    var v = b.getDefaults.call(h, E);
                    C = e.extend({}, v, g)
                } else {
                    C = e.extend({}, C, g)
                }
                if (C.totalRows == 0) {
                    C.totalRows = C.totalPages * C.rowsPerPage
                }
                h.data(d, C);
                h.unbind("onChangePage").bind("onChangePage", C.onChangePage);
                h.unbind("onLoad").bind("onLoad", C.onLoad);
                var y = h.attr("id"), r = c(C.nav_list_id_prefix, y), k = c(C.nav_top_id_prefix, y), A = c(C.nav_prev_id_prefix, y), q = c(C.nav_item_id_prefix, y) + "_", o = c(C.nav_next_id_prefix, y), p = c(C.nav_last_id_prefix, y), x = c(C.nav_goto_page_id_prefix, y), B = c(C.nav_rows_per_page_id_prefix, y), m = c(C.nav_rows_info_id_prefix, y), w = "", l, i, u, s, z, t, n, j, D;
                if (C.bootstrap_version == "3") {
                    w += '<div class="' + C.mainWrapperClass + '">';
                    w += '<div class="' + C.navListContainerClass + ' text-right">';
                    w += '<div class="' + C.navListWrapperClass + '">';
                    w += '<ul id="' + r + '" class="' + C.navListClass + '" style="margin: 0">';
                    w += "</ul>";
                    w += "</div>";
                    w += "</div>";
                    if (C.showGoToPage && C.visiblePageLinks < C.totalPages) {
                        w += '<div class="' + C.navGoToPageContainerClass + '">';
                        w += '<div class="input-group" style="margin: 4px 0">';
                        w += '<span>共'+ C.totalPages+'页，'+ C.totalCount+'条，' + C.navGoToPageIconClass + '</span>';
                        w += '<input id="' + x + '" type="text" style="width: 30px">';
                        w += '<button type="button" id="goPage" class="btn btn-default btn-xs" style="margin-left: 10px">GO</button>';
                        w += "</div>";
                        w += "</div>"
                    }
                } else {
                    w += '<div class="' + C.mainWrapperClass + '">';
                    w += '<div class="' + C.navListContainerClass + '">';
                    w += '<div class="' + C.navListWrapperClass + '">';
                    w += '<ul id="' + r + '" class="' + C.navListClass + '" style="margin: 0">';
                    w += "</ul>";
                    w += "</div>";
                    w += "</div>";
                    if ((C.showGoToPage && C.visiblePageLinks < C.totalPages) || C.showRowsPerPage) {
                        w += '<div class="' + C.navInputsContainerClass + '">';
                        if (C.showGoToPage && C.visiblePageLinks < C.totalPages) {
                            w += '<div class="' + C.navGoToPageWrapperClass + '">';
                            w += '<span>' + C.navGoToPageIconClass + '</span>';
                            w += '<input id="' + x + '" type="text">';
                            w += "</div>"
                        }
                        w += "</div>"
                    }
                }
                w += "</div>";
                h.html(w);
                l = null;
                i = C.currentPage;
                f(y, l, i, true, false);
                h.removeClass();
                if (!C.directURL) {
                    u = "#" + k;
                    h.off("click", u).on("click", u, function () {
                        var G = C.currentPage;
                        C.currentPage = 1;
                        var F = C.currentPage;
                        f(y, G, F, true, true)
                    });
                    s = "#" + A;
                    h.off("click", s).on("click", s, function () {
                        if (C.currentPage > 1) {
                            var G = C.currentPage;
                            C.currentPage = parseInt(C.currentPage) - 1;
                            var F = C.currentPage;
                            var H = (h.data("nav_start") == G);
                            f(y, G, F, H, true)
                        }
                    });
                    t = "#" + o;
                    h.off("click", t).on("click", t, function () {
                        if (C.currentPage < C.totalPages) {
                            var G = C.currentPage;
                            C.currentPage = parseInt(C.currentPage) + 1;
                            var F = C.currentPage;
                            var H = (h.data("nav_end") == G);
                            f(y, G, F, H, true)
                        }
                    });
                    n = "#" + p;
                    h.off("click", n).on("click", n, function () {
                        var G = C.currentPage;
                        C.currentPage = parseInt(C.totalPages);
                        var F = C.currentPage;
                        f(y, G, F, true, true)
                    });
                    z = '[id^="' + q + '"]';
                    h.off("click", z).on("click", z, function (I) {
                        var H = C.currentPage;
                        var F = q.length;
                        C.currentPage = parseInt(e(I.target).attr("id").substr(F));
                        var G = C.currentPage;
                        f(y, H, G, false, true)
                    })
                }
                if (C.showGoToPage) {
                    j = "#" + x;
                    h.off("keypress", j).on("keypress", j, function (I) {
                        if (I.which === 13) {
                            var H = parseInt(e(j).val());
                            e(j).val("");
                            if (!isNaN(H) && H > 0) {
                                if (H > C.totalPages) {
                                    H = C.totalPages
                                }
                                var G = C.currentPage;
                                C.currentPage = H;
                                var F = H;
                                if (C.directURL) {
                                    location.href = C.directURL(F)
                                } else {
                                    f(y, G, F, true, true)
                                }
                            }
                            I.preventDefault()
                        } else {
                            if (!(I.which === 8 || I.which === 0 || (I.shiftKey === false && (I.which > 47 && I.which < 58)))) {
                                I.preventDefault()
                            }
                        }
                    })
                }
                $("#goPage").on("click",function(I){
                    j = "#" + x;
                    var H = parseInt(e(j).val());
                    e(j).val("");
                    if (!isNaN(H) && H > 0) {
                        if (H > C.totalPages) {
                            H = C.totalPages
                        }
                        var G = C.currentPage;
                        C.currentPage = H;
                        var F = H;
                        if (C.directURL) {
                            location.href = C.directURL(F)
                        } else {
                            f(y, G, F, true, true)
                        }
                    }
                    I.preventDefault()
                });
            })
        }, getVersion: function () {
            return "1.0.2"
        }, getDefaults: function (h) {
            var g = {
                currentPage: 1,
                rowsPerPage: 10,
                maxRowsPerPage: 100,
                totalPages: 100,
                totalCount: 0,
                totalRows: 0,
                visiblePageLinks: 5,
                showGoToPage: true,
                showRowsPerPage: true,
                showRowsInfo: true,
                showRowsDefaultInfo: true,
                directURL: false,
                disableTextSelectionInNavPane: true,
                bootstrap_version: "3",
                mainWrapperClass: "row",
                navListContainerClass: "col-sm-6",
                navListWrapperClass: "",
                navListClass: "pagination pagination_custom",
                navListActiveItemClass: "active",
                navGoToPageContainerClass: "col-sm-6 row-space",
                navGoToPageIconClass: "转到",
                navGoToPageClass: "form-control small-input",
                navRowsPerPageContainerClass: "col-sm-6 row-space",
                navRowsPerPageIconClass: "glyphicon glyphicon-th-list",
                navRowsPerPageClass: "form-control small-input",
                navInfoContainerClass: "col-xs-12 col-sm-4 col-md-2 row-space",
                navInfoClass: "",
                nav_list_id_prefix: "nav_list_",
                nav_top_id_prefix: "top_",
                nav_prev_id_prefix: "prev_",
                nav_item_id_prefix: "nav_item_",
                nav_next_id_prefix: "next_",
                nav_last_id_prefix: "last_",
                nav_goto_page_id_prefix: "goto_page_",
                nav_rows_per_page_id_prefix: "rows_per_page_",
                nav_rows_info_id_prefix: "rows_info_",
                onChangePage: function () {
                },
                onLoad: function () {
                }
            };
            if (h == "2") {
                g.bootstrap_version = "2";
                g.mainWrapperClass = "row-fluid";
                g.navListContainerClass = "span6";
                g.navListWrapperClass = "pagination pagination_custom";
                g.navListClass = "";
                g.navListActiveItemClass = "active";
                g.navInputsContainerClass = "span4 row-space";
                g.navGoToPageWrapperClass = "input-prepend goto_page_wrapper";
                g.navGoToPageIconClass = "icon-arrow-right";
                g.navGoToPageClass = "small-input";
                g.navRowsPerPageWrapperClass = "input-prepend rows_per_page_wrapper";
                g.navRowsPerPageIconClass = "icon-th-list";
                g.navRowsPerPageClass = "small-input";
                g.navInfoContainerClass = "span2 row-space";
                g.navInfoClass = ""
            }
            return g
        }, getOption: function (g) {
            var h = this;
            return h.data(d)[g]
        }, getAllOptions: function () {
            var g = this;
            return g.data(d)
        }, destroy: function () {
            var g = this;
            g.removeData()
        }, setRowsInfo: function (g) {
            var h = this, i = c(b.getOption.call(h, "getOption", "nav_rows_info_id_prefix"), h.attr("id"));
            e("#" + i).html(g)
        }
    };
    var c = function (h, g) {
        return h + g
    };
    var f = function (F, k, g, J, u) {
        var I = e("#" + F), B = b.getAllOptions.call(I), t = c(B.nav_item_id_prefix, F) + "_";
        if (J) {
            var l = c(B.nav_list_id_prefix, F), h = c(B.nav_top_id_prefix, F), H = c(B.nav_prev_id_prefix, F), m = c(B.nav_next_id_prefix, F), r = c(B.nav_last_id_prefix, F), v = e("#" + l), G = "", z = parseInt(B.currentPage), x, E, L, q, C, y = "", w = "javascript:void(0);";
            if (B.totalPages < B.visiblePageLinks) {
                z = 1;
                x = B.totalPages
            } else {
                C = Math.ceil(B.totalPages / B.visiblePageLinks);
                if (z > B.visiblePageLinks * (C - 1)) {
                    z = B.totalPages - B.visiblePageLinks + 1
                } else {
                    L = z % B.visiblePageLinks;
                    q = L == 0 ? -B.visiblePageLinks + 1 : -L + 1;
                    z += q
                }
                x = z + B.visiblePageLinks - 1
            }
            I.data("nav_start", z);
            I.data("nav_end", x);
            if (z > 1) {
                y = B.directURL ? B.directURL(1) : w;
                G += '<li><a id="' + h + '" href="' + y + '">首页</a></li>';
                y = B.directURL ? B.directURL(z - 1) : w;
                G += '<li><a id="' + H + '" href="' + y + '">上一页</a></li>';
            }
            for (E = z; E <= x; E++) {
                y = B.directURL ? B.directURL(E) : w;
                G += '<li><a id="' + t + E + '" href="' + y + '">' + E + "</a></li>"
            }
            if (x < B.totalPages) {
                y = B.directURL ? B.directURL(x + 1) : w;
                G += '<li><a id="' + m + '" href="' + y + '">下一页</a></li>';
                y = B.directURL ? B.directURL(B.totalPages) : w;
                G += '<li><a id="' + r + '" href="' + y + '">末页</a></li>';
            }
            v.html(G);
            if (B.disableTextSelectionInNavPane) {
                a(v)
            }
        }
        var p = e("#" + t + k), K = e("#" + t + g);
        p.closest("li").removeClass(B.navListActiveItemClass);
        K.closest("li").addClass(B.navListActiveItemClass);
        p.prop("title", "");
        if (B.showRowsInfo && B.showRowsDefaultInfo) {
            var o = ((B.currentPage - 1) * B.rowsPerPage) + 1, n = Math.min(o + B.rowsPerPage - 1, B.totalRows), j = c(B.nav_rows_info_id_prefix, F);
            e("#" + j);
        }
        if (u) {
            I.triggerHandler("onChangePage", {currentPage: g, rowsPerPage: B.rowsPerPage})
        } else {
            I.triggerHandler("onLoad", {currentPage: g, rowsPerPage: B.rowsPerPage})
        }
    };
    var a = function (g) {
        return g.attr("unselectable", "on").css("user-select", "none").on("selectstart", false)
    };
    e.fn.bs_pagination = function (h) {
        if (this.size() != 1) {
            var g = "You must use this plugin (" + d + ") with a unique element (at once)";
            this.html('<span style="color: red;">ERROR: ' + g + "</span>");
            e.error(g)
        }
        if (b[h]) {
            return b[h].apply(this, Array.prototype.slice.call(arguments, 1))
        } else {
            if (typeof h === "object" || !h) {
                return b.init.apply(this, arguments)
            } else {
                e.error("Method " + h + " does not exist on jQuery." + d)
            }
        }
    }
})(jQuery);