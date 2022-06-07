import java.util.*;

class IntegerConverter {

    public static String intToRoman(int number) {
        if (number >= 100 || number <= 0)
            return null;
        StringBuilder result = new StringBuilder();
        for(Integer key : units.descendingKeySet()) {
            while (number >= key) {
                number -= key;
                result.append(units.get(key));
            }
        }
        return result.toString();
    }

    private static final NavigableMap<Integer, String> units;
    static {
        NavigableMap<Integer, String> initMap = new TreeMap<>();
        for(int i = RomanNumeral.values().length - 1; i >= 0; i--) {
            initMap.put(RomanNumeral.values()[i].getArabicNumber(), RomanNumeral.values()[i].toString());
        }
        units = Collections.unmodifiableNavigableMap(initMap);
    }
}
