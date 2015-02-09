package com.libs.network;

public interface OnRequestListener {
    
     void loadDataSuccess(String result);
    
     void loadDataError(Throwable t);
}
