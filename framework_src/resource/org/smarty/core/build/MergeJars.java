package org.test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Ant合并jar包工具.
 */
public class MergeJars {
    private final String VERSION = "1.0";
    private final String IGNORED_EXISTING_HEADERS ="Bnd-LastModified,Import-Package,Export-Package,Tool";
    // MANIFEST.mf依赖key
    private final String[] MANIFEST_ATTR = {"Import-Package", "Import-Template", "Export-Package", "Ignored-Existing-Headers"};
    // 忽略文件式
    private final String IGNORED_REG = "^.*(?=(overview.html|META-INF|META-INF/maven|LICENSE|NOTICE|README|META-INF/MANIFEST.MF)).*$";
    @Deprecated
    private String DISPATCHER_REG;

    public static void main(String[] args) throws Exception {
        MergeJars mj = new MergeJars();
        // mj.createJarFile("D:\\usr\\iwork\\test\\example\\libs", "D:\\usr\\iwork\\test\\example\\build\\test.jar");
        // ant运行模式
        try {
            mj.createJarFile(args[0], args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建jar文件
     *
     * @param srcPath jar源
     * @param target  目标
     * @throws java.io.IOException 创建错误
     */
    public void createJarFile(String srcPath, String target) throws IOException {
        if (target == null || "".equals(target)) {
            return;
        }
        OutputStream os = new FileOutputStream(target);
        List<File> jars = getJars(srcPath);
        Manifest manifest = createManifest(jars);
        JarOutputStream jos = new JarOutputStream(os, manifest);
        try {
            for (File jar : jars) {
                addZipFile(jar, jos);
                jos.flush();
            }
            // TODO README
        } finally {
            jos.flush();
            jos.close();
        }
    }

    /**
     * jar源 MANIFEST.MF文件合并到一个新的MANIFEST.MF
     *
     * @param jars jar源
     * @return MANIFEST.MF
     */
    public Manifest createManifest(List<File> jars) {
        List<Manifest> manifests = getManifestList(jars);
        Manifest mf = new Manifest();
        Attributes attr = mf.getMainAttributes();
        attr.putValue("Manifest-Version", VERSION);
        attr.putValue("Ignored-Existing-Headers",IGNORED_EXISTING_HEADERS);
        for (String mfattr : MANIFEST_ATTR) {
            attr.putValue(mfattr, mergeManifest(manifests, mfattr, ","));
        }
        return mf;
    }

    /**
     * 获取指定路径下所有jar文件(包括子目录)
     *
     * @param path jar路径
     * @return jar文件列表
     */
    public List<File> getJars(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        final List<File> fList = new ArrayList<File>();
        File[] fs = file.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    fList.addAll(getJars(pathname.getPath()));
                }
                return pathname.getName().endsWith(".jar");
            }
        });
        if (fs != null) {
            fList.addAll(Arrays.asList(fs));
        }
        return fList;
    }

    /**
     * 将源jar添加到目标流
     *
     * @param jar 源jar
     * @param jos 目标流
     * @throws java.io.IOException 写入错误
     */
    private void addZipFile(File jar, JarOutputStream jos) throws IOException {
        if (jar == null) {
            return;
        }
        ZipFile zf = new ZipFile(jar);
        Enumeration<?> ens = zf.entries();
        while (ens.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) ens.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            if (isIgnored(entry)) {
                continue;
            }
            jos.putNextEntry(new ZipEntry(entry.getName()));
            copyEntry(zf, entry, jos);
        }
    }

    /**
     * 打开源jar,把文件复制到目标流
     *
     * @param jar   源jar
     * @param entry 源jar 文件
     * @param os    目标流
     * @throws java.io.IOException 你懂得
     */
    private void copyEntry(ZipFile jar, ZipEntry entry, JarOutputStream os) throws IOException {
        InputStream in = jar.getInputStream(entry);
        int read = 0;
        while ((read = in.read()) != -1) {
            os.write(read);
        }
        in.close();
        os.closeEntry();
        os.flush();
    }

    /**
     * 检查是否是忽略文件
     *
     * @param entry 包内文件
     * @return true忽略, false不忽略
     */
    private boolean isIgnored(ZipEntry entry) {
        String name = entry.getName();
        if (name == null || "".equals(name)) {
            return true;
        }
        Pattern ign = Pattern.compile(IGNORED_REG, Pattern.CASE_INSENSITIVE);
        Matcher ignm = ign.matcher(name);
        return ignm.find();
    }

    /**
     * 合并MANIFEST.MF
     *
     * @param manifests MANIFEST.MF列表
     * @param attrName  合并属性名
     * @param split     分隔符
     * @return 合并后字符串
     */
    private String mergeManifest(List<Manifest> manifests, String attrName, String split) {
        StringBuffer sb = new StringBuffer();
        for (Manifest manifest : manifests) {
            Attributes mfattr = manifest.getMainAttributes();
            String attrVal = mfattr.getValue(attrName);
            if (attrVal == null || "".equals(attrVal)) {
                continue;
            }
            String[] vals = attrVal.split(split);
            for (String val : vals) {
                if (sb.indexOf(val) == -1) {
                    sb.append(val).append(split);
                }
            }
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * 获取指定jar列表中MANIFEST.mf
     *
     * @param jars jar列表
     * @return MANIFEST.mf列表
     */
    private List<Manifest> getManifestList(List<File> jars) {
        List<Manifest> mfList = new ArrayList<Manifest>();
        for (File jar : jars) {
            try {
                ZipFile jarFile = new ZipFile(jar);
                ZipEntry ze = jarFile.getEntry("META-INF/MANIFEST.MF");
                InputStream is = jarFile.getInputStream(ze);
                mfList.add(new Manifest(is));
            } catch (Exception ignored) {
            }
        }
        return mfList;
    }


    /**
     * 检查目标流中该文件存在
     *
     * @param entry 文件
     * @return true存在, false不存在
     * @deprecated 与#isIgnored方法一起使用时冲突, 阻止子级目录无法继续迭代
     */
    @Deprecated
    private boolean isExistDir(ZipEntry entry) {
        if (DISPATCHER_REG == null || "".equals(DISPATCHER_REG)) {
            return false;
        }
        Pattern dir = Pattern.compile(DISPATCHER_REG, Pattern.CASE_INSENSITIVE);
        Matcher dirm = dir.matcher(entry.getName());
        return dirm.find();
    }

    /**
     * 把指定目录加入正则式
     *
     * @param entry 文件
     * @deprecated 与#isExistDir, #isIgnored方法一起使用时冲突, 阻止子级目录无法继续迭代
     */
    @Deprecated
    private void addDirectory(ZipEntry entry) {
        String name = entry.getName();
        StringBuffer sb = new StringBuffer();
        if (DISPATCHER_REG == null || "".equals(DISPATCHER_REG)) {
            sb.append("^()+$");
        } else {
            sb.append(DISPATCHER_REG);
            sb.insert(sb.length() - 3, "|");
        }
        sb.insert(sb.length() - 3, name);
        DISPATCHER_REG = sb.toString();
    }
}
