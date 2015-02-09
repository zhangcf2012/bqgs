package com.libs.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import com.wyxz.chaogao.app.Constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

public class BitmapUtil {
    private static final String TAG = "BitmapUtil";
    
    /**
     * 图片旋转的参数
     */
    public static final int ROTATE_TYPE_NONE = 0;
    public static final int ROTATE_TYPE_LEFT = 1;
    public static final int ROTATE_TYPE_RIGHT = 2;
    public static final int ROTATE_TYPE_180_DEGREES = 3;
    
    /**
     * 按指定的“图片旋转参数”，旋转Bitmap。 Notice：执行此方法后，仍需要释放源Bitmap的内存。
     * 
     * @param original 源Bitmap
     * @param rotateType 图片旋转参数
     * @return
     */
    
    public static Bitmap rotateBitmap(Bitmap original, int rotateType) {
        LogUtil.v(TAG, "rotate bitmap , type = " + rotateType);
        if (original == null || original.isRecycled()) {
            return null;
        }
        
        float degrees = 0f;
        switch (rotateType) {
            case ROTATE_TYPE_LEFT:
            degrees = -90f;
            break;
            
            case ROTATE_TYPE_RIGHT:
            degrees = 90f;
            break;
            
            case ROTATE_TYPE_180_DEGREES:
            degrees = 180f;
            break;
            
            default:
            // error, should not go here
            LogUtil.e(TAG, "error , rotate bitmap , type = " + rotateType);
            break;
        }
        
        return rotate(original, degrees);
        
    }
    
    private static Bitmap rotate(Bitmap original, float degrees) {
        if (original == null || degrees == 0) {
            return original;
        }
        
        int width = original.getWidth();
        int height = original.getHeight();
        
        Matrix rotateMatrix = new Matrix();
        rotateMatrix.postRotate(degrees);
        
        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, rotateMatrix, true);
        
