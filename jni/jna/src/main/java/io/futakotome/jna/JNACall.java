package io.futakotome.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.win32.StdCallLibrary;

public class JNACall {
    public interface StdCallDll extends StdCallLibrary {
        StdCallDll INSTANCE = Native.load(Platform.isWindows() ? "msvcrt" : "c", StdCallDll.class);

        void printf(String format, Object... args);
    }

    public interface CcallDll extends Library {
        CcallDll INSTANCE = Native.load("C:\\Users\\Administrator\\Desktop\\microservice-support\\jni\\jni-native\\lib\\libhello", CcallDll.class);

        void Java_io_futakotome_jni_HelloWorld_sayHello();
    }

    public static void main(String[] args) {
        StdCallDll.INSTANCE.printf("hello world jna!\n");
        CcallDll.INSTANCE.Java_io_futakotome_jni_HelloWorld_sayHello();
    }
}
