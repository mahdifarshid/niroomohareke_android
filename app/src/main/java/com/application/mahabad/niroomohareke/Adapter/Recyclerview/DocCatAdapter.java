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

import com.application.mahabad.niroomohareke.Activities.NavigationActivity.ActivityDocuments;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocCatModel;

import java.util.ArrayList;

public class DocCatAdapter  extends RecyclerView.Adapter<DocCatAdapter.MyHolder> {

    private Context context;
    private ArrayList<DocCatModel> docCatModels = new ArrayList<>();

    public DocCatAdapter(Context context, ArrayList<DocCatModel> categoriesModels) {
        this.context = context;
        this.docCatModels = categoriesModels;
    }


    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private RelativeLayout relativeLayout;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            textView.setSelected(true);
            relativeLayout = itemView.findViewById(R.id.root);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (docCatModels.get(getAdapterPosition()).documents_count > 0) {
                        Intent intent = new Intent(context, ActivityDocuments.class);
                        intent.putExtra("cat_id", docCatModels.get(getAdapterPosition()).id);
                        context.startActivity(intent);
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, context.getResources().getString(R.string.nodocument), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public DocCatAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doc_cat, parent, false);
        return new DocCatAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocCatAdapter.MyHolder holder, int position) {
        if((position+1)%2==0){
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.doc_item1));
        }
        else{
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.doc_item2));
        }
        holder.textView.setText(docCatModels.get(position).title);
    }

    @Override
    public int getItemCount() {
        return docCatModels.size();
    }


}
