package com.liangshou.llmsrefactor.metrics.pmd;

import com.liangshou.llmsrefactor.codedata.repository.CodeDataRepository;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import com.liangshou.llmsrefactor.metrics.pmd.entity.PmdAnalysisResult;
import com.liangshou.llmsrefactor.metrics.pmd.entity.ResultAnalysisEntity;
import jakarta.annotation.Resource;
import net.sourceforge.pmd.PMDConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.liangshou.llmsrefactor.metrics.pmd.constant.PmdConfigConstant.*;

/**
 * @author X-L-S
 */
@RestController
public class PmdAnalysisController {
    @Resource
    private PmdAnalysisService pmdAnalysisService;

    @Resource
    private CodeDataRepository codeDataRepository;

    @GetMapping("/analysis")
    public String doAnalysis(@RequestParam(defaultValue = "POWERSET.java") String fileName,
                             @RequestParam(defaultValue = "origin") String codeType){
        String filePath;

        switch (codeType) {
            case "origin":
                filePath = JAVA_CODE_PATH;
                break;
            case "new":
                filePath = JAVA_REFACTORED_PATH;
                break;
            default:
                throw new RuntimeException("Code Type Error: in origin or new.");
        }

        PMDConfiguration config = pmdAnalysisService.createPmdConfig("java");

        PmdAnalysisResult result = pmdAnalysisService.analyzeCode(config, filePath, fileName);

        CodeDataEntity codeEntity = codeDataRepository.findByFileName(fileName).get();

        codeEntity.setNewReport(result.getResultJson());
        codeEntity.setNewNumProblem(result.getTotal());


        codeEntity.setUpdateAt(Instant.now());

        codeDataRepository.save(codeEntity);

        System.out.println(result.getTotal());
        return result.getResultJson();
    }

    @GetMapping("/analysis/path")
    public void analyzePath (@RequestParam(defaultValue = "java") String languageType,
                             @RequestParam(defaultValue = "origin") String codeType){
        boolean isOrigin = switch (codeType) {
            case "origin" -> true;
            case "new" -> false;
            default -> throw new RuntimeException("Code Type Error: in origin or new.");
        };
        PMDConfiguration config = pmdAnalysisService.createPmdConfig("java");
        pmdAnalysisService.analyzePath(config, languageType, isOrigin);
    }

    @GetMapping("/analysis/result/single")
    public ResultAnalysisEntity singleResultAnalysis (@RequestParam(defaultValue = "1") String codeId,
                                                      @RequestParam(defaultValue = ORIGIN) String analysisType) {
        long id = Long.parseLong(codeId);
        boolean isOrigin = analysisType.equals(ORIGIN);
        return pmdAnalysisService.resultAnalysis(id, isOrigin);
    }

    @GetMapping("/analysis/result")
    public ResultAnalysisEntity resultAnalysis (@RequestParam(defaultValue = ORIGIN) String analysisType) {
        boolean isOrigin = analysisType.equals(ORIGIN);
        return pmdAnalysisService.resultAnalysisAll(isOrigin);
    }

    @GetMapping("/analysis/ruleset_count")
    public Map<String, Map<String, Integer>> analyzeRuleSetCount (@RequestParam(defaultValue = "Code Style") String ruleSetName,
                                                     @RequestParam(defaultValue = "origin") String reportType) {
        List<String> reportList = new ArrayList<>();
        Iterable<CodeDataEntity> codeDataEntityList = codeDataRepository.findAll();

        for (CodeDataEntity codeData: codeDataEntityList) {
            if (reportType.equals("origin")) {
                reportList.add(codeData.getOriginReport());
            }
            else if (reportType.equals("new")) {
                reportList.add(codeData.getNewReport());
            }
            else {
                throw new RuntimeException("Parameter reportType must be 'origin' or 'new', retry please");
            }
        }

        Map<String, Integer> rulesetCount = pmdAnalysisService.analyzeRuleCount(reportList, ruleSetName);

        return Map.of(ruleSetName, rulesetCount);
    }

