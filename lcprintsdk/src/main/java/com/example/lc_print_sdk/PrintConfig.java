//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.lc_print_sdk;

public class PrintConfig {
    public PrintConfig() {
        throw new RuntimeException("Stub!");
    }

    public class IErrorCode {
        public static final int ERROR_CMD = 19;
        public static final int ERROR_DATA_INPUT = 165;
        public static final int ERROR_DATA_INVALID = 18;
        public static final int ERROR_DEV_BMARK = 7;
        public static final int ERROR_DEV_FEED = 5;
        public static final int ERROR_DEV_IS_BUSY = 1;
        public static final int ERROR_DEV_NOT_OPEN = 16;
        public static final int ERROR_DEV_NO_BATTERY = 4;
        public static final int ERROR_DEV_PRINT = 6;
        public static final int ERROR_GRAY_INVALID = 20;
        public static final int ERROR_NO_DATA = 17;
        public static final int ERROR_NO_ERROR = 0;
        public static final int ERROR_PRINT_BARCODE = 162;
        public static final int ERROR_PRINT_BITMAP = 161;
        public static final int ERROR_PRINT_BITMAP_WIDTH_OVERFLOW = 164;
        public static final int ERROR_PRINT_DATA_MAC = 167;
        public static final int ERROR_PRINT_HOT = 2;
        public static final int ERROR_PRINT_ILLEGAL_ARGUMENT = 166;
        public static final int ERROR_PRINT_NOPAPER = 3;
        public static final int ERROR_PRINT_QRCODE = 163;
        public static final int ERROR_PRINT_TEXT = 160;
        public static final int ERROR_PRINT_UNKNOWN = 255;
        public static final int ERROR_RESULT_EXIST = 168;
        public static final int ERROR_TIME_OUT = 169;

        public IErrorCode() {
            throw new RuntimeException("Stub!");
        }
    }

    public class StateType {
        public static final int CHECK_ALL = 1;
        public static final int CHECK_BMASK = 7;
        public static final int CHECK_BUSY = 2;
        public static final int CHECK_FEED = 5;
        public static final int CHECK_PAPER = 4;
        public static final int CHECK_PRINT = 6;
        public static final int CHECK_TEMP = 3;

        public StateType() {
            throw new RuntimeException("Stub!");
        }
    }

    public static class HRIPosition {
        public static final int POSITION_ABOVE = 2;
        public static final int POSITION_BELOW = 3;
        public static final int POSITION_BOTH = 4;
        public static final int POSITION_NONE = 1;

        public HRIPosition() {
            throw new RuntimeException("Stub!");
        }
    }

    public static class BarCodeType {
        public static final int TOP_TYPE_CODABAR = 71;
        public static final int TOP_TYPE_CODE128 = 73;
        public static final int TOP_TYPE_CODE39 = 69;
        public static final int TOP_TYPE_CODE93 = 72;
        public static final int TOP_TYPE_EAN13 = 67;
        public static final int TOP_TYPE_EAN8 = 68;
        public static final int TOP_TYPE_ITF = 70;
        public static final int TOP_TYPE_UPCA = 65;
        public static final int TOP_TYPE_UPCE = 66;

        public BarCodeType() {
            throw new RuntimeException("Stub!");
        }
    }

    public static class Align {
        public static final int ALIGN_CENTER = 2;
        public static final int ALIGN_LEFT = 1;
        public static final int ALIGN_RIGHT = 3;

        public Align() {
            throw new RuntimeException("Stub!");
        }
    }

    public static class Density {
        public static final int TOP_GRAY_LARGE = 5;
        public static final int TOP_GRAY_MIDDLE = 3;
        public static final int TOP_GRAY_SMALL = 1;
        public static final int TOP_GRAY_SUPER = 8;
        public static final int TOP_GRAY_XLARGE = 6;
        public static final int TOP_GRAY_XMIDDLE = 4;
        public static final int TOP_GRAY_XSMALL = 2;
        public static final int TOP_GRAY_XSUPER = 9;
        public static final int TOP_GRAY_XXLARGE = 7;
        public static final int TOP_GRAY_XXSUPER = 10;

        public Density() {
            throw new RuntimeException("Stub!");
        }
    }

    public static class FontSize {
        public static final int TOP_FONT_SIZE_LARGE = 5;
        public static final int TOP_FONT_SIZE_MIDDLE = 3;
        public static final int TOP_FONT_SIZE_SMALL = 1;
        public static final int TOP_FONT_SIZE_SUPER = 7;
        public static final int TOP_FONT_SIZE_XLARGE = 6;
        public static final int TOP_FONT_SIZE_XMIDDLE = 4;
        public static final int TOP_FONT_SIZE_XSMALL = 2;
        public static final int TOP_FONT_SIZE_XSUPER = 8;

        public FontSize() {
            throw new RuntimeException("Stub!");
        }
    }
}
