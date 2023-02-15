package com.example.lc_print_sdk;

import android.bld.PrintManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by JinXing
 * on 2021/8/12
 */
public class PrintUtil {
    private static PrintManager printManager=null;
    private PrinterBinderListener mListener=null;
    public interface PrinterBinderListener extends android.bld.print.aidl.PrinterBinderListener {
        void onPrintCallback(int state);
        void onVersion(String version);

    }
    //获取实例
    private static PrintUtil singleCase = null;
    public static synchronized PrintUtil getInstance(Context context) {
        if (singleCase == null) {
            try {
                singleCase = new PrintUtil();
                printManager=PrintManager.getDefaultInstance (context);
                printManager.open ();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return singleCase;
    }


    public static synchronized void setPrintEventListener(PrinterBinderListener aListener) {
        printManager.addPrintListener (aListener);
        aListener.onVersion (printManager.getPrinterVer ());
    }

    public static synchronized void removePrintListener(PrinterBinderListener aListener) {
        printManager.removePrintListener (aListener);
    }

    public static synchronized String getVersion() {
        return printManager.getPrinterVer ();
    }

    public static synchronized void open() {
        printManager.open ();
    }

    public static synchronized void close() {
        printManager.close ();
    }

    public static synchronized void printConcentration(int density) {
        Log.d ("aa", "(int)Math.ceil(density/4): "+(((int)Math.ceil(density/4))+1));
        printManager.setDensity (((int)Math.ceil(density/4))+1);
    }

    public static synchronized int getConcentration() {
        return printManager.getDensity ()*4;
    }

    public static synchronized void resetPrint() {
        printManager.reset ();
    }

    public static synchronized void printFontSize(int fontSize) {
        printManager.setFontSize (fontSize);
    }

    public static synchronized int getFontSize() {
        return printManager.getFontSize ();
    }

    public static synchronized void printTextBold(boolean fontBold) {
        printManager.setFontBold (fontBold);
    }

    public static synchronized boolean isFontBold() {
        return printManager.isFontBold ();
    }

    public static synchronized void printEnableMark(boolean enable) {
        printManager.setBlackLabel (enable);
    }

    public static synchronized boolean isBlackLabel() {
        return printManager.isBlackLabel ();
    }

    public static synchronized void setUnderLine(boolean enable) {
        printManager.setUnderLine (enable);
    }

    public static synchronized boolean isUnderLine() {
        return printManager.isUnderLine ();
    }

    public static synchronized void setFeedPaperSpace(int space) {
        printManager.setFeedPaperSpace (space);
    }

    public static synchronized int getFeedPaperSpace() {
        return printManager.getFeedPaperSpace ();
    }

    public static synchronized void setUnwindPaperLen(int length) {
        printManager.setUnwindPaperLen (length);
    }

    public static synchronized int getUnwindPaperLen() {
        return printManager.getUnwindPaperLen ();
    }

    public static synchronized void printText(int offset, int fontSize, boolean isBold, boolean isUnderLine, String content) {
        printManager.addText (offset, fontSize, isBold, isUnderLine, content);
    }
    public static synchronized void printText( String content) {
        printManager.addText (PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, content);
    }

    public static synchronized void printBarcode(int offset, int height, String content, int barcodeType, int hriPosition) {
        printManager.addBarcode (offset, height, content, barcodeType, hriPosition);
    }
    public static synchronized void printBarcode( int height, String content, int barcodeType) {
        printManager.addBarcode (PrintConfig.Align.ALIGN_CENTER, height, content, barcodeType, PrintConfig.HRIPosition.POSITION_BELOW);
    }

    public static synchronized void printQR(int offset, int height, String content) {
        printManager.addQRCode (offset, height, content);
    }
    public static synchronized void printQR( String content) {
        printManager.addQRCode (PrintConfig.Align.ALIGN_CENTER, 384, content);
    }

    public static synchronized void printBitmap(int offset, Bitmap image) {
        printManager.addImage (offset, image);
    }
    public static synchronized void printBitmap( Bitmap image) {
        printManager.addImage (PrintConfig.Align.ALIGN_CENTER, image);
    }
    public static synchronized void printBitmap(int offset, String imagePath) {
        printManager.addImageFile (offset, imagePath);
    }
    public static synchronized void printBitmap( String imagePath) {
        printManager.addImageFile (PrintConfig.Align.ALIGN_CENTER, imagePath);
    }

    public static synchronized void printLine(int lines) {
        printManager.addLineFeed (lines);
    }

    public static synchronized void start() {
        printManager.start ();
    }

    public static synchronized void setReverse(boolean reverse) {
        printManager.setReverse (reverse);
    }

    public static synchronized boolean isReverse() {
        return printManager.isReverse ();
    }

    public synchronized void printGoToNextMark(int distance) {
        printManager.setFeedPaperSpace(distance);
        printManager.start ();

    }
    public synchronized void printGoToNextMark() {
        printManager.setFeedPaperSpace(1000);
        printManager.start ();

    }

    /**
     * 接口功能：设置文本内容间距，单位倍数
     * @param spacing 走纸缝隙大小
     *
     */
    public synchronized void setLineSpacing(float spacing){
       printManager.setLineSpacing (spacing);
    }

    /**
     * 接口功能：获取本内容间距,单位倍数
     */
    public synchronized float getLineSpacing(){
        return  printManager.getLineSpacing ();
    }

    /**
     * 接口功能：获取本内容间距,单位倍数
     */
    public static synchronized int getsupportprint(){

        return  SystemProperties.getInt ("ro.blovedream_support_print",0);
    }

}