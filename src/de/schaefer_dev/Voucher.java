package de.schaefer_dev;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

// (I decided against using a Voucher Interface/Parent with the implementations of redeemed and pending voucher on purpose!)
public class Voucher {
    // Voucher Value tracked in ints to avoid imprecisions caused by usage of Float for currency
    private final Integer centsValue;
    private final String identifier;
    private final Player belongsTo;
    private Boolean redeemed;
    private Timestamp redeemedDate;
    private final Timestamp createdDate;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public Voucher(String voucher_identifier, Integer voucher_cents_value, Player voucher_belongs_to) {
        identifier = voucher_identifier;
        centsValue = voucher_cents_value;
        belongsTo = voucher_belongs_to;
        redeemed = false;
        createdDate = new Timestamp(System.currentTimeMillis());
        redeemedDate = null;
    }

    /* attempts to redeem this Voucher, returns true if successful, false on failure */
    public Boolean redeem() {
        if (redeemed) {
            return false;
        }
        redeemed = true;
        redeemedDate = new Timestamp(System.currentTimeMillis());
        return true;
    }

    /* used to display the content of this voucher in a readable fashion to the console */
    public void debug_print() {
        System.out.print("Voucher with Identifier " + identifier);
        Integer euros = centsValue / 100;
        Integer cents = centsValue % 100;
        System.out.print(" of value " + euros.toString() + "." + String.format("%02d", cents));
        System.out.print(" belonging to " + belongsTo.getFirstname() + " " + belongsTo.getLastname());
        System.out.print(" was created at " + sdf.format(createdDate));
        if (redeemed) {
            System.out.println(" and was redeemed on the " + sdf.format(redeemedDate));
        } else {
            System.out.println(" and has not yet been redeemed.");
        }
    }

    public Integer getCentsValue() {
        return centsValue;
    }

    public Player getBelongsTo() {
        return belongsTo;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Boolean getRedeemed() {
        return redeemed;
    }

    public Timestamp getRedeemedDate() {
        return redeemedDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
