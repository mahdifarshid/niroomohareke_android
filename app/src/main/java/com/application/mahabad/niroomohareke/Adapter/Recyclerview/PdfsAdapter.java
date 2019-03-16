package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.application.mahabad.niroomohareke.ConnectivityReceiver;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocCatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.PdfsModel;
import com.application.mahabad.niroomohareke.Utils.PdfInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfsAdapter extends RecyclerView.Adapter<PdfsAdapter.MyHolder> {

    private Context context;
    private ArrayList<PdfsModel> pdfsModels = new ArrayList<>();

    PdfInterface pdfInterface;
    File root;
    private String adobe;
    public PdfsAdapter(Context context, ArrayList<PdfsModel> categoriesModels,String adobe) {
        this.context = context;
        this.pdfsModels = categoriesModels;
        this.pdfInterface = (PdfInterface) context;
        this.adobe=adobe;
    }
    public static boolean canDisplayPdf(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        if (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CardView cardView;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            title.setSelected(true);
            cardView = itemView.findViewById(R.id.cardview);
            root = new File(Environment.getExternalStorageDirectory() + "/"+ConnectivityReceiver.NIROO_DIRECTORY);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(canDisplayPdf(context)){
                        File file = new File(root.getAbsolutePath()+"/"+pdfsModels.get(getAdapterPosition()).filename);
                        Intent target = new Intent(Intent.ACTION_VIEW);
                        Uri contentUri;
                        if(Build.VERSION.SDK_INT >= 24){
                            contentUri = FileProvider.getUriForFile(
                                    context,
                                    context.getApplicationContext()
                                            .getPackageName() + ".provider", file);
                        } else{
                            contentUri = Uri.fromFile(file);
                        }
                        target.setDataAndType(contentUri,"application/pdf");

                        target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
                        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        Intent intent = Intent.createChooser(target, "Open File");
                        try {
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
//                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        if(!adobe.equals("")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle(R.string.adobereader);
                            builder.setIcon(R.mipmap.ic_pdf);
                            builder.setMessage(R.string.needadobereader);
                            builder.setPositiveButton(R.string.wantdownload, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adobe));
                                    context.startActivity(intent);
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
                    }
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    AlertDialog alertDialog = new AlertDialog.Builder(context)
//                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.deletefile)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String filename = pdfsModels.get(getAdapterPosition()).filename;
                                    if (!root.exists()) {
                                        root.mkdir();
                                    }
                                    File file1 = new File(root.getPath() + "/" + filename);
                                    file1.delete();
                                    pdfInterface.refresh();

                                }
                            })
                            //set negative button
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .show();

                    return false;
                }
            });
        }
    }


    @NonNull
    @Override
    public PdfsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pdfs, parent, false);
        return new PdfsAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfsAdapter.MyHolder holder, int position) {
        holder.title.setText(pdfsModels.get(position).title);
    }

    @Override
    public int getItemCount() {
        return pdfsModels.size();
    }


}
