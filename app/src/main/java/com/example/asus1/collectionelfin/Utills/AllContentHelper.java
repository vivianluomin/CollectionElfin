package com.example.asus1.collectionelfin.Utills;

import com.example.asus1.collectionelfin.models.CollectionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus1 on 2017/10/10.
 */

public class AllContentHelper {

    private static List<String> Collecton_Sort = new ArrayList<>();
    private static  List<String> Note_Sort = new ArrayList<>();


    public static List<String> getNote_Sort() {
        return Note_Sort;
    }

    public static void setNote_Sort(List<String> note_Sort) {
        Note_Sort.clear();
        Note_Sort.addAll(note_Sort);
    }

    public static  void setCollecton_Sort(List<String> Collections){
        Collecton_Sort.clear();
        Collecton_Sort.addAll(Collections);
    }

    public static List<String> getCollecton_Sort(){
        return Collecton_Sort;
    }

    public static void addCollection_Sort(String collection){
        Collecton_Sort.add(collection);
    }




}
