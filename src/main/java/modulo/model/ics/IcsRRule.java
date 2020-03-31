package modulo.model.ics;

/**
 * todo
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

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getByDay() {
        return byDay;
    }

    public void setByDay(String byDay) {
        this.byDay = byDay;
    }

    @Override
    public String toString() {
        return "FREQ=" + freq + ";"
                + "COUNT=" + count + ";"
                + "BYDAY=" + byDay;
    }
}
