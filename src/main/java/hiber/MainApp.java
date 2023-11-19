package hiber;

import hiber.config.AppConfig;
import hiber.exception.UserNotFoundException;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        userService.add(new User(
                "Walter",
                "White",
                "heisenberg@gmail.com",
                new Car("Chrysler 300C I, Sedan SRT8", 123456)));

        userService.add(new User(
                "Hank",
                "Schrader",
                "bestcop@gmail.com",
                new Car("2006 Jeep Commander", 654321)));

        userService.add(new User(
                "Saul",
                "Goodman",
                "betterwritesaul@gmail.com",
                new Car("1997 Cadillac DeVille", 777777)));

        userService.add(new User(
                "Tuco",
                "Salamanca",
                "drugdealer@gmail.com",
                new Car("1970 Pontiac Lemans", 666)));

        List<User> users = userService.listUsers();
        users.forEach(System.out::println);

        printFindedUser(userService, "Lada Sedan", 1111);
        printFindedUser(userService, "1970 Pontiac Lemans", 666);

        context.close();
    }

    private static void printFindedUser(UserService userService, String model, int series) {
        try {
            User user = userService.getUserByModelAndSeries(model, series)
                    .orElseThrow(() -> new UserNotFoundException("user with the car model " + model + " series " + series + " was not found"));
            System.out.println(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
