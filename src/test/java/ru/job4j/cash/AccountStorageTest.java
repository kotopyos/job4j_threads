package ru.job4j.cash;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = " + 1));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = " + 2));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @ParameterizedTest
    @CsvSource({"1000, 500, 500",
            "100, -200, 100",
            "100, 0, 0"})
    void whenTransferExtended(int amountFrom, int amountTo, int amountTransfer) {
        var ID_FROM = 1;
        var ID_TO = 2;

        var storage = new AccountStorage();
        storage.add(new Account(ID_FROM, amountFrom));
        storage.add(new Account(ID_TO, amountTo));
        storage.transfer(ID_FROM, ID_TO, amountTransfer);
        var firstAccount = storage.getById(ID_FROM)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = " + ID_FROM));
        var secondAccount = storage.getById(ID_TO)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = " + ID_TO));
        assertThat(firstAccount.amount()).isEqualTo(amountFrom - amountTransfer);
        assertThat(secondAccount.amount()).isEqualTo(amountTo + amountTransfer);
    }

    @ParameterizedTest
    @CsvSource({"-100, 0, 100",
            "0, 100000, 1",
            "100, 0, 101"})
    void whenTransferGreaterAmountThanRecieverHas(int amountFrom, int amountTo, int amountTransfer) {
        var ID_FROM = 1;
        var ID_TO = 2;

        var storage = new AccountStorage();
        storage.add(new Account(ID_FROM, amountFrom));
        storage.add(new Account(ID_TO, amountTo));
        assertThat(storage.transfer(ID_FROM, ID_TO, amountTransfer)).isFalse();
    }

    @Test
    void whenTransferNegativeAmount() {
        var storage = new AccountStorage();

        storage.add(new Account(1, 100));
        storage.add(new Account(2, 0));
        assertThat(storage.transfer(1, 2, -50)).isFalse();
    }
}