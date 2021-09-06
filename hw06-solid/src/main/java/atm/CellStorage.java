package atm;

import java.util.List;
import java.util.Map;

public interface CellStorage {
    BanknoteCell getCell(Integer key);

    Map<Integer, BanknoteCell> getAllCells();

    int getRest();

    void addCell(Map.Entry<Integer, BanknoteCell> cell);

    void addBanknotes(Integer key, List<Banknote> banknoteList);

    List<Banknote> removeBanknotes(Integer key, int cnt);

}
