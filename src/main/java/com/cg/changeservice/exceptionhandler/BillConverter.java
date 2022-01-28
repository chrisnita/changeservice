package com.cg.changeservice.exceptionhandler;

import com.cg.changeservice.domain.Bill;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

public class BillConverter  extends PropertyEditorSupport{

    public void setAsText(final String text) throws IllegalArgumentException {
        try {
            setValue(Bill.fromValue(Double.parseDouble(text)));
        }catch(NumberFormatException numberFormatException){
            setValue(Bill.valueOf(text.toUpperCase()));
        }

    }

}