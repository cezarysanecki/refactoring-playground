package pl.cezarysanecki.demo.app.cloud;

public class CloudUtils {

    public static boolean canRegisterBean() {
        throw new RuntimeException("cannot connect to cloud");
    }

}
