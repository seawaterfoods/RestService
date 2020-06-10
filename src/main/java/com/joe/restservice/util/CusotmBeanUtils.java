package com.joe.restservice.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class CusotmBeanUtils {

    /*
    *
    *
    * */

    public static String[] getNullPropertyNames(Object source){
//      將實體對象包裝起來
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds=beanWrapper.getPropertyDescriptors();

        List<String> nullPropertyNames=new ArrayList<>();

        for (PropertyDescriptor pd:pds){
//            獲取屬性對象得屬姓名
            String propertyName = pd.getName();
//            如果對應屬姓名的屬性值為null時，將其加入nullPropertyNames
            if (beanWrapper.getPropertyValue(propertyName)== null){
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
