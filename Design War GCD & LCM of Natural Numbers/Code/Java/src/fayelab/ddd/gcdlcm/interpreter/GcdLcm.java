package fayelab.ddd.gcdlcm.interpreter;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class GcdLcm
{
    private static final String SOLUTION_TYPE_GCD = "gcd";
    private static final String SOLUTION_TYPE_LCM = "lcm";
    
    private static final String EXTRACTION_TYPE_PF = "prime_factor";
    private static final String EXTRACTION_TYPE_POWER = "power";
    
    private static final String PF_EXTRACTION_TYPE_COMMON = "common";
    private static final String PF_EXTRACTION_TYPE_ALLNP = "all_np";
    
    private static final String POWER_EXTRACTION_TYPE_MIN = "min";
    private static final String POWER_EXTRACTION_TYPE_MAX = "max";
    
    @SuppressWarnings("serial")
    private static final Map<String, Map<String, String>> spec = 
            new HashMap<String, Map<String, String>>(){
        {
            put(SOLUTION_TYPE_GCD, 
                new HashMap<String, String>(){
                    {
                        put(EXTRACTION_TYPE_PF, PF_EXTRACTION_TYPE_COMMON);
                        put(EXTRACTION_TYPE_POWER, POWER_EXTRACTION_TYPE_MIN);
                    }       
                }
            );
            
            put(SOLUTION_TYPE_LCM, 
                new HashMap<String, String>(){
                    {
                        put(EXTRACTION_TYPE_PF, PF_EXTRACTION_TYPE_ALLNP);
                        put(EXTRACTION_TYPE_POWER, POWER_EXTRACTION_TYPE_MAX);
                    }       
                }
            );
        }
    };
    
    private static int unifiedProc(String solutionType, List<Integer> Numbers)
    {
        String[] extractionSpecItems = extractSpecItems(solutionType);
        
        return 0;
    }

    private static String[] extractSpecItems(String solutionType)
    {
        Map<String, String> specItems = spec.get(solutionType);
        String pfExtractionType = specItems.get(EXTRACTION_TYPE_PF);
        String powerExtractionType = specItems.get(EXTRACTION_TYPE_POWER);
        
        return new String[]{pfExtractionType, powerExtractionType};
    }
}
