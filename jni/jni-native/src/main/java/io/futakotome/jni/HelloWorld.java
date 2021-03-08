package io.futakotome.jni;

public class HelloWorld {

    public native void sayHello();

    static {
        System.loadLibrary("libhello");
    }

    public static void main(String[] args) {
        new HelloWorld().sayHello();
    }

}
