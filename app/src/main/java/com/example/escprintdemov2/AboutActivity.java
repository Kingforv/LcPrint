package com.example.escprintdemov2;


import android.bld.PrintManager;
import android.bld.print.aidl.PrinterBinderListener;
import android.bld.print.configuration.PrintConfig;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.escprintdemov2.utils.CommonUtils;

import java.text.SimpleDateFormat;

public class AboutActivity extends BaseActivity implements PrinterBinderListener {

    private PrintManager printUtil = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_about);
        setTitle(getString(R.string.app_set_about));//设置标题
        setBackArrow();//设置返回按钮和点击事件

        printUtil = PrintManager.getDefaultInstance(this);
        printUtil.addPrintListener(this);
        printUtil.open();

        Button btnPrintCheck = findViewById(R.id.btn_print_check);
        Button btnSetDef = findViewById(R.id.btn_set_def);

        TextView txt_version = findViewById(R.id.txt_version);
        txt_version.setText(CommonUtils.getPackageName(this));

        btnPrintCheck.setOnClickListener(view -> {
            canBack = false;
            try {
                String strVersion = printUtil.getPrinterVer();
                printUtil.setBlackLabel(false);
                printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_LARGE, true, false,"\nSDK Version: " + strVersion + "\n");
                printUtil.addLineFeed(10);
                printUtil.start();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            canBack = true;
        });

        btnSetDef.setOnClickListener(view -> {
            try {
                printUtil.reset();
                printUtil.setUnwindPaperLen (60);
                showToast(getString(R.string.reset_completed));
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public void onPrintCallback(int state) {
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
