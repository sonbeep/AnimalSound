package com.example.animalsound;

public interface OnMainCallback {
    default void callback(String key, Object data){
    }
    void showFragment(String tag, Object data, boolean isBack);
    void makeBackArrow(String typeAnimal);

    void disableDrawer();

    void enableDrawer();
}
