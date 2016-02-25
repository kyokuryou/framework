/**
 *
 */
(function (window, TS) {
    var defaults = {
        dateFormat: "yyyy-MM-dd hh:mm:ss S"
    };
    TS = function (params) {
        if (params == null) {
            return;
        }
        for (var k in params) {
            var val = params[k];
            if (k == "dateFormat" && val != null) {
                defaults.dateFormat = val;
            }
            defaults[k] = val;
        }
    };
    TS.object = {
        /**
         *判断此document对象是否存在
         * @param obj
         * @return boolean
         */
        isExist: function (obj) {
            return obj != undefined && obj != null;
        }, /**
         * 判断此document对象是否不存在
         * @param obj
         * @return boolean
         */
        isNotExist: function (obj) {
            return obj == undefined || obj == null;
        }, /**
         * 判断此document对象是否是顶级对象，如：body,html
         * @param obj
         * @return boolean
         */
        isSuperTag: function (obj) {
            var tagName = obj.tagName.toLowerCase();
            return tagName == "body" || tagName == "html";
        }, type: function (obj) {
            var classType = {
                "[object Boolean]": "boolean",
                "[object Number]": "number",
                "[object String]": "string",
                "[object Function]": "function",
                "[object Array]": "array",
                "[object Date]": "date",
                "[object RegExp]": "regexp",
                "[object Object]": "object",
                "[object Error]": "error"
            };
            if (obj == null) {
                return String(obj);
            }
            return typeof obj === "object" || typeof obj === "function" ? classType[classType.toString.call(obj)] || "object" : typeof obj;
        }
    };
    TS.string = {
        /**
         * 判断此string是否是空
         * @param str 字符串
         * @return boolean
         */
        isEmpty: function (str) {
            return str == undefined || str == null || TS.object.type(str) !== "string" || str.length <= 0;
        }, /**
         * 判断此string是否不是空
         * @param str 字符串
         * @return boolean
         */
        isNotEmpty: function (str) {
            return str != undefined && str != null && TS.object.type(str) === "string" && str.length > 0;
        }, /**
         * 根据切分标识获得字符串的前缀
         * @param str 字符串
         * @param mark 标识
         * @return string
         */
        subPrefix: function (str, mark) {
            if (TS.string.isEmpty(str) || TS.string.isEmpty(mark)) {
                return "";
            }
            var obj = str.split(mark);
            return obj[0];
        }, /**
         * 根据切分标识获得字符串的后缀
         * @param str 字符串
         * @param mark 标识
         * @return string
         */
        subSuffix: function (str, mark) {
            if (TS.string.isEmpty(str) || TS.string.isEmpty(mark)) {
                return "";
            }
            var obj = str.split(mark);
            return obj[1];
        }, /**
         * 字符是包含字符串
         * @param str1 字符串
         * @param str2 被包含的字符串
         * @return boolean
         */
        isMatch: function (str1, str2) {
            if (TS.string.isEmpty(str1) || TS.string.isEmpty(str2)) {
                return false;
            }
            return str1.indexOf(str2) != -1;
        }, /**
         * 字符是否以字符串结束
         * @param str1 字符串
         * @param str2 被包含的字符串
         * @return boolean
         */
        isLastMatch: function (str1, str2) {
            if (TS.string.isEmpty(str1) || TS.string.isEmpty(str2)) {
                return false;
            }
            return str1.lastIndexOf(str2) == str1.length - 1;
        }, /**
         * 字符是否以字符串开始
         * @param str1 字符串
         * @param str2 被包含的字符串
         * @return boolean
         */
        isFirstMatch: function (str1, str2) {
            if (TS.string.isEmpty(str1) || TS.string.isEmpty(str2)) {
                return false;
            }
            return str1.indexOf(str2) == 0;
        }, /**
         * 计算字节数,全角或汉字2个字符,半角1个字符
         * @param varField
         * @return number 字数
         */
        getTotalBytes: function (varField) {
            if (TS.string.isEmpty(varField)) {
                return 0;
            }
            var l = 0;
            var c = varField.split("");

            for (var i = 0; i < c.length; i++) {
                if (c[i].charCodeAt(0) > 299)
                    l += 2; else
                    l++;
            }
            return l;
        }, /**
         * 字符串去空格
         * @param sInputString 去掉空格的字符串
         * @return string 处理后的字符串
         */
        cTrim: function (sInputString) {
            if (TS.string.isEmpty(sInputString)) {
                return "";
            }
            return sInputString.replace(/(^\s*)|(\s*$)/g, "").replace(/(^　*)|(　*$)/g, "");
        }

    };
    TS.date = {
        /**
         * 比较两个日期大小
         * @param date1 日期1
         * @param date2 日期2
         * @returns number 如果参数表示的时间等于此表示的时间，则返回 0 值；
         */
        compareTo: function (date1, date2) {
            var d1 = TS.date.toDate(date1);
            var d2 = TS.date.toDate(date2);
            if (d1 == null || d2 == null) {
                return null;
            }
            if (d1 > d2) {
                return 1;
            } else if (d1 < d2) {
                return -1;
            } else {
                return 0;
            }
        }, /**
         * 将字符串转换日期对象
         * @param date 字符串描述的日期
         * @returns Date
         */
        toDate: function (date) {
            if (TS.string.isNotEmpty(date)) {
                return new Date(Date.parse(date.replace(/-/g, "/")));
            }
            return null;
        }, /**
         * 将日期对象转换成字符串描述的日期
         * @param date Date
         * @param format 格式
         * @return string
         */
        toString: function (date, format) {
            if (TS.object.type(date) !== "date") {
                return null;
            }
            var formatDate = TS.string.isNotEmpty(format) ? format : defaults.dateFormat;
            var o = {
                "M+": date.getMonth() + 1, //month
                "d+": date.getDate(), //day
                "h+": date.getHours(), //hour
                "m+": date.getMinutes(), //minute
                "s+": date.getSeconds(), //second
                "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
                "S": date.getMilliseconds() //millisecond
            };
            if (/(y+)/.test(formatDate)) {
                formatDate = formatDate.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").exec(formatDate)) {
                    formatDate = formatDate.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return formatDate;
        }, /**
         * 获得某年某月的最大天数
         * @param year 年
         * @param month 月
         * @return number 日
         */
        getMaxDay: function (year, month) {
            var data = 31;
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                data = 30;
            }
            if (month == 2) {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    data = 29;
                } else {
                    data = 28;
                }
            }
            return data;
        }
    };

    TS.array = {
        /**
         * 获得字符串在数组中的位置
         * @param arr 数组
         * @param str 数组中的元素
         * @return number
         */
        arrayIndexOf: function (arr, str) {
            if (TS.array.isEmptyArray(arr) || TS.string.isEmpty(str)) {
                return null;
            } else {
                for (var k in arr) {
                    if (arr[k] == str) {
                        return k;
                    }
                }
                return null;
            }
        }, /**
         * 判定是否是数组
         * @param arr 数组
         * @return boolean
         */
        isArray: function (arr) {
            return TS.object.type(arr) === "array";
        },

        /**
         * 判定是否是数组
         * @param arr 数组
         * @return boolean
         */
        isEmptyArray: function (arr) {
            return TS.object.type(arr) != "array" || arr.length <= 0;
        }, /**
         * 将数组转换成json
         * @param arr 数组
         * @return string json
         */
        toJson: function (arr) {
            var arrayEach = function (arr) {
                var arrayJson = '';
                if (!TS.array.isEmptyArray(arr)) {
                    for (var k in arr) {
                        var v = arr[k];
                        if (!TS.array.isArray(v)) {
                            arrayJson += '"' + k + '":"' + v + '",';
                        } else {
                            arrayJson += '"' + k + '":' + arrayEach(v) + ',';
                        }
                    }
                    arrayJson = subJsonString(arrayJson);
                }
                return '{' + arrayJson + '}';
            };
            var subJsonString = function (str) {
                if (str != "" && TS.object.type(str) == "string") {
                    return str.substring(0, str.length - 1);
                }
                return null;
            };
            if (TS.array.isEmptyArray(arr)) {
                return null;
            }
            return arrayEach(arr);
        }
    };
    TS.json = {
        /**
         * 将json转换成数组
         * @param json json格式描述的字符串
         * @return array
         */
        toArray: function (json) {
            if (TS.string.isNotEmpty(json)) {
                return eval(json);
            }
            return null;
        }
    };
    TS.eregi = {
        // 正则定义
        code: {
            "Integer": /^[-]{0,1}[0-9]{1,}$/,
            "Number": /^[0-9]+$/,
            "Decimal": /^[-]{0,1}(\d+)[\.]+(\d+)$/,
            "Money": /^[0-9]+[\.]{0,1}[0-9]{0,2}$/,
            "Email": /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/,
            "IP": /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/,
            "Mobile": /^[0]{0,1}[1][3|5|8][0-9]{9}$/,
            "Phone": /^[0][1-9]{2,3}-[0-9]{5,10}$/,
            "PhoneNoArea": /^[1-9]{1}[0-9]{5,8}$/,
            "NumberOr_Letter": /^[0-9a-zA-Z\_]+$/,
            "NumberOrLetter": /^[0-9a-zA-Z]+$/,
            "ChinaOrNumbOrLetter": /^[0-9a-zA-Z\u4e00-\u9fa5]+$/,
            "DateTime": /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/,
            "Date": /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/,
            "Time": /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/,
            "QQ": /[1-9][0-9]{4,}$/,
            "Extend": ""
        },

        /**
         * 检查输入对象的值是否符合整数格式
         * @param str
         * @returns {boolean}
         */
        integer: function (str) {
            return TS.eregi.code.Integer.test(str);
        }, /**
         * 检查输入字符串是否符合正整数格式
         * @param str
         * @returns {boolean}
         */
        number: function (str) {
            return TS.eregi.code.Number.test(str);
        }, /**
         * 检查输入字符串是否是带小数的数字格式,可以是负数
         * @param str
         * @returns {boolean}
         */
        decimal: function (str) {
            return TS.eregi.code.Decimal.test(str);
        }, /**
         * 检查输入字符串是否符合金额格式,格式定义为带小数的正数，小数点后最多两位
         * @param str
         * @returns {boolean}
         */
        money: function (str) {
            return TS.eregi.code.Money.test(str);
        }, /**
         * 检查输入的Email信箱格式是否正确
         * @param str
         * @returns {boolean}
         */
        email: function (str) {
            return TS.eregi.code.Email.test(str);
        }, /**
         * 校验ip地址的格式
         * @param str
         * @returns {boolean}
         */
        ip: function (str) {
            if (!TS.eregi.code.IP.test(str)) {
                return false;
            }
            var regIp = TS.eregi.code.IP.exec(str);
            var ip1 = Number(regIp[1]);
            var ip2 = Number(regIp[2]);
            var ip3 = Number(regIp[3]);
            var ip4 = Number(regIp[4]);
            var boos = ip1 >= 0 && ip1 != 127 && ip1 <= 233;
            boos = boos && ip2 >= 0 && ip2 <= 255;
            boos = boos && ip3 >= 0 && ip3 <= 255;
            boos = boos && ip4 >= 0 && ip4 <= 255;
            return boos;
        }, /**
         * 检查输入手机号码是否正确
         * @param str
         * @returns {boolean}
         */
        mobile: function (str) {
            return TS.eregi.code.Mobile.test(str);
        }, /**
         * 检查输入的电话号码格式是否正确 (带区号)
         * @param str
         * @returns {boolean}
         */
        phone: function (str) {
            return TS.eregi.code.Phone.test(str);
        }, /**
         * 检查输入的电话号码格式是否正确
         * @param str
         * @returns {boolean}
         */
        phoneNoArea: function (str) {
            return TS.eregi.code.PhoneNoArea.test(str);
        }, /**
         * 检查输入字符串是否只由英文字母和数字和下划线组成
         * @param str
         * @returns {boolean}
         */
        numberOr_Letter: function (str) {
            return TS.eregi.code.NumberOr_Letter.test(str);
        }, /**
         * 检查输入字符串是否只由英文字母和数字组成
         * @param str
         * @returns {boolean}
         */
        numberOrLetter: function (str) {
            return TS.eregi.code.NumberOrLetter.test(str);
        }, /**
         * 检查输入字符串是否只由汉字、字母、数字组成
         * @param str
         * @returns {boolean}
         */
        chinaOrNumbOrLetter: function (str) {
            return TS.eregi.code.ChinaOrNumbOrLetter.test(str);
        }, /**
         * 判断输入是否是有效的长日期格式 - "YYYY-MM-DD HH:MM:SS" || "YYYY/MM/DD HH:MM:SS"
         * @param str
         * @returns {boolean}
         */
        dateTime: function (str) {
            if (!TS.eregi.code.DateTime.test(str)) {
                return false;
            }
            var regDT = TS.eregi.code.DateTime.exec(str);
            var dy = Number(regDT[1]);
            var dm = Number(regDT[3]);
            var dd = Number(regDT[4]);
            var th = Number(regDT[5]);
            var tm = Number(regDT[6]);
            var ts = Number(regDT[7]);
            var boos = dy > 1970;
            boos = boos && dm > 0 && dm <= 12;
            boos = boos && dd > 0 && dd <= TS.date.getMaxDay(dy, dm);
            boos = boos && th >= 0 && th <= 23;
            boos = boos && tm >= 0 && tm <= 59;
            boos = boos && ts >= 0 && ts <= 59;
            return boos;
        }, /**
         * 判断输入是否是有效的时间格式 - "HH:MM:SS"
         * @param str
         * @returns {boolean}
         */
        time: function (str) {
            if (!TS.eregi.code.Time.test(str)) {
                return false;
            }
            var regTime = TS.eregi.code.Time.exec(str);
            var th = Number(regTime[1]);
            var tm = Number(regTime[2]);
            var ts = Number(regTime[3]);
            var boos = th >= 0 && th <= 23;
            boos = boos && tm >= 0 && tm <= 59;
            boos = boos && ts >= 0 && ts <= 59;
            return boos;
        }, /**
         * 判断输入是否是有效的长日期格式 - "YYYY-MM-DD" || "YYYY/MM/DD"
         * @param str
         * @returns {boolean}
         */
        date: function (str) {
            if (!TS.eregi.code.Date.test(str)) {
                return false;
            }
            var regDate = TS.eregi.code.Date.exec(str);
            var dy = Number(regDate[1]);
            var dm = Number(regDate[3]);
            var dd = Number(regDate[4]);
            var boos = dy > 1970;
            boos = boos && dm > 0 && dm <= 12;
            boos = boos && dd > 0 && dd <= TS.date.getMaxDay(dy, dm);
            return boos;
        }, /**
         * 判断输入是否是有效的长度的QQ
         * @param str
         * @returns {boolean}
         */
        qq: function (str) {
            return TS.eregi.code.QQ.test(str);
        }, /**
         * 自定义正则表达式
         * @param str
         * @returns {boolean}
         */
        extend: function (str) {
            var ex = TS.eregi.code.Extend;
            if (TS.object.type(ex) === "regexp") {
                return new RegExp(ex).test(str);
            }
            return false;
        }
    };
    window.TS = TS;
})(window);

