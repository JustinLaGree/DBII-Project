package com.example.db2.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//helper classes to modify or access lists using the stream/lambda functions
public class ListHelpers {

    //get the single value from the list where the lambda condition applies
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> T single(List<T> list, Predicate<? super T> lambda)
    {
        return list.stream().filter(lambda)
                .findFirst().get();
    }

    //get the sublist from the list where the lambda condition applies
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> List<T> where(List<T> list, Predicate<? super T> lambda)
    {
        return list.stream().filter(lambda)
                .collect(Collectors.toList());
    }

    //check to see if there are any elements in the list where the lambda condition applies
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> boolean any(List<T> list, Predicate<? super T> lambda)
    {
        return list.stream().anyMatch(lambda);
    }
}
