public class bank {

    private int value;

    public bank(int value)
    {
        this.value = value;
    }

    public void addMoney()
    {
        value += 10;
    }

    public void decreaseMoney()
    {
        value -= 10;
    }

    public int getMoney()
    {
        return value;
    }
}
