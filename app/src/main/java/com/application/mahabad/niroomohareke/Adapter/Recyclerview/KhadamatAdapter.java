package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.application.mahabad.niroomohareke.Activities.KhadamtDetailActivity;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.View.ExpandableTextView;
import com.application.mahabad.niroomohareke.Retrofit.Model.KhadamatModel;

import java.util.ArrayList;



public class KhadamatAdapter extends RecyclerView.Adapter<KhadamatAdapter.MyHolder> {

    private Context context;
    private ArrayList<KhadamatModel> list = new ArrayList<>();

    public KhadamatAdapter(Context context, ArrayList<KhadamatModel> list) {
        this.context = context;
        this.list = list;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView title, readmore;
        private ExpandableTextView description;
        private CardView cardView;
        private RelativeLayout root;
        public MyHolder(View itemView) {
            super(itemView);
            root=itemView.findViewById(R.id.root);
            title = itemView.findViewById(R.id.title);

//            description = itemView.findViewById(R.id.description);
//            cardView=itemView.findViewById(R.id.cardview);

            WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            final DisplayMetrics dimension = new DisplayMetrics();
            windowmanager.getDefaultDisplay().getMetrics(dimension);

            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/vazir.ttf");
          /*  description.setTypeface(tf);
            description.setAnimationDuration(750L);
            description.setInterpolator(new OvershootInterpolator());
            description.setExpandInterpolator(new OvershootInterpolator());
            description.setCollapseInterpolator(new OvershootInterpolator());


            description.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v)
                {
                    description.toggle();
                }
            });*/


            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, KhadamtDetailActivity.class);
                    intent.putExtra("khadamat_id",list.get(getAdapterPosition()).id);
                    context.startActivity(intent);
                }
            });
        }
    }


    @NonNull
    @Override
    public KhadamatAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khadamat, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.title.setText(list.get(position).title);
        if((position+1)%2==0){
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.doc_item1));
        }
        else{
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.doc_item2));
        }
//        holder.description.setText(list.get(position).description);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

