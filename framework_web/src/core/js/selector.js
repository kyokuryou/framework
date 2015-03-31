/**
 * 让IE支持getElementsByClassName ◑﹏◐
 */
if (navigator.appName == 'Microsoft Internet Explorer') {
    document.getElementsByClassName = function () {
        var pObj;
        var tagName = "*";
        if (arguments.length > 1) {
            tagName = arguments[1];
        }
        if (arguments.length > 2) {
            pObj = arguments[2];
        } else {
            pObj = document;
        }
        var objArr = pObj.getElementsByTagName(tagName);
        var trObj = [];
        for (var i = 0; i < objArr.length; i++) {
            if (objArr[i].className == arguments[0]) {
                trObj.push(objArr[i]);
            }
        }
        return trObj;
    }
}

/**
 * 根据expr(表达式)查找父级
 * 返回:undefined,parent;
 */
jQuery.fn.findParent = function (expr) {
    var $this = $(this);
    var exprParent = undefined;
    var detectObj = function ($obj) {
        var errors = TS.object.isNotExist($obj[0]);
        errors = errors || TS.object.isSuperTag($obj[0]);
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
