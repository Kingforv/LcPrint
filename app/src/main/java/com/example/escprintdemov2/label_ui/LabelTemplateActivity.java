package com.example.escprintdemov2.label_ui;

import android.bld.PrintManager;
import android.bld.print.aidl.PrinterBinderListener;
import android.bld.print.configuration.PrintConfig;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.escprintdemov2.BaseActivity;
import com.example.escprintdemov2.R;
import com.example.escprintdemov2.utils.BitmapUtils;
import com.google.zxing.BarcodeFormat;

public class LabelTemplateActivity extends BaseActivity implements PrinterBinderListener {

    private PrintManager printUtil;
    private String mPageType;
    private EditText mEditCon, mEditDistance;
    private EditText edit_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_label_template);
        setTitle(getString(R.string.btn_label_TXT4));//设置标题
        setBackArrow();//设置返回按钮和点击事件

        Intent intent = getIntent();
        mPageType = intent.getStringExtra("TYPE");

        printUtil = PrintManager.getDefaultInstance(this);
        printUtil.addPrintListener(this);
        printUtil.open();

        mEditCon = findViewById(R.id.edit_con);
        mEditDistance = findViewById(R.id.edit_distance);
        Button mBtnTemplatePrint = findViewById(R.id.btn_template_print);
        edit_return=findViewById (R.id.edit_return);
        int unwindPaperLen=printUtil.getUnwindPaperLen ();
        edit_return.setText (unwindPaperLen+"");
        mBtnTemplatePrint.setOnClickListener(view -> {
            canBack = false;
            if (TextUtils.isEmpty(mEditCon.getText().toString().trim())) {
                Toast.makeText(LabelTemplateActivity.this, getString(R.string.toast_density), Toast.LENGTH_SHORT).show();
                canBack = true;
                return;
            }
            int concentration = Integer.valueOf(mEditCon.getText().toString().trim());
            if( concentration > 10 || concentration < 1){
                Toast.makeText(this, getString(R.string.toast_density_outofrange), Toast.LENGTH_SHORT).show();
                canBack = true;
                return;
            }
            if (mEditDistance.getText().toString().trim().length() == 0) {
                Toast.makeText(this, getString(R.string.toast_distance_invalid), Toast.LENGTH_SHORT).show();
                canBack = true;
                return;
            }
            int distance = Integer.valueOf(mEditDistance.getText().toString().trim());
            if (distance <= 0) {
                Toast.makeText(this, getString(R.string.toast_distance_invalid), Toast.LENGTH_SHORT).show();
                canBack = true;
                return;
            }
            if (edit_return.getText().toString().trim().length() == 0) {
                Toast.makeText(this, getString(R.string.toast_rerurn_distance_invalid), Toast.LENGTH_SHORT).show();
                canBack = true;
                return;
            }
            int return_distance = Integer.valueOf(edit_return.getText().toString().trim());
            if (return_distance <= 0) {
                Toast.makeText(this, getString(R.string.toast_rerurn_distance_invalid), Toast.LENGTH_SHORT).show();
                canBack = true;
                return;
            }
            printUtil.setUnwindPaperLen (return_distance);
            printLabel(concentration, distance);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printUtil!=null){
            printUtil.removePrintListener (this);
            printUtil.close();
        }
    }

    public void printLabel(int concentration, int distance) {
        try {
//
            printUtil.setBlackLabel(true);
            printUtil.setDensity(concentration);
            printUtil.setFeedPaperSpace(distance);
            printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false,getString(R.string.Label_test1) + "1\n");
            printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false,getString(R.string.Label_test2) + "2\n");
            printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, true,getString(R.string.Label_test3) + "3\n");
