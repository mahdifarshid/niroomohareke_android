package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.R;
import com.bumptech.glide.Glide;
import com.application.mahabad.niroomohareke.Activities.NavigationActivity.ProductActivity;
import com.application.mahabad.niroomohareke.Retrofit.Model.CategoriesModel;

import java.util.ArrayList;

import static com.application.mahabad.niroomohareke.Utils.DummyContent.checkMap;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

    private Context context;
    private ArrayList<CategoriesModel> categoriesModels = new ArrayList<>();

    public CategoryAdapter(Context context, ArrayList<CategoriesModel> categoriesModels) {
        this.context = context;
        this.categoriesModels = categoriesModels;
    }

    private boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private RelativeLayout relativeLayout;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            textView.setSelected(true);
            imageView = itemView.findViewById(R.id.image);
            relativeLayout = itemView.findViewById(R.id.root);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (categoriesModels.get(getAdapterPosition()).getposts_count > 0) {
                        checkMap.clear();
                        Intent intent = new Intent(context, ProductActivity.class);
                        intent.putExtra("category_id", categoriesModels.get(getAdapterPosition()).id);
                        intent.putExtra("category_name", categoriesModels.get(getAdapterPosition()).cat_name);
                        context.startActivity(intent);
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, context.getResources().getString(R.string.noproduct), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView.setText(categoriesModels.get(position).cat_name);
        Glide.with(context).load(categoriesModels.get(position).cat_image).placeholder(R.drawable.ic_post_image_loading).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return categoriesModels.size();
    }


}
