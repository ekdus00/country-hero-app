package com.example.hero.etc;

@FunctionalInterface
public interface OnButtonClickListenerOwnerStatus {
    void onButtonClickOwnerStatus(int jobId, ButtonType buttonType);
    enum ButtonType {
        MODIFY,
        DEADLINE
    }
}