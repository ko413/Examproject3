import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import creature.Character;
import creature.Monster;
import creature.character.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/BattleServlet")
public class BattleServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        ArrayList<Character> party = (ArrayList<Character>) session.getAttribute("party");
        ArrayList<Monster> monsters = (ArrayList<Monster>) session.getAttribute("monsters");
        ArrayList<String> messages = (ArrayList<String>) session.getAttribute("messages");

        int playerIndex = Integer.parseInt(request.getParameter("playerIndex"));
        int actionChoice = Integer.parseInt(request.getParameter("actionChoice"));

        Character currentCharacter = party.get(playerIndex);

        // --- 特殊行動の処理 ---
        if (currentCharacter instanceof Thief && actionChoice == 2) {
            messages.add(((Thief) currentCharacter).guard());
        } else if (currentCharacter instanceof Hero && !(currentCharacter instanceof SuperHero) && actionChoice == 2) {
            SuperHero sh = new SuperHero(currentCharacter.getName(), currentCharacter.getHp(), currentCharacter.getWeapon());
            messages.add(sh.transform());
            party.set(playerIndex, sh);
            if (!sh.isAlive()) {
                messages.add(sh.die());
            }
        } else {
            // --- 攻撃行動の処理 ---
            int targetIndex = Integer.parseInt(request.getParameter("targetIndex"));
            Monster target = monsters.get(targetIndex);

            if (currentCharacter instanceof Wizard && actionChoice == 2) {
                messages.add(((Wizard) currentCharacter).magic(target));
            } else {
                messages.add(currentCharacter.attack(target));
            }
        }

        // --- 攻撃後のモンスターの状態チェック ---
        Iterator<Monster> monsterIterator = monsters.iterator();
        while (monsterIterator.hasNext()) {
            Monster m = monsterIterator.next();
            if (!m.isAlive()) {
                messages.add(m.die());
                monsterIterator.remove();
            } else if (m.getHp() <= 5) {
                messages.add(m.run());
                monsterIterator.remove();
            }
        }

        // --- 次の行動の判定 ---
        if (monsters.isEmpty()) {
            response.sendRedirect("BattleEndServlet");
            return;
        }

        playerIndex++;
        if (playerIndex >= party.size()) {
            session.setAttribute("playerIndex", 0);
            response.sendRedirect("MonsterServlet");
        } else {
            session.setAttribute("playerIndex", playerIndex);
            response.sendRedirect("SelectServlet");
        }
    }
}