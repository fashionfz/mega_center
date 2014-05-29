package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.licence.MachineNo;
import com.common.server.AppLicenceUtil;
import com.dto.LicenceDTO;
import com.dto.QueryDeviceDTO;
import com.po.Device;
import com.po.WorkBook;
import com.service.IDeviceService;
import com.service.IWorkBookService;
import com.util.ExcelUtil;
import com.util.FileUtil;

@Controller
public class CommonController {

    @Autowired
    private IDeviceService deviceService;
    
    @Autowired
    private IWorkBookService workBookService;
    
    @RequestMapping("/test.do")
    public void inputOrOutBody(InputStream requestBodyIn, OutputStream responseBodyOut)
    throws IOException {
        responseBodyOut.write("success".getBytes());
    }
    
    @RequestMapping(value="/upload.do", method = RequestMethod.POST)
    public String upload(HttpServletRequest request,HttpServletResponse response){
        String name ="";
        try {
            name =FileUtil.uploadSingleFile(request,null,"licence",FileUtil.RELATIVELY_PATH);
            request.setAttribute("fileName", name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "uploading";
    }
    
    @RequestMapping(value="/excelIn.do", method = RequestMethod.POST)
    public String excelIn(HttpServletRequest request,HttpServletResponse response){
        ExcelUtil util = new ExcelUtil();
        int result =0;
        try {
            List<Device> list = util.excelIn(request);
            deviceService.create(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result ==0)
            return "uploading";
        else
            return "error";
    }
    
    @RequestMapping(value="/download.do")
    public void download(HttpServletRequest request,HttpServletResponse response){
        String url="";
        try {
            url = new String(request.getParameter("url").getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
                FileUtil.download(request, response, url, FileUtil.RELATIVELY_PATH);
        } catch (Exception e) {

            
        }
    }
    
    
    @RequestMapping(value="/queryFile")
    public String queryFile(HttpServletRequest request,HttpServletResponse response){
        List<String> names = FileUtil.loadFiles(request, "file");
        request.setAttribute("names", names);
        return "download";
            
    }
    
    @RequestMapping(value="/queryLicence")
    public @ResponseBody LicenceDTO queryMachineNo(){
        LicenceDTO dto = new LicenceDTO();
        dto.setMachineCode(MachineNo.getMachineCode().toUpperCase());
        try {
            Map map = AppLicenceUtil.getLicence();
            dto.setExpDate(String.valueOf(map.get("expireDate")));
            dto.setUserCount(String.valueOf(map.get("userCount")));
            dto.setDeviceCount(String.valueOf(map.get("videoCount")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
    
    
    @RequestMapping(value="/deviceExcelOut.do")
    public String excelOut(HttpServletRequest request,HttpServletResponse response)
    {
        QueryDeviceDTO dto = new QueryDeviceDTO();
        String dvrName = request.getParameter("dvrName");
        dto.setDvrName(dvrName);
        String dvrIp = request.getParameter("dvrIp");
        dto.setDvrIp(dvrIp);
        String dvrUserName = request.getParameter("dvrUserName");
        dto.setDvrUserName(dvrUserName);
        String dvrPassword = request.getParameter("dvrPassword");
        dto.setDvrPassword(dvrPassword);
        String type = request.getParameter("type");
        dto.setType(type);
        
        String fileName="device.xls";
        
        List<WorkBook> deviceField = workBookService.query("device_field");
        String[] Title=new String[deviceField.size()+1];
        Title[0]="序号";
        for(int i=0;i<deviceField.size();i++){
            Title[i+1]=deviceField.get(i).getValue();
        }
        List<Device> listContent = deviceService.query(dto,1,1000);
        
        String result="系统提示：Excel文件导出成功！"; 

        try {
            OutputStream os = response.getOutputStream();// 取得输出流     
            response.reset();// 清空输出流     
            response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1"));
            // 设定输出文件头     
            response.setContentType("application/msexcel");// 定义输出类型 
            /** **********创建工作簿************ */
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            /** **********创建工作表************ */
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);
            /** **********设置纵横打印（默认为纵打）、打印纸***************** */
            jxl.SheetSettings sheetset = sheet.getSettings();
            sheetset.setProtected(false);
            /** ************设置单元格字体************** */
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
            /** ************以下设置三种单元格样式，灵活备用************ */
            // 用于标题居中
            WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_center.setWrap(false); // 文字是否换行
            // 用于正文居左
            WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
            wcf_left.setWrap(false); // 文字是否换行  
            /** ***************以下是EXCEL开头大标题，暂时省略********************* */
            //sheet.mergeCells(0, 0, colWidth, 0);
            //sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
            /** ***************以下是EXCEL第一行列标题********************* */
            for (int i = 0; i < Title.length; i++) {
                sheet.addCell(new Label(i, 0,Title[i],wcf_center));
            }  
            /** ***************以下是EXCEL正文数据********************* */
            Field[] fields=null;
            int i=1;
            for(Object obj:listContent)
            {
                fields=obj.getClass().getDeclaredFields();
                int j=0;
                for(Field v:fields)
                {
                    v.setAccessible(true);
                    Object va=v.get(obj);
                    if(va==null){
                        va="";
                    }
                    sheet.addCell(new Label(j, i,va.toString(),wcf_left));
                    j++;
                }
                i++;
            }
            /** **********将以上缓存中的内容写到EXCEL文件中******** */
            workbook.write();
            /** *********关闭文件************* */
            workbook.close();  
        } catch (Exception e) {
            result="系统提示：Excel文件导出失败，原因："+ e.toString();
            e.printStackTrace();
        }
        return "uploading";

    }
}
