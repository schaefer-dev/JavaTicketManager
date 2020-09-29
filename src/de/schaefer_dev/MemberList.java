package de.schaefer_dev;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/* Structure that maintains the list of Users that may create/own vouchers */
public class MemberList {

    // next free identifier which may be used for a newly created user
    private Integer nextFreeId;

    // Linked list that contains all users.
    private final LinkedList<Player> members;

    /* Create empty Memberlist.
     * This constructor is overloaded. */
    public MemberList() {
        nextFreeId = 0;
        members = new LinkedList<Player>();
    }

    /* Create Memberlist from existing csv file.
     * This constructor is overloaded. */
    public MemberList(String members_csv_file_path) {
        nextFreeId = 0;
        members = new LinkedList<Player>();

        BufferedReader buffered_reader = null;
        String line = "";
        String csv_split_by = ";";

        try {

            buffered_reader = new BufferedReader(new FileReader(members_csv_file_path));
            // skip first line
            buffered_reader.readLine();

            // read until last line is reached
            while ((line = buffered_reader.readLine()) != null) {

                String[] values = line.split(csv_split_by);
                createPlayerWithID(values[0], values[1], values[2]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffered_reader != null) {
                try {
                    buffered_reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Create new Player with the given name. Guarantees uniqueness of Player ID */
    public Player createPlayer(String firstname, String lastname) {
        Player new_player = createPlayerWithID(nextFreeId.toString(), firstname, lastname);
        nextFreeId += 1;
        return new_player;
    }

    /* Create new Player with the given name. Guarantees uniqueness of Player ID */
    private Player createPlayerWithID(String identifier, String firstname, String lastname) {
        Player new_player = new Player(identifier, firstname, lastname);
        // add player to global player list
        members.add(new_player);
        return new_player;
    }

    public Player getPlayer(String player_identifier) {
        for (Player player : members) {
            if (player.getIdentifier().equals(player_identifier)) {
                return player;
            }
        }
        return null;
    }

    public Integer getSize() {
        return members.size();
    }

    // prints details for all players that participate
    public void reportAllPlayers() {
        if (members.size() > 0) {
            System.out.println("The following players take part in this game:");
            for (Player player : members) {
                player.debug_print();
            }
        } else {
            System.out.println("No players take part in this game.");
        }
    }
}
