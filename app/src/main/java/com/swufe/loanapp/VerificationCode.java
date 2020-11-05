package com.swufe.loanapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class VerificationCode {
//    随机数数组，去除了易混淆的0和o O；1和i I l L；6和b；9和q；c C和G；t T
    private static final char[] CHARS = {
            '2', '3', '4', '5',  '7', '8',
            'a',  'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p',  'r', 's',  'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B',  'D', 'E', 'F',  'H',  'J', 'K', 'M',
            'N', 'P', 'Q', 'R', 'S', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static VerificationCode code;

//    用于直接调用创建VerificationCode对象
    public static VerificationCode getInstance() {
        if(code == null)
            code = new VerificationCode();
        return code;
    }

//    默认值，方便修改
//    验证码默认随机数的个数
    private static final int DEFAULT_CODE_LENGTH = 4;
//    默认字体大小
    private static final int DEFAULT_FONT_SIZE = 25;
//    默认线条的条数
    private static final int DEFAULT_LINE_NUMBER = 5;
//    padding值
    private static final int BASE_PADDING_LEFT = 10, RANGE_PADDING_LEFT = 15, BASE_PADDING_TOP = 15, RANGE_PADDING_TOP = 20;
//    验证码的默认宽高
    private static final int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 40;

//    settings decided by the layout xml
//    canvas的宽、高的默认值
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;
//    要随机生成的padding_left和pading_top的默认值
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;
//    将验证码中的个数、线条的条数、字体大小设置默认值
    private int codeLength = DEFAULT_CODE_LENGTH, line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

    private String codestr;
    private int padding_left, padding_top;
    private Random random = new Random();

//    用位图Bitmap+Canvas画出验证码图片
    public Bitmap createBitmap() {
        padding_left = 0;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        codestr = createCode();

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();//画笔
        paint.setAntiAlias(true);//抗锯齿，防止验证码太模糊
        paint.setTextSize(font_size);
//        画验证码
        for (int i = 0; i < codestr.length(); i++) {
            randomTextStyle(paint);
            randomPadding();//随机padding_left、padding_top
            canvas.drawText(codestr.charAt(i) + "", padding_left, padding_top, paint);//绘制文本
        }
//        画线条
        for (int i = 0; i < line_number; i++) {
            drawLine(canvas, paint);
        }
        canvas.save();//保存Canvas的状态
        canvas.restore();//将画布恢复至canvas.save()时的状态
        return bitmap;
    }

    public String getCode() {
        return codestr;
    }

//    生成codeLength个[0,CHARS.length)间的验证码加入StringBuilder，转化为string
    private String createCode() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {//生成4个
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }
//    画随机起始端点的干扰线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);//宽度
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
//    生成随机颜色
    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }
//    文字样式，颜色，粗细，倾斜度 随机
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());//true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
        paint.setUnderlineText(true); //true为下划线，false为非下划线
        paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }
//    随机生成padding值
    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }
}