package movies;

import java.util.ArrayList;
import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Alexey Smolyaninov
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActorTest extends FluentTest{
    
    @LocalServerPort
    private Integer port;
    
    @Autowired
    ActorRepository actorRepository;
    
    @Test
    public void addingAndDeletingAnActor(){
        final String actor = "Uuno Turhapuro";
        
        goTo("http://localhost:" + port + "/actors");
        
        assertFalse(pageSource().contains(actor));
        
        find("#name").fill().with(actor);
        
        find("form").first().submit();
        
        assertTrue(pageSource().contains(actor));
        
        Actor foundedActor = actorRepository.findAll(Example.of(new Actor(actor, new ArrayList<>()))).stream().findFirst().get();
        
        find("#remove-" + foundedActor.getId()).click();
        
        assertFalse(pageSource().contains(actor));
    }
}
