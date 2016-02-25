/**
 *
 */
(function ($) {
    $.browser = {
        "safari": false, "opera": false, "msie": false, "mozilla": false, "version": "0"
    };
    $.ua = function (ua) {
        ua = ua.toLowerCase();

        var match = /(chrome)[ \/]([\w.]+)/.exec(ua) || /(webkit)[ \/]([\w.]+)/.exec(ua) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(msie) ([\w.]+)/.exec(ua) || ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];

        $.browser[match[1]] = true;
        $.browser["version"] = match[2];
    };
    /**
     * ** 1.9过渡 **
     * 浏览器判断支持
     */
    $.ua(navigator.userAgent);

    /**
     * ** 1.9过渡 **
     * 委托事件绑定支持
     */
    if (!$.isFunction(jQuery.fn.live)) {
        $.fn.live = function (types, data, fn) {
            $(this).on(types, null, data, fn);
        };
    }
    /**
     * ** 1.9过渡 **
     * 委托事件解除绑定支持
     */
    if (!$.isFunction(jQuery.fn.die)) {
        $.fn.die = function (types, fn) {
            $(this).off(types, null, fn);
        }
    }
})(jQuery);