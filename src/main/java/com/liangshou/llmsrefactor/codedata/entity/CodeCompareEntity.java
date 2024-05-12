package com.liangshou.llmsrefactor.codedata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author X-L-S
 */
@Entity
@Table(name = "code_compare")
@Getter
@Setter
public class CodeCompareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "file_name", nullable = false)
    String fileName;

    @Column(name = "language_type", nullable = false)
    String languageType;

    @Column(name = "origin_code")
    String originCode;

    @Column(name = "new_code_1")
    String newCode1;

    @Column(name = "new_code_2")
    String newCode2;

    @Column(name = "new_code_3")
    String newCode3;

    @Column(name = "new_code_4")
    String newCode4;

    @Column(name = "new_code_5")
    String newCode5;

    @Column(name = "new_code_6")
    String newCode6;

    @Column(name = "new_code_7")
    String newCode7;

    @Column(name = "new_code_8")
    String newCode8;

    @Column(name = "new_code_9")
    String newCode9;

    @Column(name = "new_code_10")
    String newCode10;

    public boolean allNewCodeDone () {
        return newCode1 != null &&
                newCode2 != null &&
                newCode3 != null &&
                newCode4 != null &&
                newCode5 != null &&
                newCode6 != null &&
                newCode7 != null &&
                newCode8 != null &&
                newCode9 != null &&
                newCode10 != null ;
    }

    public List<String> getNewCodeList () {
        if (!allNewCodeDone()) {
            throw new RuntimeException("Existing NULL Code， check please...");
        }
        List<String> newCodeList = new ArrayList<>(10);
        newCodeList.add(newCode1);
        newCodeList.add(newCode2);
        newCodeList.add(newCode3);
        newCodeList.add(newCode4);
        newCodeList.add(newCode5);
        newCodeList.add(newCode6);
        newCodeList.add(newCode7);
        newCodeList.add(newCode8);
        newCodeList.add(newCode9);
        newCodeList.add(newCode10);
        return newCodeList;
    }
}
