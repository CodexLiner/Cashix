package com.cashix.database;

public class bankDataModel {
    int COLUMN_ID;
    String   ACCOUNT ,NAME ,IFSC;

    public bankDataModel(int COLUMN_ID, String ACCOUNT, String NAME, String IFSC) {
        this.COLUMN_ID = COLUMN_ID;
        this.ACCOUNT = ACCOUNT;
        this.NAME = NAME;
        this.IFSC = IFSC;
    }

    public int getCOLUMN_ID() {
        return COLUMN_ID;
    }

    public void setCOLUMN_ID(int COLUMN_ID) {
        this.COLUMN_ID = COLUMN_ID;
    }

    public String getACCOUNT() {
        return ACCOUNT;
    }

    public void setACCOUNT(String ACCOUNT) {
        this.ACCOUNT = ACCOUNT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    @Override
    public String toString() {
        return "bankDataModel{" +
                "COLUMN_ID=" + COLUMN_ID +
                ", ACCOUNT='" + ACCOUNT + '\'' +
                ", NAME='" + NAME + '\'' +
                ", IFSC='" + IFSC + '\'' +
                '}';
    }
}
