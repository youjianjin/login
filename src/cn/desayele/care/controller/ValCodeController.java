package cn.desayele.care.controller;

import cn.desayele.care.util.ResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/ValCode")
public class ValCodeController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    /**
     * 干扰线小点点的个数，宽，高，干扰线长度
     */
    private static final int count = 400;
    private static final int width = 100;
    private static final int height = 32;
    private static final int lineWidth = 1;

    @RequestMapping(value = "/generate", method = {RequestMethod.POST, RequestMethod.GET})
    public void VerificationCode(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws ServletException, IOException {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 在内存中创建图象
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 设定背景颜色
        graphics.setColor(Color.WHITE); // ---1.Color.WHITE为白色
        graphics.fillRect(0, 0, width, height);//填充衍射
        // 设定边框颜色
        graphics.drawRect(0, 0, width - 1, height - 1);
        final Random random = new Random();
        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < count; i++) {
            graphics.setColor(getRandColor(150, 200)); // ---3.
            final int x = random.nextInt(width - lineWidth - 1) + 1; // 保证画在边框之内
            final int y = random.nextInt(height - lineWidth - 1) + 1;
            final int xl = random.nextInt(lineWidth);
            final int yl = random.nextInt(lineWidth);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(4位数字)
        final String resultCode = exctractRandCode();
        for (int i = 0; i < resultCode.length(); i++) {
            // 设置字体颜色
            graphics.setColor(getRandColor(150, 200));
            // 设置字体样式
            graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
            // 设置字符，字符间距，上边距
            graphics.drawString(String.valueOf(resultCode.charAt(i)), (23 * i) + 8, 26);
        }
        System.out.println("验证码：" + resultCode);
        // 将认证码存入SESSION
        httpSession.setAttribute("randCode",resultCode);
        // 图象生效
        graphics.dispose();
        // 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * @return 随机码
     */
    private String exctractRandCode() {
        final String randCodeType = ResourceUtil.getRandCodeType();
        int randCodeLength = Integer.parseInt(ResourceUtil.getRandCodeLength());
        if (randCodeType == null) {
            return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
        } else {
            switch (randCodeType.charAt(0)) {
                case '1':
                    return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
                case '2':
                    return RandCodeImageEnum.LOWER_CHAR.generateStr(randCodeLength);
                case '3':
                    return RandCodeImageEnum.UPPER_CHAR.generateStr(randCodeLength);
                case '4':
                    return RandCodeImageEnum.LETTER_CHAR.generateStr(randCodeLength);
                case '5':
                    return RandCodeImageEnum.ALL_CHAR.generateStr(randCodeLength);
                default:
                    return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
            }
        }
    }

    /**
     * 描述：根据给定的数字生成不同的颜色
     *
     * param 这是以数字型来设置颜色，颜色模式是指使用三种基色：红、绿、蓝，通过三种颜色的调整得出其它各种颜色，这三种基色的值范围为0～255
     * param 这是以数字型来设置颜色，颜色模式是指使用三种基色：红、绿、蓝，通过三种颜色的调整得出其它各种颜色，这三种基色的值范围为0～255
     * return 描述：返回颜色
     */
    private Color getRandColor(int fc, int bc) { // 取得给定范围随机颜色
        final Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        final int r = fc + random.nextInt(bc - fc);
        final int g = fc + random.nextInt(bc - fc);
        final int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 验证码辅助类
     *
     * @author 张国明 guomingzhang2008@gmail.com <br/>
     *         2012-2-28 下午2:15:14
     */
    enum RandCodeImageEnum {
        /**
         * 混合字符串
         */
        ALL_CHAR("0123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), // 去除小写的l和o这个两个不容易区分的字符；
        /**
         * 字符
         */
        LETTER_CHAR("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        /**
         * 小写字母
         */
        LOWER_CHAR("abcdefghijklmnopqrstuvwxyz"),
        /**
         * 数字
         */
        NUMBER_CHAR("0123456789"),
        /**
         * 大写字符
         */
        UPPER_CHAR("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        /**
         * 待生成的字符串
         */
        private String charStr;

        /**
         * @param charStr
         */
        RandCodeImageEnum(final String charStr) {
            this.charStr = charStr;
        }

        /**
         * 生产随机验证码
         *
         * @param codeLength 验证码的长度
         * @return 验证码
         */
        public String generateStr(final int codeLength) {
            final StringBuffer sb = new StringBuffer();
            final Random random = new Random();
            final String sourseStr = getCharStr();
            for (int i = 0; i < codeLength; i++) {
                sb.append(sourseStr.charAt(random.nextInt(sourseStr.length())));
            }
            return sb.toString();
        }

        /**
         * @return the {@link #charStr}
         */
        public String getCharStr() {
            return charStr;
        }

    }

}