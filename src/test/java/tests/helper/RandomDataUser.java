package tests.helper;

import api.CreateUser;
import net.datafaker.Faker;

public class RandomDataUser {
    private static final Faker faker = new Faker();

    public static CreateUser generate() {
        String uniqueEmail = faker.internet().emailAddress();
        String password = faker.internet().password(6, 12, true, true);
        String name = faker.name().firstName();

        return new CreateUser(uniqueEmail, password, name);
    }
}
