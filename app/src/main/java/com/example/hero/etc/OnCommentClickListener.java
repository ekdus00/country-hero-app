package com.example.hero.etc;

@FunctionalInterface
public interface OnCommentClickListener {

    void OnCommentClick(int commentId, OnCommentClickListener.ButtonType buttonType);

    enum ButtonType {
        CHILD,
        EDIT,
        DELETE
    }
//    void onChildButtonClick(int commentId);
//    void onEditButtonClick(int commentId);
//    void onDeleteButtonClick(int commentId);
}
