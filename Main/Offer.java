import java.util.HashMap;
import java.util.Map;
public class Offer {
    static Map<String, Double> promoCodes = new HashMap<>();
    static{
        promoCodes.put("Summer50", 0.5);
        promoCodes.put("Spring30", 0.3);
        promoCodes.put("Winter20", 0.2);
        promoCodes.put("BackToSchool",0.15);
        promoCodes.put("DrNesma", 1.0);
        promoCodes.put("EngSawsan", 1.0);
        promoCodes.put("EngAbrar", 1.0);
        promoCodes.put("EngYoussra", 1.0);

    }
    public static Double getPromoValue(String code) {
        return promoCodes.getOrDefault(code, null);
    }
    public static Map<String, Double> getPromoCodes() {
        return promoCodes;
    }
}
