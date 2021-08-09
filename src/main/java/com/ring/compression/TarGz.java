package com.ring.compression;




import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class TarGz {
    public  void FilesTarGzip(List<String> source_file, String target_file){
        List<Path> paths=new ArrayList<>();
        for(String file:source_file){
            paths.add(Paths.get(file));
        }
        Path t_p=Paths.get(target_file);
        String tar_file = target_file;
        Path output=Paths.get(tar_file);
        //OutputStream输出流、BufferedOutputStream缓冲输出流
        //GzipCompressorOutputStream是gzip压缩输出流
        //TarArchiveOutputStream打tar包输出流（包含gzip压缩输出流）
        try (OutputStream fOut = Files.newOutputStream(output);
             BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
             GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
             TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {

            //遍历文件list
            for (Path path : paths) {
                //该文件不是目录或者符号链接
                if (!Files.isRegularFile(path)) {
                    throw new IOException("Support only file!");
                }
                //将该文件放入tar包，并执行gzip压缩
                TarArchiveEntry tarEntry = new TarArchiveEntry(
                        path.toFile(),
                        path.getFileName().toString());

                tOut.putArchiveEntry(tarEntry);
                Files.copy(path, tOut);

                tOut.closeArchiveEntry();
            }
            //for循环完成之后，finish-tar包输出流
            tOut.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void FilesTarGzip1(String source_file, String target_file){

        Path t_p=Paths.get(target_file);
        String tar_file = target_file;
        Path output=Paths.get(tar_file);
        //OutputStream输出流、BufferedOutputStream缓冲输出流
        //GzipCompressorOutputStream是gzip压缩输出流
        //TarArchiveOutputStream打tar包输出流（包含gzip压缩输出流）
        try (OutputStream fOut = Files.newOutputStream(output);
             BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
             GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
             TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {
            Path path=Paths.get(source_file);
            //遍历文件list
                //该文件不是目录或者符号链接
                if (!Files.isRegularFile(path)) {
                    throw new IOException("Support only file!");
                }
                //将该文件放入tar包，并执行gzip压缩
                TarArchiveEntry tarEntry = new TarArchiveEntry(
                        path.toFile(),
                        path.getFileName().toString());

                tOut.putArchiveEntry(tarEntry);
                Files.copy(path, tOut);

                tOut.closeArchiveEntry();
            //for循环完成之后，finish-tar包输出流
            tOut.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public  void dirtargzip(String source_path,String target_path) {
       Path s_p = Paths.get(source_path);
       Path t_p = Paths.get(target_path);
       try {
           if (!Files.isDirectory(s_p)) {
               throw new IOException("源文件错误");
           }
           //压缩之后的输出文件名称
           String tarFileName = target_path;
           //OutputStream输出流、BufferedOutputStream缓冲输出流
           //GzipCompressorOutputStream是gzip压缩输出流
           //TarArchiveOutputStream打tar包输出流（包含gzip压缩输出流）
           try (OutputStream fOut = Files.newOutputStream(Paths.get(tarFileName));
                BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
                GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
                TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {
               //遍历文件目录树
               Files.walkFileTree(s_p, new SimpleFileVisitor<Path>() {
                   //当成功访问到一个文件
                   @Override
                   public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {

                       // 判断当前遍历文件是不是符号链接(快捷方式)，不做打包压缩处理
                       if (attributes.isSymbolicLink()) {
                           return FileVisitResult.CONTINUE;
                       }

                       //获取当前遍历文件名称
                       Path targetFile = s_p.relativize(file);

                       //将该文件打包压缩
                       TarArchiveEntry tarEntry = new TarArchiveEntry(
                               file.toFile(), targetFile.toString());
                       tOut.putArchiveEntry(tarEntry);
                       Files.copy(file, tOut);
                       tOut.closeArchiveEntry();
                       //继续下一个遍历文件处理
                       return FileVisitResult.CONTINUE;
                   }

                   //当前遍历文件访问失败
                   @Override
                   public FileVisitResult visitFileFailed(Path file, IOException exc) {
                       System.err.printf("无法对该文件压缩打包为tar.gz : %s%n%s%n", file, exc);
                       return FileVisitResult.CONTINUE;
                   }

               });
               //for循环完成之后，finish-tar包输出流
               tOut.finish();
           } catch (Exception e) {
               e.printStackTrace();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       }
    //判断压缩文件是否被损坏，并返回该文件的解压目录
    private  Path zipSlipProtect(ArchiveEntry entry,Path targetDir)
            throws IOException {

        Path targetDirResolved = targetDir.resolve(entry.getName());
        Path normalizePath = targetDirResolved.normalize();

        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("压缩文件已被损坏: " + entry.getName());
        }

        return normalizePath;
    }

    public  void UnCompressTarGzip(String resource_file,String target_path){
        try {
            //解压文件
            Path source = Paths.get(resource_file);
            //解压到哪
            Path target = Paths.get(target_path);

            if (Files.notExists(source)) {
                throw new IOException("您要解压的文件不存在");
            }

            //InputStream输入流，以下四个流将tar.gz读取到内存并操作
            //BufferedInputStream缓冲输入流
            //GzipCompressorInputStream解压输入流
            //TarArchiveInputStream解tar包输入流
            try (InputStream fi = Files.newInputStream(source);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 GzipCompressorInputStream gzi = new GzipCompressorInputStream(bi);
                 TarArchiveInputStream ti = new TarArchiveInputStream(gzi)) {

                ArchiveEntry entry;
                while ((entry = ti.getNextEntry()) != null) {

                    //获取解压文件目录，并判断文件是否损坏
                    Path newPath = zipSlipProtect(entry, target);

                    if (entry.isDirectory()) {
                        //创建解压文件目录
                        Files.createDirectories(newPath);
                    } else {
                        //再次校验解压文件目录是否存在
                        Path parent = newPath.getParent();
                        System.out.println(parent);
                        if (parent != null) {
                            if (Files.notExists(parent)) {
                                Files.createDirectories(parent);
                            }
                        }
                        // 将解压文件输入到TarArchiveInputStream，输出到磁盘newPath目录
                        Files.copy(ti, newPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
