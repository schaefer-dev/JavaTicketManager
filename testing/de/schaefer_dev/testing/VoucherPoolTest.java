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

        LinkedList<Voucher> pendingVouchers = vouchers.getOpenVouchers();
        assertEquals(4, pendingVouchers.size());

        test_voucher_3.redeem();
        pendingVouchers = vouchers.getOpenVouchers();
        assertEquals(3, pendingVouchers.size());

        test_voucher_1.redeem();
        pendingVouchers = vouchers.getOpenVouchers();
        assertEquals(2, pendingVouchers.size());

        test_voucher_0.redeem();
        pendingVouchers = vouchers.getOpenVouchers();
        assertEquals(1, pendingVouchers.size());

        // check that only voucher 2 remains
        assertEquals("2", pendingVouchers.getFirst().getIdentifier());
        test_voucher_2.redeem();
        pendingVouchers = vouchers.getOpenVouchers();
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

    @Test
    void getVoucher() {
        VoucherPool vouchers = new VoucherPool();
        MemberList memberList = new MemberList();
        Player test_player_0 = memberList.createPlayer("Max", "Mustermann");
        Player test_player_1 = memberList.createPlayer("Max", "Mustermann");
        Voucher test_voucher_0 = vouchers.createVoucher(232,  test_player_0);
        Voucher test_voucher_1 = vouchers.createVoucher(999,  test_player_1);
        Voucher test_voucher_2 = vouchers.createVoucher(321,  test_player_0);
        Voucher test_voucher_3 = vouchers.createVoucher(444,  test_player_1);


        test_voucher_1.redeem();
        test_voucher_3.redeem();
        assertEquals(232, vouchers.getVoucher("0").getCentsValue());
        assertEquals(999, vouchers.getVoucher("1").getCentsValue());
        assertEquals(321, vouchers.getVoucher("2").getCentsValue());
        assertEquals(444, vouchers.getVoucher("3").getCentsValue());

    }

    @Test
    void csvLoading() {
        String spieler_file_path = "data/spieler.csv";
        String gutschein_eingeloest_file_path = "data/gutschein_eingeloest.csv";
        String gutschein_ausgegeben_file_path = "data/gutschein_ausgegeben.csv";

        MemberList  member_list = new MemberList(spieler_file_path);
        VoucherPool voucher_pool = new VoucherPool(gutschein_ausgegeben_file_path, gutschein_eingeloest_file_path, member_list);

        assertEquals(4, voucher_pool.getRedeemedVouchers().size());
        assertEquals(2, voucher_pool.getOpenVouchers().size());
        assertEquals(6, voucher_pool.getAllVouchers().size());
    }

    @Test
    void getAllVouchersSorted() {
        String spieler_file_path = "data/spieler.csv";
        String gutschein_eingeloest_file_path = "data/gutschein_eingeloest.csv";
        String gutschein_ausgegeben_file_path = "data/gutschein_ausgegeben.csv";

        MemberList  member_list = new MemberList(spieler_file_path);
        VoucherPool voucher_pool = new VoucherPool(gutschein_ausgegeben_file_path, gutschein_eingeloest_file_path, member_list);

        LinkedList<Voucher> allVouchers = voucher_pool.getAllVouchers();
        assertEquals(6, allVouchers.size());

        assertEquals(3230, allVouchers.get(0).getCentsValue());
        assertEquals(25000, allVouchers.get(1).getCentsValue());
        assertEquals(36000, allVouchers.get(2).getCentsValue());
        assertEquals(100000, allVouchers.get(3).getCentsValue());
        assertEquals(25050, allVouchers.get(4).getCentsValue());
        assertEquals(2475, allVouchers.get(5).getCentsValue());

        // this voucher should be created with identifier 0, hence inserted at the very beginning of the voucher list.
        voucher_pool.createVoucher(777, member_list.getPlayer("113-88"));

        assertEquals(7, allVouchers.size());

        assertEquals(777, allVouchers.get(0).getCentsValue());
        assertEquals(3230, allVouchers.get(1).getCentsValue());
        assertEquals(25000, allVouchers.get(2).getCentsValue());
        assertEquals(36000, allVouchers.get(3).getCentsValue());
        assertEquals(100000, allVouchers.get(4).getCentsValue());
        assertEquals(25050, allVouchers.get(5).getCentsValue());
        assertEquals(2475, allVouchers.get(6).getCentsValue());
    }

    @Test
    void getOpenVouchersSorted() {
        String spieler_file_path = "data/spieler.csv";
        String gutschein_eingeloest_file_path = "data/gutschein_eingeloest.csv";
        String gutschein_ausgegeben_file_path = "data/gutschein_ausgegeben.csv";

        MemberList  member_list = new MemberList(spieler_file_path);
        VoucherPool voucher_pool = new VoucherPool(gutschein_ausgegeben_file_path, gutschein_eingeloest_file_path, member_list);

        LinkedList<Voucher> openVouchers = voucher_pool.getOpenVouchers();
        assertEquals(2, openVouchers.size());

        assertEquals(3230, openVouchers.get(0).getCentsValue());
        assertEquals(100000, openVouchers.get(1).getCentsValue());
    }
}