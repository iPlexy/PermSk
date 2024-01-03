package de.iplexy.permsk.utils;

import ch.njol.skript.Skript;
import de.iplexy.permsk.PermSk;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bstats.charts.SimplePie;

import java.util.HashMap;
import java.util.Map;

public class BStats {

    public static void loadStats() {
        Metrics metrics = new Metrics(PermSk.getInstance(), 16435);
        metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
        metrics.addCustomChart(new DrilldownPie("permission_plugin", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(PermSk.getPermissionPlugin(), 1);
            map.put(PermSk.getPermissionPlugin(), entry);
            return map;
        }));
        PermSk.sendConsoleMessage("<gray>[<blue>bStats<gray>] <green>Sucessfully loaded");
    }
}
