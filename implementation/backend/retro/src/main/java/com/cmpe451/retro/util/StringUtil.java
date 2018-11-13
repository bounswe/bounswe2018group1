package com.cmpe451.retro.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public boolean isValidPassword(String password){
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
        Matcher m = p.matcher(password);
        if (m.find())
            return true;
        return false;
    }
}
