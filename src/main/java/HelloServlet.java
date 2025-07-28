import java.io.*;
import java.util.ArrayList;
import creature.Character;
import creature.Monster;
import creature.character.*;
import creature.monster.*;
import weapon.Dagger;
import weapon.Sword;
import weapon.Wand;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        // --- 1. ゲームの準備 ---
        // CharacterとMonsterのリストを作成します
        ArrayList<Character> party = new ArrayList<>();
        party.add(new Hero("勇者", 120, new Sword()));
        party.add(new Wizard("魔法使い", 80, 25, new Wand()));
        party.add(new Thief("盗賊", 90, new Dagger()));

        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<String> messages = new ArrayList<>(); // メッセージを保存するリスト
        Random rand = new Random();
        int matangoCount = 0, goblinCount = 0, slimeCount = 0;

        for (int i = 0; i < 5; i++) {
            int monsterType = rand.nextInt(3);
            switch (monsterType) {
                case 0:
                    monsters.add(new Matango((char) ('A' + matangoCount++), 45));
                    break;
                case 1:
                    monsters.add(new Goblin((char) ('A' + goblinCount++), 60));
                    break;
                case 2:
                    monsters.add(new Slime((char) ('A' + slimeCount++), 30));
                    break;
            }
        }

        // --- 2. ゲームの状態をサーバーに保存 ---
        HttpSession session = request.getSession();
        session.setAttribute("party", party);
        session.setAttribute("monsters", monsters);
        session.setAttribute("messages", messages); // メッセージリストも保存
        session.setAttribute("playerIndex", 0); // 次に行動する味方のインデックス

        // --- 3. ブラウザに表示するHTMLを生成 ---
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>RPG Battle</title></head><body>");
        out.println("<h1>冒険が始まる！</h1>");

        // --- ステータス表示 ---
        out.println("<h2>味方パーティ</h2>");
        for (Character c : party) {
            out.println(c.showStatus() + "<br>");
        }
        out.println("<h2>敵グループ</h2>");
        for (Monster m : monsters) {
            out.println(m.showStatus() + "<br>");
        }

        // --- 戦闘開始ボタン ---
        out.println("<form action='SelectServlet' method='get'>");
        out.println("<button type='submit'>戦闘開始</button>");
        out.println("</form>");

        out.println("</body></html>");
    }
}