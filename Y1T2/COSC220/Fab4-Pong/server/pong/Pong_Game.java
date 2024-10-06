package minigames.server.pong;

import java.util.*;

import minigames.server.achievements.AchievementHandler;
import minigames.server.pong.PongServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.json.JsonObject;
import minigames.commands.CommandPackage;
import minigames.rendering.*;
import minigames.rendering.NativeCommands.LoadClient;

import static minigames.server.pong.PongAchievement.*;


/**
 * Represents an actual Muddle game in progress
 */
public class Pong_Game {

    /** A logger for logging output */
    private static final Logger logger = LogManager.getLogger(Pong_Game.class);

    static int WIDTH = 2;
    static int HEIGHT = 2;

    record PongPlayer(
        String name,
        int x, int y,
        List<String> inventory
    ) {    
    }

    /** Uniquely identifies this game */
    String name;
    AchievementHandler achievementHandler;
    String playerName;

    public Pong_Game(String name, String playerName) {
        this.name = name;
        this.playerName = playerName;
        this.achievementHandler = new AchievementHandler(PongServer.class);

        // Unlock Muddler achievement for starting a new game
        // achievementHandler.unlockAchievement(playerName, MUDDLER.toString());
    }

    String[][] rooms = new String[][] {
        {
            "You are in a maze of twisting passages, all alike",
            "You are in a maze of twisting passages that weren't so alike after all"
        },
        {
            "You are standing in an open field west of a white house, with a boarded front door. There is a small mailbox here.",
            "You wake up. The room is very gently spinning around your head. Or at least it would be if you could see it which you can't. It is pitch black."
        }
    };

    HashMap<String, PongPlayer> players = new HashMap<>();

    /** The players currently playing this game */
    public String[] getPlayerNames() {
        return players.keySet().toArray(String[]::new);
    }

    /** Metadata for this game */
    public GameMetadata pongMetadata() {
        return new GameMetadata("Pong", name, getPlayerNames(), true);
    }

    /** Describes the state of a player */
    private String describeState(PongPlayer p) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("[%d,%d] \n\n", p.x, p.y));
        sb.append(rooms[p.x()][p.y()]);

        return sb.toString();
    }

    private String directions(int x, int y) {
        String d = "";
        if (x > 0) d += "W";
        if (y > 0) d += "N";
        if (x < WIDTH - 1) d += "E";
        if (x < HEIGHT - 1) d += "S";

        return d;
    }


    public RenderingPackage runCommands(CommandPackage cp) {   
        logger.info("Received command package {}", cp);     
        PongPlayer p = players.get(cp.player());

        // FIXME: Need to actually run the commands!
        String userInput = (String) cp.commands().get(0).getValue("command");

        // Unlock achievements
        ArrayList<JsonObject> renderingCommands = new ArrayList<>();

        switch (userInput) {
            case "START_GAME" -> renderingCommands.add(new JsonObject().put("command", "start_game")
            .put("value","5"));
            
        }

        return new RenderingPackage(this.pongMetadata(), renderingCommands);
    }

    /** Joins this game */
    public RenderingPackage joinGame(String playerName) {
        if (players.containsKey(playerName)) {
            return new RenderingPackage(
                pongMetadata(),
                Arrays.stream(new RenderingCommand[] {
                    new NativeCommands.ShowMenuError("That name's not available")
                }).map((r) -> r.toJson()).toList()
            );
        } else {
           PongPlayer p = new PongPlayer(playerName, 0, 0, List.of());
            players.put(playerName, p);

            ArrayList<JsonObject> renderingCommands = new ArrayList<>();
            renderingCommands.add(new LoadClient("PongUi", "Pong", name, playerName).toJson());
            renderingCommands.add(new JsonObject().put("command", "clearText"));
            renderingCommands.add(new JsonObject().put("command", "appendText").put("text", describeState(p)));
            renderingCommands.add(new JsonObject().put("command", "setDirections").put("directions", directions(p.x(), p.y())));

            return new RenderingPackage(pongMetadata(), renderingCommands);
        }

    }
    
}
