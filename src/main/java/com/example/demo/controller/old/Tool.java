package com.example.demo.controller.old;/*
package com.example.demo.controller.old;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tool {
    public  boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {

        boolean flag = false;
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            log.info(f.getName());
            if(f.get(obj) == null){
                flag = true;
                return flag;
            }
        }
        return flag;
    }

}
*/
