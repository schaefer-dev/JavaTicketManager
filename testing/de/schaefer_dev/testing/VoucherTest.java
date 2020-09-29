package de.schaefer_dev.testing;

import de.schaefer_dev.MemberList;
import de.schaefer_dev.Player;
import de.schaefer_dev.Voucher;
import de.schaefer_dev.VoucherPool;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {

    @org.junit.jupiter.api.Test
    void redeem() {
        VoucherPool vouchers = new VoucherPool();
        MemberList memberList = new MemberList();
        Player test_player = memberList.createPlayer("Max", "Mustermann");
        Voucher test_voucher = vouchers.createVoucher(232,  test_player);

        assertFalse(test_voucher.getRedeemed());
        assertNull(test_voucher.getRedeemedDate());
        test_voucher.redeem();

        assertTrue(test_voucher.getRedeemed());
        assertNotNull(test_voucher.getRedeemedDate());
    }
}