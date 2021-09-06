package atm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Банкомат
 */
public class MyATM implements ATM {
    private final CellStorage cellStorage;
    private final Comparator<Map.Entry<Integer, BanknoteCell>> cellComparator;

    public MyATM(CellStorage cellStorage) {
        this.cellStorage = cellStorage;
        this.cellComparator = Map.Entry.comparingByKey();
    }

    /**
     * Принять банкноты. Группируем банкноты по номиналу затем вставляем их в свои ячейки
     *
     * @param banknoteList список банкнот
     */
    @Override
    public void takeBanknotes(List<Banknote> banknoteList) {
        Map<Integer, List<Banknote>> banknotesMap = banknoteList.stream().collect(Collectors.groupingBy(Banknote::getDenomination));
        for (Integer key : banknotesMap.keySet()) {
            if (cellStorage.getCell(key) != null)
                cellStorage.addBanknotes(key, banknotesMap.get(key));
            else throw new RuntimeException("Фальшивка");
        }
    }

    /**
     * Выдача суммы минимальным количеством банкнот или ошибка, если не хватает денег.
     *
     * @param targetSum Нужная сумма
     * @return Список банкнот
     */
    @Override
    public List<Banknote> getBanknotes(int targetSum) {
        var resultList = new ArrayList<Banknote>(); //список банкнот на выдачу
        var checkerMap = new HashMap<Integer, Integer>(); // количество банкнот каждого номинала
        //#костылиВелосипеды :)
        int sum = 0;
        //отсортированные ячейки по номиналу от большего
        List<Map.Entry<Integer, BanknoteCell>> orderedEntry = new ArrayList<>(cellStorage.getAllCells().entrySet());
        orderedEntry.sort(cellComparator.reversed());
        //пройдемся по ячейкам и убедимся что там есть нужная сумма
        for (var cellEntry : orderedEntry) {
            var key = cellEntry.getKey();
            var bestCnt = (targetSum - sum) / key;
            var availableCnt = cellEntry.getValue().getBanknoteList().size();
            if (availableCnt != 0 && bestCnt != 0) {
                sum += Integer.min(availableCnt, bestCnt) * key;
                checkerMap.put(key, Integer.min(availableCnt, bestCnt));
            }
            if (sum == targetSum) break;
        }
        if (sum < targetSum) throw new RuntimeException("Денег нет, но вы держитесь");
        //заберем банкноты из ячеек
        for (var removeEntry : checkerMap.entrySet())
            resultList.addAll(cellStorage.removeBanknotes(removeEntry.getKey(), removeEntry.getValue()));

        return resultList;
    }

    /**
     * Остаток денег в банкомате
     *
     * @return сумма всех ячеек банкомата
     */
    @Override
    public int getRest() {
        return cellStorage.getRest();
    }

    /**
     * Список ячеек.
     *
     * @return Unmodifiable Map
     */
    @Override
    public Map<Integer, BanknoteCell> getAllCells() {
        return cellStorage.getAllCells();
    }

    /**
     * Добавление ячеек нужного номинала
     *
     * @param cell Map.Entry<Integer, BanknoteCell>
     */
    @Override
    public void addCell(Map.Entry<Integer, BanknoteCell> cell) {
        this.cellStorage.addCell(cell);
    }
}
