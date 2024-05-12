package com.liangshou.llmsrefactor.codedata.repository;

import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 操作代码文件相关的接口
 *
 * @author X-L-S
 */
public interface CodeDataRepository extends
        CrudRepository<CodeDataEntity, Long> {

    Page<CodeDataEntity> findAll(Pageable pageable);
    // 添加根据 fileName 查询的方法
    Optional<CodeDataEntity> findByFileName(String fileName);

    // 查询多个匹配的记录，可以返回 List 或者 Page
    List<CodeDataEntity> findAllByFileName(String fileName);

    // 分页查询
    Page<CodeDataEntity> findAllByFileName(String fileName, Pageable pageable);
}
