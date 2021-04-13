package com.sbe.zomatoapp;

class CommentHelperClass {
    String user,comment;

    public CommentHelperClass() {
    }

    public CommentHelperClass(String user, String comment) {
        this.user = user;
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
