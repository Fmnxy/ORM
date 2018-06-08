package me.jume.test;

import me.jume.dao.ORMHelper;
import me.jume.vo.Account;

public class TestConn {

    public static void main(String[] args) {
        ORMHelper<Account> ormHelper = new ORMHelper<Account>();

        Account account = (Account) ormHelper.load(Account.class,2);
        System.out.println(account);


    }
}
