package com.example.db2.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListHelpers {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> T single(List<T> list, Predicate<? super T> lambda)
    {
        return list.stream().filter(lambda)
                .findFirst().get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> List<T> where(List<T> list, Predicate<? super T> lambda)
    {
        return list.stream().filter(lambda)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> boolean any(List<T> list, Predicate<? super T> lambda)
    {
        return list.stream().anyMatch(lambda);
    }
}
