import java.io.*;
import java.util.ArrayList; // ArrayListのimportを追加します
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); // 文字化け防止のおまじないを追加

        // --- javaで実行する内容 ---
        HttpSession session = request.getSession();
        ArrayList<String> names = new ArrayList<String>();
        names.add("勇者");
        names.add("魔法使い");
        names.add("盗賊");
        session.setAttribute("names", names);

        // --- ブラウザに表示する内容 ---
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>サーブレットへようこそ！</h1>"); // 見出しを追加
        out.println("<form action=\"SelectServlet\">");
        out.println("<p>誰に攻撃しますか？ (0, 1, 2のいずれか)</p>");
        out.println("<input type=\"text\" name=\"targetIndex\">");
        out.println("<button type=\"submit\">攻撃</button>"); // ボタン名を変更
        out.println("</form>");
        out.println("</body></html>");
    }
}