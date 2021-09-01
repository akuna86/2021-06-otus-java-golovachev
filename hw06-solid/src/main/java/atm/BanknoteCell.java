package atm;

import java.util.List;

public interface BanknoteCell {
    Integer getRest();

    void addBanknotes(List<Banknote> banknotes);

    void removeBanknote(Banknote banknote);

    List<Banknote> getBanknoteList();
}
