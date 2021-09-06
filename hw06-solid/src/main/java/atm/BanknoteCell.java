package atm;

import java.util.List;

public interface BanknoteCell {
    int getRest();

    void addBanknotes(List<Banknote> banknotes);

    List<Banknote> removeBanknotes(int cnt);

    List<Banknote> getBanknoteList();
}
