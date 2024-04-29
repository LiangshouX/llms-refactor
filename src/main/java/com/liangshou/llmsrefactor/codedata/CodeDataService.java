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

/**
 * @author X-L-S
 */
@Service
public class CodeDataService {
    private final CodeDataRepository codeDataRepository;

    private final ResourceLoader resourceLoader;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String directoryPath = "codeData";

    public CodeDataService(CodeDataRepository codeDataRepository, ResourceLoader resourceLoader){
        this.codeDataRepository = codeDataRepository;
        this.resourceLoader = resourceLoader;
    }


    /**
     * 将代码所在目录下的代码文件存入数据库中
     *
     * @throws IOException IO异常
     */
    public void saveFilesToDb () throws IOException {
        ClassPathResource resource = new ClassPathResource(directoryPath);
        if(resource.exists()){
            File[] files = resource.getFile().listFiles();
            assert files != null;
            for(File file: files){
                if(file.isFile()){
                    logger.info("Starting save file {} to Database", file.getName());
                    resourceLoader.getResource("classpath:" + directoryPath + "/" + file.getName());

                    byte[] content = Files.readAllBytes(file.toPath());

                    CodeDataEntity codeData = new CodeDataEntity();
                    codeData.setFileName(file.getName());
                    codeData.setLanguageType(file.getName().split("\\.")[1]);
                    codeData.setOriginCode(new String(content, StandardCharsets.UTF_8));
                    codeData.setCreateAt(Instant.now());
                    codeData.setUpdateAt(Instant.now());

                    codeDataRepository.save(codeData);
                    logger.info("Save file {} successfully", file.getName());
                }
            }
        }
        else {
            throw new IllegalArgumentException("The provided directory does not exist or is not a directory.");
        }
    }

    public String tmp(){
        return directoryPath;
    }
}
