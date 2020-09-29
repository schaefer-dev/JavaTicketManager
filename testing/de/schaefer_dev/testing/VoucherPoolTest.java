package de.schaefer_dev.testing;

import de.schaefer_dev.MemberList;
import de.schaefer_dev.Player;
import de.schaefer_dev.Voucher;
import de.schaefer_dev.VoucherPool;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class VoucherPoolTest {

    @Test
    void createVoucher() {
        VoucherPool vouchers = new VoucherPool();
        MemberList memberList = new MemberList();
        Player test_player = memberList.createPlayer("Max", "Mustermann");
        Voucher test_voucher_1 = vouchers.createVoucher(232,  test_player);
        Voucher test_voucher_2 = vouchers.createVoucher(999,  test_player);

        assertEquals(232, test_voucher_1.getCentsValue());
        assertEquals("0", test_voucher_1.getIdentifier());
        assertEquals(999, test_voucher_2.getCentsValue());
        assertEquals("1", test_voucher_2.getIdentifier());

    }

    @Test
    void getPendingVouchers() {
        VoucherPool vouchers = new VoucherPool();
        MemberList memberList = new MemberList();
        Player test_player_0 = memberList.createPlayer("Max", "Mustermann");
        Player test_player_1 = memberList.createPlayer("Max", "Mustermann");
        Voucher test_voucher_0 = vouchers.createVoucher(232,  test_player_0);
        Voucher test_voucher_1 = vouchers.createVoucher(999,  test_player_1);
        Voucher test_voucher_2 = vouchers.createVoucher(321,  test_player_0);
        Voucher test_voucher_3 = vouchers.createVoucher(444,  test_player_1);

        LinkedList<Voucher> pendingVouchers = vouchers.getPendingVouchers();
        assertEquals(4, pendingVouchers.size());

        test_voucher_3.redeem();
        pendingVouchers = vouchers.getPendingVouchers();
        assertEquals(3, pendingVouchers.size());

        test_voucher_1.redeem();
        pendingVouchers = vouchers.getPendingVouchers();
        assertEquals(2, pendingVouchers.size());

        test_voucher_0.redeem();
        pendingVouchers = vouchers.getPendingVouchers();
        assertEquals(1, pendingVouchers.size());

        // check that only voucher 2 remains
        assertEquals("2", pendingVouchers.getFirst().getIdentifier());
        test_voucher_2.redeem();
        pendingVouchers = vouchers.getPendingVouchers();
        assertEquals(0, pendingVouchers.size());
    }

    @Test
    void getRedeemedVouchers() {
        VoucherPool vouchers = new VoucherPool();
        MemberList memberList = new MemberList();
        Player test_player_0 = memberList.createPlayer("Max", "Mustermann");
        Player test_player_1 = memberList.createPlayer("Max", "Mustermann");
        Voucher test_voucher_0 = vouchers.createVoucher(232,  test_player_0);
        Voucher test_voucher_1 = vouchers.createVoucher(999,  test_player_1);
        Voucher test_voucher_2 = vouchers.createVoucher(321,  test_player_0);
        Voucher test_voucher_3 = vouchers.createVoucher(444,  test_player_1);

        LinkedList<Voucher> redeemedVouchers = vouchers.getRedeemedVouchers();
        assertEquals(0, redeemedVouchers.size());

        test_voucher_3.redeem();
        redeemedVouchers = vouchers.getRedeemedVouchers();
        assertEquals(1, redeemedVouchers.size());
        assertEquals("3", redeemedVouchers.getFirst().getIdentifier());

        test_voucher_1.redeem();
        redeemedVouchers = vouchers.getRedeemedVouchers();
        assertEquals(2, redeemedVouchers.size());

        test_voucher_0.redeem();
        redeemedVouchers = vouchers.getRedeemedVouchers();
        assertEquals(3, redeemedVouchers.size());

        test_voucher_2.redeem();
        redeemedVouchers = vouchers.getRedeemedVouchers();
        assertEquals(4, redeemedVouchers.size());
    }
}