package de.schaefer_dev;

import java.sql.Timestamp;
import java.util.LinkedList;

// Player class describes the entity that possesses vouchers
public class Player {

    private String firstname;
    private String lastname;

    private final String identifier;

    // Vouchers created by this Player. Alternatively one could also store a reference to the owner/player in the voucher class
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

    /* used to display the content of this player in a readable fashion to the console */
    public void debug_print() {
        System.out.print("Player " + firstname + " " + lastname + "  with Identifier " + identifier);
        if (vouchers.size() > 0) {
            System.out.println(" owns the following Vouchers:");
            for (Voucher voucher: vouchers) {
                System.out.print("  - ");
                voucher.debug_print();
            }
        } else {
            System.out.println(" does not own any Vouchers.");
        }
    }


}
