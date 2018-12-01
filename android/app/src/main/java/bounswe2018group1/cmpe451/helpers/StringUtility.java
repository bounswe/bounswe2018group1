package bounswe2018group1.cmpe451.helpers;

import com.google.gson.JsonArray;
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
        if (endDateYYYY == 0 && endDateMM == 0 && endDateDD == 0 && endDateHH == 0) {
            //Single point in time
            result.append("On ");
            if(startDateYYYY < 0) {
                startDateYYYY = -startDateYYYY;
                result.append("B.C. ");
            } else if(startDateYYYY < 1000) {
                result.append("A.D. ");
            }
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
            if(startDateYYYY < 0) {
                startDateYYYY = -startDateYYYY;
                result.append("B.C. ");
            } else if(startDateYYYY < 1000) {
                result.append("A.D. ");
            }
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
            if(endDateYYYY < 0) {
                endDateYYYY = -endDateYYYY;
                result.append("BC ");
            } else if(endDateYYYY < 1000) {
                result.append("AD ");
            }
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

    public static String memoryLocation(JsonArray listOfLocations) {
        StringBuilder result = new StringBuilder();
        result.append("List Of Locations:" + System.getProperty("line.separator"));
        for(int i = 0, size = listOfLocations.size(); i < size; ++i) {
            result.append(String.valueOf(i+1) + ") " +
                    listOfLocations.get(i).getAsJsonObject().get("locationName").getAsString() +
                    System.getProperty("line.separator"));
        }

        return result.toString();
    }

    public static String getFileExtensionFromUrl(String url) {
        if (url != null && url.length() > 0) {
            int query = url.lastIndexOf('?');
            if (query > 0) {
                url = url.substring(0, query);
            }
            int filenamePos = url.lastIndexOf('/');
            String filename =
                    0 <= filenamePos ? url.substring(filenamePos + 1) : url;

            if (filename.length() > 0) {
                int dotPos = filename.lastIndexOf('.');
                if (0 <= dotPos) {
                    return filename.substring(dotPos + 1);
                }
            }
        }

        return "";
    }

}
