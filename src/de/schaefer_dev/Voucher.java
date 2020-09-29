package de.schaefer_dev;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Voucher {
    private final Integer value;
    private final Integer id;
    private final Player belongsTo;
    private Boolean redeemed;
    private Timestamp redeemedDate;
    private final Timestamp createdDate;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");

    public Voucher(Integer voucher_id, Integer voucher_value, Player voucher_belongs_to) {
        id = voucher_id;
        value = voucher_value;
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
        System.out.print("Voucher with id " + id.toString() + " of value " + value.toString());
        System.out.print(" belonging to " + belongsTo.getFirstname() + " " + belongsTo.getLastname());
        System.out.print(" was created at " + sdf.format(createdDate));
        if (redeemed) {
            System.out.println(" and was redeemed on the " + sdf.format(redeemedDate));
        } else {
            System.out.println(" and has not yet been redeemed.");
        }
    }

    public Integer getValue() {
        return value;
    }

    public Player getBelongsTo() {
        return belongsTo;
    }

    public Integer getId() {
        return id;
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
