package com.liangshou.llmsrefactor.codedata.repository;

import com.liangshou.llmsrefactor.codedata.entity.CodeCompareEntity;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author X-L-S
 */
public interface CodeCompareRepository extends
        CrudRepository<CodeCompareEntity, Long> {

    Page<CodeCompareEntity> findAll(Pageable pageable);

    Optional<CodeCompareEntity> findByFileName(String fileName);
}
