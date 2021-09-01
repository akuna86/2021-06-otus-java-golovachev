package atm;

import java.util.*;
import java.util.stream.Collectors;

public class MyATM {
    private final Map<Integer, BanknoteCell> cellsMap;

    public MyATM() {
        this.cellsMap = new HashMap<>();
    }

    /**
     * Принять банкноты. Группируем банкноты по номиналу затем вставляем их в свои ячейки
     *
     * @param banknoteList список банкнот
     */
    public void takeBanknotes(List<Banknote> banknoteList) {
        Map<Integer, List<Banknote>> banknotesMap = banknoteList.stream().collect(Collectors.groupingBy(Banknote::getDenomination));
        for (Integer key : banknotesMap.keySet()) {
            try {
                this.cellsMap.get(key).addBanknotes(banknotesMap.get(key));
            } catch (Exception ex) {
                throw new RuntimeException("Фальшивка");
            }
        }
    }

    /**
     * Выдача суммы минимальным количеством банкнот или ошибка, если не хватает денег.
     *
     * @param targetSum Нужная сумма
     * @return Список банкнот
     */
    public List<Banknote> getBanknotes(int targetSum) {
        var resultMap = new HashMap<Integer, List<Banknote>>();
        //#костылиВелосипеды :)
        int sum = 0;
        var cell_100 = cellsMap.get(100);
        var cell_50 = cellsMap.get(50);
        while (sum < targetSum) {
            if (targetSum - sum >= 100 && cell_100.getRest() > 0) { //ищем по 100
                var banknote = cell_100.getBanknoteList().get(0);
                var bl = resultMap.computeIfAbsent(100, f -> new ArrayList<>());
                bl.add(banknote);
                cell_100.removeBanknote(banknote);
                sum += banknote.getDenomination();
            } else if (targetSum - sum >= 50 && cell_50.getRest() > 0) { //ищем по 50
                var banknote = cell_50.getBanknoteList().get(0);
                var bl = resultMap.computeIfAbsent(50, f -> new ArrayList<>());
                bl.add(banknote);
                cell_50.removeBanknote(banknote);
                sum += banknote.getDenomination();
            } else { // вернем банкноты. По идее их и брать-то не надо было...
                cell_100.addBanknotes(resultMap.get(100));
                cell_50.addBanknotes(resultMap.get(50));
                throw new RuntimeException("Денег нет, но вы держитесь");
            }
        }

        return resultMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Остаток денег в банкомате
     *
     * @return сумма всех ячеек банкомата
     */
    public Integer getRest() {
        return cellsMap.values().stream().mapToInt(BanknoteCell::getRest).sum();
    }

    /**
     * Список ячеек.
     *
     * @return Unmodifiable Map
     */
    public Map<Integer, BanknoteCell> getCells() {
        return Map.copyOf(cellsMap);
    }

    /**
     * Добавление ячеек нужного номинала
     *
     * @param cell Map.Entry<Integer, BanknoteCell>
     */
    public void addCell(Map.Entry<Integer, BanknoteCell> cell) {
        this.cellsMap.put(cell.getKey(), cell.getValue());
    }
}
