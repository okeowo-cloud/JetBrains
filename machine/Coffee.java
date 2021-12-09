package machine;

public enum Coffee {
    ESPRESSO(250, 0, 16, 4), LATTE(350, 75, 20, 7), CAPPUCCINO(200, 100, 12, 6);

    private final int WATER;
    private final int MILK;
    private final int COFFEE_BEANS;
    private final int PRICE;

    Coffee(int water, int milk, int coffeeBeans, int price) {
        this.WATER = water;
        this.MILK = milk;
        this.COFFEE_BEANS = coffeeBeans;
        this.PRICE = price;
    }

    public int getWATER() {
        return WATER;
    }

    public int getMILK() {
        return MILK;
    }

    public int getCOFFEE_BEANS() {
        return COFFEE_BEANS;
    }

    public int getPRICE() {
        return PRICE;
    }
}
