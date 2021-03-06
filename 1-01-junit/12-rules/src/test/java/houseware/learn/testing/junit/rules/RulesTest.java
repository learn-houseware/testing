package houseware.learn.testing.junit.rules;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RulesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public Timeout timeout = Timeout.seconds(1);

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Test
    public void null_pointer_exception_in_rule(){
        List<Object> objects = new LinkedList<>();
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Index: 1, Size: 0");
        objects.get(1);
    }

    @Test
    public void fail_for_timeout() throws Exception {
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void using_temp_folder() throws IOException {
        File createdFile= folder.newFile("myfile.txt");
        File createdFolder= folder.newFolder("subfolder");
        assertFalse(createdFile.isDirectory());
        assertTrue(createdFolder.isDirectory());
        assertThat(createdFile.getAbsolutePath(), containsString("myfile.txt"));
    }
}
