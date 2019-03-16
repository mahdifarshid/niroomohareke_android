package com.application.mahabad.niroomohareke.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.FilterChildAdapter;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.AttributesModel;
import com.application.mahabad.niroomohareke.Utils.DummyContent;


import java.util.ArrayList;

public class FilterDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
//    private DummyContent.DummyItem mItem;
    private AttributesModel.children children;
    private ArrayList<AttributesModel.children.attribute_values> arrayList = new ArrayList<>();

    public FilterDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            if (DummyContent.Mymap.get(getArguments().getString(ARG_ITEM_ID)) != null)
                arrayList = DummyContent.Mymap.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_filter_child_recycler, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(animation);

        FilterChildAdapter recAdapter = new FilterChildAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(recAdapter);

        return rootView;
    }
}
