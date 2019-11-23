package movies;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.filter.FilterConstructor;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Alexey Smolyaninov
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieTest extends FluentTest{
    
    @LocalServerPort
    private Integer port;
    
    @Test
    public void addingMovieAndLinkingAnActorToIt(){
        
        final String film = "Uuno Epsanjassa";
        final Integer filmLength = 92;
        final String actor = "Uuno Turhapuro";
        
        goTo("http://localhost:" + port + "/movies");
        assertFalse(pageSource().contains(film));
        assertFalse(pageSource().contains(actor));
        
        find("#name").fill().with(film);
        find("#lengthInMinutes").fill().with(filmLength.toString());
        find("form").first().submit();
        
        assertTrue(pageSource().contains(film));
        assertFalse(pageSource().contains(actor));
        
        goTo("http://localhost:" + port + "/actors");
        assertFalse(pageSource().contains(actor));
        
        find("#name").fill().with(actor);
        find("form").first().submit();
        assertTrue(pageSource().contains(actor));
        
        $("a", FilterConstructor.withText(actor)).click();
        $("#add-to-movie").click();
        goTo("http://localhost:" + port + "/movies");
        assertTrue(pageSource().contains(film));
        assertTrue(pageSource().contains(actor));
    }
}
