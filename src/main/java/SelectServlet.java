import java.io.*;
import java.util.ArrayList;
import creature.Character;
import creature.Monster;
import creature.character.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/SelectServlet")
public class SelectServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        ArrayList<Character> party = (ArrayList<Character>) session.getAttribute("party");
        ArrayList<Monster> monsters = (ArrayList<Monster>) session.getAttribute("monsters");
        ArrayList<String> messages = (ArrayList<String>) session.getAttribute("messages");
        int playerIndex = (int) session.getAttribute("playerIndex");

        Character currentCharacter = party.get(playerIndex);

        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Action Select</title></head><body>");
        out.println("<h1>" + currentCharacter.getName() + "のターン</h1>");

        // --- メッセージログの表示 ---
        out.println("<div>");
        for (String message : messages) {
            out.println(message + "<br>");
        }
        messages.clear(); // 表示したらクリア
        out.println("</div><hr>");

        // --- ステータス表示 ---
        out.println("<h2>味方パーティ</h2>");
        for (Character c : party) {
            out.println(c.showStatus() + "<br>");
        }
        out.println("<h2>敵グループ</h2>");
        for (Monster m : monsters) {
            out.println(m.showStatus() + "<br>");
        }
        out.println("<hr>");

        // --- 行動選択フォーム ---
        out.println("<form action='BattleServlet' method='get'>");
        // どのキャラクターが行動したか、次のサーブレットに伝えるためにhiddenで送る
        out.println("<input type='hidden' name='playerIndex' value='" + playerIndex + "'>");

        // --- キャラクターごとの選択肢 ---
        if (currentCharacter instanceof SuperHero) {
            out.println("<p>スーパーヒーローは攻撃するしかない！</p>");
            out.println("<input type='hidden' name='actionChoice' value='1'>");
        } else if (currentCharacter instanceof Hero) {
            out.println("<p>どうする？</p>");
            out.println("<input type='radio' name='actionChoice' value='1' checked> 攻撃");
            out.println("<input type='radio' name='actionChoice' value='2'> スーパーヒーローになる");
        } else if (currentCharacter instanceof Wizard) {
            out.println("<p>どうする？</p>");
            out.println("<input type='radio' name='actionChoice' value='1' checked> 攻撃(石)");
            out.println("<input type='radio' name='actionChoice' value='2'> 魔法攻撃");
        } else if (currentCharacter instanceof Thief) {
            out.println("<p>どうする？</p>");
            out.println("<input type='radio' name='actionChoice' value='1' checked> 攻撃");
            out.println("<input type='radio' name='actionChoice' value='2'> 守る");
        }

        // --- ターゲット選択 ---
        out.println("<p>誰に攻撃しますか？</p>");
        out.println("<select name='targetIndex'>");
        for (int i = 0; i < monsters.size(); i++) {
            out.println("<option value='" + i + "'>" + monsters.get(i).getName() + "</option>");
        }
        out.println("</select>");

        out.println("<br><br><button type='submit'>決定</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}