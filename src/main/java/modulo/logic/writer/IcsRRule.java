package modulo.logic.writer;

/**
 * Class that helps
 */
public class IcsRRule {
    private String freq;
    private int count;
    private String byDay;

    public IcsRRule(String freq, int count, String byDay) {
        this.freq = freq;
        this.count = count;
        this.byDay = byDay;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FREQ=" + freq + ";"
                + "COUNT=" + count + ";"
                + "BYDAY=" + byDay;
    }
}
