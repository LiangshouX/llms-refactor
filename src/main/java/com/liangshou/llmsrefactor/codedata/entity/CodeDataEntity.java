package com.liangshou.llmsrefactor.codedata.entity;

import com.liangshou.llmsrefactor.codedata.request.CreateCodeRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * 代码数据的实体类
 * @author X-L-S
 */
@Entity
@Table(name = "code_data_level2")
@Getter
@Setter
public class CodeDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "file_name", nullable = false)
    String fileName;

    @Column(name = "language_type", nullable = false)
    String languageType;

    @Column(name = "origin_code")
    String originCode;

    @Column(name = "origin_report")
    String originReport;

    @Column(name = "origin_num_problem")
    Integer originNumProblem;

    @Column(name = "new_code")
    String newCode;


    @Column(name = "new_report")
    String newReport;

    @Column(name = "new_num_problem")
    Integer newNumProblem;

    @Column(name = "is_same")
    Boolean isSame;

    @Column(name = "description")
    String description;

    @Column(name = "create_time")
    Instant createAt;

    @Column(name = "update_time")
    Instant updateAt;

    public static CodeDataEntity fromRequest(CreateCodeRequest request){
        CodeDataEntity codeDataEntity = new CodeDataEntity();
        // TODO
        codeDataEntity.languageType = request.languageType();
        codeDataEntity.originCode = request.originCode();
        return codeDataEntity;
    }

    static CodeDataEntity fromCodeData(CodeData codeData){
        CodeDataEntity entity = new CodeDataEntity();
        entity.id = Long.parseLong(codeData.id());
        entity.fileName = codeData.fileName();
        entity.languageType = codeData.languageType();
        entity.originCode = codeData.originCode();
        entity.originReport = codeData.originReport();
        entity.originNumProblem = codeData.originNumProblem();
        entity.newCode = codeData.newCode();
        entity.newReport = codeData.newReport();
        entity.newNumProblem = codeData.newNumProblem();
        entity.createAt = codeData.createAt();
        entity.updateAt = codeData.updateAt();
        return entity;
    }

    // TODO
    public CodeData toCodeData () {
        return new CodeData(fileName, languageType, originCode,originReport,
                originNumProblem, newCode, newReport, newNumProblem, isSame);
    }
}
