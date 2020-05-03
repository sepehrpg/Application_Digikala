package com.eksirsanat.ir.Property_Header;

public class ChildModel implements Section {

    String child;
    private int section;


    public void setInfo(String info) {
        this.info = info;
    }

    String info;

    public ChildModel(int section) {
        this.section = section;
    }

    public void setChild(String child) {
        this.child = child;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getName() {
        return child;
    }

    @Override
    public String getinfo() {
        return info;
    }

    @Override
    public int sectionPosition() {
        return section;
    }
}