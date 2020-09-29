package de.schaefer_dev;

public class Main {

    public static void main(String[] args) {

        VoucherPool vouchers = new VoucherPool();
        MemberList  memberList = new MemberList();

        Player test_player_0 = memberList.createPlayer("Daniel", "Schäfer");
        Player test_player_1 = memberList.createPlayer("Bob", "Beispiel");
        Voucher test_voucher_0 = vouchers.createVoucher(200,  test_player_0);
        Voucher test_voucher_1 = vouchers.createVoucher(204,  test_player_1);
        Voucher test_voucher_2 = vouchers.createVoucher(999,  test_player_0);
        Voucher test_voucher_3 = vouchers.createVoucher(403,  test_player_1);

        vouchers.reportOpenVouchers();
        vouchers.reportRedeemedVouchers();

        System.out.println("---- redeeming Voucher 0");

        test_voucher_0.redeem();
        vouchers.reportOpenVouchers();
        vouchers.reportRedeemedVouchers();

        System.out.println("---- redeeming Voucher 1");

        test_voucher_1.redeem();
        vouchers.reportOpenVouchers();
        vouchers.reportRedeemedVouchers();

        memberList.reportAllPlayers();

        // Reading information from files

        System.out.println("\nLETS START THE GAME ....\n");

        String spieler_file_path = "data/spieler.csv";
        String gutschein_eingeloest_file_path = "data/gutschein_eingeloest.csv";
        String gutschein_ausgegeben_file_path = "data/gutschein_ausgegeben.csv";

        MemberList  member_list = new MemberList(spieler_file_path);
        VoucherPool voucher_pool = new VoucherPool(gutschein_ausgegeben_file_path, gutschein_eingeloest_file_path, member_list);


        member_list.reportAllPlayers();
    }
}
