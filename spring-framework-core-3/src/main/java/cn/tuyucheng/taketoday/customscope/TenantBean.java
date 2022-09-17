package cn.tuyucheng.taketoday.customscope;

public record TenantBean(String name) {

    public void sayHello() {
        System.out.printf("Hello from %s of type %s%n", this.name, this.getClass().getName());
    }
}