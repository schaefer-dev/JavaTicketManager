package de.schaefer_dev;

import java.io.*;
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

    /* Create new voucher with the next available identifier. Voucher is assigned to the given player with the given value. */
    public Voucher createVoucher(Integer value, Player belongs_to) {
        if (belongs_to == null) {
            throw new IllegalArgumentException("given Player reference is null.");
        }

        Voucher new_voucher = createVoucherWithIdentifier(nextFreeId.toString(), value, belongs_to);
        nextFreeId += 1;
        return new_voucher;
    }

    /* Create new voucher with given value for the given player. */
    private Voucher createVoucherWithIdentifier(String identifier, Integer value, Player belongs_to) {
        if (belongs_to == null) {
            throw new IllegalArgumentException("given Player reference is null.");
        }

        Voucher new_voucher = new Voucher(identifier, value, belongs_to);

        // add voucher to player it belongs to and in global voucher list
        pendingVouchers.add(new_voucher);
        belongs_to.vouchers.add(new_voucher);

        return new_voucher;
    }

    /* Create empty Voucher Pool in case we want to start from scratch */
    public VoucherPool() {
        nextFreeId = 0;
        pendingVouchers = new LinkedList<Voucher>();
        redeemedVouchers = new LinkedList<Voucher>();
    }

    /* Create empty Voucher Pool from CSV files */
    public VoucherPool(String pending_voucher_csv_file_path, String redeemed_voucher_csv_file_path, MemberList member_list) {
        nextFreeId = 0;
        pendingVouchers = new LinkedList<Voucher>();
        redeemedVouchers = new LinkedList<Voucher>();

        parseVoucherFile(pending_voucher_csv_file_path, false, member_list);
        parseVoucherFile(redeemed_voucher_csv_file_path, true, member_list);
    }

    private void parseVoucherFile(String voucher_csv_file, Boolean redeemed, MemberList member_list) {
        // TODO: parse files
        BufferedReader buffered_reader = null;
        String line = "";
        String csv_split_by = ";";

        try {

            buffered_reader = new BufferedReader(new FileReader(voucher_csv_file));
            // skip first line
            buffered_reader.readLine();

            while ((line = buffered_reader.readLine()) != null) {
                String[] values = line.split(csv_split_by);
                /* For some reason the order of attributes differs for redeemed and pending vouchers, hence both
                    are handled seperatly. */
                if (redeemed) {
                    /* Parse CSV file with redeemed Vouchers */
                    Player player = member_list.getPlayer(values[1]);
                    Voucher new_voucher = createVoucherWithIdentifier(values[0], Integer.parseInt(values[2]), player);
                    new_voucher.redeem();
                    // TODO: date is missing and redeemed property is missing too
                } else {
                    /* Parse CSV file with pending Vouchers */
                    Player player = member_list.getPlayer(values[2]);
                    Voucher new_voucher = createVoucherWithIdentifier(values[0], Integer.parseInt(values[1].replace(".", "")), player);
                    // TODO: date is missing
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffered_reader != null) {
                try {
                    buffered_reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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


    public Voucher getVoucher(String voucher_identifier) {
        for (Voucher voucher: pendingVouchers) {
            if (voucher.getIdentifier().equals(voucher_identifier)) {
                return voucher;
            }
        }
        for (Voucher voucher: redeemedVouchers) {
            if (voucher.getIdentifier().equals(voucher_identifier)) {
                return voucher;
            }
        }
        return null;
    }

    public LinkedList<Voucher> getPendingVouchers() {
        archiveRedeemedVouchers();
        return pendingVouchers;
    }

    // prints details for all pending vouchers to console
    public void reportPendingVouchers() {
        archiveRedeemedVouchers();
        if (pendingVouchers.size() > 0) {
            System.out.println("The following Vouchers are still open:");
            for (Voucher voucher : pendingVouchers) {
                voucher.debug_print();
            }
        } else {
            System.out.println("No Vouchers are currently open.");
        }
    }

    // prints details for all redeemed vouchers to console
    public void reportRedeemedVouchers() {
        archiveRedeemedVouchers();
        if (redeemedVouchers.size() > 0) {
            System.out.println("The following Vouchers have been redeemed:");
            for (Voucher voucher : redeemedVouchers) {
                voucher.debug_print();
            }
        } else {
            System.out.println("No Vouchers have been redeemed yet.");
        }
    }

    public LinkedList<Voucher> getRedeemedVouchers() {
        archiveRedeemedVouchers();
        return redeemedVouchers;
    }
}
