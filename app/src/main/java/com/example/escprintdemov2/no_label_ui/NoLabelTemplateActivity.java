package com.example.escprintdemov2.no_label_ui;

import android.bld.PrintManager;
import android.bld.print.aidl.PrinterBinderListener;
import android.bld.print.configuration.PrintConfig;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.escprintdemov2.App;
import com.example.escprintdemov2.BaseActivity;
import com.example.escprintdemov2.R;
import com.example.escprintdemov2.utils.BitmapUtils;
import com.example.escprintdemov2.utils.PrintStrUtils;
import com.google.zxing.BarcodeFormat;

public class NoLabelTemplateActivity extends BaseActivity implements PrinterBinderListener {

    private PrintManager printUtil;
    private String mPageType;

    private Context mContext;
    private EditText editNoTempCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_no_label_template);
        setTitle(getString(R.string.btn_NO_label_TXT4));//设置标题
        setBackArrow();//设置返回按钮和点击事件

        mContext = getApplicationContext();
        Intent intent = getIntent();
        mPageType = intent.getStringExtra("TYPE");
        printUtil = PrintManager.getDefaultInstance(this);
        printUtil.addPrintListener(this);
        printUtil.open();

        editNoTempCon = findViewById(R.id.edit_no_temp_con);
        if ("1".equals(mPageType)) {
            printUtil.setBlackLabel(true);
        } else {
            printUtil.setBlackLabel(false);
        }

        Button mBtnNoTemplatePrint = findViewById(R.id.btn_no_template_print);
        mBtnNoTemplatePrint.setOnClickListener(view -> {
            canBack = false;
            try {
                if (!checkValidation()) {
                    canBack = true;
                    return;
                }
                printUtil.setDensity(Integer.valueOf(editNoTempCon.getText().toString().trim()));
                printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, PrintStrUtils.LanguageChange(mContext));
                printUtil.addLineFeed(4);
                printUtil.start();
            } catch (Exception e) {
                canBack = true;
                e.printStackTrace();
            }
        });
        Button mBtnNoTemplatePrint2 = findViewById(R.id.btn_no_template_print2);
        mBtnNoTemplatePrint2.setOnClickListener(view -> {
            canBack = false;
            if (!checkValidation()) {
                canBack = true;
                return;
            }

            if (Integer.valueOf(editNoTempCon.getText().toString().trim()) > 10) {
                Toast.makeText(this, getString(R.string.toast_density_outofrange), Toast.LENGTH_SHORT).show();
                canBack = true;
                return;
            }
            printTextTemplate();
        });
    }

    private boolean checkValidation() {
        if (TextUtils.isEmpty(editNoTempCon.getText().toString().trim())) {
            Toast.makeText(NoLabelTemplateActivity.this, getString(R.string.toast_density), Toast.LENGTH_SHORT).show();
            return false;
        }
        int density = Integer.valueOf(editNoTempCon.getText().toString().trim());
        if (density > 10 || density < 1) {
            Toast.makeText(this, getString(R.string.toast_density_outofrange), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printUtil != null) {
            printUtil.removePrintListener(this);
            printUtil.close();
        }
    }

    // 打印样例
    public void printTextTemplate() {
        int dens = 8;
        try {
            dens = Integer.parseInt(editNoTempCon.getText().toString());
        } catch (Exception ex) {
            Log.e("parse", ex.getLocalizedMessage());
        }
        printUtil.setDensity(dens);

        //标题
        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n");
        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_LARGE, true, true, "路边停车缴费\n");
        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n");
        printUtil.addLineFeed(1);

        //道路名称
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "收费单位：深圳市腾讯科技\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "收费员：张三\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "道路名称：深南大道1008号\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "车位号：B01\n");


        //车牌号
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_LARGE, true, false, "车牌：粤B22556\n");

        //道路名称
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "车辆类型：小车\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "驶入时间：2021-04-01 11:20:01\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "驶离时间：2021-04-01 16:30:17\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "停车时长：5小时10分钟16秒\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "小票类型：出场\n");

        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n");
        //提示补缴
        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "温馨提示\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "粤B22556号车主，您在2021-04-01 24:00:00之前累计有35笔2146.5元停车未付款记录，请尽快补缴相关费用\n");
        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n");
        printUtil.addLineFeed(1);
        String text = "http://openpark.suipa.cc:8082/outlinePay/roadSide/roadSideLeaseArrears.html?optId=28&carNo=浙A8E574&carNoColor=蓝";
        Bitmap bitmap = BitmapUtils.encode2dAsBitmap(text, BarcodeFormat.QR_CODE, 360, 360);
        bitmap = BitmapUtils.compressPic(bitmap, 300, 300, 80);
        printUtil.addImage(PrintConfig.Align.ALIGN_CENTER, bitmap);
        printUtil.addLineFeed(1);
        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "【费率描述】：65元\n");

        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n\n");

        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "停车泊位系利用城市道路等公共资源设置，按管理成本收取停车占道费，不含车辆保管费用，不承担车辆损毁、被盗等所致的法律责任。在泊位内发生的交通事故由交警部门根据《道路交通安全法》有关规定处理\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "服务咨询电话：88592157\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "价格投诉电话：12358\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "温馨提示：若车主离开时未找到路段管理员，可自行驶离，系统将自动停止计费，并将改停车记录上传后台。\n");
        printUtil.addText(PrintConfig.Align.ALIGN_LEFT, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "★恶意拖欠费用的车主将通过有关部门纳入征信系统\n");
        printUtil.addText(PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n\n");
        printUtil.addLineFeed(5);

        printUtil.start();

    }

    @Override
    public void onPrintCallback(int state) {
        Log.e("testtest", "  state:" + state);

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
        if (!canBack) {
            return;
        }
        super.onBackPressed();
    }


}