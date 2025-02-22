package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean transferStatus = false;

        Optional<Account> sender = getById(fromId);
        Optional<Account> reciever = getById(toId);

        if (sender.isPresent() && reciever.isPresent()
                && sender.get().amount() >= amount
                && amount > 0) {
            update(new Account(fromId, sender.get().amount() - amount));
            update(new Account(toId, reciever.get().amount() + amount));
            transferStatus = true;
        }
        return transferStatus;
    }
}
