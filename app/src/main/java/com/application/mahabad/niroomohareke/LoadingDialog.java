package com.application.mahabad.niroomohareke;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class LoadingDialog {

    private Context context;
    private Dialog loaddialog;
    private Dialog conDialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void showloaddialog() {
        loaddialog = new Dialog(context, android.R.style.Theme_Light);
        loaddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        loaddialog.setContentView(R.layout.dialog_content_loading);
        loaddialog.show();
    }

    public void dismissLoad() {
        if(loaddialog!=null)
        if(loaddialog.isShowing())
        loaddialog.dismiss();
    }

    public void showNoCondialog(final Class activity) {
        conDialog = new Dialog(context, android.R.style.Theme_Light);
        conDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        conDialog.setContentView(R.layout.dialog_noconnection);
        conDialog.show();
        conDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                ((Activity)context). finish();
            }
        });
        Button button = conDialog.findViewById(R.id.btn_tryagain);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectivityReceiver.isOnline()) {
                    conDialog.dismiss();
                    context.startActivity(new Intent(context, activity));
                    ((Activity)context).finish();
                }
                else{
                    Toast.makeText(context, "شما به اینترنت متصل نیستید", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void dismissNoCon(Class activity) {
        conDialog.dismiss();
        context.startActivity(new Intent(context, activity));
        conDialog.dismiss();
    }

}

