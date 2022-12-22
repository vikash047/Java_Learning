package Interview.IO.SplitWiseApplication.Models;

public class Amount {
    private final Currency currency;
    private final Long value;

    public Amount(Currency currency, Long value) {
        this.currency = currency;
        this.value = value;
    }

    public Amount addAmount(Long value) {
        return new Amount(this.currency, this.value + value);
    }

    public Currency getCurrency() {
        return currency;
    }

    public Long getValue() {
        return value;
    }
}
