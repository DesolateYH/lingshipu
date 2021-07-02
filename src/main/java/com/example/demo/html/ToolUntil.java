package com.example.demo.html;

import com.sun.management.OperatingSystemMXBean;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static com.example.demo.html.MultipartFileToFile.multipartFileToFile;

public class ToolUntil {

    /**
     * 获取操作系统名称
     *
     * @return
     */
    public static String getOsName() {
        // 操作系统
        String osName = System.getProperty("os.name");
        return osName;
    }

    /**
     * 获取系统cpu负载
     *
     * @return
     */
    public static String getSystemCpuLoad() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        double SystemCpuLoad = osmxb.getSystemCpuLoad();
        double d = SystemCpuLoad;
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(d);
    }

    /**
     * 获取jvm线程负载
     *
     * @return
     */
    public static String getProcessCpuLoad() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        double ProcessCpuLoad = osmxb.getProcessCpuLoad();
        double d = ProcessCpuLoad;
        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(d);
    }

    /**
     * 获取总的物理内存
     *
     * @return
     */
    public static long getTotalMemorySize() {
        int kb = 1024;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
        return totalMemorySize;
    }

    /**
     * 获取剩余的物理内存
     *
     * @return
     */
    public static long getFreePhysicalMemorySize() {
        int kb = 1048576;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
        return freePhysicalMemorySize;
    }

    /**
     * 获取已使用的物理内存
     *
     * @return
     */
    public static long getUsedMemory() {
        int kb = 1024;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        //已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
        return usedMemory;
    }

    /**
     * 生成订单号
     *
     * @return 1
     */
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String GetGUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String decodeUnicode(String theString) {
        char aChar;

        int len = theString.length();

        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);

            if (aChar == '\\') {
                aChar = theString.charAt(x++);

                if (aChar == 'u') {
// Read the xxxx

                    int value = 0;

                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);

                        switch (aChar) {
                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':

                            case '7':

                            case '8':

                            case '9':

                                value = (value << 4) + aChar - '0';

                                break;

                            case 'a':

                            case 'b':

                            case 'c':

                            case 'd':

                            case 'e':

                            case 'f':

                                value = (value << 4) + 10 + aChar - 'a';

                                break;

                            case 'A':

                            case 'B':

                            case 'C':

                            case 'D':

                            case 'E':

                            case 'F':

                                value = (value << 4) + 10 + aChar - 'A';

                                break;

                            default:

                                throw new IllegalArgumentException(

                                        "Malformed \\uxxxx encoding.");

                        }

                    }

                    outBuffer.append((char) value);

                } else {
                    if (aChar == 't')

                        aChar = '\t';

                    else if (aChar == 'r')

                        aChar = '\r';

                    else if (aChar == 'n')

                        aChar = '\n';

                    else if (aChar == 'f')

                        aChar = '\f';

                    outBuffer.append(aChar);

                }

            } else

                outBuffer.append(aChar);

        }

        return outBuffer.toString();

    }

    /**
     * 上传图片
     *
     * @param file1 文件
     * @return 地址
     * @throws Exception 异常
     */

    public static String uploadFile(MultipartFile file1) throws Exception {
        String url = "https://sm.ms/api/v2/upload";
        File file = multipartFileToFile(file1);
        if (file.exists()) {
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(url);

            try {
                // FilePart：用来上传文件的类,file即要上传的文件
                FilePart fp = new FilePart("smfile", file);
                Part[] parts = {fp};

                // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
                MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
                postMethod.setRequestEntity(mre);
                // 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
                client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);

                int status = client.executeMethod(postMethod);
                if (status == HttpStatus.SC_OK) {

                    // 获取返回数据
                    InputStream inputStream = postMethod.getResponseBodyAsStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer stringBuffer = new StringBuffer();
                    String str = "";
                    // 遍历返回数据
                    while ((str = br.readLine()) != null) {
                        stringBuffer.append(str);
                    }
                    String body = stringBuffer.toString();
                    JSONObject object = JSONObject.fromObject(body);
                    String flag = String.valueOf(object.get("success"));
                    if (!"true".equals(flag)) {
                        return "error";
                    }
                    JSONObject object2 = JSONObject.fromObject(object.get("data"));
                    String pic_url = String.valueOf(object2.get("url"));
                    System.out.println(pic_url);
                    return pic_url;
                }
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                // 释放连接
                postMethod.releaseConnection();
            }
        }
        return "error";
    }

    /**
     * 用字符串模拟两个大数相加
     *
     * @param n1 加数1
     * @param n2 加数2
     * @return 相加结果
     */
    public static String add2(String n1, String n2) {
        //小数加减乘除用BigDecimal
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        BigDecimal b3 = b1.add(b2);//
        return b3.toString();

    }
}
