package com.example.hero.etc;

public interface OnCommentClickListener {

    void OnCommentClick(int commentId, OnCommentClickListener.ButtonType buttonType);

    enum ButtonType {
        CHILD,
        EDIT,
        DELETE
    }
}
