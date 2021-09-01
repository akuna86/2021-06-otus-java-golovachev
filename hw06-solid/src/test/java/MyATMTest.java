import atm.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyATMTest {
    private final static int DEFAULT_VOLUME = 10;
    MyATM myATM = new MyATM();

    @BeforeEach
    public void prepareATM() {
        //добавим ячейки на 50 и 100 по 10 банкнот в каждой
        List<Banknote> banknotes_50 = Arrays.stream(new Banknote[DEFAULT_VOLUME])
                .map(banknote -> new BanknoteImpl(50))
                .collect(Collectors.toList());
        BanknoteCell cell_50 = new BanknoteCellImpl();
        cell_50.addBanknotes(banknotes_50);
        myATM.addCell(new AbstractMap.SimpleEntry<>(50, cell_50));

        List<Banknote> banknotes_100 = Arrays.stream(new Banknote[DEFAULT_VOLUME])
                .map(banknote -> new BanknoteImpl(100))
                .collect(Collectors.toList());
        BanknoteCell cell_100 = new BanknoteCellImpl();
        cell_100.addBanknotes(banknotes_100);
        myATM.addCell(new AbstractMap.SimpleEntry<>(100, cell_100));
    }

    @Test
    @DisplayName("остаток нового банкомата 1500")
    public void restEquals1500() {
        Assertions.assertEquals(1500, myATM.getRest());
    }

    @Test
    @DisplayName("принять200остаток1700")
    public void takeBanknotes200RestEquals1700() {
        List<Banknote> banknotesList = List.of(new BanknoteImpl(50), new BanknoteImpl(100), new BanknoteImpl(50));
        myATM.takeBanknotes(banknotesList);
        Assertions.assertEquals(1700, myATM.getRest());
    }

    @Test
    @DisplayName("принять 2по50 в ячейку 50 и 1по100 в ячейку 100")
    public void takeBanknotesToTheirCells() {
        List<Banknote> banknotesList = List.of(new BanknoteImpl(50), new BanknoteImpl(100), new BanknoteImpl(50));
        myATM.takeBanknotes(banknotesList);

        var bc = myATM.getCells();

        Assertions.assertEquals(12, bc.get(50).getBanknoteList().size());
        Assertions.assertEquals(11, bc.get(100).getBanknoteList().size());
    }

    @Test
    @DisplayName("положить 500 получить ошибку о подделке")
    public void fakeDenominationThrowRTE() {
        List<Banknote> banknotesList = List.of(new BanknoteImpl(500));

        var rt = Assertions.assertThrows(RuntimeException.class, () -> myATM.takeBanknotes(banknotesList));
        Assertions.assertEquals("Фальшивка", rt.getMessage());
    }

    @Test
    @DisplayName("запросить 150 остаток 1350 и 9по50 и 9по100")
    public void get150With2BanknotesAndRest1350() {
        var myMoney = myATM.getBanknotes(150);
        Assertions.assertEquals(2, myMoney.size());
        Assertions.assertEquals(1350, myATM.getRest());

        var bc = myATM.getCells();

        Assertions.assertEquals(9, bc.get(50).getBanknoteList().size());
        Assertions.assertEquals(9, bc.get(100).getBanknoteList().size());
    }

    @Test
    @DisplayName("запросить 1050 остаток 0по100 и 9по50")
    public void get1050With11BanknotesAndRest450() {
        var myMoney = myATM.getBanknotes(1050);
        Assertions.assertEquals(11, myMoney.size());
        Assertions.assertEquals(450, myATM.getRest());

        var bc = myATM.getCells();

        Assertions.assertEquals(9, bc.get(50).getBanknoteList().size());
        Assertions.assertEquals(0, bc.get(100).getBanknoteList().size());
    }

    @Test
    @DisplayName("Запросить 1550 и получить ошибку нет денег")
    public void get1550ThrowRTEandRestEquals1500() {
        var rt = Assertions.assertThrows(RuntimeException.class, () -> myATM.getBanknotes(1550));
        Assertions.assertEquals("Денег нет, но вы держитесь", rt.getMessage());

        Assertions.assertEquals(1500, myATM.getRest());
    }
}
