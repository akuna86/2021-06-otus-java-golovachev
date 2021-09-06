package atm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Хранилище ячеек
 */
public class CellStorageImpl implements CellStorage {
    private final Map<Integer, BanknoteCell> cellsMap;

    public CellStorageImpl() {
        this.cellsMap = new HashMap<>();
    }

    /**
     * Получить ячейку хранилища
     *
     * @param key номинал банкнот ячейки
     * @return ячейка
     */
    @Override
    public BanknoteCell getCell(Integer key) {
        return cellsMap.get(key);
    }

    /**
     * Получить все ячейки хранилища
     *
     * @return копия всех ячеек
     */
    @Override
    public Map<Integer, BanknoteCell> getAllCells() {
        return Map.copyOf(cellsMap);
    }

    /**
     * Остаток всех ячеек
     *
     * @return сумма номиналов всех банкнот
     */
    @Override
    public int getRest() {
        return cellsMap.values().stream().mapToInt(BanknoteCell::getRest).sum();
    }

    /**
     * Добавить ячейку в хранилище
     *
     * @param cell номинал и ячейка
     */
    @Override
    public void addCell(Map.Entry<Integer, BanknoteCell> cell) {
        cellsMap.put(cell.getKey(), cell.getValue());
    }

    /**
     * Добавить банкноты в ячейку
     *
     * @param key          номинал
     * @param banknoteList список банкнот
     */
    @Override
    public void addBanknotes(Integer key, List<Banknote> banknoteList) {
        cellsMap.get(key).addBanknotes(banknoteList);
    }

    /**
     * Забрать банкноты из ячейки
     *
     * @param key номинал
     * @param cnt количество
     * @return список убранных банкнот
     */
    @Override
    public List<Banknote> removeBanknotes(Integer key, int cnt) {
        return cellsMap.get(key).removeBanknotes(cnt);
    }
}
