package com.liangshou.llmsrefactor.llms.refactorengin;

import com.google.common.util.concurrent.RateLimiter;
import com.liangshou.llmsrefactor.codedata.entity.CodeCompareEntity;
import com.liangshou.llmsrefactor.codedata.repository.CodeCompareRepository;
import com.liangshou.llmsrefactor.codedata.repository.CodeDataRepository;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import com.liangshou.llmsrefactor.llms.refactorengin.rabbitmq.MyMessageProducer;
import org.springframework.stereotype.Service;

import static com.liangshou.llmsrefactor.llms.refactorengin.rabbitmq.MqConstant.CODE_COMPARE;
import static com.liangshou.llmsrefactor.llms.refactorengin.rabbitmq.MqConstant.CODE_DATA;

/**
 * @author X-L-S
 */
@Service
public class RefactorService {

    private final MyMessageProducer myMessageProducer;

    private final CodeDataRepository codeDataRepository;

    private final CodeCompareRepository codeCompareRepository;

    // 创建一个初始速率为每秒 1 个令牌的限流器
    private final RateLimiter rateLimiter = RateLimiter.create(1);

    public RefactorService (MyMessageProducer myMessageProducer,
                            CodeDataRepository codeDataRepository,
                            CodeCompareRepository codeCompareRepository) {
        this.myMessageProducer = myMessageProducer;
        this.codeDataRepository = codeDataRepository;
        this.codeCompareRepository = codeCompareRepository;
    }

    public Long doRefactor (Long id, String dataType) {

        myMessageProducer.sendMessage("code_exchange", "my_routingKey",
                "%s:%s".formatted(dataType, String.valueOf(id)));

        return id;
    }

    public void doRefactorAll () {

        for ( Long id = 1L; id <= 48; id++) {
            CodeDataEntity codeData = codeDataRepository.findById(id).get();

            if (codeData.getNewCode() != null) {
                continue;
            }
            doRefactor(id, CODE_DATA);
        }
    }

    public void doRefactorCompareAll () {
        for (long id = 1L; id <= 48; id++){
            CodeCompareEntity codeCompare = codeCompareRepository.findById(id).get();

            if (codeCompare.allNewCodeDone()) {
                continue;
            }

            rateLimiter.acquire();
            doRefactor(id, CODE_COMPARE);
        }
    }
}
