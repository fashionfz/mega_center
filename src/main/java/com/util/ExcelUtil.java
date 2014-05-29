package com.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.po.Device;

public class ExcelUtil {

    /**
     * 写入Excel示例
     */
    public void writeExcel() {
        try {
            // 创建一个写入Excel表文件的对象
            jxl.write.WritableWorkbook book = jxl.Workbook
                    .createWorkbook(new File("E:/demo/测试1.xls"));
            /*
             * 根据写入表对象中创建一个单张表格的写入对象 第一个参数是单张表的名字 第二个是单张表的位置
             */
            jxl.write.WritableSheet sheet = book.createSheet("第一页", 0);
            /*
             * 单元格的文本内容及位置用 Label表示
             * 
             * 在Label对 象的构造方法中指名单元格位置是第一列第一行(0,0)
             * 
             * 第一个参数是列的位置
             * 
             * 第二个参数是行的位置
             * 
             * 第三个参数是单元格的值是一个字符串
             */
            jxl.write.Label label = new jxl.write.Label(0, 0, "aaaaa");
            // 将定义好的单元格添加到工作表中
            sheet.addCell(label);
            /*
             * 生成一个保存数字的单元格
             * 
             * 必须使用Number的完整包路径，否则有语法歧义
             * 
             * 单元格位置是第二列，第一行，值为789.123
             */
            jxl.write.Number number = new jxl.write.Number(1, 0, 789.123);
            // 将数字内容对象添加到工作表中
            sheet.addCell(number);
            // 写入数据并关闭文件
            book.write();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (jxl.write.biff.RowsExceededException e) {
            e.printStackTrace();
        } catch (jxl.write.WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读入Excel示例
     */
    public List<Device> readExcel(InputStream in) {
        List<Device> list = new ArrayList<Device>();
        jxl.Cell cell;
        try {
            // 根据指定的Excel文件得到一个工作表对象
            jxl.Workbook book = jxl.Workbook.getWorkbook(in);
            // 根据指定的位置得到工作表对象中的一张表
            jxl.Sheet sheet = book.getSheet(0);
            /*
             * 从一张表的对象得到指定的单元格
             * 
             * 第一个参数是列的位置
             * 
             * 第二个参数是行的位置
             */
            for(int row=1;row<sheet.getRows();row++){
                Device device = new Device();
                
                cell = sheet.getCell(0, row);
                device.setVicName(cell.getContents());
                
                cell = sheet.getCell(1, row);
                device.setDvrName(cell.getContents());
                
                cell = sheet.getCell(2, row);
                device.setDvrIp(cell.getContents());
                
                cell = sheet.getCell(3, row);
                device.setDvrPort(Integer.parseInt(cell.getContents()));
                
                cell = sheet.getCell(4, row);
                device.setDvrUserName(cell.getContents());
                
                cell = sheet.getCell(5, row);
                device.setDvrPassword(cell.getContents());
                
                cell = sheet.getCell(6, row);
                if(cell.getContents()!=null&&!"".equals(cell.getContents()))
                    device.setRecordCycle(Integer.parseInt(cell.getContents()));
                else
                    device.setRecordCycle(0);
                
                cell = sheet.getCell(7, row);
                if(cell.getContents()!=null&&!"".equals(cell.getContents()))
                    device.setRecordCycleRemote(Integer.parseInt(cell.getContents()));
                else
                    device.setRecordCycleRemote(0);
                
                
                cell = sheet.getCell(8, row);
                device.setRecordPlot(cell.getContents());
                
                cell = sheet.getCell(9, row);
                if(cell.getContents()!=null&&!"".equals(cell.getContents()))
                    device.setChannel(Integer.parseInt(cell.getContents()));
                else
                    device.setChannel(0);
                
                cell = sheet.getCell(10, row);
                device.setLocation(cell.getContents());
                
                list.add(device);
            }
            // 得到单元格中的内容
            
        } catch (jxl.read.biff.BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 修改Excel示例
     */
    public void updateExcel() {
        try {
            // 获得Excel文件对象
            jxl.Workbook wb = jxl.Workbook.getWorkbook(new File(
                    "E:/demo/测试1.xls"));
            // 打开一个文件的副本 ，并且指定数据写回到原文件中
            jxl.write.WritableWorkbook book = jxl.Workbook.createWorkbook(
                    new File("E:/demo/测试1.xls"), wb);
            // 添加一个工作表
            jxl.write.WritableSheet sheet = book.createSheet("第二页", 1);
            sheet.addCell(new jxl.write.Label(0, 0, "第二页的测试数据"));
            book.write();
            book.close();
        } catch (jxl.read.biff.BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (jxl.write.biff.RowsExceededException e) {
            e.printStackTrace();
        } catch (jxl.write.WriteException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 单文件上传
     * @param request
     * @param path
     * @throws IOException
     */
    public List<Device> excelIn(HttpServletRequest request) throws IOException{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest  
        .getFile("file");  
        List<Device> list = readExcel(file.getInputStream());
        return list;
    }

}
