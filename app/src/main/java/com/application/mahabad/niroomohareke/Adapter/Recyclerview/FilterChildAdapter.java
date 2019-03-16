package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.AttributesModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.FilterModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.application.mahabad.niroomohareke.Utils.DummyContent.checkMap;


public class FilterChildAdapter extends RecyclerView.Adapter<FilterChildAdapter.MyHolder> {

    private Context context;
    private ArrayList<AttributesModel.children.attribute_values> list = new ArrayList<>();

    public FilterChildAdapter(Context context, ArrayList<AttributesModel.children.attribute_values> list) {
        this.context = context;
        this.list = list;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView title,cunt;
        private CheckBox checkBox;
        private RelativeLayout relative_attr;
        public MyHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.id_text);
            cunt=itemView.findViewById(R.id.id_count);
            checkBox=itemView.findViewById(R.id.check_filter);
            relative_attr=itemView.findViewById(R.id.relative_attr);

            relative_attr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkMap.get(list.get(getAdapterPosition()).id).flag) {
                        checkBox.setChecked(false);
                        FilterModel filter=new FilterModel();
                        filter.flag=false;
                        filter.attr_id=list.get(getAdapterPosition()).Attr_id;
                        filter.attr_value=list.get(getAdapterPosition()).Attr_val;
                        checkMap.put(list.get(getAdapterPosition()).id, filter);
                    } else {
                        checkBox.setChecked(true);
                        FilterModel filter=new FilterModel();
                        filter.flag=true;
                        filter.attr_id=list.get(getAdapterPosition()).Attr_id;
                        filter.attr_value=list.get(getAdapterPosition()).Attr_val;
                        checkMap.put(list.get(getAdapterPosition()).id, filter);
                    }
                    Log.i("jksdnvjknv",new Gson().toJson(checkMap));
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkMap.get(list.get(getAdapterPosition()).id).flag) {
                        checkBox.setChecked(false);
                        FilterModel filter=new FilterModel();
                        filter.flag=false;
                        filter.attr_id=list.get(getAdapterPosition()).Attr_id;
                        filter.attr_value=list.get(getAdapterPosition()).Attr_val;
                        checkMap.put(list.get(getAdapterPosition()).id, filter);
                    } else {
                        checkBox.setChecked(true);
                        FilterModel filter=new FilterModel();
                        filter.flag=true;
                        filter.attr_id=list.get(getAdapterPosition()).Attr_id;
                        filter.attr_value=list.get(getAdapterPosition()).Attr_val;
                        checkMap.put(list.get(getAdapterPosition()).id, filter);
                    }

                }
            });

        }
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter_child, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.title.setText(list.get(position).Attr_val);
        holder.cunt.setText(String.valueOf(list.get(position).total));
//        Log.i("checkbox",checkMap.get(list.get(position).id)+" ");
//        Log.i("checked",new Gson().toJson(checkMap)+"==");
//        holder.checkBox.setChecked(true);
        if(checkMap.get(list.get(position).id).flag){
            holder.checkBox.setChecked(checkMap.get(list.get(position).id).flag);
        }
        else{
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

