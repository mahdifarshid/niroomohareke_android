package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.Activities.DetailActivity;
import com.application.mahabad.niroomohareke.Adapter.ToPersianNumber;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.ProductModel;
import com.bumptech.glide.Glide;
import com.application.mahabad.niroomohareke.Utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ToPersianNumber persianNumber;
    public final static int TYPE_ONE = 1, TYPE_GRID = 2, TYPE_LIST = 3, LOADING = 0;
    public int viewtype = 1;
    private ArrayList<ProductModel.data> industrialModels = new ArrayList<>();
    private boolean value;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private int oneListHeight = 0;

    public ProductsAdapter(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
//        this.industrialModels = industrialModels;
    }


    public List<ProductModel.data> getProducts() {
        return industrialModels;
    }

    public void setproducts(ArrayList<ProductModel.data> movieResults) {
        this.industrialModels = movieResults;
    }


    @Override
    public int getItemViewType(int position) {
//        Log.i("dasdas",industrialModels.size()+"  "+position+"   "+ isLoadingAdded+"");
        return (position == industrialModels.size() - 1 && isLoadingAdded) ? LOADING : viewtype;
    }

    public void refreshAdapter(boolean value, ArrayList<ProductModel.data> tempCardItem) {
        this.value = value;
        this.industrialModels = tempCardItem;
        notifyDataSetChanged();
    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    class Myholder extends RecyclerView.ViewHolder {
        private TextView title, price, codekala;
        private ImageView imageView;
        private CardView cardView;
        private RelativeLayout relativeLayout;

        public Myholder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.root_relative);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            codekala = itemView.findViewById(R.id.code_kala);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardview);


            DisplayMetrics displayMetrics = new DisplayMetrics();

            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int width_px = Resources.getSystem().getDisplayMetrics().widthPixels;

            int height_px = Resources.getSystem().getDisplayMetrics().heightPixels;

            int pixeldpi = Resources.getSystem().getDisplayMetrics().densityDpi;


            if (viewtype == 1) {
//                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();

                cardView.getLayoutParams().height = width_px + (int) convertDpToPixel(100f, context);

                imageView.getLayoutParams().height = width_px;
                relativeLayout.getLayoutParams().height = width_px + (int) convertDpToPixel(100f, context);
                oneListHeight = width_px + (int) convertDpToPixel(100f, context);

            }


            persianNumber = new ToPersianNumber();
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isConnected()) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("product_id", industrialModels.get(getAdapterPosition()).id);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_industrial_one, parent, false);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ONE:
                View view = LayoutInflater.from(context).inflate(R.layout.item_product_one, parent, false);
                viewHolder = new Myholder(view);
                break;
            case TYPE_GRID:
                View view1 = LayoutInflater.from(context).inflate(R.layout.item_product_grid, parent, false);
                viewHolder = new Myholder(view1);
                break;
            case TYPE_LIST:
                View view2 = LayoutInflater.from(context).inflate(R.layout.item_product_list, parent, false);
                viewHolder = new Myholder(view2);
                break;
            case LOADING:
                View viewLoading = LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
            case TYPE_ONE:
            case TYPE_GRID:
            case TYPE_LIST:
                String price = persianNumber.toPersianNumber(industrialModels.get(position).price);
                if (viewtype == 2) {
                    if (industrialModels.get(position).code_kala != null) {
                        if (industrialModels.get(position).code_kala.equals("") && price.equals("")) {
                            ((Myholder) holder).cardView.getLayoutParams().height = (int) convertDpToPixel(270f, context);
                        } else if (industrialModels.get(position).code_kala.equals("")) {
                            ((Myholder) holder).cardView.getLayoutParams().height = (int) convertDpToPixel(300f, context);
                        }
                    }
                    else if ( price.equals("")){
                        ((Myholder) holder).cardView.getLayoutParams().height = (int) convertDpToPixel(270f, context);
                    }
                } else if (viewtype == 1) {
                    if (industrialModels.get(position).code_kala != null) {
                        if (industrialModels.get(position).code_kala.equals("") && price.equals("")) {
                            ((Myholder) holder).cardView.getLayoutParams().height = oneListHeight - 40;
                        } else if (industrialModels.get(position).code_kala.equals("")) {
                            ((Myholder) holder).cardView.getLayoutParams().height = oneListHeight - 20;
                        }
                    }
                    else if ( price.equals("")){
                        ((Myholder) holder).cardView.getLayoutParams().height = oneListHeight - 40;
                    }



                }
                if(industrialModels.get(position).is_code_dastgah==1){
                    String code = context.getString(R.string.codeDastgah)+industrialModels.get(position).title;
                    ((Myholder) holder).title.setText(code);
                }
                else{
                    ((Myholder) holder).title.setText(industrialModels.get(position).title);
                }




                if(industrialModels.get(position).code_kala!=null){
                    if (industrialModels.get(position).code_kala.equals("")) {
                        ((Myholder) holder).codekala.setText("");
                        ((Myholder) holder).codekala.setVisibility(View.GONE);
                    }
                    else{
                        ((Myholder) holder).codekala.setText(persianNumber.toPersianNumberCode(industrialModels.get(position).code_kala));
                    }
                }

                ((Myholder) holder).price.setText(persianNumber.toPersianNumber(industrialModels.get(position).price));
                Glide.with(context).load(industrialModels.get(position).thumb).error(R.drawable.ic_post_image_loading)
                        .placeholder(R.drawable.ic_post_image_loading).into(((Myholder) holder).imageView);
                break;
        }
    }

    public void removeall() {
        industrialModels.clear();
        ;
    }

    public void add(ProductModel.data r) {
        industrialModels.add(r);
        Log.i("dasdasdas", industrialModels.size() + "");
        notifyItemInserted(industrialModels.size() - 1);
    }

    public void addAll(ArrayList<ProductModel.data> moveResults) {
        for (ProductModel.data result : moveResults) {
            add(result);
        }
    }

    public void remove(ProductModel.data r) {
        int position = industrialModels.indexOf(r);
        if (position > -1) {
            industrialModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ProductModel.data());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = industrialModels.size() - 1;
        ProductModel.data result = getItem(position);

        if (result != null) {
            industrialModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ProductModel.data getItem(int position) {
        return industrialModels.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(industrialModels.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable drawableProgress = DrawableCompat.wrap(mProgressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(context, R.color.colorPrimary));
                mProgressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

            } else {
                mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:


                    if (isConnected()) {
                        showRetry(false, null);
                        mCallback.retryPageLoad();
                    }

                    break;
            }
        }
    }

    private boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }


    @Override
    public int getItemCount() {
        return industrialModels.size();
    }


}
