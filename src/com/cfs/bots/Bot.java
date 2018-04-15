package com.cfs.bots;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Bot {

    public String delimiter;
    public String name;

    public final DataInputStream dis;
    public final DataOutputStream dos;

    public Bot(String delimiter, DataInputStream dis, DataOutputStream dos){
        this.delimiter = delimiter;
        this.dis = dis;
        this.dos = dos;
    }

    public abstract void run(String[] arr) throws IOException;

    public abstract String getInfo();

    @Override
    public String toString(){
        return this.name;
    }

}
