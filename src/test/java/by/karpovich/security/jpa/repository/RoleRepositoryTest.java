package by.karpovich.security.jpa.repository;

import by.karpovich.security.config.PostgresTestConfiguration;
import by.karpovich.security.jpa.entity.RoleEntity;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@ComponentScan("by.karpovich.security.jpa.repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(PostgresTestConfiguration.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
@DatabaseSetup({
        "classpath:dbunit/role.xml"
})
class RoleRepositoryTest {

    @Autowired
    private  RoleRepository repository;

    @Test
    void findByName() {
        RoleEntity result = repository.findByName("BOSS").get();

        assertThat("Wrong name", result.getName(), is("BOSS WRONG"));
    }

}