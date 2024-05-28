package com.example.hero.etc;

@FunctionalInterface
public interface OnButtonClickListenerOwnerStatus {
    void onButtonClickWorkerStatus(int jobId, ButtonType buttonType);

    enum ButtonType {
        MODIFY,
        DEADLINE
    }
}