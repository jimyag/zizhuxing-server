package cn.jimyag.zizhuxingserver.Utils;

import java.util.Random;

public class RandomCode {
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
