package com.ssafy.coffeeing.modules.util.base;

import org.springframework.stereotype.Component;

@Component
public class RandomUtil {

    public int generate(int boundary) {

        return (int) (Math.random() * boundary);
    }
}
