package de.schaefer_dev.testing;

import de.schaefer_dev.MemberList;
import de.schaefer_dev.Voucher;
import de.schaefer_dev.VoucherPool;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void getVouchers() {
        String spieler_file_path = "data/spieler.csv";
        String gutschein_eingeloest_file_path = "data/gutschein_eingeloest.csv";
        String gutschein_ausgegeben_file_path = "data/gutschein_ausgegeben.csv";

        MemberList member_list = new MemberList(spieler_file_path);
        VoucherPool voucher_pool = new VoucherPool(gutschein_ausgegeben_file_path, gutschein_eingeloest_file_path, member_list);

        LinkedList<Voucher> vouchers = member_list.getPlayer("113-88").getVouchers();
        assertEquals(3, vouchers.size());
        assertEquals(25000, vouchers.get(0).getCentsValue());
        assertEquals(25050, vouchers.get(1).getCentsValue());
        assertEquals(100000, vouchers.get(2).getCentsValue());

        vouchers = member_list.getPlayer("115-9").getVouchers();
        assertEquals(0, vouchers.size());
    }
}