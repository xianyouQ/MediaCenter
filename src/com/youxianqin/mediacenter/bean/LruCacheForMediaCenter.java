package com.youxianqin.mediacenter.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

public class LruCacheForMediaCenter {

	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 30; 
	private static final String DISK_CACHE_SUBDIR = "thumbnails";
	private LruCache<String,Bitmap> mMemoryCache;
	private DiskLruCache mDiskLruCache;
	private Context ctx;
	private String DiskLruCachePath;
	public LruCacheForMediaCenter(Context ctx)
	{
		this.ctx=ctx;
		LruCacheUtils();
	}
	public String GetDiskLruCache(){
		return this.DiskLruCachePath;
	}

	private void LruCacheUtils() {
		
		DiskLruCachePath= getCacheDir(ctx, DISK_CACHE_SUBDIR); 
		 File cacheDir = new File(DiskLruCachePath);
		 
		    try {
		    	if (!cacheDir.exists()) {  
			        cacheDir.mkdirs();  
			    }  
		    	mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		final int MAXMEMONRY=(int)Runtime.getRuntime().maxMemory()/8;
				
        if (mMemoryCache == null)
        	mMemoryCache = new LruCache<String, Bitmap>(MAXMEMONRY) {
        	
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.getByteCount()/8;
                }

                @Override
                protected void entryRemoved(boolean evicted, String key,
                        Bitmap oldValue, Bitmap newValue) {
                    Log.v("Lrucache", "hard cache is full , push to soft cache");
                    super.entryRemoved(evicted, key, oldValue, newValue);
                }
            };
    }
	
	public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                Log.d("CacheUtils",
                        "mMemoryCache.size() " + mMemoryCache.size());
                mMemoryCache.evictAll();
                Log.d("CacheUtils", "mMemoryCache.size()" + mMemoryCache.size());
            }
            mMemoryCache = null;
        }
    }
	public void cleanDiskCache()
	{
		try {
			mDiskLruCache.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            if (key != null && bitmap != null){
                mMemoryCache.put(key, bitmap);
                try {
					DiskLruCache.Editor editor = mDiskLruCache.edit(key);
					 if (editor != null) {
				            OutputStream outputStream = editor.newOutputStream(0);
					        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);        
					        editor.commit();
					        Log.v("Lrucache", key+" "+key+" have add to diskLrucache");
				        } 
					 else {
				           editor.abort();
					     }
					 mDiskLruCache.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
         
            }
        } 
           
    }
    
   
    public synchronized void addBitmapToMemoryCache(Video mVideo,Bitmap bitmap)
    {
    	String cacheKey = mVideo.GetVideoScriptPicUrl();
    	if (mMemoryCache.get(cacheKey) != null){
    		removeImageCache(cacheKey);	
    	}
    	addBitmapToMemoryCache(cacheKey,bitmap);

	
    	
    }
    


    public synchronized Bitmap getBitmapFromMemCache(String key) {
   
        Bitmap bm = mMemoryCache.get(key);
        if (key != null&&bm!=null) {
            return bm;
        }
        else {
        	try{
        	DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
        	  if (snapShot != null) {
        	    InputStream is = snapShot.getInputStream(0);
        	    Bitmap bitmap = BitmapFactory.decodeStream(is);
        	    if(key != null && bitmap != null)
        	    mMemoryCache.put(key, bitmap);
        	    return bitmap;
        	  }
        	} catch (IOException e) {
        	  e.printStackTrace();
        	}
        }
		return null;
    }

    /**
     * 移除缓存
     * 
     * @param key
     * @throws IOException 
     */
    public synchronized void removeImageCache(String key)  {
        if (key != null) {
            if (mMemoryCache != null && mDiskLruCache !=null) {
                Bitmap bm = mMemoryCache.remove(key);
                try {
					mDiskLruCache.remove(key);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                if (bm != null)
                    bm.recycle();
            }
        }
    }
    public static String getCacheDir(Context context, String uniqueName) { 
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir 
        // otherwise use internal cache dir 
        final String cachePath = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED 
                || !Environment.isExternalStorageRemovable() ? 
                        context.getExternalCacheDir().getPath() : context.getCacheDir().getPath(); 
         System.out.println("CachePath "+cachePath);                     
        return (cachePath + File.separator + uniqueName); 
    }

    public void close()
    {
    	try {
			mDiskLruCache.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}