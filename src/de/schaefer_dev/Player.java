package de.schaefer_dev;

import java.sql.Timestamp;
import java.util.LinkedList;

// Player class describes the entity that possesses vouchers
public class Player {

    private String firstname;
    private String lastname;

    private final String identifier;

    LinkedList<Voucher> vouchers;

    public Player(String player_identifier, String player_firstname, String player_lastname) {
        identifier = player_identifier;
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

    public String getIdentifier() {
        return identifier;
    }

    public LinkedList<Voucher> getVouchers() {
        return vouchers;
    }


}
