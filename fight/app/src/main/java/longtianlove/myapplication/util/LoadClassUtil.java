package longtianlove.myapplication.util;

import android.content.Context;
import android.sax.Element;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Created by 58 on 2016/12/22.
 */

public class LoadClassUtil {
    public static List<String> getClasses(Context context) {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            Class ElementClass= BaseDexClassLoader.class.getClassLoader().loadClass("dalvik.system.DexPathList$Element");
//            dalvik.system.DexPathList
//            ElementClass.newInstance();
//            ElementClass  baseDexElements = (ElementClass) getDexElements(getPathList(context.getClassLoader()));
            getDexElements(getPathList(context.getClassLoader())).getClass().toString();
//            for(dalvik.system.DexPathList$Element  element:baseDexElements){
//                classes.add(element.);
//            }
            classes.add(getDexElements(getPathList(context.getClassLoader())).getClass().toString());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }
    private static Object getPathList(Object baseDexClassLoader)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        return ReflectionUtil.getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }
    private static Object getDexElements(Object paramObject)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        return ReflectionUtil.getField(paramObject, paramObject.getClass(), "dexElements");
    }
}

