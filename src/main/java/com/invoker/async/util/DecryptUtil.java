package com.invoker.async.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2021/9/7-10:17 上午
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
@Slf4j
public class DecryptUtil {

    public static final String AES_KEY = "52d637efddb9e8ad";

    public static final String FILE_NAME = "/Users/jasonsmart/Downloads/easy-code-template/yefeng/";


    private static final String IV_STRING = "0000000000000000";


    public static void fileDe() {
        List<Runnable> runners =
                FileUtil.loopFiles(FileUtil.file(FILE_NAME)).stream().filter(FileUtil::isFile).map(f -> new Runnable() {
                    @Override
                    public void run() {
                        decrypt(f, FileUtil.file(FILE_NAME + "de/" + f.getName()));
                    }
                }).collect(Collectors.toList());
        AsyncHelper.asyncRun(runners);
        log.info("解密完成！");
    }

    public static void decrypt(File file, File outFile) {
        try {
            AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, StrUtil.bytes(AES_KEY), StrUtil.bytes(IV_STRING));
            FileUtil.writeBytes(aes.decrypt(FileUtil.getInputStream(file)), outFile);
        } catch (Exception e) {
            log.info("文件名【{}】有错误！", file.getName());
            log.error("写文件错误！", e);
        }
    }

}
