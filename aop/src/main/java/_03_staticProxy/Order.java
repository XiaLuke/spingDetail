package _03_staticProxy;

class Order {
    private String orderNo;

    public String orderNo() {
        return orderNo;
    }

    public Order setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
}
