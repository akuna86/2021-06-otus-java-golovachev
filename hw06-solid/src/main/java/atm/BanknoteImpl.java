package atm;

public class BanknoteImpl implements Banknote {
    private final int denomination;

    public BanknoteImpl(int denomination) {
        this.denomination = denomination;
    }

    /**
     * Номинал банкноты
     *
     * @return номинал
     */
    public int getDenomination() {
        return this.denomination;
    }
}
