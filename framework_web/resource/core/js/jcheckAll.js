/**
 *
 */
(function ($) {
    var defData = {
        allchbox: "", chboxarea: "", checked: function (obj) {
        }, unchecked: function (obj) {
        }
    };
    // 事件绑定
    var bind = {
        /**
         * 全选按钮
         */
        allchbox: function () {
            $(defData.allchbox).bind("change", function () {
                handle.allBox($(this));
            });
        }, chboxarea: function () {
            $(defData.chboxarea).find(":checkbox").bind("change", function () {
                var $this = $(this);
                handle.areaDown($this);
                handle.areaUp($this);
            });
        }
    };
    // 处理
    var handle = {
        /**
         * 全选处理
         * @param $obj 全选按钮
         */
        allBox: function ($obj) {
            var $chboxs = $(defData.chboxarea).find(":checkbox");
            if ($obj.is(":checked")) {
                $chboxs.prop("checked", true);
                defData.checked($obj);
            } else {
                $chboxs.prop("checked", false);
                defData.unchecked($obj);
            }
        }, /**
         * 向上选择处理
         * @param $obj 当前对象
         */
        areaUp: function ($obj) {
            // TODO
            var $parent = $obj.parent();
            while (true) {
                //当父节点名为body或html的时候跳出循环
                if (TS.object.isSuperTag($parent[0])) {
                    break;
                }
                //sibAllchecked保存当前节点同级节点被选中的个数
                var sibAllchecked = 0;
                //遍历当前节点的同级节点，获得被选中的个数
                $parent.siblings().each(function (index) {
                    if ($(this).children().prop("checked") && $parent.children().prop("checked")) {
                        sibAllchecked++;
                    }
                });
                //当当前节点的同级节点的个数等于选中节点的个数，然后就修改当前节点的父节点的父节点的 父节点的第一个子节点的checked属性为true否则修改为false
                if (sibAllchecked == $parent.siblings().length) {
                    $parent.parent().parent().children().prop("checked", true);
                    defData.checked($obj);
                } else {
                    $parent.parent().parent().children().prop("checked", false);
                    defData.unchecked($obj);
                }
                $parent = $parent.parent();
            }
            /**
             * 检查是不是全选，如果全选下面的子节点都选中，则全选，否则，取消全选
             * $chboxs保存所有的checkbox
             * allChBoxes保存所有被选中的checkbox
             */
            var $chboxs = $(defData.chboxarea).find(":checkbox");
            var allChBoxes = 0;
            $chboxs.each(function (i) {
                if ($(this).is(":checked")) {
                    allChBoxes++;
                }
            });
            if (allChBoxes == $chboxs.length) {
                $(defData.allchbox).prop("checked", true);
            } else {
                $(defData.allchbox).prop("checked", false);
            }
        }, /**
         * 向下选择处理
         * @param $obj 当前对象
         */
        areaDown: function ($obj) {
            var $nchboxs = $obj.next().find(":checkbox");
            if ($obj.is(":checked")) {
                $nchboxs.prop("checked", true);
                defData.checked($obj);
            } else {
                $nchboxs.prop("checked", false);
                defData.unchecked($obj);
            }

        }
    };
    $.jcheckAll = function (data) {
        $.extend(defData, data);
        if (TS.string.isNotEmpty(data.allchbox)) {
            bind.allchbox();
        }
        if (TS.string.isNotEmpty(data.chboxarea)) {
            bind.chboxarea();
        }
    };
})(jQuery);