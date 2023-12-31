package com.my.springcloud.service;

import com.my.springcloud.util.PDFDataMemo;
import com.my.springcloud.util.PDFTemplateUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PDFService {

    public void exportPdf(HttpServletResponse response,String docName) throws Exception{
        ByteArrayOutputStream baos = null;
        OutputStream out = null;
        try {
            // 模板中的数据，实际运用从数据库中查询
            Map<String,Object> data = new HashMap<>();
            data.put("curr", 1);
            data.put("one", 2);
            data.put("two", 1);
            data.put("three", 6);

            List<PDFDataMemo> detailList = new ArrayList<>();
            detailList.add(new PDFDataMemo(123456,"测试","测试","测试","测试"));
            detailList.add(new PDFDataMemo(111111,"测试","测试","测试","测试"));
            detailList.add(new PDFDataMemo(222222,"测试","测试","测试","测试"));
            data.put("detailList", detailList);

            baos = PDFTemplateUtil.createPDF(data, "pdf.ftl");;
            // 设置响应消息头，告诉浏览器当前响应是一个下载文件
            response.setContentType( "application/x-msdownload");
            // 告诉浏览器，当前响应数据要求用户干预保存到文件中，以及文件名是什么 如果文件名有中文，必须URL编码
            String fileName = URLEncoder.encode(docName+".pdf", "UTF-8");
            response.setHeader( "Content-Disposition", "attachment;filename=" + fileName);
            out = response.getOutputStream();
            baos.writeTo(out);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("导出失败：" + e.getMessage());
        } finally{
            if(baos != null){
                baos.close();
            }
            if(out != null){
                out.close();
            }
        }
    }
}
