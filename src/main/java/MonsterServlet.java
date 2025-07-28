import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import creature.Character;
import creature.Monster;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/MonsterServlet")
public class MonsterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        ArrayList<Character> party = (ArrayList<Character>) session.getAttribute("party");
        ArrayList<Monster> monsters = (ArrayList<Monster>) session.getAttribute("monsters");
        ArrayList<String> messages = (ArrayList<String>) session.getAttribute("messages");
        Random rand = new Random();

        messages.add("<hr>--- 敵のターン ---");

        for (Monster m : monsters) {
            if (party.isEmpty()) break;
            Character target = party.get(rand.nextInt(party.size()));

            // Thiefのガード判定
            boolean isGuarded = false;
            if (target instanceof creature.character.Thief) {
                if (((creature.character.Thief) target).isGuarding()) {
                    isGuarded = true;
                }
            }

            // 攻撃実行
            messages.add(m.attack(target));

            // ガードしていた場合のメッセージ
            if (isGuarded && target.isAlive()) {
                messages.add("しかし、" + target.getName() + "は攻撃を回避し、ダメージが入らなかった！");
            }

            if (!target.isAlive()) {
                messages.add(target.die());
            }
        }
        party.removeIf(c -> !c.isAlive());

        if (party.isEmpty()) {
            response.sendRedirect("BattleEndServlet");
        } else {
            response.sendRedirect("SelectServlet");
        }
    }
}