package de.schaefer_dev.testing;

import de.schaefer_dev.MemberList;
import de.schaefer_dev.Player;
import de.schaefer_dev.Voucher;
import de.schaefer_dev.VoucherPool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberListTest {

    @Test
    void createPlayer() {
        MemberList memberList = new MemberList();
        Player test_player_1 = memberList.createPlayer("Max", "Mustermann");
        Player test_player_2 = memberList.createPlayer("Bernd", "Beispiel");

        assertEquals("Max", test_player_1.getFirstname());
        assertEquals("Mustermann", test_player_1.getLastname());
        assertEquals("0", test_player_1.getIdentifier());

        assertEquals("Bernd", test_player_2.getFirstname());
        assertEquals("Beispiel", test_player_2.getLastname());
        assertEquals("1", test_player_2.getIdentifier());

    }

    @Test
    void getPlayer() {
        MemberList memberList = new MemberList();
        Player test_player_0 = memberList.createPlayer("Max", "Mustermann");
        Player test_player_1 = memberList.createPlayer("Bernd", "Beispiel");
        Player test_player_2 = memberList.createPlayer("Bobby", "Carlsen");

        assertEquals("Bobby", memberList.getPlayer("2").getFirstname());
        assertEquals("Mustermann", memberList.getPlayer("0").getLastname());
        assertNull(memberList.getPlayer("anonymous"));
    }

    @Test
    void csvLoading() {
        MemberList memberList = new MemberList("data/spieler.csv");

        assertEquals(6, memberList.getSize());
        assertEquals("Ferdinand", memberList.getPlayer("114-501").getLastname());
        assertEquals("Walter", memberList.getPlayer("117-123").getFirstname());
    }
}