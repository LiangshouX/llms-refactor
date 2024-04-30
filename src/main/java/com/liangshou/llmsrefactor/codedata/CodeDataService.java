package com.liangshou.llmsrefactor.codedata;

import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Arrays;

import static com.liangshou.llmsrefactor.codedata.constant.CodeDataPathConstant.JAVA_RAW_RESOURCE_PATH;
import static com.liangshou.llmsrefactor.codedata.constant.CodeDataPathConstant.JAVA_RESOURCE_ROOT;


/**
 * @author X-L-S
 */
@Service
public class CodeDataService {
    private final CodeDataRepository codeDataRepository;

    private final ResourceLoader resourceLoader;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String rootPath = "codeData";

    public CodeDataService(CodeDataRepository codeDataRepository, ResourceLoader resourceLoader){
        this.codeDataRepository = codeDataRepository;
        this.resourceLoader = resourceLoader;
    }


    /**
     * 将指定语言类型的代码所在目录下的代码文件存入数据库中
     *
     * @throws IOException IO异常
     */
    public void saveFilesToDb (String languageType) throws IOException {
        switch (languageType) {
            case "java":
                rootPath = JAVA_RAW_RESOURCE_PATH;
                break;
            case "python":
                rootPath = null;
            default:
                logger.error("Language Type {} is not supported! ", languageType);
                throw new RuntimeException();
        }
        ClassPathResource resource = new ClassPathResource(rootPath);
        if(resource.exists()){
            File[] files = resource.getFile().listFiles();
            assert files != null;
            for(File file: files){
                if(file.isFile()){
                    logger.info("Starting save file {} to Database", file.getName());
                    resourceLoader.getResource("classpath:" + rootPath + "/" + file.getName());

                    byte[] content = Files.readAllBytes(file.toPath());

                    // 设置各个字段的属性
                    CodeDataEntity codeData = new CodeDataEntity();
                    codeData.setFileName(file.getName());
                    codeData.setLanguageType(file.getName().split("\\.")[1]);
                    codeData.setOriginCode(new String(content, StandardCharsets.UTF_8));
                    codeData.setCreateAt(Instant.now());
                    codeData.setUpdateAt(Instant.now());

                    codeDataRepository.save(codeData);

                }
            }
        }
        else {
            throw new IllegalArgumentException("The provided directory does not exist or is not a directory.");
        }
    }

    public String tmp(){
        return rootPath;
    }
}
