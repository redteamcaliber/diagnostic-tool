package com.openxc.openxcdiagnostic.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import android.app.Activity;
import android.graphics.Rect;

import com.openxc.messages.DiagnosticRequest;
import com.openxc.messages.DiagnosticResponse;
import com.openxc.messages.DiagnosticResponse.NegativeResponseCode;
import com.openxc.openxcdiagnostic.R;

public class Utilities {

    private static Random rnd = new Random();
    
    private Utilities() {
    };
    
    //TODO this contains NegativeResponseCode.None, but ok because it's just for testing anyway
    private static final List<DiagnosticResponse.NegativeResponseCode> negativeResponseCodes = 
            Collections.unmodifiableList(Arrays.asList(DiagnosticResponse.NegativeResponseCode.values()));
    
    
    //all these outputs are italicized so the space prevents them from being cut off
    //at the end (mostly)
    public static String getBusOutput(DiagnosticRequest req) {
        return String.valueOf(req.getBusId()) + " ";
    }
    
    public static String getBusOutput(DiagnosticResponse resp) {
        return String.valueOf(resp.getBusId()) + " ";
    }
    
    public static String getIdOutput(DiagnosticRequest req) {
        return String.valueOf(req.getId()) + " ";
    }
    
    public static String getIdOutput(DiagnosticResponse resp) {
        return String.valueOf(resp.getId()) + " ";
    }
    
    public static String getModeOutput(DiagnosticRequest req) {
        return "0x" + Integer.toHexString(req.getMode()).toUpperCase(Locale.US) + " ";
    }
    
    public static String getModeOutput(DiagnosticResponse resp) {
        return "0x" + Integer.toHexString(resp.getMode()).toUpperCase(Locale.US) + " ";
    }
    
    public static String getPidOutput(DiagnosticRequest req) {
        return req.getPid() == null ? "" : String.valueOf(req.getPid()) + " ";
    }
    
    public static String getPidOutput(DiagnosticResponse resp) {
        return resp.getPid() == null ? "" : String.valueOf(resp.getPid()) + " ";
    }
    
    public static String getPayloadOutput(DiagnosticRequest req) {
        return req.getPayload() == null ? "" : "0x" + new String(req.getPayload()) + " ";
    }
    
    public static String getPayloadOutput(DiagnosticResponse resp) {
        return resp.getPayload() == null ? "" : "0x" + new String(resp.getPayload()) + " ";
    }
    
    public static String getSuccessOutput(DiagnosticResponse resp) {
        return String.valueOf(resp.getSuccess()) + " ";
    }
    
    public static String getValueOutput(DiagnosticResponse resp) {
        return String.valueOf(resp.getValue()) + " ";
    }    
    
    public static String getFrequencyOutput(DiagnosticRequest req) {
        return req.getFrequency() == null ? "" : String.valueOf(req.getFrequency()) + " ";
    }
    
    public static String getNameOutput(DiagnosticRequest req) {
        return req.getName() == null ? "" : String.valueOf(req.getName()) + " ";
    }
    
    public static String getResponseCodeOutput(DiagnosticResponse resp) {
        return resp.getNegativeResponseCode().hexCodeString() + " ";
    }
    
    public static String getOutputTableResponseCodeOutput(DiagnosticResponse resp) {
        return getDocumentationError(resp) + " : " + getResponseCodeOutput(resp) + " ";
    }
    
    public static String getDocumentationError(DiagnosticResponse resp) {
        return resp.getNegativeResponseCode().toDocumentationString() + " ";
    }
    
    public static DiagnosticResponse generateRandomFakeResponse(DiagnosticRequest request) {
        int bus = request.getBusId();
        int id = request.getId();
        int mode = request.getMode();
        int pid = rnd.nextInt(5);
        boolean success = rnd.nextBoolean();
        float value = 0;
        NegativeResponseCode responseCode = NegativeResponseCode.NONE;
        if (success) {
            value = rnd.nextFloat();
        } else {
            responseCode = negativeResponseCodes.get(rnd.nextInt(negativeResponseCodes.size()));
        }
        
        return new DiagnosticResponse(bus, id, mode, pid, 
                request.getPayload(), success, responseCode, value, null);
    }
    
    public static int getOutputColor(Activity context, DiagnosticResponse resp) {
        int color = resp.getSuccess() ? R.color.lightBlue : R.color.darkRed;
        return context.getResources().getColor(color);
    }
    
    public static int getScreenHeight(Activity context) {
        Rect displayRect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(displayRect);
        return displayRect.height();
    }
    
    public static int getScreenWidth(Activity context) {
        Rect displayRect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(displayRect);
        return displayRect.width();
    }
}
