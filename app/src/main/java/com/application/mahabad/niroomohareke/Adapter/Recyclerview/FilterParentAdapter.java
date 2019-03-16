package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.mahabad.niroomohareke.Activities.ActivityFilter;
import com.application.mahabad.niroomohareke.Fragments.FilterDetailFragment;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.AttributesModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.application.mahabad.niroomohareke.Utils.DummyContent.CountFilterMap;
import static com.application.mahabad.niroomohareke.Utils.DummyContent.checkMap;

public class FilterParentAdapter extends RecyclerView.Adapter<FilterParentAdapter.ViewHolder> {

    private final ActivityFilter mParentActivity;
    //    private final List<DummyContent.DummyItem> mValues;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            AttributesModel.children attributesModel = (AttributesModel.children) view.getTag();
            Bundle arguments = new Bundle();
            arguments.putString(FilterDetailFragment.ARG_ITEM_ID, attributesModel.title);
            FilterDetailFragment fragment = new FilterDetailFragment();
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        }
    };
    private int children_size = 0;
    private ArrayList<AttributesModel> attributesModels = new ArrayList<>();
    private ArrayList<AttributesModel.children> Allattributes = new ArrayList<>();

    public FilterParentAdapter(ActivityFilter parent, ArrayList<AttributesModel> items) {
        attributesModels = items;
        mParentActivity = parent;

        for (int i = 0; i < items.size(); i++) {
            children_size += items.get(i).children.size();
            AttributesModel.children children = new AttributesModel.children();
            children.id = items.get(i).id;
            children.title = items.get(i).title;
            children.color = parent.getResources().getColor(R.color.grayitem);
            Allattributes.add(children);
            Allattributes.addAll(items.get(i).children);
        }

        if (Allattributes.size() > 1) {
            AttributesModel.children attributesModel = (AttributesModel.children) Allattributes.get(1);
            Bundle arguments = new Bundle();
            arguments.putString(FilterDetailFragment.ARG_ITEM_ID, attributesModel.title);
            FilterDetailFragment fragment = new FilterDetailFragment();
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }
//        mValues = items;
        int count = 0;
        for (int i = 0; i < Allattributes.size(); i++) {
            for (int j = 0; j < Allattributes.get(i).attribute_values.size(); j++) {
                if (checkMap.get(Allattributes.get(i).attribute_values.get(j).id) != null) {
                    if (checkMap.get(Allattributes.get(i).attribute_values.get(j).id).flag)
                        count++;
                }
            }
            CountFilterMap.put(Allattributes.get(i).id,count);
            count=0;
        }

        Log.i("custom",new Gson().toJson(CountFilterMap));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_parent, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

     /*   if(Allattributes.get(position).lable){
            holder.mIdView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }*/
        holder.relativeLayout.setBackgroundColor(Allattributes.get(position).color);

        holder.mIdView.setText(Allattributes.get(position).title);
//      holder.mContentView.setText(mValues.get(position).content);
        holder.relativeLayout.setTag(Allattributes.get(position));
        holder.relativeLayout.setOnClickListener(mOnClickListener);
        if(CountFilterMap.get(Allattributes.get(position).id)!=null&&CountFilterMap.get(Allattributes.get(position).id)!=0)
        holder.filter_count.setText(String.valueOf(CountFilterMap.get(Allattributes.get(position).id)));
        else{
            holder.filter_count.setText("");
        }

      /*  for (int i = 0; i < Allattributes.get(position).attribute_values.size(); i++) {

            if (checkMap.get(Allattributes.get(position).attribute_values.get(i).Attr_val) != null) {
                if (checkMap.get(Allattributes.get(position).attribute_values.get(i).Attr_val)) {

                }
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return attributesModels.size() + children_size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView, filter_count;
        final RelativeLayout relativeLayout;

        //            final TextView mContentView;
        ViewHolder(View view) {
            super(view);
            relativeLayout = view.findViewById(R.id.linear_attr);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            filter_count = (TextView) view.findViewById(R.id.id_count);
//                mContentView = (TextView) view.findViewById(R.id.content);

          /*  relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/

        }
    }
}