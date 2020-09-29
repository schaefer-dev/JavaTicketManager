package de.schaefer_dev;

import java.io.File;
import java.util.LinkedList;

public class MemberList {

    private Integer nextFreeId;
    private LinkedList<Player> members;

    /* Create empty Memberlist */
    public MemberList() {
        nextFreeId = 0;
        members = new LinkedList<Player>();
    }

    /* Create Memberlist from existing files */
    public MemberList(File members_csv_file) {
        nextFreeId = 0;
        members = new LinkedList<Player>();

        // TODO: parse file
    }

    /* Create new Player with the given name. Guarantees uniqueness of Player ID */
    public Player createPlayer(String firstname, String lastname) {
        Player new_player = new Player(nextFreeId.toString(), firstname, lastname);
        nextFreeId += 1;

        // add player to global player list
        members.add(new_player);

        return new_player;
    }

    public Player getPlayer(String player_identifier) {
        for (Player player: members) {
            if (player.getIdentifier().equals(player_identifier)) {
                return player;
            }
        }
        return null;
    }
}
