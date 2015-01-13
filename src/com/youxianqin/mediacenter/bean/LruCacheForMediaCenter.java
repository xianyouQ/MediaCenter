package com.youxianqin.mediacenter.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

public class LruCacheForMediaCenter {

	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 30; // 10MB 
	private static final String DISK_CACHE_SUBDIR = "thumbnails";
	private LruCache<String,Bitmap> mMemoryCache;
	private DiskLruCache mDiskLruCache;
	private Context ctx;
	public LruCacheForMediaCenter(Context ctx)
	{
		this.ctx=ctx;
		LruCacheUtils();
	}
	private void LruCacheUtils() {
		
		
		 File cacheDir = getCacheDir(ctx, DISK_CACHE_SUBDIR); 
		 
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
		
		System.out.println("分配的空间 "+MAXMEMONRY+"  "+Runtime.getRuntime().maxMemory());
		
		
		
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
    	String cacheKey = hashKeyForCache(key);
        if (mMemoryCache.get(cacheKey) == null) {
            if (key != null && bitmap != null){
                mMemoryCache.put(cacheKey, bitmap);
                try {
					DiskLruCache.Editor editor = mDiskLruCache.edit(cacheKey);
					 if (editor != null) {
				            OutputStream outputStream = editor.newOutputStream(0);
					        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);        
					        editor.commit();
					        Log.v("Lrucache", key+" "+cacheKey+" have add to diskLrucache");
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

    public synchronized Bitmap getBitmapFromMemCache(String key) {
    	String cacheKey = hashKeyForCache(key);
        Bitmap bm = mMemoryCache.get(cacheKey);
        if (key != null&&bm!=null) {
            return bm;
        }
        else {
        	try{
        	DiskLruCache.Snapshot snapShot = mDiskLruCache.get(cacheKey);
        	  if (snapShot != null) {
        	    InputStream is = snapShot.getInputStream(0);
        	    Bitmap bitmap = BitmapFactory.decodeStream(is);
        	    if(key != null && bitmap != null)
        	    mMemoryCache.put(cacheKey, bitmap);
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
     */
    public synchronized void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }
    public static File getCacheDir(Context context, String uniqueName) { 
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir 
        // otherwise use internal cache dir 
        final String cachePath = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED 
                || !Environment.isExternalStorageRemovable() ? 
                        context.getExternalCacheDir().getPath() : context.getCacheDir().getPath(); 
         System.out.println("CachePath "+cachePath);                     
        return new File(cachePath + File.separator + uniqueName); 
    }
    private String bytesToHexString(byte[] bytes) {
    	  StringBuilder sb = new StringBuilder();
    	  for (int i = 0; i < bytes.length; i++) {
    	    String hex = Integer.toHexString(0xFF & bytes[i]);
    	    if (hex.length() == 1) {
    	      sb.append('0');
    	    }
    	    sb.append(hex);
    	  }
    	  return sb.toString();
    	}
    
    public String hashKeyForCache(String key) {
    	  String cacheKey;
    	  try {
    	    final MessageDigest mDigest = MessageDigest.getInstance("MD5");
    	    mDigest.update(key.getBytes());
    	    cacheKey = bytesToHexString(mDigest.digest());
    	  } catch (NoSuchAlgorithmException e) {
    	    cacheKey = String.valueOf(key.hashCode());
    	  }
    	  return cacheKey;
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