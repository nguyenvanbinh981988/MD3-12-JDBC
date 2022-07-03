package controller;



import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "UserServlet",urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }


//------------------------------------trien khai do post-------------------------

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }
            try {
                switch (action) {
                    case "create":
                        insertUser(request, response);
                        break;
                    case "edit":
                        updateUser(request, response);
                        break;
                    case "find":
                        findByCountry(request, response);
                        break;
                }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }

    //------------------------------------trien khai do get-------------------------

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "find":
                    showFindByCountry(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        }catch  (SQLException ex) {
            throw new ServletException(ex);
    }
    }

    //------------trien khai cac phuong thuc------------------------------------------------

    // hien thi ve list.jsp
    public void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException,IOException,ServletException {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/list.jsp");
        requestDispatcher.forward(request, response);
    }

    // hien thi ve create.jsp
    public void showNewForm(HttpServletRequest request,HttpServletResponse response) throws ServletException,SQLException,IOException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/create.jsp");
        requestDispatcher.forward(request, response);
    }


    //hien thi ve edit.jsp
    public void showEditForm(HttpServletRequest request,HttpServletResponse response) throws IOException,SQLException,ServletException{
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
        request.setAttribute("user", existingUser);
        requestDispatcher.forward(request, response);
    }


    //xu ly sua thong tin them vao
    public void insertUser(HttpServletRequest request,HttpServletResponse response) throws IOException,SQLException,ServletException{
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User newUser = new User(name,email,country);
        userDAO.insertUser(newUser);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/create.jsp");
        requestDispatcher.forward(request, response);
    }

    //xu ly sua thong tin edit

    public void updateUser(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException,SQLException{
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User book = new User(id,name,email,country);
        userDAO.updateUser(book);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.jsp");
        requestDispatcher.forward(request,response);
    }

    // xu ly xo bo user
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException,SQLException,ServletException{
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);

        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list.jsp");
        dispatcher.forward(request, response);

    }

    // xu ly xo bo findByCountry
    public void showFindByCountry(HttpServletRequest request, HttpServletResponse response) throws IOException,SQLException,ServletException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/find.jsp");
        requestDispatcher.forward(request,response);
    }

    public void findByCountry(HttpServletRequest request,HttpServletResponse response) throws IOException,SQLException,ServletException{
        String country = request.getParameter("country");
        List<User> listFind = userDAO.findByCountry(country);
        request.setAttribute("listFind", listFind);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/list.jsp");
        requestDispatcher.forward(request, response);
    }

}
