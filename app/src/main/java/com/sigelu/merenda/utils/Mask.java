package com.sigelu.merenda.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by felcks on Jun, 2019
 */
public abstract class Mask {
    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll("[:]", "")
                .replace("[ ]", "");
    }

    public static String mask(String mask, String s) {

        if(s.isEmpty())
            return "";

        StringBuilder maskedText = new StringBuilder();

        int stringPosition = 0;

        for(int i = 0; i < mask.length(); i++) {
            if(mask.charAt(i) == '#') {

                if(stringPosition < s.length()) {
                    maskedText.append(s.charAt(stringPosition));
                    stringPosition++;
                } else {
                    maskedText.append(" ");
                }
            } else {
                maskedText.append(mask.charAt(i));
            }
        }

        return maskedText.toString();
    }

    public static TextWatcher insert(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {
                String str = Mask.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                if( str.length() > old.length()){
                    for (char m : mask.toCharArray()) {
                        if (m != '#') {
                            mascara += m;
                            continue;
                        }else{
                            try {
                                mascara += str.charAt(i);
                            } catch (Exception e) {
                                break;
                            }
                            i++;
                        }
                    }
                }else{
                    mascara = s.toString();
                }


//                int i = 0;
//                for (char m : mask.toCharArray()) {
//                    if (m != '#' && str.length() > old.length()) {
//                        mascara += m;
//                        continue;
//                    }
//                    try {
//                        mascara += str.charAt(i);
//                    } catch (Exception e) {
//                        break;
//                    }
//                    i++;
//                }


                isUpdating = true;
                if(ediTxt != null) {
                    ediTxt.setText(mascara);
                    ediTxt.setSelection(mascara.length());
                }
            }
        };


    }

}