package de.schaefer_dev;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

// (I decided against using a Voucher Interface/Parent with the implementations of redeemed and pending voucher on purpose!)
public class Voucher implements Comparable<Voucher> {
    // Voucher Value tracked in ints to avoid imprecisions caused by usage of Float for currency
    private final Integer centsValue;
    private final String identifier;
    private final Player belongsTo;
    private Boolean redeemed;
    private String redeemedDate;
    private final String createdDate;

    // Defines desired display-format for timestamps
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    /* Creates new voucher that has not been redeemed yet with the timestep of the current point in time
    * This constructor is overloaded. */
    public Voucher(String voucher_identifier, Integer voucher_cents_value, Player voucher_belongs_to) {
        identifier = voucher_identifier;
        centsValue = voucher_cents_value;
        belongsTo = voucher_belongs_to;
        redeemed = false;
        createdDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        redeemedDate = null;
    }

    /* Creates new voucher of custom information.
    * This constructor is overloaded. */
    public Voucher(String voucher_identifier, Integer voucher_cents_value, Player voucher_belongs_to, String timestamp, Boolean already_redeemed) {
        identifier = voucher_identifier;
        centsValue = voucher_cents_value;
        belongsTo = voucher_belongs_to;
        redeemed = already_redeemed;
        createdDate = timestamp;
        redeemedDate = timestamp;
    }

    /* attempts to redeem this Voucher, returns true if successful, false on failure.
    * This function is overloaded. */
    public Boolean redeem() {
        if (redeemed) {
            return false;
        }
        redeemed = true;
        redeemedDate = sdf.format(new Timestamp(System.currentTimeMillis()));
        return true;
    }

    /* attempts to redeem this Voucher, returns true if successful, false on failure
    * This function is overloaded. */
    public Boolean redeem(String timestamp) {
        if (redeemed) {
            return false;
        }
        redeemed = true;
        redeemedDate = timestamp;
        return true;
    }

    /* used to display the content of this voucher in a readable fashion to the console */
    public void debug_print() {
        System.out.print("Voucher with Identifier " + identifier.toString());
        Integer euros = centsValue / 100;
        Integer cents = centsValue % 100;
        System.out.print(" of value " + euros.toString() + "." + String.format("%02d", cents));
        System.out.print(" belonging to " + belongsTo.getFirstname() + " " + belongsTo.getLastname());
        System.out.print(" was created at " + createdDate);
        if (redeemed) {
            System.out.println(" and was redeemed on the " + redeemedDate);
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

    public String getRedeemedDate() {
        return redeemedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @Override
    public int compareTo(Voucher other) {
        return Double.compare(Double.parseDouble(this.identifier), Double.parseDouble(other.identifier));
    }
}
