package de.schaefer_dev;

import java.sql.Timestamp;
import java.util.LinkedList;

// Player class describes the entity that possesses vouchers
public class Player {

    private String firstname;
    private String lastname;

    private final Integer id;

    LinkedList<Voucher> vouchers;

    public Player(Integer player_id, String player_firstname, String player_lastname) {
        id = player_id;
        firstname = player_firstname;
        lastname = player_lastname;

        vouchers = new LinkedList<Voucher>();
    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getId() {
        return id;
    }

    public LinkedList<Voucher> getVouchers() {
        return vouchers;
    }


}
