package com.liangshou.llmsrefactor.utils;

import lombok.extern.slf4j.Slf4j;
import okio.Path;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

/**
 * 用于加载并解析 Prompt Data 的工具类
 * @author X-L-S
 */
@Deprecated
@Slf4j
public class LoadPromptUtil {
    private static final String promptRootDir = "src/resources/prompt-data/";
    @Nullable
    public static String loadPrompt(String filePath) {
        filePath = promptRootDir + filePath;
        String fullPath = getFullFilePath(filePath);
        try {
            return Files.lines(Path.get(fullPath).toNioPath())
                    .collect(Collectors.joining(System.lineSeparator()));
        }catch (IOException e){
            log.error("文件%s加载失败！请检查文件名及路径等是否正确，并重试！".formatted(fullPath));
            return null;
        }

    }

    private static String getFullFilePath(String filePath){
        String rootPath = System.getProperty("user.dir");
        return Path.get(rootPath, Boolean.parseBoolean(filePath)).toString();
    }
}
