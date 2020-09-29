package de.schaefer_dev;

import java.util.Iterator;
import java.util.LinkedList;

public class VoucherPool {
    private Integer nextFreeId;

    /* IMPORTANT REMARK:
        for efficiency reasons pendingVouchers may contain Vouchers which have already been redeemed. Only on calls
        of getPendingVouchers and getRedeemedVouchers the split between both lists is updated. This way the iterations
        over those lists is kept at a minimum.

        Both lists will always remain sorted according to the voucher-id. This is the case because new vouchers are
        always added at the end and because the update function also maintains the sorted property for both lists.
     */
    private LinkedList<Voucher> pendingVouchers;
    private LinkedList<Voucher> redeemedVouchers;

    /* Create new voucher with given value for the given player. Guarantees uniqueness of Voucher ID */
    public Voucher createVoucher(Integer value, Player belongs_to) {
        if (belongs_to == null) {
            throw new IllegalArgumentException("given Player reference is null.");
        }

        Voucher new_voucher = new Voucher(nextFreeId, value, belongs_to);
        nextFreeId += 1;

        // add voucher to player it belongs to and in global voucher list
        pendingVouchers.add(new_voucher);
        belongs_to.vouchers.add(new_voucher);

        return new_voucher;
    }

    public VoucherPool() {
        nextFreeId = 0;
        pendingVouchers = new LinkedList<Voucher>();
        redeemedVouchers = new LinkedList<Voucher>();
    }

    // go through list of pending vouchers and archive all vouchers that have been redeemed since the last
    // call of this method.
    private void archiveRedeemedVouchers() {
        Iterator<Voucher> iter = pendingVouchers.iterator();
        while(iter.hasNext()) {
            Voucher voucher = iter.next ();
            // if voucher was redeemed move it to redeemed voucher list
            if(voucher.getRedeemed()){
                redeemedVouchers.add(voucher);
                iter.remove();
            }
        }
    }

    public LinkedList<Voucher> getPendingVouchers() {
        archiveRedeemedVouchers();
        return pendingVouchers;
    }

    public LinkedList<Voucher> getRedeemedVouchers() {
        archiveRedeemedVouchers();
        return redeemedVouchers;
    }
}
