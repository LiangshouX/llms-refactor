package com.liangshou.llmsrefactor.codedata;

import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * 操作代码文件相关的接口
 *
 * @author X-L-S
 */
public interface CodeDataRepository extends
        CrudRepository<CodeDataEntity, Long> {

    Page<CodeDataEntity> findAll(Pageable pageable);
}
