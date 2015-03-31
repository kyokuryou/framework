(function ($) {
    var init = false;
    var __data = {
        width: 430,
        height: 320,
        cns: {
            scope: ".encryption_scope",
            path: ".encryption_path",
            builder: ".encryption_builder",
            type: ".encryption_type",
            encode: ".encryption_encode",
            decode: ".encryption_decode",
            console: ".encryption_console"
        }
    };
    var __driver = {
        folder: "driver",
        code: {
            base: {
                file: "base.js",
                enMethod: "packerBase",
                deMethod: ""
            },
            base64: {
                file: "base64.js",
                enMethod: "enbase64",
                deMethod: "debase64"
            },
            md5: {
                file: "md5.js",
                enMethod: "hex_md5",
                deMethod: ""
            }

        }
    };
    /**
     * 初始化窗体
     * @param width 宽
     * @param height 高
     */
    var instance = {
        panelSite: function () {
            window.resizeTo(__data.width, __data.height);
            window.moveTo((screen.width - __data.width) / 2, (screen.height - __data.height) / 2);
        }
    };
    var console = {
        info: function (node, c) {
            var $console = $(__data.cns.console);
            var $span = create.span({color: ['#000', 'red'][c || 0]}, node);
            $console.append($span).append("<br/>");
            $console.scrollTop($console[0].scrollHeight);
        },
        clear: function () {
            $(__data.cns.console).empty();
        }
    };
    var load = {
        script: function (jsF, callSuccess) {
            var url = __driver["folder"] + "/" + jsF;
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                cache: true,
                dataType: "script",
                beforeSend: function () {
                    console.info("正在加载加密驱动。。。");
                },
                success: function () {
                    console.info("加密驱动成功载入。");
                    callSuccess();
                },
                error: function () {
                    console.info("加密驱动载入失败！", 1);
                }
            });
        }
    };
    var logic = {
        isEmpty: function (val) {
            return val == null || val == "";
        },
        isNotEmpty: function (val) {
            return val != null && val != "";
        },
        isNumber: function (val) {
            return val != null && !isNaN(val);
        },
        isFunction: function (fun) {
            return $.isFunction(fun);
        }
    };
    var run = {
        encode: function (type, file, cb) {
            var jsf = __driver.code[type]["file"];
            var enm = __driver.code[type]["enMethod"];
            if (logic.isEmpty(jsf) || logic.isEmpty(enm)) {
                console.info('暂时不支持，该加密方式！', 1);
                return;
            }
            if (!logic.isFunction(enm)) {
                load.script(jsf, function () {
                    run.enfile(enm, file, cb);
                });
            } else {
                run.enfile(enm, file, cb);
            }
        },
        enfile: function (enm, file, callback) {
            var fv = format.removeComments(io.read(file));
            alert(fv);
            console.info('正在加密中，请稍后。');
            var env = eval(enm).call(this, fv);
            if (logic.isNotEmpty(env)) {
                callback(env);
                console.info('加密成功。');
            } else {
                console.info('加密失败！', 1);
            }
        },
        decode: function (type, file, cb) {
            var jsf = __driver.code[type]["file"];
            var dem = __driver.code[type]["deMethod"];
            if (logic.isEmpty(jsf) || logic.isEmpty(dem)) {
                console.info('暂时不支持，该解密方式！', 1);
                return;
            }
            if (!logic.isFunction(dem)) {
                load.script(jsf, function () {
                    run.defile(dem, file, cb);
                });
            } else {
                run.defile(dem, file, cb);
            }
        },
        defile: function (dem, file, callback) {
            var fv = io.read(file);
            console.info('正在解密中，请稍后。');
            var dev = eval(dem).call(this, fv);
            if (logic.isNotEmpty(dev)) {
                callback(dev);
                console.info('解密成功。');
            } else {
                console.info('解密失败！', 1);
            }
        }
    };

    var checking = {
        scope: function ($chk) {
            var $cd = $chk.filter(":checked");
            if ($cd == null || $cd.length == 0) {
                console.info("请选择范围!", 1);
                return false;
            }
            return true;
        },
        path: function ($path) {
            var pv = $path.val();
            if (logic.isEmpty(pv)) {
                console.info("请选择正确的文件路径！", 1);
                return false;
            }
            return true;
        },
        builder: function ($builder) {
            var bv = $builder.val();
            if (logic.isEmpty(bv)) {
                console.info("请填写生成目录名！", 1);
                return false;
            }
            return true;
        },
        type: function ($type) {
            var tv = $type.val();
            if (logic.isEmpty(tv)) {
                console.info("请选择加密类型！", 1);
                return false;
            }
            return true;
        },
        all: function () {
            var boo = true;
            var $chk = $(__data.cns.scope);
            var $path = $(__data.cns.path);
            var $builder = $(__data.cns.builder);
            var $type = $(__data.cns.type);
            // boo = checking.scope($chk);
            boo = boo && checking.path($path);
            boo = boo && checking.builder($builder);
            boo = boo && checking.type($type);
            return boo;
        }
    };
    var create = {
        span: function (css, text) {
            var $span = $("<span/>");
            $span.css(css);
            $span.text(text);
            return $span;
        }
    };
    var io = {
        ax: function () {
            return new ActiveXObject("Scripting.FileSystemObject");
        },
        folder: function (folder) {
            return io.ax().GetFolder(folder);
        },
        file: function (fileName) {
            return io.ax().GetFile(fileName);
        },
        simpleFileName: function (fileName) {
            return io.ax().GetBaseName(fileName) + ".js";
        },
        fileList: function (folder) {
            var fk = new Enumerator(io.folder(folder).files);
            var fs = {};
            while (!fk.atEnd()) {
                fs[io.simpleFileName(fk.item())] = fk.item();
                fk.moveNext();
            }
            return fs;
        },
        read: function (file) {
            var input = io.ax().OpenTextFile(file, 1, true);
            return input.Readall();
        },
        createFolder: function (path) {
            var ax = io.ax();
            if (!ax.FolderExists(path)) {
                if (ax.CreateFolder(path)) {
                    console.info("创建文件夹成功！");
                } else {
                    console.info("创建文件夹失败！", 1);
                    return false;
                }
            }
            return true;
        },
        write: function (path, file, content) {
            if (io.createFolder(path)) {
                var output = io.ax().CreateTextFile(path + "/" + file, true);
                output.Write(content);
                output.Close();
            }
        },
        parentFolder: function (path) {
            return io.ax().getParentFolderName(path);
        }
    };
    var format = {
        removeComments: function (code) {
            return code.replace(/(?:^|\n|\r)\s*\/\*[\s\S]*?\*\/\s*(?:\r|\n|$)/g, '').replace(/(?:^|\n|\r)\s*\/\/.*(?:\r|\n|$)/g, '');
        }
    };

    var bind = {
        encode: function () {
            var $enbtn = $(__data.cns.encode);
            $enbtn.click(function () {
                var $path = $(__data.cns.path);
                var $builder = $(__data.cns.builder);
                var $type = $(__data.cns.type);
                if (checking.all()) {
                    var tv = $type.val();
                    var pv = $path.val();
                    var bv = $builder.val();
                    var pfv = io.parentFolder(pv);
                    var sn = io.simpleFileName(pv);
                    run.encode(tv, pv, function (cv) {
                        io.write(pfv + "/" + bv, sn, cv);
                    });
                }
            });
        },
        decode: function () {
            var debtn = $(__data.cns.decode);
            debtn.click(function () {
                var $path = $(__data.cns.path);
                var $builder = $(__data.cns.builder);
                var $type = $(__data.cns.type);
                if (checking.all()) {
                    var tv = $type.val();
                    var pv = $path.val();
                    var bv = $builder.val();
                    var pfv = io.parentFolder(pv);
                    var sn = io.simpleFileName(pv);
                    run.decode(tv, pv, function (cv) {
                        io.write(pfv + "/" + bv, sn, cv);
                    });
                }
            });
        },
        clear: function () {
            var $console = $(__data.cns.console);
            $console.dblclick(function () {
                console.clear();
            });
        }
    };

    /**
     * 入口
     * @param d 参数
     */
    $.encryption = function (d) {
        if (init) return;
        $.extend(__data, d);
        instance.panelSite();
        bind.encode();
        bind.decode();
        bind.clear();
        init = true;
    }
})(jQuery);