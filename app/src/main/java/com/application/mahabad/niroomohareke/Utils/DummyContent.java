package com.application.mahabad.niroomohareke.Utils;

import com.application.mahabad.niroomohareke.Retrofit.Model.AttributesModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.FilterModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

//    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
//public static final ArrayList<AttributesModel> ATTRIBUTES_MODELS = new ArrayList<>();
    //    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
    public static Map<String, ArrayList<AttributesModel.children.attribute_values>> Mymap = new HashMap<>();
    public static Map<Integer,FilterModel>checkMap=new HashMap<>();
    public static Map<Integer,Integer>CountFilterMap=new HashMap<>();
    private static final int COUNT = 5;

}

