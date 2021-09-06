package atm;

/**
 * Банкнота
 */
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
    @Override
    public int getDenomination() {
        return this.denomination;
    }
}
