package com.xiaochui.defaultproject.utils.glideConfiguration;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;


/**
 * @author Death丶Love
 * @project_name XiaoChuiProject
 * @package_name com.xiaochui.mainlibrary.optionConfiguration.glideConfiguration
 * @time 2017/7/25 9:15
 * @remark
 */
public class GlideCacheModel implements GlideModule {
//    public static final String GlideCachePatch = Environment.getExternalStorageDirectory().getPath() + "/cache/GlideCache";
    // 图片缓存最大容量，150M，根据自己的需求进行修改
//    public static final int GLIDE_CATCH_SIZE = 150 * 1000 * 1000;

    // 图片缓存子目录
//    public static final String GLIDE_CARCH_DIR = "image_catch";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));


        //存放在外置文件浏览器
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "Glide", diskCacheSize));
//        builder.setDiskCache(new DiskCache.Factory() {
//            @Override
//            public DiskCache build() {
//                File file = new File(GlideCachePatch);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
////
//                int diskCacheSize = 1024 * 1024 * 50;//最多可以缓存多少字节的数据
//                return DiskLruCacheWrapper.get(file, diskCacheSize);
////                return null;
//            }
//        });
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                GlideCatchConfig.GLIDE_CARCH_DIR,
                GlideCatchConfig.GLIDE_CATCH_SIZE));
        //设置图片解码格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));

//        builder.setDiskCacheService(ExecutorService service);
//        builder.setResizeService(ExecutorService service);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}