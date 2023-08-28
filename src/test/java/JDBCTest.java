import automation.DB;
import automation.db.UserData;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

//Створити тестовий метод, який підключиться до бази даних і здійснить 4 основні види запитів
// (INSERT, SELECT, UPDATE, DELETE), використовувати PreparedStatement з параметрами.
public class JDBCTest
{

    @Test
    public void testInsert() {
        int testId = 4;
        UserData.User testUserToAdd = new UserData.User(
                testId,
                "Frank",
                "Sinatra",
                "fsinatra",
                "827ccb0eea8a706c4c34a16891f84e7",
                "12-12-1915");

        DB.userData.addUser(testUserToAdd);
        UserData.User actualUser = DB.userData.getUserData(testId);

        String testUserData = testUserToAdd.id + "-" + testUserToAdd.first_name + " " + testUserToAdd.last_name;
        String actualUserData = actualUser.id + "-" + actualUser.first_name + " " + actualUser.last_name;

        Assert.assertEquals(testUserData, actualUserData);
    }

    @Test(priority = 1)
    public void testSelect() {
        List<UserData.User> users = DB.userData.getAll();
        for (UserData.User user : users) {
            System.out.println(user.id + "-" + user.first_name + " " + user.last_name);
        }

        Assert.assertEquals(users.size(), 4);
    }

    @Test(priority = 2)
    public void testUpdate() {
        int testId = 4;
        String newLogin = "h1k3g5n9)";
        DB.userData.updateLogin(newLogin, testId);
        UserData.User actualUser = DB.userData.getUserData(testId);

        Assert.assertEquals(actualUser.login, newLogin);
    }

    @Test(priority = 3)
    public void testDelete() {
        int testId = 4;
        DB.userData.deleteUser(testId);

        Assert.assertEquals(DB.userData.getAll().size(), 3);
    }
}
