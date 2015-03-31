/**
 *
 */
(function (window, MT) {
    var defaults = {
        scale: 2
    };
    MT = {
        /**
         * 设置数值精度
         * @param scale 保留位数
         */
        setScale: function (scale) {
            defaults.scale = scale;
        },
        /**
         * 浮点数加法运算
         * @param arg1 值1
         * @param arg2 值2
         * @returns String 计算结果
         */
        add: function (arg1, arg2) {
            var num1, num2, val;
            var r1 = arg1.toString().split(".");
            var r2 = arg2.toString().split(".");

            if (r1.length > 1 && r1[1].length != 0) {
                num1 = r1[1].length;//num1等于arg1的小数位数
            } else {
                num1 = 0;//当arg1为整数时num1等于0
            }
            if (r2.length > 1 && r2[1].length != 0) {
                //num2等于arg2的小数位数
                num2 = r2[1].length;
            } else {
                num2 = 0;
            }
            //当num1>num2时,val等于10的num1次方,else val等于10的num2次方
            val = Math.pow(10, Math.max(num1, num2));
            //先把小数转换成整数然后进行相加，最后转换成小数，toFixed()是精确到小数多少位
            return ((arg1 * val + arg2 * val) / val).toFixed(defaults.scale);
        },
        /**
         * 浮点数减法运算
         * @param arg1 值1
         * @param arg2 值2
         * @returns String 计算结果
         */
        sub: function (arg1, arg2) {
            var num1, num2, val;
            var r1 = arg1.toString().split(".");
            var r2 = arg2.toString().split(".");
            if (r1.length > 1 && r1[1].length != 0) {
                //num1等于arg1的小数位数
                num1 = r1[1].length;
            } else {
                //当arg1为整数时num1等于0
                num1 = 0;
            }
            if (r2.length > 1 && r2[1].length != 0) {
                //num2等于arg2的小数位数
                num2 = r2[1].length;
            } else {
                num2 = 0;
            }
            //当num1>num2时,val等于10的num1次方,else val等于10的num2次方
            val = Math.pow(10, Math.max(num1, num2));
            //先把小数转换成整数然后进行相减，最后转换成小数，toFixed()是精确到小数多少位
            return ((arg1 * val - arg2 * val) / val).toFixed(defaults.scale);
        },
        /**
         * 浮点数乘法运算
         * @param arg1 值1
         * @param arg2 值2
         * @returns String 计算结果
         */
        mul: function (arg1, arg2) {
            var a1 = 0, a2 = 0, s1, s2;
            var r1 = arg1.toString().split(".");
            var r2 = arg2.toString().split(".");
            //a1、a2分别等于arg1、arg2的小数位数
            if (r1.length > 1 && r1[1].length != 0) {
                a1 = r1[1].length;
            } else {
                a1 = 0;
            }
            if (r2.length > 1 && r2[1].length != 0) {
                a2 = r2[1].length;
            } else {
                a2 = 0;
            }
            if (arg1.toString().indexOf(".") == -1) {
                s1 = arg1;
            } else {
                //把浮点数arg1转换成整数
                s1 = Number(arg1.toString().replace(".", ""));
            }
            if (arg2.toString().indexOf(".") == -1) {
                s2 = arg2;
            } else {
                //把浮点数arg2转换成整数
                s2 = Number(arg2.toString().replace(".", ""));
            }
            //把转换后的整数相乘，然后再转换成浮点数
            return ((s1 * s2) / Math.pow(10, a1 + a2)).toFixed(defaults.scale);
        },
        /**
         * 浮点数除法运算
         * @param arg1 值1
         * @param arg2 值2
         * @returns String 计算结果
         */
        div: function (arg1, arg2) {
            if (arg2 == 0) {
                return NaN;
            }
            var a1 = 0, a2 = 0, s1, s2;
            var r1 = arg1.toString().split(".");
            var r2 = arg2.toString().split(".");
            if (r1.length > 1 && r1[1].length != 0) {
                a1 = r1[1].length;
            } else {
                a1 = 0;
            }
            if (r2.length > 1 && r2[1].length != 0) {
                a2 = r2[1].length;
            } else {
                a2 = 0;
            }
            if (arg1.toString().indexOf(".") == -1) {
                s1 = arg1;
            } else {
                //把浮点数arg1转换成整数
                s1 = Number(arg1.toString().replace(".", ""));
            }
            if (arg2.toString().indexOf(".") == -1) {
                s2 = arg2;
            } else {
                //把浮点数arg2转换成整数
                s2 = Number(arg2.toString().replace(".", ""));
            }
            return ((s1 / s2) / Math.pow(10, a1 - a2)).toFixed(defaults.scale);
        }
    };
    window.MT = MT;
})(window);