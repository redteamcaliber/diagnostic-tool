package com.openxc.openxcdiagnostic.diagnostic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.openxc.messages.DiagnosticRequest;
import com.openxc.messages.DiagnosticResponse;
import com.openxc.openxcdiagnostic.R;
import com.openxc.openxcdiagnostic.resources.Utilities;

public class DiagnosticOutputRow {

    private LinearLayout mView;
    private DiagnosticOutputTable mTable;

    public DiagnosticOutputRow(Activity context, DiagnosticOutputTable table,
            DiagnosticRequest req, DiagnosticResponse resp) {

        mTable = table;
        mView = (LinearLayout) context.getLayoutInflater().inflate(R.layout.singleoutputrow, null);
        initButtons(context, table, req, resp);
        TextView outputText = (TextView) mView.findViewById(R.id.outputText);
        outputText.setText(Utilities.getOutputString(resp));
    }

    private void initButtons(final Activity context,
            DiagnosticOutputTable table, final DiagnosticRequest req,
            final DiagnosticResponse resp) {

        Button moreButton = (Button) mView.findViewById(R.id.outputMoreButton);
        moreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LinearLayout alertLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.detailsalertlayout, null);
                builder.setView(alertLayout);
                ((TextView) alertLayout.findViewById(R.id.diagAlertRequestOutput)).setText(Utilities.getOutputString(req));
                ((TextView) alertLayout.findViewById(R.id.diagAlertResponseOutput)).setText(Utilities.getOutputString(resp));
                builder.setTitle(context.getResources().getString(R.string.details_button_label));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.create().show();
            }
        });

        final Button deleteButton = (Button) mView.findViewById(R.id.outputDeleteButton);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTable.removeRow(DiagnosticOutputRow.this);
            }
        });

    }

    public LinearLayout getView() {
        return mView;
    }

}