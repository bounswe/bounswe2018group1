package bounswe2018group1.cmpe451.helpers;

import com.google.gson.JsonObject;

public class StringUtility {

    public static String join(String delimiter, String[] argStrings) {
        if (argStrings == null) {
            return "";
        }
        String ret = "";
        int i;
        for (i = 0; i < argStrings.length && (argStrings[i] == null || argStrings[i].isEmpty()); ++i)
            ;
        if (i < argStrings.length) {
            ret = argStrings[i];
        } // if
        for (i = i + 1; i < argStrings.length; i++) {
            ret += (argStrings[i] == null)
                    ? ""
                    : (argStrings[i].isEmpty()
                    ? ""
                    : (delimiter + argStrings[i]));
        } // for
        return ret;
    }

    public static String memoryDate(JsonObject memory) {
        int startDateYYYY = memory.get("startDateYYYY").getAsInt(),
                startDateMM = memory.get("startDateMM").getAsInt(),
                startDateHH = memory.get("startDateHH").getAsInt(),
                startDateDD = memory.get("startDateDD").getAsInt(),
                endDateYYYY = memory.get("endDateYYYY").getAsInt(),
                endDateMM = memory.get("endDateMM").getAsInt(),
                endDateHH = memory.get("endDateHH").getAsInt(),
                endDateDD = memory.get("endDateDD").getAsInt();
        StringBuilder result = new StringBuilder();
        result.append("Happened ");
        if (endDateYYYY == 0) {
            //Single point in time
            result.append("On ");
            result.append(startDateYYYY);
            if (startDateMM > 0) {   // Resolution >= MONTH
                result.append("-" + startDateMM);
                if (startDateDD > 0) {   // Resolution >= DAY
                    result.append("-" + startDateDD);
                    if (startDateHH > 0) {   // Resolution >= HOUR
                        result.append(" " + startDateHH);
                    }
                }
            }
        } else {
            //Interval of time
            result.append("From ");
            result.append(startDateYYYY);
            if (startDateMM > 0) {   // Resolution >= MONTH
                result.append("-" + startDateMM);
                if (startDateDD > 0) {   // Resolution >= DAY
                    result.append("-" + startDateDD);
                    if (startDateHH > 0) {   // Resolution >= HOUR
                        result.append(" " + startDateHH);
                    }
                }
            }
            result.append(" To ");
            result.append(endDateYYYY);
            if (endDateMM > 0) {   // Resolution >= MONTH
                result.append("-" + endDateMM);
                if (endDateDD > 0) {   // Resolution >= DAY
                    result.append("-" + endDateDD);
                    if (endDateHH > 0) {   // Resolution >= HOUR
                        result.append(" " + endDateHH);
                    }
                }
            }
        }

        return result.toString();
    }

}