        /*
         * if (rotatedBitmap != original) { original.recycle(); }
         */
        return rotatedBitmap;
    }
    
    /**
     * 回收不用的bitmap
     * 
     * @param b
     */
    public static void recycleBitmap(Bitmap b) {
        LogUtil.i(TAG, "recycleBitmap , Bitmap =  " + b);
        if (b != null && !b.isRecycled()) {
            b.recycle();
            b = null;
        }
    }
    
    public static boolean saveBitmapToFile(Bitmap bitmap, String path, Bitmap.CompressFormat format) {
        boolean ret = false;
        File file = new File(path);
        if (!bitmap.isRecycled()) {
            try {
                ret = bitmap.compress(format, 80, new FileOutputStream(file, false));
            } catch (Exception e) {
                LogUtil.e(TAG, "saveBitmapToFile Exception , " + e.toString());
            }
            if (!ret) {
                file.delete();
            }
        }
        return ret;
    }
    
    /**
     * 
     * @param srcPath
     * @return
     */
    /**
     * 图片按指定宽度、指定大小的压缩方法（根据路径获取图片并压缩）。
     * 
     * @param srcPath 图片的地址
     * @param width 图片指定宽度。如果为负值，表示不压缩
     * @param maxsize 指定的图片大小 ，单位K bytes
     * @return Bitmap对象
     */
    public static Bitmap getCompressBitmap(final String srcPath, final float width, final float maxsize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        float ww = 0f;// 这里是设置的固定宽度
        if (width > 0) {
            ww = width;
        } else {
            ww = w;
        }
        
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > ww) {
            be = (int) (newOpts.outWidth / ww);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressBitmap(bitmap, maxsize);// 压缩好比例大小后再进行质量压缩
    }
    
    /**
     * 压缩图片，使其大小在maxsize k 以下。如果压缩失败，则返回原图。 Notice：执行成功，原Bitmap会被释放。
     * 
     * @param image
     * @return
     * 
     */
    private static Bitmap compressBitmap(Bitmap image, final float maxsize) {
        if (image == null) {
            return null;
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (maxsize > 0 && (baos.toByteArray().length / 1024 > maxsize)) { // 循环判断如果压缩后图片是否大于maxsize,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        
        if (bitmap != null) {
            recycleBitmap(image);
            return bitmap;
        }
        return image;
    }
    
    /**
     * 截取屏幕
     * 
     * @param activity
     * @return
     */
    public static Bitmap takeScreenShot(Activity activity, int marginLeft, int marginTop, int cutWidth, int cutHeight) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Bitmap b = Bitmap.createBitmap(b1, marginLeft, marginTop, cutWidth, cutHeight);
        view.destroyDrawingCache();
        return b;
    }
    
    public static Bitmap getCompressBitmapNew(final String srcPath, final float width, final long maxsize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        // newOpts.inJustDecodeBounds = true;
        // Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//
        // 此时返回bm为空
        Bitmap bitmap = null;
        newOpts.inJustDecodeBounds = false;
        // int w = newOpts.outWidth;
        // int h = newOpts.outHeight;
        // float ww = 0f;// 这里是设置的固定宽度
        // if (width > 0) {
        // ww = width;
        // } else {
        // ww = w;
        // }
        
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        long filesize = getFileSize(srcPath);
        long maxSizeBit = maxsize * 1024;
        if (filesize > maxSizeBit) {
            be = (int) (filesize / maxSizeBit);
        }
        // if (w > ww) {
        // be = (int) (newOpts.outWidth / ww);
        // }
        // if (be <= 0)
        // be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressBitmap(bitmap, maxsize);// 0缩好比例大小后再进行质量压缩
    }
    
    /**
     * 
     * 
     * 
     * 读取文件大小的计算
     */
    
    public static long getFileSize(String path) {
        long fileSize = 0;
        File f = new File(path);
        fileSize = f.length();
        // try {
        // FileInputStream fis = new FileInputStream(f);
        // try {
        // fileSize = fis.available();// 获取文件大小
        // } catch (IOException e1) {
        // e1.printStackTrace();
        // }
        //
        // } catch (FileNotFoundException e2) {
        // e2.printStackTrace();
        // }
        return fileSize;
    }
    
    /**
     * 得到一个小点的图片
     * 
     * @param filePath
     * @param sampleSize
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int sampleSize) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            LogUtil.d(TAG, "decode file exception : " + e.getMessage());
        }
        return null;
    }
    
    public static Bitmap bmpToByteArray(final String pic, final boolean needRecycle) {
        if (TextUtils.isEmpty(pic)) {
            return null;
        }
        Bitmap thumb = null;
        // String fileName = pic.substring(pic.lastIndexOf("/") + 1);
        // String filePath = "";
        File file = null;
        if (pic.startsWith("http")) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = new File(Constants.IMAGE_CACHE_DIR, String.valueOf(pic.hashCode()));
            } else
                return null;
            
            if (file.exists())//
                thumb = getFileBitmap(file.getAbsolutePath());
            else {
                
                HttpURLConnection conn;
                try {
                    conn = (HttpURLConnection) new URL(pic).openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    FileOutputStream os = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                    is.close();
                    os.close();
                    thumb = getFileBitmap(file.getAbsolutePath());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    
                    e.printStackTrace();
                } catch (Exception e) {
                    
                }
            }
        } else
            thumb = getFileBitmap(pic);
        if (thumb == null) {
            return null;
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 100, 100, true);
        thumb.recycle();
        // ByteArrayOutputStream output = new ByteArrayOutputStream();
        // thumbBmp.compress(CompressFormat.JPEG, 50, output);
        // if (needRecycle) {
        // thumbBmp.recycle();
        // }
        //
        // byte[] result = output.toByteArray();
        // try {
        // output.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        
        return thumbBmp;
    }
    
    public static Bitmap getFileBitmap(String imgpath) {
        if (TextUtils.isEmpty(imgpath)) {
            return null;
        }
        Bitmap bit = null;
        try {
            bit = BitmapFactory.decodeFile(imgpath);
        } catch (OutOfMemoryError err) {
            // LetvApplication.getContext().clearStanderMemory();
            System.gc();
        }
        
        return bit;
    }
    
    public static byte[] BitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 95, baos);
        return baos.toByteArray();
    }
    
    public static byte[] BitmapToBytesNoCompress(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    
    public static Bitmap scaleBigBitmap(Bitmap bitmap, Context context) {
     // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
        int newWidth = 320;
        int newHeight = 320;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
//        Matrix matrix = new Matrix();
//        matrix.postScale(2.5f, 2.5f); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
    
    private static Bitmap scaleSmallBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.8f, 0.8f); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
    
}
