package org.happyfaces.jsf.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter {

    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object obj) {
        if (obj == null) {
            return "";
        }
        BigDecimal number = (BigDecimal) obj;
        DecimalFormat format = new DecimalFormat(ResourceBundle.getBundle("messages").getString("bigDecimal.format"));
        String value = format.format(number);
        return value;
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        if (!str.matches(ResourceBundle.getBundle("messages").getString("bigDecimal.pattern"))) {
            throw new ConverterException(ResourceBundle.getBundle("messages").getString("javax.faces.converter.BigDecimalConverter.DECIMAL_detail"));
        }
        str = str.replace(" ", "");
        str = str.replace("\u00a0", "");
        str = str.replace(",", ".");

        return new BigDecimal(str);
    }
}