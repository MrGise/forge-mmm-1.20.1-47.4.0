package net.MrGise.mmm.util;

import java.util.function.Supplier;

public class Methods {



    public static <T> Supplier<T> s(T s) {
        return () -> s;
    }
}
