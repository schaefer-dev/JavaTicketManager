package de.schaefer_dev;

import java.io.*;
import java.util.Hashtable;
import java.util. Collection;
import java.util.LinkedList;

/* Entity keeps track of all vouchers that have been created until this point in time */
public class VoucherPool {
    private Integer nextFreeId;

    // this list is always sorted by identifier of voucher!
    private LinkedList<Voucher> vouchers;

    /* Create empty Voucher Pool in case we want to start from scratch */
    public VoucherPool() {
        nextFreeId = 0;
        vouchers = new LinkedList<Voucher>();
    }

    /* Create empty Voucher Pool from CSV files */
    public VoucherPool(String pending_voucher_csv_file_path, String redeemed_voucher_csv_file_path, MemberList member_list) {
        nextFreeId = 0;
        vouchers = new LinkedList<Voucher>();

        parseVoucherFile(pending_voucher_csv_file_path, false, member_list);
        parseVoucherFile(redeemed_voucher_csv_file_path, true, member_list);
    }


    private void addVoucherSorted(Voucher new_voucher) {
        // This could be optimized using binary search because list is always sorted to improve complexity to O(log(n))
        Integer target_index = 0;
        for (Voucher voucher: vouchers) {
            if (voucher.getIdentifier().compareTo(new_voucher.getIdentifier()) > 0 ) {
                vouchers.add(target_index, new_voucher);
                return;
            } else {
                target_index += 1;
            }
        }
        vouchers.add(new_voucher);
    }


    /* Create new voucher with the next available identifier. Voucher is assigned to the given player with the given value.
    * This method is overloaded. */
    public Voucher createVoucher(Integer value, Player belongs_to) {
        if (belongs_to == null) {
            throw new IllegalArgumentException("given Player reference is null.");
        }

        Voucher new_voucher = createVoucher(nextFreeId.toString(), value, belongs_to);
        nextFreeId += 1;
        return new_voucher;
    }

    /* Create new voucher with given value for the given player.
    * This method is overloaded. */
    private Voucher createVoucher(String identifier, Integer value, Player belongs_to) {
        if (belongs_to == null) {
            throw new IllegalArgumentException("given Player reference is null.");
        }

        Voucher new_voucher = new Voucher(identifier, value, belongs_to);

        // add voucher to player it belongs to and in global voucher list
        addVoucherSorted(new_voucher);
        belongs_to.vouchers.add(new_voucher);

        return new_voucher;
    }

    /* Create new fully custom voucher with given value and timestamp for the given player.
    * This method is overloaded. */
    private Voucher createVoucher(String identifier, Integer value, Player belongs_to, Boolean redeemed, String timestamp) {
        if (belongs_to == null) {
            throw new IllegalArgumentException("given Player reference is null.");
        }

        Voucher new_voucher = new Voucher(identifier, value, belongs_to, timestamp, redeemed);

        // add voucher to player it belongs to and in global voucher list
        addVoucherSorted(new_voucher);
        belongs_to.vouchers.add(new_voucher);

        return new_voucher;
    }

    private void parseVoucherFile(String voucher_csv_file, Boolean redeemed, MemberList member_list) {
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
                    Voucher voucher = getVoucher(values[0]);
                    voucher.redeem(values[3]);
                } else {
                    /* Parse CSV file with created Vouchers */
                    Player player = member_list.getPlayer(values[2]);

                    // make sure number that do not contain a period yet, get '.00' added at the back
                    String value_string = values[1];
                    if (!value_string.contains(".")) {
                        value_string += ".00";
                    }
                    Voucher new_voucher = createVoucher(values[0], Integer.parseInt(value_string.replace(".", "")), player, false, values[3]);
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

    /* returns Voucher with the given identifier */
    public Voucher getVoucher(String voucher_identifier) {
        // TODO: could be optimized using binary search because list is always sorted to improve complexity to O(log(n))
        for (Voucher voucher: vouchers) {
            if (voucher.getIdentifier().equals(voucher_identifier)) {
                return voucher;
            }
        }
        return null;
    }

    /* returns Linked list of all vouchers that have not yet been redeemed. */
    public LinkedList<Voucher> getOpenVouchers() {
        LinkedList<Voucher> openVochers = new LinkedList<Voucher>();
        for (Voucher voucher: vouchers) {
            if (! voucher.getRedeemed()) {
                openVochers.add(voucher);
            }
        }
        return openVochers;
    }

    /* returns Linked list of all vouchers that have already been redeemed. */
    public LinkedList<Voucher> getRedeemedVouchers() {
        LinkedList<Voucher> redeemedVouchers = new LinkedList<Voucher>();
        for (Voucher voucher: vouchers) {
            if (voucher.getRedeemed()) {
                redeemedVouchers.add(voucher);
            }
        }
        return redeemedVouchers;
    }

    // prints details for all pending vouchers to console
    public void reportOpenVouchers() {
        LinkedList<Voucher> openVouchers = getOpenVouchers();
        if (openVouchers.size() > 0) {
            System.out.println("The following Vouchers are still open:");
            for (Voucher voucher : openVouchers) {
                voucher.debug_print();
            }
        } else {
            System.out.println("No Vouchers are currently open.");
        }
    }

    // prints details for all redeemed vouchers to console
    public void reportRedeemedVouchers() {
        LinkedList<Voucher> redeemedVouchers = getRedeemedVouchers();
        if (redeemedVouchers.size() > 0) {
            System.out.println("The following Vouchers have been redeemed:");
            for (Voucher voucher : redeemedVouchers) {
                voucher.debug_print();
            }
        } else {
            System.out.println("No Vouchers have been redeemed yet.");
        }
    }

    /* Returns all vouchers (order of increasing identifier) */
    public LinkedList<Voucher> getAllVouchers() {
        return vouchers;
    }

    /* Returns the number of existing vouchers. */
    public Integer getSize() {
        return vouchers.size();
    }
}
