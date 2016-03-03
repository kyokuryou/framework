(function ($l) {
    var _requestUrl = "";
    var _target = undefined;
    var _pagerParams = {
        "pager.pageSize":"20",
        "pager.pageCount":"1",
        "pager.pageNumber":"1",
        "pager.orderBy":"",
        "pager.orderType":""
    };
    var _pagerLocale = {
        firstpage:"首页",
        prevpage:"上一页",
        nextpage:"下一页",
        lastpage:"末页"
    };
    $l.setPagerParams = function (option) {
        _pagerParams = $l.extend(_pagerParams, option);
    };
    $l.setPagerLocale = function (locale) {
        _pagerLocale = $l.extend(_pagerLocale, locale);
    };
    $l.setPagerTarget = function (target, url) {
        if ($l.object.isExist(target)) {
            _target = target;
        }
        if ($l.string.isNotEmpty(url)) {
            _requestUrl = url;
        }
    };
    // 排序绑定
    $l.fn.sortBind = function (params) {
        var $lThis = $l(this);
        $lThis.click(function () {
            var $currentOrderBy = $l(this).attr("name");
            if (_pagerParams["pager.orderBy"] == $currentOrderBy) {
                if (_pagerParams["pager.orderType"] == "") {
                    _pagerParams["pager.orderType"] = "asc";
                } else if (_pagerParams["pager.orderType"] == "desc") {
                    _pagerParams["pager.orderType"] = "asc";
                } else if (_pagerParams["pager.orderType"] == "asc") {
                    _pagerParams["pager.orderType"] = "desc";
                }
            } else {
                _pagerParams["pager.orderBy"] = $currentOrderBy;
                _pagerParams["pager.orderType"] = "asc";
            }
            _pagerParams["pager.pageNumber"] = "1";
            searchData(params);
        });
    };
    // 分页绑定
    $l.pagerBind = function (params) {
        $l("#pager").pager({
            pagenumber:_pagerParams["pager.pageNumber"],
            pagecount:_pagerParams["pager.pageCount"],
            locale:_pagerLocale,
            buttonClickCallback:function (pagenumber) {
                _pagerParams["pager.pageNumber"] = pagenumber;
                searchData(params);
            }
        });
    };
    // 显示数绑定
    $l.fn.pageSizeBind = function (params) {
        var $this = $l(this);
        $this.change(function () {
            _pagerParams["pager.pageNumber"] = "1";
            _pagerParams["pager.pageSize"] = $this.val();
            searchData(params);
        });
    };
    // 全选按钮绑定
    $1.fn.checkAllBind = function (childCheck, childCallBack) {
        var $1Tthis = $l(this);
        $1Tthis.click(function () {
            childCheck.attr("checked", $l(this).is(":checked"));
        });
        childCheck.click(function () {
            childCheck.each(function (k, v) {
                if (!$l(v).is(":checked")) {
                    $1Tthis.attr("checked", false);
                    return false;
                } else if (childCheck.length - 1 == k) {
                    $1Tthis.attr("checked", true);
                }
            });
            var callBack = childCallBack() || function (){};
            callBack($l(this));
        })
    };
    function searchData(params) {
        var dataParams = $l.extend(params, _pagerParams);
        $l.doAjax({
            url:_requestUrl,
            data:dataParams,
            dataType:"html",
            beforeSend:function () {
            },
            success:function (data) {
                _target.html(data);
                $l.pagerBind(params);

            },
            error:function () {
            }
        });
    }
})(jQuery);