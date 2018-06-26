package com.neusoft.cq.cbec.common.util;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public abstract class PlatformUtil {
    private PlatformUtil() {
    }

    
    public static String join(int[] arr,String join){
        if(arr==null||arr.length==0)return "";

        StringBuilder result=new StringBuilder();
        for(int i=0,z=arr.length-1; i<z ; i++){
            result.append( arr[z] ).append(join);
        }
        result.append(arr[arr.length-1]);
        return result.toString();
    }
    
    public static boolean isBlank(String s){
        return s==null||s.trim().length()==0;
    }
    
    public static <T>String join(T[] arr,String join){
        if(arr==null||arr.length==0)return "";
        StringBuilder result=new StringBuilder();
        for(int i=0,z=arr.length-1; i<z ; i++){
            result.append( arr[z] ).append(join);
        }
        result.append(arr[arr.length-1]);
        return result.toString();
    }

    public static <T>String join(List<T> list,String join){
        if(list==null||list.size()==0)return "";
        StringBuilder result=new StringBuilder();
        for(int i=0,z=list.size()-1; i<z ; i++){
            result.append( list.get(i)).append(join);
        }
        result.append(list.get(list.size()-1));
        return result.toString();
    }
    public static int parseInt(String v, int def){
        try{
            return Integer.parseInt(v);
        }catch(Exception e){
        }
        return def;
    }

    public static String escape(String s){
        if(s==null) return s;
        if(s.length()==0) return s;
        return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    public static String escapeTrim(String s){
        if(s==null) return null;
        if((s = s.trim()).length()==0) return null;
        return escape(s);
    }

    public static   String wrap(final String s, final String wrap) {
        if (s == null) {
            return s;
        }
        return new StringBuilder(wrap.length() * 2 + s.length()).append(wrap)
                .append(s).append(wrap).toString();
    }
    
    public static String repeat(final String s,final int times){
        if (s == null) {
            return s;
        }
        StringBuilder result=new StringBuilder(s.length()*times);
        for(int i=times;i-->0;){
            result.append(s);
        }
        return result.toString();
    }
    /**
     * 清理字符串 如果是s为null,则返回null 否则去掉前后空格(trim) 然后如果是空字符串,则返回null
     *
     * @param s
     * @return
     */
    public static  String trim(String s){
        return s==null ? null : (s = s.trim()).length()==0 ? null : s;
    }


    /**
     * 添加前置0
     *
     * @param target
     * @param maxLength
     * @return
     */
    public static String prefix0(String target, int maxLength){
        int need = maxLength-target.length();
        char[] result = new char[maxLength];
        for(int i = 0; i<need; i++){
            result[i] = '0';
        }
        for(int i = target.length(), j = result.length-1; i-->0; ){
            result[j--] = target.charAt(i);
        }
        return new String(result);
    }
    /**
     * 随机生成32位
     *
     * @return
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        char[] cs = new char[32];
        for (int i = uuid.length(), j = 0; i-- > 0; ) {
            if (uuid.charAt(i) != '-') {
                cs[j++] = uuid.charAt(i);
            }
        }
        return new String(cs);
    }

    public static String uuid16() {
        long uuid = SnowFlakeUUID.uuid();
        return prefix0(Long.toString(uuid, 16), 16);
    }

    public static String uuid12() {
        long uuid = SnowFlakeUUID.uuid();
        return prefix0(Long.toString(uuid, 36), 12);
    }
    public static void main(String[] args) {
		System.out.println(uuid12());
	}



    static public <TID extends Serializable, T> LinkedHashMap<TID, T> list2map(List<T> list, Function<T, TID> idSupplier) {
        LinkedHashMap<TID, T> map = new LinkedHashMap<TID, T>(list.size(), 1);
        for (T t : list) {
            map.put(idSupplier.apply(t), t);
        }
        return map;
    }

    public static String inputStream2String(InputStream in) throws  IOException{
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(in,"utf-8"))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }
            return result.toString();
        }


    }
}
