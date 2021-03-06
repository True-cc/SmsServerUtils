package uwu.smsgamer.smsserverutils.commands.commands;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import uwu.smsgamer.smsserverutils.SmsServerUtils;
import uwu.smsgamer.smsserverutils.commands.SmsCommand;
import uwu.smsgamer.smsserverutils.config.*;
import uwu.smsgamer.smsserverutils.managers.ChatFilterManager;
import uwu.smsgamer.smsserverutils.utils.ChatUtils;

import java.util.List;

public class SmsServerUtilsCommand extends SmsCommand {
    public ConfVal<String> noPermissionReload = new ConfVal<>("commands.smsserverutils.no-permission-reload", "messages",
      "%prefix% &cYou do not have permission to execute this command!");
    public ConfVal<String> noPermissionVersion = new ConfVal<>("commands.smsserverutils.no-permission-version", "messages",
      "%prefix% &cYou do not have permission to execute this command!");
    public ConfVal<String> usage = new ConfVal<>("commands.smsserverutils.usage", "messages",
      "%prefix% &rUsage: /%label% <version/reload> [...]");
    public ConfVal<String> reloading = new ConfVal<>("commands.smsserverutils.reloading", "messages",
      "%prefix% &rReloading...");
    public ConfVal<String> reloadSuccess = new ConfVal<>("commands.smsserverutils.reload-success", "messages",
      "%prefix% &rReload success! Time took: %ms%ms");
    public ConfVal<String> reloadFail = new ConfVal<>("commands.smsserverutils.reload-fail", "messages",
      "%prefix% &cReload failed! Please check console for more information. Time took: %ms%ms");
    public ConfVal<String> version = new ConfVal<>("commands.smsserverutils.version", "messages",
      "%prefix% &rPlugin version is %plugin-ver%. Server version is %server-ver%. Bukkit version is %bukkit-ver%.");

    public SmsServerUtilsCommand() {
        super("smsserverutils", true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (testPermission(sender)) {
            if (args.length == 0) {
                ChatUtils.sendMessage(usage.getValue().replace("%label%", label), sender);
            } else {
                switch (args[0].toLowerCase()) {
                    case "reload":
                        if (testPermission(sender, noPermissionReload, "reload")) {
                            ChatUtils.sendMessage(reloading, sender);
                            long begin = System.currentTimeMillis();
                            boolean success = true;
                            for (String s : ConfigManager.configs.keySet().toArray(new String[0])) {
                                try {
                                    ConfigManager.loadConfig(s);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    success = false;
                                }
                            }
                            for (ConfVal<?> val : ConfigManager.vals.toArray(new ConfVal<?>[0])) {
                                ConfigManager.reloadConfVal(val);
                            }
                            ChatFilterManager.getInstance().reload();
                            long end = System.currentTimeMillis();
                            ChatUtils.sendMessage((success ? reloadSuccess : reloadFail).getValue()
                              .replace("%ms%", String.valueOf(end-begin)), sender);
                        }
                        break;
                    case "version":
                        if (testPermission(sender, noPermissionReload, "version")) {
                            JavaPlugin plugin = SmsServerUtils.getInstance().plugin;
                            ChatUtils.sendMessage(version.getValue()
                              .replace("%plugin-ver%", plugin.getDescription().getVersion())
                              .replace("%server-ver%", plugin.getServer().getVersion())
                              .replace("%bukkit-ver%", plugin.getServer().getBukkitVersion()), sender);
                        }
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
