package com.application.mahabad.niroomohareke.Adapter;

public class ToPersianNumber {

    private String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};


    public String toPersianNumber(String text) {
        if (text != null) {
            if (text.length() == 0) {
                return "";
            }
        } else
            return "";
        String out = "";
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(String.valueOf(c));
                out += persianNumbers[number];
            } else if (c == '٫') {
                out += '،';
            } else {
                out += c;
            }

        }
        return out + " تومان";
    }

    public String toPersianNumberCode(String text) {
        if (text != null) {
            if (text.length() == 0) {
                return "";
            }
        } else
            return "";
        String out = "";
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(String.valueOf(c));
                out += persianNumbers[number];
            } else if (c == '٫') {
                out += '،';
            } else {
                out += c;
            }

        }
        return  "کد کالا: "+out ;
    }
}
