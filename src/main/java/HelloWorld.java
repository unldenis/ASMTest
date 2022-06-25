import org.objectweb.asm.*;

import java.io.*;

import static org.objectweb.asm.Opcodes.*;

public class HelloWorld {

    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[] { "pkg/Mesurable" });
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, -1).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, 0).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, 1).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT,
                "compareTo",
                "(Ljava/lang/Object;)I",
                null, null)
                .visitEnd();
        cw.visitEnd();

        byte[] b = cw.toByteArray();
        Class<?> c = new DynamicClassLoader().define("pkg.Comparable", b);


        try(FileOutputStream o = new FileOutputStream(c.getSimpleName() + ".class")) {
            o.write(b);


            /*
            // Source code recreated from a .class file by IntelliJ IDEA
            // (powered by FernFlower decompiler)
            //

            package pkg;

            public interface Comparable extends Mesurable {
                int LESS = -1;
                int EQUAL = 0;
                int GREATER = 1;

                int compareTo(Object var1);
            }
            */
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
