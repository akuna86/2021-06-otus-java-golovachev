package atm;

import java.util.List;
import java.util.Map;

public interface ATM {
    void takeBanknotes(List<Banknote> banknoteList);

    List<Banknote> getBanknotes(int targetSum);

    int getRest();

    Map<Integer, BanknoteCell> getAllCells();

    void addCell(Map.Entry<Integer, BanknoteCell> cell);
}
