package com.cmpe451.retro.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {

    StringUtil stringUtil = new StringUtil();

    @Test
    public void it_should_validate_passwords() {
        //given
        String validPassword = "As1asd1sdas21";
        String invalidPassword = "asd123";

        //when and then
        assertTrue(stringUtil.isValidPassword(validPassword));
        assertFalse(stringUtil.isValidPassword(invalidPassword));
    }
}