package com.liangshou.llmsrefactor.pmd;

import com.liangshou.llmsrefactor.pmd.entity.PmdViolations;
import jakarta.annotation.Resource;
import net.sourceforge.pmd.PMDConfiguration;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;

import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.CODE_ROOT;
import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.JAVA_CODE_PATH;

/**
 * @author X-L-S
 */
@RestController
public class PmdAnalysisController {
    @Resource
    private PmdAnalysisService pmdAnalysisService;

    @GetMapping("/analysis")
    public String doAnalysis(){
        PMDConfiguration config = pmdAnalysisService.createPmdConfig("java");
        String fileName = "BREADTH_FIRST_SEARCH.java";
        return pmdAnalysisService.analyzeCode(config, JAVA_CODE_PATH, fileName).getResultJson();
    }

    @GetMapping("/analysis/path")
    public void analyzePath (@RequestParam(defaultValue = "java") String languageType,
                             @RequestParam(defaultValue = "origin") String codeType){
        boolean isOrigin;
        switch (codeType) {
            case "origin":
                isOrigin = true;
                break;
            case "new":
                isOrigin = false;
                break;
            default:
                throw new RuntimeException("Code Type Error: in origin or new.");
        }
        PMDConfiguration config = pmdAnalysisService.createPmdConfig("java");
        pmdAnalysisService.analyzePath(config, languageType, isOrigin);
    }

    @GetMapping("/analysis/tes")
    public String tesDb(@RequestParam(required = true, defaultValue = "修狗") String name)
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
