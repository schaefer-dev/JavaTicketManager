package de.schaefer_dev;

public class Main {

    public static void main(String[] args) {

        VoucherPool vouchers = new VoucherPool();
        MemberList  memberList = new MemberList();

        Player test_player = memberList.createPlayer("Daniel", "Schäfer");

        Voucher test_voucher = vouchers.createVoucher(200,  test_player);

        test_voucher.debug_print();
    }
}
