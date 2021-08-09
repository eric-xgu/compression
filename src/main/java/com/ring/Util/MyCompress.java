package com.ring.Util;

import com.ring.compression.TarGz;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCompress {
    public static void main(String args[]){
        TarGz ta=new TarGz();
        String uc=args[0];
        String resource_dir;
        String target_name;
        String file_name;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        if(uc.equals("comd")){
            resource_dir=args[1];
            target_name=args[2];
            System.out.println("正压缩文件夹:"+uc);
            System.out.println("源文件"+resource_dir);
            System.out.println("目标文件"+target_name);
            ta.dirtargzip(resource_dir,target_name);
        }else if(uc.equals("comf")){
            file_name=args[1];
            target_name=args[2];
            System.out.println("正压缩文件夹:"+uc);
            System.out.println("源文件"+file_name);
            System.out.println("目标文件"+target_name);
            ta.FilesTarGzip1(file_name,args[2]);
        }
        else if(uc.equals("ucom")){
            file_name=args[1];
            target_name=args[2];
            System.out.println("正解压文件夹:"+uc);
            System.out.println("压缩文件"+file_name);
            System.out.println("解压文件夹"+target_name);
            ta.UnCompressTarGzip(file_name,target_name);
        }
        else{
            System.out.println("请使用正确的参数");
        }
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间




        /*
            Parameter p=new Parameter();
            p.verify_parameter(p);
            TarGz ta=new TarGz();

            if(p.getComp_type()==1){
                if(p.getType()==1){  //压缩文件
                }else if(p.getType()==2){

                }else{
                    System.out.println("please using ccorrect parameter:comp_type,file_type");
                }
            }else if(p.getComp_type()==2){ //解压

            }else {
                System.out.println("please using ccorrect parameter:comp_type,file_type");
            }

            //TarGz.dirtargzip(p.resource_dir,p.target_dir);

            ta.UnCompressTarGzip("D:/d/c.tar.gz","D:/c/c");
            //TarGz.FilesTarGzip(p.list_files,p.target_dir);
//            for(String s:list_files){
//                System.out.println(s);
//            }
            // TarGz.dirtargzip(p.resource_dir,p.target_dir);
*/
        }
}
