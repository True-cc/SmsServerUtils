package uwu.smsgamer.smsserverutils.utils;

import org.bukkit.OfflinePlayer;
import uwu.smsgamer.senapi.utils.Evaluator;
import uwu.smsgamer.smsserverutils.config.ConfVal;

public class EvalUtils {
    public static final ConfVal<String> scriptStart = new ConfVal<>("start-every-script", "py-settings",
      "def color(inp):\n" +
      "  i = 0\n" +
      "  arr = bytearray(inp, 'unicode_escape')\n" +
      "  brr = bytearray('0123456789AaBbCcDdEeFfKkLlMmNnOoRr', 'unicode_escape')\n" +
      "  while i < len(arr):\n" +
      "    if arr[i] == 38 and arr[i + 1] in brr:\n" +
      "      arr[i] = 0xA7\n" +
      "      arr[i + 1] = arr[i + 1]\n" +
      "    i += 1\n" +
      "  return arr.decode('unicode_escape')");

    public static void init() {
    }

    public static Evaluator newEvaluator(OfflinePlayer player) {
        Evaluator eval = new Evaluator();
        eval.set("player", player);
        eval.exec(scriptStart.getValue());
        return eval;
    }
}
