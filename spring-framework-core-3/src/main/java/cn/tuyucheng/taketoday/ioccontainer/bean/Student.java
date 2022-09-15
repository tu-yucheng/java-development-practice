package cn.tuyucheng.taketoday.ioccontainer.bean;

public class Student {
    private static boolean isBeanInstantiated = false;

    public static boolean isBeanInstantiated() {
        return isBeanInstantiated;
    }

    public static void setBeanInstantiated(boolean isBeanInstantiated) {
        Student.isBeanInstantiated = isBeanInstantiated;
    }

    public void postConstruct() {
        setBeanInstantiated(true);
    }
}