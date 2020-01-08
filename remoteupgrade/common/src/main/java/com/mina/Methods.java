/**
 *
 */
package com.mina;


import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class Methods {

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            if (s4.length() == 1)
                s4 = "0" + s4;
            s4 = s4 + " ";
            str = str + s4;
        }
        return str;
    }

    public static Object getObjNewInstance(String className)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class<?> class1 = null;
        Object object = null;
        class1 = Class.forName(className);

        object = class1.newInstance();

        return object;
    }


    /**
     * 追加空格
     */
    public static String putSpace(String str) {
        String ret = "";

        if (str.length() < 16) {

            int k = 16 - str.length();

            for (int i = 0; i < k; i++) {
                ret = ret + " ";
            }
            ret = str + ret;
        }

        return ret;
    }


    /**
     * @date 2018��7��20��
     * @readme ִ��ָ������ָ������
     * @param instance
     *            ִ�е���
     * @param annotationClass
     *            ִ�����з����ϵ�ע����
     * @param annotationField
     *            ע������ָ������
     * @param annotationValue
     *            ע������ָ�����Ե�ֵ
     * @param params
     *            ִ�����з����Ĳ���
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static Object invoke(Object instance, Class<? extends Annotation> annotationClass, String annotationField,
                                Object annotationValue, Object params)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException,
            InstantiationException, NoSuchFieldException, SecurityException, NoSuchMethodException {

        Class<?> classE = instance.getClass();

        Method[] methods = classE.getMethods();

        for (Method method : methods) {

            // �жϸ÷����Ƿ����annotationClassע��
            if (method.isAnnotationPresent(annotationClass)) {

                // ʵ���÷����ϵ�ע����
                Object annotationClassInstance = method.getAnnotation(annotationClass);

                // ��ȡע������ָ������(annotationField)����
                // Field field =
                // annotationClass.getDeclaredField(annotationField);

                Method annotationM = annotationClass.getDeclaredMethod(annotationField);

                // ���Ʒ�װ ʵ����setAccessible�����úͽ��÷��ʰ�ȫ���Ŀ���,������Ϊtrue���ܷ���Ϊfalse�Ͳ��ܷ���
                // ����JDK�İ�ȫ����ʱ�϶�.����ͨ��setAccessible(true)�ķ�ʽ�رհ�ȫ���Ϳ��Դﵽ���������ٶȵ�Ŀ��
                annotationM.setAccessible(true);

                // �ж�annotationClassInstanceʵ����ע������annotationField���Ե�ֵ�Ƿ����annotationValue

                if (annotationM.invoke(annotationClassInstance).equals(annotationValue)) {
                    method.setAccessible(true);
                    return method.invoke(instance, params);
                }

            }

        }

        return null;

    }


    /**
     * @param msg
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeGBK(String msg) throws UnsupportedEncodingException {
        byte[] array = msg.getBytes("GBK");
        return new String(array, "ISO-8859-1");
    }


    public static String hexStr2Byte(String hex) throws UnsupportedEncodingException {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return new String(bf.array(),"ISO-8859-1");
    }


}
