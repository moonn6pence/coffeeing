package com.ssafy.coffeeing.regex;

import com.ssafy.coffeeing.modules.member.dto.NicknameChangeRequest;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexTest {
    @Test
    void testNicknameRegex() {
        String input = "nickname";
        String regex = "^(?!\\s)(?!.*\\s$)(?!.*\\s{2}).*$";
        String regex2 = "^[\\w\\s가-힣ㄱ-ㅎ]*$";

        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(input);

        assertTrue(matcher.find());
    }
}
