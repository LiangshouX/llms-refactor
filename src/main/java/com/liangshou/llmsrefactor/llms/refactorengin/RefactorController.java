package com.liangshou.llmsrefactor.llms.refactorengin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void doRefactor (Long id) {
        refactorService.doRefactor(id);
    }

    @GetMapping("/doAll")
    public void doRefactorAll () {
        refactorService.doRefactorAll();
    }
}
