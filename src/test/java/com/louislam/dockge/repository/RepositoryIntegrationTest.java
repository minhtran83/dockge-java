package com.louislam.dockge.repository;

import com.louislam.dockge.model.User;
import com.louislam.dockge.model.Agent;
import com.louislam.dockge.model.Stack;
import com.louislam.dockge.model.Setting;
import com.louislam.dockge.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class RepositoryIntegrationTest extends SpringBootIntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Test
    public void testUserCRUD() {
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("hash");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("testuser");
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testAgentCRUD() {
        Agent agent = new Agent();
        agent.setUrl("http://localhost:5001");
        agent.setUsername("admin");
        agent.setPassword("password");
        agentRepository.save(agent);

        Optional<Agent> found = agentRepository.findByUrl("http://localhost:5001");
        assertThat(found).isPresent();
        assertThat(found.get().getUrl()).isEqualTo("http://localhost:5001");
    }

    @Test
    public void testStackCRUD() {
        Stack stack = new Stack();
        stack.setName("test-stack");
        stackRepository.save(stack);

        Optional<Stack> found = stackRepository.findByName("test-stack");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("test-stack");
    }

    @Test
    public void testSettingCRUD() {
        Setting setting = new Setting();
        setting.setKey("theme");
        setting.setValue("dark");
        settingRepository.save(setting);

        Optional<Setting> found = settingRepository.findByKey("theme");
        assertThat(found).isPresent();
        assertThat(found.get().getValue()).isEqualTo("dark");
    }
}
