package com.taorusb.consolecrundusesjdbc.commandhandler;

public enum Warehouse {

    ERROR(0), SELECT(1), INSERT(2),
    UPDATE(3), DELETE(4), WRITER(8), POST(9),
    REGION(10), WRITERS(11), POSTS(12), REGIONS(13);

    private int requestNum;

    Warehouse(int requestNum) {
        this.requestNum = requestNum;
    }

    public int getNum() {
        return requestNum;
    }

}