    @GetMapping("/analysis/tes")
    public String tesDb(@RequestParam(required = true, defaultValue = "吴晓豪") String name)
            throws SQLException {
        return """
                  嘿嘿嘿，小笨蛋%s，没想到吧？就逗你玩，略略略略略~~~~
                  
                  *            .,,       .,:;;iiiiiiiii;;:,,.     .,,                  \s
                 *          rGB##HS,.;iirrrrriiiiiiiiiirrrrri;,s&##MAS,               \s
                 *         r5s;:r3AH5iiiii;;;;;;;;;;;;;;;;iiirXHGSsiih1,              \s
                 *            .;i;;s91;;;;;;::::::::::::;;;;iS5;;;ii:                 \s
                 *          :rsriii;;r::::::::::::::::::::::;;,;;iiirsi,              \s
                 *       .,iri;;::::;;;;;;::,,,,,,,,,,,,,..,,;;;;;;;;iiri,,.          \s
                 *    ,9BM&,            .,:;;:,,,,,,,,,,,hXA8:            ..,,,.      \s
                 *   ,;&@@#r:;;;;;::::,,.   ,r,,,,,,,,,,iA@@@s,,:::;;;::,,.   .;.     \s
                 *    :ih1iii;;;;;::::;;;;;;;:,,,,,,,,,,;i55r;;;;;;;;;iiirrrr,..      \s
                 *   .ir;;iiiiiiiiii;;;;::::::,,,,,,,:::::,,:;;;iiiiiiiiiiiiri        \s
                 *   iriiiiiiiiiiiiiiii;;;::::::::::::::::;;;iiiiiiiiiiiiiiiir;       \s
                 *  ,riii;;;;;;;;;;;;;:::::::::::::::::::::::;;;;;;;;;;;;;;iiir.      \s
                 *  iri;;;::::,,,,,,,,,,:::::::::::::::::::::::::,::,,::::;;iir:      \s
                 * .rii;;::::,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,::::;;iri      \s
                 * ,rii;;;::,,,,,,,,,,,,,:::::::::::,:::::,,,,,,,,,,,,,:::;;;iir.     \s
                 * ,rii;;i::,,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,,::i;;iir.     \s
                 * ,rii;;r::,,,,,,,,,,,,,:,:::::,:,:::::::,,,,,,,,,,,,,::;r;;iir.     \s
                 * .rii;;rr,:,,,,,,,,,,,,,,:::::::::::::::,,,,,,,,,,,,,:,si;;iri      \s
                 *  ;rii;:1i,,,,,,,,,,,,,,,,,,:::::::::,,,,,,,,,,,,,,,:,ss:;iir:      \s
                 *  .rii;;;5r,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,sh:;;iri       \s
                 *   ;rii;:;51,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.:hh:;;iir,       \s
                 *    irii;::hSr,.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.,sSs:;;iir:        \s
                 *     irii;;:iSSs:.,,,,,,,,,,,,,,,,,,,,,,,,,,,..:135;:;;iir:         \s
                 *      ;rii;;:,r535r:...,,,,,,,,,,,,,,,,,,..,;sS35i,;;iirr:          \s
                 *       :rrii;;:,;1S3Shs;:,............,:is533Ss:,;;;iiri,           \s
                 *        .;rrii;;;:,;rhS393S55hh11hh5S3393Shr:,:;;;iirr:             \s
                 *          .;rriii;;;::,:;is1h555555h1si;:,::;;;iirri:.              \s
                 *            .:irrrii;;;;;:::,,,,,,,,:::;;;;iiirrr;,                 \s
                 *               .:irrrriiiiii;;;;;;;;iiiiiirrrr;,.                   \s
                 *                  .,:;iirrrrrrrrrrrrrrrrri;:.                       \s
                 *                        ..,:::;;;;:::,,.       \s
                ————————————————
                """.formatted(name);
    }
}
