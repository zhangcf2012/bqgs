package com.libs.share;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * 授权成功后 存储 第三方平台 token等信息的类
 */
public class AuthSharePreference {
    private static final String AUTH_SHARED_PREFERENCES_NAME = "auth_succ_sp";
    private final Context mContext;
    private SharedPreferences mGlobalPreferences;
    /** sina微博 Token **/
    public final StringPreference SINA_WEIBO_EXPIRES_IN = new StringPreference("SINA_WEIBO_EXPIRES_IN", "");
    public final StringPreference SINA_WEIBO_ACCESS_TOKEN = new StringPreference("SINA_WEIBO_ACCESS_TOKEN", "");
    public final StringPreference SINA_WEIBO_UID = new StringPreference("SINA_WEIBO_UID", "");
    public final StringPreference SINA_WEIBO_REMIND_IN = new StringPreference("SINA_WEIBO_REMIND_IN", "");
    /** tecent微博 Token **/
    public final StringPreference TECENT_WEIBO_EXPIRES_IN = new StringPreference("TECENT_WEIBO_EXPIRES_IN", "");
    public final StringPreference TECENT_WEIBO_ACCESS_TOKEN = new StringPreference("TECENT_WEIBO_ACCESS_TOKEN", "");
    public final StringPreference TECENT_WEIBO_UID = new StringPreference("TECENT_WEIBO_UID", "");
    public final StringPreference TECENT_WEIBO_REMIND_IN = new StringPreference("TECENT_WEIBO_REMIND_IN", "");
    /** qq Token **/
    public final StringPreference QQ_EXPIRES_IN = new StringPreference("QQ_EXPIRES_IN", "");
    public final StringPreference QQ_ACCESS_TOKEN = new StringPreference("QQ_ACCESS_TOKEN", "");
    public final StringPreference QQ_UID = new StringPreference("QQ_UID", "");
    public final StringPreference QQ_REMIND_IN = new StringPreference("QQ_REMIND_IN", "");
    public AuthSharePreference(Context context){
        this.mContext = context;
        mGlobalPreferences = context.getSharedPreferences(AUTH_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
    
    /** 抽象通用sp基类**/
    public abstract class CommonPreference<T> {
        private final String id;
        private T defaultValue;
        
        public CommonPreference(String id, T defaultValue) {
            this.id = id;
            this.defaultValue = defaultValue;
        }
        
        protected T getDefaultValue() {
            return defaultValue;
        }
        
        public abstract T getValue();
        
        public abstract boolean setValue(T val);
        
        public String getId() {
            return id;
        }
        
        /** 重置为初始值 */
        public void resetToDefault() {
            setValue(getDefaultValue());
        }
    }
    
    /** 整型数值参数保存 */
    public class IntPreference extends CommonPreference<Integer> {
        public IntPreference(String id, int defaultValue) {
            super(id, defaultValue);
        }
        
        @Override
        public Integer getValue() {
            return mGlobalPreferences.getInt(getId(), getDefaultValue());
        }
        
        @Override
        public boolean setValue(Integer val) {
            return mGlobalPreferences.edit().putInt(getId(), val).commit();
        }
    }
    
    /** String参数保存 */
    public class StringPreference extends CommonPreference<String> {
        
        public StringPreference(String id, String defaultValue) {
            super(id, defaultValue);
        }
        
        @Override
        public String getValue() {
            return mGlobalPreferences.getString(getId(), getDefaultValue());
        }
        
        @Override
        public boolean setValue(String val) {
            return mGlobalPreferences.edit().putString(getId(), val).commit();
        }
    }
    
    public void clearAll(){
        this.SINA_WEIBO_ACCESS_TOKEN.resetToDefault();
        this.TECENT_WEIBO_ACCESS_TOKEN.resetToDefault();
        this.QQ_ACCESS_TOKEN.resetToDefault();
    }
}
