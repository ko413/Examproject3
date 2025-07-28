import java.io.*;
import java.util.ArrayList;
import creature.Character;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/BattleEndServlet")
public class BattleEndServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF--8");
        HttpSession session = request.getSession();
        ArrayList<Character> party = (ArrayList<Character>) session.getAttribute("party");

        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Battle End</title></head><body>");
        out.println("<h1>戦闘終了</h1>");

        if (party.isEmpty()) {
            out.println("<h2>味方パーティは全滅してしまった…</h2>");
        } else {
            out.println("<h2>敵を全て倒した！味方パーティは勝利した！</h2>");
        }

        out.println("<br><a href='hello-servlet'>もう一度冒険する</a>");
        out.println("</body></html>");

        session.invalidate(); // セッションを無効化
    }
}