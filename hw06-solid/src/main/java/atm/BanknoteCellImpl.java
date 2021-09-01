package atm;

import java.util.ArrayList;
import java.util.List;

public class BanknoteCellImpl implements BanknoteCell {
    private final List<Banknote> banknoteList;

    public BanknoteCellImpl() {
        this.banknoteList = new ArrayList<>();
    }

    /**
     * Остаток в ячейке
     *
     * @return сумма
     */
    public Integer getRest() {
        return banknoteList.stream().mapToInt(Banknote::getDenomination).sum();
    }

    /**
     * Добавить банкноты
     *
     * @param banknotes список
     */
    public void addBanknotes(List<Banknote> banknotes) {
        this.banknoteList.addAll(banknotes);
    }

    /**
     * Забрать банкноту
     *
     * @param banknote банкнота
     */
    public void removeBanknote(Banknote banknote) {
        this.banknoteList.remove(banknote);
    }

    /**
     * Список банкнот
     *
     * @return список
     */
    public List<Banknote> getBanknoteList() {
        return banknoteList.stream().toList(); //The returned List is unmodifiable
    }

}
