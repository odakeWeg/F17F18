package com.edson.util;

//@TODO: Unificar com o SapTestDataUtil.java
public class SapTestDataInstanceUtil {
    public static SapTestDataUtil dados = new SapTestDataUtil();

    public static void resetar() {
        dados = new SapTestDataUtil();
    }
}