//            printUtil.addLineFeed (1);
//            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.a);
//            Bitmap bitmap1 = BitmapUtils.compressPic(bitmap, 200, 200);
//            printUtil.addImage (PrintConfig.Align.ALIGN_CENTER,bitmap1);
//            printUtil.addLineFeed (1);
            String text = "http://openpark.suipa.cc:8082/outlinePay/roadSide/roadSideLeaseArrears.html?optId=28&carNo=浙A8E574&carNoColor=蓝";
            Bitmap bitmap = BitmapUtils.encode2dAsBitmap(text, BarcodeFormat.QR_CODE, 360, 360);
            bitmap = BitmapUtils.compressPic(bitmap, 300, 300, 80);
            printUtil.addImage(PrintConfig.Align.ALIGN_CENTER, bitmap);
            printUtil.start();
        } catch (Exception e) {
            canBack = true;
            e.printStackTrace();
        }
    }

    @Override
    public void onPrintCallback(int state) {
        Log.e ("testtest", "  state:"+state);
        canBack = true;
        if (PrintConfig.IErrorCode.ERROR_NO_ERROR == state) {
            //打印成功
            showToast(getString(R.string.toast_print_success));

        } else if (PrintConfig.IErrorCode.ERROR_PRINT_NOPAPER == state) {
            showToast(getString(R.string.toast_no_paper));
        } else if ( PrintConfig.IErrorCode.ERROR_DATA_INPUT==state ) {
            showToast(getString(R.string.toast_input_parameter_error));
        } else if ( PrintConfig.IErrorCode.ERROR_CMD==state) {
            showToast(getString(R.string.toast_Instruction_error));
        } else if ( PrintConfig.IErrorCode.ERROR_DATA_INVALID==state) {
            showToast(getString(R.string.toast_data_is_invalid));
        } else if ( PrintConfig.IErrorCode.ERROR_DEV_BMARK==state) {
            showToast(getString(R.string.toast_abnormal_black_mark_detection));
        }else if ( PrintConfig.IErrorCode.ERROR_DEV_FEED==state) {
            showToast(getString(R.string.toast_moving_paper));
        }else if ( PrintConfig.IErrorCode.ERROR_DEV_IS_BUSY==state) {
            showToast(getString(R.string.toast_device_busy));
        }else if ( PrintConfig.IErrorCode.ERROR_DEV_NOT_OPEN==state) {
            showToast(getString(R.string.toast_device_is_not_turned_on));
        }else if ( PrintConfig.IErrorCode.ERROR_DEV_NO_BATTERY==state) {
            showToast(getString(R.string.toast_low_electricity));
        }else if ( PrintConfig.IErrorCode.ERROR_DEV_PRINT==state) {
            showToast(getString(R.string.toast_print_now));
        }else if ( PrintConfig.IErrorCode.ERROR_GRAY_INVALID==state) {
            showToast(getString(R.string.toast_illegal_concentrationr));
        }else if ( PrintConfig.IErrorCode.ERROR_NO_DATA==state) {
            showToast(getString (R.string.toast_no_data));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_BARCODE==state) {
            showToast(getString(R.string.toast_error_printing_barcode));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_BITMAP==state) {
            showToast(getString(R.string.toast_error_printing_bitmap));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_BITMAP_WIDTH_OVERFLOW==state) {
            showToast(getString(R.string.toast_print_bitmap_width_overflow));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_DATA_MAC==state) {
            showToast(getString(R.string.toast_mac_check_error));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_HOT==state) {
            showToast(getString (R.string.toast_high_temperature));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_ILLEGAL_ARGUMENT==state) {
            showToast(getString(R.string.toast_parameter_error));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_QRCODE==state) {
            showToast(getString(R.string.toast_error_printing_qrcode));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_TEXT==state) {
            showToast(getString(R.string.toast_print_text_error));
        }else if ( PrintConfig.IErrorCode.ERROR_PRINT_UNKNOWN==state) {
            showToast(getString(R.string.toast_unknown_error));
        }else if ( PrintConfig.IErrorCode.ERROR_RESULT_EXIST==state) {
            showToast(getString(R.string.toast_result_already_exists));
        }else if ( PrintConfig.IErrorCode.ERROR_TIME_OUT==state) {
            showToast(getString(R.string.toast_overtime));
        }else {
            showToast("Printer error. state=" + state);
        }
    }


    private boolean canBack = true;
    @Override
    protected boolean titleCanBack() {
        return canBack;
    }
    @Override
    public void onBackPressed() {
        if (!canBack){
            return;
        }
        super.onBackPressed ();
    }

}