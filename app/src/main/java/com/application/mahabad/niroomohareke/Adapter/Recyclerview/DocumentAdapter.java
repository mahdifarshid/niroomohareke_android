package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.Activities.NavigationActivity.ActivityDocuments;
import com.application.mahabad.niroomohareke.Activities.NavigationActivity.DownloadTask;
import com.application.mahabad.niroomohareke.ConnectivityReceiver;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocCatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocumentModel;
import com.application.mahabad.niroomohareke.Utils.PdfInterface;
import com.application.mahabad.niroomohareke.Utils.PermissionHandel;
import com.application.mahabad.niroomohareke.View.ExpandableTextView;

import java.io.File;
import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.MyHolder> {

    private Context context;
    private ArrayList<DocumentModel> documentModels = new ArrayList<>();
    private  ProgressDialog mProgressDialog;
    private  String filename;
    public DocumentAdapter(Context context, ArrayList<DocumentModel> categoriesModels) {
        this.context = context;

        this.documentModels = categoriesModels;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ExpandableTextView description;
        private RelativeLayout relativeLayout;
        private LinearLayout linear_download;

        public MyHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            linear_download = itemView.findViewById(R.id.linear_download);
            description.setAnimationDuration(750L);
            description.setInterpolator(new OvershootInterpolator());
            description.setExpandInterpolator(new OvershootInterpolator());
            description.setCollapseInterpolator(new OvershootInterpolator());

            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    description.toggle();
                }
            });

            relativeLayout = itemView.findViewById(R.id.root);

            linear_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PermissionHandel permissionHandel=new PermissionHandel(context);
                    permissionHandel.checkPermision();

                    if(permissionHandel.is_havepermistion()) {

                        if(ConnectivityReceiver.isOnline()){
                            mProgressDialog = new ProgressDialog(context);
                            mProgressDialog.setMessage(context.getString(R.string.downloadprogress));
                            mProgressDialog.setIndeterminate(true);
                            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.setCanceledOnTouchOutside(false);
                            DocumentModel documentModel = documentModels.get(getAdapterPosition());
                             filename = documentModel.title + "." + documentModel.extention;
                            if(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ConnectivityReceiver.NIROO_DIRECTORY+"/"+filename).exists()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle( documentModel.title);
                                builder.setIcon(R.mipmap.ic_pdf);
                                builder.setMessage(R.string.redownload);
                                builder.setPositiveButton(R.string.wantdownload, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        final DownloadTask downloadTask = new DownloadTask(context, mProgressDialog, filename);
                                        downloadTask.execute(documentModels.get(getAdapterPosition()).doc);

                                        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                            @Override
                                            public void onCancel(DialogInterface dialog) {
                                                downloadTask.cancel(true);
                                            }
                                        });
                                    }
                                });
                                builder.setNegativeButton(R.string.notdownload, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });
                                builder.show();
                            }
                            else{
                                final DownloadTask downloadTask = new DownloadTask(context, mProgressDialog, filename);
                                downloadTask.execute(documentModels.get(getAdapterPosition()).doc);

                                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        downloadTask.cancel(true);
                                    }
                                });
                            }
                        }
                        else{
                            Toast.makeText(context, context.getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                        }



                    }
                    else{
                        Toast.makeText(context, "شما دسترسی به حافظه داخلی را نداده اید", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }


    @NonNull
    @Override
    public DocumentAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_documents, parent, false);
        return new DocumentAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.MyHolder holder, int position) {

        holder.title.setText(documentModels.get(position).title);
        holder.description.setText(documentModels.get(position).description);
        if (documentModels.get(position).doc.equals("")) {
            holder.linear_download.setVisibility(View.GONE);
        }
        else{
            holder.linear_download.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return documentModels.size();
    }


}
