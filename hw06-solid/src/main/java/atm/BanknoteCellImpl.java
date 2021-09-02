package atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Ячейка для банкнот
 */
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
    @Override
    public int getRest() {
        return banknoteList.stream().mapToInt(Banknote::getDenomination).sum();
    }

    /**
     * Добавить банкноты
     *
     * @param banknotes список
     */
    @Override
    public void addBanknotes(List<Banknote> banknotes) {
        this.banknoteList.addAll(banknotes);
    }

    /**
     * Забрать банкноты
     *
     * @param cnt количество
     */
    @Override
    public List<Banknote> removeBanknotes(int cnt) {
        List<Banknote> removed = this.banknoteList.subList(banknoteList.size() - cnt, banknoteList.size()).stream().toList();
        this.banknoteList.subList(banknoteList.size() - cnt, banknoteList.size()).clear();
        return removed;
    }

    /**
     * Список банкнот
     *
     * @return список
     */
    @Override
    public List<Banknote> getBanknoteList() {
        return banknoteList.stream().toList(); //The returned List is unmodifiable
    }

}
