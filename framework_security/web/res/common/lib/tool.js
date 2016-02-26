// 工具
(function ($) {
    var defaults = {
        superTag:"body",
        dateFormat:"yyyy-MM-dd hh:mm:ss S"
    };
    $.setParams = function (params) {
        $.extend(defaults, params);
    };
    $.exception = function (e) {
        var debugModel = true;
        if (debugModel) {
            $.error(e);
        }
    };
    $.doAjax = function (options) {
        $.ajaxSetup({
            type:"POST",
            cache:false,
            async:true,
            global:false
        });
        var _ajaxOptions = $.extend({}, options);
        $.ajax(_ajaxOptions);
    };
    /**
     * 根据expr(表达式)查找父级
     * 返回:undefined,parent;
     */
    $.fn.findParent = function (expr) {
        var $this = $(this);
        var exprParent = undefined;
        var detectObj = function ($obj) {
            var errors = $.object.isNotExist($obj);
            errors = errors || $.object.isSuperTag($obj);
            return errors;
        };

        if (detectObj($this)) {
            return undefined;
        }

        var parentObj = $this.parent();
        while (true) {
            if (detectObj(parentObj)) {
                exprParent = undefined;
                break;
            }

            if (parentObj.is(expr)) {
                exprParent = parentObj;
                break;
            } else {
                parentObj = parentObj.parent();
            }
        }
        return exprParent;
    };
    $.object = {
        isExist:function (obj) {
            return (obj != undefined && obj.length > 0);
        },
        isNotExist:function (obj) {
            return (obj == undefined || obj.length <= 0);
        },
        isSuperTag:function (obj) {
            return ($.object.isExist(obj) && obj.is(defaults.superTag));
        }
    };
    $.string = {
        isEmpty:function (str) {
            return str == null || $.trim(str) == "";
        },
        isNotEmpty:function (str) {
            return str != null && $.trim(str) != "";
        },
        subPrefix:function (str, mark) {
            var result = "";
            var v = $.string.isNotEmpty(str);
            v = v && $.string.isNotEmpty(mark);
            if (v) {
                var index = str.indexOf(mark);
                result = str.substring(0, index);
            }
            return result;
        },
        subSuffix:function (str, mark) {
            var result = "";
            var v = $.string.isNotEmpty(str);
            v = v && $.string.isNotEmpty(mark);
            if (v) {
                var index = str.indexOf(mark) + 1;
                result = str.substring(index, str.length);
            }
            return result;
        },
        /**
         * 字符是包含字符串
         * @param str1 字符串
         * @param str2 被包含的字符串
         */
        isMatch:function (str1, str2) {
            var index = str1.indexOf(str2);
            return index == -1;
        },
        /**
         * 字符是否以字符串结束
         * @param str1 字符串
         * @param str2 被包含的字符串
         */
        isLastMatch:function (str1, str2) {
            var index = str1.lastIndexOf(str2);
            return str1.length == index + str2.length;
        },
        /**
         * 字符是否以字符串开始
         * @param str1 字符串
         * @param str2 被包含的字符串
         */
        isFirstMatch:function (str1, str2) {
            var index = str1.indexOf(str2);
            return index == 0;
        },
        /**
         * 计算字节数,全角或汉字2个字符,半角1个字符
         * @param varField
         */
        getTotalBytes:function (varField) {
            if ($.string.isEmpty(varField)) {
                return -1;
            }
            var totalCount = 0;
            for (var i = 0; i < varField.value.length; i++) {
                if (varField.value.charCodeAt(i) > 127) {
                    totalCount += 2;
                }
                else {
                    totalCount++;
                }
            }
            return totalCount;
        },
        /**
         * 字符串去空格的函数
         * @param sInputString 去掉空格的字符串
         * @param iType 1=去掉字符串左边的空格;2=去掉字符串左边的空格;0=去掉字符串左边和右边的空格
         */
        cTrim:function (sInputString, iType) {
            var sTmpStr = ' ';
            var i = -1;
            if (iType == 0 || iType == 1) {
                while (sTmpStr == ' ') {
                    ++i;
                    sTmpStr = sInputString.substr(i, 1);
                }
                sInputString = sInputString.substring(i);
            }
            if (iType == 0 || iType == 2) {
                sTmpStr = ' ';
                i = sInputString.length;
                while (sTmpStr == ' ') {
                    --i;
                    sTmpStr = sInputString.substr(i, 1);
                }
                sInputString = sInputString.substring(0, i + 1);
            }
            return sInputString;
        }

    };
    $.date = {
        compareTo:function (minDate, maxDate) {
            if ($.string.isNotEmpty(minDate) && $.string.isNotEmpty(maxDate)) {
                var date1 = Date.parse(minDate.replace(/-/g, "/"));
                var date2 = Date.parse(maxDate.replace(/-/g, "/"));
                return date1 < date2;
            }
            return false;
        },
        toDate:function (date, format) {
            if ($.string.isNotEmpty(date)) {
                var formatDate = $.string.isNotEmpty(format) ? format : defaults.dateFormat;
                var dateS = new Date(Date.parse(date.replace(/-/g, "/")));
                var o = {
                    "M+":dateS.getMonth() + 1, //month
                    "d+":dateS.getDate(), //day
                    "h+":dateS.getHours(), //hour
                    "m+":dateS.getMinutes(), //minute
                    "s+":dateS.getSeconds(), //second
                    "q+":Math.floor((dateS.getMonth() + 3) / 3), //quarter
                    "S":dateS.getMilliseconds() //millisecond
                };
                if (/(y+)/.test(formatDate)) {
                    formatDate = formatDate.replace(RegExp.$1, (dateS.getFullYear() + "").substr(4 - RegExp.$1.length));
                }
                for (var k in o) {
                    if (new RegExp("(" + k + ")").test(formatDate)) {
                        formatDate = formatDate.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                    }
                }
                return formatDate;
            }
            return null;
        },
        getMaxDay:function (year, month) {
            var data = 0;
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                data = 30;
            }
            if (month == 2) {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    data = 29;
                }
                else {
                    data = 28;
                }
                data = 31;
            }
            return data;
        }
    };
    $.array = {
        arrayIndexOf:function (arr, str) {
            if ($.isArray(arr)) {
                return $.inArray(str, arr);
            }
        },
        toJson:function (arr) {
            var arrayEach = function (arr) {
                var arrayJson = '';
                if (!$.isEmptyObject(arr)) {
                    $.each(arr, function (k, v) {
                        if (!$.isPlainObject(v)) {
                            arrayJson += '"' + k + '":"' + v + '",';
                        } else {
                            arrayJson += '"' + k + '":' + arrayEach(v) + ',';
                        }
                    });
                    arrayJson = subJsonString(arrayJson);
                }
                return '{' + arrayJson + '}';
            };
            var subJsonString = function (str) {
                if (str != "" && $.type(str) == "string") {
                    return str.substring(0, str.length - 1);
                }
            };
            if (!$.isPlainObject(arr)) {
                return undefined;
            }
            return arrayEach(arr);
        }
    };
    $.json = {
        toArray:function (json) {
            return $.parseJSON(json);
        }
    };
    $.eregi = {
        code:{
            "Integer":/^[-]{0,1}[0-9]{1,}$/, // 检查输入对象的值是否符合整数格式
            "Number":/^[0-9]+$/, // 检查输入字符串是否符合正整数格式
            "Decimal":/^[-]{0,1}(\d+)[\.]+(\d+)$/, // 检查输入字符串是否是带小数的数字格式,可以是负数
            "Money":/^[0-9]+[\.][0-9]{1,2}$/, //检查输入字符串是否符合金额格式,格式定义为带小数的正数，小数点后最多三位
            "Email":/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/, // 检查输入的Email信箱格式是否正确
            "IP":/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g, // 校验ip地址的格式
            "Mobile":/^[1][3][0-9]{9}$/, // 检查输入手机号码是否正确
            "Phone":/^[0][1-9]{2,3}-[0-9]{5,10}$/, // 检查输入的电话号码格式是否正确 (带区号)
            "PhoneNoArea":/^[1-9]{1}[0-9]{5,8}$/, // 检查输入的电话号码格式是否正确
            "NumberOr_Letter":/^[0-9a-zA-Z\_]+$/, //检查输入字符串是否只由英文字母和数字和下划线组成
            "NumberOrLetter":/^[0-9a-zA-Z]+$/, // 检查输入字符串是否只由英文字母和数字组成
            "ChinaOrNumbOrLetter":/^[0-9a-zA-Z\u4e00-\u9fa5]+$/, // 检查输入字符串是否只由汉字、字母、数字组成
            "DateTime":/^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/, //判断输入是否是有效的长日期格式 - "YYYY-MM-DD HH:MM:SS" || "YYYY/MM/DD HH:MM:SS"
            "Date":/^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/, //判断输入是否是有效的长日期格式 - "YYYY-MM-DD" || "YYYY/MM/DD"
            "Time":/^(\d{1,2}):(\d{1,2}):(\d{1,2})$/, //判断输入是否是有效的时间格式 - "HH:MM:SS"
            "QQ":/[1-9][0-9]{4,}/, //QQ
            "extend":""
        },
        integer:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Integer.test(str);
            }
            return false;
        },
        number:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Number.test(str);
            }
            return false;
        },
        decimal:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Decimal.test(str);
            }
            return false;
        },
        money:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Money.test(str);
            }
            return false;
        },
        email:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Email.test(str);
            }
            return false;
        },
        ip:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.IP.test(str);
            }
            return false;
        },
        mobile:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Mobile.test(str);
            }
            return false;
        },
        phone:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Phone.test(str);
            }
            return false;
        },
        phoneNoArea:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.PhoneNoArea.test(str);
            }
            return false;
        },
        numberOr_Letter:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.NumberOr_Letter.test(str);
            }
            return false;
        },
        numberOrLetter:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.NumberOrLetter.test(str);
            }
            return false;
        },
        chinaOrNumbOrLetter:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.ChinaOrNumbOrLetter.test(str);
            }
            return false;
        },
        dateTime:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.DateTime.test(str);
            }
            return false;
        },
        time:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Time.test(str);
            }
            return false;
        },
        date:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.Date.test(str);
            }
            return false;
        },
        qq:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.QQ.test(str);
            }
            return false;
        },
        extend:function (str) {
            if ($.string.isNotEmpty(str)) {
                return $.eregi.code.extend.test(str);
            }
            return false;
        }
    }
})(jQuery);
// 选择器
(function ($) {
    /**
     * 根据name查找form
     */
    $.fn.findFormByName = function (name) {
        var $this = $(this);
        if ($.object.isNotExist($this)) {
            return undefined;
        }
        return $this.find("form[name='" + name + "']");
    };
    /**
     * 根据name查找select
     */
    $.fn.findSelectByName = function (name) {
        var $this = $(this);
        if ($.object.isNotExist($this)) {
            return undefined;
        }
        return $this.find("select[name='" + name + "']");
    };
    /**
     * 根据name查找input
     */
    $.fn.findInputByName = function (name) {
        var $this = $(this);
        if ($.object.isNotExist($this)) {
            return undefined;
        }
        return $this.find("input[name='" + name + "']");

    };
})(jQuery);