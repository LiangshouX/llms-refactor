package com.liangshou.llmsrefactor.llms.refactorengin;

import com.liangshou.llmsrefactor.codedata.CodeDataRepository;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import com.liangshou.llmsrefactor.llms.openai.GptCompletionService;
import com.liangshou.llmsrefactor.llms.refactorengin.rabbitmq.MyMessageProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author X-L-S
 */
@Service
public class RefactorService {

    private final MyMessageProducer myMessageProducer;

    private final GptCompletionService gptCompletionService;

    private final CodeDataRepository codeDataRepository;


    public RefactorService (MyMessageProducer myMessageProducer,
                            GptCompletionService gptCompletionService,
                            CodeDataRepository codeDataRepository) {
        this.myMessageProducer = myMessageProducer;
        this.gptCompletionService = gptCompletionService;
        this.codeDataRepository = codeDataRepository;
    }

    public Long doRefactor (Long id) {

        myMessageProducer.sendMessage("code_exchange", "my_routingKey", String.valueOf(id));

        return id;
    }

    public void doRefactorAll () {

        for ( Long id = 1L; id <= 48; id++) {
            CodeDataEntity codeData = codeDataRepository.findById(id).get();

            if (codeData.getNewCode() != null) {
                continue;
            }
            doRefactor(id);
        }
    }
}
