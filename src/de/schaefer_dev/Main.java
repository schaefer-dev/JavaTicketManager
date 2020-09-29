package de.schaefer_dev;

public class Main {

    public static void main(String[] args) {
        // Reading information from files

        System.out.println("LETS START THE GAME ....\n");

        String spieler_file_path = "data/spieler.csv";
        String gutschein_eingeloest_file_path = "data/gutschein_eingeloest.csv";
        String gutschein_ausgegeben_file_path = "data/gutschein_ausgegeben.csv";

        MemberList  member_list = new MemberList(spieler_file_path);
        VoucherPool voucher_pool = new VoucherPool(gutschein_ausgegeben_file_path, gutschein_eingeloest_file_path, member_list);


        voucher_pool.reportRedeemedVouchers();
        voucher_pool.reportOpenVouchers();

        System.out.println("\nSearching for voucher with id: 123456789012345678...");
        voucher_pool.getVoucher("123456789012345678").debug_print();

        System.out.println("\nReporting all vouchers for player with id: 113-88...");
        member_list.getPlayer("113-88").debug_print();
    }
}
