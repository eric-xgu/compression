package com.ring.Util;

import com.ring.compression.TarGz;
import com.sun.corba.se.spi.orb.PropertyParser;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static javafx.application.Platform.exit;

public class Parameter {
        private String resource_dir;
        private String target_dir;
        private String file_name;
        private String compress_type;
        private List<String> list_files;
        private int comp_type; //1压缩 2解压文件
        private int type; //1 压缩文件，2压缩文件夹
        private  String tar_parent;
    public String getResource_dir() {
        return resource_dir;
    }

    public void setResource_dir(String resource_dir) {
        this.resource_dir = resource_dir;
    }

    public String getTarget_dir() {
        return target_dir;
    }

    public void setTarget_dir(String target_dir) {
        this.target_dir = target_dir;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getCompress_type() {
        return compress_type;
    }

    public void setCompress_type(String compress_type) {
        this.compress_type = compress_type;
    }

    public List<String> getList_files() {
        return list_files;
    }

    public void setList_files(List<String> list_files) {
        this.list_files = list_files;
    }

    public int getComp_type() {
        return comp_type;
    }

    public void setComp_type(int comp_type) {
        this.comp_type = comp_type;
    }



        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public  Parameter(){
            Properties properties = new Properties();
            //new File默认是相对路径
//            System.out.println(System.getProperty("user.dir"));
            File file = new File("config");
            if(file.exists())
            {
                try{
                    InputStream fis = new FileInputStream(file);
                    properties.load(fis);
                    comp_type=Integer.parseInt(properties.getProperty("comp_type"));
                    resource_dir=properties.getProperty("resource_dir");
                    target_dir=properties.getProperty("target_dir");
                    file_name=properties.getProperty("resource_file");
                    compress_type=properties.getProperty("compress_type");
                    list_files=Arrays.asList(file_name.split(" "));
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
            }
        }

        public static void verify_parameter(Parameter p){
            if(p.resource_dir!=null&&p.file_name!=null&&p.file_name.length()!=0&&p.resource_dir.length()!=0){
                System.out.println("Only one parameter resource_dir and resource_file can be set");
                exit();
            }
            else if(p.resource_dir!=null||p.resource_dir.length()!=0){
                p.setType(2);
            }
            else {
                p.setType(1);
            }
            if(p.resource_dir.equals(p.getTarget_dir())){
                System.out.println("The parameter resource_dir and resource_file cannot be the same");
                exit();
            }

        }
}
