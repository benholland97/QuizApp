package bh.h63jav.quizapp;

class Category {

    private String title,summary,imgText;

    Category(String title, String summary, String imgTxt) {
        this.title = title;
        this.summary = summary;
        this.imgText = imgTxt;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getSummary() {
        return summary;
    }

    void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }
}
