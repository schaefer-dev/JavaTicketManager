package de.schaefer_dev;

import java.util.LinkedList;

public class MemberList {

    private Integer nextFreeId;
    private LinkedList<Player> members;

    public MemberList() {
        nextFreeId = 0;
        members = new LinkedList<Player>();
    }

    /* Create new Player with the given name. Guarantees uniqueness of Player ID */
    public Player createPlayer(String firstname, String lastname) {
        Player new_player = new Player(nextFreeId, firstname, lastname);
        nextFreeId += 1;

        // add player to global player list
        members.add(new_player);

        return new_player;
    }
}
