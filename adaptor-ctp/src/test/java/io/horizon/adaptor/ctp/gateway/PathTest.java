package io.horizon.adaptor.ctp.gateway;

import java.io.File;
import java.util.List;

public class PathTest {

    public static void main(String[] args) {

        System.out.println("=========================================");

        System.out.println(System.getenv("JAVA_HOME"));

        System.out.println(new File("").getAbsolutePath() + "/lib");

        File file = new File(new File("").getAbsolutePath() + "/lib/6.3.15/linux");

        if (file.list() != null)
            System.out.println(List.of());

    }

}
