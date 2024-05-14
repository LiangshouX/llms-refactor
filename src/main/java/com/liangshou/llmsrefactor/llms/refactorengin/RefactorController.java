package com.liangshou.llmsrefactor.llms.refactorengin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.liangshou.llmsrefactor.llms.refactorengin.rabbitmq.MqConstant.CODE_DATA;

/**
 * @author X-L-S
 */
@RestController
@RequestMapping("/refactor")
public class RefactorController {

    private final RefactorService refactorService;

    public RefactorController(RefactorService refactorService) {
        this.refactorService = refactorService;
    }

    @GetMapping("/do")
    public void doRefactor (@RequestParam() Long id,
                            @RequestParam(defaultValue = CODE_DATA) String dataType) {
        refactorService.doRefactorAndCompare(id, dataType);
    }

    @GetMapping("/doAll")
    public void doRefactorAll () {
        refactorService.doRefactorAll();
    }

    @GetMapping("/compareAll")
    public void doCompareAll () {
        refactorService.doRefactorCompareAll();
    }

    @GetMapping("/compareTwoCode")
    public void doCompareTwoCodeAll () {
        refactorService.doCompareTwoCodeAll();
    }
}
