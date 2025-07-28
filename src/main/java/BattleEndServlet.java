import java.io.*;
import java.util.ArrayList;
import creature.Character;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/BattleEndServlet")
public class BattleEndServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); // 文字化け防止のためUTF-8に修正
        HttpSession session = request.getSession();
        ArrayList<Character> party = (ArrayList<Character>) session.getAttribute("party");

        PrintWriter out = response.getWriter();
        out.println("<html><head><title>戦闘終了</title></head><body>");
        out.println("<h1>戦闘終了</h1>");

        if (party == null || party.isEmpty()) {
            out.println("<p>味方パーティは全滅してしまった…</p>");
        } else {
            // ▼▼▼ 勝利メッセージを画像に合わせて修正 ▼▼▼
            out.println("<p>敵を全て倒した！勇者達は勝利した！</p>");
        }

        // ▼▼▼ リンクの文字を画像に合わせて修正 ▼▼▼
        out.println("<br><a href='hello-servlet'>もう一度遊ぶ</a>");
        out.println("</body></html>");

        session.invalidate(); // セッションを無効化
    }
}