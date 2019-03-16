package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.ProductDetailModel;

import java.util.ArrayList;

public class ProductDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ProductDetailModel.attributes> attr = new ArrayList<>();
    private final static int TYPE_HEADER = 1, TYPE_CONTENT = 2;

    class Myattr {
        public String title;
        public String value;
        public boolean is_parent;
    }

    private ArrayList<Myattr> myattrs = new ArrayList<>();

    private int children_size = 0;

    public ProductDetailAdapter(Context context, ArrayList<ProductDetailModel.attributes> items) {
        this.context = context;
//        this.attr = items;
        children_size = 0;
        for (int i = 0; i < items.size(); i++) {
            Myattr myattr = new Myattr();
            myattr.title = items.get(i).parent_title;
            myattr.value = "";
            myattr.is_parent = true;
            myattrs.add(myattr);
            for (int j = 0; j < items.get(i).attribute_list.size(); j++) {
                Myattr myattr2 = new Myattr();
                myattr2.title = items.get(i).attribute_list.get(j).title;
                myattr2.value = items.get(i).attribute_list.get(j).Attr_val;
                myattr2.is_parent = false;
                myattrs.add(myattr2);
                children_size++;
            }
        }

//        Log.i("fasav",new Gson().toJson(myattrs));
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        private TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.tv_header);
        }

    }

    class ContentHolder extends RecyclerView.ViewHolder {
        private TextView attr_title;
        private TextView attr_value;

        public ContentHolder(View itemView) {
            super(itemView);
            attr_title = itemView.findViewById(R.id.tv_attrtitle);
            attr_value = itemView.findViewById(R.id.tv_attrvalue);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (myattrs.get(position).is_parent)
            return TYPE_HEADER;
        else
            return TYPE_CONTENT;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_HEADER:
                View view = LayoutInflater.from(context).inflate(R.layout.item_product_detail_header, parent, false);
                viewHolder = new HeaderHolder(view);
                Log.i("mylog", "4");
                break;
            case TYPE_CONTENT:
                View view1 = LayoutInflater.from(context).inflate(R.layout.item_product_detail, parent, false);
                viewHolder = new ContentHolder(view1);
                Log.i("mylog", "5");
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 1:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.header.setText(myattrs.get(position).title);
                break;
            case 2:
                ContentHolder contentHolder = (ContentHolder) holder;
                contentHolder.attr_title.setText(myattrs.get(position).title);
                contentHolder.attr_value.setText(myattrs.get(position).value);
                break;
        }

    }

  /*  @Override
    public void onBindViewHolder(@NonNull ProductDetailAdapter.MyHolder holder, int position) {
//        holder.attr_title.setText(attr.get(1).attribute_list.get(position).title);
//        holder.attr_value.setText(attr.get(1).attribute_list.get(position).Attr_val);

        holder.attr_title.setText(attr.get(position).parent_title);

//        holder.attr_value.setText(attr.get(position).attribute_list.get(position).Attr_val);
    }*/

    @Override
    public int getItemCount() {
//        return attr.get(1).attribute_list.size();
        return attr.size() + children_size+1;
    }


}
