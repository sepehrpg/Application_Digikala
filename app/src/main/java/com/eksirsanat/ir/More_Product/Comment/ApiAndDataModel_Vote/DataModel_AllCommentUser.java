package com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote;

public class DataModel_AllCommentUser {

    String idVote;
    String idUser;
    double stars;
    String name;
    String mosbat;
    String manfi;
    String comment;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdVote() {
        return idVote;
    }

    public void setIdVote(String idVote) {
        this.idVote = idVote;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMosbat() {
        return mosbat;
    }

    public void setMosbat(String mosbat) {
        this.mosbat = mosbat;
    }

    public String getManfi() {
        return manfi;
    }

    public void setManfi(String manfi) {
        this.manfi = manfi;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
