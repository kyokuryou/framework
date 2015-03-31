<script type="text/javascript" src="${base}/common/js/jquery/jquery.ui.core.js"></script>
<script type="text/javascript" src="${base}/common/js/jquery/jquery.ui.datepicker.js"></script>
<link href="${base}/common/css/ui/jquery.ui.all.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
    var _dpLocale = {
        closeText:'关闭',
        prevText:'&#x3c;上月',
        nextText:'下月&#x3e;',
        currentText:'今天',
        monthNames:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort:['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
        dayNames:['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort:['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin:['日', '一', '二', '三', '四', '五', '六'],
        weekHeader:'周',
        dateFormat:'yy-mm-dd',
        firstDay:7,
        isRTL:false,
        yearSuffix:'年'
    };
    var _dpOptions = {
        changeMonth:true,
        changeYear:true,
        showButtonPanel:true,
        showOtherMonths:true,
        selectOtherMonths:true,
        showMonthAfterYear:true,
        showAnim:"fadeIn",
        yearRange:"1970:2099"
    };
    $(function () {
        $.datepicker.setDefaults(_dpLocale);
        $("input.datePicker").attr("readonly", true);
        $("input.datePicker").datepicker(_dpOptions);
        $.bindDatePicker = function () {
            $("input.datePicker").datepicker(_dpOptions);
        };
    });
</script>